package com.example.auctionhause;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddItemActivity extends AppCompatActivity {

   Spinner  s_continent;
   Spinner  s_categories ;
   JSONObject jsonObjItem= new JSONObject();
   JSONObject jsonObjOrigin= new JSONObject();
   private final static String FILE_NAME="jsonFileItem";
   Date duedate;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    public static final String ADD_ITEM = "adaugaLucru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        //spinner for continents
        String[] arraySpinner = new String[] {
                "AF", "EU", "AN", "NA", "OC", "SA", "AS"
        };

          s_continent = (Spinner) findViewById(R.id.spinnerContinent);
          s_categories = (Spinner) findViewById(R.id.spinnerCategory);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_continent.setAdapter(adapter);


        //spinner for categories

        String[] categories = new String[] {
                "Jewelry", "Antiques", "Paintings", "Sculptures", "Collectibles", "Sports Memorabilia", "Old Cars"
        };


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_categories.setAdapter(adapter2);

        SeekBar priceBar = (SeekBar)findViewById(R.id.seekBarPrice);
        TextView price = (TextView)findViewById(R.id.textviewPrice);

        priceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub

                //t1.setTextSize(progress);
             // Toast.makeText(getApplicationContext(), String.valueOf(progress), Toast.LENGTH_LONG).show();
                int proxy = 250*progress;
                price.setText(""+ proxy);
            }
        });

        CalendarView calendar = (CalendarView)findViewById(R.id.calendarViewItem);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                duedate=new Date(year,month,dayOfMonth);

                Log.d("calendar",duedate.toString());
            }
        });


    }

    public void backToFirst(View view)
    {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED,returnIntent);
        finish();
    }


    public void returnFirstCreatedItem(View view)
    {
        Intent returnIntent = new Intent();


        //place of origin construction
        String continent = s_continent.getSelectedItem().toString();

        TextView t_city = (TextView)findViewById(R.id.editCity);
        String city = t_city.getText().toString();

        TextView t_conuntry = (TextView)findViewById(R.id.editContry);
        String country = t_conuntry.getText().toString();

        PlaceOrigin origin = new PlaceOrigin(country,city,continent);


        // item construction
        TextView t_name = (TextView)findViewById(R.id.editItemName);
        String itemName = t_name.getText().toString();

        String category = s_categories.getSelectedItem().toString();

        TextView t_price = (TextView)findViewById(R.id.textviewPrice);
        long price = Long.parseLong(t_price.getText().toString());

        Item item = new Item(itemName,category,price,duedate,origin);

        try {
            jsonObjItem.put("ItemName", itemName.toString());
            jsonObjItem.put("Category", category.toString());
            jsonObjItem.put("Price", price);
            jsonObjItem.put("DueDate", duedate);
            jsonObjOrigin.put("Country", country);
            jsonObjOrigin.put("City", city);
            jsonObjOrigin.put("Continent", continent);
            jsonObjItem.put("Origin", jsonObjOrigin);

            // Convert JsonObject to String Format
            String userString = jsonObjItem.toString();
            // Define the File Path and its Name
            File file = new File(this.getFilesDir(),FILE_NAME);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userString);
            bufferedWriter.close();
        }catch(Exception e){
            Log.d("error", "failed to create jsonObjItem");
        }
    Log.d("creare json obj item", jsonObjItem.toString());
    Log.d("creare item",item.toString());

       // Log.d("calendar")

        returnIntent.putExtra( ADD_ITEM,item);

        setResult(RESULT_OK,returnIntent);

        finish();

    }


}