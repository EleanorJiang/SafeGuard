package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class DoctorChat extends AppCompatActivity {
    String activeUsername;
    ParseObject follow;

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.doctorchatmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.sendmessage){
            Intent intent = new Intent(getApplicationContext(), sendNewMassege.class);
            intent.putExtra("username",activeUsername);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.home){
            Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.info){
            Intent intent = new Intent(getApplicationContext(), Doctor_PatientInfo.class);
            intent.putExtra("username",activeUsername);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_chat);
        Intent intent = getIntent();
        activeUsername = intent.getStringExtra("username");
        setTitle(activeUsername+"'s 消息列表");
        final ArrayList<String> recordsdisplay= new ArrayList<String>();
        final ListView listView = (ListView) findViewById(R.id.listViewmeasure);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recordsdisplay);

        ParseQuery<ParseUser> iinnerQuery = ParseUser.getQuery();
        iinnerQuery.whereMatches("DotocrOrPatient", "Patient");
        iinnerQuery.whereMatches("username", activeUsername);
        ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Follow");
        innerQuery.whereEqualTo("doctor", ParseUser.getCurrentUser());
        innerQuery.whereMatchesQuery("patient",iinnerQuery);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Communicate");
        query.whereMatchesQuery("belongto", innerQuery);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject message : objects) {
                            Log.i("Attention",message.getString("message"));
                           if(message.getBoolean("PtoD")==false)
                               recordsdisplay.add("我: "+message.getString("message")+"\n日期:"+ message.getCreatedAt().toString());
                           else if(message.getBoolean("PtoD")==true)
                               recordsdisplay.add(activeUsername+": "+message.getString("message")+"\n日期:"+ message.getCreatedAt().toString());
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
}
