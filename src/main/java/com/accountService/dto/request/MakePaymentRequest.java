package com.accountService.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class MakePaymentRequest {
     private UUID customerId;
    private BigDecimal amount;
    private String paymentMethod;
}
