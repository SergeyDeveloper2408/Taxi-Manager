package com.sergeydeveloper7.data.models;

/**
 * Created by serg on 11.01.18.
 */

public class DriverModel {

    private long id;
    private String userName;
    private String userState;
    private CarModel car;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public CarModel getCar() {
        return car;
    }

    public void setCar(CarModel car) {
        this.car = car;
    }
}
