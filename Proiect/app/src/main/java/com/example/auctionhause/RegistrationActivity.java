package com.example.auctionhause;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.channels.ScatteringByteChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    public final static String BuyerACticty = "buyerActivity";
    private final static String FILE_NAME="jsonFileUser";
    DbHelper dbhelper ;
    JSONObject jsonObject= new JSONObject();
    JSONObject jOUser = new JSONObject();
    JSONArray jArrUsers= new JSONArray();
    JSONArray jArrItems= new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void returnFirst(View view) {
        Intent returnIntent = new Intent();

        //  returnIntent.putExtra();

        setResult(RESULT_OK);
        finish();
    }


    public void createUser(View view) {

        try {
            EditText name = (EditText) findViewById(R.id.RegisterName);
            EditText userName = (EditText) findViewById(R.id.RegisterUsername);
            EditText password = (EditText) findViewById(R.id.RegistrationPassword);
            EditText email = (EditText) findViewById(R.id.RegistrationEmail);
            jOUser.put("Name", name.getText().toString());
            jOUser.put("Username", userName.getText().toString());
            jOUser.put("Password", password.getText().toString());
            jOUser.put("Email", email.getText().toString());

            RadioGroup userGroup;
            RadioButton user;
            userGroup = findViewById(R.id.RegRadioGroup);
            int id = userGroup.getCheckedRadioButtonId();
            user = findViewById(id);

            TextView bday = (TextView) findViewById(R.id.RegBday);
            //Log.d("createUser",bday.getText().toString());
            String ceva = bday.getText().toString();

            String month = ceva.substring(0, 2);


            String day = ceva.substring(3, 5);
            String year = ceva.substring(6);


            Calendar birthCal = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));

            Calendar nowCal = new GregorianCalendar();


            int age = nowCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
            boolean isMonthGreater = birthCal.get(Calendar.MONTH) >= nowCal.get(Calendar.MONTH);

            boolean isMonthSameButDayGreater = (birthCal.get(Calendar.MONTH) == nowCal.get(Calendar.MONTH))
                    && (birthCal.get(Calendar.DAY_OF_MONTH) >= nowCal.get(Calendar.DAY_OF_MONTH));


       /*
        if (age < 18) {
             age=age;
        }
        else if((birthCal.get(Calendar.MONTH) < nowCal.get(Calendar.MONTH)) || ((birthCal.get(Calendar.MONTH) == nowCal.get(Calendar.MONTH)) && ( birthCal.get(Calendar.DAY_OF_MONTH)< nowCal.get(Calendar.DAY_OF_MONTH)))){

        age -= 1;
        }
    */
            boolean major =false;
            if (age > 18) {
                Log.d("major", true + "");
                major = true;
                jOUser.put("Adult", major);
            } else if (age == 18) {
                if (birthCal.get(Calendar.MONTH) > nowCal.get(Calendar.MONTH)) {
                    Log.d("major", true + "");
                    major = true;
                    jOUser.put("Adult", major);
                } else if (birthCal.get(Calendar.MONTH) == nowCal.get(Calendar.MONTH)) {
                    int todayDate = nowCal.get(Calendar.DAY_OF_MONTH);
                    int dobDate = birthCal.get(Calendar.DAY_OF_MONTH);
                    if (dobDate <= todayDate) {
                        major = true;
                        jOUser.put("Adult", major);
                        Log.d("major", true + "");
                    } else {
                        Log.d("major", false + "");
                        major = false;
                        jOUser.put("Adult", major);
                    }
                } else {
                    Log.d("major", false + "");
                    major = false;
                    jOUser.put("Adult", major);
                }
            }


        //    Log.d("major" ,major+"");
            int noBuyer=0;
            int noSeller=0;
            if(user.getText().toString().equals("Seller") )
            {
                if(major)
                {
                    noSeller++;
                    List<Item> iteme = new ArrayList<Item>();
                    jOUser.put("Items", jArrItems);
                    Seller s = new Seller(iteme,name.getText().toString(),userName.getText().toString(),password.getText().toString(),email.getText().toString());
                    Log.d("obj", s.toString());

                    dbhelper = new DbHelper(RegistrationActivity.this);
                    dbhelper.addSeller(s);

                    Intent intent = new Intent(this,MainSellerAct.class);
                    //Bundle bundle = new Bundle();


                    intent.putExtra(BuyerACticty,s);
                    //start a normal activity
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this, "  You cannot be a seller unless you are 18 years of age ", Toast.LENGTH_SHORT).show();
                }

            }

            if(user.getText().toString().equals("Buyer") )
            {
                noBuyer++;
                List<Item> iteme = new ArrayList<Item>();
                jOUser.put("Items", jArrItems);
                Buyer b = new Buyer(iteme,name.getText().toString(),userName.getText().toString(),password.getText().toString(),email.getText().toString(),major);
                Log.d("obj", b.toString());

                dbhelper = new DbHelper(RegistrationActivity.this);
                dbhelper.addBuyer(b);

                Intent intent = new Intent(this,MainBuyerAct.class);
                //Bundle bundle = new Bundle();


                intent.putExtra(BuyerACticty,b);
                //start a normal activity
                       startActivity(intent);
                //start an activity for getting back a result

            }
            jArrUsers.put(jOUser);
            jsonObject.put("NoSeller", noSeller);
            jsonObject.put("NoBuyer", noBuyer);
            jsonObject.put("Users",jArrUsers);
            Log.d("obj", jsonObject.toString());

            // Convert JsonObject to String Format
            String userString = jsonObject.toString();
            // Define the File Path and its Name
            File file = new File(this.getFilesDir(),FILE_NAME);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userString);
            bufferedWriter.close();



        } catch (Exception e) {
            Log.d("error", "test");
        }

    }

    
}





