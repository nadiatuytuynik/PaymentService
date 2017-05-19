package com.service.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nadia on 19.05.2017.
 */
public class Customer_basket {
    private Long customer_basket_line_id;
    private int customer_id;
    private int basket_line_id;


    public Customer_basket() {
    }

    public Customer_basket(int customer_id, int basket_line_id){
        this.customer_id = customer_id;
        this.basket_line_id = basket_line_id;
    }

    public Customer_basket(ResultSet rs) throws SQLException {
        this.customer_basket_line_id = rs.getLong("customer_basket_line_id");
        this.customer_id = rs.getInt("customer_id");
        this.basket_line_id = rs.getInt("basket_line_id");
    }

    public Long getCustomer_basket_line_id() {
        return customer_basket_line_id;
    }

    public void setCustomer_basket_line_id(Long customer_basket_line_id) {
        this.customer_basket_line_id = customer_basket_line_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getBasket_line_id() {
        return basket_line_id;
    }

    public void setBasket_line_id(int basket_line_id) {
        this.basket_line_id = basket_line_id;
    }


    @Override
    public String toString() {
        return "Customer_basket{" +
                "customer_basket_line_id=" + customer_basket_line_id +
                ", customer_id=" + customer_id +
                ", basket_line_id=" + basket_line_id +
                '}';
    }
}