package com.service.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nadia on 30.05.2017.
 */
public class Phone_message {
    private Long phone_message_id;
    private int phone_id;
    private int message_id;


    public Phone_message(){
    }

    public Phone_message(int phone_id,int message_id){
        this.phone_id = phone_id;
        this.message_id = message_id;
    }

    public Phone_message(ResultSet rs) throws SQLException {
        this.phone_message_id = rs.getLong("phone_message_id");
        this.phone_id = rs.getInt("phone_id");
        this.message_id = rs.getInt("message_id");
    }

    public Long getPhone_message_id() {
        return phone_message_id;
    }

    public void setPhone_message_id(Long phone_message_id) {
        this.phone_message_id = phone_message_id;
    }

    public int getPhone_id() {
        return phone_id;
    }

    public void setPhone_id(int phone_id) {
        this.phone_id = phone_id;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    @Override
    public String toString() {
        return "Phone_message{" +
                "phone_message_id=" + phone_message_id +
                ", phone_id=" + phone_id +
                ", message_id=" + message_id +
                '}';
    }
}
