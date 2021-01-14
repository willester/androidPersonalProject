package com.example.auctionhause;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DbHelper dbHelper = new DbHelper(MainActivity.this);
    private final static String FILE_NAME="jsonFile.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get_json();
    }
    public void get_json(){
        String json=null;
        try {
            InputStream is = this.getAssets().open("jsonFile.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            //Log.d("json", json);

            JSONObject jsonObject= new JSONObject(json);
            int noSeller= Integer.parseInt(jsonObject.getString("noSellers"));
           // Log.d("noSel", String.valueOf(noSeller));
            int noBuyer= Integer.parseInt(jsonObject.getString("noBuyers"));
           // Log.d("noBuy", String.valueOf(noBuyer));

            JSONArray jsonArray= jsonObject.getJSONArray("users");
            for(int i=0;i<noSeller;i++){
                JSONObject userSel=jsonArray.getJSONObject(i);
                String nameSel=userSel.getString("name");
                String usernameSel=userSel.getString("username");
                String emailSel=userSel.getString("email");
                String passwordSel=userSel.getString("password");
                Seller seller= new Seller(nameSel, emailSel, usernameSel, passwordSel);

                Log.d("seller created", seller.toString());
                if(!(dbHelper.checkValidty(usernameSel, passwordSel))) {
                    dbHelper.addSeller(seller);
                    Log.d("seller inserted in db", seller.toString());
                }

                JSONArray jsonArrItems= userSel.getJSONArray("items");
                for(int j=0;j<jsonArrItems.length();j++){
                    JSONObject jitem=jsonArrItems.getJSONObject(j);
                    String nameItem=jitem.getString("name");
                    String category=jitem.getString("category");
                    long price=jitem.getLong("price");
                    String sdueDate=jitem.getString("date");
                    Date dueDate=new SimpleDateFormat("dd/mm/yyyy").parse(sdueDate);
                    JSONObject origin=jitem.getJSONObject("origin");
                    String nameCountry=origin.getString("name");
                    String city=origin.getString("city");
                    String continent=origin.getString("continent");

                    PlaceOrigin placeOrigin=new PlaceOrigin(nameCountry, city, continent);
                    Log.d("origin created", placeOrigin.toString());
                    Item item=new Item(nameItem,category,price,dueDate,placeOrigin);
                    Log.d("item created", item.toString());
                    if(!(dbHelper.checkValidtyItem(nameItem, category))) {
                        dbHelper.addItem(item, seller);
                        Log.d("item inserted in db", item.toString());
                    }
                }
            }
            for(int i=noSeller;i<=noBuyer;i++) {
                JSONObject userBuy = jsonArray.getJSONObject(i);
                String nameBuy = userBuy.getString("name");
                String usernameBuy = userBuy.getString("username");
                String emailBuy = userBuy.getString("email");
                String passwordBuy = userBuy.getString("password");
                boolean adult=userBuy.getBoolean("adult");
                Buyer buyer=new Buyer(nameBuy,emailBuy,usernameBuy,passwordBuy,adult);
                //dbHelper.addBuyer(buyer);
                Log.d("buyer created", String.valueOf(buyer));
                if(!(dbHelper.checkValidtyBuyers(usernameBuy,passwordBuy))) {
                    dbHelper.addBuyer(buyer);
                    Log.d("buyer inserted in db", String.valueOf(buyer));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


      public void callRegistration(View view)
    {
        Intent intent = new Intent(this,RegistrationActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);

        //start a normal activity
//        startActivity(intent);
        //start an activity for getting back a result
        startActivityForResult(intent, 100);
    }
    public void callLogIn(View view)
    {
        Intent intent = new Intent(this,LoginActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);

        //start a normal activity
//        startActivity(intent);
        //start an activity for getting back a result
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100)
        {
            if(resultCode == RESULT_OK)
            {
                if(data != null)
                {
                   // Bundle extras = data.getExtras();
                    //Object param2 = extras.get("param2");
                    //if(param2 instanceof String)
                      //  Toast.makeText(this,"Value: " + param2, Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}