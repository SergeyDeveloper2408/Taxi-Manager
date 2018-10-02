package com.sergeydeveloper7.data.models.general;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by serg on 02.03.18.
 */

public class UserModel {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("emailAddress")
    @Expose
    private String emailAddress = "";

    @SerializedName("password")
    @Expose
    private String password = "";

    @SerializedName("userName")
    @Expose
    private String userName = "";

    @SerializedName("rating")
    @Expose
    private int    rating;

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber = "";

    @SerializedName("role")
    @Expose
    private String role = "";

    transient private boolean isValid = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
