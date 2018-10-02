package com.sergeydeveloper7.data.db.models;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Review extends RealmObject {

    @Required
    private int customersRating;

    @Required
    private int driversRating;

    @Required
    private String customersReview;

    @Required
    private String driversReview;

    public int getCustomersRating() {
        return customersRating;
    }

    public void setCustomersRating(int customersRating) {
        this.customersRating = customersRating;
    }

    public int getDriversRating() {
        return driversRating;
    }

    public void setDriversRating(int driversRating) {
        this.driversRating = driversRating;
    }

    public String getCustomersReview() {
        return customersReview;
    }

    public void setCustomersReview(String customersReview) {
        this.customersReview = customersReview;
    }

    public String getDriversReview() {
        return driversReview;
    }

    public void setDriversReview(String driversReview) {
        this.driversReview = driversReview;
    }
}