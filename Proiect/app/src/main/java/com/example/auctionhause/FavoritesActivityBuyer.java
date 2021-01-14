package com.example.auctionhause;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoritesActivityBuyer extends AppCompatActivity {

    Buyer passedArg;
    DbHelper dbhelper = new DbHelper(FavoritesActivityBuyer.this);
    public final static String BuyerFav = "buyetFav";
    public final static String ReturnDiscover = "returnDiscover";
    public final static String doubleEnter = "\n\n";
    ListView recView ;
    Map<Long, Item> itemMap ;
    TextView pret;
    TextView minPrice;
    List<Item> favorite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_buyer);

        Intent intent = getIntent();

        recView = findViewById(R.id.FavoritesList);

        itemMap = new HashMap<>();

        passedArg = (Buyer) intent.getSerializableExtra(MainBuyerAct.BuyerFav);


        Log.d("primire buyer in favs",passedArg.toString());

        favorite = dbhelper.getFavoriteItems(passedArg.getUsername());

        if (favorite != null) {
            for (Item i : favorite) {
                itemMap.put(i.getMinPrice(), i);
                CustomAdapterBuyer itemadapter = new CustomAdapterBuyer(this, itemMap,passedArg,dbhelper);

                recView.setAdapter(itemadapter);

            }
        }



    }

    public void SaveReportFavorites(View v) {

        try {
            FileOutputStream fileout=openFileOutput(passedArg.getUsername()+"FavoritesReport.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);

            if(favorite != null)
            {
                for(Item i : favorite)
                {
                    outputWriter.write(i.toString());
                    outputWriter.write(doubleEnter);
                }
            }

            outputWriter.close();
            Toast.makeText(getBaseContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void callDiscoverfromFavorites(View view){
        Intent intent = new Intent(this, MainBuyerAct.class);
        Bundle bundle= new Bundle();
        intent.putExtras(bundle);
        startActivityForResult(intent, 100);
    }
    public void callBiddingsfromFavorites(View view){
        Intent intent = new Intent(this, BiddingsBuyer.class);
        Bundle bundle= new Bundle();
        intent.putExtras(bundle);
        startActivityForResult(intent, 100);
    }
    public void callSettingsfromFavorites(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        Bundle bundle= new Bundle();
        intent.putExtras(bundle);
        startActivityForResult(intent, 100);
    }








}