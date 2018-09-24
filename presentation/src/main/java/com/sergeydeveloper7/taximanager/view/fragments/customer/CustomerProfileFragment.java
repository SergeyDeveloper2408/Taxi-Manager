package com.sergeydeveloper7.taximanager.view.fragments.customer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.navigation.Navigator;
import com.sergeydeveloper7.taximanager.utils.Const;
import com.sergeydeveloper7.taximanager.view.activities.customer.CustomerActivity;
import com.sergeydeveloper7.taximanager.view.activities.main.MainActivity;

import javax.inject.Inject;

public class CustomerProfileFragment extends Fragment {

    private CustomerActivity customerActivity;

    @Inject Navigator navigator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        customerActivity = (CustomerActivity) getActivity();
        customerActivity.getApplicationComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_profile, container,
                false);
        initializeToolbar();
        return rootView;
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

    private void startEditProfileScreen(){
        navigator.startFragmentWithBackStack(customerActivity, new CustomerEditProfileFragment(),
                Const.CUSTOMER_EDIT_PROFILE_FRAGMENT_ID, R.id.customerFrame);
    }

    private void initializeToolbar(){
        customerActivity.getToolbar().setNavigationIcon(R.drawable.ic_menu);
        customerActivity.getToolbar().setTitle(getString(
                R.string.customer_screen_navigation_menu_my_profile));
        customerActivity.getToolbar().setNavigationOnClickListener(v
                -> customerActivity.openDrawer());
    }

}
