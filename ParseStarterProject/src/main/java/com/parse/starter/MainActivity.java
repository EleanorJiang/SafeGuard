
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    Boolean signUpModeActive = true;

    TextView changeSignupModeTextView;

    EditText passwordEditText;

    public void showUserList() {

        Log.i("SHOW", "showUserHome: ");
        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);

    }
    public void showPatientHome() {
        Log.i("SHOW", "showPatientHome: ");
        {Log.i("Attention",ParseUser.getCurrentUser().getUsername());}
        Intent intent = new Intent(getApplicationContext(), PatientHome.class);
        startActivity(intent);

    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

            signUp(view);

        }

        return false;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.changeSignupModeTextView) {

            Button signupButton = (Button) findViewById(R.id.signupButton);

            if (signUpModeActive) {

                signUpModeActive = false;
                signupButton.setText("Login");
                changeSignupModeTextView.setText("Or, Signup");

            } else {

                signUpModeActive = true;
                signupButton.setText("Signup");
                changeSignupModeTextView.setText("Or, Login");

            }

        } else if (view.getId() == R.id.backgroundRelativeLayout || view.getId() == R.id.logoImageView) {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


        }

    }

    public void signUp(View view) {

        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        Switch userTypeSwitch = (Switch) findViewById(R.id.switch1);
        String userType = "patient";

        if (userTypeSwitch.isChecked()) {

            userType = "doctor";

        }

        if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {

            Toast.makeText(this, "A username and password are required.", Toast.LENGTH_SHORT).show();

        } else {
            if( userType== "patient") {
                Patient patient = new Patient();
                if (signUpModeActive) {

                    patient.setUsername(usernameEditText.getText().toString());
                    patient.setPassword(passwordEditText.getText().toString());
                    patient.put("DotocrOrPatient","Patient");

                    patient.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                Log.i("Signup", "Successful");
                                ParseObject patientInfo=new ParseObject("PatientInfo");
                                patientInfo.put("patient",ParseUser.getCurrentUser());
                                patientInfo.saveInBackground();
                                showPatientHome();

                            } else {

                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                } else {

                    Patient.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {

                            if (user != null) {
                                if (user.get("DotocrOrPatient").toString().matches("Doctor")){
                                    Toast.makeText(MainActivity.this, "您的用户类型为医生！", Toast.LENGTH_SHORT).show();
                                }
                                else {

                                    Log.i("Signup", "Login successful");

                                    showPatientHome();
                                }

                            } else {

                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }


                        }
                    });


                }
            } else if(userType== "doctor") {
                Doctor user = new Doctor();
                if (signUpModeActive) {

                    user.setUsername(usernameEditText.getText().toString());
                    user.setPassword(passwordEditText.getText().toString());
                    user.put("DotocrOrPatient","Doctor");

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                Log.i("Signup", "Successful");
                                ParseObject patientInfo=new ParseObject("DoctorInfo");
                                patientInfo.put("doctor",ParseUser.getCurrentUser());
                                patientInfo.saveInBackground();
                                showUserList();

                            } else {

                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                } else {

                    Doctor.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {

                            if (user != null) {
                                if (user.get("DotocrOrPatient").toString().matches("Patient")){
                                    Toast.makeText(MainActivity.this, "您的用户类型为病人！", Toast.LENGTH_SHORT).show();
                                }
                                else {

                                    Log.i("Signup", "Login successful");

                                    showUserList();
                                }

                            } else {

                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }


                        }
                    });

                }
            }
        }


    }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

      setTitle("Heart Guard");

      changeSignupModeTextView = (TextView) findViewById(R.id.changeSignupModeTextView);

      changeSignupModeTextView.setOnClickListener(this);

      RelativeLayout backgroundRelativeLayout = (RelativeLayout) findViewById(R.id.backgroundRelativeLayout);

      ImageView logoImageView = (ImageView) findViewById(R.id.logoImageView);

      backgroundRelativeLayout.setOnClickListener(this);

      logoImageView.setOnClickListener(this);

      passwordEditText = (EditText) findViewById(R.id.passwordEditText);

      passwordEditText.setOnKeyListener(this);

      if (ParseUser.getCurrentUser() != null) {

         if(ParseUser.getCurrentUser().getString("DotocrOrPatient").matches("Doctor"))
              showUserList();
          else
              showPatientHome();

      }

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }


}
