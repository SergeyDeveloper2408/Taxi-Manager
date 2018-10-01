package com.sergeydeveloper7.taximanager.view.fragments.customer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sergeydeveloper7.data.models.general.UserModel;
import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.navigation.Navigator;
import com.sergeydeveloper7.taximanager.utils.Const;
import com.sergeydeveloper7.taximanager.view.activities.customer.CustomerActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerProfileFragment extends Fragment {

    private CustomerActivity  customerActivity;
    private UserModel         user;
    private SharedPreferences sharedPreferences;

    @Inject Navigator navigator;

    @BindView(R.id.customerProfileName)  TextView customerProfileName;
    @BindView(R.id.customerProfileEmail) TextView customerProfileEmail;
    @BindView(R.id.customerProfilePhone) TextView customerProfilePhone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initializeComponents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_profile, container,
                false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.customer, menu);
        menu.findItem(R.id.action_edit).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_edit) {
            startEditProfileScreen();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeViews(){

        //TODO Need to refresh when use back action
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(customerActivity);
        user = new Gson().fromJson(sharedPreferences.getString("user", null),
                UserModel.class);

        customerActivity.setToolbarTitle(getString(
                R.string.customer_screen_navigation_menu_my_profile));
        customerActivity.setNavigationActionMenu();

        customerProfileName.setText(!TextUtils.isEmpty(user.getUserName())
                ? user.getUserName() : getString(R.string.no_data_error));
        customerProfileEmail.setText(!TextUtils.isEmpty(user.getEmail())
                ? user.getEmail() : getString(R.string.no_data_error));
        customerProfilePhone.setText(!TextUtils.isEmpty(user.getPhoneNumber())
                ? user.getPhoneNumber() : getString(R.string.no_data_error));

    }

    private void initializeComponents(){
        customerActivity = (CustomerActivity) getActivity();
        customerActivity.getApplicationComponent().inject(this);
    }

    private void startEditProfileScreen(){
        navigator.startFragmentWithBackStack(customerActivity, new CustomerEditProfileFragment(),
                Const.CUSTOMER_EDIT_PROFILE_FRAGMENT_ID, R.id.customerFrame);
    }



}
