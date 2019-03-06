package com.parse.starter;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    ParseUser patient = new ParseUser();

    public void returnhome(View view) {
        Log.i("mes", "returnhome");
        Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
        startActivity(intent);
    }

    public void gosetting(View view) {
        Log.i("mes", "gosetting");
        Intent intent = new Intent(getApplicationContext(), DoctorSetting.class);
        startActivity(intent);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.share_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_patient) {

            Intent intent = new Intent(getApplicationContext(), DoctorAddPatient.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.logout) {

            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setTitle("您的病人");
        Button homeButton = (Button) findViewById(R.id.home);
        homeButton.setBackgroundResource(R.drawable.house);
        Button settingButton = (Button) findViewById(R.id.setting);
        settingButton.setBackgroundResource(R.drawable.settings);

        final ArrayList<String> usernames = new ArrayList<String>();
        final ListView userListView = (ListView) findViewById(R.id.userListView);
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, usernames);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), Doctor_PatientInfo.class);
                intent.putExtra("username", usernames.get(i));
                startActivity(intent);

            }
        });
        userListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int itemToDelete = i;
                new AlertDialog.Builder(UserListActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this patient?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        try {
                                            Log.i("Attention", usernames.get(itemToDelete));
                                            ParseQuery<ParseUser> query = ParseUser.getQuery();
                                            query.whereMatches("username", usernames.get(itemToDelete));
                                            query.whereMatches("DotocrOrPatient", "Patient");
                                            query.findInBackground(new FindCallback<ParseUser>() {
                                                public void done(List<ParseUser> userList, ParseException e) {
                                                    if (e == null && userList.size()>0) {
                                                        patient = userList.get(0);
                                                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
                                                        query.whereEqualTo("doctor", ParseUser.getCurrentUser());
                                                        query.whereEqualTo("patient", patient);
                                                        query.findInBackground(new FindCallback<ParseObject>(){
                                                            public void done(List<ParseObject> follows, ParseException e) {
                                                                if(e==null && follows.size()>0){
                                                                        follows.get(0).deleteInBackground(new DeleteCallback() {
                                                                            @Override
                                                                            public void done(ParseException e) {
                                                                                if (e == null) {
                                                                                   usernames.remove(itemToDelete);
                                                                                   arrayAdapter.notifyDataSetChanged();
                                                                                   Toast.makeText(getBaseContext(), "Deleted Successfully!", Toast.LENGTH_LONG).show();
                                                                                } else {
                                                                                    Toast.makeText(getBaseContext(), "Cant Delete!" + e.toString(), Toast.LENGTH_LONG).show();
                                                                                }
                                                                            }
                                                                        });
                                                                    }
                                                                else{
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        });
                                                    } else {
                                                        Log.d("Attention", "Error: " + e.getMessage());
                                                    }
                                                }
                                            });
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                    }
                                }
                        )
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }

        });



        ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
        query.include("patient");
        query.whereEqualTo("doctor", ParseUser.getCurrentUser());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() > 0) {

                        for (ParseObject follow : objects) {
                            ParseUser u=follow.getParseUser("patient");
                            usernames.add(u.getUsername());
                            arrayAdapter.notifyDataSetChanged();

                        }

                        userListView.setAdapter(arrayAdapter);
                    }
                    else{
                        usernames.add("无病人");
                        arrayAdapter.notifyDataSetChanged();
                    }

                } else {

                    e.printStackTrace();

                }

            }
        });






    }
}
