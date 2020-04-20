-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema payment_system
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema payment_system
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `payment_system` DEFAULT CHARACTER SET utf8mb4 ;
USE `payment_system` ;

-- -----------------------------------------------------
-- Table `payment_system`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_system`.`user_role` (
  `user_role_value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_role_value`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `payment_system`.`bank_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_system`.`bank_user` (
  `bank_user_login` VARCHAR(255) NOT NULL,
  `bank_user_password` VARCHAR(255) NOT NULL,
  `bank_user_email` VARCHAR(255) NOT NULL,
  `user_role_value` VARCHAR(45) NOT NULL,
  `bank_user_name` VARCHAR(255) NULL,
  `bank_user_surname` VARCHAR(255) NULL,
  `bank_user_is_blocked` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`bank_user_login`),
  UNIQUE INDEX `user_email_UNIQUE` (`bank_user_email` ASC),
  INDEX `fk_bank_user_user_role1_idx` (`user_role_value` ASC),
  CONSTRAINT `fk_bank_user_user_role1`
    FOREIGN KEY (`user_role_value`)
    REFERENCES `payment_system`.`user_role` (`user_role_value`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `payment_system`.`currency`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_system`.`currency` (
  `currency_name` CHAR(3) NOT NULL,
  `currency_rate` DECIMAL(10,4) NOT NULL,
  `currency_scale` INT NOT NULL,
  `currency_update_date` DATETIME ON UPDATE CURRENT_TIMESTAMP,
  `currency_is_base_currency` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`currency_name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `payment_system`.`bank_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_system`.`bank_account` (
  `bank_account_number` VARCHAR(255) NOT NULL,
  `bank_account_opening_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `bank_account_balance` DECIMAL(15,2) NOT NULL DEFAULT 0,
  `currency_name` CHAR(3) NOT NULL,
  `bank_user_login` VARCHAR(255) NOT NULL,
  `bank_account_is_blocked` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`bank_account_number`),
  INDEX `fk_bank_account_currency1_idx` (`currency_name` ASC),
  INDEX `fk_bank_account_bank_user1_idx` (`bank_user_login` ASC),
  CONSTRAINT `fk_bank_account_currency1`
    FOREIGN KEY (`currency_name`)
    REFERENCES `payment_system`.`currency` (`currency_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bank_account_bank_user1`
    FOREIGN KEY (`bank_user_login`)
    REFERENCES `payment_system`.`bank_user` (`bank_user_login`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `payment_system`.`payment_system`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_system`.`payment_system` (
  `payment_system_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`payment_system_name`),
  UNIQUE INDEX `payment_system_name_UNIQUE` (`payment_system_name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `payment_system`.`card_expiration_date`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_system`.`card_expiration_date` (
  `card_expiration_date_value` INT NOT NULL,
  PRIMARY KEY (`card_expiration_date_value`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `payment_system`.`card`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_system`.`card` (
  `card_number` VARCHAR(255) NOT NULL,
  `card_opening_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `card_valid_until_date` DATETIME NOT NULL,
  `card_number_mask` CHAR(16) NOT NULL,
  `card_cvc_code` VARCHAR(255) NOT NULL,
  `card_expiration_date_value` INT NOT NULL,
  `payment_system_name` VARCHAR(45) NOT NULL,
  `bank_account_number` VARCHAR(255) NOT NULL,
  `bank_user_login` VARCHAR(255) NOT NULL,
  `card_is_blocked` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`card_number`),
  INDEX `fk_card_payment_system1_idx` (`payment_system_name` ASC),
  INDEX `fk_card_card_expiration_date1_idx` (`card_expiration_date_value` ASC),
  INDEX `fk_card_bank_user1_idx` (`bank_user_login` ASC),
  INDEX `fk_card_bank_account1_idx` (`bank_account_number` ASC),
  CONSTRAINT `fk_card_payment_system1`
    FOREIGN KEY (`payment_system_name`)
    REFERENCES `payment_system`.`payment_system` (`payment_system_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_card_card_expiration_date1`
    FOREIGN KEY (`card_expiration_date_value`)
    REFERENCES `payment_system`.`card_expiration_date` (`card_expiration_date_value`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_card_bank_user1`
    FOREIGN KEY (`bank_user_login`)
    REFERENCES `payment_system`.`bank_user` (`bank_user_login`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_card_bank_account1`
    FOREIGN KEY (`bank_account_number`)
    REFERENCES `payment_system`.`bank_account` (`bank_account_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `payment_system`.`payment_transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_system`.`payment_transaction` (
  `transaction_id` INT NOT NULL AUTO_INCREMENT,
  `transaction_completed` TINYINT NOT NULL,
  `transaction_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `transaction_is_write_off` TINYINT NOT NULL,
  `transaction_amount` DECIMAL(15,2) NOT NULL,
  `currency_name` CHAR(3) NOT NULL,
  `transaction_payment_purpose` TEXT NOT NULL,
  `bank_account_number` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`transaction_id`),
  INDEX `fk_payment_transaction_currency1_idx` (`currency_name` ASC),
  INDEX `fk_payment_transaction_bank_account1_idx` (`bank_account_number` ASC),
  CONSTRAINT `fk_payment_transaction_currency1`
    FOREIGN KEY (`currency_name`)
    REFERENCES `payment_system`.`currency` (`currency_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_payment_transaction_bank_account1`
    FOREIGN KEY (`bank_account_number`)
    REFERENCES `payment_system`.`bank_account` (`bank_account_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `payment_system`.`request_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_system`.`request_status` (
  `request_status_value` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`request_status_value`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `payment_system`.`request_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_system`.`request_type` (
  `request_type_value` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`request_type_value`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `payment_system`.`user_request`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_system`.`user_request` (
  `user_request_id` INT NOT NULL AUTO_INCREMENT,
  `bank_user_login` VARCHAR(255) NOT NULL,
  `request_type_value` VARCHAR(255) NOT NULL,
  `request_status_value` VARCHAR(255) DEFAULT 'In processing',
  `request_creation_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `request_handle_date` DATETIME NULL,
  PRIMARY KEY (`user_request_id`),
  INDEX `fk_user_request_request_status1_idx` (`request_status_value` ASC),
  INDEX `fk_user_request_request_type1_idx` (`request_type_value` ASC),
  INDEX `fk_user_request_bank_user1_idx` (`bank_user_login` ASC),
  CONSTRAINT `fk_user_request_request_status1`
    FOREIGN KEY (`request_status_value`)
    REFERENCES `payment_system`.`request_status` (`request_status_value`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_request_request_type1`
    FOREIGN KEY (`request_type_value`)
    REFERENCES `payment_system`.`request_type` (`request_type_value`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_request_bank_user1`
    FOREIGN KEY (`bank_user_login`)
    REFERENCES `payment_system`.`bank_user` (`bank_user_login`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `payment_system`.`parameter_header`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_system`.`parameter_header` (
  `parameter_header_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`parameter_header_name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `payment_system`.`request_parameter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_system`.`request_parameter` (
  `request_parameter_id` INT NOT NULL AUTO_INCREMENT,
  `parameter_header_name` VARCHAR(255) NOT NULL,
  `user_request_id` INT NOT NULL,
  `value` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`request_parameter_id`),
  INDEX `fk_parameter_user_request1_idx` (`user_request_id` ASC),
  INDEX `fk_request_parameter_parameter_header1_idx` (`parameter_header_name` ASC),
  CONSTRAINT `fk_parameter_user_request1`
    FOREIGN KEY (`user_request_id`)
    REFERENCES `payment_system`.`user_request` (`user_request_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_request_parameter_parameter_header1`
    FOREIGN KEY (`parameter_header_name`)
    REFERENCES `payment_system`.`parameter_header` (`parameter_header_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;






