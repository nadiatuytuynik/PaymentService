package com.service.dao;

import com.service.model.Customer;
import com.service.model.Mobile_refill;
import com.service.model.Remittance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nadia on 22.04.2017.
 */
public class RemittanceDaoImpl extends AbstractDaoImpl<Remittance> implements RemittanceDao{

    @Override
    protected void fillCreateStatement(PreparedStatement ps, Remittance entity) {
        try {
            ps.setLong(1, entity.getSender_card_number());
            ps.setLong(2, entity.getRecipient_card_number());
            ps.setInt(3, entity.getAmount__of_remittance());
            ps.setString(4, entity.getCurrency_of_amount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void fillUpdateStatement(PreparedStatement ps, Remittance entity) {
        try{
            ps.setLong(1, entity.getRemittance_id());
            ps.setLong(2, entity.getSender_card_number());
            ps.setLong(3, entity.getRecipient_card_number());
            ps.setInt(4, entity.getAmount__of_remittance());
            ps.setString(5, entity.getCurrency_of_amount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Remittance getEntity(ResultSet rs) {
        try {
            return new Remittance(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String[] getGeneratedColumn() {
        return new String[]{"remittance_id"};
    }

    @Override
    protected String getFindOneQuery() {
        return this.dbManager.getQuery("find.remittance");
    }

    @Override
    protected String getCreateQuery() {
        return this.dbManager.getQuery("create.remittance");
    }

    @Override
    protected String getDeleteQuery() {
        return this.dbManager.getQuery("delete.remittance");
    }

    @Override
    protected String getUpdateQuery() {
        return this.dbManager.getQuery("update.remittance");
    }

    @Override
    public Customer findCustomerByLogin(String customer_login) {
        return null;
    }

}
