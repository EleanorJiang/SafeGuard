package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class PatientAdd1 extends AppCompatActivity {

    public void finish(View view) {
        EditText shousuoya = (EditText) findViewById(R.id.shousuoya2);


        try {

            if(shousuoya.getText().toString().matches(""))
            {
                Toast.makeText(PatientAdd1.this, "记录不能为空", Toast.LENGTH_SHORT).show();
            }
            else {
                ParseObject object = new ParseObject("HeartRate");

                object.put("xinlv", shousuoya.getText().toString());

                object.put("createdby", ParseUser.getCurrentUser());

                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null) {

                            Toast.makeText(PatientAdd1.this, "成功添加了新的心率记录", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), PatientMeasure.class);
                            intent.putExtra("measureId", 1);
                            startActivity(intent);

                        } else {

                            Toast.makeText(PatientAdd1.this, "未成功添加心率记录 - please try again later.", Toast.LENGTH_SHORT).show();

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
        setContentView(R.layout.activity_patient_add1);
        setTitle("心率记录");
    }
}
