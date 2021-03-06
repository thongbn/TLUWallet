package com.client.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.R;
import com.client.model.MyDeal;
import com.client.model.User;
import com.client.model.UserFB;
import com.facebook.AccessToken;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by ToanNguyen on 03/03/2016.
 */
public class CustomDealList extends BaseAdapter {

    private Context context;
    private String dateOutput;

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
            holder.dealGroupDetails = (TextView) convertView.findViewById(R.id.txt_deal_group_details);
            holder.dealGroup = (TextView) convertView.findViewById(R.id.txtDealGroup);
            holder.dealDetails = (TextView) convertView.findViewById(R.id.txtDealDetail);
            holder.dealMoney = (TextView) convertView.findViewById(R.id.txtDealMoney);
            holder.transaction_image = (ImageView) convertView.findViewById(R.id.icon_transection);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        String date = MyDeal.listDealDate.get(position);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date newDate = format.parse(date);
            format = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            dateOutput = format.format(newDate);
        }catch (java.text.ParseException e){
            e.printStackTrace();
        }

        holder.dateDeal.setText(dateOutput);

        if (MyDeal.listDealGroup.get(position).equals(1)){
            holder.dealGroup.setText(R.string.common_income);
            String income [] = convertView.getResources().getStringArray(R.array.income_categories);
            holder.dealGroupDetails.setText(income[MyDeal.listDealGroupDetailsPos.get(position)]);
            holder.transaction_image.setImageResource(MyDeal.listDealGroupIcon.get(position));
        }else {
            holder.dealGroup.setText(R.string.common_outcome);
            String outcome [] = convertView.getResources().getStringArray(R.array.outcome_categories);
            holder.dealGroupDetails.setText(outcome[MyDeal.listDealGroupDetailsPos.get(position)]);
            holder.transaction_image.setImageResource(MyDeal.listDealGroupIcon.get(position));
        }

        holder.dealDetails.setText(MyDeal.listDealDetails.get(position));

        if (AccessToken.getCurrentAccessToken() != null){
            holder.dealMoney.setText(MyDeal.listDealMoney.get(position) + " " + UserFB.getIdMoneyTypebyFB());
        }else {
            holder.dealMoney.setText(MyDeal.listDealMoney.get(position) + " " + User.getIdMoneyType());
        }

        return convertView;
    }

    static class ViewHolder {
        TextView dateDeal , dealGroup, dealMoney, dealDetails, dealGroupDetails;
        ImageView transaction_image;
    }

}
