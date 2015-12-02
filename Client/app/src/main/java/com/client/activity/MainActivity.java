package com.client.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.client.R;
import com.client.database.User;
import com.client.fragment.DatabaseFragment;
import com.client.fragment.DealDetailsFragment;
import com.client.fragment.GroupFragment;
import com.client.fragment.HelpFragment;
import com.client.fragment.SettingsFragment;
import com.client.fragment.WalletFragment;
import com.client.database.AddData;
import com.client.ultils.UtilsDevice;
import com.client.ultils.UtilsMiscellaneous;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DrawerLayout mDrawerLayout;
    ImageButton FAB;
    User user;
    TextView headerUserEmail;

    private LinearLayout mNavDrawerEntriesRootView;
    private RelativeLayout mFrameLayout_AccountView;
    private FrameLayout mFrameLayout_Wallet, mFrameLayout_Group, mFrameLayout_Database, mFrameLayout_Help, mFrameLayout_Settings, mFrameLayout_DealDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FAB = (ImageButton) findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplication(), AddData.class);
                intent.putExtra("update", false);
                startActivity(intent);
            }
        });

        initialise();
    }

    private void initialise(){

        //Toolbar
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Layout resources
        mFrameLayout_AccountView = (RelativeLayout) findViewById(R.id.navigation_drawer_account_view);

        mNavDrawerEntriesRootView = (LinearLayout) findViewById(R.id.navigation_drawer_linearLayout_entries_root_view);

        mFrameLayout_Wallet = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_wallet);

        mFrameLayout_Group = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_group);

        mFrameLayout_Database = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_database);

        mFrameLayout_Help = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_help);

        mFrameLayout_Settings = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_settings);

        mFrameLayout_DealDetails = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_deal_details);


        // Navigation Drawer

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity);

        final ScrimInsetsFrameLayout mScrimInsetsFrameLayout = (ScrimInsetsFrameLayout) findViewById(R.id.main_activity_navigation_drawer_rootLayout);

        final ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_opened, R.string.navigation_drawer_closed){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset){

                //Disables the burger/arrow animation by defaut
                super.onDrawerSlide(drawerView, 0);
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        mActionBarDrawerToggle.syncState();

        // Header User Email

        user = new User();
        headerUserEmail = (TextView) findViewById(R.id.navigation_drawer_account_information_display_name);
//        final String emailHeader = user.getEmail().toString();
        headerUserEmail.setText("Xin chào.........");

        // Navigation Drawer layout width

        final int possibleMinDrawerWidth = UtilsDevice.getScreenWidth(this) - UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);

        final int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

        mScrimInsetsFrameLayout.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);

        // Nav Drawer item click listener
        mFrameLayout_AccountView.setOnClickListener(this);
        mFrameLayout_Wallet.setOnClickListener(this);
        mFrameLayout_DealDetails.setOnClickListener(this);
        mFrameLayout_Group.setOnClickListener(this);
        mFrameLayout_Database.setOnClickListener(this);
        mFrameLayout_Help.setOnClickListener(this);
        mFrameLayout_Settings.setOnClickListener(this);

        // set the first item as selected for the first time

        mFrameLayout_Wallet.setSelected(true);

        // Create the first fragment to be shown

        Fragment dealDetailsFragment = new DealDetailsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerView, dealDetailsFragment, null);
        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View view){
        if (view == mFrameLayout_AccountView)
        {
            mDrawerLayout.closeDrawer(GravityCompat.START);

            // SignUp/SignIn/Profile
            startActivity(new Intent(view.getContext(), AccountActivity.class));
        }

        else{
            if (!view.isSelected())
            {
                onRowPressed((FrameLayout) view);

                if (view == mFrameLayout_Wallet)
                {

                    view.setSelected(true);

                    Fragment walletFragment = new WalletFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, walletFragment, null);
                    fragmentTransaction.commit();
                }
                else if (view == mFrameLayout_DealDetails){
                    view.setSelected(true);

                    Fragment dealDetailsFragment = new DealDetailsFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, dealDetailsFragment, null);
                    fragmentTransaction.commit();
                }
                else if (view == mFrameLayout_Group)
                {

                    view.setSelected(true);

                    Fragment groupFragment = new GroupFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, groupFragment, null);
                    fragmentTransaction.commit();
                }
                else if (view == mFrameLayout_Database)
                {

                    view.setSelected(true);

                    Fragment databaseFragment = new DatabaseFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, databaseFragment, null);
                    fragmentTransaction.commit();
                }
                else if (view == mFrameLayout_Help)
                {

                    view.setSelected(true);

                    Fragment helpFragment = new HelpFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, helpFragment, null);
                    fragmentTransaction.commit();
                }
                else if (view == mFrameLayout_Settings){

                    view.setSelected(true);

                    Fragment settingsFragment = new SettingsFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, settingsFragment, null);
                    fragmentTransaction.commit();
                }
            }
            else
            {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        }
    }

    private void onRowPressed(@NonNull final FrameLayout pressedRow)
    {
        if (pressedRow.getTag() != getResources().getString(R.string.tag_nav_drawer_special_entry))
        {
            for (int i = 0; i < mNavDrawerEntriesRootView.getChildCount(); i++)
            {
                final View currentView = mNavDrawerEntriesRootView.getChildAt(i);

                final boolean currentViewIsMainEntry = currentView.getTag() ==
                        getResources().getString(R.string.tag_nav_drawer_main_entry);

                if (currentViewIsMainEntry)
                {
                    currentView.setSelected(currentView == pressedRow);
                }
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
}
