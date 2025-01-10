package com.accountService.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankTransferResponse {
    private String transactionReference;
    private String status;
    private String message;
    private BigDecimal amount;
    private String sourceAccount;
    private String destinationAccount;
    private String destinationBankCode;
    private LocalDateTime transactionDate;
    private String narration;
    private String sessionId;
    private String responseCode;
}
