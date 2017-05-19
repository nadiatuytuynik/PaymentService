package com.service.service;

import com.service.dao.RemittanceDao;
import com.service.dao.RemittanceDaoImpl;
import com.service.model.Remittance;

/**
 * Created by Nadia on 23.04.2017.
 */
public class RemittanceServiceImpl implements RemittanceService{
    private RemittanceDao remittanceDao;

    public RemittanceServiceImpl(){this.remittanceDao = new RemittanceDaoImpl();}

    @Override
    public Remittance create(Remittance remittance) {
        return this.remittanceDao.create(remittance);
    }
    // assert not null (user) -> throw exception

    @Override
    public Remittance update(Remittance remittance) {
        return this.remittanceDao.update(remittance);
    }

    @Override
    public Remittance findOne(Long remittance_id) {
        return this.remittanceDao.findOne(remittance_id);
    }

    @Override
    public void delete(Long remittance_id) {this.remittanceDao.delete(remittance_id);
    }
}
