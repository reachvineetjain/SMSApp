package com.nehvin.smsapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    Context mContext;
    public SectionsPagerAdapter(FragmentManager fm, Context ctx) {

        super(fm);
        mContext = ctx;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new InboxFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Inbox";
        }
        return null;
    }
}
