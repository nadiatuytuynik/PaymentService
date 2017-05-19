package com.service.service;

import com.service.model.Card_customer;


public interface Card_customerService {

    Card_customer create(Card_customer card_customer);

    Card_customer update(Card_customer card_customer);

    Card_customer findOne(Long card_customer_id);

    void delete(Long card_customer_id);

}
