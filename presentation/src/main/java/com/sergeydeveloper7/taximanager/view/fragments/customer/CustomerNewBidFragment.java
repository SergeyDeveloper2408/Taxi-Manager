package com.sergeydeveloper7.taximanager.view.fragments.customer;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.sergeydeveloper7.data.models.general.BidModel;
import com.sergeydeveloper7.data.models.general.UserModel;
import com.sergeydeveloper7.data.models.map.response.StepResponse;
import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.presenter.customer.CustomerNewBidPresenter;
import com.sergeydeveloper7.taximanager.view.activities.customer.CustomerActivity;
import com.sergeydeveloper7.taximanager.view.adapters.googleplaces.PlaceArrayAdapter;
import com.sergeydeveloper7.taximanager.view.base.customer.CustomerNewBidView;
import com.sergeydeveloper7.taximanager.view.custom.TextInputAutoCompleteTextView;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class CustomerNewBidFragment extends Fragment implements CustomerNewBidView,
        OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, View.OnClickListener {

    private static final String     TAG = CustomerNewBidFragment.class.getSimpleName();
    private CustomerActivity        activity;
    private GoogleMap               mGoogleMap;
    private CustomerNewBidPresenter presenter;
    private GoogleApiClient         googleApiClient;
    private PlaceArrayAdapter       placeArrayAdapter;
    private String                  pointFrom;
    private String                  pointTo;
    private UserModel               user;
    private BidModel                newBid;

    //Layouts
    @BindView(R.id.newBidMainLayout)
    RelativeLayout newBidMainLayout;

    //Buttons
    @BindView(R.id.newBidButtonFindPath)
    CircularProgressButton newBidButtonFindPath;

    //MapViews
    @BindView(R.id.newBidLocationMap)
    MapView newBidLocationMap;

    //ImageViews
    @BindView(R.id.mapMock)
    ImageView mapMock;

    //TextInput EditTexts
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

    //Progress bars
    @BindView(R.id.newBidLocationProgressBar)
    ProgressBar newBidLocationProgressBar;

    @BindView(R.id.newBidDistanceProgressBar)
    ProgressBar newBidDistanceProgressBar;

    @BindView(R.id.newBidTimeProgressBar)
    ProgressBar newBidTimeProgressBar;

    @BindView(R.id.newBidCostProgressBar)
    ProgressBar newBidCostProgressBar;

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
        MapsInitializer.initialize(activity);
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Google Places API connected.");

        if(googleApiClient != null && googleApiClient.isConnected()){
            placeArrayAdapter.setGoogleApiClient(googleApiClient);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "Google Places API connection suspended.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(activity,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void fakeShowFindDirectionProcessStart() {
        newBidButtonFindPath.startAnimation();
        hideViews();
    }

    @Override
    public void fakeShowFindDirectionProcessEnd(StepResponse response) {
        newBidButtonFindPath.doneLoadingAnimation(getResources()
                .getColor(R.color.lightGray), BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_checkmark_green));

        showViews();

        newBidDistanceTextView.setText(response.getDistance().getText());
        newBidTimeTextView.setText(response.getDuration().getText());
        newBidCostTextView.setText(response.getCost().getText());

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            newBidButtonFindPath.revertAnimation(() -> newBidButtonFindPath.setText(getString(
                    R.string.customer_screen_new_bid_choose_this_route)));
        }, 1000);
    }

    @OnTextChanged(R.id.newBidPointFromTextInputEditText)
    void onPointFromEditTextChanged(CharSequence text) {
        pointFrom = text.toString();
        restoreButtonText();
    }

    @OnTextChanged(R.id.newBidPointToTextInputEditText)
    void onPointToEditTextChanged(CharSequence text) {
        pointTo = text.toString();
        restoreButtonText();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.newBidButtonFindPath){
            if(newBidButtonFindPath.getText().toString().equals(getString(
                    R.string.customer_screen_new_bid_find_path))){
                if(!isPointsFieldsEmpty()){
                    presenter.findDirectionFake(
                            newBidPointFromTextInputEditText.getText().toString(),
                            newBidPointToTextInputEditText.getText().toString()
                    );
                }
            } else if(newBidButtonFindPath.getText().toString().equals(getString(
                    R.string.customer_screen_new_bid_choose_this_route))){
                //TODO
            }
        }
    }

    private void fillNewBidParams(){

    }

    private void showViews(){
        newBidDistanceTextView.setVisibility(View.VISIBLE);
        newBidTimeTextView.setVisibility(View.VISIBLE);
        newBidCostTextView.setVisibility(View.VISIBLE);

        mapMock.setVisibility(View.VISIBLE);

        newBidDistanceProgressBar.setVisibility(View.GONE);
        newBidTimeProgressBar.setVisibility(View.GONE);
        newBidCostProgressBar.setVisibility(View.GONE);
        newBidLocationProgressBar.setVisibility(View.GONE);
    }

    private void hideViews(){
        newBidDistanceTextView.setVisibility(View.GONE);
        newBidTimeTextView.setVisibility(View.GONE);
        newBidCostTextView.setVisibility(View.GONE);

        mapMock.setVisibility(View.GONE);

        newBidDistanceProgressBar.setVisibility(View.VISIBLE);
        newBidTimeProgressBar.setVisibility(View.VISIBLE);
        newBidCostProgressBar.setVisibility(View.VISIBLE);
        newBidLocationProgressBar.setVisibility(View.VISIBLE);
    }

    private void initializeComponents(){
        activity = (CustomerActivity) getActivity();

        user = activity.getUser();

        initializeGooglePlaces();

        placeArrayAdapter = new PlaceArrayAdapter(activity,
                android.R.layout.simple_list_item_1, null);
    }

    private void initializeViews(){
        presenter = new CustomerNewBidPresenter(activity, this);

        activity.setToolbarTitle(getString(R.string.customer_screen_new_bid));
        activity.hideFloatingActionButton();

        if (newBidLocationMap != null) {
            newBidLocationMap.onCreate(null);
            newBidLocationMap.onResume();
            newBidLocationMap.getMapAsync(this);
        }

        newBidButtonFindPath.setOnClickListener(this);

        initializeTextInputAutoCompleteTextView(newBidPointFromTextInputEditText);
        initializeTextInputAutoCompleteTextView(newBidPointToTextInputEditText);

        if(googleApiClient != null && googleApiClient.isConnected()){
            placeArrayAdapter.setGoogleApiClient(googleApiClient);
        }

        initializeProgressBars();
    }

    private void initializeGooglePlaces(){
        googleApiClient = new GoogleApiClient.Builder(activity)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .build();
        googleApiClient.connect();
    }

    private boolean isPointsFieldsEmpty(){
        if(newBidPointFromTextInputEditText.getText().toString().isEmpty()){
            Snackbar.make(newBidMainLayout,
                    R.string.customer_screen_new_bid_from_empty_error, Snackbar.LENGTH_LONG)
                    .show();
            return true;
        } else if(newBidPointToTextInputEditText.getText().toString().isEmpty()){
            Snackbar.make(newBidMainLayout,
                    R.string.customer_screen_new_bid_to_empty_error, Snackbar.LENGTH_LONG)
                    .show();
            return true;
        } else {
            return false;
        }
    }

    private void restoreButtonText(){
        if(newBidButtonFindPath.getText().toString().equals(getString(
                R.string.customer_screen_new_bid_choose_this_route))) {
            newBidButtonFindPath.setText(getString(R.string.customer_screen_new_bid_find_path));
        }
    }

    private void initializeTextInputAutoCompleteTextView(
            TextInputAutoCompleteTextView autoCompleteTextView){
        autoCompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        autoCompleteTextView.setAdapter(placeArrayAdapter);
    }

    private void initializeProgressBars(){
        newBidLocationProgressBar.getIndeterminateDrawable()
                .setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        newBidDistanceProgressBar.getIndeterminateDrawable()
                .setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        newBidTimeProgressBar.getIndeterminateDrawable()
                .setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

        newBidCostProgressBar.getIndeterminateDrawable()
                .setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = placeArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(googleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = places -> {
                if (!places.getStatus().isSuccess()) {
                    Log.e(TAG, "Place query did not complete. Error: " +
                            places.getStatus().toString());
                    return;
                }
                // Selecting the first object buffer.
                final Place place = places.get(0);
                CharSequence attributions = places.getAttributions();

                System.out.println(Html.fromHtml(place.getName() + ""));
                System.out.println(Html.fromHtml(place.getAddress() + ""));
                System.out.println(Html.fromHtml(place.getId() + ""));
                System.out.println(Html.fromHtml(place.getPhoneNumber() + ""));
                System.out.println(place.getWebsiteUri() + "");
                if (attributions != null) {
                    System.out.println(Html.fromHtml(attributions.toString()));
                }
            };
}
