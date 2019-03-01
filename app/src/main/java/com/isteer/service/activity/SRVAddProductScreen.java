package com.isteer.service.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.isteer.service.R;

/**
 * Created by Home on 6/6/2018.
 */

public class SRVAddProductScreen extends AppCompatActivity {

    public static String SERV_ID;
    private EditText search_box;
    private ImageView btn_close;
    private RecyclerView rv_list_product_search;
    private RecyclerView rv_list_added_product;
    private LinearLayoutManager recylerViewLayoutManager;
    private LinearLayoutManager recylerViewLayoutManager2;
    private String str_search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_search_product);

        initvar();
    }

    private void initvar() {

        recylerViewLayoutManager = new LinearLayoutManager(this);
        rv_list_product_search = findViewById(R.id.rv_list_product_search);
        rv_list_product_search.setLayoutManager(recylerViewLayoutManager);

        recylerViewLayoutManager2 = new LinearLayoutManager(this);
       // rv_list_added_product = findViewById(R.id.rv_list_added_product);
        rv_list_added_product.setLayoutManager(recylerViewLayoutManager);

        search_box = (EditText) findViewById(R.id.search_box);
        btn_close = (ImageView) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoAddWorkLogScreen();
            }
        });

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
            }
        });
    }

    private void gotoAddWorkLogScreen() {
        startActivity(new Intent(this,SRVAddWorklogScreen.class));
    }

    public void refreshlistPopupProducts() {

        /*productDataList.clear();
        productDataList = (ArrayList<ProductList>) DSMApp.getINSTANCE().getRoomDB().daoproductList().fetchAllProductList();
        if (productDataList == null || productDataList.size() == 0) {

        } else {
            popupProductAdapter = new AddProductAdapter(this, productDataList, ACTIVITYID);
            list_popup_product_search.setAdapter(popupProductAdapter);
        }*/
    }

    public void refreshlistPopupProducts(String search) {
        /*productDataList.clear();
        productDataList = (ArrayList<ProductList>) DSMApp.getINSTANCE().getRoomDB().daoproductList().fetchLikeProduct(search);
        if (productDataList == null || productDataList.size() == 0) {
            Toast.makeText(this, "No such product ", Toast.LENGTH_SHORT).show();
        } else {
            popupProductAdapter = new AddProductAdapter(this, productDataList, ACTIVITYID);
            list_popup_product_search.setAdapter(popupProductAdapter);
        }*/
    }

}
