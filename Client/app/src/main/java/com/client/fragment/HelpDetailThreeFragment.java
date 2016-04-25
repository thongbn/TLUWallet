package com.client.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.client.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by ToanNguyen on 22/04/2016.
 */
public class HelpDetailThreeFragment extends Fragment {

  private PieChart mChart;

  @Override
  public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.help_detail_three_fragment, null);

    mChart = (PieChart) rootView.findViewById(R.id.pieChart1);
    mChart.setDescription("");

    // radius of the center hole in percent of maximum radius
    mChart.getLegend().setEnabled(false);
    mChart.setDrawHoleEnabled(false);

    ArrayList<Entry> entries = new ArrayList<>();
    entries.add(new Entry(60, 0));
    entries.add(new Entry(10, 1));
    entries.add(new Entry(20, 2));
    entries.add(new Entry(10, 3));

    ArrayList<String> labels = new ArrayList<String>();
    labels.add(getResources().getString(R.string.tutorial_9));
    labels.add(getResources().getString(R.string.tutorial_10));
    labels.add(getResources().getString(R.string.tutorial_11));
    labels.add(getResources().getString(R.string.tutorial_12));

    PieDataSet dataset = new PieDataSet(entries, "");

    PieData data = new PieData(labels, dataset);

    mChart.setData(data);
    dataset.setColors(ColorTemplate.COLORFUL_COLORS);
    mChart.animateY(1500);
    data.setValueFormatter(new MyValueFormatter());

    return rootView;
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  public class MyValueFormatter implements ValueFormatter {

    private DecimalFormat mFormat;

    public MyValueFormatter() {
      mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
      // write your logic here
      return mFormat.format(value) + " %"; // e.g. append a dollar-sign
    }
  }

}
