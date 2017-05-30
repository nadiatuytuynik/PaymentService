package com.service.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nadia on 26.05.2017.
 */
public class Phone {
    private Long phone_id;
    private int customer_id;
    private String phone_operator;
    private String phone_number;
    private double phone_account;
    private String currency_of_acount;

    public Phone() {
    }

    public Phone(int customer_id, String phone_operator, String phone_number, double phone_account,
            String currency_of_acount) {
        this.customer_id = customer_id;
        this.phone_operator = phone_operator;
        this.phone_number = phone_number;
        this.phone_account = phone_account;
        this.currency_of_acount = currency_of_acount;
    }

    public Phone(ResultSet rs) throws SQLException {
        this.phone_id = rs.getLong("phone_id");
        this.customer_id = rs.getInt("customer_id");
        this.phone_operator = rs.getString("phone_operator");
        this.phone_number = rs.getString("phone_number");
        this.phone_account = rs.getDouble("phone_account");
        this.currency_of_acount = rs.getString("currency_of_acount");

    }


    public Long getPhone_id() {
        return phone_id;
    }

    public void setPhone_id(Long phone_id) {
        this.phone_id = phone_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getPhone_operator() {
        return phone_operator;
    }

    public void setPhone_operator(String phone_operator) {
        this.phone_operator = phone_operator;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public double getPhone_account() {
        return phone_account;
    }

    public void setPhone_account(double phone_account) {
        this.phone_account = phone_account;
    }

    public String getCurrency_of_acount() {
        return currency_of_acount;
    }

    public void setCurrency_of_acount(String currency_of_acount) {
        this.currency_of_acount = currency_of_acount;
    }


    @Override
    public String toString() {
        return "Phone{" +
                "phone_id=" + phone_id +
                ", customer_id=" + customer_id +
                ", phone_operator='" + phone_operator + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", phone_account=" + phone_account +
                ", currency_of_acount='" + currency_of_acount + '\'' +
                '}';
    }

}