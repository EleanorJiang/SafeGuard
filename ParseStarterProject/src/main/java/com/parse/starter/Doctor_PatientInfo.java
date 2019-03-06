package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class Doctor_PatientInfo extends AppCompatActivity {
    String activeUsername;

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
        Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
        startActivity(intent);
    }


    public void gochat(View view) {
        Log.i("mes", "gochat");
        Intent intent = new Intent(getApplicationContext(), DoctorChat.class);
        intent.putExtra("username", activeUsername);
        startActivity(intent);
    }

    public void gosetting(View view) {
        Log.i("mes", "gosetting");
        Intent intent = new Intent(getApplicationContext(), DoctorSetting.class);
        startActivity(intent);
    }
    public void showrecord0(View view) {
        Intent intent = new Intent(getApplicationContext(),UserFeedActivity.class);
        intent.putExtra("measureId", 0);
        intent.putExtra("username", activeUsername);
        startActivity(intent);
    }

    public void showrecord1(View view) {
        Intent intent = new Intent(getApplicationContext(),UserFeedActivity.class);
        intent.putExtra("measureId", 1);
        intent.putExtra("username", activeUsername);
        startActivity(intent);
    }

    public void showrecord2(View view) {
        Intent intent = new Intent(getApplicationContext(),UserFeedActivity.class);
        intent.putExtra("measureId", 2);
        intent.putExtra("username", activeUsername);
        startActivity(intent);
    }

    public void showrecord3(View view) {
        Intent intent = new Intent(getApplicationContext(),UserFeedActivity.class);
        intent.putExtra("measureId", 3);
        intent.putExtra("username", activeUsername);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__patient_info);
        Intent intent = getIntent();
        activeUsername = intent.getStringExtra("username");

        setTitle(activeUsername + "'s Feed");
        Button homeButton = (Button) findViewById(R.id.home);
        homeButton.setBackgroundResource(R.drawable.house);
        Button settingButton = (Button) findViewById(R.id.setting);
        settingButton.setBackgroundResource(R.drawable.settings);
        final TextView gendertext= (TextView) findViewById(R.id.gendertext);
        final TextView birthdaytext= (TextView) findViewById(R.id.birthdaytext);
        final TextView bloodtyptext= (TextView) findViewById(R.id.bloodtypetext);
        final TextView diseasetext= (TextView) findViewById(R.id.diseasetext);
        Log.i("Attention",activeUsername);
        ParseQuery<ParseUser> innerQuery = ParseUser.getQuery();
        innerQuery.whereMatches("username",activeUsername);
        innerQuery.include("gender");
        innerQuery.findInBackground(new FindCallback<ParseUser>(){
            public void done(List<ParseUser> patientList, ParseException e) {
                if(e==null && patientList.size()>0 && (patientList.get(0).get("gender")!=null)){
                    gendertext.setText(patientList.get(0).getString("gender"));
                    Log.i("Attention","gendersuccess");
                }else{
                    Log.i("Attention","genderERROR");
                    gendertext.setText("暂无");
                }
            }
        });
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PatientInfo");
        query.whereMatchesQuery("patient", innerQuery);
        query.include("birthday");
        query.include("bloodtype");
        query.include("disease");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> patientInfoList, ParseException e) {
                if(e==null && patientInfoList.size()>0){
                    //显示信息
                    if((patientInfoList.get(0).getString("birthday")!=null) && !patientInfoList.get(0).getString("birthday").matches(""))
                    birthdaytext.setText(patientInfoList.get(0).getString("birthday"));
                    else  birthdaytext.setText("暂无");
                    if((patientInfoList.get(0).getString("bloodtype")!=null) && !patientInfoList.get(0).getString("bloodtype").matches(""))
                    bloodtyptext.setText(patientInfoList.get(0).getString("bloodtype"));
                    else bloodtyptext.setText("暂无");
                    if((patientInfoList.get(0).getString("disease")!=null) && !patientInfoList.get(0).getString("disease").matches(""))
                    diseasetext.setText(patientInfoList.get(0).getString("disease"));
                    else diseasetext.setText("暂无");

                }else{
                    Log.i("Attention","ERRORbbd");
                    birthdaytext.setText("暂无");
                    bloodtyptext.setText("暂无");
                    diseasetext.setText("暂无");
                }
            }
        });

    }
}
