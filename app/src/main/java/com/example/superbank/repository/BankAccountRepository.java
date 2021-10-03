package com.example.superbank.repository;

import com.example.superbank.entity.BankAccount;

public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

    //signatures for additional methods special for BankAccount
}
