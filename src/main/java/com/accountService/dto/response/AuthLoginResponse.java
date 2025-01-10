package com.accountService.dto.response;

import lombok.Data;

@Data
public class AuthLoginResponse {
     private String message;
    private String status;
    private String token;
    private String userId;
}
