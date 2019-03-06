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
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class DoctorSettingDetail extends AppCompatActivity {
    TextView tip1 ;
    TextView tip2 ;
    TextView oldinfo ;
    EditText newinfo ;
    int infoID;
    ParseObject thisPatientInfo;

    public void finishd(View view) {
        newinfo = (EditText) findViewById(R.id.newtextview);
        if(newinfo.getText().toString().matches("")) {
            Toast.makeText(DoctorSettingDetail.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(infoID==3){
                ParseUser.getCurrentUser().put("gender",newinfo.getText().toString());
                ParseUser.getCurrentUser().saveInBackground();
                Toast.makeText(DoctorSettingDetail.this, "性别更改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), DoctorSetting.class);
                startActivity(intent);
            }
            else if(infoID==1){
                    ParseUser.getCurrentUser().setEmail(newinfo.getText().toString());
                    ParseUser.getCurrentUser() .saveInBackground();
                    Toast.makeText(DoctorSettingDetail.this, "email更改成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DoctorSetting.class);
                    startActivity(intent);
                }
            else {
                    if (infoID==4) {
                        thisPatientInfo.put("department", newinfo.getText().toString());
                        thisPatientInfo.saveInBackground();
                        Toast.makeText(DoctorSettingDetail.this, "科室更改成功", Toast.LENGTH_SHORT).show();
                    } else if (infoID==5) {
                        thisPatientInfo.put("hospital", newinfo.getText().toString());
                        thisPatientInfo.saveInBackground();
                        Toast.makeText(DoctorSettingDetail.this, "医院信息更改成功", Toast.LENGTH_SHORT).show();
                    } else if (infoID==6) {
                        thisPatientInfo.put("title", newinfo.getText().toString());
                        thisPatientInfo.saveInBackground();
                        Toast.makeText(DoctorSettingDetail.this, "职称信息更改成功", Toast.LENGTH_SHORT).show();
                    }else if (infoID==7) {
                        thisPatientInfo.put("major", newinfo.getText().toString());
                        thisPatientInfo.saveInBackground();
                        Toast.makeText(DoctorSettingDetail.this, "专攻领域更改成功", Toast.LENGTH_SHORT).show();
                    }
                }
            Intent intent = new Intent(getApplicationContext(), DoctorSetting.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_setting_detail);
        setTitle("设置");
        oldinfo = (TextView) findViewById(R.id.currenttextview);
        tip1 = (TextView) findViewById(R.id.tip1);
        tip2 = (TextView) findViewById(R.id.tip2);
        Intent intent=getIntent();
        infoID = intent.getIntExtra("infoId",-1);
        if(infoID==3){
            tip1.setText("当前性别：");
            tip2.setText("新的性别：");
            String info= ParseUser.getCurrentUser().getString("gender");
            if(info==null){
                oldinfo.setText("unsetted");
            } else{
                oldinfo.setText(info);
            }
        }else if(infoID==1){
            tip1.setText("当前性别：");
            tip2.setText("新的性别：");
            String info=ParseUser.getCurrentUser().getEmail();
            if(info==null){
                oldinfo.setText("unsetted");
            } else{
                oldinfo.setText(info);
            }
        }
        else {
            ParseQuery<ParseObject> doctorInfoQuery = ParseQuery.getQuery("DoctorInfo");
            doctorInfoQuery.whereEqualTo("doctor", ParseUser.getCurrentUser());
            Log.i("Attention" ,Patient.getCurrentUser().getObjectId());
            doctorInfoQuery.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> patientInfolist, ParseException e) {
                    if ( e != null) {
                        Toast.makeText(DoctorSettingDetail.this, "未找到user对应的DoctorInfo", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.d("Attention", "找到DoctorInfo");
                        thisPatientInfo=patientInfolist.get(0);
                        //更换
                        if (infoID==4) {
                            tip1.setText("当前科室：");
                            tip2.setText("新的科室：");
                            String info = patientInfolist.get(0).getString("department");
                            if (info == null) {
                                oldinfo.setText("unsetted");
                            } else {
                                oldinfo.setText(info);
                            }
                        } else if (infoID ==5) {
                            tip1.setText("当前医院：");
                            tip2.setText("新的医院：");
                            String info = patientInfolist.get(0).getString("hospital" );
                            if (info == null) {
                                oldinfo.setText("unsetted");
                            } else {
                                oldinfo.setText(info);
                            }
                        } else if (infoID ==6) {
                            tip1.setText("当前职称：");
                            tip2.setText("新的职称：");
                            String info = patientInfolist.get(0).getString("title");
                            if (info == null) {
                                oldinfo.setText("unsetted");
                            } else {
                                oldinfo.setText(info);
                            }
                        }else if (infoID ==7) {
                            tip1.setText("当前专攻领域：");
                            tip2.setText("新的专攻领域：");
                            String info = patientInfolist.get(0).getString("major");
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
