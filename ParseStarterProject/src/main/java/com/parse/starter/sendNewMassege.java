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

public class sendNewMassege extends AppCompatActivity {
    String activeUsername;
    public void finish(View view)
    {
        final EditText editText = (EditText) findViewById(R.id.editText);
        try {

            if(editText.getText().toString().matches(""))
            {
                Toast.makeText(sendNewMassege.this, "消息不能为空", Toast.LENGTH_SHORT).show();
            }
            else {
                ParseQuery<ParseUser> innerQuery = ParseUser.getQuery();
                innerQuery.whereMatches("DotocrOrPatient", "Patient");
                innerQuery.whereMatches("username", activeUsername);
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
                query.whereEqualTo("doctor", ParseUser.getCurrentUser());
                query.whereMatchesQuery("patient", innerQuery);
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> followList, ParseException e) {
                        if(e==null && followList.size()>0){
                            ParseObject object = new ParseObject("Communicate");
                            object.put("belongto", followList.get(0));
                            object.put("message",editText.getText().toString());
                            object.put("PtoD",false);
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Toast.makeText(sendNewMassege.this, "成功发送消息", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),DoctorChat.class);
                                        intent.putExtra("username",activeUsername);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(sendNewMassege.this, "未成功发送消息 - please try again later.", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_send_new_massege);
        Intent intent = getIntent();
        activeUsername = intent.getStringExtra("username");
        setTitle("Send to "+activeUsername);
    }
}
