package com.example.superbank.payload.request;

import java.io.Serializable;

public class UserRequestDto implements Serializable {

    private String name;
    private String password;
    private String email;

    public UserRequestDto() {
    }

    public UserRequestDto(String name, String password, String email) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
