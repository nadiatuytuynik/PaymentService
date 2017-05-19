package com.service.dao;

import com.service.model.Card;
import com.service.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nadia on 22.04.2017.
 */
public class CardDaoImpl extends AbstractDaoImpl<Card> implements CardDao{

    @Override
    protected void fillCreateStatement(PreparedStatement ps, Card entity) {
        try {
            ps.setString(1, entity.getCard_name());
            ps.setString(2, entity.getCard_number());
            ps.setString(3, entity.getCard_validity());
            ps.setString(4, entity.getCvv2_code());
            ps.setDouble(5, entity.getAccount_status());
            ps.setString(6, entity.getCurrency_of_account());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void fillUpdateStatement(PreparedStatement ps, Card entity) {
        try{
            ps.setString(1, entity.getCard_name());
            ps.setString(2, entity.getCard_number());
            ps.setString(3, entity.getCard_validity());
            ps.setString(4, entity.getCvv2_code());
            ps.setDouble(5, entity.getAccount_status());
            ps.setString(6, entity.getCurrency_of_account());
            ps.setLong(7, entity.getCard_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Card getEntity(ResultSet rs) {
        try {
            return new Card(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String[] getGeneratedColumn() {
        return new String[]{"card_id"};
    }

    @Override
    protected String getFindOneQuery() {
        return this.dbManager.getQuery("find.card");
    }

    @Override
    protected String getCreateQuery() {
        return this.dbManager.getQuery("create.card");
    }

    @Override
    protected String getDeleteQuery() {
        return this.dbManager.getQuery("delete.card");
    }

    @Override
    protected String getUpdateQuery() {
        return this.dbManager.getQuery("update.card");
    }

    @Override
    public Customer findCustomerByLogin(String login) {return null;}


}
