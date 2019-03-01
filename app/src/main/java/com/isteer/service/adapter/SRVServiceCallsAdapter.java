package com.isteer.service.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.isteer.service.R;
import com.isteer.service.app.App;
import com.isteer.service.config.SRVAppConstant;
import com.isteer.service.model.ServiceCallData;

import java.util.ArrayList;

public class SRVServiceCallsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private ArrayList<ServiceCallData> data;

    private OnclickServiceCall onclick;

    public SRVServiceCallsAdapter(Activity a) {

        activity = a;
        onclick = (OnclickServiceCall) a;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }


    public void setData(ArrayList<ServiceCallData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public ArrayList<ServiceCallData> getData() {
        return data;
    }

    public void romoveData(int pos) {
        data.remove(pos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View v = inflater.inflate(R.layout.listrow_servicecalls, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder myHolder = (ViewHolder) holder;

        myHolder.view_status_indicator.setBackgroundColor(activity.getResources().getColor(R.color.dark_red));


        ServiceCallData tServiceCall = data.get(position);

        if (tServiceCall.getStatus() == SRVAppConstant.SERVICE_STATUS_COMPLETED) {
            myHolder.tv_status_indicator.setTextColor(activity.getResources().getColor(R.color.dark_green));
            myHolder.tv_status_indicator.setText("Completed");

        } else if (tServiceCall.getStatus() == SRVAppConstant.SERVICE_STATUS_PENDING) {
            myHolder.tv_status_indicator.setTextColor(activity.getResources().getColor(R.color.dark_red));
            myHolder.tv_status_indicator.setText("Pending");

        } else if (tServiceCall.getStatus() == SRVAppConstant.SERVICE_STATUS_ENTERED) {
            myHolder.tv_status_indicator.setTextColor(activity.getResources().getColor(R.color.dark_yellow));
            myHolder.tv_status_indicator.setText("Entered");

        } else if (tServiceCall.getStatus() == SRVAppConstant.SERVICE_STATUS_CANCELLED) {
            myHolder.tv_status_indicator.setTextColor(activity.getResources().getColor(R.color.grey_dark));
            myHolder.tv_status_indicator.setText("Cancelled");

        } else {
            myHolder.tv_status_indicator.setTextColor(activity.getResources().getColor(R.color.dark_red));
            myHolder.tv_status_indicator.setText("Incompleted");
        }


        Log.e("Call Status of " + position, " : " + tServiceCall.getStatus());

        myHolder.tv_customername.setText(tServiceCall.getCustomer_name());
        myHolder.tv_contract.setText(tServiceCall.getContract());
        myHolder.tv_reported_on.setText("reported on " + App.appUtils.getFormattedDate("dd-MM-yyyy",
                App.appUtils.getTimestamp("yyyyMMdd", tServiceCall.getReport_date())));

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.onclick(position);
            }
        });

    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        public View view_status_indicator;
        public TextView tv_status_indicator;
        public TextView tv_customername;
        public TextView tv_contract;
        public TextView tv_reported_on;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_customername = itemView.findViewById(R.id.tv_customername);
            tv_contract = itemView.findViewById(R.id.tv_contract);
            tv_reported_on = itemView.findViewById(R.id.tv_reported_on);
            view_status_indicator = itemView.findViewById(R.id.view_status_indicator);
            tv_status_indicator = itemView.findViewById(R.id.tv_status_indicator);
        }
    }

    public interface OnclickServiceCall {
        void onclick(int pos);
    }
}