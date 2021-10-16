package com.example.superbank.mapper;

import com.example.superbank.entity.Customer;
import com.example.superbank.payload.request.CustomerRequestDto;
import com.example.superbank.payload.response.CustomerResponseDto;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Locale;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerRequestDto toRequestDto(Customer entity);

    default Customer toEntity(CustomerRequestDto requestDto) {
        Customer customer = new Customer(requestDto.getFirstName(), requestDto.getLastName(),
                requestDto.getBirthDate(), requestDto.getCountry(), requestDto.getTown());
        customer.setFirstName(customer.getFirstName().toUpperCase(Locale.ROOT));
        customer.setLastName(customer.getLastName().toUpperCase(Locale.ROOT));

        String patronymic = requestDto.getPatronymic();

        if (patronymic != null && !patronymic.isEmpty()) {
            customer.setPatronymic(patronymic.toUpperCase(Locale.ROOT));
        }

        return customer;
    }

    default CustomerResponseDto toResponseDto(Customer entity) {
        CustomerResponseDto responseDto = new CustomerResponseDto();
        responseDto.setFirstName(entity.getFirstName());
        responseDto.setLastName(String.format("%c.", entity.getLastName().charAt(0)));
        responseDto.setPatronymic(entity.getPatronymic());

        return responseDto;
    }


}
