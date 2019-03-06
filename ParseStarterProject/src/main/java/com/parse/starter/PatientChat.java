package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PatientChat extends AppCompatActivity {
    ParseObject follow;
    String mydoctorname;
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addxiaoxi, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_xiaoxi) {

            Intent intent = new Intent(getApplicationContext(), PatientMessage.class);
            intent.putExtra("doctorname",mydoctorname);
            startActivity(intent);

        }
        else  if (item.getItemId() == R.id.logout) {

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
        setContentView(R.layout.activity_patient_chat);
        setTitle("消息列表");
        Button homeButton = (Button) findViewById(R.id.home);
        homeButton.setBackgroundResource(R.drawable.house);
        Button chatButton = (Button) findViewById(R.id.chat);
        chatButton.setBackgroundResource(R.drawable.chat);
        Button settingButton = (Button) findViewById(R.id.setting);
        settingButton.setBackgroundResource(R.drawable.settings);

        final ArrayList<String> recordsdisplay= new ArrayList<String>();
        final ListView listView = (ListView) findViewById(R.id.listmessage);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recordsdisplay);
        final TextView textView = (TextView) findViewById(R.id.dortorname);
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Follow");
        query1.whereEqualTo("patient", ParseUser.getCurrentUser());
        query1.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject follow1, ParseException e) {
                if (e==null && follow1 !=null) {
                    Log.i("Attention", "显示了当前医生");
                    Log.i("Attention", follow1.getParseUser("doctor").getObjectId());
                    ParseQuery<ParseUser> querydc = ParseUser.getQuery();
                    querydc.whereEqualTo("objectId", follow1.getParseUser("doctor").getObjectId());
                    querydc.include("username");
                    querydc.getFirstInBackground(new GetCallback<ParseUser>() {
                        public void done(ParseUser doctor, ParseException e) {
                            if (e == null) {
                                mydoctorname=doctor.getUsername();
                                textView.setText(mydoctorname + "  (" + doctor.getObjectId() + ")");
                            } else {
                                // Something went wrong.
                                Log.i("Attention", "ERROR");
                            }
                        }
                    });
                    follow=follow1;
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Communicate");
                    query.whereEqualTo("belongto", follow);
                    query.addDescendingOrder("createdAt");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                if (objects.size() > 0) {
                                    for (ParseObject message : objects) {
                                        Log.i("Attention",message.getString("message"));
                                        if(message.getBoolean("PtoD")==false)
                                            recordsdisplay.add(mydoctorname+": "+message.getString("message")+"\n日期:"+ message.getCreatedAt().toString());
                                        else if(message.getBoolean("PtoD")==true)
                                            recordsdisplay.add("我: "+message.getString("message")+"\n日期:"+ message.getCreatedAt().toString());
                                    }
                                    listView.setAdapter(arrayAdapter);
                                }
                            } else {
                                e.printStackTrace();
                                Log.i("Attention","ErrorAgainJesus");
                            }
                        }
                    });
                }
                else{
                    textView.setText("无医生");
                    e.printStackTrace();
                    Log.i("Attention","ERORR");
                }
            }
        });
    }
}
