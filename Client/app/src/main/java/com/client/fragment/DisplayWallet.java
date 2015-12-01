package com.client.fragment;

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
public class DisplayWallet extends BaseAdapter{
    private Context mContext;
    private ArrayList<String> id;
    private ArrayList<String> name;
    private ArrayList<String> money;
    //private ArrayList<String> type;

    public DisplayWallet(Context context, ArrayList<String> idWallet, ArrayList<String> walletName, ArrayList<String> walletMoney /*ArrayList<String> walletType*/){
        this.mContext = context;

        this.id = idWallet;
        this.name = walletName;
        this.money = walletMoney;
        //    this.type = walletType;
    }
    public int getCount(){
        return id.size();
    }
    public Object getItem(int position){
        return null;
    }
    public long getItemId(int position) {
        return 0;
    }
    public View getView(int pos, View child, ViewGroup parent){
        Holder mHolder;
        LayoutInflater layoutInflater;
        if(child == null){
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listcell, null);
            mHolder = new Holder();
            mHolder.txt_id = (TextView) child.findViewById(R.id.txt_id);
            mHolder.txt_Name = (TextView) child.findViewById(R.id.txt_Name);
            mHolder.txt_Money = (TextView) child.findViewById(R.id.txt_Money);

            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }
        mHolder.txt_id.setText(id.get(pos));
        mHolder.txt_Name.setText(name.get(pos));
        mHolder.txt_Money.setText(money.get(pos));

        return child;

    }
    public class Holder{
        TextView txt_id;
        TextView txt_Name;
        TextView txt_Money;

    }

}
