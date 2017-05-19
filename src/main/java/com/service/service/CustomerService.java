package com.service.service;

import com.service.model.Customer;

/**
 * Created by Nadia on 23.04.2017.
 */
public interface CustomerService {

    Customer create(Customer customer);

    Customer update(Customer Customer);

    Customer findOne(Long customer_id);

    void delete(Long customer_id);

}
