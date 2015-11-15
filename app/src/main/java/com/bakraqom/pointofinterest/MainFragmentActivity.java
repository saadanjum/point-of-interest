package com.bakraqom.pointofinterest;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.bakraqom.pointofinterest.adapters.TabsPagerAdapter;
import com.bakraqom.pointofinterest.fragments.ListPOIFragment;

public class MainFragmentActivity extends FragmentActivity implements ActionBar.TabListener {


    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    int currentPage = 0;
    // Tab titles
    private String[] tabs = { "Add POI", "View POIs" };

    public static Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_loader);

        this.context = this;

        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);

        SigninActivity.userId = sharedPref.getString(getString(R.string.shared_pref_user_id), getString(R.string.shared_pref_user_id));
        SigninActivity.userName = sharedPref.getString(getString(R.string.shared_pref_user_id), getString(R.string.shared_pref_user_name));
        SigninActivity.userEmail = sharedPref.getString(getString(R.string.shared_pref_user_email), getString(R.string.shared_pref_user_email));

        if (SigninActivity.userId == getString(R.string.shared_pref_user_id)){
            Intent signin = new Intent(this, SigninActivity.class);
            startActivity(signin);
        }

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1)
                    ListPOIFragment.refreshPage();
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        Log.i("tabPsition", String.valueOf(tab.getPosition()));
        currentPage = tab.getPosition();
        viewPager.setCurrentItem(currentPage);

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
