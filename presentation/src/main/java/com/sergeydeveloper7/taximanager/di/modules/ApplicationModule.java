package com.sergeydeveloper7.taximanager.di.modules;

import android.content.Context;

import com.sergeydeveloper7.taximanager.application.TaxiManagerApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by serg on 02.01.18.
 */

@Module
public class ApplicationModule {

    private final TaxiManagerApplication application;

    public ApplicationModule(TaxiManagerApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }
}
