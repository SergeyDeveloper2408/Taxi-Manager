package com.sergeydeveloper7.taximanager.view.fragments.main;

import android.app.Fragment;
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
import com.sergeydeveloper7.data.models.general.CarModel;
import com.sergeydeveloper7.data.models.general.CustomerModel;
import com.sergeydeveloper7.data.models.general.DriverModel;
import com.sergeydeveloper7.data.models.general.UserModel;
import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.navigation.Navigator;
import com.sergeydeveloper7.taximanager.presenter.main.RegisterPresenter;
import com.sergeydeveloper7.taximanager.utils.Const;
import com.sergeydeveloper7.taximanager.view.activities.customer.CustomerActivity;
import com.sergeydeveloper7.taximanager.view.activities.driver.DriverActivity;
import com.sergeydeveloper7.taximanager.view.activities.main.MainActivity;
import com.sergeydeveloper7.taximanager.view.adapters.RegisterAdapter;
import com.sergeydeveloper7.taximanager.view.basic.main.RegisterView;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterFragment extends Fragment implements RegisterView {

    private static final String TAG = RegisterFragment.class.getSimpleName();
    private MainActivity        mainActivity;
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
            Const.REGISTER_BUTTON));

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
        mainActivity = (MainActivity)getActivity();
        mainActivity.getApplicationComponent().inject(this);
        presenter = new RegisterPresenter(this, mainActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLeadsRecyclerView();
    }

    public void validateUser(){
        presenter.validateUser(userModel);
    }

    public void showCarFillingInformation(){
        index.clear();
        index.addAll(driverIndex);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.customerRoleRelativeLayout)
    void setRoleCustomer() {
        userModel = new UserModel();
        userModel.setRole(getString(R.string.register_screen_role_customer));
        customerModel = new CustomerModel();
        setViewsVisibility();
    }

    @OnClick(R.id.driverRoleRelativeLayout)
    void setRoleDriver() {
        userModel = new UserModel();
        userModel.setRole(getString(R.string.register_screen_role_driver));
        driverModel = new DriverModel();
        carModel = new CarModel();
        index.set(index.size()-1, Const.REGISTER_BUTTON_NEXT_STEP);
        setViewsVisibility();
    }

    @Override
    public void showLoadingProcessStart() {
        showLoading();
    }

    @Override
    public void showLoadingProcessEnd() {
        if(userModel.getRole().equals(getString(R.string.register_screen_role_customer)))
            this.navigator.startActivity(mainActivity, CustomerActivity.class);
        else
            this.navigator.startActivity(mainActivity, DriverActivity.class);

    }

    @Override
    public void showRegistrationProcessError(Throwable throwable) {
        hideLoading();
        Snackbar.make(registerRelativeLayout, getString(R.string.register_screen_error), Snackbar.LENGTH_LONG)
                .show();
        throwable.printStackTrace();
    }

    @Override
    public void showEmailExistError() {
        hideLoading();
        adapter.getErrorMap().get(Const.REGISTER_FIELD_EMAIL).showError(ValidationError.EMAIL_EXIST);
    }

    @Override
    public void showPhoneNumberExistError() {
        hideLoading();
        adapter.getErrorMap().get(Const.REGISTER_FIELD_PHONENUMBER).showError(ValidationError.PHONENUMBER_EXIST);
    }

    @Override
    public void setValidation(boolean validation) {
        if(validation){
            if(userModel.getRole().equals(getString(R.string.register_screen_role_customer))){
                presenter.registerCustomer(userModel, customerModel);
            } else
                presenter.registerDriver(userModel, driverModel, carModel);
        }
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

    private void setLeadsRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false);
        registerFieldsRecyclerView.setHasFixedSize(false);
        registerFieldsRecyclerView.setLayoutManager(layoutManager);
        registerFieldsRecyclerView.setItemViewCacheSize(50);
        registerFieldsRecyclerView.setDrawingCacheEnabled(true);
        RecyclerView.ItemAnimator animator = registerFieldsRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        adapter = new RegisterAdapter(mainActivity, index, this);
        registerFieldsRecyclerView.setAdapter(adapter);
    }

    private void setViewsVisibility(){
        chooseYourRoleTextView.setText(getString(R.string.register_screen_fill_information));
        customerRoleRelativeLayout.setVisibility(View.GONE);
        driverRoleRelativeLayout.setVisibility(View.GONE);
        registerFieldsRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showLoading(){
        containerRoleLayout.setVisibility(View.GONE);
        registerProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        containerRoleLayout.setVisibility(View.VISIBLE);
        registerProgressBar.setVisibility(View.GONE);
    }
}
