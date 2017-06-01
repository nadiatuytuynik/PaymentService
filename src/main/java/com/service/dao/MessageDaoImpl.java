package com.service.dao;

import com.service.model.Customer;
import com.service.model.Message;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MessageDaoImpl extends AbstractDaoImpl<Message> implements MessageDao{


    @Override
    protected void fillCreateStatement(PreparedStatement ps, Message entity) {
        try {
            ps.setString(1, entity.getMessage_content());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void fillUpdateStatement(PreparedStatement ps, Message entity) {
        try{
            ps.setLong(1, entity.getMessage_id());
            ps.setString(2, entity.getMessage_content());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Message getEntity(ResultSet rs) {
        try {
            return new Message(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String[] getGeneratedColumn() {
        return new String[]{"message_id"};
    }

    @Override
    protected String getFindOneQuery() {
        return this.dbManager.getQuery("find.message");
    }

    @Override
    protected String getCreateQuery() {
        return this.dbManager.getQuery("create.message");
    }

    @Override
    protected String getDeleteQuery() {
        return this.dbManager.getQuery("delete.message");
    }

    @Override
    protected String getUpdateQuery() {
        return this.dbManager.getQuery("update.message");
    }

    @Override
    public Customer findCustomerByLogin(String customer_login) {
        return null;
    }


}
