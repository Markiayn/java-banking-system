    package ua.markiyan.bank.dto.request;

    import lombok.Data;
    import ua.markiyan.bank.entity.Account;

    @Data
    public class CreateAccountRequest {
        private Long userId;
        private String currency;
    }
