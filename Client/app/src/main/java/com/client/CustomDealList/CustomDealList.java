package com.client.CustomDealList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.R;
import com.client.database.DataBaseHelper;
import com.client.database.model.MyDeal;

/**
 * Created by ToanNguyen on 03/03/2016.
 */
public class CustomDealList extends BaseAdapter {

    private Context context;

    public CustomDealList (Context context){
        super();
        this.context = context;
    }

    public int getCount () {
        return MyDeal.listDealiD.size();
    }

    public Object getItem (int position) {
        return null;
    }

    public long getItemId (int position) {
        return position;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent){

        ViewHolder holder = null;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_deal_detail, null);
            holder = new ViewHolder();
            holder.dateDeal = (TextView) convertView.findViewById(R.id.date_Deal);
            holder.dealGroup = (TextView) convertView.findViewById(R.id.txtDealGroup);
            holder.dealDetails = (TextView) convertView.findViewById(R.id.txtDealDetail);
            holder.dealMoney = (TextView) convertView.findViewById(R.id.txtDealMoney);
            holder.transaction_image = (ImageView) convertView.findViewById(R.id.icon_transection);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.dateDeal.setText(MyDeal.listDealDate.get(position));

        if (MyDeal.listDealGroup.get(position).equals("1")){
            holder.dealGroup.setText("Thu nhập");
            holder.transaction_image.setImageResource(R.drawable.ic_category_family);
        }else {
            holder.dealGroup.setText("Chi tiêu");
            holder.transaction_image.setImageResource(R.drawable.ic_category_give);
        }

        holder.dealDetails.setText(MyDeal.listDealDetails.get(position));

        holder.dealMoney.setText(MyDeal.listDealMoney.get(position) + " " + MyDeal.listDealTypeMoney.get(position));

        return convertView;
    }

    static class ViewHolder {
        TextView dateDeal , dealGroup, dealMoney, dealDetails;
        ImageView transaction_image;
    }
}
