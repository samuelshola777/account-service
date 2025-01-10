package com.accountService.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class MakePaymentResponse {
     private UUID transactionId;
    private BigDecimal amount;
    private String status;
    private LocalDateTime createdAt;
}

