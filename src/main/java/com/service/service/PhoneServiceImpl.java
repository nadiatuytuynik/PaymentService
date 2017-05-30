package com.service.service;

import com.service.dao.PhoneDao;
import com.service.dao.PhoneDaoImpl;

import com.service.model.Phone;



public class PhoneServiceImpl implements PhoneService{
    private PhoneDao phoneDao;

    public PhoneServiceImpl(){this.phoneDao = new PhoneDaoImpl();}

    @Override
    public Phone create(Phone phone) {
        return this.phoneDao.create(phone);
    }
    // assert not null (user) -> throw exception

    @Override
    public Phone update(Phone phone) {
        return this.phoneDao.update(phone);
    }

    @Override
    public Phone findOne(Long phone_id) {return this.phoneDao.findOne(phone_id);
    }

    @Override
    public void delete(Long phone_id) {this.phoneDao.delete(phone_id);
    }
}

