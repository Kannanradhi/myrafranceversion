package com.isteer.service.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isteer.service.R;
import com.isteer.service.activity.SRVAddWorklogScreen;

import java.util.ArrayList;

/**
 * Created by Home on 5/9/2018.
 */

public class PopupDefaultAdapter extends RecyclerView.Adapter<PopupDefaultAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private int POPUPID;
    private ArrayList<String> dataList = new ArrayList<String>();


    private View view;
    private ViewHolder viewHolder;
    private final int ID_MODEL = 1;
    private final int ID_BRAND = 2;

    public PopupDefaultAdapter(Context context1, int Popup_Id, ArrayList<String> arrayList) {

        context = context1;
        POPUPID = Popup_Id;
        dataList = arrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context).inflate(R.layout.list_popup_default, parent, false);

        viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.txt_data.setText(dataList.get(position));
        holder.cb_text.setChecked(false);

        holder.ll_popup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        holder.cb_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onClick(View view) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final LinearLayout ll_popup_text;
        private final TextView txt_data;
        private final CheckBox cb_text;

        public ViewHolder(View itemView) {
            super(itemView);

            ll_popup_text = itemView.findViewById(R.id.ll_popup_text);
            txt_data =  itemView.findViewById(R.id.txt_popup_data);
            cb_text =  itemView.findViewById(R.id.cb_text);

        }

        @Override
        public void onClick(View view) {

        }
    }

}

