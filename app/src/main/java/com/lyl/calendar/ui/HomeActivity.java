package com.lyl.calendar.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lyl.calendar.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.lyl.calendar.HomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        mFragmentManager = getSupportFragmentManager();

        initTitle();
        initView();
        showCurrentFragment();
    }

    private void initTitle() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("123");
        setSupportActionBar(mToolbar);
    }

    private void initView() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void showCurrentFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.content_home, new CurrentFragment(), CurrentFragment.TAG);
        transaction.setCustomAnimations(
                R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_right_exit);
        if (isFinishing())
            return;
        transaction.commit();
    }

    private void showHistoryFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.content_home, new HistoryFragment(), HistoryFragment.TAG);
        transaction.setCustomAnimations(
                R.anim.fragment_slide_right_enter,
                R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_left_enter,
                R.anim.fragment_slide_right_exit);
        if (isFinishing())
            return;
        transaction.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.current:
                Fragment current = mFragmentManager.findFragmentByTag(CurrentFragment.TAG);
                if (!(current != null && current.isAdded())) {
                    showCurrentFragment();
                }
                break;
            case R.id.history:
                Fragment history = mFragmentManager.findFragmentByTag(HistoryFragment.TAG);
                if (!(history != null && history.isAdded())) {
                    showHistoryFragment();
                }
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
