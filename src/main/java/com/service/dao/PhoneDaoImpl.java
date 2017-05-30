package com.service.dao;

import com.service.model.Customer;
import com.service.model.Phone;
import com.service.model.Remittance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nadia on 28.05.2017.
 */
public class PhoneDaoImpl extends AbstractDaoImpl<Phone> implements PhoneDao {

    @Override
    protected void fillCreateStatement(PreparedStatement ps, Phone entity) {
        try {
            ps.setInt(1, entity.getCustomer_id());
            ps.setString(2, entity.getPhone_operator());
            ps.setString(3, entity.getPhone_number());
            ps.setDouble(4, entity.getPhone_account());
            ps.setString(5, entity.getCurrency_of_acount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void fillUpdateStatement(PreparedStatement ps, Phone entity) {
        try{
            ps.setInt(1, entity.getCustomer_id());
            ps.setString(2, entity.getPhone_operator());
            ps.setString(3, entity.getPhone_number());
            ps.setDouble(4, entity.getPhone_account());
            ps.setString(5, entity.getCurrency_of_acount());
            ps.setLong(6, entity.getPhone_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Phone getEntity(ResultSet rs) {
        try {
            return new Phone(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String[] getGeneratedColumn() {
        return new String[]{"phone_id"};
    }

    @Override
    protected String getFindOneQuery() {
        return this.dbManager.getQuery("find.phone");
    }

    @Override
    protected String getCreateQuery() {
        return this.dbManager.getQuery("create.phone");
    }

    @Override
    protected String getDeleteQuery() {
        return this.dbManager.getQuery("delete.phone");
    }

    @Override
    protected String getUpdateQuery() {
        return this.dbManager.getQuery("update.phone");
    }

    @Override
    public Customer findCustomerByLogin(String customer_login) {
        return null;
    }

}