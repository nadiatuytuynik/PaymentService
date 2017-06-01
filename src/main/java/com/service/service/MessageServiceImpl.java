package com.service.service;

import com.service.dao.MessageDao;
import com.service.dao.MessageDaoImpl;

import com.service.model.Message;


public class MessageServiceImpl implements MessageService{
    private MessageDao messageDao;

    public MessageServiceImpl(){this.messageDao = new MessageDaoImpl();}

    @Override
    public Message create(Message message) {
        return this.messageDao.create(message);
    }
    // assert not null (user) -> throw exception

    @Override
    public Message update(Message message) {
        return this.messageDao.update(message);
    }

    @Override
    public Message findOne(Long message_id) {
        return this.messageDao.findOne(message_id);
    }

    @Override
    public void delete(Long message_id) {this.messageDao.delete(message_id);
    }
}
