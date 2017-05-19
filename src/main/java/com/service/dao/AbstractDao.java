package com.service.dao;

import com.service.model.Customer;

/**
 * Created by Nadia on 22.04.2017.
 */

public interface AbstractDao<T,ID> {
    T create(T entity);

    T update(T entity);

    T findOne(ID id);

    void delete(ID id);

}
