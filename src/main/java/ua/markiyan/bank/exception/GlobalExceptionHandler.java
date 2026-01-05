package ua.markiyan.bank.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 1. Це каже Спрінгу: "Я слухаю всі контролери"
public class GlobalExceptionHandler {

    // 2. Ловимо помилки валідації (ті, що ми кидаємо через throw new IllegalArgumentException)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    // 3. Ловимо всі інші непередбачувані помилки
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralError(Exception e) {
        return ResponseEntity.internalServerError().body("An internal error occurred: " + e.getMessage());
    }
}