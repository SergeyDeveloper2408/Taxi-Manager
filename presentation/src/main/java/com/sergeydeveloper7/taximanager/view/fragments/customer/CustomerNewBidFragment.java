package com.sergeydeveloper7.taximanager.view.fragments.customer;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.sergeydeveloper7.data.models.map.response.StepResponse;
import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.presenter.customer.CustomerNewBidPresenter;
import com.sergeydeveloper7.taximanager.view.activities.customer.CustomerActivity;
import com.sergeydeveloper7.taximanager.view.adapters.googlePlaces.PlaceArrayAdapter;
import com.sergeydeveloper7.taximanager.view.basic.customer.CustomerNewBidView;
import com.sergeydeveloper7.taximanager.view.custom.TextInputAutoCompleteTextView;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerNewBidFragment extends Fragment implements CustomerNewBidView,
        OnMapReadyCallback {

    private CustomerActivity        customerActivity;
    private GoogleMap               mGoogleMap;
    private CustomerNewBidPresenter presenter;
    private GoogleApiClient         googleApiClient;
    private PlaceArrayAdapter       placeArrayAdapter;

    @BindView(R.id.newBidButtonFindPath)
    CircularProgressButton newBidButtonFindPath;

    @BindView(R.id.eventLocationMap)
    MapView eventLocationMap;

    //Text Input EditTexts
    @BindView(R.id.newBidPointFromTextInputEditText)
    TextInputAutoCompleteTextView newBidPointFromTextInputEditText;

    @BindView(R.id.newBidPointToTextInputEditText)
    TextInputAutoCompleteTextView newBidPointToTextInputEditText;

    //TextViews
    @BindView(R.id.newBidDistanceTextView)
    TextView newBidDistanceTextView;

    @BindView(R.id.newBidTimeTextView)
    TextView newBidTimeTextView;

    @BindView(R.id.newBidCostTextView)
    TextView newBidCostTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeComponents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_new_bid, container,
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
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(customerActivity);
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void fakeShowFindDirectionProcessStart() {
        newBidButtonFindPath.startAnimation();
    }

    @Override
    public void fakeShowFindDirectionProcessEnd(StepResponse response) {
        newBidButtonFindPath.doneLoadingAnimation(getResources()
                .getColor(R.color.lightGray), BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_checkmark_green));

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            newBidButtonFindPath.revertAnimation();
        }, 1000);
    }

    private void initializeComponents(){
        customerActivity = (CustomerActivity) getActivity();
        placeArrayAdapter = new PlaceArrayAdapter(customerActivity,
                android.R.layout.simple_list_item_1, null);
    }

    private void initializeViews(){
        presenter = new CustomerNewBidPresenter(customerActivity, this);
        customerActivity.setToolbarTitle(getString(R.string.customer_screen_new_bid));

        if (eventLocationMap != null) {
            eventLocationMap.onCreate(null);
            eventLocationMap.onResume();
            eventLocationMap.getMapAsync(this);
        }

        newBidButtonFindPath.setOnClickListener(v -> presenter.findDirectionFake(
                newBidPointFromTextInputEditText.getText().toString(),
                newBidPointToTextInputEditText.getText().toString()
        ));
    }

}
