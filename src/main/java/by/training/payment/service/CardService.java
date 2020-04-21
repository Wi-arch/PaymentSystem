package by.training.payment.service;

import java.math.BigDecimal;
import java.util.List;

import by.training.payment.entity.Card;
import by.training.payment.entity.Currency;
import by.training.payment.entity.Transaction;
import by.training.payment.exception.ServiceException;

/**
 * Interface describing the behavior of classes for working with {@link Card}
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public interface CardService {

	/**
	 * Returns a list of cards in which the owner matches the login passed as a
	 * parameter. Returns an empty list if no cards were found.
	 * 
	 * @param login based on which the selection of card will be made
	 * @return a list containing {@link Card}, if no accounts are found returns an
	 *         empty list. Never <code>null</code>.
	 * @throws ServiceException if login null or an exception occurred while reading
	 *                          data from a data source
	 */
	List<Card> getAllCardsByUserLogin(String login) throws ServiceException;

	/**
	 * Performs an operation on a payment card specified as a parameter. If
	 * parameter isWriteOffPayment is true, then it deducts the amount indicated in
	 * the parameter quality from the card, , otherwise the amount is added to the
	 * current card balance. The amount of the payment transaction is converted into
	 * the card currency at the exchange rate at the time of the transaction. If as
	 * a result of the payment transaction the card balance is less than zero, then
	 * the operation is not performed and the method throws ServiceException. If a
	 * payment card or bank account is blocked throws ServiceException. If the
	 * completion transaction is successful then it saves {@link Transaction} with
	 * the parameter {@link Transaction#setCompleted(true)}, otherwise with the
	 * parameter false.
	 * 
	 * @param card              payment card used for operation
	 * @param amount            transaction amount
	 * @param currency          currency in which the payment transaction will be
	 *                          completed
	 * @param paymentPurpose    purpose of payment
	 * @param cvcCode           CVC code of the card on which the operation is
	 *                          performed
	 * @param isWriteOffPayment true if the transaction write off cash, false if you
	 *                          need to recharge the card
	 * @throws ServiceException if one of the parameters is not valid. If as a
	 *                          result of the payment transaction the card balance
	 *                          is less than zero. If a payment card or bank account
	 *                          is blocked.
	 */
	void makeSinglCardPayment(Card card, BigDecimal amount, Currency currency, String paymentPurpose, String cvcCode,
			boolean isWriteOffPayment) throws ServiceException;

	/**
	 * Performs the operation of transferring funds from card to card. Deducts the
	 * amount of money indicated as a parameter from the sender’s card and credits
	 * it to the recipient’s card. The transaction amount is converted into the bank
	 * account currency at the exchange rate at the time of the transaction. If as a
	 * result of the payment transaction the sender card balance is less than zero,
	 * then the operation is not performed and the method throws ServiceException.
	 * If a sender payment card or bank account is blocked throws ServiceException.
	 * If the completion transaction is successful then it saves {@link Transaction}
	 * with the parameter {@link Transaction#setCompleted(true)}, otherwise with the
	 * parameter false.
	 * 
	 * @param cardFrom       payment card from which funds will be debited
	 * @param amount         transaction amount
	 * @param currency       currency in which the payment transaction will be
	 *                       completed
	 * @param paymentPurpose purpose of payment
	 * @param cvcCode        CVC code of sender payment card
	 * @param cardTo         to which funds will be credited
	 * @throws ServiceException if one of the parameters is not valid. If as a
	 *                          result of the payment transaction the sender card
	 *                          balance is less than zero. If a sender/receiver
	 *                          payment card or bank account is blocked.
	 */
	void makeTransferFromCardToCard(Card cardFrom, BigDecimal amount, Currency currency, String paymentPurpose,
			String cvcCode, Card cardTo) throws ServiceException;

	/**
	 * Blocks the payment card specified as a parameter. If the card is already
	 * locked throws ServiceException.
	 * 
	 * @param card payment card to be blocked
	 * @throws ServiceException if card null, if specified card not found or if card
	 *                          is already locked.
	 */
	void lockCard(Card card) throws ServiceException;

	/**
	 * Returns a list of all payment cards. If no cards are found, returns an empty
	 * list.
	 * 
	 * @return a list containing cards, if no cards are found returns an empty list.
	 *         Never <code>null</code>.
	 * @throws ServiceException if an exception occurred while reading data from a
	 *                          data source
	 */
	List<Card> getAllCards() throws ServiceException;

	/**
	 * Blocks all cards whose {@link Card#getValidUntilDate()} is earlier than the
	 * current date.
	 * 
	 * @throws ServiceException if an exception occurred while reading/updating data
	 *                          in a data source
	 */
	void blockAllExpiredCards() throws ServiceException;

	/**
	 * Return payment card in which the number matches the number passed as a
	 * parameter. Return null if no matching payment card is found.
	 * 
	 * @param number payment card number on the basis of which the payment card will
	 *               be found
	 * @return {@link Card} in which the number matches the number passed as a
	 *         parameter or null if the card with the specified number is not found
	 * @throws ServiceException if number null or if an exception occurred while
	 *                          reading data from a data source
	 */
	Card getCardByNumber(String number) throws ServiceException;

	/**
	 * Sets the {@link Card#setBlocked(false)} to the specified card. If the card
	 * has expired, throws an ServiceException.
	 * 
	 * @param card payment card to be unlocked
	 * @throws ServiceException if card null, if specified card not found or if the
	 *                          card has expired
	 */
	void unlockCard(Card card) throws ServiceException;

	/**
	 * Returns a list of cards that can be unlocked (i.e. cards that have not
	 * expired) whose owner is the user specified as parameter login. If no such
	 * payment cards are found, returns an empty list.
	 * 
	 * @param login based on which the selection of card will be made
	 * @return list of cards that can be unlocked (i.e. cards that have not expired)
	 *         whose owner is the user specified as parameter login. Never
	 *         <code>null</code>.
	 * @throws ServiceException an exception occurred while reading data from a data
	 *                          source
	 */
	List<Card> getAvailableToUnlockCardListByUserLogin(String login) throws ServiceException;
}
