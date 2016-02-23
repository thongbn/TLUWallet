package com.client.activity;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.ScrimInsetsFrameLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.client.CustomWalletList.CustomWalletList;
import com.client.R;
import com.client.database.model.MyWallet;
import com.client.fragment.DatabaseFragment;
import com.client.fragment.DealDetailsFragment;
import com.client.fragment.HelpFragment;
import com.client.fragment.PlanFragment;
import com.client.fragment.SettingsFragment;
import com.client.ultils.UtilsDevice;
import com.client.ultils.UtilsMiscellaneous;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.ProfilePictureView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DrawerLayout mDrawerLayout;
    ImageButton FAB;
    ImageView button_show_wallet;
    TextView headerUserEmail, pick_Wallet;
    private LinearLayout mNavDrawerEntriesRootView, m_LinearLayout_Show_Wallet;
    private RelativeLayout mFrameLayout_AccountView, m_activity_choosen_wallet;
    private FrameLayout mFrameLayout_Plan, mFrameLayout_Database, mFrameLayout_Help, mFrameLayout_Settings, mFrameLayout_DealDetails;
    private SwipeMenuListView listWalletView;
    private CustomWalletList adapter;
    private SharedPreferences loginPreferences;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        //Floating action button

        FAB = (ImageButton) findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplication(), DealActivity.class);
                intent.putExtra("update", false);
                startActivity(intent);
            }
        });
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

        mFrameLayout_Plan = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_group);

        mFrameLayout_Database = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_database);

        mFrameLayout_Help = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_help);

        mFrameLayout_Settings = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_settings);

        mFrameLayout_DealDetails = (FrameLayout) findViewById(R.id.navigation_drawer_list_linearLayout_deal_details);


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

        // set the first item as selected for the first time

        mFrameLayout_DealDetails.setSelected(true);

        // Create the first fragment to be shown

        Fragment dealDetailsFragment = new DealDetailsFragment();
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

        //Wallet choosen

        m_LinearLayout_Show_Wallet = (LinearLayout) findViewById(R.id.show_wallet);
        button_show_wallet = (ImageView) findViewById(R.id.click_to_slide);

        mNavDrawerEntriesRootView.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        m_activity_choosen_wallet = (RelativeLayout) findViewById(R.id.activity_choosen_wallet);

        m_LinearLayout_Show_Wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewGroup.LayoutParams params = m_activity_choosen_wallet.getLayoutParams();
                params.height = params.height == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : 0;
                m_activity_choosen_wallet.setLayoutParams(params);

                float xoayVong = (button_show_wallet.getRotation() == 180F) ? 0F : 180F;
                button_show_wallet.animate().rotation(xoayVong).setInterpolator(new AccelerateDecelerateInterpolator());

            }

        });


        final TextView addWallet = (TextView) findViewById(R.id.addWallet);
        addWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplication(), WalletActivity2.class);
                intent.putExtra("update", false);
                startActivity(intent);
            }
        });



        //My Wallet List
        adapter = new CustomWalletList(getApplication());
        listWalletView = (SwipeMenuListView) findViewById(R.id.listWallet);
        listWalletView.setAdapter(adapter);

        pick_Wallet = (TextView) findViewById(R.id.navigation_drawer_item_textView_wallet);

        if (MyWallet.listWalletName.size() > 0) {
            pick_Wallet.setText(MyWallet.listWalletName.get(0));
        } else {
            pick_Wallet.setText("Tạo ví ");
        }

        listWalletView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ViewGroup.LayoutParams params = m_activity_choosen_wallet.getLayoutParams();
                params.height = params.height == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : 0;
                m_activity_choosen_wallet.setLayoutParams(params);

                float xoayVong = (button_show_wallet.getRotation() == 180F) ? 0F : 180F;
                button_show_wallet.animate().rotation(xoayVong).setInterpolator(new AccelerateDecelerateInterpolator());

                pick_Wallet.setText(MyWallet.listWalletName.get(position));

            }
        });

        // Menu listview

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                //create an action that will be showed on swiping an item in the list
                SwipeMenuItem edit = new SwipeMenuItem(
                        getApplicationContext());
                edit.setBackground(new ColorDrawable(Color.DKGRAY));
                // set width of an option (px)
                edit.setWidth(dp2px(90));
                edit.setTitle("Chỉnh sửa");
                edit.setTitleSize(14);
                edit.setIcon(R.drawable.ic_edit);
                edit.setTitleColor(Color.WHITE);
                menu.addMenuItem(edit);

                SwipeMenuItem delete = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                delete.setBackground(new ColorDrawable(Color.RED));
                delete.setWidth(dp2px(90));
                delete.setTitle("Xóa");
                delete.setTitleSize(14);
                delete.setIcon(R.drawable.ic_delete);
                delete.setTitleColor(Color.WHITE);
                menu.addMenuItem(delete);
            }
        };

        listWalletView.setMenuCreator(creator);

        listWalletView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {

            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });

        listWalletView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    // Chinh sua
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), WalletActivity2.class);
                        intent.putExtra("ID", MyWallet.listWalletID.get(position));
                        intent.putExtra("WName", MyWallet.listWalletName.get(position));
                        intent.putExtra("WMoney", MyWallet.listWalletMoney.get(position));
                        intent.putExtra("WType", MyWallet.listWalletMoneyType.get(position));
                        intent.putExtra("update", true);
                        startActivity(intent);
                        break;

                    // Xoa
                    case 1:

                        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme_Dark_Dialog));
                        builder.setTitle("Wait a second...");
                        builder.setMessage("Bạn có chắc chắn muốn xóa " + MyWallet.listWalletName.get(position));
                        builder.setCancelable(true);


                        builder.setPositiveButton(
                                "Đồng ý",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(getApplicationContext(), WalletActivity2.class);
                                        intent.putExtra("ID", MyWallet.listWalletID.get(position));
                                        intent.putExtra("delete", true);
                                        startActivity(intent);

                                        dialog.cancel();
                                    }
                                });

                        builder.setNegativeButton(
                                "Hủy bỏ",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert = builder.create();
                        alert.show();

                        break;
                }
                return false;
            }
        });

        listWalletView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });

        listWalletView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
        listWalletView.setOpenInterpolator(new BounceInterpolator());
        listWalletView.setCloseInterpolator(new BounceInterpolator());


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

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_left) {
            listWalletView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
            return true;
        }
        if (id == R.id.action_right) {
            listWalletView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
