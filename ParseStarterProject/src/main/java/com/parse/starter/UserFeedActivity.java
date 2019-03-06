package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UserFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);


        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        Intent intent = getIntent();
        String activeUsername = intent.getStringExtra("username");
        int measureID = intent.getIntExtra("measureId",-1);

        ParseQuery<ParseUser> innerQuery = ParseUser.getQuery();
        innerQuery.whereMatches("username",activeUsername);
        if(measureID ==0){
            setTitle(activeUsername + "'s blood pressure");
            ParseQuery<ParseObject> query = ParseQuery.getQuery("BloodPressure");
            query.whereMatchesQuery("createdby", innerQuery);
            query.addDescendingOrder("createdAt");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (e == null) {

                        if (objects.size() > 0) {

                            for (ParseObject pressure : objects) {
                                TextView textView = new  TextView(UserFeedActivity.this);

                                textView.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                ));
                                textView.setText("血压："+pressure.getString("shousuoya")+"/"+pressure.getString("shuzhangya")+"\n记录日期:"+ pressure.getCreatedAt().toString());
                                textView.setTextSize(20);
                                Log.i("Attention",textView.getText().toString());
                                linearLayout.addView(textView);
                            }
                        }

                    } else {
                        e.printStackTrace();
                    }

                }
            });
        }
        else if(measureID ==1){
            setTitle(activeUsername + "'s heart rate");
            ParseQuery<ParseObject> query = ParseQuery.getQuery("HeartRate");
            query.whereMatchesQuery("createdby", innerQuery);
            query.addDescendingOrder("createdAt");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (e == null) {

                        if (objects.size() > 0) {

                            for (ParseObject pressure : objects) {
                                TextView textView = new  TextView(UserFeedActivity.this);

                                textView.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                ));
                                textView.setText("心率："+pressure.getString("xinlv")+"次/min\n记录日期:"+ pressure.getCreatedAt().toString());
                                textView.setTextSize(20);
                                Log.i("Attention",textView.getText().toString());
                                linearLayout.addView(textView);
                            }
                        }

                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
        else if(measureID ==2){
            setTitle(activeUsername + "'s medicine record");
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Medicine");
            query.whereMatchesQuery("createdby", innerQuery);
            query.addDescendingOrder("createdAt");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (e == null) {

                        if (objects.size() > 0) {

                            for (ParseObject pressure : objects) {
                                TextView textView = new  TextView(UserFeedActivity.this);

                                textView.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                ));
                                textView.setText(pressure.getString("record")+"\n记录日期:"+ pressure.getCreatedAt().toString());
                                textView.setTextSize(20);
                                Log.i("Attention",textView.getText().toString());
                                linearLayout.addView(textView);
                            }
                        }

                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
        else if(measureID ==3){
            setTitle(activeUsername + "'s diet record");
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Diet");
            query.whereMatchesQuery("createdby", innerQuery);
            query.addDescendingOrder("createdAt");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (e == null) {

                        if (objects.size() > 0) {

                            for (ParseObject pressure : objects) {
                                TextView textView = new  TextView(UserFeedActivity.this);

                                textView.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                ));
                                textView.setText(pressure.getString("record")+"\n记录日期:"+ pressure.getCreatedAt().toString());
                                textView.setTextSize(20);
                                Log.i("Attention",textView.getText().toString());
                                linearLayout.addView(textView);
                            }
                        }

                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
