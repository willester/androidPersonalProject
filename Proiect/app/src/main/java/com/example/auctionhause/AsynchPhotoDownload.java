package com.example.auctionhause;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;

public class AsynchPhotoDownload extends AsyncTask<String, Void, Bitmap> {


    private  String URL = "";

    @Override
    protected Bitmap doInBackground(String... strings) {

        String imageUrl = strings[0];

        Bitmap bitmap = null;
        try {

            InputStream input = new java.net.URL(imageUrl).openStream();

            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
