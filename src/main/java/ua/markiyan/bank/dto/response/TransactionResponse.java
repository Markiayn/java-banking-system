package ua.markiyan.bank.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private Long id;
    private String sourceAccountNumber; // Замість цілого об'єкта Account
    private String targetAccountNumber; // Замість цілого об'єкта Account
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String status;
}