package com.sergeydeveloper7.taximanager.view.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.navigation.Navigator;
import com.sergeydeveloper7.taximanager.view.activities.MainActivity;

import javax.inject.Inject;

public class MainScreenFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = MainScreenFragment.class.getSimpleName();
    private Context             context;
    private Button              loginButton;
    private Button              registerButton;

    @Inject
    Navigator navigator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        ((MainActivity)getActivity()).getApplicationComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_screen, container, false);
        loginButton = rootView.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        registerButton = rootView.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:

                break;
            case R.id.registerButton:
                break;
        }
    }
}
