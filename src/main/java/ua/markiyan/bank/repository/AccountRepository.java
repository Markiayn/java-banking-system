package ua.markiyan.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.markiyan.bank.entity.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    @Query(value = "SELECT * FROM accounts WHERE status = 'CLOSED'", nativeQuery = true)
    List<Account> findAllClosedAccounts();

}
