package com.service.service;

import com.service.model.Customer_basket;

/**
 * Created by Nadia on 19.05.2017.
 */
public interface Customer_basketService {

    Customer_basket create(Customer_basket customer_basket);

    Customer_basket update(Customer_basket customer_basket);

    Customer_basket findOne(Long customer_basket_line_id);

    void delete(Long customer_basket_line_id);

}

