package com.service.service;


import com.service.dao.KeysDao;
import com.service.dao.KeysDaoImpl;
import com.service.model.Keys;


public class KeysServiceImpl implements KeysService{
    private KeysDao keysDao;

    public  KeysServiceImpl(){this. keysDao = new KeysDaoImpl();}

    @Override
    public Keys create( Keys  keys) {
        return this. keysDao.create(keys);
    }
    // assert not null (user) -> throw exception

    @Override
    public  Keys update( Keys  keys) {
        return this. keysDao.update(keys);
    }

    @Override
    public  Keys findOne(Long  keys_id) {
        return this. keysDao.findOne(keys_id);
    }

    @Override
    public void delete(Long keys_id) {
        this.keysDao.delete(keys_id);
    }
}
