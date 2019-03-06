package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.HashSet;

public class PatientAdd2 extends AppCompatActivity {
    int ID;
    String s;

    public void finish(View view)
    {
        EditText editText = (EditText) findViewById(R.id.editText);


        try {

            if(editText.getText().toString().matches(""))
            {
                Toast.makeText(PatientAdd2.this, "记录不能为空", Toast.LENGTH_SHORT).show();
            }
            else {
                if(ID==2) {
                    ParseObject object = new ParseObject("Medicine");

                    object.put("record", editText.getText().toString());

                    object.put("createdby", ParseUser.getCurrentUser());

                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {

                                Toast.makeText(PatientAdd2.this, "成功添加了新的用药记录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), PatientMeasure.class);
                                intent.putExtra("measureId", 2);
                                startActivity(intent);

                            } else {

                                Toast.makeText(PatientAdd2.this, "未成功添加用药记录 - please try again later.", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }
                else if(ID==3) {
                    ParseObject object = new ParseObject("Diet");

                    object.put("record", editText.getText().toString());

                    object.put("createdby", ParseUser.getCurrentUser());

                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e == null) {

                                Toast.makeText(PatientAdd2.this, "成功添加了新的膳食记录", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), PatientMeasure.class);
                                intent.putExtra("measureId", 3);
                                startActivity(intent);

                            } else {

                                Toast.makeText(PatientAdd2.this, "未成功添加膳食记录 - please try again later.", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add2);

        Intent intent = getIntent();
        ID = intent.getIntExtra("measureId", -1);
        if(ID==2)
            setTitle("药物记录");
        else if(ID==3)
            setTitle("膳食记录");

    }
}
