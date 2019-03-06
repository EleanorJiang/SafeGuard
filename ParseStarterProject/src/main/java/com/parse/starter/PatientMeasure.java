package com.parse.starter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class PatientMeasure extends AppCompatActivity {
    int measureID;


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_record, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.add_record) {

            if(measureID == 0){
                Intent intent = new Intent(getApplicationContext(), PatientAdd.class);

                startActivity(intent);
            }
            else if(measureID == 1){
                Intent intent = new Intent(getApplicationContext(), PatientAdd1.class);

                startActivity(intent);
            }
            else if(measureID == 2){
                Intent intent = new Intent(getApplicationContext(), PatientAdd2.class);
                intent.putExtra("measureId", 2);
                startActivity(intent);
            }
            else if(measureID == 3){
                Intent intent = new Intent(getApplicationContext(), PatientAdd2.class);
                intent.putExtra("measureId", 3);
                startActivity(intent);
            }


            return true;

        }
        else if (item.getItemId() == R.id.return_home) {
            Intent intent = new Intent(getApplicationContext(), PatientHome.class);

            startActivity(intent);
            return true;

        }

        return false;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_measure);
        final  ArrayList<String> recordsdisplay= new ArrayList<String>();
        final ListView listView = (ListView) findViewById(R.id.listViewmeasure);
        Intent intent = getIntent();
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recordsdisplay);

        measureID = intent.getIntExtra("measureId", -1);

       if(measureID == 0){
           setTitle("您的血压记录");
           final ArrayList<String> ids= new ArrayList<String>();
           ParseQuery<ParseObject> query1 = ParseQuery.getQuery("BloodPressure");
           query1.whereEqualTo("createdby", ParseUser.getCurrentUser());
           query1.addDescendingOrder("createdAt");
           query1.findInBackground(new FindCallback<ParseObject>() {
               @Override
               public void done(List<ParseObject> objects, ParseException e) {

                   if (e == null) {

                       if (objects.size() > 0) {

                           for (ParseObject pressure : objects) {

                               recordsdisplay.add("血压："+pressure.getString("shousuoya")+"/"+pressure.getString("shuzhangya")+"\n记录日期:"+ pressure.getCreatedAt().toString());
                               ids.add(pressure.getObjectId());

                           }

                           listView.setAdapter(arrayAdapter);
                       }

                   } else {
                       e.printStackTrace();
                   }

               }
           });
           listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

               @Override
               public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                   final int itemToDelete = i;
                   new AlertDialog.Builder(PatientMeasure.this)
                           .setIcon(android.R.drawable.ic_dialog_alert)
                           .setTitle("Are you sure?")
                           .setMessage("Do you want to delete this record?")
                           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialogInterface, int i) {
                                           Log.i("Attention",ids.get(itemToDelete));
                                           try {

                                               ParseQuery<ParseObject> query = ParseQuery.getQuery("BloodPressure");
                                               query.whereEqualTo("objectId", ids.get(itemToDelete));
                                               query.findInBackground(new FindCallback<ParseObject>() {
                                                   public void done(List<ParseObject> objectList, ParseException e) {
                                                       if (e == null) {
                                                           objectList.get(0).deleteInBackground(new DeleteCallback() {
                                                               @Override
                                                               public void done(ParseException e) {
                                                                   if (e == null) {
                                                                       recordsdisplay.remove(itemToDelete);
                                                                       arrayAdapter.notifyDataSetChanged();
                                                                       Toast.makeText(getBaseContext(), "Deleted Successfully!", Toast.LENGTH_LONG).show();
                                                                   } else {
                                                                       Toast.makeText(getBaseContext(), "Cant Delete!" + e.toString(), Toast.LENGTH_LONG).show();
                                                                   }
                                                               }
                                                           });
                                                       } else {
                                                           Log.d("Attention", "Error: " + e.getMessage());
                                                       }
                                                   }
                                               });
                                           }catch (Exception e){
                                               e.printStackTrace();
                                           }

                                       }
                                   }
                           )
                           .setNegativeButton("No", null)
                           .show();
                   return true;
               }

           });


       }
       else if(measureID == 1){
           setTitle("您的心率记录");
           ParseQuery<ParseObject> query1 = ParseQuery.getQuery("HeartRate");
           final ArrayList<String> ids= new ArrayList<String>();
           query1.whereEqualTo("createdby", ParseUser.getCurrentUser());
           query1.addDescendingOrder("createdAt");
           query1.findInBackground(new FindCallback<ParseObject>() {
               @Override
               public void done(List<ParseObject> objects, ParseException e) {

                   if (e == null) {

                       if (objects.size() > 0) {
                           Log.i("objects.size","retrived "+objects.size());

                           for (ParseObject pressure : objects) {

                               recordsdisplay.add("心率："+pressure.getString("xinlv")+"次/min"+"\n记录日期:"+ pressure.getCreatedAt().toString());
                               ids.add(pressure.getObjectId());
                           }

                           listView.setAdapter(arrayAdapter);
                       }

                   } else {

                       e.printStackTrace();

                   }

               }
           });
           listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

               @Override
               public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                   final int itemToDelete = i;
                   new AlertDialog.Builder(PatientMeasure.this)
                           .setIcon(android.R.drawable.ic_dialog_alert)
                           .setTitle("Are you sure?")
                           .setMessage("Do you want to delete this record?")
                           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialogInterface, int i) {
                                           Log.i("Attention",ids.get(itemToDelete));
                                           try {

                                               ParseQuery<ParseObject> query = ParseQuery.getQuery("HeartRate");
                                               query.whereEqualTo("objectId", ids.get(itemToDelete));
                                               query.findInBackground(new FindCallback<ParseObject>() {
                                                   public void done(List<ParseObject> objectList, ParseException e) {
                                                       if (e == null) {
                                                           objectList.get(0).deleteInBackground(new DeleteCallback() {
                                                               @Override
                                                               public void done(ParseException e) {
                                                                   if (e == null) {
                                                                       recordsdisplay.remove(itemToDelete);
                                                                       arrayAdapter.notifyDataSetChanged();
                                                                       Toast.makeText(getBaseContext(), "Deleted Successfully!", Toast.LENGTH_LONG).show();
                                                                   } else {
                                                                       Toast.makeText(getBaseContext(), "Cant Delete!" + e.toString(), Toast.LENGTH_LONG).show();
                                                                   }
                                                               }
                                                           });
                                                       } else {
                                                           Log.d("Attention", "Error: " + e.getMessage());
                                                       }
                                                   }
                                               });
                                           }catch (Exception e){
                                               e.printStackTrace();
                                           }

                                       }
                                   }
                           )
                           .setNegativeButton("No", null)
                           .show();
                   return true;
               }

           });
       }
       else if(measureID == 2){
           setTitle("您的用药记录");
           final ArrayList<String> ids= new ArrayList<String>();
           ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Medicine");
           query1.whereEqualTo("createdby", ParseUser.getCurrentUser());
           query1.addDescendingOrder("createdAt");
           query1.findInBackground(new FindCallback<ParseObject>() {
               @Override
               public void done(List<ParseObject> objects, ParseException e) {

                   if (e == null) {

                       if (objects.size() > 0) {
                           Log.i("objects.size","retrived "+objects.size());

                           for (ParseObject pressure : objects) {

                               recordsdisplay.add("药品："+pressure.getString("record")+"\n记录日期:"+ pressure.getCreatedAt().toString());
                               ids.add(pressure.getObjectId());
                           }

                           listView.setAdapter(arrayAdapter);
                       }

                   } else {

                       e.printStackTrace();

                   }

               }
           });
           listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

               @Override
               public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                   final int itemToDelete = i;
                   new AlertDialog.Builder(PatientMeasure.this)
                           .setIcon(android.R.drawable.ic_dialog_alert)
                           .setTitle("Are you sure?")
                           .setMessage("Do you want to delete this record?")
                           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialogInterface, int i) {
                                           Log.i("Attention",ids.get(itemToDelete));
                                           try {

                                               ParseQuery<ParseObject> query = ParseQuery.getQuery("Medicine");
                                               query.whereEqualTo("objectId", ids.get(itemToDelete));
                                               query.findInBackground(new FindCallback<ParseObject>() {
                                                   public void done(List<ParseObject> objectList, ParseException e) {
                                                       if (e == null) {
                                                           objectList.get(0).deleteInBackground(new DeleteCallback() {
                                                               @Override
                                                               public void done(ParseException e) {
                                                                   if (e == null) {
                                                                       recordsdisplay.remove(itemToDelete);
                                                                       arrayAdapter.notifyDataSetChanged();
                                                                       Toast.makeText(getBaseContext(), "Deleted Successfully!", Toast.LENGTH_LONG).show();
                                                                   } else {
                                                                       Toast.makeText(getBaseContext(), "Cant Delete!" + e.toString(), Toast.LENGTH_LONG).show();
                                                                   }
                                                               }
                                                           });
                                                       } else {
                                                           Log.d("Attention", "Error: " + e.getMessage());
                                                       }
                                                   }
                                               });
                                           }catch (Exception e){
                                               e.printStackTrace();
                                           }

                                       }
                                   }
                           )
                           .setNegativeButton("No", null)
                           .show();
                   return true;
               }

           });
       }
       else if(measureID == 3){
           setTitle("您的膳食记录");
           final ArrayList<String> ids= new ArrayList<String>();
           ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Diet");
           query1.whereEqualTo("createdby", ParseUser.getCurrentUser());
           query1.addDescendingOrder("createdAt");
           query1.findInBackground(new FindCallback<ParseObject>() {
               @Override
               public void done(List<ParseObject> objects, ParseException e) {

                   if (e == null) {

                       if (objects.size() > 0) {
                           Log.i("objects.size","retrived "+objects.size());

                           for (ParseObject pressure : objects) {

                               recordsdisplay.add(pressure.getString("record")+"\n记录日期:"+ pressure.getCreatedAt().toString());
                               ids.add(pressure.getObjectId());
                           }

                           listView.setAdapter(arrayAdapter);
                       }

                   } else {

                       e.printStackTrace();

                   }

               }
           });
           listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

               @Override
               public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                   final int itemToDelete = i;
                   new AlertDialog.Builder(PatientMeasure.this)
                           .setIcon(android.R.drawable.ic_dialog_alert)
                           .setTitle("Are you sure?")
                           .setMessage("Do you want to delete this record?")
                           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialogInterface, int i) {
                                           Log.i("Attention",ids.get(itemToDelete));
                                           try {

                                               ParseQuery<ParseObject> query = ParseQuery.getQuery("Diet");
                                               query.whereEqualTo("objectId", ids.get(itemToDelete));
                                               query.findInBackground(new FindCallback<ParseObject>() {
                                                   public void done(List<ParseObject> objectList, ParseException e) {
                                                       if (e == null) {
                                                           objectList.get(0).deleteInBackground(new DeleteCallback() {
                                                               @Override
                                                               public void done(ParseException e) {
                                                                   if (e == null) {
                                                                       recordsdisplay.remove(itemToDelete);
                                                                       arrayAdapter.notifyDataSetChanged();
                                                                       Toast.makeText(getBaseContext(), "Deleted Successfully!", Toast.LENGTH_LONG).show();
                                                                   } else {
                                                                       Toast.makeText(getBaseContext(), "Cant Delete!" + e.toString(), Toast.LENGTH_LONG).show();
                                                                   }
                                                               }
                                                           });
                                                       } else {
                                                           Log.d("Attention", "Error: " + e.getMessage());
                                                       }
                                                   }
                                               });
                                           }catch (Exception e){
                                               e.printStackTrace();
                                           }

                                       }
                                   }
                           )
                           .setNegativeButton("No", null)
                           .show();
                   return true;
               }

           });
       }

    }
}
