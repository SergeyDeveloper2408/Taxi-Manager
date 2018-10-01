package com.sergeydeveloper7.data.models.map.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PolylineResponse {

    @SerializedName("points")
    @Expose
    private String points;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
