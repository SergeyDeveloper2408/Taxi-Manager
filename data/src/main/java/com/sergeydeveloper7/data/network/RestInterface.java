package com.sergeydeveloper7.data.network;

import com.sergeydeveloper7.data.models.map.response.FindDirectionResponse;

import java.util.Map;



import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface RestInterface {


    @GET("maps/api/directions/json")
    Observable<Response<FindDirectionResponse>> findDirection(
            @Query("origin") String from,
            @Query("destination") String to,
            @Query("apiKey") String apiKey);
}
