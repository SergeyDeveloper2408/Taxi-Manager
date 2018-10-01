package com.sergeydeveloper7.data.repository.basic.customer;

import com.sergeydeveloper7.data.models.map.response.FindDirectionResponse;


import retrofit2.Response;
import rx.Observable;

public interface FindDirectionRepository {
    Observable<Response<FindDirectionResponse>> findDirection(String from, String to);
}
