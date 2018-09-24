package com.sergeydeveloper7.taximanager.di.components;

import android.content.Context;

import com.sergeydeveloper7.taximanager.di.modules.ApplicationModule;
import com.sergeydeveloper7.taximanager.view.activities.base.BaseActivity;
import com.sergeydeveloper7.taximanager.view.fragments.customer.CustomerProfileFragment;
import com.sergeydeveloper7.taximanager.view.fragments.main.MainScreenFragment;
import com.sergeydeveloper7.taximanager.view.fragments.main.RegisterFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by serg on 02.01.18.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);
    void inject(MainScreenFragment mainScreenFragment);
    void inject(RegisterFragment registerFragment);
    void inject(CustomerProfileFragment customerProfileFragment);
    Context context();
}
