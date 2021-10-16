package com.example.superbank.mapper;

import com.example.superbank.entity.Card;
import com.example.superbank.payload.request.CardRequestDto;
import com.example.superbank.payload.response.CardResponseDto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardMapper {

    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

    CardRequestDto toRequestDto(Card entity);

    @InheritInverseConfiguration
    Card toEntity(CardRequestDto requestDto);

    CardResponseDto toResponseDto(Card entity);

    @InheritInverseConfiguration
    Card toEntity(CardResponseDto responseDto);
}
