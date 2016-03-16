package com.client.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.database.ShowDetails;
import com.client.database.model.Deal;
import com.client.database.model.MyDeal;
import com.client.database.model.MyPlan;
import com.client.database.model.Plan;
import com.client.fragment.DatabaseFragment;
import com.client.fragment.DealDetailsFragment;
import com.client.fragment.HelpFragment;
import com.client.fragment.PlanFragment;
import com.client.fragment.ReportFragment;
import com.client.fragment.SettingsFragment;
import com.client.ultils.UtilsDevice;
import com.client.ultils.UtilsMiscellaneous;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.ProfilePictureView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DrawerLayout mDrawerLayout;
    private TextView headerUserEmail;
    private LinearLayout mNavDrawerEntriesRootView;
    private RelativeLayout mFrameLayout_AccountView;
    private FrameLayout mFrameLayout_Plan, mFrameLayout_Database, mFrameLayout_Help, mFrameLayout_Settings, mFrameLayout_DealDetails, mFrameLayout_Report;
    private SharedPreferences loginPreferences;
    private DataBaseHelper dataBaseHelper;
    private ShowDetails showDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        showDetails = new ShowDetails();

        showDetails.clear_list();

        showDetails.showDetails(dataBaseHelper);

        initialise();

    }

    private void initialise() {

        //Toolbar
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Layout resources
        mFrameLayout_AccountView = (RelativeLayout) findViewById(R.id.navigation_drawer_account_view);

        mNavDrawerEntriesRootView = (LinearLayout) findViewById(R.id.navigation_drawer_linearLayout_entries_root_view);

        mFrameLayout_Plan = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_plan);

        mFrameLayout_Database = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_database);

        mFrameLayout_Help = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_help);

        mFrameLayout_Settings = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_settings);

        mFrameLayout_DealDetails = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_deal_details);

        mFrameLayout_Report = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_report);


        // Navigation Drawer

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity);

        final ScrimInsetsFrameLayout mScrimInsetsFrameLayout = (ScrimInsetsFrameLayout) findViewById(R.id.main_activity_navigation_drawer_rootLayout);

        final ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_opened, R.string.navigation_drawer_closed) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

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
        mFrameLayout_DealDetails.setOnClickListener(this);
        mFrameLayout_Plan.setOnClickListener(this);
        mFrameLayout_Database.setOnClickListener(this);
        mFrameLayout_Help.setOnClickListener(this);
        mFrameLayout_Settings.setOnClickListener(this);
        mFrameLayout_Report.setOnClickListener(this);

        // set the first item as selected for the first time

        mFrameLayout_DealDetails.setSelected(true);

        // Create the first fragment to be shown

        final Fragment dealDetailsFragment = new DealDetailsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerView, dealDetailsFragment, null);
        fragmentTransaction.commit();

        //Header User

        headerUserEmail = (TextView) findViewById(R.id.navigation_drawer_account_information_display_name);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        String emailLogin = loginPreferences.getString("email", "");
        if (AccessToken.getCurrentAccessToken() != null) {
            SharedPreferences idFacebook = getSharedPreferences("idFacebook", MODE_PRIVATE);
            String facebookName = idFacebook.getString("nameFB", "");
            String facebookId = idFacebook.getString("idFB", "");
            headerUserEmail.setText(facebookName);

            // Header User Picture Facebook
            ProfilePictureView profilePictureView;
            profilePictureView = (ProfilePictureView) findViewById(R.id.imageFB);
            profilePictureView.setProfileId(facebookId);

        } else {
            headerUserEmail.setText(emailLogin);
        }


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

                if (view == mFrameLayout_DealDetails){
                    view.setSelected(true);

                    Fragment dealDetailsFragment = new DealDetailsFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, dealDetailsFragment, null);
                    fragmentTransaction.commit();
                }
                else if (view == mFrameLayout_Plan)
                {

                    view.setSelected(true);

                    Fragment planFragment = new PlanFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, planFragment, null);
                    fragmentTransaction.commit();
                }
                else if (view == mFrameLayout_Report){

                    view.setSelected(true);

                    Fragment reportFragment = new ReportFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, reportFragment, null);
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

    private void clear_list (){
        MyDeal.listDealGroupDetailsPos.clear();
        MyDeal.listDealGroup.clear();
        MyDeal.listDealGroupIcon.clear();
        MyDeal.listDealiD.clear();
        MyDeal.listDealDetails.clear();
        MyDeal.listDealMoney.clear();
        MyDeal.listDealDate.clear();
        MyDeal.listAllIncome.clear();
        MyDeal.listAllOutcome.clear();

        MyPlan.listPlanGroupDetailsPos.clear();
        MyPlan.listPlanGroup.clear();
        MyPlan.listPlanGroupIcon.clear();
        MyPlan.listPlaniD.clear();
        MyPlan.listPlanDetails.clear();
        MyPlan.listPlanMoney.clear();
        MyPlan.listPlanDate.clear();
        MyPlan.listAllIncome.clear();
        MyPlan.listAllOutcome.clear();
    }

}
