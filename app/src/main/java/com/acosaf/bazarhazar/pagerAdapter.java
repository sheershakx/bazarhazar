package com.acosaf.bazarhazar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class pagerAdapter extends FragmentPagerAdapter {

    private int tabsize;


    public pagerAdapter(FragmentManager fm, int tabsize) {
        super(fm);
        this.tabsize = tabsize;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return new allFragment();

            case 1:
                return new categFragment();

            case 2:
                return new fabFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabsize;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
