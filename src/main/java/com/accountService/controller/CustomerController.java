package com.accountService.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accountService.dto.request.OnBoardCustomerRequest;
import com.accountService.dto.response.OnBoardCustomerResponse;
import com.accountService.service.interfaces.AccountService;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import com.accountService.dto.request.MakePaymentRequest;
import com.accountService.dto.response.MakePaymentResponse;
import com.accountService.dto.request.AuthLoginRequest;
import com.accountService.dto.response.AuthLoginResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final AccountService accountService;

    /**
     * Handles new customer onboarding process
     * @param request Contains customer registration details like name, email, etc.
     * @return ResponseEntity containing the onboarding response with customer details and status
     */
    @PostMapping("/onboard")
    public ResponseEntity<OnBoardCustomerResponse> onboardCustomer(@RequestBody OnBoardCustomerRequest request) {
        return ResponseEntity.ok(accountService.onBoardCustomer(request));
    }

    /**
     * Retrieves payment history for a specific customer
     * @param customerId Unique identifier of the customer
     * @return ResponseEntity containing list of payment transactions made by the customer
     */
    @GetMapping("/payment-history/{customerId}")
    public ResponseEntity<List<MakePaymentResponse>> getPaymentHistory(@PathVariable UUID customerId) {
        return ResponseEntity.ok(accountService.getPaymentHistory(customerId));
    }

    /**
     * Processes a payment transaction for a customer
     * @param request Contains payment details like amount, payment method, etc.
     * @return ResponseEntity containing the payment transaction response with status
     */
    @PostMapping("/make-payment")
    public ResponseEntity<MakePaymentResponse> makePayment(@RequestBody MakePaymentRequest request) {
        return ResponseEntity.ok(accountService.makePayment(request));
    }

    /**
     * Authenticates customer login attempt
     * @param request Contains login credentials like username/email and password
     * @return ResponseEntity containing authentication response with token or status
     */
    @PostMapping("/auth/login")
    public ResponseEntity<AuthLoginResponse> authLogin(@RequestBody AuthLoginRequest request) {
        return ResponseEntity.ok(accountService.authLogin(request));
    }

   }
