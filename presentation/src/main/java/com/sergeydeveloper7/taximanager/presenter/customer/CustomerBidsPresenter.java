package com.sergeydeveloper7.taximanager.presenter.customer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.sergeydeveloper7.data.models.general.BidModel;
import com.sergeydeveloper7.data.models.general.CustomerModel;
import com.sergeydeveloper7.data.models.general.UserModel;
import com.sergeydeveloper7.data.models.map.response.BasicModelResponse;
import com.sergeydeveloper7.data.models.map.response.StepResponse;
import com.sergeydeveloper7.data.repository.implementations.customer.CustomerBidsRepositoryImplements;
import com.sergeydeveloper7.data.repository.implementations.customer.FindDirectionRepositoryImplements;
import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.presenter.base.BasePresenter;
import com.sergeydeveloper7.taximanager.utils.Const;
import com.sergeydeveloper7.taximanager.view.base.customer.CustomerBidsView;
import com.sergeydeveloper7.taximanager.view.base.customer.CustomerNewBidView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import rx.Subscription;

public class CustomerBidsPresenter implements BasePresenter {

    private static final String              TAG = CustomerBidsPresenter.class.getSimpleName();
    private Context                          context;
    private CustomerBidsView                 view;
    private CustomerBidsRepositoryImplements repository;
    private boolean                          isViewDestroyed = false;

    public CustomerBidsPresenter(Context context, CustomerBidsView view) {
        this.context = context;
        this.view = view;
        repository = new CustomerBidsRepositoryImplements();
    }

    public void getBids(long customersUserID, String bidsState){
        view.showFindBidsProcessStart();
        repository.getBids(customersUserID, bidsState)
                .takeUntil((Predicate<ArrayList<BidModel>>) customer1 -> isViewDestroyed)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ArrayList<BidModel>>() {
                    @Override
                    public void onNext(ArrayList<BidModel> bids) {
                        view.showFindBidsProcessEnd(bids);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showFindBidsProcessError(e);
                    }

                    @Override
                    public void onComplete() {}
                });
    }

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
        isViewDestroyed = true;
    }
}
