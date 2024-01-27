package com.stripe.dto;

import lombok.Data;

@Data
public class StripeTokenDto {
    public String cardNumber;
    public String expMonth;
    public String expYear;
    public String cvc;
    public String username;
}
