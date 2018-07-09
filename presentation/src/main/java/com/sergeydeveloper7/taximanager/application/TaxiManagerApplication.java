package com.sergeydeveloper7.taximanager.application;

import android.support.multidex.MultiDexApplication;

import com.sergeydeveloper7.data.db.RealmInitializer;
import com.sergeydeveloper7.taximanager.di.components.ApplicationComponent;
import com.sergeydeveloper7.taximanager.di.components.DaggerApplicationComponent;
import com.sergeydeveloper7.taximanager.di.modules.ApplicationModule;


/**
 * Created by serg on 02.01.18.
 */

public class TaxiManagerApplication extends MultiDexApplication {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        RealmInitializer.initializeRealm(this);
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

}
