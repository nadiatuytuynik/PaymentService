package com.service.service;

import com.service.model.Mobile_refill;

/**
 * Created by Nadia on 23.04.2017.
 */
public interface Mobile_refillService {

    Mobile_refill create(Mobile_refill mobile_refill);

    Mobile_refill update(Mobile_refill mobile_refill);

    Mobile_refill findOne(Long mobile_refill_id);

    void delete(Long mobile_refill_id);

}
