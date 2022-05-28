package com.college.portal.clubs.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ClubsPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> mFragment;
    private final ArrayList<String> mTitle;

    public ClubsPagerAdapter(FragmentManager fm) {
        super(fm);
        this.mFragment = new ArrayList<>();
        this.mTitle = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragment.add(fragment);
        mTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
