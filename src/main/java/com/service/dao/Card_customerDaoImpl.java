package com.service.dao;


import com.service.model.Card_customer;
import com.service.model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Card_customerDaoImpl extends AbstractDaoImpl<Card_customer> implements Card_customerDao{

    @Override
    protected void fillCreateStatement(PreparedStatement ps, Card_customer entity) {
        try {
            ps.setInt(1, entity.getCard_id());
            ps.setInt(2, entity.getCustomer_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void fillUpdateStatement(PreparedStatement ps, Card_customer entity) {
        try{
            ps.setInt(1, entity.getCard_id());
            ps.setInt(2, entity.getCustomer_id());
            ps.setLong(3, entity.getCard_customer_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Card_customer getEntity(ResultSet rs) {
        try {
            return new Card_customer(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String[] getGeneratedColumn() {return new String[]{"card_customer_id"};}

    @Override
    protected String getFindOneQuery() {
        return this.dbManager.getQuery("find.card_customer");
    }

    @Override
    protected String getCreateQuery() {
        return this.dbManager.getQuery("create.card_customer");
    }

    @Override
    protected String getDeleteQuery() {
        return this.dbManager.getQuery("delete.card_customer");
    }

    @Override
    protected String getUpdateQuery() {
        return this.dbManager.getQuery("update.card_customer");
    }

    @Override
    public Customer findCustomerByLogin(String login) {return null;}


}

