package com.client.fragment;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.client.R;

import java.util.ArrayList;

/**
 * Created by nguye on 11/9/2015.
 */
public class DisplayWallet extends BaseAdapter {
    private Context context;
    private ArrayList<String> name;
    private ArrayList<String> money;

    public DisplayWallet(Context context, ArrayList<String> walletName, ArrayList<String> walletMoney) {
        this.context = context;
        this.name = walletName;
        this.money = walletMoney;
    }


    public int getCount() {
        return name.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int pos, View child, ViewGroup parent) {
        Holder mHolder;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listcell, parent);
            mHolder = new Holder();
            mHolder.txt_Name = (TextView) child.findViewById(R.id.txtWalletName);
            mHolder.txt_Money = (TextView) child.findViewById(R.id.txtWalletMoney);

            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }
        mHolder.txt_Name.setText(name.get(pos));
        mHolder.txt_Money.setText(money.get(pos));

        return child;

    }

    public class Holder {
        TextView txt_Name;
        TextView txt_Money;

    }
}
