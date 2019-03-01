package com.isteer.service.activity;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.isteer.service.R;
import com.isteer.service.adapter.AddedProductAdapter;
import com.isteer.service.adapter.PopupProductAdapter;
import com.isteer.service.app.App;
import com.isteer.service.config.DSRAppConstant;
import com.isteer.service.config.SRVAppConstant;
import com.isteer.service.databinding.NewAddWorkLogBinding;
import com.isteer.service.model.MasterItemData;
import com.isteer.service.model.ServiceCallData;
import com.isteer.service.model.SpareItemData;
import com.isteer.service.model.WorkLogData;
import com.isteer.service.utility.DatePicker;
import com.isteer.service.utility.TimePicker;
import com.isteer.service.utility.ValidationUtils;
import com.isteer.service.volley.VolleyHttpRequest;
import com.isteer.service.volley.VolleyTaskListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SRVAddWorklogScreen extends AppCompatActivity implements View.OnClickListener, VolleyTaskListener {

    private static final int SERVICED_DATE = 101;
    private static final int START_TIME = 102;
    private static final int STOP_TIME = 103;
    public static boolean toRefresh = false;
    public static int currentScl_key;
    public static boolean isCallCompleted = false;
    private Integer[] worksArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private MaterialBetterSpinner spn_service_type, replacement_suggestion, status, no_of_workers;
    private WorkLogData workLogData = new WorkLogData();


    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);


    private TextInputEditText et_start_time;
    private TextInputEditText et_stop_time;
    private TextInputEditText et_serviced_by;
    private TextInputEditText et_serviced_date;
    private CheckBox cb_spare_used;
    private RecyclerView rv_added_product;
    private LinearLayoutManager recylerViewLayoutManager;
    private ArrayList<SpareItemData> addedProductsList = new ArrayList<SpareItemData>();
    private AddedProductAdapter addedProductAdapter;
    private LinearLayout ll_spare_used;
    public static String SERVICECALL_ID;
    private final int SYNC_NEW = 0;
    private Dialog dialog;
    private EditText search_box;
    private ImageView btn_close;
    private RecyclerView rv_list_product_search;
    private String str_search;
    private ArrayList<MasterItemData> productDataList = new ArrayList<MasterItemData>();
    private PopupProductAdapter popupProductAdapter;
    private final int ACTIVITY_ADDWORKLOG = 1;
    private ServiceCallData serviceCallData;
    private AutoCompleteTextView txt_products;


    TimePicker start_time, end_time;
    DatePicker service_date;
    private Button btn_save;
    private int TEMP_ID;
    private boolean refresh = false;
    private NewAddWorkLogBinding binding;
    private TextView tv_add_spare;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  setContentView(R.layout.new_add_work_log);

        binding = DataBindingUtil.setContentView(this, R.layout.new_add_work_log);

//        binding.txtProducts.setThreshold(1);
//
//        binding.txtProducts.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, gerProductDetails()));

        initvar();


        serviceCallData = getIntent().getParcelableExtra("worklog");

        TEMP_ID = App.appPreference.getNewEntryCount();
        binding.etRecivedAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double balance_total = App.validationUtils.doubleFormat(binding.getWorklog().getTotal_amount()) -
                        App.validationUtils.doubleFormat(binding.etRecivedAmount.getText().toString());
                binding.etBalanceAmount.setText(String.valueOf(balance_total > 0 ? balance_total : 0));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etLabourAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double total = App.validationUtils.doubleFormat(binding.etLabourAmount.getText().toString()) +
                        App.validationUtils.doubleFormat(binding.getWorklog().getVisiting_Charge()) +
                        App.validationUtils.doubleFormat(binding.getWorklog().getOther_Charge()) +
                        addedProductAdapter.getTotalSpareAmount();
                binding.etOverallTotal.setText(String.valueOf(total > 0 ? total : ""));
                binding.etRecivedAmount.setText("");
                binding.etBalanceAmount.setText("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.etOtherCharge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Double total = App.validationUtils.doubleFormat(binding.etOtherCharge.getText().toString())
                        + App.validationUtils.doubleFormat(binding.getWorklog().getLabour_Amount()) +
                        App.validationUtils.doubleFormat(binding.getWorklog().getVisiting_Charge()) +
                        addedProductAdapter.getTotalSpareAmount();
                binding.etOverallTotal.setText(String.valueOf(total > 0 ? total : ""));
                binding.etRecivedAmount.setText("");
                binding.etBalanceAmount.setText("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.etVisitCharge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double total = App.validationUtils.doubleFormat(binding.etVisitCharge.getText().toString()) + App.validationUtils.doubleFormat(binding.getWorklog().getLabour_Amount()) +
                        App.validationUtils.doubleFormat(binding.getWorklog().getOther_Charge()) + addedProductAdapter.getTotalSpareAmount();
                binding.etOverallTotal.setText(String.valueOf(total > 0 ? total : ""));
                binding.etRecivedAmount.setText("");
                binding.etBalanceAmount.setText("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (toRefresh) {
            clearTempEntries();
        }

        binding.checkAmc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });


        loadServiceDetails();
    }


    private void loadServiceDetails() {
        if (serviceCallData != null) {

            TEMP_ID = App.appPreference.getNewEntryCountWorklog();

            workLogData = new WorkLogData();
            workLogData.setScl_key(TEMP_ID);
            workLogData.setService_key(serviceCallData.getServicecall_key());
            workLogData.setEntered_by_name(App.appPreference.getUserName());
            workLogData.setServiced_by_name(App.appPreference.getUserName());
            workLogData.setServiced_by(App.appPreference.getUserId());
            workLogData.setEntered_by(App.appPreference.getUserId());
            binding.setLifecycleOwner(this);

            binding.setServices(serviceCallData);
            binding.setWorklog(workLogData);

            App.appPreference.setNewEntryCountWorklog(TEMP_ID - 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void clearTempEntries() {
        App.getINSTANCE().getRoomDB().daoSpareItem().removeTempProduct();
    }

    private void initvar() {

        service_date = findViewById(R.id.et_serviced_date);
        et_serviced_by = findViewById(R.id.et_serviced_by);
        start_time = findViewById(R.id.start_time);
        end_time = findViewById(R.id.stop_time);
        cb_spare_used = findViewById(R.id.cb_spare_used);
        rv_added_product = findViewById(R.id.rv_added_product);
        ll_spare_used = findViewById(R.id.ll_spare_used);
        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
        txt_products = (AutoCompleteTextView) findViewById(R.id.txt_products);
        ((TextView) findViewById(R.id.header_title)).setText("Add Work Log");

        tv_add_spare = findViewById(R.id.tv_add_spare);
        tv_add_spare.setOnClickListener(this);
        tv_add_spare.setVisibility(View.GONE);

        recylerViewLayoutManager = new LinearLayoutManager(this);
        rv_added_product.setLayoutManager(recylerViewLayoutManager);
        addedProductAdapter = new AddedProductAdapter(this);
        rv_added_product.setAdapter(addedProductAdapter);


        no_of_workers = findViewById(R.id.no_of_engineers);
        no_of_workers.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line, worksArray));

        spn_service_type = findViewById(R.id.spn_service_type);
        spn_service_type.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.serviceType)));

        replacement_suggestion = findViewById(R.id.replacement);
        replacement_suggestion.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.replacement)));

        status = findViewById(R.id.status);
        status.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.status)));

        service_date.setOnClickListener(new DatePicker.CustomOnclickListener(this));
        start_time.setOnClickListener(new TimePicker.TimeOnclickListener(this));
        end_time.setOnClickListener(new TimePicker.TimeOnclickListener(this));

        txt_products.setThreshold(1);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, gerProductDetails());
        txt_products.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        binding.checkNewPump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    binding.newPumpValue.setEnabled(true);
                    binding.getWorklog().setNew_pump("yes");
                } else {
                    binding.newPumpValue.setEnabled(false);
                    binding.getWorklog().setNew_pump("no");
                    binding.newPumpValue.getText().clear();
                }
            }
        });
        binding.checkSpare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    binding.getWorklog().setSpares("yes");
                    binding.spareValue.setEnabled(true);
                } else {
                    binding.spareValue.setEnabled(false);
                    binding.getWorklog().setSpares("no");
                    binding.spareValue.getText().clear();
                }
            }
        });
        binding.checkAmc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.getWorklog().setAmc("yes");
                    binding.amcValue.setEnabled(true);
                } else {
                    binding.amcValue.setEnabled(false);
                    binding.getWorklog().setAmc("no");
                    binding.amcValue.getText().clear();
                }
            }
        });
        binding.checkIc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.getWorklog().setTo_ic("yes");
                    binding.icValue.setEnabled(true);

                } else {
                    binding.getWorklog().setTo_ic("no");
                    binding.icValue.setEnabled(false);
                    binding.icValue.getText().clear();
                }
            }
        });

        cb_spare_used.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cb_spare_used.isChecked()) {
                    tv_add_spare.setVisibility(View.VISIBLE);
                    ll_spare_used.setVisibility(View.VISIBLE);
                } else {
                    tv_add_spare.setVisibility(View.GONE);
                    ll_spare_used.setVisibility(View.GONE);
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

    private void refreshListAddedProducts(SpareItemData spareItemData) {
        addedProductAdapter.addProductToList(spareItemData);
    }

    public void addToSelectedProducts(MasterItemData tempAddedProduct) {

        SpareItemData spareItemData = new SpareItemData();

        spareItemData.setScl_key("" + TEMP_ID);
        spareItemData.setService_key("" + serviceCallData.getServicecall_key());
        spareItemData.setSpare_part_code(tempAddedProduct.getITEM_CODE());
        spareItemData.setSpare_part_name(tempAddedProduct.getSHORT_NAME());
        spareItemData.setSpare_part_desc(tempAddedProduct.getSHORT_DESC());
        spareItemData.setRate("" + tempAddedProduct.getRate());
        spareItemData.setQty("0");
        spareItemData.setIs_synced_to_server(0);

        refreshListAddedProducts(spareItemData);
    }

    public void removeFromAddedProductList(int i) {
        addedProductAdapter.removeProductToList(i);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_add_spare:
                popUpSearchProduct();
                break;

            case R.id.btn_save:
                saveWorklog();
                break;

        }
    }

    private void saveWorklog() {


        workLogData = binding.getWorklog();

        workLogData.setService_key(serviceCallData.getServicecall_key());
        workLogData.setEntered_by(App.appPreference.getUserId());
        workLogData.setEntered_by_name(workLogData.getServiced_by_name());
        if (status.getText().toString().equalsIgnoreCase(SRVAppConstant.SERVICE_LOG_STATUS[0])) {
            workLogData.setStatus(1);
        } else if (status.getText().toString().equalsIgnoreCase(SRVAppConstant.SERVICE_LOG_STATUS[1])) {
            workLogData.setStatus(2);
        } else if (status.getText().toString().equalsIgnoreCase(SRVAppConstant.SERVICE_LOG_STATUS[2])) {
            workLogData.setStatus(3);
        } else if (status.getText().toString().equalsIgnoreCase(SRVAppConstant.SERVICE_LOG_STATUS[3])) {
            workLogData.setStatus(4);
        } else if (status.getText().toString().equalsIgnoreCase(SRVAppConstant.SERVICE_LOG_STATUS[4])) {
            workLogData.setStatus(5);
        }

        if (App.validationUtils.isValidWorkLog(workLogData)) {
            if (!App.validationUtils.isValidTimeSelection(workLogData)) {
                Toast.makeText(this, "Check time selection!!", Toast.LENGTH_SHORT).show();
                return;
            }

            workLogData.setProject_key("0");


            try {


                workLogData.setMi_key(App.getINSTANCE().getRoomDB().daoMasterItem().getMikey(txt_products.getText().toString()).split("-")[0]);
                System.out.println("----mi key-------------------"+App.getINSTANCE().getRoomDB().daoMasterItem().getMikey(txt_products.getText().toString()));

            } catch (Exception e) {

                e.printStackTrace();
            } finally {

                workLogData.setMi_key(App.getINSTANCE().getRoomDB().daoMasterItem().getMikey(txt_products.getText().toString()).split("-")[0]);
                System.out.println("----mi key-------------------"+App.getINSTANCE().getRoomDB().daoMasterItem().getMikey(txt_products.getText().toString()));

            }



            workLogData.setIs_spare_parts_needed(binding.cbSpareUsed.isChecked() ? "Y" : "N");
            workLogData.setSpares_total_amount("" + addedProductAdapter.getTotalSpareAmount());

            Long isInserted = App.getINSTANCE().getRoomDB().daoWorkLog().insertWorkLogData(workLogData);

            if (isInserted != 0) {
                App.getINSTANCE().getRoomDB().daoSpareItem().insertSpareItemData(addedProductAdapter.getSpareList());
                if (App.appUtils.isNetAvailable()) {

                    VolleyHttpRequest.makeVolleyPost(this, App.appPreference.getBaseUrl().concat("/iSteerService/addWorkLogs"), getJsonInput(), "WORKLOG");

                } else {
                    Toast.makeText(this, "No Connectivity.", Toast.LENGTH_SHORT).show();
                    gotoAddWorkLog();
                }
            }

        } else

        {
            Toast.makeText(this, "Please fill all fields!!", Toast.LENGTH_SHORT).show();
        }

    }

    public void popUpSearchProduct() {
        dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_search_product);
        dialog.setCanceledOnTouchOutside(true);

        recylerViewLayoutManager = new LinearLayoutManager(this);
        rv_list_product_search = (RecyclerView) dialog.findViewById(R.id.rv_list_product_search);
        search_box = dialog.findViewById(R.id.search_box);
        btn_close = dialog.findViewById(R.id.btn_close);

        rv_list_product_search.setLayoutManager(recylerViewLayoutManager);

        refreshlistPopupProducts();

        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                str_search = search_box.getText().toString();
                if (str_search != null && str_search.length() != 0) {
                    refreshlistPopupProducts(str_search + "%");
                } else {
                    refreshlistPopupProducts();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
            }
        });
    }

    public void refreshlistPopupProducts() {

        productDataList.clear();
        productDataList = (ArrayList<MasterItemData>)App.getINSTANCE().getRoomDB().daoMasterItem().fetchAllMasterItemData();
        if (productDataList == null || productDataList.size() == 0) {
            Toast.makeText(this, "No product available", Toast.LENGTH_SHORT).show();

        } else {
            popupProductAdapter = new PopupProductAdapter(this, productDataList, ACTIVITY_ADDWORKLOG);
            rv_list_product_search.setAdapter(popupProductAdapter);
            dialog.show();
        }
    }

    public void refreshlistPopupProducts(String search) {
        productDataList.clear();
        productDataList = (ArrayList<MasterItemData>)App.getINSTANCE().getRoomDB().daoMasterItem().fetchLikeProduct(search);
        if (productDataList == null || productDataList.size() == 0) {
            Toast.makeText(this, "No such product ", Toast.LENGTH_SHORT).show();
        } else {
            popupProductAdapter = new PopupProductAdapter(this, productDataList, ACTIVITY_ADDWORKLOG);
            rv_list_product_search.setAdapter(popupProductAdapter);
        }
    }


    private JSONObject getJsonInput() {
        JSONObject outerJson = new JSONObject();
        JSONArray innerArray = new JSONArray();
        JSONArray sparePart = new JSONArray();
        JSONObject worklog = new JSONObject();
        try {
            worklog = new JSONObject(new Gson().toJson(workLogData));
            worklog.put("user_id", App.appPreference.getUserId());


            if (workLogData.getScl_key() < 0) {
                worklog.put("dummy_key", workLogData.getScl_key());
                worklog.put("scl_key", "");
            } else {
                worklog.put("dummy_key", "");
            }

            worklog.put("visited_date", ValidationUtils.dateFormater(workLogData.getVisited_date(), "dd/MM/yyyy", "yyyyMMdd"));


            for (SpareItemData date : addedProductAdapter.getSpareList()) {
                sparePart.put(new JSONObject(new Gson().toJson(date)).put("dummy_key1", (date.getScsp_key() > 0 ? "" : date.getScsp_key())));
            }
            worklog.put("spare_parts", sparePart);
            innerArray.put(worklog);
            outerJson.put("vals", innerArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }



        return outerJson;
    }


    @Override
    public void handleResult(String method_name, JSONObject response) throws JSONException {

        System.out.println("-----kannan json responds----------" + response);


        if (method_name.equalsIgnoreCase("WORKLOG")) {

            if (response == null) {

                gotoSRVMenuScreen();

                return;
            }

            if (response.has(DSRAppConstant.KEY_STATUS) && response.getInt(DSRAppConstant.KEY_STATUS) == 1) {

                JSONArray outterJson = response.getJSONArray("data");

                for (int i = 0; i < outterJson.length(); i++) {

                    JSONObject worklog = outterJson.getJSONObject(i);


                    if (worklog.has("is_success") && worklog.getString("is_success").equalsIgnoreCase("1")) {


                        String dummy_key = worklog.getString("dummy_key");
                        String scl_key = worklog.getString("scl_key");

                        App.getINSTANCE().getRoomDB().daoWorkLog().updateWorklogKey(dummy_key, scl_key);
                        App.getINSTANCE().getRoomDB().daoSpareItem().upadteWorkLogKey("" + dummy_key, "" + scl_key);


                        JSONArray spareArray = worklog.getJSONArray("spare_parts");
                        for (int j = 0; j < spareArray.length(); j++) {
                            JSONObject spareObject = spareArray.getJSONObject(j);

                            String spare_dummy_key = spareObject.getString("dummy_key");
                            String scsp_key = spareObject.getString("scsp_key");

                            App.getINSTANCE().getRoomDB().daoSpareItem().upadteSpareKey("" + spare_dummy_key, "" + scsp_key);


                        }


                    }

                }

                Toast.makeText(getApplicationContext(), "Posted Successfully", Toast.LENGTH_SHORT).show();

//                gotoSRVMenuScreen();

                finish();

            }


        }
    }

    private void gotoAddWorkLog() {
        finish();
        startActivity(new Intent(this, SRVWorklogListScreen.class));
    }

    private void gotoSRVMenuScreen() {
        finish();
        startActivity(new Intent(this, MenuScreen.class));
    }


    @Override
    public void handleError(String method_name, VolleyError e) {

    }

    private String[] gerProductDetails() {
        List<MasterItemData> d = App.getINSTANCE().getRoomDB().daoMasterItem().fetchAllMasterItemData();
        String[] c = new String[d.size()];
        for (int i = 0; i < d.size(); i++) {
            c[i] = d.get(i).getSHORT_NAME()/*.concat("-").concat(d.get(i).getREMARKS())*/;
        }
        return c;
    }
}
