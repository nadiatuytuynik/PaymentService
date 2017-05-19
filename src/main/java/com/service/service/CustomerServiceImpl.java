package com.service.service;


import com.service.dao.CustomerDao;
import com.service.dao.CustomerDaoImpl;
import com.service.model.Customer;

/**
 * Created by Nadia on 23.04.2017.
 */
public class CustomerServiceImpl implements CustomerService{
    private CustomerDao customerDao;

    public CustomerServiceImpl(){this.customerDao = new CustomerDaoImpl();}

    @Override
    public Customer create(Customer customer) {
        return this.customerDao.create(customer);
    }
    // assert not null (user) -> throw exception

    @Override
    public Customer update(Customer customer) {
        return this.customerDao.update(customer);
    }

    @Override
    public Customer findOne(Long customer_id) {
        return this.customerDao.findOne(customer_id);
    }

    @Override
    public void delete(Long customer_id) {
        this.customerDao.delete(customer_id);
    }
}
