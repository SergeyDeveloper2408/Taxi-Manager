package com.sergeydeveloper7.taximanager.presenter.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.sergeydeveloper7.data.errors.EmailAddressExistException;
import com.sergeydeveloper7.data.errors.PhoneNumberExistException;
import com.sergeydeveloper7.data.models.general.CarModel;
import com.sergeydeveloper7.data.models.general.CustomerModel;
import com.sergeydeveloper7.data.models.general.DriverModel;
import com.sergeydeveloper7.data.models.general.UserModel;
import com.sergeydeveloper7.data.repository.implementations.main.RegisterRepositoryImplements;
import com.sergeydeveloper7.data.validation.Validation;
import com.sergeydeveloper7.taximanager.presenter.base.BasePresenter;
import com.sergeydeveloper7.taximanager.utils.Const;
import com.sergeydeveloper7.taximanager.view.base.main.RegisterView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;


/**
 * Created by serg on 11.01.18.
 */

public class RegisterPresenter implements BasePresenter {

    private Context                      context;
    private RegisterView                 view;
    private RegisterRepositoryImplements repository;
    private boolean                      isViewDestroyed = false;

    public RegisterPresenter(RegisterView view, Context context) {
        this.view = view;
        this.context = context;
        repository = new RegisterRepositoryImplements();
    }

    public void validateUser(UserModel userModel){
        view.showLoadingProcessStart();
        repository.validateRegistration(userModel)
                .takeUntil((Predicate<Validation>) validation -> isViewDestroyed)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Validation>() {
                    @Override
                    public void onNext(Validation registerValidation) {
                        if(!registerValidation.isValid()){
                            showException(registerValidation.getException());
                        } else {
                            view.setValidation(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showRegistrationProcessError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void registerCustomer(UserModel userModel, CustomerModel customerModel){
        view.showLoadingProcessStart();
        repository.registerCustomer(userModel, customerModel)
                .takeUntil((Predicate<UserModel>) userModel1 -> isViewDestroyed)
                .subscribe(new DisposableObserver<UserModel>() {
                    @Override
                    public void onNext(UserModel userModel) {
                        saveUser(userModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showRegistrationProcessError(e);
                    }

                    @Override
                    public void onComplete() {
                        view.showLoadingProcessEnd();
                    }
                });
    }

    public void registerDriver(UserModel userModel, DriverModel driverModel, CarModel carModel){
        view.showLoadingProcessStart();
        repository.registerDriver(userModel, driverModel, carModel)
                .takeUntil((Predicate<UserModel>) userModel1 -> isViewDestroyed)
                .subscribe(new DisposableObserver<UserModel>() {
                    @Override
                    public void onNext(UserModel userModel) {
                        saveUser(userModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showRegistrationProcessError(e);
                    }

                    @Override
                    public void onComplete() {
                        view.showLoadingProcessEnd();
                    }
                });
    }

    private void showException(Exception e){
        if(e instanceof EmailAddressExistException){
            view.showEmailExistError();
        } else if(e instanceof PhoneNumberExistException){
            view.showPhoneNumberExistError();
        }
    }

    private void saveUser(UserModel userModel){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", new Gson().toJson(userModel));
        editor.putBoolean(Const.SHARED_PREFERENCE_IS_USER_LOGIN, true);
        editor.apply();
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
