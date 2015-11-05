package com.client;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.client.account.AccountActivity;
import com.client.activity.DatabaseFragment;
import com.client.activity.GroupFragment;
import com.client.activity.HelpFragment;
import com.client.activity.SettingsFragment;
import com.client.activity.WalletFragment;
import com.client.customViews.ScrimInsetsFrameLayout;
import com.client.ultils.UtilsDevice;
import com.client.ultils.UtilsMiscellaneous;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DrawerLayout mDrawerLayout;
    Context context;
    private LinearLayout mNavDrawerEntriesRootView;
    private PercentRelativeLayout mFrameLayout_AccountView;
    private FrameLayout mFrameLayout_Wallet, mFrameLayout_Group, mFrameLayout_Database, mFrameLayout_Help, mFrameLayout_Settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();
    }

    private void initialise(){

        //Toolbar
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        //Layout resources
        mFrameLayout_AccountView = (PercentRelativeLayout) findViewById(R.id.navigation_drawer_account_view);

        mNavDrawerEntriesRootView = (LinearLayout) findViewById(R.id.navigation_drawer_linearLayout_entries_root_view);

        mFrameLayout_Wallet = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_wallet);

        mFrameLayout_Group = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_group);

        mFrameLayout_Database = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_database);

        mFrameLayout_Help = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_help);

        mFrameLayout_Settings = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_settings);

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

        // Navigation Drawer layout width

        final int possibleMinDrawerWidth = UtilsDevice.getScreenWidth(this) - UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);

        final int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

        mScrimInsetsFrameLayout.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);

        // Nav Drawer item click listener
        mFrameLayout_AccountView.setOnClickListener(this);
        mFrameLayout_Wallet.setOnClickListener(this);
        mFrameLayout_Group.setOnClickListener(this);
        mFrameLayout_Database.setOnClickListener(this);
        mFrameLayout_Help.setOnClickListener(this);
        mFrameLayout_Settings.setOnClickListener(this);

        // set the first item as selected for the first time

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.toolbar_title_wallet);
        }

        mFrameLayout_Wallet.setSelected(true);

        // Create the first fragment to be shown

        Fragment walletFragment = new WalletFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerView, walletFragment, null);
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
                    if (getSupportActionBar() != null)
                    {
                        getSupportActionBar().setTitle(getString(R.string.toolbar_title_wallet));
                    }

                    view.setSelected(true);

                    Fragment walletFragment = new WalletFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, walletFragment, null);
                    fragmentTransaction.commit();
                }
                else if (view == mFrameLayout_Group)
                {
                    if (getSupportActionBar() != null)
                    {
                        getSupportActionBar().setTitle(getString(R.string.toolbar_title_group));
                    }

                    view.setSelected(true);

                    Fragment groupFragment = new GroupFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, groupFragment, null);
                    fragmentTransaction.commit();
                }
                else if (view == mFrameLayout_Database)
                {
                    if (getSupportActionBar() != null)
                    {
                        getSupportActionBar().setTitle(getString(R.string.toolbar_title_database));
                    }

                    view.setSelected(true);

                    Fragment databaseFragment = new DatabaseFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, databaseFragment, null);
                    fragmentTransaction.commit();
                }
                else if (view == mFrameLayout_Help)
                {

                    if (getSupportActionBar() != null)
                    {
                        getSupportActionBar().setTitle(getString(R.string.toolbar_title_help));
                    }

                    view.setSelected(true);

                    Fragment helpFragment = new HelpFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, helpFragment, null);
                    fragmentTransaction.commit();
                }
                else if (view == mFrameLayout_Settings){

                    if (getSupportActionBar() != null)
                    {
                        getSupportActionBar().setTitle(getString(R.string.toolbar_title_settings));
                    }

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
