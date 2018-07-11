package com.sergeydeveloper7.taximanager.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.sergeydeveloper7.data.errors.EmailExistException;
import com.sergeydeveloper7.data.errors.PhoneNumberExistException;
import com.sergeydeveloper7.data.models.CarModel;
import com.sergeydeveloper7.data.models.CustomerModel;
import com.sergeydeveloper7.data.models.DriverModel;
import com.sergeydeveloper7.data.models.UserModel;
import com.sergeydeveloper7.data.repository.implementations.RegisterRepositoryImplements;
import com.sergeydeveloper7.data.validation.RegisterValidation;
import com.sergeydeveloper7.taximanager.utils.Const;
import com.sergeydeveloper7.taximanager.view.basic.RegisterView;

import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;


/**
 * Created by serg on 11.01.18.
 */

public class RegisterPresenter implements BasePresenter {

    private Context context;
    private RegisterView view;
    private RegisterRepositoryImplements dbRepository;
    private boolean isViewDestroyed = false;

    public RegisterPresenter(RegisterView view, Context context) {
        this.view = view;
        this.context = context;
        dbRepository = new RegisterRepositoryImplements();
    }

    public void validateUser(UserModel userModel){
        view.showLoadingProcessStart();
        dbRepository.validateRegistration(userModel)
                .takeUntil((Predicate<RegisterValidation>) validation -> isViewDestroyed)
                .subscribe(new DisposableObserver<RegisterValidation>() {
                    @Override
                    public void onNext(RegisterValidation registerValidation) {
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
        dbRepository.registerCustomer(userModel, customerModel)
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
        dbRepository.registerDriver(userModel, driverModel, carModel)
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
        if(e instanceof EmailExistException){
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
