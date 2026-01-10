package ua.markiyan.bank.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponse {
    private String accountNumber;
    private BigDecimal balance;
    private String currency;
    private String status;
}
