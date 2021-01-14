package com.example.auctionhause;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.Map;

import static androidx.core.app.ActivityCompat.startActivityForResult;


public class CustomAdapterBuyer  extends BaseAdapter {

    Map<Long, Item> itemList;
    LayoutInflater layoutInflater;
    Context context;
    DbHelper dbHelper;
    Buyer buyer;
    MainBuyerAct mainBuyerAct = new MainBuyerAct();


    private static final String TAG = MainActivity.class.getSimpleName();



    public CustomAdapterBuyer(Context context, Map<Long, Item> itemList,Buyer b,DbHelper d) {
        this.itemList = itemList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.buyer = b;
        this.dbHelper = d;

    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        Object[] objects = itemList.keySet().toArray();
        return itemList.get(objects[position]);
    }

    @Override
    public long getItemId(int position) {
        Object[] objects = itemList.keySet().toArray();
        return itemList.get(objects[position]).getMinPrice();
    }

    private static class ItemViewHolder
    {
        public ImageButton photo;
        public TextView desc,pret;
        SeekBar priceChanger ;
        Button bidBtn;
        TextView bigPrice;
        long bidPrice;
        CheckBox checkBox;
        CheckBox cbValidate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomAdapterBuyer.ItemViewHolder holder;
        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.discover_item_buyer, parent, false);
            holder = new CustomAdapterBuyer.ItemViewHolder();

            holder.photo = convertView.findViewById(R.id.imageButton);
            holder.pret = convertView.findViewById(R.id.textViewPriceItem);
            holder.desc = convertView.findViewById(R.id.tvName);
            holder.bidBtn = convertView.findViewById(R.id.buttonBid);
            holder.bigPrice = convertView.findViewById(R.id.editTextItemDiscover);
            holder.checkBox = convertView.findViewById(R.id.checkBoxFavoites);
            holder.cbValidate=convertView.findViewById(R.id.cbValidBid);


            convertView.setTag(holder);
        }
        else
        {
            holder = (CustomAdapterBuyer.ItemViewHolder) convertView.getTag();
        }
        Item i = itemList.get(getItemId(position));


        holder.bigPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s.toString() == "" )
//                {
//                    holder.bidPrice = 0;
//                }
//                else {
//                    try {
//                        holder.bidPrice = Long.parseLong(s.toString());
//                    }
//                    catch (Exception e)
//                    {
//                        holder.bidPrice = 0;
//                    }
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString() == "" )
                {
                    holder.bidPrice = 0;
                }
                else {
                    try {
                        holder.bidPrice = Long.parseLong(s.toString());
                    }
                    catch (Exception e)
                    {
                        holder.bidPrice = 0;
                    }
                }
                   }
        });


        holder.desc.setText(i.toString());
        holder.pret.setText(i.getMinPrice()+"");

        DownloadPhotoByCategory.handler =  new Handler()
        {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.d(TAG, "----------image received from thread------------");
                Bundle data = msg.getData();
                Bitmap image = data.getParcelable("image");


                holder.photo.setImageBitmap(Bitmap.createScaledBitmap(image,480,360,false));

            }
        };

        holder.bidBtn.setEnabled(false);
        holder.cbValidate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(holder.cbValidate.isChecked()){
                    Intent intent = new Intent(v.getContext(),PaintActivity.class);
                    v.getContext().startActivity(intent);
                    holder.bidBtn.setEnabled(true);
                }else{
                    holder.bidBtn.setEnabled(false);
                    Toast.makeText(MainBuyerAct.context, "Please validate the not a robot check", Toast.LENGTH_LONG);
                }
            }
        });
        holder.bidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long minPriceC = Long.parseLong(holder.pret.getText().toString());

                if(holder.bidPrice > minPriceC)
                {
                    Log.d("vrificare bid ", "test");
                    dbHelper.tryBecomingthenewBidder(holder.bidPrice,i.getName(),buyer.getUsername() );
                }
                if(holder.checkBox.isChecked())
                {
                    dbHelper.addFav(buyer.getUsername(),i.getName());
                }

            }
        });


        String linkPoza = "" ;

        switch (i.getCategory()) {
            case "Jewelry":
                linkPoza = "http://dreamicus.com/data/jewelry/jewelry-08.jpg";
                break;
            case "Antiques":
                linkPoza = "http://meltonmowbraymarket.co.uk/wp-content/uploads/2015/04/0b1ad7a7b79268a1f4558db78e092446_XL.jpg";
                break;
            case "Paintings":
                linkPoza="https://www.byhien.com/wp-content/uploads/2020/03/ce679ab33ba6bfcb1ba6aeb5f750472b.jpg";
                break;
            case "Sculptures":
                linkPoza = "https://www.ignant.com/wp-content/uploads/2014/03/Top10_Wooden_Sculptures01.jpeg";
                break;
            case "Collectibles":
                linkPoza = "https://eaglecoinstamp.com/wp-content/uploads/2019/04/eagle-coin-stamp-7.jpg?quality=100.3018032623280";
                break;
            case "Sports Memorabilia":
                linkPoza = "https://www.casino.org/blog/wp-content/uploads/Sports-Collection.jpg";
                break;
            case "Old Cars":
                linkPoza = "http://www.mldavisinsurance.com/wp-content/uploads/2015/08/Classic-Car.jpg";
                break;
        }

       // downloadImage(holder.photo,linkPoza);

        AsynchPhotoDownload downloadAsync = new AsynchPhotoDownload()
        {
            @Override
            protected void onPostExecute(Bitmap b) {
                //  super.onPostExecute(b);

                holder.photo.setImageBitmap(Bitmap.createScaledBitmap(b,480,360,false));

            }
        };

        downloadAsync.execute(linkPoza);




//        int photo = context.getResources().getIdentifier("photo" + i.getPhoto(),
//                "drawable", context.getPackageName());
//        holder.photo.setImageDrawable(context.getResources().getDrawable(photo));

        return convertView;
    }

    public void downloadImage(View view,String link)
    {
        Log.d(TAG, "----------downloadImage method------------");
        DownloadPhotoByCategory imageTask = new DownloadPhotoByCategory(link);
        Thread downloadThread = new Thread(imageTask);
        downloadThread.start();
        Log.d(TAG, "----------download thread started------------");
    }

}


