package com.service.dao;

import com.service.model.Customer;
import com.service.model.Mobile_refill;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nadia on 22.04.2017.
 */
public class Mobile_refillDaoImpl extends AbstractDaoImpl<Mobile_refill> implements Mobile_refillDao{

    @Override
    protected void fillCreateStatement(PreparedStatement ps, Mobile_refill entity) {
        try {
            ps.setLong(1, entity.getSender_card_number());
            ps.setLong(2, entity.getRecipient_phone_number());
            ps.setInt(3, entity.getAmount__of_remittance());
            ps.setString(4, entity.getCurrency_of_amount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void fillUpdateStatement(PreparedStatement ps, Mobile_refill entity) {
        try{
            ps.setLong(1, entity.getMobile_refill_id());
            ps.setLong(2, entity.getSender_card_number());
            ps.setLong(3, entity.getRecipient_phone_number());
            ps.setInt(4, entity.getAmount__of_remittance());
            ps.setString(5, entity.getCurrency_of_amount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Mobile_refill getEntity(ResultSet rs) {
        try {
            return new Mobile_refill(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String[] getGeneratedColumn() {
        return new String[]{"mobile_refill_id"};
    }

    @Override
    protected String getFindOneQuery() {
        return this.dbManager.getQuery("find.mobile_refill");
    }

    @Override
    protected String getCreateQuery() {
        return this.dbManager.getQuery("create.mobile_refill");
    }

    @Override
    protected String getDeleteQuery() {
        return this.dbManager.getQuery("delete.mobile_refill");
    }

    @Override
    protected String getUpdateQuery() {
        return this.dbManager.getQuery("update.mobile_refill");
    }

    @Override
    public Customer findCustomerByLogin(String customer_login) {
        return null;
    }


}
