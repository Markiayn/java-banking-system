package ua.markiyan.bank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.markiyan.bank.dto.response.TransactionResponse;
import ua.markiyan.bank.entity.Account;
import ua.markiyan.bank.entity.Transaction;
import ua.markiyan.bank.mapper.TransactionMapper;
import ua.markiyan.bank.repository.AccountRepository;
import ua.markiyan.bank.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;




@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;


    @Transactional
    public void transferMoney(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {

        //Перевірка чи існують рахунки відправника
        if(!accountRepository.existsByAccountNumber(fromAccountNumber)) {
            throw new IllegalArgumentException("Source account does not exist");
        }

        //Перевірка чи існують рахунки отримувача
        if(!accountRepository.existsByAccountNumber(toAccountNumber)) {
            throw new IllegalArgumentException("Target account does not exist");
        }


        //Чи не відправляємо гроші на той же рахунок
        if(fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Cannot transfer money to the same account");
        }

        //Чи сума перезаху більше нуля
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сума переказу має бути більше нуля");
        }

        //Знаходження відпраника
        Account source = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

        //Знаходження отримувача
        Account target = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Target account not found"));

        //Перевірка балансу відправника
        if (source.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds in source account");
        }
        //Віднімання суми з рахунку відправника
        BigDecimal sourceBalance = source.getBalance();
        BigDecimal updatedSourceBalance = sourceBalance.subtract(amount);
        source.setBalance(updatedSourceBalance);

        BigDecimal currentBalance = target.getBalance();
        BigDecimal newBalance = currentBalance.add(amount); // (або subtract для списання)
        target.setBalance(newBalance);

        accountRepository.save(source);
        accountRepository.save(target);

        Transaction transcaction = Transaction.builder()
                .sourceAccount(source)
                .targetAccount(target)
                .amount(amount)
                .status(Transaction.Status.SUCCESS)
                .build();

        transactionRepository.save(transcaction);
        System.out.println("Трансакція успішна");
    }

    public List<TransactionResponse> getTransactionHistory (String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        List<Transaction> transactions = transactionRepository.findAllBySourceAccountOrTargetAccountOrderByCreatedAtDesc(account, account);

        return transactionMapper.toDtoList(transactions);
    }
}