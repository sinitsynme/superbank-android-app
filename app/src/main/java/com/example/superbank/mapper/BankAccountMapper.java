package com.example.superbank.mapper;

import com.example.superbank.entity.BankAccount;
import com.example.superbank.payload.request.CustomerRequestDto;
import com.example.superbank.payload.response.BankAccountResponseDto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BankAccountMapper {

    BankAccountMapper INSTANCE = Mappers.getMapper(BankAccountMapper.class);
    CustomerMapper customerMapperInstance = Mappers.getMapper(CustomerMapper.class);

    CustomerRequestDto toRequestDto(BankAccount entity);

    @InheritInverseConfiguration
    BankAccount toEntity(CustomerRequestDto requestDto);

    default BankAccountResponseDto toResponseDto(BankAccount entity){
        return new BankAccountResponseDto(entity.getAccountId(), customerMapperInstance.toResponseDto(entity.getCustomer()));
    }

    BankAccount toEntity(BankAccountResponseDto responseDto);




}
