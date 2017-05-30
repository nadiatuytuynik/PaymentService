package com.service.dao;

import com.service.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nadia on 22.04.2017.
 */
public class CustomerDaoImpl extends AbstractDaoImpl<Customer> implements CustomerDao{

    @Override
    protected void fillCreateStatement(PreparedStatement ps, Customer entity) {
        try {
            ps.setString(1, entity.getCustomer_name());
            ps.setString(2, entity.getCustomer_second_name());
            ps.setString(3, entity.getCustomer_login());
            ps.setString(4, entity.getCustomer_password());
            ps.setString(5, entity.getCustomer_status());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void fillUpdateStatement(PreparedStatement ps, Customer entity) {
        try{
            ps.setString(1, entity.getCustomer_name());
            ps.setString(2, entity.getCustomer_second_name());
            ps.setString(3, entity.getCustomer_login());
            ps.setString(4, entity.getCustomer_password());
            ps.setString(5, entity.getCustomer_status());
            ps.setLong(6, entity.getCustomer_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Customer getEntity(ResultSet rs) {
        try {
            return new Customer(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String[] getGeneratedColumn() {
        return new String[]{"customer_id"};
    }

    @Override
    protected String getFindOneQuery() {
        return this.dbManager.getQuery("find.customer");
    }

    @Override
    protected String getCreateQuery() {
        return this.dbManager.getQuery("create.customer");
    }

    @Override
    protected String getDeleteQuery() {
        return this.dbManager.getQuery("delete.customer");
    }

    @Override
    protected String getUpdateQuery() {
        return this.dbManager.getQuery("update.customer");
    }

    @Override
    public Customer findCustomerByLogin(String customer_login) {
        return null;
    }
}
