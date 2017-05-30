package com.service.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Created by Nadia on 22.04.2017.
 */
public class Basket {
    private Long basket_line_id;
    private String sender_card_number;
    private String recipient_card_number;
    private double amount__of_remittance;
    private String currency_of_amount;
    private String data_of_operation;
    private String status_of_line;

    public Basket(){
    }

    public Basket(String sender_card_number,String recipient_card_number,double amount__of_remittance,
                  String currency_of_amount,String data_of_operation,String status_of_line){
        this.sender_card_number = sender_card_number;
        this.recipient_card_number = recipient_card_number;
        this.amount__of_remittance = amount__of_remittance;
        this.currency_of_amount = currency_of_amount;
        this.data_of_operation = data_of_operation;
        this.status_of_line = status_of_line;
    }

    public Basket(ResultSet rs) throws SQLException {
        this.basket_line_id = rs.getLong("basket_line_id");
        this.sender_card_number = rs.getString("sender_card_number");
        this.recipient_card_number = rs.getString("recipient_card_number");
        this.amount__of_remittance = rs.getDouble("amount_of_remittance");
        this.currency_of_amount = rs.getString("currency_of_amount");
        this.data_of_operation = rs.getString("data_of_operation");
        this.status_of_line = rs.getString("status_of_line");
    }

    public Long getBasket_line_id() {
        return basket_line_id;
    }

    public void setBasket_line_id(Long basket_line_id) {
        this.basket_line_id = basket_line_id;
    }

    public String getSender_card_number() {
        return sender_card_number;
    }

    public void setSender_card_number(String sender_card_number) {
        this.sender_card_number = sender_card_number;
    }

    public String getRecipient_card_number() {
        return recipient_card_number;
    }

    public void setRecipient_card_number(String recipient_card_number) {
        this.recipient_card_number = recipient_card_number;
    }

    public double getAmount__of_remittance() {
        return amount__of_remittance;
    }

    public void setAmount__of_remittance(double amount__of_remittance) {
        this.amount__of_remittance = amount__of_remittance;
    }

    public String getCurrency_of_amount() {
        return currency_of_amount;
    }

    public void setCurrency_of_amount(String currency_of_amount) {
        this.currency_of_amount = currency_of_amount;
    }

    public String getData_of_operation() {
        return data_of_operation;
    }

    public void setData_of_operation(String data_of_operation) {
        this.data_of_operation = data_of_operation;
    }

    public String getStatus_of_line() {
        return status_of_line;
    }

    public void setStatus_of_line(String status_of_line) {
        this.status_of_line = status_of_line;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "basket_line_id=" + basket_line_id +
                ", sender_card_number='" + sender_card_number + '\'' +
                ", recipient_card_number='" + recipient_card_number + '\'' +
                ", amount__of_remittance=" + amount__of_remittance +
                ", currency_of_amount='" + currency_of_amount + '\'' +
                ", data_of_operation='" + data_of_operation + '\'' +
                ", status_of_line='" + status_of_line + '\'' +
                '}';
    }
}
