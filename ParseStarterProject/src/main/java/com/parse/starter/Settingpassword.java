package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class Settingpassword extends AppCompatActivity {
    EditText mima ;
    EditText miman ;

    public void finishpw(View view) {
        mima = (EditText) findViewById(R.id.mima2);
        miman = (EditText) findViewById(R.id.mima4);
        try {

            if (mima.getText().toString().matches("") || miman.getText().toString().matches("")) {
                Toast.makeText(Settingpassword.this, "密码框不能为空", Toast.LENGTH_SHORT).show();
            } else {
                String s = ParseUser.getCurrentUser().getUsername();
                ParseUser.logInInBackground(s, mima.getText().toString(), new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            // Hooray! The user is logged in.
                            user.setPassword(miman.getText().toString());
                            user.saveInBackground();
                            Toast.makeText(Settingpassword.this, "更改密码成功", Toast.LENGTH_SHORT).show();
                            if(ParseUser.getCurrentUser().getString("DotocrOrPatient").matches("Patient")) {
                                Intent intent = new Intent(getApplicationContext(), PatientSetting.class);
                                startActivity(intent);
                            }else if(ParseUser.getCurrentUser().getString("DotocrOrPatient").matches("Doctor")){
                                Intent intent = new Intent(getApplicationContext(), DoctorSetting.class);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(Settingpassword.this, "您输入的密码有误！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            } catch(Exception e){
                e.printStackTrace();
            }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingpassword);
        setTitle("更改密码");
    }
}
