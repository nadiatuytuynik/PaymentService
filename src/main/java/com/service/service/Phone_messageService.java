package com.service.service;


import com.service.model.Phone_message;


public interface Phone_messageService {

    Phone_message create(Phone_message phone_message);

    Phone_message update(Phone_message phone_message);

    Phone_message findOne(Long phone_message_id);

    void delete(Long phone_message_id);

}
