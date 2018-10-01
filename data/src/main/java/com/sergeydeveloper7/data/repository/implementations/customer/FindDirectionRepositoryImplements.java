package com.sergeydeveloper7.data.repository.implementations.customer;

import com.sergeydeveloper7.data.BuildConfig;
import com.sergeydeveloper7.data.models.map.response.FindDirectionResponse;
import com.sergeydeveloper7.data.network.RestClient;
import com.sergeydeveloper7.data.network.RestInterface;
import com.sergeydeveloper7.data.repository.basic.customer.FindDirectionRepository;

import retrofit2.Response;
import rx.Observable;

public class FindDirectionRepositoryImplements implements FindDirectionRepository {

    private RestInterface client;
    private static final String API_KEY = BuildConfig.API_KEY;

    public FindDirectionRepositoryImplements() {
        client = new RestClient().getApiService();
    }

    @Override
    public Observable<Response<FindDirectionResponse>> findDirection(String from, String to) {
        return client.findDirection(from, to, API_KEY);

    }
}
