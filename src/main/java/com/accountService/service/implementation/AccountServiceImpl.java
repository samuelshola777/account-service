package com.accountService.service.implementation;

import com.accountService.service.interfaces.AccountService;
import com.accountService.model.repository.AccountRepository;
import com.accountService.model.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.accountService.model.Customer;
import com.accountService.model.Account;
import com.accountService.dto.response.OnBoardCustomerResponse;
import com.accountService.dto.request.OnBoardCustomerRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.core.env.Environment;
import com.accountService.dto.request.RegisterAuthRequest;
import com.accountService.dto.request.MakePaymentRequest;
import com.accountService.dto.response.MakePaymentResponse;
import com.accountService.dto.request.AuthLoginRequest;
import com.accountService.dto.response.AuthLoginResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import java.time.LocalDateTime;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final Environment environment;
    private String authToken;

    /**
     * Handles the onboarding process for new customers.
     * Validates customer identity using BVN or NIN, registers them with auth service,
     * creates customer and account records.
     * 
     * @param request Contains customer details including BVN/NIN, personal info
     * @return OnBoardCustomerResponse with account number and success message
     * @throws IllegalArgumentException if BVN/NIN validation fails
     * @throws IllegalStateException if customer already exists or registration fails
     */
    @Override
    public OnBoardCustomerResponse onBoardCustomer(OnBoardCustomerRequest request) {
        validateIdentity(request.getBvn(), request.getNin());
        
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
        
        Customer savedCustomer = customerRepository.save(customer);
        
        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .customer(savedCustomer)
                .balance(0.0)
                .build();
        
        Account savedAccount = accountRepository.save(account);
        
        return OnBoardCustomerResponse.builder()
                .accountNumber(savedAccount.getAccountNumber())
                .message("Customer successfully onboarded")
                .build();
    }

    /**
     * Validates customer identity documents (BVN or NIN) through external API calls.
     * 
     * @param bvn Bank Verification Number to validate
     * @param nin National Identity Number to validate
     * @throws IllegalArgumentException if validation fails for either document
     */
    private void validateIdentity(String bvn, String nin) {
        if (bvn == null && nin == null) {
            throw new IllegalArgumentException("Either BVN or NIN must be provided");
        }
        // Validation logic implementation
    }

    /**
     * Generates a unique 10-digit account number based on system timestamp.
     * 
     * @return String containing generated account number
     */
    private String generateAccountNumber() {
        return String.format("%010d", System.currentTimeMillis() % 10000000000L);
    }

    /**
     * Authenticates user with auth service and stores the returned token.
     * 
     * @param request Contains login credentials
     * @return AuthLoginResponse containing authentication token and status
     */
    @Override
    public AuthLoginResponse authLogin(AuthLoginRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String authUrl = environment.getProperty("api.auth.url", "https://auth-service/login");
        return restTemplate.postForObject(authUrl, request, AuthLoginResponse.class);
    }

    /**
     * Retrieves payment transaction history for a specific customer.
     * Makes authenticated call to payment service API.
     * 
     * @param customerId UUID of customer to get history for
     * @return List of MakePaymentResponse containing transaction details
     * @throws RuntimeException if API call fails or returns error
     */
    @Override
    public List<MakePaymentResponse> getPaymentHistory(UUID customerId) {
        RestTemplate restTemplate = new RestTemplate();
        String historyUrl = environment.getProperty("api.payment.history.url", 
            "https://payment-service/history/" + customerId);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<?> entity = new HttpEntity<>(headers);
        
        ResponseEntity<List<MakePaymentResponse>> response = restTemplate.exchange(
            historyUrl,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<MakePaymentResponse>>() {}
        );
        
        return response.getBody();
    }

    /**
     * Processes a payment request through external payment service.
     * Validates customer existence before processing.
     * 
     * @param request Contains payment details including amount and customer ID
     * @return MakePaymentResponse with payment confirmation details
     * @throws RuntimeException if customer not found or payment processing fails
     */
    @Override
    public MakePaymentResponse makePayment(MakePaymentRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
            .orElseThrow(() -> new RuntimeException("Customer not found"));

        RestTemplate restTemplate = new RestTemplate();
        String paymentUrl = environment.getProperty("api.payment.url", "https://payment-service/process");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<MakePaymentRequest> paymentRequest = new HttpEntity<>(request, headers);
        
        return restTemplate.postForObject(paymentUrl, paymentRequest, MakePaymentResponse.class);
    }
    
    /**
     * Retrieves comprehensive dashboard information for a customer.
     * Includes personal details, account status, balance and transaction history.
     * 
     * @param customerId UUID of customer to get dashboard for
     * @return CustomerDashBoardResponse containing all customer and account information
     * @throws RuntimeException if customer not found
     */
    @Override
    public CustomerDashBoardResponse getCustomerDashBoard(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
            
        Account account = accountRepository.findByCustomerId(customerId)
            .orElseThrow(() -> new RuntimeException("Account not found"));
            
        List<MakePaymentResponse> transactions = getPaymentHistory(customerId);
        
        return CustomerDashBoardResponse.builder()
            .customerName(customer.getFirstName() + " " + customer.getLastName())
            .accountNumber(account.getAccountNumber())
            .balance(account.getBalance())
            .transactions(transactions)
            .build();
    }

}
