package com.service.service;

import com.service.model.Keys;
import com.service.model.Phone;

/**
 * Created by Nadia on 28.05.2017.
 */
public interface PhoneService {

    Phone create(Phone phone);

    Phone update(Phone phone);

    Phone findOne(Long phone_id);

    void delete(Long phone_id);

}