package com.service.service;

import com.service.dao.BasketDao;
import com.service.dao.BasketDaoImpl;
import com.service.model.Basket;


public class BasketServiceImpl implements BasketService{
    private BasketDao basketDao;

    public BasketServiceImpl(){
        this.basketDao = new BasketDaoImpl();
    }

    @Override
    public Basket create(Basket basket) {
        return this.basketDao.create(basket);
    }
    // assert not null (user) -> throw exception

    @Override
    public Basket update(Basket basket) {
        return this.basketDao.update(basket);
    }

    @Override
    public Basket findOne(Long basket_line_id) {
        return this.basketDao.findOne(basket_line_id);
    }

    @Override
    public void delete(Long basket_line_id) {
        this.basketDao.delete(basket_line_id);
    }
}
