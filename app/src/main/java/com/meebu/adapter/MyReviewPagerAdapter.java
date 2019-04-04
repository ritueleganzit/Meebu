package com.meebu.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by eleganz on 18/3/19.
 */

public class MyReviewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<String> tabTitle=new ArrayList<>();
    ArrayList<Fragment> fragments=new ArrayList<>();

    public MyReviewPagerAdapter(FragmentManager fm) {
        super(fm);


    }

    public void addFragments(Fragment fragment,String title)
    {
        fragments.add(fragment);
        tabTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle.get(position);
    }


    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
