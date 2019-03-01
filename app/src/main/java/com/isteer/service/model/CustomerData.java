package com.isteer.service.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "aerp_contact_master")
public class CustomerData {


    @ColumnInfo(name = "cmkey")
    @PrimaryKey
    private String cmkey;


    @ColumnInfo(name = "userkey")
    private String userkey;


    @ColumnInfo(name = "cmp_phone1")
    private String cmp_phone1;


    @ColumnInfo(name = "cmp_phone2")
    private String cmp_phone2;


    @ColumnInfo(name = "cmp_email")
    private String cmp_email;


    @ColumnInfo(name = "company_name")
    private String company_name;


    @ColumnInfo(name = "address1")
    private String address1;


    @ColumnInfo(name = "address2")
    private String address2;


    @ColumnInfo(name = "address3")
    private String address3;


    @ColumnInfo(name = "area")
    private String area;


    @ColumnInfo(name = "city")
    private String city;


    @ColumnInfo(name = "state")
    private String state;


    @ColumnInfo(name = "country")
    private String country;


    @ColumnInfo(name = "pincode")
    private String pincode;


    @ColumnInfo(name = "email")
    private String email;


    @ColumnInfo(name = "phone1")
    private String phone1;


    @ColumnInfo(name = "phone2")
    private String phone2;


    @ColumnInfo(name = "website")
    private String website;


    @ColumnInfo(name = "industry")
    private String industry;


    @ColumnInfo(name = "category1")
    private String category1;


    @ColumnInfo(name = "category2")
    private String category2;


    @ColumnInfo(name = "category3")
    private String category3;


    @ColumnInfo(name = "display_code")
    private String display_code;


    @ColumnInfo(name = "area_name")
    private String area_name;


    @ColumnInfo(name = "first_name")
    private String first_name;

    @ColumnInfo(name ="is_syncTo_server")
    private int is_syncTo_server = 1;




    public String getCmkey() {
        return cmkey;
    }

    public void setCmkey(String cmkey) {
        this.cmkey = cmkey;
    }

    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public String getCmp_phone1() {
        return cmp_phone1;
    }

    public void setCmp_phone1(String cmp_phone1) {
        this.cmp_phone1 = cmp_phone1;
    }

    public String getCmp_phone2() {
        return cmp_phone2;
    }

    public void setCmp_phone2(String cmp_phone2) {
        this.cmp_phone2 = cmp_phone2;
    }

    public String getCmp_email() {
        return cmp_email;
    }

    public void setCmp_email(String cmp_email) {
        this.cmp_email = cmp_email;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public String getCategory3() {
        return category3;
    }

    public void setCategory3(String category3) {
        this.category3 = category3;
    }

    public String getDisplay_code() {
        return display_code;
    }

    public void setDisplay_code(String display_code) {
        this.display_code = display_code;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public int getIs_syncTo_server() {
        return is_syncTo_server;
    }

    public void setIs_syncTo_server(int is_syncTo_server) {
        this.is_syncTo_server = is_syncTo_server;
    }
}
