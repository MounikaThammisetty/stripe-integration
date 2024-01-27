package com.stripe.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private long amount;
    private String currency;
    private String tokenId;
}
