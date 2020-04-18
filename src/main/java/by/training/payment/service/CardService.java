package by.training.payment.service;

import java.math.BigDecimal;
import java.util.List;

import by.training.payment.entity.Card;
import by.training.payment.entity.Currency;
import by.training.payment.exception.ServiceException;

public interface CardService {

	List<Card> getAllCardsByUserLogin(String login) throws ServiceException;

	void makeSinglCardPayment(Card card, BigDecimal amount, Currency currency, String paymentPurpose, String ccvCode,
			boolean isWriteOffPayment) throws ServiceException;

	void makeTransferFromCardToCard(Card cardFrom, BigDecimal amount, Currency currency, String paymentPurpose,
			String ccvCode, Card cardTo) throws ServiceException;

	void lockCard(Card card) throws ServiceException;

	List<Card> getAllCards() throws ServiceException;

	void blockAllExpiredCards() throws ServiceException;

	Card getCardByNumber(String number) throws ServiceException;

	void unlockCard(Card card) throws ServiceException;

	List<Card> getAvailableToUnlockCardListByUserLogin(String login) throws ServiceException;
}
