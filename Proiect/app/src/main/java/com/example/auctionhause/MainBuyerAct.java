package com.example.auctionhause;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class MainBuyerAct extends AppCompatActivity {


    Buyer passedArg;
    DbHelper dbhelper = new DbHelper(MainBuyerAct.this);
    public final static String BuyerFav = "buyetFav";
    public final static String BuyerBiddings = "buyyerBids";
    static Context context;
    ListView recView ;
    Map<Long, Item> itemMap ;
    TextView pret;
    TextView minPrice;
    TextView location;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_buyer);

        Intent intent = getIntent();

        recView = findViewById(R.id.buyerDicoverList);
        MainBuyerAct.context = getApplicationContext();



        itemMap = new HashMap<>();

        passedArg = (Buyer) intent.getSerializableExtra(RegistrationActivity.BuyerACticty);
        if (passedArg == null)
        {
            passedArg =(Buyer) intent.getSerializableExtra(LoginActivity.LOG_IN_Buyer);
        }


        List<Item> itemsNedetinute = null;

        Log.d("verif dbhelerp merge",dbhelper+"");

        itemsNedetinute = dbhelper.getAllItems(passedArg.getUsername());


    if (itemsNedetinute != null) {
        for (Item i : itemsNedetinute) {
            itemMap.put(i.getMinPrice(), i);
            CustomAdapterBuyer itemadapter = new CustomAdapterBuyer(this, itemMap,passedArg,dbhelper);

            recView.setAdapter(itemadapter);

        }
    }


    }


    public void bidForItem(View view)
    {
        pret = findViewById(R.id.editTextItemDiscover);
        minPrice = findViewById(R.id.textViewPriceItem);

        long pretC = Long.parseLong(pret.getText().toString());
        long minPriceC = Long.parseLong(minPrice.getText().toString());

        if(pretC > minPriceC)
        {
            Log.d("vrificare bid ", "test");
        }

    }

    public void callSettings(View view)
    {
        Intent intent = new Intent(this,SettingsActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);

        //start a normal activity
//        startActivity(intent);
        //start an activity for getting back a result
        startActivityForResult(intent, 100);
    }


    public static Context getContext()
    {
        return MainBuyerAct.context;
    }

    public void openFavsActivity(View view)
    {

        Intent intent = new Intent(this,FavoritesActivityBuyer.class);

        intent.putExtra(BuyerFav,passedArg);
        //start a normal activity
        startActivity(intent);



    }

    public void openBiddings(View view)
    {
        Intent intent = new Intent(this,BiddingsBuyer.class);

        intent.putExtra(BuyerBiddings,passedArg);
        startActivity(intent);
    }
//    public void callPaintActivity(View view){
//        Intent intent = new Intent(MainBuyerAct.context, PaintActivity.class);
//        Bundle bundle= new Bundle();
//        intent.putExtras(bundle);
//        startActivityForResult(intent, 102);
//    }
    public void callMapsActivity(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        Bundle bundle= new Bundle();
        intent.putExtras(bundle);
        startActivityForResult(intent, 100);
    }

}