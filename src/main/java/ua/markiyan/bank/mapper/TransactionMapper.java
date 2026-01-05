package ua.markiyan.bank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.markiyan.bank.dto.response.TransactionResponse;
import ua.markiyan.bank.entity.Transaction;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper { // 1. Тільки interface!

    @Mapping(source = "sourceAccount.accountNumber", target = "sourceAccountNumber")
    @Mapping(source = "targetAccount.accountNumber", target = "targetAccountNumber")
    @Mapping(source = "createdAt", target = "timestamp") // Переконайся, що в DTO поле називається timestamp
    TransactionResponse toDto(Transaction transaction);

    List<TransactionResponse> toDtoList(List<Transaction> transactions);
}