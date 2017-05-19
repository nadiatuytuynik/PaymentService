package com.service.dao;

import com.service.model.Customer;
import com.service.util.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDaoImpl<T> implements AbstractDao<T, Long>{
    DBManager dbManager = DBManager.getInstance();

    @Override
    public T create(T entity) {
        try(Connection connection = this.dbManager.getConnection();
            PreparedStatement ps = this.cretePreparedStatement(entity, connection);
            ResultSet rs = ps.getGeneratedKeys()) {

            if (rs.next()){
                Long generatedID = rs.getLong(1);
                return this.findOne(generatedID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T findOne(Long ID) {
        try(Connection connection = this.dbManager.getConnection();
            PreparedStatement ps = this.findOnePreparedStatement(ID, connection);
            ResultSet rs = ps.executeQuery()) {//выполнение запроса

            if(rs.next()){
                return this.getEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T update(T entity) {
        try(Connection connection = this.dbManager.getConnection();
            PreparedStatement ps = this.updatePreparedStatement(entity, connection)) {
            ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Long ID) {
        try(Connection connection = this.dbManager.getConnection();
            PreparedStatement ps = this.deletePreparedStatement(ID, connection)) {

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement deletePreparedStatement(Long id, Connection connection) throws SQLException {
        String query = this.getDeleteQuery();
        PreparedStatement ps = connection.prepareStatement(query);//драйвер обращается к СУБД для подготовки запроса
        ps.setLong(1,id);

        return null;
    }

    private PreparedStatement cretePreparedStatement(T entity, Connection connection) throws SQLException {
        String query = this.getCreateQuery();
        String[] generatedColumn = this.getGeneratedColumn();
        PreparedStatement ps = connection.prepareStatement(query, generatedColumn);
        this.fillCreateStatement(ps,entity);

        ps.executeUpdate();
        return ps;
    }

    private PreparedStatement findOnePreparedStatement(Long id, Connection connection) throws SQLException {
        String query = this.getFindOneQuery();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, id);

        return ps;
    }

    private PreparedStatement updatePreparedStatement(T entity, Connection connection) throws SQLException {
        String query = this.getUpdateQuery();
        PreparedStatement ps = connection.prepareStatement(query);
        this.fillUpdateStatement(ps, entity);

        return ps;
    }

    protected abstract String[] getGeneratedColumn();

    protected abstract void fillCreateStatement(PreparedStatement ps, T entity);

    protected abstract void fillUpdateStatement(PreparedStatement ps, T entity);

    protected abstract String getCreateQuery();

    protected abstract T getEntity(ResultSet rs);

    protected abstract String getDeleteQuery();

    protected abstract String getFindOneQuery();

    protected abstract String getUpdateQuery();


    public abstract Customer findCustomerByLogin(String login);
}
