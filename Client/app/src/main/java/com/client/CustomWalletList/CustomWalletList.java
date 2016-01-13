package com.client.CustomWalletList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.client.R;
import com.client.activity.MainActivity;
import com.client.database.model.MyWallet;

import java.util.List;

/**
 * Created by nguye on 1/7/2016.
 */
public class CustomWalletList extends BaseAdapter {
    private Context context;


    public CustomWalletList (Context context) {
        super();
        this.context = context;

    }

    public int getCount(){
        return MyWallet.listWalletName.size();
    }

    public Object getItem (int position) {
        return null;
    }

    public long getItemId (int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listcell, null);
            holder = new ViewHolder();
            holder.itemName = (TextView) convertView.findViewById(R.id.txtWalletName);
            holder.itemMoney = (TextView)convertView.findViewById(R.id.txtWalletMoney);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemName.setText(MyWallet.listWalletName.get(position));
        holder.itemMoney.setText(MyWallet.listWalletMoney.get(position));

        return convertView;

    }

    static class ViewHolder {
        TextView itemName , itemMoney;
    }

}
