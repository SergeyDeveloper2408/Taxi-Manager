package com.sergeydeveloper7.taximanager.view.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.sergeydeveloper7.taximanager.R;
import com.sergeydeveloper7.taximanager.utils.Const;
import com.sergeydeveloper7.taximanager.view.fragments.MainScreenFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.toolbarMain) Toolbar      toolbar;
    @BindView(R.id.mainAppBar)  AppBarLayout appBarLayout;
    @BindView(R.id.container)   LinearLayout container;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeNoActionBar);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        toolbar.setVisibility(View.GONE);
        this.navigator.startFragmentNoBackStack(this, new MainScreenFragment(), Const.MAIN_SCREEN_FRAGMENT_ID);
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
}
