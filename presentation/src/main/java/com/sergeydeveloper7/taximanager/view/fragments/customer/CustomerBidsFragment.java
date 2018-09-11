package com.sergeydeveloper7.taximanager.view.fragments.customer;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.sergeydeveloper7.taximanager.R;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;

public class CustomerBidsFragment extends Fragment {

    private ArrayList<String>    spinnerItems = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private MaterialSpinner      customersBidsSpinner;
    private Context              context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_bids, container, false);
        addSpinnersItems();
        adapter = new ArrayAdapter<>(getActivity(), R.layout.hint_item, spinnerItems);
        adapter.setDropDownViewResource(R.layout.dropdown_hint_item);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSpinnerScrolling(view);
    }

    private void addSpinnersItems(){
        spinnerItems.add(context.getString(R.string.customers_screen_waiting_bids));
        spinnerItems.add(context.getString(R.string.customers_screen_active_bids));
        spinnerItems.add(context.getString(R.string.customers_screen_completed_bids));
    }

    private void initSpinnerScrolling(View view) {
        customersBidsSpinner = view.findViewById(R.id.customersBidsSpinner);
        customersBidsSpinner.setAdapter(adapter);
    }

}
