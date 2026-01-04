package ua.markiyan.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.markiyan.bank.entity.Account;
import ua.markiyan.bank.entity.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllBySourceAccountOrTargetAccountOrderByCreatedAtDesc(Account sourceAccount, Account targetAccount);
}
