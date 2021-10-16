package com.example.superbank.repository.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.superbank.entity.Card;
import com.example.superbank.repository.CardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CardListRepository implements CardRepository {

    private final List<Card> cardList = new ArrayList<>();

    private static long cardNumberCounter = 9908000000000000L;

    @Override

    public Card add(Card entity) {
        entity.setCardNumber(cardNumberCounter++);
        cardList.add(entity);

        return entity;
    }

    @Override
    public Card update(Card entity, Long entityId) {
        cardList.removeIf(it -> it.getCardNumber().equals(entityId));
        cardList.add(entity);
        return entity;

    }

    @Override
    public Optional<Card> get(Long entityId) {
        return cardList.stream().filter(it -> it.getCardNumber().equals(entityId)).findFirst();
    }

    @Override
    public List<Card> getAll() {
        return cardList;
    }

    @Override
    public boolean existsById(Long entityId) {
        return cardList.stream().anyMatch(it -> it.getCardNumber().equals(entityId));
    }

    @Override
    public void delete(Long entityId) {
        cardList.removeIf(it -> it.getCardNumber().equals(entityId));
    }
}
