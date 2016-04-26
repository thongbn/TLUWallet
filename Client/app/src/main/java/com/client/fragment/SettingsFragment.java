package com.client.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.client.R;
import com.client.activity.MainActivity;

import java.util.Locale;

public class SettingsFragment extends Fragment{

    private Spinner spinnerctrl;
    private Locale myLocale;
    private int [] imageViews = {0 ,R.drawable.ic_currency_vnd, R.drawable.ic_currency_gbp};
    private SharedPreferences langPreferences;
    private SharedPreferences.Editor langPrefsEditor;
	
	@Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);

        getActivity().setTitle(R.string.nav_drawer_item_settings);

        spinnerctrl = (Spinner) rootView.findViewById(R.id.spinner1);
        spinnerctrl.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, getResources().getStringArray(R.array.languages)));
        spinnerctrl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                if (pos == 1) {

                    Toast.makeText(parent.getContext(),
                            getResources().getString(R.string.vietnam_selected), Toast.LENGTH_SHORT)
                            .show();
                    setLocale("vi");
                } else if (pos == 2) {

                    Toast.makeText(parent.getContext(),
                            getResources().getString(R.string.english_selected), Toast.LENGTH_SHORT)
                            .show();
                    setLocale("en");
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });

        return rootView;
    }

    public void setLocale(String lang) {

        langPreferences = getActivity().getSharedPreferences("langPrefs", getActivity().MODE_PRIVATE);
        langPrefsEditor = langPreferences.edit();
        langPrefsEditor.clear();
        langPrefsEditor.putString("lang", lang);
        langPrefsEditor.apply();
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Configuration config = new Configuration();
        config.locale = myLocale;
        getActivity().getBaseContext().getResources().updateConfiguration(config,
                getActivity().getBaseContext().getResources().getDisplayMetrics());

        Intent refresh = new Intent(getActivity(), MainActivity.class);
        startActivity(refresh);
    }

    public class MyAdapter extends ArrayAdapter <String> {

        public MyAdapter (Context context, int txtViewResourceID, String[] objects) {
            super(context, txtViewResourceID, objects);
            }

        @Override
         public View getDropDownView (int position, View convertView, ViewGroup parent)  {
             return getCustomView(position, convertView, parent);
            }

         @Override
         public View getView (int position, View convertView, ViewGroup parent) {
               return getCustomView(position, convertView, parent);
            }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            //return super.getView(position, convertView, parent);
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spinner, parent, false);
            TextView text_typeMoney = (TextView) mySpinner.findViewById(R.id.txt);
            text_typeMoney.setText(getResources().getStringArray(R.array.languages)[position]);
            ImageView image_typeMomey = (ImageView) mySpinner.findViewById(R.id.img);
            image_typeMomey.setImageResource(imageViews[position]);
            return mySpinner;
        }
    }
}