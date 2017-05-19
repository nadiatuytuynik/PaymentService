package com.service.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nadia on 22.04.2017.
 */
public class Remittance {
    private Long remittance_id;
    private Long sender_card_number;
    private Long recipient_card_number;
    private int amount__of_remittance;
    private String currency_of_amount;

    public Remittance(){
    }

    public Remittance(Long sender_card_number, Long recipient_card_number, int amount__of_remittance,
                      String currency_of_amount){
        this.sender_card_number = sender_card_number;
        this.recipient_card_number = recipient_card_number;
        this.amount__of_remittance = amount__of_remittance;
        this.currency_of_amount = currency_of_amount;
    }

    public Remittance(ResultSet rs) throws SQLException {
        this.remittance_id = rs.getLong("remittance_id");
        this.sender_card_number = rs.getLong("sender_card_number");
        this.recipient_card_number =  rs.getLong("recipient_card_number");
        this.amount__of_remittance = rs.getInt("amount__of_remittance");
        this.currency_of_amount = rs.getString("currency_of_amount");
    }

    public Long getRemittance_id() {
        return remittance_id;
    }

    public void setRemittance_id(Long remittance_id) {
        this.remittance_id = remittance_id;
    }

    public Long getSender_card_number() {
        return sender_card_number;
    }

    public void setSender_card_number(Long sender_card_number) {
        this.sender_card_number = sender_card_number;
    }

    public Long getRecipient_card_number() {
        return recipient_card_number;
    }

    public void setRecipient_card_number(Long recipient_card_number) {
        this.recipient_card_number = recipient_card_number;
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
        return "Remittance {" +
                " remittance_id =  " + remittance_id +
                " sender_card_number = " + sender_card_number +
                " recipient_card_number = " + recipient_card_number +
                " amount__of_remittance = " + amount__of_remittance +
                " currency_of_amount = " + currency_of_amount +
                " }";
    }


}
