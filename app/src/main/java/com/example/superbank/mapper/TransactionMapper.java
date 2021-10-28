package com.example.superbank.mapper;

import com.example.superbank.entity.Card;
import com.example.superbank.entity.Transaction;
import com.example.superbank.payload.request.CardRequestDto;
import com.example.superbank.payload.request.transaction.requestDto.TransactionRequestDto;
import com.example.superbank.payload.response.CardResponseDto;
import com.example.superbank.payload.response.TransactionResponseDto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionRequestDto toRequestDto(Transaction entity);

    @InheritInverseConfiguration
    Transaction toEntity(TransactionRequestDto requestDto);

    TransactionResponseDto toResponseDto(Transaction entity);

    @InheritInverseConfiguration
    Transaction toEntity(TransactionResponseDto responseDto);

}
