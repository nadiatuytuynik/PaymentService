package com.service.dao;

import com.service.model.Basket;
import com.service.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nadia on 22.04.2017.
 */
public class BasketDaoImpl extends AbstractDaoImpl<Basket> implements BasketDao{

    @Override
    protected void fillCreateStatement(PreparedStatement ps, Basket entity) {
        try {
            ps.setString(1, entity.getSender_card_number());
            ps.setString(2, entity.getRecipient_card_number());
            ps.setDouble(3, entity.getAmount__of_remittance());
            ps.setString(4, entity.getCurrency_of_amount());
            ps.setString(5, entity.getStatus_of_line());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void fillUpdateStatement(PreparedStatement ps, Basket entity) {
        try{
            ps.setString(1, entity.getSender_card_number());
            ps.setString(2, entity.getRecipient_card_number());
            ps.setDouble(3, entity.getAmount__of_remittance());
            ps.setString(4, entity.getCurrency_of_amount());
            ps.setString(5, entity.getStatus_of_line());
            ps.setLong(6, entity.getBasket_line_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Basket getEntity(ResultSet rs) {
        try {
            return new Basket(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String[] getGeneratedColumn() {
        return new String[]{"basket_line_id"};
    }

    @Override
    protected String getFindOneQuery() {
        return this.dbManager.getQuery("find.basket");
    }

    @Override
    protected String getCreateQuery() {
        return this.dbManager.getQuery("create.basket");
    }

    @Override
    protected String getDeleteQuery() {
        return this.dbManager.getQuery("delete.basket");
    }

    @Override
    protected String getUpdateQuery() {
        return this.dbManager.getQuery("update.basket");
    }

    @Override
    public Customer findCustomerByLogin(String login) {
        return null;
    }


}
