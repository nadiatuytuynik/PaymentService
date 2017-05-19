package com.service.service;


import com.service.dao.Mobile_refillDao;
import com.service.dao.Mobile_refillDaoImpl;
import com.service.model.Mobile_refill;

/**
 * Created by Nadia on 23.04.2017.
 */
public class Mobile_refillServiceImpl implements Mobile_refillService{
    private Mobile_refillDao mobile_refillDao;

    public Mobile_refillServiceImpl(){this.mobile_refillDao = new Mobile_refillDaoImpl();}

    @Override
    public Mobile_refill create(Mobile_refill mobile_refill) {
        return this.mobile_refillDao.create(mobile_refill);
    }
    // assert not null (user) -> throw exception

    @Override
    public Mobile_refill update(Mobile_refill mobile_refill) {
        return this.mobile_refillDao.update(mobile_refill);
    }

    @Override
    public Mobile_refill findOne(Long mobile_refill_id) {
        return this.mobile_refillDao.findOne(mobile_refill_id);
    }

    @Override
    public void delete(Long mobile_refill_id) {this.mobile_refillDao.delete(mobile_refill_id);
    }
}
