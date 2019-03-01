package com.isteer.service.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isteer.service.R;
import com.isteer.service.app.App;
import com.isteer.service.model.MasterItemData;
import com.isteer.service.model.SpareItemData;

import java.util.ArrayList;


public class AddedProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private int ACTIVITYID;
    private Boolean UPDATEENQUIRY;
    private EditText search_box;
    public Context context;
    private View view1;
    private ArrayList<SpareItemData> list1 = new ArrayList<SpareItemData>();
    private ViewHolder viewHolder1;

    private ArrayList<MasterItemData> ProductListAryList = new ArrayList<MasterItemData>();
    final private int PRODUCT_SEARCH = 1;
    final private int COMP_SEARCH = 2;
    private int id;
    private String str_search;

    public AddedProductAdapter(Context context1) {

        //ACTIVITYID = ID;
        context = context1;
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final EditText et_qty;
        private final EditText et_rate;
        private final EditText et_total;
        private TextView et_product;
        private LinearLayout lt_popup_product;
        private CheckBox check_box;

        public ViewHolder(View v) {


            super(v);

            et_product = (TextView) v.findViewById(R.id.et_product);
            et_qty = (EditText) v.findViewById(R.id.et_qty);
            et_rate = (EditText) v.findViewById(R.id.et_rate);
            et_total = (EditText) v.findViewById(R.id.et_total);
            lt_popup_product = (LinearLayout) v.findViewById(R.id.lt_popup_product);

            check_box = (CheckBox) v.findViewById(R.id.check_box);
        }

        @Override
        public void onClick(View view) {
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.list_added_product, parent, false);

        viewHolder1 = new ViewHolder(view1);

        return viewHolder1;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder holder1 = (ViewHolder) holder;
        final int pos = position;

        holder1.check_box.setChecked(true);
        holder1.et_product.setText(list1.get(pos).getSpare_part_name());


        holder1.check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(context.getResources().getString(R.string.ab_remove_spare_product))
                            //.setTitle(getResources().getString(R.string.lb_alert))
                            .setCancelable(true)
                            .setPositiveButton(context.getResources().getString(R.string.ab_remove),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {

                                            list1.remove(pos);
                                            notifyDataSetChanged();
                                            dialog.cancel();
                                        }
                                    })
                            .setNegativeButton(context.getResources().getString(R.string.ab_cancel),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            holder1.check_box.setChecked(true);
                                            dialog.cancel();

                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }
        });
        holder1.et_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                int int_qty = App.validationUtils.integerFormat(holder1.et_qty.getText().toString());

                double int_rate = App.validationUtils.doubleFormat(holder1.et_rate.getText().toString());

                double int_total = int_qty * int_rate;

                holder1.et_total.setText(String.valueOf(int_total));

                list1.get(pos).setRate(String.valueOf(int_rate));
                list1.get(pos).setQty(String.valueOf(int_qty));
                list1.get(pos).setTotal_amount(String.valueOf(int_total));


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder1.et_rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                int int_qty = App.validationUtils.integerFormat(holder1.et_qty.getText().toString());

                double int_rate = App.validationUtils.doubleFormat(holder1.et_rate.getText().toString());

                double int_total = int_qty * int_rate;

                holder1.et_total.setText("" + int_total);

                list1.get(pos).setRate(String.valueOf(int_rate));
                list1.get(pos).setQty(String.valueOf(int_qty));
                list1.get(pos).setTotal_amount(String.valueOf(int_total));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder1.et_total.setText(list1.get(pos).getTotal_amount());

        holder1.et_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder1.check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

    public void addProductToList(SpareItemData e) {
     int TEMP_ID =  App.appPreference.getNewEntryCountWorklog();
        e.setScsp_key(TEMP_ID);
        this.list1.add(e);
        App.appPreference.setNewEntryCountWorklog(TEMP_ID -1);
        notifyDataSetChanged();
    }

    public void removeProductToList(int
                                            e) {
        this.list1.remove(e);
        notifyDataSetChanged();
    }

    public ArrayList<SpareItemData> getSpareList() {
        notifyDataSetChanged();
        return list1;
    }


    public Double getTotalSpareAmount() {
        Double v = 0.0;
        for (SpareItemData d : list1) {
            v = v + App.validationUtils.doubleFormat(d.getTotal_amount());
        }

        return v;
    }

    public int getTotalQty() {
        int v = 0;
        for (SpareItemData d : list1) {
            v = v + App.validationUtils.integerFormat(d.getQty());
        }
        return v;
    }

}