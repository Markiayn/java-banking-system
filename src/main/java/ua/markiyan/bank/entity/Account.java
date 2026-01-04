package ua.markiyan.bank.entity;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
@Builder
// 1. Коли викликаєш repo.delete(account) -> виконується цей SQL:
@SQLDelete(sql = "UPDATE accounts SET status = 'CLOSED' WHERE id = ?")
// 2. Коли викликаєш repo.findAll() -> додається ця умова:
@SQLRestriction("status = 'ACTIVE'")


public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- ЗВ'ЯЗКИ ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    // --- ПОЛЯ ---
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal balance = new BigDecimal("0.00");

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 3)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private AccountStatus status = AccountStatus.ACTIVE;


    // --- ENUM ---
    public enum Currency {USD, UAH, EUR}

    public enum AccountStatus {ACTIVE, BLOCKED, CLOSED}
}
