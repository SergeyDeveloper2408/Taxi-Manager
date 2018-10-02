package com.sergeydeveloper7.taximanager.presenter.customer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.sergeydeveloper7.data.models.general.UserModel;
import com.sergeydeveloper7.data.repository.implementations.customer.ChangeCustomerDataRepositoryImplements;
import com.sergeydeveloper7.data.validation.Validation;
import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.presenter.base.BasePresenter;
import com.sergeydeveloper7.taximanager.view.base.customer.CustomerEditProfileView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;

public class CustomerEditProfilePresenter implements BasePresenter {

    private Context                                context;
    private CustomerEditProfileView                view;
    private ChangeCustomerDataRepositoryImplements repository;
    private boolean                                isViewDestroyed = false;
    private UserModel                              userModel;

    public CustomerEditProfilePresenter(CustomerEditProfileView view, Context context) {
        this.view = view;
        this.context = context;
        repository = new ChangeCustomerDataRepositoryImplements();
    }

    public void validateEmailAddress(String emailAddress){
        view.showValidateEmailAddressStart();

        repository.validateEmailAddress(emailAddress)
                .takeUntil((Predicate<Validation>) validation -> isViewDestroyed)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Validation>() {
                    @Override
                    public void onNext(Validation registerValidation) {
                        if(registerValidation.isValid()){
                            view.showEmailAddressIsValid();
                        } else {
                            view.showEmailAddressIsInvalid();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showEmailAddressValidationError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void validatePhoneNumber(String phoneNumber){
        view.showValidatePhoneNumberStart();

        repository.validatePhoneNumber(phoneNumber)
                .takeUntil((Predicate<Validation>) validation -> isViewDestroyed)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Validation>() {
                    @Override
                    public void onNext(Validation registerValidation) {
                        if(registerValidation.isValid()){
                            view.showPhoneNumberIsValid();
                        } else {
                            view.showPhoneNumberIsInvalid();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showPhoneNumberValidationError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void changeData(UserModel changedUserModel){
        view.showChangeDataProcessStart();

        repository.changeData(changedUserModel)
                .takeUntil((Predicate<UserModel>) customer1 -> isViewDestroyed)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UserModel>() {
                    @Override
                    public void onNext(UserModel userModelResponse) {

                        if(userModelResponse.getRole().isEmpty()){
                            view.showChangeDataProcessError(new Throwable(context.getString(R.string.no_data_error)));
                        } else {
                            userModel = userModelResponse;
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("user", new Gson().toJson(userModel));
                            editor.apply();
                            view.showChangeDataProcessEnd();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showChangeDataProcessError(e);
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