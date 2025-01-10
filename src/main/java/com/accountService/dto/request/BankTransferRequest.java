package com.accountService.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankTransferRequest {
    private UUID customerId;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private String destinationBankCode;
    private BigDecimal amount;
    private String narration;
    private String destinationAccountName;
    private String transactionPin;
    private String sessionId;
}
