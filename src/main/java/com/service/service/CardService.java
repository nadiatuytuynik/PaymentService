package com.service.service;

import com.service.model.Card;

/**
 * Created by Nadia on 23.04.2017.
 */
public interface CardService {

   Card create(Card card);

    Card update(Card card);

    Card findOne(Long card_id);

    void delete(Long card_id);

}

