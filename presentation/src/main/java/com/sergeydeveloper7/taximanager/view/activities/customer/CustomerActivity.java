package com.sergeydeveloper7.taximanager.view.activities.customer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sergeydeveloper7.data.models.UserModel;
import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.utils.Const;
import com.sergeydeveloper7.taximanager.view.activities.base.BaseActivity;
import com.sergeydeveloper7.taximanager.view.activities.main.MainActivity;
import com.sergeydeveloper7.taximanager.view.activities.preferences.SettingsActivity;
import com.sergeydeveloper7.taximanager.view.fragments.customer.CustomerBidsFragment;
import com.sergeydeveloper7.taximanager.view.fragments.customer.CustomerProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView              navHeaderMainTitleTextView;
    private TextView              navHeaderSecondaryTitleTextView;
    private UserModel             userModel;
    private SharedPreferences     sharedPreferences;
    private ActionBarDrawerToggle toggle;

    @BindView(R.id.toolbar)       Toolbar toolbar;
    @BindView(R.id.fab)           FloatingActionButton fab;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)      NavigationView navigationView;
    @BindView(R.id.titleTodo)     TextView titleTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initViews();
        setViewsProperties();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.navigationCustomerMyProfile) {
            startProfileScreen();
        } else if (id == R.id.navigationCustomerBids) {
            startBidsScreen();
        } else if (id == R.id.navigationCustomerTools) {
            startToolsScreen();
        } else if (id == R.id.navigationCustomerLogout) {
            logout();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    private void initViews(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userModel = new Gson().fromJson(sharedPreferences.getString("user", null),
                UserModel.class);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        navHeaderMainTitleTextView = navigationView.getHeaderView(0)
                .findViewById(R.id.navHeaderMainTitleTextView);
        navHeaderSecondaryTitleTextView = navigationView.getHeaderView(0)
                .findViewById(R.id.navHeaderSecondaryTitleTextView);
    }

    private void setViewsProperties(){
        fab.setOnClickListener((View view) -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navHeaderMainTitleTextView.setText(userModel.getUserName());
        navHeaderSecondaryTitleTextView.setText(userModel.getEmail());
        fab.setVisibility(View.GONE);
    }

    private void startProfileScreen(){
        fab.setVisibility(View.GONE);
        titleTodo.setVisibility(View.GONE);
        toolbar.setTitle(getString(R.string.customer_screen_navigation_menu_my_profile));
        this.navigator.startFragmentNoBackStack(this, new CustomerProfileFragment(),
                Const.CUSTOMER_PROFILE_FRAGMENT_ID, R.id.customerFrame);
    }

    private void startToolsScreen(){
        fab.setVisibility(View.GONE);
        titleTodo.setVisibility(View.GONE);
        toolbar.setTitle(getString(R.string.customer_screen_navigation_menu_tools));
        this.navigator.startActivity(this, SettingsActivity.class);
    }

    private void startBidsScreen(){
        fab.setVisibility(View.VISIBLE);
        titleTodo.setVisibility(View.GONE);
        toolbar.setTitle(getString(R.string.customer_screen_bids));
        this.navigator.startFragmentNoBackStack(this, new CustomerBidsFragment(),
                Const.CUSTOMER_BIDS_FRAGMENT_ID, R.id.customerFrame);
    }

    private void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putBoolean(Const.SHARED_PREFERENCE_IS_USER_LOGIN, false);
        editor.apply();
        this.navigator.startActivity(this, MainActivity.class);
        finish();
    }
}
