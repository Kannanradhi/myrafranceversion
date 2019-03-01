package com.isteer.service.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.isteer.service.R;
import com.isteer.service.activity.SRVWorklogListScreen;
import com.isteer.service.app.App;
import com.isteer.service.config.SRVAppConstant;
import com.isteer.service.model.WorkLogData;

import java.util.ArrayList;

public class SRVWorklogsAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<WorkLogData> data;
    private static LayoutInflater inflater = null;

    public SRVWorklogsAdapter(Activity a, ArrayList<WorkLogData> d) {

        activity = a;
        data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

    private static class ViewHolder {

        public TextView tv_status;
        public TextView tv_productname;
        public TextView tv_serviced_by;
        public TextView tv_entered_by;
        public Button btn_remarks;
        public Button btn_mom;
        public Button btn_attach;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listrow_worklogs, null);

            holder = new ViewHolder();

            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tv_productname = (TextView) convertView.findViewById(R.id.tv_productname);
            holder.tv_serviced_by = (TextView) convertView.findViewById(R.id.tv_serviced_by);
            holder.tv_entered_by = (TextView) convertView.findViewById(R.id.tv_entered_by);
            holder.btn_remarks = (Button) convertView.findViewById(R.id.btn_remarks);
            holder.btn_mom = (Button) convertView.findViewById(R.id.btn_mom);
            holder.btn_attach = (Button) convertView.findViewById(R.id.btn_attach);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        WorkLogData tWorkLog = new WorkLogData();
        tWorkLog = data.get(position);

        int statusFigure = tWorkLog.getStatus();

        Log.e("status", "" + statusFigure);
        if (statusFigure == SRVAppConstant.WORKLOG_STATUS_COMPLETED)
            holder.tv_status.setTextColor(activity.getResources().getColor(R.color.iSteerGreen));
        else if (statusFigure == SRVAppConstant.WORKLOG_STATUS_INCOMPLETED || statusFigure == SRVAppConstant.WORKLOG_STATUS_PARTIALLY_COMPLETED
                || statusFigure == SRVAppConstant.WORKLOG_STATUS_RE_OPENED || statusFigure == SRVAppConstant.WORKLOG_STATUS_OFFICE_CLOSED)
            holder.tv_status.setTextColor(activity.getResources().getColor(R.color.red));
        // stats figure is > 5
        String product_name = tWorkLog.getProduct_name();
        if (product_name == null || product_name.equalsIgnoreCase("null"))
            product_name = "";

        holder.tv_status.setText(SRVAppConstant.SERVICE_LOG_STATUS[--statusFigure]);
        holder.tv_productname.setText(product_name);
        holder.tv_serviced_by.setText("by " + tWorkLog.getServiced_by_name() + " on " + App.appUtils.getFormattedDate("dd-MM-yyyy",
                App.appUtils.getTimestamp("dd/MM/yyyy", tWorkLog.getVisited_date())));

        holder.tv_entered_by.setText("Entered by " + tWorkLog.getEntered_by_name() + " on " + App.appUtils.getFormattedDate("dd-MM-yyyy",
                App.appUtils.getTimestamp("dd/MM/yyyy", tWorkLog.getVisited_date().concat(tWorkLog.getEnd_time()))));

        Log.e("start time , end time", tWorkLog.getVisited_date() + " , " + tWorkLog.getEntered_date_time());

        holder.btn_attach.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

               // ((SRVWorklogListScreen) activity).onAttachmentsClicked(position);
            }
        });

        holder.btn_mom.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ((SRVWorklogListScreen) activity).onMOMClicked(position);

            }
        });

        holder.btn_remarks.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ((SRVWorklogListScreen) activity).onRemarksClicked(position);
            }
        });

        return convertView;
    }

}
