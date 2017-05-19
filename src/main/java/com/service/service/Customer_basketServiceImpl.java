package com.service.service;

import com.service.dao.Card_customerDao;
import com.service.dao.Card_customerDaoImpl;
import com.service.dao.Customer_basketDao;
import com.service.dao.Customer_basketDaoImpl;
import com.service.model.Card_customer;
import com.service.model.Customer_basket;

/**
 * Created by Nadia on 19.05.2017.
 */
public class Customer_basketServiceImpl implements Customer_basketService {
    private Customer_basketDao customer_basketDao;

    public Customer_basketServiceImpl() {
        this.customer_basketDao = new Customer_basketDaoImpl();
    }

    @Override
    public Customer_basket create(Customer_basket customer_basket) {
        return this.customer_basketDao.create(customer_basket);
    }
    // assert not null (user) -> throw exception

    @Override
    public Customer_basket update(Customer_basket customer_basket) {
        return this.customer_basketDao.update(customer_basket);
    }

    @Override
    public Customer_basket findOne(Long customer_basket_line_id) {
        return this.customer_basketDao.findOne(customer_basket_line_id);
    }

    @Override
    public void delete(Long customer_basket_line_id) {
        this.customer_basketDao.delete(customer_basket_line_id);
    }
}
