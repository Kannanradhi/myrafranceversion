package com.isteer.service.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.isteer.service.R;

import java.util.ArrayList;

public class DSRCustAdapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<String> data;
    private static LayoutInflater inflater=null;

    public DSRCustAdapter(Activity a, ArrayList<String> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    private static class ViewHolder {  

        public TextView customer_entry;
    }  
    
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
    	
        ViewHolder holder;  
        
        if(convertView==null)
        {
        	convertView = inflater.inflate(R.layout.listrow_customer, null);
        	TextView customer = (TextView)convertView.findViewById(R.id.customer_entry);
            holder = new ViewHolder();  
            holder.customer_entry = customer;  
            
            convertView.setTag(holder);  
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();  
        }

        String cust = data.get(position);
        
        holder.customer_entry.setText(cust);
        
        return convertView;
        
    }
    
}