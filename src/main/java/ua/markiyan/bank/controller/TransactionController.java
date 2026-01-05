package ua.markiyan.bank.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.markiyan.bank.dto.request.TransferRequest;
import ua.markiyan.bank.dto.response.TransactionResponse;
import ua.markiyan.bank.entity.Transaction;
import ua.markiyan.bank.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        try{
            transactionService.transferMoney(
                    request.getFromAccountNumber(),
                    request.getToAccountNumber(),
                    request.getAmount()
            );
            return ResponseEntity.ok("Transfer successful");
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @GetMapping("/{accountNumber}/history")
    public ResponseEntity<List<TransactionResponse>> getTransactionHistory(@PathVariable String accountNumber){
        try{
            return ResponseEntity.ok(transactionService.getTransactionHistory(accountNumber));
        } catch ( IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
