package com.accountService.dto.request;

import lombok.Data;

@Data
public class OnBoardCustomerRequest {
    private String bvn;
    private String nin;
    private String phoneNumber;
    private String name;
     private String firstName;
    private String lastName;
    private String email;
    
}
