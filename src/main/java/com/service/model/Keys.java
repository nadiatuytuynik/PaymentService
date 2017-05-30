package com.service.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nadia on 21.05.2017.
 */
public class Keys {
    private Long keys_id;
    private int public_key;
    private int private_key;
    private int public_key_n;
    private int public_key_fn;

    public Keys(){
    }

    public Keys(int public_key,int private_key,int public_key_n,int public_key_fn){
        this.public_key = public_key;
        this.private_key = private_key;
        this.public_key_n = public_key_n;
        this.public_key_fn = public_key_fn;
    }

    public Keys(ResultSet rs) throws SQLException {
        this.keys_id = rs.getLong("keys_id");
        this.public_key = rs.getInt("public_key");
        this.private_key = rs.getInt("private_key");
        this.public_key_n = rs.getInt("public_key_n");
        this.public_key_fn = rs.getInt("public_key_fn");
    }


    public Long getKeys_id() {
        return keys_id;
    }

    public void setKeys_id(Long keys_id) {
        this.keys_id = keys_id;
    }

    public int getPublic_key() {
        return public_key;
    }

    public void setPublic_key(int public_key) {
        this.public_key = public_key;
    }

    public int getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(int private_key) {
        this.private_key = private_key;
    }

    public int getPublic_key_n() {
        return public_key_n;
    }

    public void setPublic_key_n(int public_key_n) {
        this.public_key_n = public_key_n;
    }

    public int getPublic_key_fn() {
        return public_key_fn;
    }

    public void setPublic_key_fn(int public_key_fn) {
        this.public_key_fn = public_key_fn;
    }
//
    @Override
    public String toString() {
        return "Keys{" +
                "keys_id=" + keys_id +
                ", public_key=" + public_key +
                ", private_key=" + private_key +
                ", public_key_n=" + public_key_n +
                ", public_key_fn=" + public_key_fn +
                '}';
    }
}