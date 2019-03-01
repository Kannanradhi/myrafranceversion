package com.isteer.service.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class AddProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private int ACTIVITYID;
    private Boolean UPDATEENQUIRY;
    private EditText search_box;
    public Context context;
    private View view1;
      private ArrayList<String> list1 = new ArrayList<String>();
    private ViewHolder viewHolder1;

    // private ArrayList<ProductList> ProductListAryList = new ArrayList<ProductList>();
    final private int PRODUCT_SEARCH = 1;
    final private int COMP_SEARCH = 2;
    private int id;
    private String str_search;

   /* public PopupProductAdapter(Context context1, ArrayList<ProductList> productList, int ID) {

        ACTIVITYID = ID;
        context = context1;
        list1 = productList;
    }*/

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txt_product;
        private LinearLayout lt_popup_product;
        private CheckBox check_product;

        public ViewHolder(View v) {

            super(v);

          /*  txt_product = (TextView) v.findViewById(R.id.txt_product);
            lt_popup_product = (LinearLayout) v.findViewById(R.id.lt_popup_product);
            check_product = (CheckBox) v.findViewById(R.id.check_product);*/
        }

        @Override
        public void onClick(View view) {
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(0, parent, false);

        viewHolder1 = new ViewHolder(view1);

        return viewHolder1;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder holder1 = (ViewHolder) holder;
        final int pos = position;

     //   holder1.txt_product.setText(list1.get(position).getRemarks());

      /*  holder1.txt_product.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                Log.e("onClick: newSource PRODUCT  ", "" + (list1.get(pos).getDisplay_code()));
                ((CreateEnquiry) context).addToSelectedProducts(list1.get(pos));

            }
        });
*/
        holder1.check_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     Log.e("onClick: newSource ", "PRODUCT " + (list1.get(pos).getDisplay_code()));

                switch (ACTIVITYID) {
                    case 1:
       //                 ((CreateEnquiry) context).addToSelectedProducts(list1.get(pos));
                        break;
                    case 2:
         //               ((CreateQuote) context).addToSelectedProducts(list1.get(pos));
                        break;
                }
               /* if(UPDATEENQUIRY) {
                    ((CreateEnquiry) context).addToSelectedProducts(list1.get(pos));
                }else {
                    ((CreateQuote) context).addToSelectedProducts(list1.get(pos));
                }*/
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