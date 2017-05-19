package com.service.dao;

import com.service.model.Customer;
import com.service.model.Customer_basket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nadia on 19.05.2017.
 */
public class Customer_basketDaoImpl extends AbstractDaoImpl<Customer_basket> implements Customer_basketDao{

    @Override
    protected void fillCreateStatement(PreparedStatement ps, Customer_basket entity) {
        try {
            ps.setInt(1, entity.getCustomer_id());
            ps.setInt(2, entity.getBasket_line_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void fillUpdateStatement(PreparedStatement ps, Customer_basket entity) {
        try{
            ps.setInt(1, entity.getCustomer_id());
            ps.setInt(2, entity.getBasket_line_id());
            ps.setLong(3, entity.getCustomer_basket_line_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Customer_basket getEntity(ResultSet rs) {
        try {
            return new Customer_basket(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String[] getGeneratedColumn() {
        return new String[]{"customer_basket_line_id"};
    }

    @Override
    protected String getFindOneQuery() {
        return this.dbManager.getQuery("find.customer_basket");
    }

    @Override
    protected String getCreateQuery() {
        return this.dbManager.getQuery("create.customer_basket");
    }

    @Override
    protected String getDeleteQuery() {
        return this.dbManager.getQuery("delete.customer_basket");
    }

    @Override
    protected String getUpdateQuery() {
        return this.dbManager.getQuery("update.customer_basket");
    }

    @Override
    public Customer findCustomerByLogin(String login) {return null;}


}

