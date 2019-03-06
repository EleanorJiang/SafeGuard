package com.parse.starter;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseClassName;
import com.parse.SignUpCallback;

import java.util.Date;

/**
 * Created by dell on 2018/5/16.
 */
@ParseClassName("Doctor")
public class Doctor extends ParseUser {
    private String major;
    private String department;

    public String getDepartment() {
        return department;
    }

    public String getMajor() {
        return major;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setMajor(String major) {
        this.major = major;
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
