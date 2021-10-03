package com.example.superbank.service;

import com.example.superbank.payload.request.CardRequestDto;
import com.example.superbank.payload.response.CardResponseDto;

public interface CardService extends PayloadService<CardRequestDto, CardResponseDto, Long> {

}
