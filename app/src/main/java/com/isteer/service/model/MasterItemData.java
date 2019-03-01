package com.isteer.service.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "master_item")
public class MasterItemData {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @SerializedName("mi_key")
    @ColumnInfo(name = "mi_key")
    private String MI_KEY ;

    @SerializedName("u_item_code")
    @ColumnInfo(name = "u_item_code")
    private String u_item_code;

    @ColumnInfo(name = "created_on")
    @SerializedName("created_on")
    private String created_on = "";

    @ColumnInfo(name = "grade_desc")
    @SerializedName("grade_desc")
    private String grade_desc = "";

    @ColumnInfo(name = "item_type_key")
    @SerializedName("item_type_key")
    private String item_type_key = "";

    @ColumnInfo(name = "long_desc")
    @SerializedName("long_desc")
    private String LONG_DESC = "";

    @ColumnInfo(name = "modified_by")
    @SerializedName("modified_by")
    private String modified_by = "";

    @ColumnInfo(name = "list_price")
    @SerializedName("list_price")
    private String LIST_PRICE = "";

    @ColumnInfo(name = "lp_tax_key")
    @SerializedName("lp_tax_key")
    private String lp_tax_key = "";

    @ColumnInfo(name = "max_inventar")
    @SerializedName("max_inventar")
    private String MAX_INVENTAR = "";

    @ColumnInfo(name = "item_moving_status")
    @SerializedName("item_moving_status")
    private String item_moving_status = "";

    @ColumnInfo(name = "is_non_standard")
    @SerializedName("is_non_standard")
    private String is_non_standard = "";

    @ColumnInfo(name = "thickness_dia_key")
    @SerializedName("thickness_dia_key")
    private String thickness_dia_key = "";

    @ColumnInfo(name = "type")
    @SerializedName("type")
    private String TYPE = "";

    @ColumnInfo(name = "item_type")
    @SerializedName("item_type")
    private String item_type = "";

    @ColumnInfo(name = "created_by")
    @SerializedName("created_by")
    private String created_by = "";

    @ColumnInfo(name = "modified_on")
    @SerializedName("modified_on")
    private String modified_on = "";

    @ColumnInfo(name = "op_tax_key")
    @SerializedName("op_tax_key")
    private String op_tax_key = "";

    @ColumnInfo(name = "bom")
    @SerializedName("bom")
    private String BOM = "";

    @ColumnInfo(name = "color_key")
    @SerializedName("color_key")
    private String color_key = "";

    @ColumnInfo(name = "short_desc")
    @SerializedName("short_desc")
    private String SHORT_DESC = "";

    @ColumnInfo(name = "disorder")
    @SerializedName("disorder")
    private String DISORDER = "";

    @ColumnInfo(name = "item_pack_to_base_conversion")
    @SerializedName("item_pack_to_base_conversion")
    private String item_pack_to_base_conversion = "";

    @ColumnInfo(name = "tally_item_desc")
    @SerializedName("tally_item_desc")
    private String tally_item_desc = "";

    @ColumnInfo(name = "material_type_key")
    @SerializedName("material_type_key")
    private String material_type_key = "";

    @ColumnInfo(name = "remarks")
    @SerializedName("remarks")
    private String REMARKS = "";

    @ColumnInfo(name = "active_status")
    @SerializedName("active_status")
    private String ACTIVE_STATUS = "";

    @ColumnInfo(name = "min_reodr_level")
    @SerializedName("min_reodr_level")
    private String MIN_REODR_LEVEL = "";

    @ColumnInfo(name = "short_name")
    @SerializedName("short_name")
    private String SHORT_NAME = "";

    @ColumnInfo(name = "mi_post_status")
    @SerializedName("mi_post_status")
    private String mi_post_status = "";

    @SerializedName("discount_per")
    @ColumnInfo(name = "discount_per")
    private String discount_per = "";

    @SerializedName("rate")
    @ColumnInfo(name = "rate")
    private String rate = "0.00";

    @SerializedName("item_base_unit")
    @ColumnInfo(name = "item_base_unit")
    private String item_base_unit = "";

    @SerializedName("item_key")
    @ColumnInfo(name = "item_key")
    private String item_key = "";

    @SerializedName("max_stock_level")
    @ColumnInfo(name = "max_stock_level")
    private String MAX_STOCK_LEVEL = "";

    @SerializedName("tariff_sh_no")
    @ColumnInfo(name = "tariff_sh_no")
    private String tariff_sh_no = "";

    @SerializedName("material_type_code")
    @ColumnInfo(name = "material_type_code")
    private String MATERIAL_TYPE_CODE = "";

    @SerializedName("manu_key")
    @ColumnInfo(name = "manu_key")
    private String manu_key = "";

    @SerializedName("basic_pur_cost")
    @ColumnInfo(name = "basic_pur_cost")
    private String basic_pur_cost = "";

    @SerializedName("category7_key")
    @ColumnInfo(name = "category7_key")
    private String category7_key = "";

    @SerializedName("min_inventary")
    @ColumnInfo(name = "min_inventary")
    private String MIN_INVENTARY = "";

    @SerializedName("color_code")
    @ColumnInfo(name = "color_code")
    private String COLOR_CODE = "";

    @SerializedName("catalog_no")
    @ColumnInfo(name = "catalog_no")
    private String CATALOG_NO = "";

    @SerializedName("mrp_rate")
    @ColumnInfo(name = "mrp_rate")
    private String mrp_rate = "";

    @SerializedName("thickness_dia")
    @ColumnInfo(name = "thickness_dia")
    private String THICKNESS_DIA = "";

    @SerializedName("stock_trnf_price")
    @ColumnInfo(name = "stock_trnf_price")
    private String stock_trnf_price = "";

    @SerializedName("manu_code")
    @ColumnInfo(name = "manu_code")
    private String MANU_CODE = "";

    @SerializedName("display_code")
    @ColumnInfo(name = "display_code")
    private String display_code = "";

    @SerializedName("max_reodr_qty")
    @ColumnInfo(name = "max_reodr_qty")
    private String MAX_REODR_QTY = "";

    @SerializedName("item_code")
    @ColumnInfo(name = "item_code")
    private String ITEM_CODE = "";

    @SerializedName("min_reodr_qty")
    @ColumnInfo(name = "min_reodr_qty")
    private String MIN_REODR_QTY = "";

    @SerializedName("item_package_unit")
    @ColumnInfo(name = "item_package_unit")
    private String item_package_unit = "";



    //is_synced_to_server values
    //	1 - for server values
    //  0 - for modified entries

    @ColumnInfo(name = "is_synced_to_server")
    private int is_synced_to_server = 1;


    // entry_key used to differ insert other modified entries
    // -1 for temprorary entries need to remove when you enter inside
    // 0 for default
    // servicecall id for particular worklog entries

 @ColumnInfo(name = "qty")
    private String qty = "0";

    @SerializedName("standard_price")
    @ColumnInfo(name = "standard_price")
    private String standard_price;


    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    @NonNull
    public String getU_item_code() {
        return u_item_code;
    }

    public void setU_item_code(@NonNull String u_item_code) {
        this.u_item_code = u_item_code;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getGrade_desc() {
        return grade_desc;
    }

    public void setGrade_desc(String grade_desc) {
        this.grade_desc = grade_desc;
    }

    public String getItem_type_key() {
        return item_type_key;
    }

    public void setItem_type_key(String item_type_key) {
        this.item_type_key = item_type_key;
    }

    public String getLONG_DESC() {
        return LONG_DESC;
    }

    public void setLONG_DESC(String LONG_DESC) {
        this.LONG_DESC = LONG_DESC;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getLIST_PRICE() {
        return LIST_PRICE;
    }

    public void setLIST_PRICE(String LIST_PRICE) {
        this.LIST_PRICE = LIST_PRICE;
    }

    public String getLp_tax_key() {
        return lp_tax_key;
    }

    public void setLp_tax_key(String lp_tax_key) {
        this.lp_tax_key = lp_tax_key;
    }

    public String getMAX_INVENTAR() {
        return MAX_INVENTAR;
    }

    public void setMAX_INVENTAR(String MAX_INVENTAR) {
        this.MAX_INVENTAR = MAX_INVENTAR;
    }

    public String getItem_moving_status() {
        return item_moving_status;
    }

    public void setItem_moving_status(String item_moving_status) {
        this.item_moving_status = item_moving_status;
    }

    public String getIs_non_standard() {
        return is_non_standard;
    }

    public void setIs_non_standard(String is_non_standard) {
        this.is_non_standard = is_non_standard;
    }

    public String getThickness_dia_key() {
        return thickness_dia_key;
    }

    public void setThickness_dia_key(String thickness_dia_key) {
        this.thickness_dia_key = thickness_dia_key;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getModified_on() {
        return modified_on;
    }

    public void setModified_on(String modified_on) {
        this.modified_on = modified_on;
    }

    public String getOp_tax_key() {
        return op_tax_key;
    }

    public void setOp_tax_key(String op_tax_key) {
        this.op_tax_key = op_tax_key;
    }

    public String getBOM() {
        return BOM;
    }

    public void setBOM(String BOM) {
        this.BOM = BOM;
    }

    public String getColor_key() {
        return color_key;
    }

    public void setColor_key(String color_key) {
        this.color_key = color_key;
    }

    public String getSHORT_DESC() {
        return SHORT_DESC;
    }

    public void setSHORT_DESC(String SHORT_DESC) {
        this.SHORT_DESC = SHORT_DESC;
    }

    public String getDISORDER() {
        return DISORDER;
    }

    public void setDISORDER(String DISORDER) {
        this.DISORDER = DISORDER;
    }

    public String getItem_pack_to_base_conversion() {
        return item_pack_to_base_conversion;
    }

    public void setItem_pack_to_base_conversion(String item_pack_to_base_conversion) {
        this.item_pack_to_base_conversion = item_pack_to_base_conversion;
    }

    public String getTally_item_desc() {
        return tally_item_desc;
    }

    public void setTally_item_desc(String tally_item_desc) {
        this.tally_item_desc = tally_item_desc;
    }

    public String getMaterial_type_key() {
        return material_type_key;
    }

    public void setMaterial_type_key(String material_type_key) {
        this.material_type_key = material_type_key;
    }

    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }

    public String getACTIVE_STATUS() {
        return ACTIVE_STATUS;
    }

    public void setACTIVE_STATUS(String ACTIVE_STATUS) {
        this.ACTIVE_STATUS = ACTIVE_STATUS;
    }

    public String getMIN_REODR_LEVEL() {
        return MIN_REODR_LEVEL;
    }

    public void setMIN_REODR_LEVEL(String MIN_REODR_LEVEL) {
        this.MIN_REODR_LEVEL = MIN_REODR_LEVEL;
    }

    public String getSHORT_NAME() {
        return SHORT_NAME;
    }

    public void setSHORT_NAME(String SHORT_NAME) {
        this.SHORT_NAME = SHORT_NAME;
    }

    public String getMi_post_status() {
        return mi_post_status;
    }

    public void setMi_post_status(String mi_post_status) {
        this.mi_post_status = mi_post_status;
    }

    public String getDiscount_per() {
        return discount_per;
    }

    public void setDiscount_per(String discount_per) {
        this.discount_per = discount_per;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getItem_base_unit() {
        return item_base_unit;
    }

    public void setItem_base_unit(String item_base_unit) {
        this.item_base_unit = item_base_unit;
    }

    public String getItem_key() {
        return item_key;
    }

    public void setItem_key(String item_key) {
        this.item_key = item_key;
    }

    public String getMAX_STOCK_LEVEL() {
        return MAX_STOCK_LEVEL;
    }

    public void setMAX_STOCK_LEVEL(String MAX_STOCK_LEVEL) {
        this.MAX_STOCK_LEVEL = MAX_STOCK_LEVEL;
    }

    public String getTariff_sh_no() {
        return tariff_sh_no;
    }

    public void setTariff_sh_no(String tariff_sh_no) {
        this.tariff_sh_no = tariff_sh_no;
    }

    public String getMATERIAL_TYPE_CODE() {
        return MATERIAL_TYPE_CODE;
    }

    public void setMATERIAL_TYPE_CODE(String MATERIAL_TYPE_CODE) {
        this.MATERIAL_TYPE_CODE = MATERIAL_TYPE_CODE;
    }

    public String getManu_key() {
        return manu_key;
    }

    public void setManu_key(String manu_key) {
        this.manu_key = manu_key;
    }

    public String getBasic_pur_cost() {
        return basic_pur_cost;
    }

    public void setBasic_pur_cost(String basic_pur_cost) {
        this.basic_pur_cost = basic_pur_cost;
    }

    public String getCategory7_key() {
        return category7_key;
    }

    public void setCategory7_key(String category7_key) {
        this.category7_key = category7_key;
    }

    public String getMIN_INVENTARY() {
        return MIN_INVENTARY;
    }

    public void setMIN_INVENTARY(String MIN_INVENTARY) {
        this.MIN_INVENTARY = MIN_INVENTARY;
    }

    public String getCOLOR_CODE() {
        return COLOR_CODE;
    }

    public void setCOLOR_CODE(String COLOR_CODE) {
        this.COLOR_CODE = COLOR_CODE;
    }

    public String getCATALOG_NO() {
        return CATALOG_NO;
    }

    public void setCATALOG_NO(String CATALOG_NO) {
        this.CATALOG_NO = CATALOG_NO;
    }

    public String getMrp_rate() {
        return mrp_rate;
    }

    public void setMrp_rate(String mrp_rate) {
        this.mrp_rate = mrp_rate;
    }

    public String getTHICKNESS_DIA() {
        return THICKNESS_DIA;
    }

    public void setTHICKNESS_DIA(String THICKNESS_DIA) {
        this.THICKNESS_DIA = THICKNESS_DIA;
    }

    public String getStock_trnf_price() {
        return stock_trnf_price;
    }

    public void setStock_trnf_price(String stock_trnf_price) {
        this.stock_trnf_price = stock_trnf_price;
    }

    public String getMANU_CODE() {
        return MANU_CODE;
    }

    public void setMANU_CODE(String MANU_CODE) {
        this.MANU_CODE = MANU_CODE;
    }

    public String getDisplay_code() {
        return display_code;
    }

    public void setDisplay_code(String display_code) {
        this.display_code = display_code;
    }

    public String getMAX_REODR_QTY() {
        return MAX_REODR_QTY;
    }

    public void setMAX_REODR_QTY(String MAX_REODR_QTY) {
        this.MAX_REODR_QTY = MAX_REODR_QTY;
    }

    public String getITEM_CODE() {
        return ITEM_CODE;
    }

    public void setITEM_CODE(String ITEM_CODE) {
        this.ITEM_CODE = ITEM_CODE;
    }

    public String getMIN_REODR_QTY() {
        return MIN_REODR_QTY;
    }

    public void setMIN_REODR_QTY(String MIN_REODR_QTY) {
        this.MIN_REODR_QTY = MIN_REODR_QTY;
    }

    public String getItem_package_unit() {
        return item_package_unit;
    }

    public void setItem_package_unit(String item_package_unit) {
        this.item_package_unit = item_package_unit;
    }

    public String getStandard_price() {
        return standard_price;
    }

    public void setStandard_price(String standard_price) {
        this.standard_price = standard_price;
    }

    @NonNull
    public String getMI_KEY() {
        return MI_KEY;
    }

    public void setMI_KEY(@NonNull String MI_KEY) {
        this.MI_KEY = MI_KEY;
    }

    public int getIs_synced_to_server() {
        return is_synced_to_server;
    }

    public void setIs_synced_to_server(int is_synced_to_server) {
        this.is_synced_to_server = is_synced_to_server;
    }

    @Override
    public String toString() {
        return "ClassPojo [u_item_code = " + u_item_code + ", created_on = " + created_on + ", grade_desc = " + grade_desc + ", item_type_key = " + item_type_key + ", LONG_DESC = " + LONG_DESC + ", modified_by = " + modified_by + ", LIST_PRICE = " + LIST_PRICE + ", lp_tax_key = " + lp_tax_key + ", MAX_INVENTAR = " + MAX_INVENTAR + ", item_moving_status = " + item_moving_status + ", is_non_standard = " + is_non_standard + ", thickness_dia_key = " + thickness_dia_key + ", TYPE = " + TYPE + ", item_type = " + item_type + ", created_by = " + created_by + ", modified_on = " + modified_on + ", op_tax_key = " + op_tax_key + ", BOM = " + BOM + ", color_key = " + color_key + ", SHORT_DESC = " + SHORT_DESC + ", DISORDER = " + DISORDER + ", item_pack_to_base_conversion = " + item_pack_to_base_conversion + ", tally_item_desc = " + tally_item_desc + ", material_type_key = " + material_type_key + ", REMARKS = " + REMARKS + ", ACTIVE_STATUS = " + ACTIVE_STATUS + ", MIN_REODR_LEVEL = " + MIN_REODR_LEVEL + ", SHORT_NAME = " + SHORT_NAME + ", mi_post_status = " + mi_post_status + ", discount_per = " + discount_per + ", rate = " + rate + ", item_base_unit = " + item_base_unit + ", item_key = " + item_key + ", MAX_STOCK_LEVEL = " + MAX_STOCK_LEVEL + ", tariff_sh_no = " + tariff_sh_no + ", MATERIAL_TYPE_CODE = " + MATERIAL_TYPE_CODE + ", manu_key = " + manu_key + ", basic_pur_cost = " + basic_pur_cost + ", category7_key = " + category7_key + ", MIN_INVENTARY = " + MIN_INVENTARY + ", COLOR_CODE = " + COLOR_CODE + ", CATALOG_NO = " + CATALOG_NO + ", mrp_rate = " + mrp_rate + ", THICKNESS_DIA = " + THICKNESS_DIA + ", stock_trnf_price = " + stock_trnf_price + ", MANU_CODE = " + MANU_CODE + ", display_code = " + display_code + ", MAX_REODR_QTY = " + MAX_REODR_QTY + ", ITEM_CODE = " + ITEM_CODE + ", MIN_REODR_QTY = " + MIN_REODR_QTY + ", item_package_unit = " + item_package_unit + ", standard_price = " + standard_price + ", MI_KEY = " + MI_KEY + "]";
    }
}
