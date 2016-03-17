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

/**
 * Created by ToanNguyen on 11/03/2016.
 */
public class CustomChooseMoneyType extends BaseAdapter{
    private String [] moneytype = {"Việt Nam Đồng", "United States dollar", "Euro", "Pound sterling", "Chinese Yuan", "Singapore dollar", "Japanese yen", "Korean Republic Won", "Hong Kong dollar", "Thai baht"};
    private int [] imagemoney = {R.drawable.ic_currency_vnd, R.drawable.ic_currency_usd, R.drawable.ic_currency_eur,
            R.drawable.ic_currency_gbp, R.drawable.ic_currency_cny, R.drawable.ic_currency_sgd, R.drawable.ic_currency_jpy,
            R.drawable.ic_currency_krw, R.drawable.ic_currency_hkd, R.drawable.ic_currency_thb};

    private Context context;

    public CustomChooseMoneyType (Context context){
        super();
        this.context = context;
    }

    public int getCount () {
        return imagemoney.length;
    }

    public Object getItem (int position) {
        return null;
    }

    public long getItemId (int position) {
        return position;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_choose_money_type, null);
            holder = new ViewHolder();

            holder.typemoney = (TextView) convertView.findViewById(R.id.text_typeMoney);
            holder.typemoney_image = (ImageView) convertView.findViewById(R.id.imageMoney);

            convertView.setTag(holder);
        }else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.typemoney.setText(moneytype[position]);
        holder.typemoney_image.setImageResource(imagemoney[position]);
        return convertView;
    }

    static class ViewHolder {
        TextView typemoney;
        ImageView typemoney_image;
    }

}
