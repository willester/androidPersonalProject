package com.example.auctionhause;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

public class CustomAdapterSeller extends BaseAdapter {

    Map<Long, Item> itemList;
    LayoutInflater layoutInflater;
    Context context;

    private static final String TAG = MainActivity.class.getSimpleName();



    public CustomAdapterSeller(Context context, Map<Long, Item> itemList) {
        this.itemList = itemList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
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
        public ImageView photo;
        public TextView category, name,PlaceOrigin,DueDate;





//        DownloadPhotoByCategory.handler = (Handler)handleMessage(msg)->
//        {
//
//        };




    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemViewHolder holder;

        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.discover_item_seller, parent, false);
            holder = new ItemViewHolder();
            holder.photo = convertView.findViewById(R.id.imgItemSeller);
            holder.category = convertView.findViewById(R.id.tvCategoryItemSel);
            holder.name = convertView.findViewById(R.id.tvNameItemSel);
            holder.PlaceOrigin = convertView.findViewById(R.id.tvPlaceOfOrigin);
            holder.DueDate = convertView.findViewById(R.id.tvDueDate);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ItemViewHolder) convertView.getTag();

        }
        Item i = itemList.get(getItemId(position));
        holder.category.setText(i.getCategory());
        holder.DueDate.setText(i.getDueDate().toString());
        holder.name.setText(i.getName());
        PlaceOrigin o = i.getOrigin();
        holder.PlaceOrigin.setText(o.toString());

//        DownloadPhotoByCategory.handler = new Handler()
//        {
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                Log.d(TAG, "----------image received from thread------------");
//                Bundle data = msg.getData();
//                Bitmap image = data.getParcelable("image");
//                holder.photo.setImageBitmap(image);
//            }
//        };



        String linkPoza = "" ;

        switch (i.getCategory()) {
            case "Jewelry":
                linkPoza = "http://dreamicus.com/data/jewelry/jewelry-08.jpg";
                break;
            case "Antiques":
                linkPoza = "http://meltonmowbraymarket.co.uk/wp-content/uploads/2015/04/0b1ad7a7b79268a1f4558db78e092446_XL.jpg";
                break;
            case "Paintings":
                linkPoza = "https://www.byhien.com/wp-content/uploads/2020/03/ce679ab33ba6bfcb1ba6aeb5f750472b.jpg";
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

        //downloadImage(holder.photo,linkPoza);

        AsynchPhotoDownload downloadAsync = new AsynchPhotoDownload()
        {
            @Override
            protected void onPostExecute(Bitmap b) {
              //  super.onPostExecute(b);

            holder.photo.setImageBitmap(b);

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

