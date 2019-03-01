package com.isteer.service.activity;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.isteer.service.R;
import com.isteer.service.databinding.NewAddWorkLogBinding;
import com.isteer.service.model.MasterItemData;
import com.isteer.service.model.WorkLogDataOld;
import com.isteer.service.utility.DatePicker;
import com.isteer.service.utility.TimePicker;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;


public class AddWorklog extends AppCompatActivity {

    private Integer[] worksArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private MaterialBetterSpinner spn_service_type, replacement_suggestion, status, no_of_workers;
    private TimePicker start_time, end_time;
    private DatePicker service_date;
    private ArrayList<MasterItemData> addedProductsList;
    private WorkLogDataOld workLogData = new WorkLogDataOld();
    private CheckBox cb_spare_used;
    private RecyclerView rv_added_product;
    private LinearLayout ll_spare_used;

    public static String SERVICECALL_ID;
    private final int SYNC_NEW = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_add_work_log);
      //  NewAddWorkLogBinding binding = DataBindingUtil.setContentView(this,R.layout.new_add_work_log);


        initvar();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initvar() {

        no_of_workers = findViewById(R.id.no_of_engineers);
        spn_service_type = findViewById(R.id.spn_service_type);
        spn_service_type.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.serviceType)));
        no_of_workers.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line, worksArray));
        replacement_suggestion = findViewById(R.id.replacement);
        replacement_suggestion.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.replacement)));
        status = findViewById(R.id.status);
        status.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.status)));

        start_time = findViewById(R.id.start_time);
        end_time = findViewById(R.id.stop_time);
        service_date = findViewById(R.id.et_serviced_date);


        cb_spare_used = findViewById(R.id.cb_spare_used);
        rv_added_product = findViewById(R.id.rv_added_product);
        ll_spare_used = findViewById(R.id.ll_spare_used);

        service_date.setOnClickListener(new DatePicker.CustomOnclickListener(this));
        start_time.setOnClickListener(new TimePicker.TimeOnclickListener(this));
        end_time.setOnClickListener(new TimePicker.TimeOnclickListener(this));

        findViewById(R.id.btn_header_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.btn_header_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                gotoHome();
            }
        });


        cb_spare_used.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_spare_used.isChecked()) {
                    gotoSelectSparesUsed();
                }
            }
        });

        no_of_workers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                workLogData.setService_eng_count(worksArray[i]);
                no_of_workers.setText(workLogData.getService_eng_count());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_service_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                workLogData.setService_type(getResources().getStringArray(R.array.serviceType)[i]);
                status.setText(workLogData.getService_type());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        replacement_suggestion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                workLogData.setReplace_suggestion(getResources().getStringArray(R.array.replacement)[i]);
                status.setText(workLogData.getReplace_suggestion());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void gotoHome() {
        startActivity(new Intent(this, MenuScreen.class));
    }


    private void gotoSelectSparesUsed() {

        SRVAddProductScreen.SERV_ID = SERVICECALL_ID;
        startActivity(new Intent(this, SRVAddProductScreen.class)
                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));

    }
}