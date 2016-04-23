package com.client.CustomAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.client.fragment.HelpDetailOneFragment;
import com.client.fragment.HelpDetailTwoFragment;

import java.util.List;

/**
 * Created by ToanNguyen on 22/04/2016.
 */
public class HeplAdapter extends FragmentPagerAdapter {

  private List<Fragment> fragments;
  /**
   * @param fm
   * @param fragments
   */
  public HeplAdapter(FragmentManager fm, List<Fragment> fragments) {
    super(fm);
    this.fragments = fragments;
  }
  /* (non-Javadoc)
   * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
   */
  @Override
  public Fragment getItem(int position) {
    return this.fragments.get(position);
  }

  /* (non-Javadoc)
   * @see android.support.v4.view.PagerAdapter#getCount()
   */
  @Override
  public int getCount() {
    return this.fragments.size();
  }

}
