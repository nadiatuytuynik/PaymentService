package com.service.dao;

import com.service.model.Customer;
import com.service.model.Keys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class KeysDaoImpl extends AbstractDaoImpl<Keys> implements KeysDao{


    @Override
    protected void fillCreateStatement(PreparedStatement ps, Keys entity) {
        try {
            ps.setInt(1, entity.getPublic_key());
            ps.setInt(2, entity.getPrivate_key());
            ps.setInt(3, entity.getPublic_key_n());
            ps.setInt(4, entity.getPublic_key_fn());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void fillUpdateStatement(PreparedStatement ps, Keys entity) {
        try{
            ps.setInt(1, entity.getPublic_key());
            ps.setInt(2, entity.getPrivate_key());
            ps.setInt(3, entity.getPublic_key_n());
            ps.setInt(4, entity.getPublic_key_fn());
            ps.setLong(5, entity.getKeys_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Keys getEntity(ResultSet rs) {
        try {
            return new Keys(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String[] getGeneratedColumn() {
        return new String[]{"keys_id"};
    }

    @Override
    protected String getFindOneQuery() {
        return this.dbManager.getQuery("find.keys");
    }

    @Override
    protected String getCreateQuery() {
        return this.dbManager.getQuery("create.keys");
    }

    @Override
    protected String getDeleteQuery() {
        return this.dbManager.getQuery("delete.keys");
    }

    @Override
    protected String getUpdateQuery() {
        return this.dbManager.getQuery("update.keys");
    }

    @Override
    public Customer findCustomerByLogin(String customer_login) {
        return null;
    }

}



