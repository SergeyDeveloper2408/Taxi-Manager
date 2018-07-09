package com.sergeydeveloper7.taximanager.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.sergeydeveloper7.data.models.UserModel;
import com.sergeydeveloper7.data.repository.implementations.LoginRepositoryImplements;
import com.sergeydeveloper7.taximanager.utils.Const;
import com.sergeydeveloper7.taximanager.view.basic.MainScreenView;

import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;


/**
 * Created by serg on 11.01.18.
 */

public class MainScreenPresenter implements BasePresenter {

    private Context           context;
    private MainScreenView    view;
    private LoginRepositoryImplements dbLoginRepository;
    private boolean           isViewDestroyed = false;
    private UserModel         userModel;

    public MainScreenPresenter(MainScreenView view, Context context) {
        this.view = view;
        this.context = context;
        dbLoginRepository = new LoginRepositoryImplements();
    }


        public void login(String email, String password){
            view.showLogInProcessStart();
            dbLoginRepository.login(email, password)
                    .takeUntil((Predicate<UserModel>) customer1 -> isViewDestroyed)
                    .subscribe(new DisposableObserver<UserModel>() {
                        @Override
                        public void onNext(UserModel userModelResponse) {
                            if(userModelResponse.getRole().isEmpty()){
                                view.showLogInProcessUserNotFound();
                            } else {
                                userModel = userModelResponse;
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user", new Gson().toJson(userModel));
                                editor.putBoolean(Const.SHARED_PREFERENCE_IS_USER_LOGIN, true);
                                editor.apply();
                                view.showLogInProcessEnd(userModel.getRole());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showLogInProcessError(e);
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
