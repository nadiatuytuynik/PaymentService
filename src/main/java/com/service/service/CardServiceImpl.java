package com.service.service;


import com.service.dao.CardDao;
import com.service.dao.CardDaoImpl;
import com.service.model.Card;

/**
 * Created by Nadia on 23.04.2017.
 */
public class CardServiceImpl implements CardService{
    private CardDao cardDao;

    public CardServiceImpl(){
        this.cardDao = new CardDaoImpl();
    }

    @Override
    public Card create(Card card) {
        return this.cardDao.create(card);
    }
    // assert not null (user) -> throw exception

    @Override
    public Card update(Card card) {
        return this.cardDao.update(card);
    }

    @Override
    public Card findOne(Long card_id) {
        return this.cardDao.findOne(card_id);
    }

    @Override
    public void delete(Long card_id) {
        this.cardDao.delete(card_id);
    }
}
