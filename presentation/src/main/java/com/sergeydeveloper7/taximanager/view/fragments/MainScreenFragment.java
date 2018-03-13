package com.sergeydeveloper7.taximanager.view.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.navigation.Navigator;
import com.sergeydeveloper7.taximanager.presenter.MainScreenPresenter;
import com.sergeydeveloper7.taximanager.utils.Const;
import com.sergeydeveloper7.taximanager.view.activities.MainActivity;
import com.sergeydeveloper7.taximanager.view.basic.MainScreenView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainScreenFragment extends Fragment implements View.OnClickListener, MainScreenView {

    private static final String TAG = MainScreenFragment.class.getSimpleName();
    private Context             context;
    private String              email;
    private String              password;
    private MainScreenPresenter presenter;

    @BindView(R.id.loginRelativeLayout)        RelativeLayout loginRelativeLayout;
    @BindView(R.id.registerLinkTextView)       TextView       registerLinkTextView;
    @BindView(R.id.separatorLinkView)          View           separatorLinkView;
    @BindView(R.id.registerLinkRelativeLayout) RelativeLayout registerLinkRelativeLayout;
    @BindView(R.id.loginButton)                Button         loginButton;
    @BindView(R.id.emailAddressEditText)       EditText       emailAddressEditText;
    @BindView(R.id.passwordEditText)           EditText       passwordEditText;

    @Inject Navigator navigator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        ((MainActivity)getActivity()).getApplicationComponent().inject(this);
        presenter = new MainScreenPresenter(this, context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_screen, container, false);
        ButterKnife.bind(this, rootView);
        registerLinkRelativeLayout.setOnClickListener(this);
        return rootView;
    }

    @OnTextChanged(R.id.emailAddressEditText) void onEmailAddressEditTextChanged(CharSequence text) {
        email = text.toString();
    }

    @OnTextChanged(R.id.passwordEditText) void onPasswordEditTextChanged(CharSequence text) {
        password = text.toString();
    }

    @OnClick(R.id.loginButton) void login() {
        if(email.isEmpty() || !email.contains("@")){
            Snackbar.make(loginRelativeLayout, context.getString(R.string.register_screen_email_hint), Snackbar.LENGTH_LONG)
                    .show();
        } else if(password.isEmpty()){
            Snackbar.make(loginRelativeLayout, context.getString(R.string.register_screen_password_hint), Snackbar.LENGTH_LONG)
                    .show();
        } else {
            presenter.login(email, password);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerLinkRelativeLayout:
                registerLinkTextView.setTextColor(context.getResources().getColor(R.color.white));
                separatorLinkView.setBackgroundColor(context.getResources().getColor(R.color.white));
                navigator.startFragmentWithBackStack(context, new RegisterFragment(), Const.REGISTER_FRAGMENT_ID);
                break;
        }
    }

    @Override
    public void showLogInProcessStart() {

    }

    @Override
    public void showLogInProcessEnd() {

    }

    @Override
    public void showLogInProcessError(Throwable throwable) {

    }
}
