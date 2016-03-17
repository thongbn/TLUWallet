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
import com.client.model.MyPlan;
import com.client.model.User;
import com.client.model.UserFB;
import com.facebook.AccessToken;

/**
 * Created by ToanNguyen on 14/03/2016.
 */
public class CustomPlanList  extends BaseAdapter{

    private Context context;

    public CustomPlanList (Context context){
        super();
        this.context = context;
    }

    public int getCount () {
        return MyPlan.listPlaniD.size();
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
            convertView = inflater.inflate(R.layout.custom_plan_detail, null);
            holder = new ViewHolder();
            holder.datePlan = (TextView) convertView.findViewById(R.id.date_Plan);
            holder.planGroupDetails = (TextView) convertView.findViewById(R.id.txt_plan_group_details);
            holder.planGroup = (TextView) convertView.findViewById(R.id.txtPlanGroup);
            holder.planDetails = (TextView) convertView.findViewById(R.id.txtPlanDetail);
            holder.planMoney = (TextView) convertView.findViewById(R.id.txtPlanMoney);
            holder.transaction_image = (ImageView) convertView.findViewById(R.id.icon_transection);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.datePlan.setText(MyPlan.listPlanDate.get(position));

        if (MyPlan.listPlanGroup.get(position).equals(1)){
            holder.planGroup.setText(R.string.common_income_plan);
            String income [] = convertView.getResources().getStringArray(R.array.income_categories);
            holder.planGroupDetails.setText(income[MyPlan.listPlanGroupDetailsPos.get(position)]);
            holder.transaction_image.setImageResource(MyPlan.listPlanGroupIcon.get(position));
        }else {
            holder.planGroup.setText(R.string.common_outcome_plan);
            String outcome [] = convertView.getResources().getStringArray(R.array.outcome_categories);
            holder.planGroupDetails.setText(outcome[MyPlan.listPlanGroupDetailsPos.get(position)]);
            holder.transaction_image.setImageResource(MyPlan.listPlanGroupIcon.get(position));
        }

        holder.planDetails.setText(MyPlan.listPlanDetails.get(position));

        if (AccessToken.getCurrentAccessToken() != null){
            holder.planMoney.setText(MyPlan.listPlanMoney.get(position) + " " + UserFB.getIdMoneyTypebyFB());
        }else {
            holder.planMoney.setText(MyPlan.listPlanMoney.get(position) + " " + User.getIdMoneyType());
        }

        return convertView;
    }

    static class ViewHolder {
        TextView datePlan , planGroup, planMoney, planDetails, planGroupDetails;
        ImageView transaction_image;
    }
}
