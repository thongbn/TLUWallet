package com.client.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.R;
import com.client.fragment.IncomeGroupFragment;

/**
 * Created by ToanNguyen on 07/03/2016.
 */
public class CustomIncomeGroup extends BaseAdapter{

    private String [] income_categories;
    private TypedArray income_categories_img;
    private Context context;

    public CustomIncomeGroup (Context context, String [] income_text, TypedArray income_img){
        super();
        this.context = context;
        income_categories = income_text;
        income_categories_img = income_img;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return income_categories.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class ViewHolder
    {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_pick_group, parent,false);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.textView1);
            holder.img = (ImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv.setText(income_categories[position]);
        holder.img.setImageDrawable(income_categories_img.getDrawable(position));

        return convertView;
    }

}
