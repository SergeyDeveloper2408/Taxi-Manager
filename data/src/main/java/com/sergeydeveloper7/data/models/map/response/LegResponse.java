package com.sergeydeveloper7.data.models.map.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LegResponse {

    @SerializedName("steps")
    @Expose
    private ArrayList<StepResponse> steps;

    @SerializedName("overview_polyline")
    @Expose
    private PolylineResponse overviewPolyline;

    public ArrayList<StepResponse> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<StepResponse> steps) {
        this.steps = steps;
    }

    public PolylineResponse getOverviewPolyline() {
        return overviewPolyline;
    }

    public void setOverviewPolyline(PolylineResponse overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }
}
