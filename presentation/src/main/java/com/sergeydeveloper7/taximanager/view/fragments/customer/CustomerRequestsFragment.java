package com.sergeydeveloper7.taximanager.view.fragments.customer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergeydeveloper7.taximanager.R;

public class CustomerRequestsFragment extends Fragment {

    private String bidsType = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_requests, container, false);

        return rootView;
    }
}
