package com.sergeydeveloper7.data.models.map.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StepResponse {

    @SerializedName("travel_mode")
    @Expose
    private String travelMode;

    @SerializedName("html_instructions")
    @Expose
    private String htmlInstructions;

    @SerializedName("duration")
    @Expose
    private BasicModelResponse duration;

    @SerializedName("distance")
    @Expose
    private BasicModelResponse distance;

    @SerializedName("start_location")
    @Expose
    private LocationResponse startLocation;

    @SerializedName("end_location")
    @Expose
    private LocationResponse endLocation;

    @SerializedName("start_address")
    @Expose
    private String startAddress;

    @SerializedName("end_address")
    @Expose
    private String endAddress;

    @SerializedName("polyline")
    @Expose
    private PolylineResponse polyline;

    private int cost;

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    public BasicModelResponse getDuration() {
        return duration;
    }

    public void setDuration(BasicModelResponse duration) {
        this.duration = duration;
    }

    public BasicModelResponse getDistance() {
        return distance;
    }

    public void setDistance(BasicModelResponse distance) {
        this.distance = distance;
    }

    public LocationResponse getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LocationResponse startLocation) {
        this.startLocation = startLocation;
    }

    public LocationResponse getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LocationResponse endLocation) {
        this.endLocation = endLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public PolylineResponse getPolyline() {
        return polyline;
    }

    public void setPolyline(PolylineResponse polyline) {
        this.polyline = polyline;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
