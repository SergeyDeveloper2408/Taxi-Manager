package com.sergeydeveloper7.data.models.general;

/**
 * Created by serg on 11.01.18.
 */

public class CustomerModel {

    private long id;
    private String emailAddress;

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
}
