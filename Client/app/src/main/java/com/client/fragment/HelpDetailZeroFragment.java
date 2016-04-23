package com.client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.client.CustomAdapter.HeplAdapter;
import com.client.R;

import java.util.List;
import java.util.Vector;

import me.relex.circleindicator.CircleIndicator;


public class HelpDetailZeroFragment extends FragmentActivity {

  ViewPager viewPager;

  public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.help_detail_zero_fragment);


    this.setTitle(R.string.nav_drawer_item_help);

    List<Fragment> fragments = new Vector<Fragment>();
    fragments.add(Fragment.instantiate(this, HelpDetailOneFragment.class.getName()));
    fragments.add(Fragment.instantiate(this, HelpDetailTwoFragment.class.getName()));
    fragments.add(Fragment.instantiate(this, HelpDetailThreeFragment.class.getName()));
    HeplAdapter mPagerAdapter  = new HeplAdapter(super.getSupportFragmentManager(), fragments);
    //
    viewPager = (ViewPager) findViewById(R.id.pager);
    viewPager.setAdapter(mPagerAdapter);
    CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
    indicator.setViewPager(viewPager);

    ImageButton turn_back = (ImageButton) findViewById(R.id.turn_back);
    turn_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });

  }

  @Override
  public void onBackPressed() {

    super.onBackPressed();
    finish();

  }
}
