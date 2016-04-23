package com.client.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.client.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.communication.IOnItemFocusChangedListener;
import org.eazegraph.lib.models.PieModel;

/**
 * Created by ToanNguyen on 22/04/2016.
 */
public class HelpDetailThreeFragment extends Fragment {

  private PieChart mPieChart;

  @Override
  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.help_detail_three_fragment, null);

    mPieChart = (PieChart) rootView.findViewById(R.id.piechart);
    loadData();

    return rootView;
  }

  @Override
  public void onResume() {
    super.onResume();
    mPieChart.startAnimation();
  }

  private void loadData() {
    mPieChart.addPieSlice(new PieModel(getResources().getString(R.string.tutorial_9), 50, Color.parseColor("#FE6DA8")));
    mPieChart.addPieSlice(new PieModel(getResources().getString(R.string.tutorial_10), 20, Color.parseColor("#56B7F1")));
    mPieChart.addPieSlice(new PieModel(getResources().getString(R.string.tutorial_11), 10, Color.parseColor("#CDA67F")));
    mPieChart.addPieSlice(new PieModel(getResources().getString(R.string.tutorial_12), 20, Color.parseColor("#FED70E")));

    mPieChart.setOnItemFocusChangedListener(new IOnItemFocusChangedListener() {
      @Override
      public void onItemFocusChanged(int _Position) {
//                Log.d("PieChart", "Position: " + _Position);
      }
    });
  }


}
