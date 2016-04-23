package com.client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.client.R;


public class HelpFragment extends Fragment {

  TableLayout help1, help2;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.help_fragment, container, false);

        getActivity().setTitle(R.string.nav_drawer_item_help);

        help1 = (TableLayout) rootView.findViewById(R.id.help1);
        help1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent i = new Intent(getContext(),HelpDetailZeroFragment.class);
            startActivity(i);
          }
        });

        return rootView;
    }


}
