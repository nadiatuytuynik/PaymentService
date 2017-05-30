package com.service.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nadia on 22.04.2017.
 */
public class Customer {

    private Long customer_id;
    private String customer_name;
    private String customer_second_name;
    private String customer_login;
    private String customer_password;
    private String customer_status;

    public Customer(){
    }

    public Customer(String customer_name, String customer_second_name, String customer_login, String customer_password,
                    String customer_status){
        this.customer_name = customer_name;
        this.customer_second_name = customer_second_name;
        this.customer_login = customer_login;
        this.customer_password = customer_password;
        this.customer_status = customer_status;
    }

    public Customer(ResultSet rs) throws SQLException {
        this.customer_id = rs.getLong("customer_id");
        this.customer_name = rs.getString("customer_name");
        this.customer_second_name = rs.getString("customer_second_name");
        this.customer_login = rs.getString("customer_login");
        this.customer_password = rs.getString("customer_password");
        this.customer_status = rs.getString("customer_status");
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_second_name() {
        return customer_second_name;
    }

    public void setCustomer_second_name(String customer_second_name) {
        this.customer_second_name = customer_second_name;
    }

    public String getCustomer_login() {
        return customer_login;
    }

    public void setCustomer_login(String customer_login) {
        this.customer_login = customer_login;
    }

    public String getCustomer_password() {
        return customer_password;
    }

    public void setCustomer_password(String customer_password) {
        this.customer_password = customer_password;
    }

    public String getCustomer_status() {
        return customer_status;
    }

    public void setCustomer_status(String customer_status) {
        this.customer_status = customer_status;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", customer_name='" + customer_name + '\'' +
                ", customer_second_name='" + customer_second_name + '\'' +
                ", customer_login='" + customer_login + '\'' +
                ", customer_password='" + customer_password + '\'' +
                ", customer_status='" + customer_status + '\'' +
                '}';
    }
}
