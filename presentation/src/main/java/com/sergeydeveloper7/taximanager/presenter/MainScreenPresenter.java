package com.sergeydeveloper7.taximanager.presenter;

import android.content.Context;

import com.sergeydeveloper7.data.repository.DbLoginRepository;
import com.sergeydeveloper7.taximanager.view.basic.MainScreenView;


/**
 * Created by serg on 11.01.18.
 */

public class MainScreenPresenter implements BasePresenter {

    private Context           context;
    private MainScreenView    view;
    private DbLoginRepository dbLoginRepository;
    private boolean           isViewDestroyed = false;

    public MainScreenPresenter(MainScreenView view, Context context) {
        this.view = view;
        this.context = context;
        dbLoginRepository = new DbLoginRepository();
    }

    public void login(String email, String password) {

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
