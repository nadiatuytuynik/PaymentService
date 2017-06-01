package com.service.dao;

import com.service.model.Customer;
import com.service.model.Phone_message;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Phone_messageDaoImpl extends AbstractDaoImpl<Phone_message> implements Phone_messageDao{

    @Override
    protected void fillCreateStatement(PreparedStatement ps, Phone_message entity) {
        try {
            ps.setInt(1, entity.getPhone_id());
            ps.setInt(2, entity.getMessage_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void fillUpdateStatement(PreparedStatement ps, Phone_message entity) {
        try{
            ps.setInt(1, entity.getPhone_id());
            ps.setInt(2, entity.getMessage_id());
            ps.setLong(3, entity.getPhone_message_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Phone_message getEntity(ResultSet rs) {
        try {
            return new Phone_message(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String[] getGeneratedColumn() {
        return new String[]{"phone_message_id"};
    }

    @Override
    protected String getFindOneQuery() {
        return this.dbManager.getQuery("find.phone_message");
    }

    @Override
    protected String getCreateQuery() {
        return this.dbManager.getQuery("create.phone_message");
    }

    @Override
    protected String getDeleteQuery() {
        return this.dbManager.getQuery("delete.phone_message");
    }

    @Override
    protected String getUpdateQuery() {
        return this.dbManager.getQuery("update.phone_message");
    }

    @Override
    public Customer findCustomerByLogin(String customer_login) {
        return null;
    }


}
