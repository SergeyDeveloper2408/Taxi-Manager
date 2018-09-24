package com.sergeydeveloper7.taximanager.view.activities.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sergeydeveloper7.taximanager.application.TaxiManagerApplication;
import com.sergeydeveloper7.taximanager.di.components.ApplicationComponent;
import com.sergeydeveloper7.taximanager.navigation.Navigator;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {

    public @Inject Navigator      navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return ((TaxiManagerApplication) getApplication()).getApplicationComponent();
    }

}
