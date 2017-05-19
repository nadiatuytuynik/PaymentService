package com.service.service;

import com.service.model.Basket;

/**
 * Created by Nadia on 23.04.2017.
 */
public interface BasketService {

    Basket create(Basket basket);

    Basket update(Basket basket);

    Basket findOne(Long basket_line_id);

    void delete(Long basket_line_id);

}
