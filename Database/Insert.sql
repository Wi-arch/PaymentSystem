-- Insert user_role
INSERT INTO user_role VALUES ('User');
INSERT INTO user_role VALUES ('Admin');

-- Insert request_type
INSERT INTO request_type VALUES ('Unlock card');
INSERT INTO request_type VALUES ('Account opening');
INSERT INTO request_type VALUES ('Opening a card to an existing account');
INSERT INTO request_type VALUES ('Card opening with opening a new account');

-- Insert request_status
INSERT INTO request_status VALUES ('In processing');
INSERT INTO request_status VALUES ('Processed');
INSERT INTO request_status VALUES ('Rejected');

-- Insert parameter_header
INSERT INTO parameter_header VALUES ('Card number');
INSERT INTO parameter_header VALUES ('Currency name');
INSERT INTO parameter_header VALUES ('Card expiry date');
INSERT INTO parameter_header VALUES ('Payment system');
INSERT INTO parameter_header VALUES ('Bank account number');

-- Insert currency
INSERT INTO currency  VALUES ('BYN', 1, 1, CURRENT_TIMESTAMP, true);
INSERT INTO currency  VALUES ('USD', 2.5856, 1, CURRENT_TIMESTAMP,  false);
INSERT INTO currency  VALUES ('EUR', 2.8446, 1, CURRENT_TIMESTAMP,  false);
INSERT INTO currency  VALUES ('RUB', 3.2910, 100, CURRENT_TIMESTAMP,  false);
INSERT INTO currency  VALUES ('UAH', 9.1717, 100, CURRENT_TIMESTAMP,  false);
INSERT INTO currency  VALUES ('PLN', 6.2602, 10, CURRENT_TIMESTAMP,  false);
INSERT INTO currency  VALUES ('JPY', 2.3661, 100, CURRENT_TIMESTAMP,  false);
INSERT INTO currency  VALUES ('CNY', 3.6314, 10, CURRENT_TIMESTAMP,  false);
INSERT INTO currency  VALUES ('CHF', 2.6727, 1, CURRENT_TIMESTAMP,  false);
INSERT INTO currency  VALUES ('SEK', 2.4328, 10, CURRENT_TIMESTAMP,  false);
INSERT INTO currency  VALUES ('CZK', 2.4328, 100, CURRENT_TIMESTAMP,  false);
INSERT INTO currency  VALUES ('GBP', 2.4328, 1, CURRENT_TIMESTAMP,  false);
INSERT INTO currency  VALUES ('TRY', 2.4328, 10, CURRENT_TIMESTAMP,  false);
INSERT INTO currency  VALUES ('CAD', 2.4328, 1, CURRENT_TIMESTAMP,  false);
INSERT INTO currency  VALUES ('KZT', 2.4328, 1000, CURRENT_TIMESTAMP,  false);

-- Insert payment_system
INSERT INTO payment_system VALUES ('Visa');
INSERT INTO payment_system VALUES ('MasterCard');
INSERT INTO payment_system VALUES ('UnionPay');
INSERT INTO payment_system VALUES ('БелКарт');
INSERT INTO payment_system VALUES ('American Express');

-- Insert card_expiration_date
INSERT INTO card_expiration_date VALUES (1);
INSERT INTO card_expiration_date VALUES (3);
INSERT INTO card_expiration_date VALUES (5);
