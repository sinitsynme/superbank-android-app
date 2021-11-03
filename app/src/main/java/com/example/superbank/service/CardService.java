package com.example.superbank.service;

import com.example.superbank.payload.request.CardRequestDto;
import com.example.superbank.payload.request.CustomerRequestDto;
import com.example.superbank.payload.response.CardResponseDto;
import com.example.superbank.payload.response.CustomerResponseDto;
import com.example.superbank.service.secured.Updater;

public interface CardService extends PayloadService<CardRequestDto, CardResponseDto, Long>,
        Updater<CardRequestDto, CardResponseDto, Long> {

}
