package com.sergeydeveloper7.taximanager.view.fragments.customer;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.view.activities.customer.CustomerActivity;
import com.sergeydeveloper7.taximanager.view.adapters.CustomerBidsAdapter;
import com.sergeydeveloper7.taximanager.view.base.customer.CustomerBidsView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;

public class CustomerBidsFragment extends Fragment implements CustomerBidsView {

    private ArrayList<String>    spinnerItems = new ArrayList<>();
    private ArrayAdapter<String> spinnersAdapter;
    private CustomerActivity     customerActivity;
    private CustomerBidsAdapter  recyclersAdapter;
    private ArrayList<String>    bidsList = new ArrayList<>();

    //RecyclerViews
    @BindView(R.id.customersBidsRecyclerView)
    RecyclerView customersBidsRecyclerView;

    //Spinners
    @BindView(R.id.customersBidsSpinner)
    MaterialSpinner customersBidsSpinner;

    //TextView
    @BindView(R.id.titleNoBids)
    TextView titleNoBids;

    //ProgressBars
    @BindView(R.id.customersBidsProgressBar)
    ProgressBar customersBidsProgressBar;

    //Layouts
    @BindView(R.id.customerBidsMainLayout)
    RelativeLayout customerBidsMainLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerActivity = (CustomerActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_bids, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();
        setBidsRecyclerView();
    }

    @Override
    public void showFindBidsProcessStart() {
        customersBidsProgressBar.setVisibility(View.VISIBLE);
        customersBidsRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showFindBidsProcessEnd() {
        customersBidsProgressBar.setVisibility(View.GONE);
        customersBidsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFindBidsProcessError(Throwable throwable) {
        customersBidsProgressBar.setVisibility(View.GONE);
        customersBidsRecyclerView.setVisibility(View.VISIBLE);
        Snackbar.make(customerBidsMainLayout, getString(R.string.process_error),
                Snackbar.LENGTH_LONG).show();
    }

    private void initializeViews(){
        customerActivity.setToolbarTitle(getString(R.string.customer_screen_bids));
        customersBidsProgressBar.getIndeterminateDrawable()
                .setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        initializeSpinner();
    }

    private void initializeSpinner(){

        addSpinnersItems();

        spinnersAdapter = new ArrayAdapter<>(getActivity(), R.layout.hint_item, spinnerItems);
        spinnersAdapter.setDropDownViewResource(R.layout.dropdown_hint_item);
        customersBidsSpinner.setAdapter(spinnersAdapter);

        customersBidsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(id != 0){
                    //TODO presenter.findBids(spinnerItems.get(position/id))
                } else {
                    titleNoBids.setText(getString(R.string.customer_screen_no_bids));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setBidsRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(customerActivity, LinearLayoutManager.VERTICAL, false);
        customersBidsRecyclerView.setHasFixedSize(false);
        customersBidsRecyclerView.setLayoutManager(layoutManager);
        customersBidsRecyclerView.setItemViewCacheSize(50);
        customersBidsRecyclerView.setDrawingCacheEnabled(true);
        RecyclerView.ItemAnimator animator = customersBidsRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        recyclersAdapter = new CustomerBidsAdapter(customerActivity, bidsList);
        customersBidsRecyclerView.setAdapter(recyclersAdapter);
    }

    private void addSpinnersItems(){
        spinnerItems.add(getString(R.string.customer_screen_waiting_bids));
        spinnerItems.add(getString(R.string.customer_screen_active_bids));
        spinnerItems.add(getString(R.string.customer_screen_completed_bids));
        spinnerItems.add(getString(R.string.customer_screen_canceled_bids));
    }
}
