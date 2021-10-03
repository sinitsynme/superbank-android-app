package com.example.superbank.payload.response;

import java.util.Date;

public class CardResponseDto {

    private String hiddenCardNumber;

    private Long availableMoney;

    private String holder;

    private Date validUntil;

    private boolean isBlocked;

    public CardResponseDto(String hiddenCardNumber, Long availableMoney, String holder, Date validUntil, boolean isBlocked) {
        this.hiddenCardNumber = hiddenCardNumber;
        this.availableMoney = availableMoney;
        this.holder = holder;
        this.validUntil = validUntil;
        this.isBlocked = isBlocked;
    }

    public CardResponseDto() {
    }

    public String getHiddenCardNumber() {
        return hiddenCardNumber;
    }

    public void setHiddenCardNumber(String hiddenCardNumber) {
        this.hiddenCardNumber = hiddenCardNumber;
    }

    public Long getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(Long availableMoney) {
        this.availableMoney = availableMoney;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
