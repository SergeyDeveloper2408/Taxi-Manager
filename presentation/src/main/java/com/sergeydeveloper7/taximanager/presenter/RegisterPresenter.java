package com.sergeydeveloper7.taximanager.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.sergeydeveloper7.data.errors.EmailExistException;
import com.sergeydeveloper7.data.errors.PhoneNumberExistException;
import com.sergeydeveloper7.data.models.CustomerModel;
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

    public void registerCustomer(UserModel userModel, CustomerModel customerModel){
        view.showRegistrationProcessStart();
        dbRepository.validateRegistration(userModel)
                .takeUntil((Predicate<RegisterValidation>) customer1 -> isViewDestroyed)
                .flatMap(registerValidation -> {
                    if(!registerValidation.isValid()){
                        if(registerValidation.getException() instanceof EmailExistException){
                            view.showEmailExistError();
                        } else if(registerValidation.getException() instanceof PhoneNumberExistException){
                            view.showPhoneNumberExistError();
                        }
                    } else userModel.setValid(true);
                    return dbRepository.registerCustomer(userModel, customerModel);
                })
                .filter(UserModel::isValid)
                .takeUntil((Predicate<UserModel>) customer1 -> isViewDestroyed)
                .subscribe(new DisposableObserver<UserModel>() {
                               @Override
                               public void onNext(UserModel customer) {
                                   SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                                   SharedPreferences.Editor editor = sharedPreferences.edit();
                                   editor.putString("user", new Gson().toJson(userModel));
                                   editor.putBoolean(Const.SHARED_PREFERENCE_IS_USER_LOGIN, true);
                                   editor.apply();
                               }

                               @Override
                               public void onError(Throwable e) {
                                   view.showRegistrationProcessError(e);
                               }

                               @Override
                               public void onComplete() {
                                   view.showRegistrationProcessEnd(userModel);
                               }
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
