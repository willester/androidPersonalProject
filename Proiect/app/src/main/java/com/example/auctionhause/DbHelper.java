package com.example.auctionhause;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.Attributes;

public class DbHelper extends SQLiteOpenHelper {

    public static final String BUYERS_TABLE = "BUYERS";

    public static final String BUYER_NAME = "NAME";
    public static final String BUYER_USERNAME="USERNAME";
    public static final String BUYER_PASSWORD="PASSWORD";
    public static final String BUYER_ADULT="ADULT";
    public static final String BUYER_EMAIL="EMAIL";
    public static final String BUYER_ID= "IDB";

    public static final String SELLERS_TABLE = "SELLERS";

    public static final String SELLER_NAME = "NAME";
    public static final String SELLER_USERNAME="USERNAME";
    public static final String SELLER_PASSWORD="PASSWORD";
    public static final String SELLER_EMAIL="EMAIL";
    public static final String SELLER_ID = "IDS";

    public static final String ITEMS_TABLE = "ITEMS";

    public static final String ITEM_NAME = "NAME";
    public static final String Category = "CATEGORY";
    public static final String  MIN_PRICE = "MIN_PRICE";
    public static final String DUE_DATE = "DUE_DATE";
    public static final String ORIGIN_COUNTRY = "COUNTRY_OF_ORIGIN";
    public static final String ORIGIN_CITY = " CITY_OF_ORIGIN";
    public static final String ORIGIN_CONTINENT = "CONTINENT_OF_ORIGIN";
    public static final String ITEM_ID = "IDI";
    public static final String CURRENT_BIDDER = "CURRENT_BIDDER";
    public static final String CURRENT_BID_AMMOUNT = "CURRENT_BID_AMMOUNT";

    public static final String FAVS_TABLE = "FAVORITES";

    public static final String FAV_ID = "IDF";



    public DbHelper(@Nullable Context context) {
        super(context, "auctionHouse.db", null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createBuyersTable = "CREATE TABLE " + BUYERS_TABLE + " ("+BUYER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ BUYER_NAME+" TEXT NOT NULL, "+ BUYER_USERNAME + " TEXT NOT NULL, "+ BUYER_PASSWORD +" TEXT NOT NULL, "+ BUYER_ADULT +" TEXT NOT NULL, "+ BUYER_EMAIL+" TEXT NOT NULL UNIQUE)";
//        String test = "CREATE TABLE \"Buyer\" (\n" +
//                "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
//                "\t\"name\"\tTEXT NOT NULL,\n"  +
//                "\t\"username\"\tTEXT NOT NULL,\n" +
//                "\t\"password\"\tTEXT NOT NULL,\n" +
//                "\t\"adult\"\tTEXT NOT NULL,\n" +
//                "\t\"email\"\tTEXT NOT NULL UNIQUE\n" +
//                ");";

        String createSellersTable = "CREATE TABLE " + SELLERS_TABLE + " ("+SELLER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ SELLER_NAME+" TEXT NOT NULL, "+ SELLER_USERNAME + " TEXT NOT NULL, "+ SELLER_PASSWORD +" TEXT NOT NULL, " + SELLER_EMAIL + " TEXT NOT NULL UNIQUE)";


        db.execSQL(createBuyersTable);
        db.execSQL(createSellersTable);


        String createItemsTable = "CREATE TABLE " + ITEMS_TABLE + " ("+ITEM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_NAME + " TEXT NOT NULL, " + Category + " TEXT NOT NULL, "+ MIN_PRICE +" REAL NOT NULL, " + DUE_DATE+" TEXT NOT NULL, " + ORIGIN_CONTINENT + " TEXT NOT NULL, " + ORIGIN_COUNTRY + " TEXT NOT NULL, " + SELLER_EMAIL + " TEXT, "  + CURRENT_BIDDER + " TEXT, " + CURRENT_BID_AMMOUNT + " REAL, " + ORIGIN_CITY + " TEXT NOT NULL, FOREIGN KEY(" + SELLER_EMAIL +") REFERENCES "+ SELLERS_TABLE + "(" + SELLER_EMAIL+"))";

        db.execSQL(createItemsTable);
        Log.d("creat","a fost creat db ul");


        String createFavsTable = "CREATE TABLE " + FAVS_TABLE + " (" + FAV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BUYER_USERNAME + " TEXT NOT NULL, " + ITEM_NAME + " TEXT NOT NULL)";

        db.execSQL(createFavsTable);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addBuyer(Buyer b) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BUYER_NAME,b.getName());
        cv.put(BUYER_EMAIL,b.getEmail());
        cv.put(BUYER_USERNAME,b.getUsername());
        cv.put(BUYER_PASSWORD,b.getPassword());
        cv.put(BUYER_ADULT,b.getAdult());

        long insert = db.insert(BUYERS_TABLE,null,cv);

        Log.d("DATABAZATA","s a bagat");
    }

    public void addFav(String bUsername, String iName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BUYER_USERNAME,bUsername);
        cv.put(ITEM_NAME,iName);

        long insert = db.insert(FAVS_TABLE,null,cv);


    }


    public void addSeller(Seller s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SELLER_NAME,s.getName());
        cv.put(SELLER_EMAIL,s.getEmail());
        cv.put(SELLER_USERNAME,s.getUsername());
        cv.put(SELLER_PASSWORD,s.getPassword());

        long insert = db.insert(SELLERS_TABLE,null,cv);

        Log.d("DATABAZATA_SELLERS","s a bagat");
    }


    public void addItem(Item i,Seller s)
    {
        try {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ITEM_NAME,i.getName());
        cv.put(Category,i.getCategory());
        cv.put(MIN_PRICE,i.getMinPrice());

            cv.put(DUE_DATE, i.getDueDate().toString());


            cv.put(ORIGIN_CONTINENT, i.getOrigin().getContinent());
            cv.put(ORIGIN_COUNTRY, i.getOrigin().getCountry());
            cv.put(ORIGIN_CITY, i.getOrigin().getCity());
            cv.put(SELLER_EMAIL, s.getEmail());


            long insert = db.insert(ITEMS_TABLE, null, cv);

            Log.d("DATABAZATA_ITEMS", "s a bagat");

        }
         catch (Exception e) {
        Log.d("error", "test");
    }


    }


    public boolean checkValidty(String checkUser,String checkPass)
    {
        SQLiteDatabase sqlDb = this.getWritableDatabase();

        Cursor cursor = sqlDb.rawQuery("SELECT * FROM " + SELLERS_TABLE + " WHERE    USERNAME=? AND PASSWORD=?", new String[]{checkUser,checkPass});

        if(cursor.getCount() <= 0){
            cursor.close();
            Log.d("testLog1",cursor+"");
            return false;
        }
        cursor.close();
        Log.d("testLog2",cursor+"");
        return true;

    }
    public boolean checkValidtyItem(String checkName, String checkCategory){
        SQLiteDatabase sqlDb = this.getWritableDatabase();

        Cursor cursor = sqlDb.rawQuery("SELECT * FROM " + ITEMS_TABLE + " WHERE    NAME=? AND CATEGORY=?", new String[]{checkName,checkCategory});

        if(cursor.getCount() <= 0){
            cursor.close();
            Log.d("testLog1",cursor+"");
            return false;
        }
        cursor.close();
        Log.d("testLog2",cursor+"");
        return true;
    }

    public Seller getLogInSeller(String checkUser,String checkPass)
    {

        SQLiteDatabase sqlDb = this.getWritableDatabase();

        Cursor cursor = sqlDb.rawQuery("SELECT * FROM " + SELLERS_TABLE + " WHERE    USERNAME=? AND PASSWORD=?", new String[]{checkUser,checkPass});

        //String data = cursor.getString(cursor.getColumnIndex("data"));

        Seller s = null;

        while (cursor.moveToNext()) {

            String nume = cursor.getString(cursor.getColumnIndex(SELLER_NAME));
            String username = cursor.getString(cursor.getColumnIndex(SELLER_USERNAME));
            String parola =  cursor.getString(cursor.getColumnIndex(SELLER_PASSWORD));
            String email = cursor.getString(cursor.getColumnIndex(SELLER_EMAIL));

            List<Item> iteme = new ArrayList<Item>();

            Seller returnSeller = new Seller(iteme,nume,username,parola,email);



            s = returnSeller;

        }

        cursor.close();


        return s;

    }


    public List<Item> getLogInUserItems(String sellerEmail)
    {
        List<Item> iteme = new ArrayList<>();

        SQLiteDatabase sqlDb = this.getWritableDatabase();

        Cursor cursor = sqlDb.rawQuery("SELECT * FROM " + ITEMS_TABLE + " WHERE  EMAIL=?", new String[]{sellerEmail});

        Log.d("cursor get items ", cursor.getCount() + "");
        Log.d("cursor get NR COLUMNS", cursor.getColumnName(10) + "");


        if(cursor.getCount() > 0) {

            Log.d("curos items test1 ", "test1");

            while (cursor.moveToNext()) {


                long pret = cursor.getLong(cursor.getColumnIndex(MIN_PRICE));
                String nume = cursor.getString(cursor.getColumnIndex(ITEM_NAME));
                String categorie = cursor.getString(cursor.getColumnIndex(Category));

                Date data = new Date(cursor.getString(cursor.getColumnIndex(DUE_DATE)));

                String country = cursor.getString(cursor.getColumnIndex(ORIGIN_COUNTRY));
                  String continent = cursor.getString(cursor.getColumnIndex("CONTINENT_OF_ORIGIN"));

                String city = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(10)));

                Log.d("test nu crapa city", city);

                Item i = new Item(nume,categorie,pret,data,new PlaceOrigin(country,city,continent));
                iteme.add(i);
            }
        }

        cursor.close();

        return iteme;
    }


    public boolean checkValidtyBuyers(String checkUser,String checkPass)
    {
        SQLiteDatabase sqlDb = this.getWritableDatabase();

        Cursor cursor = sqlDb.rawQuery("SELECT * FROM " + BUYERS_TABLE + " WHERE    USERNAME=? AND PASSWORD=?", new String[]{checkUser,checkPass});

        if(cursor.getCount() <= 0){
            cursor.close();
            Log.d("testLog1",cursor+"");
            return false;
        }
        cursor.close();
        Log.d("testLog2",cursor+"");
        return true;

    }

    public Buyer getLogInBuyer(String checkUser, String checkPass)
    {
        SQLiteDatabase sqlDb = this.getWritableDatabase();

        Cursor cursor = sqlDb.rawQuery("SELECT * FROM " + BUYERS_TABLE + " WHERE    USERNAME=? AND PASSWORD=?", new String[]{checkUser,checkPass});

        //String data = cursor.getString(cursor.getColumnIndex("data"));

        Buyer b = null;

        while (cursor.moveToNext()) {

            String nume = cursor.getString(cursor.getColumnIndex(SELLER_NAME));
            String username = cursor.getString(cursor.getColumnIndex(SELLER_USERNAME));
            String parola =  cursor.getString(cursor.getColumnIndex(SELLER_PASSWORD));
            String email = cursor.getString(cursor.getColumnIndex(SELLER_EMAIL));
            int adult = cursor.getInt(cursor.getColumnIndex(BUYER_ADULT));


            Boolean adultConv = false;

            List<Item> iteme = new ArrayList<Item>();

            if(adult == 1 )
            {
                adultConv = true;
            }


            Buyer returnBuyer = new Buyer(iteme,nume,username,parola,email,adultConv);


            b = returnBuyer;

        }

        cursor.close();

        return b;

    }

    public List<Item> getLogInBuyerItems(String username)
    {
        List<Item> iteme = new ArrayList<>();

        SQLiteDatabase sqlDb = this.getWritableDatabase();

        Cursor cursor = sqlDb.rawQuery("SELECT * FROM " + ITEMS_TABLE + " WHERE  CURRENT_BIDDER=?", new String[]{username});

        Log.d("cursor get items ", cursor.getCount() + "");
        Log.d("cursor get NR COLUMNS", cursor.getColumnName(10) + "");

        if(cursor.getCount() > 0) {

            Log.d("curos items test1 ", "test1");

            while (cursor.moveToNext()) {


                long pret = cursor.getLong(cursor.getColumnIndex(MIN_PRICE));
                String nume = cursor.getString(cursor.getColumnIndex(ITEM_NAME));
                String categorie = cursor.getString(cursor.getColumnIndex(Category));



                Date data = new Date(cursor.getString(cursor.getColumnIndex(DUE_DATE)));

                String country = cursor.getString(cursor.getColumnIndex(ORIGIN_COUNTRY));
                String continent = cursor.getString(cursor.getColumnIndex("CONTINENT_OF_ORIGIN"));

                Log.d("test merge curosr items", continent);




                //crapa ptr un motiv
                String city = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(10)));

                Log.d("test nu crapa city", city);

                Item i = new Item(nume,categorie,pret,data,new PlaceOrigin(country,city,continent));
                iteme.add(i);
            }
        }

        cursor.close();

        return iteme;
    }


    public List<Item> getAllItems(String username)
    {
        List<Item> iteme = new ArrayList<>();

        SQLiteDatabase sqlDb = this.getWritableDatabase();

       // Cursor cursor = sqlDb.rawQuery("SELECT * FROM " + ITEMS_TABLE + " WHERE  CURRENT_BIDDER=?", new String[]{username});


        Cursor cursor = sqlDb.rawQuery("SELECT * FROM  " + ITEMS_TABLE,null );

        if(cursor.getCount() > 0) {

            Log.d("curos items test1 ", "test1");

            while (cursor.moveToNext()) {


                long pret = cursor.getLong(cursor.getColumnIndex(MIN_PRICE));
                String nume = cursor.getString(cursor.getColumnIndex(ITEM_NAME));
                String categorie = cursor.getString(cursor.getColumnIndex(Category));



                Date data = new Date(cursor.getString(cursor.getColumnIndex(DUE_DATE)));

                String country = cursor.getString(cursor.getColumnIndex(ORIGIN_COUNTRY));
                String continent = cursor.getString(cursor.getColumnIndex("CONTINENT_OF_ORIGIN"));

                Log.d("test merge curosr items", continent);




                //crapa ptr un motiv
                String city = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(10)));

                Log.d("test nu crapa city", city);

                Item i = new Item(nume,categorie,pret,data,new PlaceOrigin(country,city,continent));
                iteme.add(i);
            }
        }


        cursor.close();

        Log.d("merge all teims/?",iteme+"");

        return iteme;
    }


    public void tryBecomingthenewBidder(long price, String itemName, String buyerU)
    {

        Log.d("suma primita",price+"");

        SQLiteDatabase sqlDb = this.getWritableDatabase();

        Cursor cursor = sqlDb.rawQuery("SELECT * FROM " + ITEMS_TABLE + " WHERE  NAME=?", new String[]{itemName});

        cursor.moveToFirst();


        long cBigAmount = cursor.getLong(cursor.getColumnIndex(cursor.getColumnName(9)));
       Log.d("selectare corecta suma",cBigAmount+"");

        if( cBigAmount < price)
        {
            ContentValues cv = new ContentValues();
            cv.put(CURRENT_BID_AMMOUNT,price);
            cv.put(CURRENT_BIDDER,buyerU);
            sqlDb.update(ITEMS_TABLE,cv,"NAME = ?",new String[]{itemName});

            Cursor cursor2 = sqlDb.rawQuery("SELECT * FROM " + ITEMS_TABLE + " WHERE  NAME=?", new String[]{itemName});

            cursor2.moveToFirst();


            long changed = cursor.getLong(cursor.getColumnIndex(cursor.getColumnName(9)));
            Log.d("selectare pret mdificat",changed+"");
            cursor2.close();
            Toast.makeText(MainBuyerAct.getContext(), " You are now the current bidder ", Toast.LENGTH_SHORT).show();
        }



        cursor.close();

    }

    public List<Item> getFavoriteItems(String username)
    {

        List<Item> favorites = new ArrayList<>();

        SQLiteDatabase sqlDb = this.getWritableDatabase();
        Cursor cursor = sqlDb.rawQuery("SELECT DISTINCT(NAME) FROM " + FAVS_TABLE + " WHERE  USERNAME=?", new String[]{username});

        Log.d("testare dinctct ",cursor.getCount()+"");

        while (cursor.moveToNext()) {

            String numeProdus = cursor.getString(cursor.getColumnIndex(ITEM_NAME));
            Log.d("testare primt fav name",numeProdus);

            Cursor cursorFav = sqlDb.rawQuery("SELECT * FROM " + ITEMS_TABLE + " WHERE  NAME=?", new String[]{numeProdus});

            if(cursorFav.getCount() > 0 )
            {
                cursorFav.moveToFirst();
                long pret = cursorFav.getLong(cursorFav.getColumnIndex(MIN_PRICE));
                String nume = cursorFav.getString(cursorFav.getColumnIndex(ITEM_NAME));
                String categorie = cursorFav.getString(cursorFav.getColumnIndex(Category));
                Date data = new Date(cursorFav.getString(cursorFav.getColumnIndex(DUE_DATE)));
                String country = cursorFav.getString(cursorFav.getColumnIndex(ORIGIN_COUNTRY));
                String continent = cursorFav.getString(cursorFav.getColumnIndex("CONTINENT_OF_ORIGIN"));

                String city = cursorFav.getString(cursorFav.getColumnIndex(cursorFav.getColumnName(10)));

                Item i = new Item(nume,categorie,pret,data,new PlaceOrigin(country,city,continent));

                favorites.add(i);

            }

            cursorFav.close();
        }

        Log.d("testare finalizare fav",favorites+"");

        cursor.close();

        return favorites;
    }


    public List<Item> getBiddingItemsBuyer(String username)
    {
        List<Item> bidddings = new ArrayList<>();

        SQLiteDatabase sqlDb = this.getWritableDatabase();
        Cursor cursor = sqlDb.rawQuery("SELECT DISTINCT(NAME),CATEGORY,MIN_PRICE,DUE_DATE,COUNTRY_OF_ORIGIN,CITY_OF_ORIGIN,CONTINENT_OF_ORIGIN FROM " + ITEMS_TABLE + " WHERE  CURRENT_BIDDER=? GROUP BY CATEGORY,MIN_PRICE,DUE_DATE,COUNTRY_OF_ORIGIN,CITY_OF_ORIGIN,CONTINENT_OF_ORIGIN", new String[]{username});

        while (cursor.moveToNext()) {

            long pret = cursor.getLong(cursor.getColumnIndex(MIN_PRICE));
            String nume = cursor.getString(cursor.getColumnIndex(ITEM_NAME));
            String categorie = cursor.getString(cursor.getColumnIndex(Category));


            Date data = new Date(cursor.getString(cursor.getColumnIndex(DUE_DATE)));

            String country = cursor.getString(cursor.getColumnIndex(ORIGIN_COUNTRY));
            String continent = cursor.getString(cursor.getColumnIndex("CONTINENT_OF_ORIGIN"));

            Log.d("test merge curosr items", continent);

            String city = cursor.getString(cursor.getColumnIndex("CITY_OF_ORIGIN"));

            Log.d("test nu crapa city", city);

            Item i = new Item(nume,categorie,pret,data,new PlaceOrigin(country,city,continent));
            bidddings.add(i);

        }
        cursor.close();

        return bidddings;
    }

    public long getTotalRevenueSeller(String sellerEmail)
    {
        long totalRev = 0;

        SQLiteDatabase sqlDb = this.getWritableDatabase();
        Cursor cursor = sqlDb.rawQuery("SELECT DISTINCT CURRENT_BID_AMMOUNT FROM " + ITEMS_TABLE + " WHERE  EMAIL=? AND CURRENT_BIDDER IS NOT NULL ", new String[]{sellerEmail});

        while (cursor.moveToNext()) {

            long pret = cursor.getLong(cursor.getColumnIndex(CURRENT_BID_AMMOUNT));
            totalRev += pret;

        }
        cursor.close();
        return totalRev;
    }

    public  List<String> getBuyersUsernamesForReports(String sellerEmail)
    {
        List<String> buyers = new ArrayList<>();

        SQLiteDatabase sqlDb = this.getWritableDatabase();
        Cursor cursor = sqlDb.rawQuery("SELECT DISTINCT(CURRENT_BIDDER) FROM " + ITEMS_TABLE + " WHERE  EMAIL=? AND CURRENT_BIDDER IS NOT NULL ", new String[]{sellerEmail});

        while (cursor.moveToNext()) {

            String buyer = cursor.getString(cursor.getColumnIndex(CURRENT_BIDDER));
            buyers.add(buyer);
        }
        cursor.close();

        return buyers;
    }

   public String getMostBoughtCategory(String sellerEmail)
   {
       String category = "none";

       SQLiteDatabase sqlDb = this.getWritableDatabase();
       Cursor cursor = sqlDb.rawQuery("SELECT COUNT(CATEGORY),CATEGORY FROM " + ITEMS_TABLE + " WHERE  EMAIL=? AND CURRENT_BIDDER IS NOT NULL GROUP BY CATEGORY ORDER BY COUNT(CATEGORY) DESC", new String[]{sellerEmail});

        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            category = cursor.getString(cursor.getColumnIndex(Category));
        }
        cursor.close();

       return category;
   }





}
