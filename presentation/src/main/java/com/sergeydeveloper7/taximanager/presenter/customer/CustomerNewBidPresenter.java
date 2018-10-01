package com.sergeydeveloper7.taximanager.presenter.customer;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sergeydeveloper7.data.models.map.basic.Distance;
import com.sergeydeveloper7.data.models.map.basic.Route;
import com.sergeydeveloper7.data.models.map.response.BasicModelResponse;
import com.sergeydeveloper7.data.models.map.response.StepResponse;
import com.sergeydeveloper7.data.repository.basic.customer.FindDirectionRepository;
import com.sergeydeveloper7.data.repository.implementations.customer.FindDirectionRepositoryImplements;
import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.presenter.base.BasePresenter;
import com.sergeydeveloper7.taximanager.view.basic.customer.CustomerNewBidView;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Random;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CustomerNewBidPresenter implements BasePresenter {

    private static final String               TAG = CustomerNewBidPresenter.class.getSimpleName();
    private Context                           context;
    private CustomerNewBidView                view;
    private FindDirectionRepositoryImplements repository;
    private boolean                           isViewDestroyed = false;
    private Subscription                      findDirectionSubscription;

    public CustomerNewBidPresenter(Context context, CustomerNewBidView view) {
        this.context = context;
        this.view = view;
        repository = new FindDirectionRepositoryImplements();
    }

    //TODO fake method, needs, because Google Directions API is not free.
    public void findDirectionFake(String from, String to){

        view.fakeShowFindDirectionProcessStart();
        StepResponse stepResponse = new StepResponse();

        //Generate Random Value
        Random rand = new Random();
        int randomInt = rand.nextInt(30) + 1;

        //Set distance
        BasicModelResponse distance = new BasicModelResponse();
        distance.setValue(randomInt);
        distance.setText(String.format(context.getString(R.string.customer_screen_new_bid_distance),
                String.valueOf(distance.getValue())));

        //Set duration
        BasicModelResponse duration = new BasicModelResponse();
        duration.setValue(distance.getValue() + 10);
        duration.setText(String.format(context.getString(R.string.customer_screen_new_bid_time),
                String.valueOf(duration.getValue())));

        //Set values
        stepResponse.setStartAddress(from);
        stepResponse.setEndAddress(to);
        stepResponse.setDistance(distance);
        stepResponse.setDuration(duration);
        stepResponse.setCost(distance.getValue()*2);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            view.fakeShowFindDirectionProcessEnd(stepResponse);
        }, 2000);

    }

    //TODO real method, needs to restore, when Google Directions API will be free.
//    public void findDirection(String from, String to){
//        view.showFindDirectionProcessStart();
//        findDirectionSubscription = repository.findDirection(from, to)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//                    System.out.println(response.message() + "\n" + response.raw() + "\n"
//                            + response.headers() + "\n");
//                    Log.d(TAG, " result " + new Gson().toJson(response.body()));
//                    Gson gson = new GsonBuilder().create();
//                    Error error;
//                    try {
//                        if(response.errorBody() != null){
//                            error = gson.fromJson(response.errorBody().string(), Error.class);
//                            if(response.errorBody() != null){
//                                error = gson.fromJson(response.errorBody().string(), Error.class);
//                            }
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    if(response.isSuccessful() && response.body() != null){
//                        Log.d(TAG, "result is OK " + new Gson().toJson(response.body()));
//                        if(response.body().getErrorMessage() != null
//                                && !response.body().getErrorMessage().isEmpty()){
//                            view.showFindDirectionProcessError(
//                                    new Throwable(response.body().getErrorMessage()));
//                        }
//                        if (response.body().getStatus() != null
//                                && !response.body().getStatus().isEmpty()){
//                            view.showFindDirectionProcessError(
//                                    new Throwable(response.body().getStatus()));
//                        }
//                    }
//                }, throwable -> {
//                    throwable.printStackTrace();
//                    if(throwable instanceof ConnectException
//                            || throwable instanceof SocketTimeoutException)
//                        view.showFindDirectionProcessError(throwable);
//                });
//    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        if(findDirectionSubscription != null && !findDirectionSubscription.isUnsubscribed())
            findDirectionSubscription.unsubscribe();
    }
}
