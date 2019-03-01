package com.isteer.service.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isteer.service.R;
import com.isteer.service.activity.SRVAddWorklogScreen;
import com.isteer.service.model.MasterItemData;

import java.util.ArrayList;


public class PopupProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private int ACTIVITYID;
    private Boolean UPDATEENQUIRY;
    private EditText search_box;
    public Context context;
    private View view1;
    private ArrayList<MasterItemData> list1 = new ArrayList<MasterItemData>();
    private ViewHolder viewHolder1;

    private ArrayList<MasterItemData> ProductListAryList = new ArrayList<MasterItemData>();
    private final int ACTIVITY_ADDWORKLOG = 1;
    private int id;
    private String str_search;

    public PopupProductAdapter(Context context1, ArrayList<MasterItemData> productList, int ID) {

        ACTIVITYID = ID;
        context = context1;
        list1 = productList;
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_product;
        private LinearLayout lt_popup_product;
        private CheckBox check_product;

         ViewHolder(View v) {

            super(v);

            txt_product =  v.findViewById(R.id.txt_product);
            lt_popup_product =  v.findViewById(R.id.lt_popup_product);
            check_product =  v.findViewById(R.id.check_product);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.list_popup_product, parent, false);

        viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder holder1 = (ViewHolder) holder;
        final int pos = position;

        holder1.txt_product.setText(list1.get(position).getSHORT_NAME());

      /*  holder1.txt_product.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                Log.e("onClick: newSource PRODUCT  ", "" + (list1.get(pos).getDisplay_code()));
                ((CreateEnquiry) context).addToSelectedProducts(list1.get(pos));

            }
        });*/

        holder1.check_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder1.check_product.isChecked()) {
                    if (ACTIVITYID == ACTIVITY_ADDWORKLOG) {
                        ((SRVAddWorklogScreen) context).addToSelectedProducts(list1.get(pos));
                    }
                }else{
                //    ((SRVAddWorklogScreen) context).removeFromAddedProductList((pos));
                }
            }
        });


    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public int getItemCount() {

        return list1.size();
    }


}