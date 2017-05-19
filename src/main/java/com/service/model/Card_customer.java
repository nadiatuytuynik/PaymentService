package com.service.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nadia on 04.05.2017.
 */
public class Card_customer {
    private Long card_customer_id;
    private int card_id;
    private int customer_id;

    public Card_customer(){
    }

    public Card_customer(int card_id, int customer_id){
        this.card_id = card_id;
        this.customer_id = customer_id;
    }

    public Card_customer(ResultSet rs) throws SQLException {
        this.card_customer_id = rs.getLong("card_customer_id");
        this.card_id = rs.getInt("card_id");
        this.customer_id = rs.getInt("customer_id");
    }

    public Long getCard_customer_id() {
        return card_customer_id;
    }

    public void setCard_customer_id(Long card_customer_id) {
        this.card_customer_id = card_customer_id;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public String toString(){
        return "Card {" +
                " card_customer_id =  " + card_customer_id +
                " card_id =  " + card_id +
                " customer_id = " + customer_id +
                " }";
    }

}


