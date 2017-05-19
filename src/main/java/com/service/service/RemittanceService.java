package com.service.service;

import com.service.model.Remittance;

/**
 * Created by Nadia on 23.04.2017.
 */
public interface RemittanceService {

    Remittance create(Remittance remittance);

    Remittance update(Remittance remittance);

    Remittance findOne(Long remittance_id);

    void delete(Long remittance_id);

}
