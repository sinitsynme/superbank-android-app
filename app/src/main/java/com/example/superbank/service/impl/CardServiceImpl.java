package com.example.superbank.service.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.superbank.R;
import com.example.superbank.exception.ResourceNotFountException;
import com.example.superbank.mapper.CardMapper;
import com.example.superbank.payload.request.CardRequestDto;
import com.example.superbank.payload.response.CardResponseDto;
import com.example.superbank.repository.CardRepository;
import com.example.superbank.service.CardService;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final CardMapper cardMapper = CardMapper.INSTANCE;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public CardResponseDto add(CardRequestDto requestDto) {
        return cardMapper.toResponseDto(cardRepository.add(cardMapper.toEntity(requestDto)));
    }


    @Override
    public CardResponseDto get(Long entityId) {
        return cardMapper.toResponseDto(cardRepository.get(entityId).orElseThrow(() -> new ResourceNotFountException(
                String.format(String.valueOf(R.string.exception_card_not_exists), entityId)
        )));
    }

    @Override
    public List<CardResponseDto> getAll() {
        return null;
    }

    @Override
    public CardResponseDto update(CardRequestDto requestDto, Long entityId) {
        return null;
    }

    @Override
    public boolean existsById(Long entityId) {
        return false;
    }

    @Override
    public void delete(Long entityId) {

    }
}
