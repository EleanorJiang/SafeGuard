package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PatientHome extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.logout, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout) {

            ParseUser.logOut();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    public void addnewblood(View view) {
        Log.i("mes", "addnewblood");
        Intent intent = new Intent(getApplicationContext(), PatientMeasure.class);
        intent.putExtra("measureId", 0);
        startActivity(intent);
    }

    public void addnewheart(View view) {
        Log.i("mes", "addnewheart");
        Intent intent = new Intent(getApplicationContext(), PatientAdd.class);
        intent.putExtra("measureId", 1);
        startActivity(intent);
    }

    public void returnhome(View view) {
        Log.i("mes", "returnhome");
        Intent intent = new Intent(getApplicationContext(),PatientHome.class);
        startActivity(intent);
    }


    public void gochat(View view) {
        Log.i("mes", "gochat");
        Intent intent = new Intent(getApplicationContext(), PatientChat.class);
        startActivity(intent);
    }

    public void gosetting(View view) {
        Log.i("mes", "gosetting");
        Intent intent = new Intent(getApplicationContext(), PatientSetting.class);
        startActivity(intent);
    }

    public void showrecord0(View view) {
        Intent intent = new Intent(getApplicationContext(),PatientMeasure.class);
        intent.putExtra("measureId", 0);
        startActivity(intent);
    }

    public void showrecord1(View view) {
        Intent intent = new Intent(getApplicationContext(),PatientMeasure.class);
        intent.putExtra("measureId", 1);
        startActivity(intent);
    }

    public void showrecord2(View view) {
        Intent intent = new Intent(getApplicationContext(),PatientMeasure.class);
        intent.putExtra("measureId", 2);
        startActivity(intent);
    }

    public void showrecord3(View view) {
        Intent intent = new Intent(getApplicationContext(),PatientMeasure.class);
        intent.putExtra("measureId", 3);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        setTitle("主页");
        Button homeButton = (Button) findViewById(R.id.home);
        homeButton.setBackgroundResource(R.drawable.house);
        Button chatButton = (Button) findViewById(R.id.chat);
        chatButton.setBackgroundResource(R.drawable.chat);
        Button settingButton = (Button) findViewById(R.id.setting);
        settingButton.setBackgroundResource(R.drawable.settings);

}

}
