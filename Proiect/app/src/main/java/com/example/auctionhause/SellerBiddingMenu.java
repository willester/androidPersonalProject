package com.example.auctionhause;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerBiddingMenu extends AppCompatActivity {

    Seller passedArg;
    DbHelper dbHelper = new DbHelper(SellerBiddingMenu.this);
    public final static String doubleEnter = "\n\n";
    TextView reportText1,reportText2,reportText3;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_bidding_menu);
        Intent intent = getIntent();

        passedArg = (Seller) intent.getSerializableExtra(MainSellerAct.SellerBidding);

        reportText1 = findViewById(R.id.BiddingsSeller1TextView);
        reportText2 = findViewById(R.id.BiddingsSeller2TextView);
        reportText3 = findViewById(R.id.BiddingsSeller3TextView);

        long totalIncome = dbHelper.getTotalRevenueSeller(passedArg.getEmail());

        reportText1.setText("The total revenue that you will make with the current bidded items (a 10% tax is applied for using our services) is " + totalIncome*0.9 + " $");

        List<String> buyers = dbHelper.getBuyersUsernamesForReports(passedArg.getEmail());

        StringBuilder buyersList = new StringBuilder();
        for(String s : buyers)
        {
            buyersList.append(s).append(",\n ");
        }

        reportText2.setText("The clients that will receive the invoices when the due date for the items will expire are: "+ buyersList);

        String categorie = dbHelper.getMostBoughtCategory(passedArg.getEmail());

        reportText3.setText("Your most bidded category is: " + categorie);

    }

    public void SaveReport(View v) {

        try {
            FileOutputStream fileout=openFileOutput(passedArg.getUsername()+"Report.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(reportText1.getText().toString());
            outputWriter.write(doubleEnter);
            outputWriter.write(reportText2.getText().toString());
            outputWriter.write(doubleEnter);
            outputWriter.write(reportText3.getText().toString());
            outputWriter.close();
            Toast.makeText(getBaseContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}