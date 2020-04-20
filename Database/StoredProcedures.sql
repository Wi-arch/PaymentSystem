USE `payment_system`;
DROP procedure IF EXISTS `PS_HandleUserRequestToOpenAccount`;

DELIMITER $$
USE `payment_system`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `PS_HandleUserRequestToOpenAccount`(userRequestId int, accountNumber varchar(255),
 userLogin varchar(255), currencyName char(3))
BEGIN
INSERT INTO bank_account (bank_account_number, currency_name, bank_user_login) VALUES (accountNumber, currencyName, userLogin);
UPDATE user_request SET request_status_value='Processed', request_handle_date = CURRENT_TIMESTAMP WHERE user_request_id = userRequestId;
END$$

DELIMITER ;


USE `payment_system`;
DROP procedure IF EXISTS `PS_HandleUserRequestToOpenCard`;

DELIMITER $$
USE `payment_system`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `PS_HandleUserRequestToOpenCard`(userRequestId int, cardNumber varchar(255), validUntilDate datetime,
 cardNumberMask varchar(255), cvcCode varchar(255), cardExpiry int, paymentSystem varchar(255), accountNumber varchar(255), userLogin varchar(255), currencyName char(3))
BEGIN
INSERT INTO bank_account (bank_account_number, currency_name, bank_user_login) VALUES (accountNumber, currencyName, userLogin);
INSERT INTO card (card_number, card_valid_until_date, card_number_mask, card_cvc_code, card_expiration_date_value, payment_system_name, bank_account_number, bank_user_login) 
VALUES (cardNumber, validUntilDate, cardNumberMask, cvcCode, cardExpiry, paymentSystem, accountNumber, userLogin);
UPDATE user_request SET request_status_value='Processed', request_handle_date = CURRENT_TIMESTAMP WHERE user_request_id = userRequestId;
END$$

DELIMITER ;


USE `payment_system`;
DROP procedure IF EXISTS `PS_HandleUserRequestToOpenCardToExistingAccount`;

DELIMITER $$
USE `payment_system`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `PS_HandleUserRequestToOpenCardToExistingAccount`(userRequestId int, cardNumber varchar(255), validUntilDate datetime,
 cardNumberMask varchar(255), cvcCode varchar(255), cardExpiry int, paymentSystem varchar(255), accountNumber varchar(255), userLogin varchar(255))
BEGIN

INSERT INTO card (card_number, card_valid_until_date, card_number_mask, card_cvc_code, card_expiration_date_value, payment_system_name, bank_account_number, bank_user_login) 
VALUES (cardNumber, validUntilDate, cardNumberMask, cvcCode, cardExpiry, paymentSystem, accountNumber, userLogin);
UPDATE user_request SET request_status_value='Processed', request_handle_date = CURRENT_TIMESTAMP WHERE user_request_id = userRequestId; 

END$$

DELIMITER ;


USE `payment_system`;
DROP procedure IF EXISTS `PS_HandleUserRequestToUnlockCard`;

DELIMITER $$
USE `payment_system`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `PS_HandleUserRequestToUnlockCard`(userRequestId int, cardNumber varchar(255))
BEGIN
UPDATE user_request SET request_status_value='Processed', request_handle_date = CURRENT_TIMESTAMP WHERE user_request_id = userRequestId;
UPDATE card SET card_is_blocked=false WHERE card_number = cardNumber; 
END$$

DELIMITER ;


USE `payment_system`;
DROP procedure IF EXISTS `PS_SaveBankAccountTransfer`;

DELIMITER $$
USE `payment_system`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `PS_SaveBankAccountTransfer`(balanceFrom decimal(15,2), bank_account_number_from varchar(255), 
balanceTo decimal(15,2), bank_account_number_to varchar(255))
BEGIN
UPDATE bank_account SET bank_account_balance=balanceFrom WHERE bank_account_number = bank_account_number_from ;
UPDATE bank_account SET bank_account_balance=balanceTo WHERE bank_account_number = bank_account_number_to; 
END$$

DELIMITER ;


USE `payment_system`;
DROP procedure IF EXISTS `PS_SaveRequestParameter`;

DELIMITER $$
USE `payment_system`$$
CREATE PROCEDURE `PS_SaveRequestParameter`(parameterHeader varchar(255), userRequestId int, parameteValue varchar(255))
BEGIN
INSERT INTO request_parameter VALUES (NULL, parameterHeader, userRequestId, parameteValue);
END$$

DELIMITER ;


USE `payment_system`;
DROP procedure IF EXISTS `PS_UpdateCardIsBlocked`;

DELIMITER $$
USE `payment_system`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `PS_UpdateCardIsBlocked`(cardNumber varchar(255), isBlocked tinyint)
BEGIN
UPDATE card SET card_is_blocked = isBlocked WHERE card_number = cardNumber; 
END$$

DELIMITER ;





