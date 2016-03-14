package com.client.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import com.client.CustomAdapter.PickGroupPlanAdapter;
import com.client.R;

/**
 * Created by ToanNguyen on 14/03/2016.
 */
public class PickGroupPlanActivity extends AppCompatActivity{
    private ImageButton turn_back;

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_group);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsPickGroup);
        tabLayout.addTab(tabLayout.newTab().setText("Thu nhập"));
        tabLayout.addTab(tabLayout.newTab().setText("Chi tiêu"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final PickGroupPlanAdapter adapter = new PickGroupPlanAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        turn_back = (ImageButton) findViewById(R.id.turn_back);
        turn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}
