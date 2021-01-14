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


public class BiddingsBuyer extends AppCompatActivity {

    Buyer passedArg;
    DbHelper dbhelper = new DbHelper(BiddingsBuyer.this);
    public final static String BuyerFav = "buyetFav";
    public final static String BuyerBiddings = "buyyerBids";
    public final static String doubleEnter = "\n\n";
    static Context context;
    ListView recView ;
    Map<Long, Item> itemMap ;
    TextView pret;
    TextView minPrice;
    List<Item> itemsbiduite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_biddings_buyer);
        Intent intent = getIntent();

        recView = findViewById(R.id.BiddingListBuyer);
        MainBuyerAct.context = getApplicationContext();

        itemMap = new HashMap<>();

        passedArg = (Buyer) intent.getSerializableExtra(MainBuyerAct.BuyerBiddings);

        itemsbiduite = null;

        Log.d("verif dbhelerp merge",dbhelper+"");

        itemsbiduite = dbhelper.getBiddingItemsBuyer(passedArg.getEmail());


        if (itemsbiduite != null) {
            for (Item i : itemsbiduite) {
                itemMap.put(i.getMinPrice(), i);
                CustomAdapterBuyer itemadapter = new CustomAdapterBuyer(this, itemMap,passedArg,dbhelper);

                recView.setAdapter(itemadapter);

            }
        }
    }

    public void SaveReportBiddings(View v) {

        try {
            FileOutputStream fileout=openFileOutput(passedArg.getUsername()+"BidsReport.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);

            if(itemsbiduite != null)
            {
                for(Item i : itemsbiduite)
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
    public void callDiscoverfromBiddings(View view){
        Intent intent = new Intent(this, MainBuyerAct.class);
        Bundle bundle= new Bundle();
        intent.putExtras(bundle);
        startActivityForResult(intent, 100);
    }
    public void callFavoritesfromBiddings(View view){
        Intent intent = new Intent(this, FavoritesActivityBuyer.class);
        Bundle bundle= new Bundle();
        intent.putExtras(bundle);
        startActivityForResult(intent, 100);
    }
    public void callSettingsfromBiddings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        Bundle bundle= new Bundle();
        intent.putExtras(bundle);
        startActivityForResult(intent, 100);
    }



}