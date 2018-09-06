package com.sergeydeveloper7.taximanager.view.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.sergeydeveloper7.data.models.UserModel;
import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.utils.Const;
import com.sergeydeveloper7.taximanager.view.fragments.main.MainScreenFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final String            TAG = MainActivity.class.getSimpleName();
    private              ActionBar         mActionBar;
    private              SharedPreferences sharedPreferences;
    private              UserModel         user;
    private              boolean           isUserLogin = false;

    @BindView(R.id.toolbarMain) Toolbar      toolbar;
    @BindView(R.id.mainAppBar)  AppBarLayout appBarLayout;
    @BindView(R.id.container)   LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeNoActionBar);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews(){
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        toolbar.setVisibility(View.GONE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        user = new Gson().fromJson(sharedPreferences.getString("user", null),
                UserModel.class);
        isUserLogin = sharedPreferences.getBoolean(Const.SHARED_PREFERENCE_IS_USER_LOGIN, false);
        checkSignIn();
    }

    private void checkSignIn() {
        if(isUserLogin){
            if(user.getRole().equals(getString(R.string.register_screen_role_customer))){
                this.navigator.startActivity(this, CustomerActivity.class);
                finish();
            } else {

            }
        } else {
            this.navigator.startFragmentNoBackStack(this, new MainScreenFragment(),
                    Const.MAIN_SCREEN_FRAGMENT_ID, R.id.main_frame);
        }
    }
}
