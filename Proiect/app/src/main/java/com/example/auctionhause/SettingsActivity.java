package com.example.auctionhause;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.HashSet;

public class SettingsActivity extends AppCompatActivity {
//    private Switch darkMode;
//    SharedPref sharedPref
    boolean checkBoxPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

//        getAccountSettingsPref();


    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    public void saveSettingsPref(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        String settingUsername=(sharedPreferences.getString("username", ""));
        Log.d("shared pref username", settingUsername);
        String settingPassword=(sharedPreferences.getString("password",""));
        Log.d("shared pref pass", settingPassword);

//        checkBoxPref= sharedPreferences.getBoolean("stayConnected", true);
    }



}