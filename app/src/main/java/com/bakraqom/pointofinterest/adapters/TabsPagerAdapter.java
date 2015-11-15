package com.bakraqom.pointofinterest.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bakraqom.pointofinterest.fragments.ListPOIFragment;
import com.bakraqom.pointofinterest.fragments.MainFragment;

/**
 * Created by saad on 10/24/15.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter{

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                // Top Rated fragment activity
                return new MainFragment();
            case 1:
                return new ListPOIFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
