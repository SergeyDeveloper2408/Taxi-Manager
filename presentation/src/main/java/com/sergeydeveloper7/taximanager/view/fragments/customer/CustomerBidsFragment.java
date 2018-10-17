package com.sergeydeveloper7.taximanager.view.fragments.customer;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
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

import com.sergeydeveloper7.data.models.general.BidModel;
import com.sergeydeveloper7.data.models.general.UserModel;
import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.presenter.customer.CustomerBidsPresenter;
import com.sergeydeveloper7.taximanager.view.activities.customer.CustomerActivity;
import com.sergeydeveloper7.taximanager.view.adapters.CustomerBidsAdapter;
import com.sergeydeveloper7.taximanager.view.base.customer.CustomerBidsView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;

public class CustomerBidsFragment extends Fragment implements CustomerBidsView {

    private ArrayList<String>     spinnerItems;
    private ArrayAdapter<String>  spinnersAdapter;
    private CustomerActivity      activity;
    private CustomerBidsAdapter   recyclersAdapter;
    private ArrayList<BidModel>   bidsList = new ArrayList<>();
    private CustomerBidsPresenter presenter;
    private UserModel             user;

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
        initializeComponents();
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
        titleNoBids.setVisibility(View.GONE);
        customersBidsSpinner.setVisibility(View.GONE);
        customersBidsRecyclerView.setVisibility(View.GONE);
        customersBidsProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFindBidsProcessEnd(ArrayList<BidModel> bids) {
        if(bids.isEmpty()){
            customersBidsProgressBar.setVisibility(View.GONE);
            customersBidsRecyclerView.setVisibility(View.GONE);
            customersBidsSpinner.setVisibility(View.VISIBLE);
            showNoBidsText();
        } else {
            bidsList.clear();
            bidsList.addAll(bids);
            recyclersAdapter.notifyDataSetChanged();
            customersBidsProgressBar.setVisibility(View.GONE);
            customersBidsSpinner.setVisibility(View.VISIBLE);
            customersBidsRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showFindBidsProcessError(Throwable throwable) {
        customersBidsProgressBar.setVisibility(View.GONE);
        titleNoBids.setVisibility(View.VISIBLE);
        customersBidsSpinner.setVisibility(View.VISIBLE);
        customersBidsRecyclerView.setVisibility(View.VISIBLE);
        Snackbar.make(customerBidsMainLayout, getString(R.string.process_error),
                Snackbar.LENGTH_LONG).show();
    }

    private void showNoBidsText(){
        titleNoBids.setVisibility(View.VISIBLE);
        switch (customersBidsSpinner.getSelectedItemPosition()){
            case 1:
                titleNoBids.setText(getString(R.string.customer_screen_title_no_waiting_bids));
                break;
            case 2:
                titleNoBids.setText(getString(R.string.customer_screen_title_no_active_bids));
                break;
            case 3:
                titleNoBids.setText(getString(R.string.customer_screen_title_no_completed_bids));
                break;
            case 4:
                titleNoBids.setText(getString(R.string.customer_screen_title_no_canceled_bids));
                break;
        }
    }

    private void initializeComponents(){
        activity = (CustomerActivity) getActivity();
        presenter = new CustomerBidsPresenter(activity, this);
        user = activity.getUser();
    }

    private void initializeViews(){
        activity.setToolbarTitle(getString(R.string.customer_screen_bids));
        activity.showFloatingActionButton();

        customersBidsProgressBar.getIndeterminateDrawable().setColorFilter(Color.GRAY,
                PorterDuff.Mode.SRC_IN);
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
                    presenter.getBids(user.getId(), spinnerItems.get(position));
                } else {
                    titleNoBids.setText(getString(R.string.customer_screen_no_bids_choose));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    private void setBidsRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        customersBidsRecyclerView.setHasFixedSize(false);
        customersBidsRecyclerView.setLayoutManager(layoutManager);
        customersBidsRecyclerView.setItemViewCacheSize(50);
        customersBidsRecyclerView.setDrawingCacheEnabled(true);
        RecyclerView.ItemAnimator animator = customersBidsRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        recyclersAdapter = new CustomerBidsAdapter(activity, bidsList);
        customersBidsRecyclerView.setAdapter(recyclersAdapter);
    }

    private void addSpinnersItems(){
        spinnerItems = new ArrayList<>();
        spinnerItems.add(getString(R.string.customer_screen_waiting_bids));
        spinnerItems.add(getString(R.string.customer_screen_active_bids));
        spinnerItems.add(getString(R.string.customer_screen_completed_bids));
        spinnerItems.add(getString(R.string.customer_screen_canceled_bids));
    }
}
