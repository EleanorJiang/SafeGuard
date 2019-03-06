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
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.List;

public class PatientSettingDetail extends AppCompatActivity {
    TextView tip1 ;
    TextView tip2 ;
    TextView oldinfo ;
    EditText newinfo ;
    int infoID;
    ParseObject thisPatientInfo;

    public void finishit(View view) {
        newinfo = (EditText) findViewById(R.id.newtextviewp);
            if(newinfo.getText().toString().matches("")) {
                Toast.makeText(PatientSettingDetail.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(infoID==4){
                    ParseUser.getCurrentUser().put("gender",newinfo.getText().toString());
                    ParseUser.getCurrentUser().saveInBackground();
                    Toast.makeText(PatientSettingDetail.this, "性别更改成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), PatientSetting.class);
                    startActivity(intent);
                }
                else //更换
                    if(infoID==1){
                    ParseUser.getCurrentUser().setEmail(newinfo.getText().toString());
                    ParseUser.getCurrentUser() .saveInBackground();
                    Toast.makeText(PatientSettingDetail.this, "email更改成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), PatientSetting.class);
                    startActivity(intent);
                }
                else {
                        if (infoID==3) {
                            thisPatientInfo.put("birthday", newinfo.getText().toString());
                            thisPatientInfo.saveInBackground();
                            Toast.makeText(PatientSettingDetail.this, "生日更改成功", Toast.LENGTH_SHORT).show();
                        } else if (infoID==5) {
                            thisPatientInfo.put("bloodtype", newinfo.getText().toString());
                            thisPatientInfo.saveInBackground();
                            Toast.makeText(PatientSettingDetail.this, "血型更改成功", Toast.LENGTH_SHORT).show();
                        } else if (infoID==6) {
                            thisPatientInfo.put("disease", newinfo.getText().toString());
                            thisPatientInfo.saveInBackground();
                            Toast.makeText(PatientSettingDetail.this, "疾病信息更改成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                Intent intent = new Intent(getApplicationContext(), PatientSetting.class);
                startActivity(intent);
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_setting_detail);
        setTitle("设置");
        oldinfo = (TextView) findViewById(R.id.currenttextviewp);
        tip1 = (TextView) findViewById(R.id.tip1);
        tip2 = (TextView) findViewById(R.id.tip2);
        Intent intent=getIntent();
        infoID = intent.getIntExtra("patientinfoId",-1);
        if(infoID==4){
            tip1.setText("当前性别：");
            tip2.setText("新的性别：");
            String info=ParseUser.getCurrentUser().getString("gender");
            if(info==null){
                oldinfo.setText("unsetted");
            } else{
                oldinfo.setText(info);
            }
        }
        else if(infoID==1){
            tip1.setText("当前email：");
            tip2.setText("新的email：");
            String info=ParseUser.getCurrentUser().getEmail();
            if(info==null){
                oldinfo.setText("unsetted");
            } else{
                oldinfo.setText(info);
            }
        }
        else {
            ParseQuery<ParseObject> patientInfoQuery = ParseQuery.getQuery("PatientInfo");
            patientInfoQuery.whereEqualTo("patient", Patient.getCurrentUser());
            Log.i("Attention" ,Patient.getCurrentUser().getObjectId());
            patientInfoQuery.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> patientInfolist, ParseException e) {
                    if ( e != null || patientInfolist.size()<=0) {
                        Toast.makeText(PatientSettingDetail.this, "未找到user对应的PatientInfo", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.d("Attention", "找到病历");
                        thisPatientInfo=patientInfolist.get(0);
                        //更换
                        if (infoID ==3) {
                            tip1.setText("当前生日：");
                            tip2.setText("新的生日：");
                            String info = patientInfolist.get(0).getString("birthday");
                            if (info == null) {
                                oldinfo.setText("unsetted");
                            } else {
                                oldinfo.setText(info);
                            }
                        } else if (infoID ==5) {
                            tip1.setText("当前血型：");
                            tip2.setText("新的血型：");
                            String info = patientInfolist.get(0).getString("bloodtype");
                            if (info == null) {
                                oldinfo.setText("unsetted");
                            } else {
                                oldinfo.setText(info);
                            }
                        } else if (infoID==6) {
                            tip1.setText("当前疾病：");
                            tip2.setText("新的疾病：");
                            String info = patientInfolist.get(0).getString("disease");
                            if (info == null) {
                                oldinfo.setText("unsetted");
                            } else {
                                oldinfo.setText(info);
                            }
                        }
                    }
                }
            });
        }
    }
}
