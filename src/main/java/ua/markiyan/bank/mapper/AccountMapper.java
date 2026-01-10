package ua.markiyan.bank.mapper;

import org.mapstruct.Mapper;
import ua.markiyan.bank.dto.response.AccountResponse;
import ua.markiyan.bank.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse toDto(Account account);
}