package com.service.service;

import com.service.dao.Card_customerDao;
import com.service.dao.Card_customerDaoImpl;
import com.service.model.Card_customer;


public class Card_customerServiceImpl implements Card_customerService{
    private Card_customerDao card_customerDao;

    public Card_customerServiceImpl(){this.card_customerDao = new Card_customerDaoImpl();}

    @Override
    public Card_customer create(Card_customer card_customer) {
        return this.card_customerDao.create(card_customer);
    }
    // assert not null (user) -> throw exception

    @Override
    public Card_customer update(Card_customer card_customer) {
        return this.card_customerDao.update(card_customer);
    }

    @Override
    public Card_customer findOne(Long card_customer_id) {
        return this.card_customerDao.findOne(card_customer_id);
    }

    @Override
    public void delete(Long card_customer_id) {
        this.card_customerDao.delete(card_customer_id);
    }
}
