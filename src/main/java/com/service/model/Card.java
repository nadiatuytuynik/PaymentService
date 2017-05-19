package com.service.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nadia on 22.04.2017.
 */
public class Card {

    private Long card_id;
    private String card_name;
    private String card_number;
    private String card_validity;
    private String cvv2_code;
    private double  account_status;
    private String currency_of_account;


    public Card(){
    }


    public Card(String card_name, String card_number, String card_validity, String cvv2_code,
                double  account_status, String currency_of_account){
        this.card_name = card_name;
        this.card_number = card_number;
        this.card_validity = card_validity;
        this.cvv2_code = cvv2_code;
        this.account_status = account_status;
        this.currency_of_account = currency_of_account;
    }

    public Card(ResultSet rs) throws SQLException{
        this.card_id = rs.getLong("card_id");
        this.card_name = rs.getString("card_name");
        this.card_number = rs.getString("card_number");
        this.card_validity = rs.getString("card_validity");
        this.cvv2_code = rs.getString("cvv2_code");
        this.account_status = rs.getDouble("account_status");
        this.currency_of_account = rs.getString("currency_of_account");
    }


    public Long getCard_id() {
        return card_id;
    }

    public void setCard_id(Long card_id) {
        this.card_id = card_id;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCard_validity() {
        return card_validity;
    }

    public void setCard_validity(String card_validity) {
        this.card_validity = card_validity;
    }

    public String getCvv2_code() {
        return cvv2_code;
    }

    public void setCvv2_code(String cvv2_code) {
        this.cvv2_code = cvv2_code;
    }

    public double getAccount_status() {
        return account_status;
    }

    public void setAccount_status(double account_status) {
        this.account_status = account_status;
    }

    public String getCurrency_of_account() {
        return currency_of_account;
    }

    public void setCurrency_of_account(String currency_of_account) {
        this.currency_of_account = currency_of_account;
    }

    @Override
    public String toString(){
       return "Card {" +
               " card_id =  " + card_id +
               " card_name = " + card_name +
               " card_number = " + card_number +
               " card_validity = " + card_validity +
               " cvv2_code = " + cvv2_code +
               " account_status = " + account_status +
               " currency_of_account = " + currency_of_account +
               " }";
    }


}
