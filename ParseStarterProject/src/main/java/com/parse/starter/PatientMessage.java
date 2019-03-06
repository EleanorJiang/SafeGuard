package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class PatientMessage extends AppCompatActivity {
    String mydoctorname;

    public void finish(View view)
    {
        final EditText editText = (EditText) findViewById(R.id.editText);
        try {

            if(editText.getText().toString().matches(""))
            {
                Toast.makeText(PatientMessage.this, "消息不能为空", Toast.LENGTH_SHORT).show();
            }
            else {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
                query.whereEqualTo("patient", ParseUser.getCurrentUser());
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> followList, ParseException e) {
                        if(e==null && followList.size()>0){
                            ParseObject object = new ParseObject("Communicate");
                            object.put("belongto", followList.get(0));
                            object.put("message",editText.getText().toString());
                            object.put("PtoD",true);
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Toast.makeText(PatientMessage.this, "成功发送消息", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),PatientChat.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(PatientMessage.this, "未成功发送消息 - please try again later.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Log.i("Attention","ERROR");
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_message);
        setTitle("Send message to doctor");
        Intent intent = getIntent();
        mydoctorname = intent.getStringExtra("doctorname");
        Log.i("Atten",mydoctorname);
    }
}
