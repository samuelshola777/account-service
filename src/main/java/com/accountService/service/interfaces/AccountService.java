package com.accountService.service.interfaces;

import com.accountService.dto.request.OnBoardCustomerRequest;
import com.accountService.dto.response.OnBoardCustomerResponse;
import com.accountService.dto.request.MakePaymentRequest;
import com.accountService.dto.response.MakePaymentResponse;
import com.accountService.dto.request.AuthLoginRequest;
import com.accountService.dto.response.AuthLoginResponse;
import java.util.List;
import java.util.UUID;
import com.accountService.dto.response.CustomerDashBoardResponse;
import com.accountService.dto.request.BankTransferRequest;
import com.accountService.dto.response.BankTransferResponse;

public interface AccountService {

    /**
     * Onboards a new customer into the system.
     * Validates customer identity using either BVN or NIN.
     * Creates a new customer account if validation is successful.
     *
     * @param request Contains customer details including BVN/NIN, personal info
     * @return Response containing created customer and account details
     * @throws IllegalArgumentException if neither BVN nor NIN is provided
     * @throws IllegalStateException if customer already exists
     */
    OnBoardCustomerResponse onBoardCustomer(OnBoardCustomerRequest request);

    /**
     * Processes a payment transaction for a customer.
     * Validates customer existence and processes payment through payment service.
     *
     * @param request Contains payment details including amount and customer ID
     * @return Response containing payment transaction details
     * @throws RuntimeException if payment processing fails or customer not found
     */
    MakePaymentResponse makePayment(MakePaymentRequest request);

    /**
     * Authenticates a user and retrieves an authentication token.
     * Communicates with auth service for login validation.
     *
     * @param request Contains login credentials
     * @return Response containing authentication token and user details
     */
    AuthLoginResponse authLogin(AuthLoginRequest request);

    /**
     * Retrieves payment transaction history for a specific customer.
     *
     * @param customerId Unique identifier of the customer
     * @return List of payment transactions
     * @throws RuntimeException if retrieval fails or customer not found
     */
    List<MakePaymentResponse> getPaymentHistory(UUID customerId);

    /**
     * Retrieves customer dashboard information including personal details,
     * account balance, status and transaction history.
     *
     * @param customerId Unique identifier of the customer
     * @return Dashboard response containing customer details and transactions
     * @throws RuntimeException if customer not found
     */
    CustomerDashBoardResponse getCustomerDashBoard(UUID customerId);

    /**
     * Initiates a bank transfer for a customer.
     *
     * @param request Contains transfer details including amount, source and destination accounts
     * @return Response containing transfer transaction details
     * @throws RuntimeException if transfer processing fails or customer not found
     */
    BankTransferResponse bankTransfer(BankTransferRequest request);
}
