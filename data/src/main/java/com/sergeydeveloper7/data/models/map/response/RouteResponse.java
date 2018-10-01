package com.sergeydeveloper7.data.models.map.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RouteResponse {

    @SerializedName("legs")
    @Expose
    private ArrayList<LegResponse> legs;

    public ArrayList<LegResponse> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<LegResponse> legs) {
        this.legs = legs;
    }
}
