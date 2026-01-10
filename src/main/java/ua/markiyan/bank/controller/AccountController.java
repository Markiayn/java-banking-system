package ua.markiyan.bank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.markiyan.bank.dto.request.CreateAccountRequest;
import ua.markiyan.bank.dto.response.AccountResponse;
import ua.markiyan.bank.entity.Account;
import ua.markiyan.bank.service.AccountService;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED) // Повертаємо 201 Created (стандарт для створення)
                .body(accountService.createAccount(request));
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAccountBalance(accountNumber));
    }

}
