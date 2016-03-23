package com.client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.client.CustomAdapter.CustomDealList;
import com.client.R;
import com.client.activity.DealActivity;
import com.client.activity.EditDealActivity;
import com.client.database.DataBaseHelper;
import com.client.database.ShowDetails;
import com.client.model.MyDeal;
import com.client.model.PageModel;
import com.client.model.User;
import com.client.model.UserFB;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import java.text.NumberFormat;
import java.util.Locale;

public class HelpFragment extends Fragment {

    private ListView listDeal;
    private com.melnykov.fab.FloatingActionButton FAB;
    private TextView totalIncome, totalOutcome, total_Money;
    private CustomDealList adapter;
    private DataBaseHelper dataBaseHelper;
    private ShowDetails showDetails;

    private static final int PAGE_LEFT = 0;
    private static final int PAGE_MIDDLE = 1;
    private static final int PAGE_RIGHT = 2;

    private LayoutInflater mInflater;
    private int mSelectedPageIndex = 1;

    private PageModel [] mPageModel = new PageModel [3];
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.help_fragment, container, false);

        getActivity().setTitle(R.string.nav_drawer_item_help);

        initPageModel();

        mInflater = getLayoutInflater(savedInstanceState);

        MyAdapter adapter = new MyAdapter();

        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(PAGE_MIDDLE, false);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == ViewPager.SCROLL_STATE_IDLE){

                    final PageModel leftPage = mPageModel[PAGE_LEFT];
                    final PageModel middlePage = mPageModel[PAGE_MIDDLE];
                    final PageModel rightPage = mPageModel[PAGE_RIGHT];

                    final int oldLeftIndex = leftPage.getIndex();
                    final int olMiddleIndex = middlePage.getIndex();
                    final int oldRightIndex = rightPage.getIndex();

                    if (mSelectedPageIndex == PAGE_LEFT){

                        leftPage.setIndex(oldLeftIndex -1);
                        middlePage.setIndex(oldLeftIndex);
                        rightPage.setIndex(olMiddleIndex);

                        setContent(PAGE_RIGHT);
                        setContent(PAGE_MIDDLE);
                        setContent(PAGE_LEFT);
                    }else if (mSelectedPageIndex == PAGE_RIGHT) {

                        leftPage.setIndex(olMiddleIndex);
                        middlePage.setIndex(oldLeftIndex);
                        rightPage.setIndex(oldRightIndex + 1);

                        setContent(PAGE_LEFT);
                        setContent(PAGE_MIDDLE);
                        setContent(PAGE_RIGHT);
                    }

                    viewPager.setCurrentItem(PAGE_MIDDLE, false);
                }
            }
        });
         
        return rootView;
    }

    private void setContent (int index) {
        final PageModel model = mPageModel[index];
        model.textView.setText(model.getText());
    }

    private void initPageModel() {
        for (int i = 0; i< mPageModel.length; i++){
            mPageModel[i] = new PageModel(i - 1);
        }
    }

    private class MyAdapter extends PagerAdapter{

        @Override
        public int getItemPosition (Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object){
            container.removeView((View) object);
        }

        @Override
        public int getCount (){
            return 3;
        }

        public Object instantiateItem (ViewGroup container, int position){

            final View rootView = mInflater.inflate(R.layout.testdealdetailsfragment, container, false);
            FacebookSdk.sdkInitialize(rootView.getContext());

            //Floating action button

            FAB = (com.melnykov.fab.FloatingActionButton) rootView.findViewById(R.id.imageButton);
            FAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(rootView.getContext(), DealActivity.class);
                    startActivityForResult(intent, 1);
                }
            });


            //Custom deal list
            listDeal = (ListView) rootView.findViewById(R.id.listDealDetails);

            FAB.attachToListView(listDeal);

            adapter = new CustomDealList(rootView.getContext());
            listDeal.setAdapter(adapter);

            listDeal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(rootView.getContext(), EditDealActivity.class);
                    intent.putExtra("DId", MyDeal.listDealiD.get(position-1));
                    intent.putExtra("DMoney", MyDeal.listDealMoney.get(position-1));
                    intent.putExtra("DDetail", MyDeal.listDealDetails.get(position - 1));
                    if (AccessToken.getCurrentAccessToken() != null){
                        intent.putExtra("DTypeMoney", UserFB.getIdMoneyTypebyFB());
                    }else {
                        intent.putExtra("DTypeMoney", User.getIdMoneyType());
                    }

                    intent.putExtra("DGroup", MyDeal.listDealGroup.get(position-1));
                    intent.putExtra("DDate", MyDeal.listDealDate.get(position-1));
                    intent.putExtra("DGroupImg", MyDeal.listDealGroupIcon.get(position-1));
                    intent.putExtra("DGroupDetails", MyDeal.listDealGroupDetailsPos.get(position-1));
                    intent.putExtra("update", true);
                    startActivityForResult(intent, 2);
                }
            });



            View header = mInflater.inflate(R.layout.custom_header_listdeal, null);

            totalIncome = (TextView) header.findViewById(R.id.total_incomeMoney);
            totalOutcome = (TextView) header.findViewById(R.id.total_outcomeMoney);
            total_Money = (TextView) header.findViewById(R.id.total_Money);

            countTotal();

            listDeal.addHeaderView(header, null, false);

            container.addView(rootView);

            return rootView;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
    }

    private void countTotal (){

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.CANADA);
        int sumIncome = 0;
        int sumOutcome = 0;
        int number1 [] = new int[MyDeal.listAllIncome.size()];
        int number [] = new int[MyDeal.listAllOutcome.size()];

        for (int i = 0 ; i< MyDeal.listAllIncome.size(); i++) {
            number1 [i] = Integer.parseInt(MyDeal.listAllIncome.get(i).replace(",", ""));
        }

        for (int i = 0; i < number1.length; i++){
            sumIncome += number1[i];
        }

        for (int i = 0 ; i< MyDeal.listAllOutcome.size(); i++) {
            number [i] = Integer.parseInt(MyDeal.listAllOutcome.get(i).replace(",", ""));
        }


        for (int i = 0; i < number.length; i++){
            sumOutcome += number[i];
        }

        String income = numberFormat.format(sumIncome);
        String outcome = numberFormat.format(sumOutcome);

        if (AccessToken.getCurrentAccessToken() != null){
            totalIncome.setText(income + " " + UserFB.getIdMoneyTypebyFB());
            totalOutcome.setText(outcome + " " + UserFB.getIdMoneyTypebyFB());
        }else {
            totalIncome.setText(income + " " + User.getIdMoneyType());
            totalOutcome.setText(outcome + " " + User.getIdMoneyType());
        }

        String totalmoney = numberFormat.format(sumIncome - sumOutcome);

        if (AccessToken.getCurrentAccessToken() != null){
            total_Money.setText(totalmoney + " " + UserFB.getIdMoneyTypebyFB());
        }else {
            total_Money.setText(totalmoney + " " + User.getIdMoneyType());
        }
    }

    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null || requestCode == 2){
            if (resultCode == getActivity().RESULT_OK) {
                dataBaseHelper = new DataBaseHelper(getContext());
                showDetails = new ShowDetails();
                showDetails.clear_list();

                showDetails.showDetails(dataBaseHelper);

                countTotal();

            }

            if (resultCode == getActivity().RESULT_CANCELED){

            }
        }

    }

}