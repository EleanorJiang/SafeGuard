package com.parse.starter;

import com.parse.ParseUser;
import com.parse.ParseClassName;
import com.parse.SignUpCallback;


@ParseClassName("Patient")
/**
 * Created by dell on 2018/5/16.
 */
public class Patient extends ParseUser {
    private String BloodType;
    private String Disease;
    private String Birthday;

    public String getBirthday() {
        return Birthday;
    }
    public void setBirthday(String birthday) {
        Birthday=birthday;
    }

    public String getBloodType() {
        return BloodType;
    }
    public void setBloodType(String bloodType) {
        BloodType=bloodType;
    }

    public String getDisease() {
        return Disease;
    }
    public void setDisease(String disease) {
        Disease=disease;
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public void setObjectId(String newObjectId) {
        super.setObjectId(newObjectId);
    }

    @Override
    public String getObjectId() {
        return super.getObjectId();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }
    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void signUpInBackground(SignUpCallback callback) {
        super.signUpInBackground(callback);
    }

}
