package com.example.auctionhause;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainSellerAct extends AppCompatActivity {

    Seller passedArg;
    DbHelper dbhelper ;
    Context context;
    ListView recView ;
    public final static String SellerBidding = "SELLLER_BIDDING_MENU";
    Map<Long, Item> itemMap ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_seller);

        Intent intent = getIntent();

        recView = findViewById(R.id.recSellerItems);
        itemMap = new HashMap<>();

        passedArg = (Seller) intent.getSerializableExtra(RegistrationActivity.BuyerACticty);
        if (passedArg == null)
        {
            passedArg =(Seller) intent.getSerializableExtra(LoginActivity.LOG_IN_SELLER);
        }



        Log.d("primite items corespur" , passedArg + "");


        List<Item> items = passedArg.getsItems();

        if(items != null)
        {
            for(Item i : passedArg.getsItems())
            {
                itemMap.put(i.getMinPrice(), i);
                CustomAdapterSeller itemadapter = new CustomAdapterSeller(this, itemMap);

                recView.setAdapter(itemadapter);

            }
        }


    }
    public void callAddItem(View view)
    {
        Intent intent = new Intent(this,AddItemActivity.class);
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

               Item i = (Item)data.getSerializableExtra(AddItemActivity. ADD_ITEM);
                passedArg.addItem(i);

                dbhelper = new DbHelper(MainSellerAct.this);
                dbhelper.addItem(i,passedArg);

                itemMap.put(i.getMinPrice(), i);
                CustomAdapterSeller itemadapter = new CustomAdapterSeller(this, itemMap);

                recView.setAdapter(itemadapter);

                Log.d("primire item",passedArg.getsItems().toString());
            }
        }

    }

    public void goToBiddings(View view)
    {
        Intent intent = new Intent(this,SellerBiddingMenu.class);

        intent.putExtra(SellerBidding,passedArg);
        //start a normal activity
        startActivity(intent);

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
}