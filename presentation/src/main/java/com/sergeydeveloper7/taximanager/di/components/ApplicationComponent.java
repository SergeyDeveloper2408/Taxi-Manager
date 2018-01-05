package com.sergeydeveloper7.taximanager.di.components;

import android.content.Context;

import com.sergeydeveloper7.taximanager.di.modules.ApplicationModule;
import com.sergeydeveloper7.taximanager.view.activities.BaseActivity;
import com.sergeydeveloper7.taximanager.view.fragments.MainScreenFragment;

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
    Context context();
}
