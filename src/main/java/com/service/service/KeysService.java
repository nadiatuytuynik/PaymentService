package com.service.service;

import com.service.model.Keys;

/**
 * Created by Nadia on 21.05.2017.
 */
public interface KeysService {

    Keys create(Keys keys);

    Keys update(Keys keys);

    Keys findOne(Long keys_id);

    void delete(Long keys_id);

}
