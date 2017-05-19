package com.service.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nadia on 22.04.2017.
 */
public class Mobile_refill {
    private Long mobile_refill_id;
    private Long sender_card_number;
    private Long recipient_phone_number;
    private int  amount__of_remittance;
    private String currency_of_amount;

    public Mobile_refill(){
    }

    public Mobile_refill(Long sender_card_number, Long recipient_phone_number, int  amount__of_remittance,
                         String currency_of_amount){

        this.sender_card_number = sender_card_number;
        this.recipient_phone_number = recipient_phone_number;
        this.amount__of_remittance = amount__of_remittance;
        this.currency_of_amount = currency_of_amount;
    }

    public Mobile_refill(ResultSet rs) throws SQLException {
        this.mobile_refill_id = rs.getLong("mobile_refill_id");
        this.sender_card_number = rs.getLong("sender_card_number");
        this.recipient_phone_number =  rs.getLong("recipient_phone_number");
        this.amount__of_remittance = rs.getInt("amount__of_remittance");
        this.currency_of_amount = rs.getString("currency_of_amount");
    }

    public Long getMobile_refill_id() {
        return mobile_refill_id;
    }

    public void setMobile_refill_id(Long mobile_refill_id) {
        this.mobile_refill_id = mobile_refill_id;
    }

    public Long getSender_card_number() {
        return sender_card_number;
    }

    public void setSender_card_number(Long sender_card_number) {
        this.sender_card_number = sender_card_number;
    }

    public Long getRecipient_phone_number() {
        return recipient_phone_number;
    }

    public void setRecipient_phone_number(Long recipient_phone_number) {
        this.recipient_phone_number = recipient_phone_number;
    }

    public int getAmount__of_remittance() {
        return amount__of_remittance;
    }

    public void setAmount__of_remittance(int amount__of_remittance) {
        this.amount__of_remittance = amount__of_remittance;
    }

    public String getCurrency_of_amount() {
        return currency_of_amount;
    }

    public void setCurrency_of_amount(String currency_of_amount) {
        this.currency_of_amount = currency_of_amount;
    }

    @Override
    public String toString(){
        return "Mobile_refill {" +
                " mobile_refill_id =  " + mobile_refill_id +
                " sender_card_number = " + sender_card_number +
                " recipient_phone_number = " + recipient_phone_number +
                " amount__of_remittance = " + amount__of_remittance +
                " currency_of_amount = " + currency_of_amount +
                " }";
    }


}
