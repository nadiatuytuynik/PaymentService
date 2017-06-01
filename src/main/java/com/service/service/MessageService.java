package com.service.service;


import com.service.model.Message;


public interface MessageService {

    Message create(Message message);

    Message update(Message message);

    Message findOne(Long message_id);

    void delete(Long message_id);

}

