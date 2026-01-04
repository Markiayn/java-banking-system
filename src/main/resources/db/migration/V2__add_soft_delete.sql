-- 1. Додаємо статус для Акаунтів
ALTER TABLE accounts
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE';

-- 2. Обмеження, щоб статус був тільки правильний
ALTER TABLE accounts
    ADD CONSTRAINT check_account_status
        CHECK (status IN ('ACTIVE', 'BLOCKED', 'CLOSED'));

-- 3. Додаємо статус для Юзерів
ALTER TABLE users
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE';

ALTER TABLE users
    ADD CONSTRAINT check_user_status
        CHECK (status IN ('ACTIVE', 'DELETED', 'BANNED'));