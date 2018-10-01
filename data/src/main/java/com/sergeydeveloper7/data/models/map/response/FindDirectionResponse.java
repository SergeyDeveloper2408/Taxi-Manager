package com.sergeydeveloper7.data.models.map.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FindDirectionResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("error_message")
    @Expose
    private String errorMessage;

    @SerializedName("routes")
    @Expose
    private ArrayList<RouteResponse> routes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<RouteResponse> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<RouteResponse> routes) {
        this.routes = routes;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
