package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class Changedoctor extends AppCompatActivity {

    int size=-1;
    ParseObject follow;

    public void finished(View view) {
        final EditText textView2 = (EditText) findViewById(R.id.name4);
        if (textView2.getText().toString().matches("")) {
            Toast.makeText(Changedoctor.this, "新医生姓名不能为空", Toast.LENGTH_SHORT).show();
        } else {
            Log.i("Size", Integer.toString(size));
            if (size == 1) {
                //更换follow里的医生
                ParseQuery<ParseUser> query2 = Doctor.getQuery();
                query2.whereEqualTo("objectId", textView2.getText().toString());
                query2.whereEqualTo("DotocrOrPatient", "Doctor");
                query2.findInBackground(new FindCallback<ParseUser>() {
                    public void done(List<ParseUser> users, ParseException e) {
                        if (e == null && users.size()>0) {
                            Log.d("Attention", "change doctor");
                            //更换follow里的医生
                            follow.put("doctor", users.get(0));
                            follow.saveInBackground();
                            Intent intent = new Intent(getApplicationContext(), PatientSetting.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Changedoctor.this, "未找到对应ID的医生，请输入正确的医生ID", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else if (size == 0) {
                ParseQuery<ParseUser> query2 = Doctor.getQuery();
                query2.whereEqualTo("objectId", textView2.getText().toString());
                query2.whereEqualTo("DotocrOrPatient", "Doctor");
                query2.findInBackground(new FindCallback<ParseUser>() {
                    public void done(List<ParseUser> users, ParseException e) {
                        if (e == null) {
                            Log.d("Attention", "新建了一个follow");
                            //更换follow里的医生
                            ParseObject follow = new ParseObject("Follow");
                            follow.put("patient", ParseUser.getCurrentUser());
                            follow.put("doctor", users.get(0));
                            follow.saveInBackground();
                            Intent intent = new Intent(getApplicationContext(), PatientSetting.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Changedoctor.this, "未找到对应ID的医生，请输入正确的医生ID", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changedoctor);
        final TextView textView = (TextView) findViewById(R.id.name2);

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Follow");
        query1.whereEqualTo("patient", ParseUser.getCurrentUser());
        query1.getFirstInBackground(new GetCallback<ParseObject>() {
          public void done(ParseObject follow1, ParseException e) {
            if (e==null && follow1 !=null) {
                    size = 1;
                    Log.i("Attention", "显示了当前医生");
                    Log.i("Attention", follow1.getParseUser("doctor").getObjectId());
                    ParseQuery<ParseUser> querydc = ParseUser.getQuery();
                    querydc.whereEqualTo("objectId", follow1.getParseUser("doctor").getObjectId());
                    querydc.getFirstInBackground(new GetCallback<ParseUser>() {
                        public void done(ParseUser doctor, ParseException e) {
                            if (e == null) {
                                textView.setText(doctor.getUsername() + "  (" + doctor.getObjectId() + ")");
                            } else {
                                // Something went wrong.
                                Log.i("Attention", "ERROR");
                            }
                        }
                    });
                    follow = follow1;

             }
            else{
                    size = 0;
                    textView.setText("无医生");
                    e.printStackTrace();
                    Log.i("Attention","ERORR");
                }
            }
        });




    }
}
