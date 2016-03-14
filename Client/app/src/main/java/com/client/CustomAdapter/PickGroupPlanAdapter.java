package com.client.CustomAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.client.fragment.IncomeGroupPlanFragment;
import com.client.fragment.OutcomeGroupPlanFragment;

/**
 * Created by ToanNguyen on 14/03/2016.
 */
public class PickGroupPlanAdapter extends FragmentStatePagerAdapter{
    int numTabs;

    public PickGroupPlanAdapter (FragmentManager fm, int numTabs){
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                IncomeGroupPlanFragment income = new IncomeGroupPlanFragment();
                return income;
            case 1:
                OutcomeGroupPlanFragment outcome = new OutcomeGroupPlanFragment();
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
