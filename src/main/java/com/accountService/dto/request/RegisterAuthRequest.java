package com.accountService.dto.request;

import lombok.Data;

@Data
public class RegisterAuthRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
