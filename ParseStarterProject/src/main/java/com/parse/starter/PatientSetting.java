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
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PatientSetting extends AppCompatActivity {

    static ArrayList<String> settings = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_setting);

        setTitle("设置");
        Button homeButton = (Button) findViewById(R.id.home);
        homeButton.setBackgroundResource(R.drawable.house);
        Button chatButton = (Button) findViewById(R.id.chat);
        chatButton.setBackgroundResource(R.drawable.chat);
        Button settingButton = (Button) findViewById(R.id.setting);
        settingButton.setBackgroundResource(R.drawable.settings);

        TextView usernameview= (TextView) findViewById(R.id.username2);
        usernameview.setText(ParseUser.getCurrentUser().getUsername().toString()+" (ID:"+ParseUser.getCurrentUser().getObjectId().toString()+")");

        ListView listView = (ListView) findViewById(R.id.shezhiliebiao);


        if (settings.size() == 0) {

            settings.add("change password");
            settings.add("change email");
            settings.add("");
            settings.add("change birthday information");
            settings.add("change gender information");
            settings.add("change bloodtype information");
            settings.add("change disease information");
            settings.add("");
            settings.add("change doctor");

        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, settings);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               if(i==0){
                   Intent intent = new Intent(getApplicationContext(), Settingpassword.class);
                   startActivity(intent);
               }
               else if(i==8){
                   Intent intent = new Intent(getApplicationContext(), Changedoctor.class);
                   startActivity(intent);
               }
               else if(i!=2 && i!=7){
                   Intent intent = new Intent(getApplicationContext(), PatientSettingDetail.class);
                   intent.putExtra("patientinfoId", i);
                   startActivity(intent);
               }
            }

        });

    }
}
