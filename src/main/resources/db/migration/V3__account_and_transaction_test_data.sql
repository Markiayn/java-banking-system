-- 1. Створюємо тестових користувачів
-- (Прибрали created_at/updated_at, бо їх немає в твоїй таблиці users)
INSERT INTO users (full_name, email, password, status)
VALUES
    ('Markiyan Kobiletskiy', 'markiyan@bank.com', 'pass123', 'ACTIVE'),
    ('Elon Musk', 'elon@tesla.com', 'mars2030', 'ACTIVE'),
    ('Bill Gates', 'bill@microsoft.com', 'windows95', 'ACTIVE');

-- 2. Створюємо рахунки
-- (Прибрали created_at/updated_at, бо їх немає в твоїй таблиці accounts)
INSERT INTO accounts (user_id, account_number, balance, currency, status)
VALUES
    (
        (SELECT id FROM users WHERE email = 'markiyan@bank.com'),
        'UA11111111',
        1500.00,
        'UAH',
        'ACTIVE'
    ),
    (
        (SELECT id FROM users WHERE email = 'elon@tesla.com'),
        'UA22222222',
        1000000.00,
        'USD',
        'ACTIVE'
    ),
    (
        (SELECT id FROM users WHERE email = 'bill@microsoft.com'),
        'UA33333333',
        500.00,
        'EUR',
        'ACTIVE'
    );

-- 3. Створюємо історію транзакцій
-- Тут created_at Є, згідно з твоєю схемою, тому ми його заповнюємо для історії
INSERT INTO transactions (source_account_id, target_account_id, amount, status, created_at)
VALUES
-- Транзакція 1: Ілон кидає тобі (2 дні тому)
(
    (SELECT id FROM accounts WHERE account_number = 'UA22222222'),
    (SELECT id FROM accounts WHERE account_number = 'UA11111111'),
    50000.00,
    'SUCCESS',
    CURRENT_TIMESTAMP - INTERVAL '2 days'
),
-- Транзакція 2: Ти купуєш щось у Білла (1 година тому)
(
    (SELECT id FROM accounts WHERE account_number = 'UA11111111'),
    (SELECT id FROM accounts WHERE account_number = 'UA33333333'),
    100.00,
    'SUCCESS',
    CURRENT_TIMESTAMP - INTERVAL '1 hour'
);