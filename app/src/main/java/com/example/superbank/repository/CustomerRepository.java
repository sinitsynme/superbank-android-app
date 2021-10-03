package com.example.superbank.repository;

import com.example.superbank.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    //signatures for additional methods special for CustomerRepository

    boolean contains(Customer customer);

}
