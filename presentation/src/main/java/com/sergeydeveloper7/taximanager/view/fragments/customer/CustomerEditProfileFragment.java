package com.sergeydeveloper7.taximanager.view.fragments.customer;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.view.activities.customer.CustomerActivity;


public class CustomerEditProfileFragment extends Fragment {

    private CustomerActivity customerActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        customerActivity = (CustomerActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_customer_edit_profile, container, false);
        initializeToolbar();
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.customer, menu);
        MenuItem save = menu.findItem(R.id.action_save);
        save.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_save) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeToolbar(){
        customerActivity.getToolbar().setNavigationIcon(R.drawable.ic_arrow_back);
        customerActivity.getToolbar().setTitle(getString(
                R.string.customer_screen_profile_edit_profile));
        customerActivity.getToolbar().setNavigationOnClickListener(v
                -> customerActivity.onBackPressed());
    }

}
