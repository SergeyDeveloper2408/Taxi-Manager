package com.sergeydeveloper7.taximanager.view.fragments.main;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sergeydeveloper7.data.enums.ValidationError;
import com.sergeydeveloper7.data.models.CarModel;
import com.sergeydeveloper7.data.models.CustomerModel;
import com.sergeydeveloper7.data.models.DriverModel;
import com.sergeydeveloper7.data.models.UserModel;
import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.navigation.Navigator;
import com.sergeydeveloper7.taximanager.presenter.RegisterPresenter;
import com.sergeydeveloper7.taximanager.utils.Const;
import com.sergeydeveloper7.taximanager.view.activities.CustomerActivity;
import com.sergeydeveloper7.taximanager.view.activities.MainActivity;
import com.sergeydeveloper7.taximanager.view.adapters.RegisterAdapter;
import com.sergeydeveloper7.taximanager.view.basic.RegisterView;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterFragment extends Fragment implements RegisterView {

    private static final String TAG = RegisterFragment.class.getSimpleName();
    private Context             context;
    private RegisterAdapter     adapter;
    private UserModel           userModel;
    private CustomerModel       customerModel;
    private DriverModel         driverModel;
    private CarModel            carModel;
    private RegisterPresenter   presenter;

    private ArrayList<String>   index = new ArrayList<>(Arrays.asList(
            Const.REGISTER_FIELD_EMAIL,
            Const.REGISTER_FIELD_PASSWORD,
            Const.REGISTER_FIELD_USERNAME,
            Const.REGISTER_FIELD_PHONENUMBER,
            Const.REGISTER_BUTTON));

    private ArrayList<String>   driverIndex = new ArrayList<>(Arrays.asList(
            Const.REGISTER_FIELD_CAR_COLOR,
            Const.REGISTER_FIELD_CAR_MODEL,
            Const.REGISTER_FIELD_CAR_NUMBER,
            Const.REGISTER_BUTTON_NEXT_STEP));

    @BindView(R.id.chooseYourRoleTextView)     TextView       chooseYourRoleTextView;
    @BindView(R.id.containerRoleLayout)        LinearLayout   containerRoleLayout;
    @BindView(R.id.customerRoleRelativeLayout) RelativeLayout customerRoleRelativeLayout;
    @BindView(R.id.driverRoleRelativeLayout)   RelativeLayout driverRoleRelativeLayout;
    @BindView(R.id.registerFieldsRecyclerView) RecyclerView   registerFieldsRecyclerView;
    @BindView(R.id.registerProgressBar)        ProgressBar    registerProgressBar;
    @BindView(R.id.registerRelativeLayout)     RelativeLayout registerRelativeLayout;

    @Inject Navigator navigator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        ((MainActivity)getActivity()).getApplicationComponent().inject(this);
        presenter = new RegisterPresenter(this, context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, rootView);
        setLeadsRecyclerView();
        return rootView;
    }

    private void setLeadsRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        registerFieldsRecyclerView.setHasFixedSize(false);
        registerFieldsRecyclerView.setLayoutManager(layoutManager);
        registerFieldsRecyclerView.setItemViewCacheSize(50);
        registerFieldsRecyclerView.setDrawingCacheEnabled(true);
        RecyclerView.ItemAnimator animator = registerFieldsRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        adapter = new RegisterAdapter(context, index, this);
        registerFieldsRecyclerView.setAdapter(adapter);
    }

    private void setViewsVisibility(){
        chooseYourRoleTextView.setText(context.getString(R.string.register_screen_fill_information));
        customerRoleRelativeLayout.setVisibility(View.GONE);
        driverRoleRelativeLayout.setVisibility(View.GONE);
        registerFieldsRecyclerView.setVisibility(View.VISIBLE);
    }

    public void registerCustomer(){
        presenter.registerCustomer(userModel, customerModel);
    }

    public void registerDriver(){
        //presenter.registerDriver(userModel, driverModel, carModel);
    }

    @OnClick(R.id.customerRoleRelativeLayout)
    void setRoleCustomer() {
        userModel = new UserModel();
        userModel.setRole(context.getString(R.string.register_screen_role_customer));
        customerModel = new CustomerModel();
        setViewsVisibility();
    }

    @OnClick(R.id.driverRoleRelativeLayout)
    void setRoleDriver() {
        userModel = new UserModel();
        userModel.setRole(context.getString(R.string.register_screen_role_driver));
        driverModel = new DriverModel();
        carModel = new CarModel();
        setViewsVisibility();
    }

    @Override
    public void showRegistrationProcessStart() {
        containerRoleLayout.setVisibility(View.GONE);
        registerProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRegistrationProcessEnd(UserModel userModel) {
        if(userModel.getRole().equals(context.getString(R.string.register_screen_role_customer))){
            this.navigator.startActivity(context, CustomerActivity.class);
        }
    }

    @Override
    public void showRegistrationProcessError(Throwable throwable) {
        containerRoleLayout.setVisibility(View.VISIBLE);
        registerProgressBar.setVisibility(View.GONE);
        Snackbar.make(registerRelativeLayout, context.getString(R.string.register_screen_error), Snackbar.LENGTH_LONG)
                .show();
        throwable.printStackTrace();
    }

    @Override
    public void showEmailExistError() {
        containerRoleLayout.setVisibility(View.VISIBLE);
        registerProgressBar.setVisibility(View.GONE);
        adapter.getErrorMap().get(Const.REGISTER_FIELD_EMAIL).showError(ValidationError.EMAIL_EXIST);
    }

    @Override
    public void showPhoneNumberExistError() {
        containerRoleLayout.setVisibility(View.VISIBLE);
        registerProgressBar.setVisibility(View.GONE);
        adapter.getErrorMap().get(Const.REGISTER_FIELD_PHONENUMBER).showError(ValidationError.PHONENUMBER_EXIST);
    }


    public CarModel getCarModel() {
        return carModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public DriverModel getDriverModel() {
        return driverModel;
    }

    public CustomerModel getCustomerModel() {
        return customerModel;
    }
}
