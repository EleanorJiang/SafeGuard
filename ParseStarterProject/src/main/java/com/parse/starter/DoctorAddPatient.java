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

public class DoctorAddPatient extends AppCompatActivity {
    ParseUser patient = new ParseUser();

    public void add(View view) {
        EditText patientID = (EditText) findViewById(R.id.patientedittextview);

        try {

            if(patientID.getText().toString().matches(""))
            {
                Toast.makeText(DoctorAddPatient.this, "记录不能为空", Toast.LENGTH_SHORT).show();
            }
            else {
                ParseQuery<ParseUser> querypatient = ParseUser.getQuery();
                querypatient.whereMatches("objectId", patientID.getText().toString());
                querypatient.whereMatches("DotocrOrPatient", "Patient");
                querypatient.findInBackground(new FindCallback<ParseUser>() {
                    public void done(List<ParseUser> users, ParseException e) {
                        if (e == null && users.size()>0) {
                            Log.d("Attention", "找到了该ID的病人");
                            //更换follow里的医生
                             patient=users.get(0);
                            Log.d("Attention", patient.toString());
                            ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
                            query.whereEqualTo("doctor", ParseUser.getCurrentUser());
                            query.whereEqualTo("patient", patient);
                            query.findInBackground(new FindCallback<ParseObject>(){
                                public void done(List<ParseObject> follows, ParseException e) {
                                    if(e==null){
                                        if(follows.size()>0){
                                            Toast.makeText(DoctorAddPatient.this, "该病人已存在于您的病人中！", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Log.i("Attention","新建follow");
                                            ParseObject follow=new ParseObject("Follow");
                                            follow.put("doctor",ParseUser.getCurrentUser());
                                            follow.put("patient", patient);
                                            follow.saveInBackground();
                                            Toast.makeText(DoctorAddPatient.this, "成功添加新的病人", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                    else{
                                        e.printStackTrace();

                                    }
                                }
                            });


                        } else {
                            Toast.makeText(DoctorAddPatient.this, "未找到对应ID的病人，请输入正确的病人ID", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_doctor_add_patient);
        setTitle("Add new patient");
    }
}
