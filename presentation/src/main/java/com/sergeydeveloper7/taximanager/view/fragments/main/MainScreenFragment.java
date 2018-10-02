package com.sergeydeveloper7.taximanager.view.fragments.main;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sergeydeveloper7.data.utils.UtilMethods;
import com.sergeydeveloper7.domain.Util;
import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.navigation.Navigator;
import com.sergeydeveloper7.taximanager.presenter.main.MainScreenPresenter;
import com.sergeydeveloper7.taximanager.utils.Const;
import com.sergeydeveloper7.taximanager.view.activities.customer.CustomerActivity;
import com.sergeydeveloper7.taximanager.view.activities.main.MainActivity;
import com.sergeydeveloper7.taximanager.view.base.main.MainScreenView;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainScreenFragment extends Fragment implements View.OnClickListener, MainScreenView {

    private static final String TAG = MainScreenFragment.class.getSimpleName();
    private MainActivity        mainActivity;
    private String              email = "";
    private String              password = "";
    private MainScreenPresenter presenter;


    //Text Input Layouts
    @BindView(R.id.loginEmailTextInputLayout)
    TextInputLayout loginEmailTextInputLayout;

    @BindView(R.id.loginPasswordTextInputLayout)
    TextInputLayout loginPasswordTextInputLayout;

    //Text Input Edit Texts
    @BindView(R.id.loginEmailTextInputEditText)
    TextInputEditText loginEmailTextInputEditText;

    @BindView(R.id.loginPasswordTextInputEditText)
    TextInputEditText loginPasswordTextInputEditText;

    //Layouts
    @BindView(R.id.loginRelativeLayout)
    RelativeLayout loginRelativeLayout;

    @BindView(R.id.registerLinkRelativeLayout)
    RelativeLayout registerLinkRelativeLayout;

    @BindView(R.id.contentRelativeLayout)
    RelativeLayout contentRelativeLayout;

    //Text Views
    @BindView(R.id.registerLinkTextView)
    TextView registerLinkTextView;

    //Views
    @BindView(R.id.separatorLinkView)
    View separatorLinkView;

    //Buttons
    @BindView(R.id.loginButton)
    Button loginButton;

    //Progress Bars
    @BindView(R.id.loginProgressBar)
    ProgressBar loginProgressBar;

    //Dependency Injection
    @Inject
    Navigator navigator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        mainActivity.getApplicationComponent().inject(this);
        presenter = new MainScreenPresenter(this, mainActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_screen, container,
                false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();
    }

    @OnTextChanged(R.id.loginEmailTextInputEditText)
    void onEmailAddressEditTextChanged(CharSequence text) {
        loginEmailTextInputLayout.setErrorEnabled(false);
        loginEmailTextInputLayout.setError(null);
        email = text.toString();
    }

    @OnTextChanged(R.id.loginPasswordTextInputEditText)
    void onPasswordEditTextChanged(CharSequence text) {
        loginPasswordTextInputLayout.setErrorEnabled(false);
        loginPasswordTextInputLayout.setError(null);
        password = text.toString();
    }

    @OnClick(R.id.loginButton)
    void login() {
        if(!UtilMethods.isValidEmail(email)){
            loginEmailTextInputLayout.setError(getString(R.string.main_screen_invalid_email));
        } else if(password.isEmpty()){
            loginPasswordTextInputLayout.setError(getString(R.string.register_screen_password_hint));
        } else {
            try {
                presenter.login(email, Util.SHA1(password));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerLinkRelativeLayout:
                registerLinkTextView.setTextColor(getResources().getColor(R.color.white));
                separatorLinkView.setBackgroundColor(getResources().getColor(R.color.white));
                navigator.startFragmentWithBackStack(mainActivity, new RegisterFragment(),
                        Const.REGISTER_FRAGMENT_ID, R.id.main_frame);
                break;
        }
    }

    @Override
    public void showLogInProcessStart() {
        contentRelativeLayout.setVisibility(View.GONE);
        loginProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLogInProcessEnd(String role) {
        if(role.equals(getString(R.string.register_screen_role_customer))){
            navigator.startActivity(mainActivity, CustomerActivity.class);
        } else {

        }
    }

    @Override
    public void showLogInProcessUserNotFound() {
        contentRelativeLayout.setVisibility(View.VISIBLE);
        loginProgressBar.setVisibility(View.GONE);
        Snackbar.make(loginRelativeLayout, getString(R.string.main_screen_invalid_credentials), Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showLogInProcessError(Throwable throwable) {
        contentRelativeLayout.setVisibility(View.VISIBLE);
        loginProgressBar.setVisibility(View.GONE);
    }

    private void initializeViews(){
        registerLinkRelativeLayout.setOnClickListener(this);
        loginProgressBar.getIndeterminateDrawable()
                .setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
    }
}
