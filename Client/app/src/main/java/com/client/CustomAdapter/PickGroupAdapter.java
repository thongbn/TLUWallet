package com.client.CustomAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.client.fragment.IncomeGroupFragment;
import com.client.fragment.OutcomeGroupFragment;

/**
 * Created by ToanNguyen on 07/03/2016.
 */
public class PickGroupAdapter extends FragmentStatePagerAdapter {
    int numTabs;

    public PickGroupAdapter (FragmentManager fm, int numTabs){
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                IncomeGroupFragment income = new IncomeGroupFragment();
                return income;
            case 1:
                OutcomeGroupFragment outcome = new OutcomeGroupFragment();
                return outcome;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
