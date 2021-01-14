package com.example.auctionhause;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class LoginActivity extends AppCompatActivity {


    public static final String LOG_IN_SELLER = "logInSellerActicity";
    public static final String LOG_IN_Buyer = "logInBuyerActicity";
    Switch aSwitch;
    String userNameString;
    String userPassString;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        aSwitch = findViewById(R.id.switch1);

    }

    public void checkCredentials(View view) {
        DbHelper db = new DbHelper(LoginActivity.this);

        EditText userName = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText userPass = (EditText) findViewById(R.id.editTextTextPassword);

        userNameString = userName.getText().toString();
        userPassString = userPass.getText().toString();

        sharedPreferences=getSharedPreferences("settings",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("username", userNameString);
        editor.putString("password", userPassString);
        editor.apply();

        if (!aSwitch.isChecked()) {

            boolean SellerExists = db.checkValidty(userNameString, userPassString);

            Log.d("validity", SellerExists + "");

            Seller logSeller = db.getLogInSeller(userNameString, userPassString);

            if (logSeller != null) {
                Log.d("getLogInSeller", logSeller.toString());

                List<Item> items = db.getLogInUserItems(logSeller.getEmail());
                Log.d("verificare primire ite", items + "");


                logSeller.setsItems(items);

                Log.d("getLogInSellerAfterAddi", logSeller.toString());

                Intent intent = new Intent(this, MainSellerAct.class);

                intent.putExtra(LOG_IN_SELLER, logSeller);
                //start a normal activity
                startActivity(intent);

            }


        } else {

            boolean buyerExists = db.checkValidtyBuyers(userNameString, userPassString);

            Buyer logBuyer = db.getLogInBuyer(userNameString, userPassString);

            if (logBuyer != null) {
                Log.d("getLogInSeller", logBuyer.toString());

                List<Item> items = db.getLogInBuyerItems(logBuyer.getUsername());
                Log.d("verificare primire ite", items + "");


                logBuyer.setsItems(items);

                Log.d("getLogInSellerAfterAddi", logBuyer.toString());

                Intent intent = new Intent(this, MainBuyerAct.class);

                intent.putExtra(LOG_IN_Buyer, logBuyer);
                //start a normal activity
                startActivity(intent);

            }
        }
    }
    public void returnFirst(View view) {
        Intent returnIntent = new Intent();

        //  returnIntent.putExtra();

        setResult(RESULT_OK);
        finish();
    }

}