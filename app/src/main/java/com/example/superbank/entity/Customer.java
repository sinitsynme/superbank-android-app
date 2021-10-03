package com.example.superbank.entity;

import com.example.superbank.enums.Country;

import java.util.Date;
import java.util.Objects;

public class Customer {

    //ID
    private Long customerId;

    private String firstName;

    private String lastName;

    private String patronymic;

    private Date birthDate;

    private Country country;

    private String town;

    private BankAccount bankAccount;

    public Customer(String firstName, String lastName, Date birthDate, Country country, String town) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.country = country;
        this.town = town;
    }

    public Customer() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return firstName.equals(customer.firstName) && lastName.equals(customer.lastName) && Objects.equals(patronymic, customer.patronymic) && birthDate.equals(customer.birthDate) && country == customer.country && town.equals(customer.town);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, patronymic, birthDate, country, town);
    }
}
