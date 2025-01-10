package com.accountService.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import com.accountService.dto.response.MakePaymentResponse;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDashBoardResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private Double accountBalance;
    private String accountStatus;
    private List<MakePaymentResponse> transactionHistory;
    
}

