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

/**
 * Created by ToanNguyen on 07/03/2016.
 */
public class CustomOutcomeGroup extends BaseAdapter{
    private String [] outcome_categories;
    private int [] outcome_categories_img;
    private Context context;

    public CustomOutcomeGroup (Context context, String [] outcome_text, int [] outcome_img){
        super();
        this.context = context;
        outcome_categories = outcome_text;
        outcome_categories_img = outcome_img;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return outcome_categories.length;
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

        holder.tv.setText(outcome_categories[position]);
        holder.img.setImageResource(outcome_categories_img[position]);

        return convertView;
    }
}
