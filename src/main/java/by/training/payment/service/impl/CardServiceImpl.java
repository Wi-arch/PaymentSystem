package by.training.payment.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import by.training.payment.dao.BankAccountDAO;
import by.training.payment.dao.CardDAO;
import by.training.payment.dao.CurrencyDAO;
import by.training.payment.entity.BankAccount;
import by.training.payment.entity.Card;
import by.training.payment.entity.Currency;
import by.training.payment.entity.Transaction;
import by.training.payment.exception.DAOException;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.DAOFactory;
import by.training.payment.service.CardService;
import by.training.payment.validator.BankAccountValidator;
import by.training.payment.validator.CardValidator;
import by.training.payment.validator.CurrencyValidator;
import by.training.payment.validator.UserValidator;

public class CardServiceImpl implements CardService {

	private static final int TWO = 2;
	private final DAOFactory daoFactory = DAOFactory.getInstance();
	private final CardDAO cardDAO = daoFactory.getCardDAO();
	private final CurrencyDAO currencyDAO = daoFactory.getCurrencyDAO();
	private final BankAccountDAO bankAccountDAO = daoFactory.getBankAccountDAO();
	private final UserValidator userValidator = new UserValidator();
	private final CardValidator cardValidator = new CardValidator();
	private final BankAccountValidator bankAccountValidator = new BankAccountValidator();
	private final CurrencyValidator currencyValidator = new CurrencyValidator();

	@Override
	public List<Card> getAllCardsByUserLogin(String login) throws ServiceException {
		userValidator.checkLogin(login);
		try {
			return cardDAO.getAllCardsByUserLogin(login);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void makeSinglCardPayment(Card card, BigDecimal amount, Currency currency, String paymentPurpose,
			String ccvCode, boolean writeOffPayment) throws ServiceException {
		BigDecimal resultAmount = null;
		cardValidator.checkCardNumberForNull(card);
		cardValidator.checkIsAmountPositive(amount);
		currencyValidator.checkCurrencyNameForNull(currency);
		cardValidator.checkIsPaymentPurposeEmpty(paymentPurpose);
		try {
			Card existingCard = cardDAO.getCardByCardNumber(card.getNumber());
			cardValidator.checkIsCardBlocked(existingCard);
			cardValidator.compareCcvCodes(ccvCode, existingCard.getCcv());
			BankAccount bankAccount = existingCard.getBankAccount();
			bankAccountValidator.checkIsBankAccountBlocked(bankAccount);
			Currency currencyFrom = currencyDAO.getCurrencyByCurrencyName(currency.getName());
			BigDecimal conversionAmount = convertCurrency(amount, currencyFrom, bankAccount.getCurrency());
			Transaction transaction = buildTransaction(bankAccount, amount, currency, paymentPurpose);
			if (writeOffPayment) {
				resultAmount = bankAccount.getBalance().subtract(conversionAmount);
				transaction.setWriteOff(writeOffPayment);
			} else {
				resultAmount = bankAccount.getBalance().add(conversionAmount);
			}
			bankAccount.setBalance(resultAmount);
			bankAccountDAO.saveSinglBankAccountOperationWithTransaction(bankAccount, transaction);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void makeTransferFromCardToCard(Card cardFrom, BigDecimal amount, Currency currency, String paymentPurpose,
			String ccvCode, Card cardTo) throws ServiceException {
		cardValidator.checkCardNumberForNull(cardFrom);
		cardValidator.checkCardNumberForNull(cardTo);
		cardValidator.compareSenderAndReceiverCardNumber(cardFrom.getNumber(), cardTo.getNumber());
		cardValidator.checkIsAmountPositive(amount);
		currencyValidator.checkCurrencyNameForNull(currency);
		cardValidator.checkIsPaymentPurposeEmpty(paymentPurpose);
		try {
			Card existingCardFrom = cardDAO.getCardByCardNumber(cardFrom.getNumber());
			cardValidator.checkIsCardBlocked(existingCardFrom);
			cardValidator.compareCcvCodes(ccvCode, existingCardFrom.getCcv());
			BankAccount bankAccountFrom = existingCardFrom.getBankAccount();
			bankAccountValidator.checkIsBankAccountBlocked(bankAccountFrom);
			Card existingCardTo = cardDAO.getCardByCardNumber(cardTo.getNumber());
			cardValidator.checkCardNumberForNull(existingCardTo);
			BankAccount bankAccountTo = existingCardTo.getBankAccount();
			bankAccountValidator.checkIsBankAccountBlocked(bankAccountTo);
			Currency transferCurrency = currencyDAO.getCurrencyByCurrencyName(currency.getName());
			BigDecimal conversionAmountFrom = convertCurrency(amount, transferCurrency, bankAccountFrom.getCurrency());
			BigDecimal conversionAmountTo = convertCurrency(amount, transferCurrency, bankAccountTo.getCurrency());
			bankAccountFrom.setBalance(bankAccountFrom.getBalance().subtract(conversionAmountFrom));
			bankAccountTo.setBalance(bankAccountTo.getBalance().add(conversionAmountTo));
			Transaction transactionFrom = buildTransaction(bankAccountFrom, amount, currency, paymentPurpose);
			transactionFrom.setWriteOff(true);
			Transaction transactionTo = buildTransaction(bankAccountTo, amount, currency, paymentPurpose);
			bankAccountDAO.saveTransfer(bankAccountFrom, transactionFrom, bankAccountTo, transactionTo);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void lockCard(Card card) throws ServiceException {
		cardValidator.checkCardNumberForNull(card);
		try {
			Card existingCard = cardDAO.getCardByCardNumber(card.getNumber());
			cardValidator.checkIsCardBlocked(existingCard);
			existingCard.setBlocked(true);
			cardDAO.updateCard(existingCard);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Card> getAllCards() throws ServiceException {
		try {
			return cardDAO.getAllCards();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void blockAllExpiredCards() throws ServiceException {
		try {
			List<Card> cardList = cardDAO.getAllUnblockedCards();
			for (Card card : cardList) {
				if (cardValidator.isExpiredDateCard(card)) {
					lockCard(card);
				}
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Card getCardByNumber(String number) throws ServiceException {
		try {
			return cardDAO.getCardByCardNumber(number);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private Transaction buildTransaction(BankAccount bankAccount, BigDecimal amount, Currency currency,
			String paymentPurpose) {
		Transaction transaction = new Transaction();
		transaction.setAccount(bankAccount);
		transaction.setAmount(amount);
		transaction.setCurrency(currency);
		transaction.setDate(new Date());
		transaction.setPaymentPurpose(paymentPurpose);
		return transaction;
	}

	private BigDecimal convertCurrency(BigDecimal amount, Currency from, Currency to) throws ServiceException {
		currencyValidator.checkCurrencyNameAndRateForNull(from);
		currencyValidator.checkCurrencyNameAndRateForNull(to);
		if (from.equals(to)) {
			return amount;
		}
		BigDecimal rateFrom = from.getRate();
		BigDecimal rateTo = to.getRate();
		BigDecimal scaleFrom = new BigDecimal(from.getScale());
		BigDecimal scaleTo = new BigDecimal(to.getScale());
		return amount.multiply(scaleTo).multiply(rateFrom.divide(rateTo, TWO, RoundingMode.HALF_UP)).divide(scaleFrom)
				.setScale(TWO, RoundingMode.HALF_UP);
	}

}
