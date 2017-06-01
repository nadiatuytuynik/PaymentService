package com.service.model;


import java.sql.ResultSet;
import java.sql.SQLException;

public class Message {

    private Long message_id;
    private String message_content;

    public Message(){
    }

    public Message(String message_content){
        this.message_content = message_content;
    }

    public Message(ResultSet rs) throws SQLException {
        this.message_id = rs.getLong("message_id");
        this.message_content = rs.getString("message_content");
    }

    public Long getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Long message_id) {
        this.message_id = message_id;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }


    @Override
    public String toString() {
        return "Message{" +
                "message_id=" + message_id +
                ", message_content='" + message_content + '\'' +
                '}';
    }
}
