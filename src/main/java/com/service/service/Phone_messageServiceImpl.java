package com.service.service;

import com.service.dao.Phone_messageDao;
import com.service.dao.Phone_messageDaoImpl;

import com.service.model.Phone_message;

public class Phone_messageServiceImpl implements Phone_messageService{
    private Phone_messageDao phone_messageDao;

    public Phone_messageServiceImpl(){this.phone_messageDao = new Phone_messageDaoImpl();}

    @Override
    public Phone_message create(Phone_message phone_message) {
        return this.phone_messageDao.create(phone_message);
    }
    // assert not null (user) -> throw exception

    @Override
    public Phone_message update(Phone_message phone_message) {
        return this.phone_messageDao.update(phone_message);
    }

    @Override
    public Phone_message findOne(Long phone_message_id) {
        return this.phone_messageDao.findOne(phone_message_id);
    }

    @Override
    public void delete(Long phone_message_id) {this.phone_messageDao.delete(phone_message_id);
    }
}