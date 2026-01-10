package ua.markiyan.bank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.markiyan.bank.dto.request.CreateAccountRequest;
import ua.markiyan.bank.dto.response.AccountResponse;
import ua.markiyan.bank.entity.Account;
import ua.markiyan.bank.entity.User;
import ua.markiyan.bank.mapper.AccountMapper;
import ua.markiyan.bank.repository.AccountRepository;
import ua.markiyan.bank.repository.UserRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    @Transactional

    public AccountResponse createAccount(CreateAccountRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Генерація номеру
        String uuid = UUID.randomUUID().toString();
        String newAccountNumber = "UA" + uuid.substring(0, 8).replace("-", "").toUpperCase();

        Account account = new Account();
        account.setUser(user);
        account.setAccountNumber(newAccountNumber);
        account.setCurrency(Account.Currency.valueOf(request.getCurrency()));
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(Account.AccountStatus.ACTIVE);

        Account savedAccount = accountRepository.save(account);

        return accountMapper.toDto(savedAccount);
    }

    public AccountResponse getAccountBalance(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        return accountMapper.toDto(account);
    }
}