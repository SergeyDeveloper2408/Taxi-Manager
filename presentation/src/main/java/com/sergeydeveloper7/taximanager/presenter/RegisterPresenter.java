package com.sergeydeveloper7.taximanager.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.sergeydeveloper7.data.models.CustomerModel;
import com.sergeydeveloper7.data.models.UserModel;
import com.sergeydeveloper7.data.repository.DbRegisterRepository;
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
    private DbRegisterRepository dbRepository;
    private boolean isViewDestroyed = false;

    public RegisterPresenter(RegisterView view, Context context) {
        this.view = view;
        this.context = context;
        dbRepository = new DbRegisterRepository();
    }

    public void registerCustomer(UserModel userModel, CustomerModel customerModel){
        view.showRegistrationProcessStart();
        dbRepository.registerCustomer(userModel, customerModel)
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
                                   view.showRegistrationProcessEnd();
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
