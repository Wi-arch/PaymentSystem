package by.training.payment.factory;

import java.util.Arrays;
import java.util.List;

import static by.training.payment.factory.ParameterHeaderFactory.BANK_ACCOUNT_NUMBER_PARAMETER_HEADER;
import static by.training.payment.factory.ParameterHeaderFactory.CARD_EXPIRY_PARAMETER_HEADER;
import static by.training.payment.factory.ParameterHeaderFactory.CARD_NUMBER_PARAMETER_HEADER;
import static by.training.payment.factory.ParameterHeaderFactory.CURRENCY_NAME_PARAMETER_HEADER;
import static by.training.payment.factory.ParameterHeaderFactory.PAYMENT_SYSTEM_PARAMETER_HEADER;

import by.training.payment.entity.BankAccount;
import by.training.payment.entity.Card;
import by.training.payment.entity.CardExpiry;
import by.training.payment.entity.Currency;
import by.training.payment.entity.PaymentSystem;
import by.training.payment.entity.RequestParameter;
import by.training.payment.exception.ServiceException;
import by.training.payment.validator.BankAccountValidator;
import by.training.payment.validator.CardValidator;
import by.training.payment.validator.CurrencyValidator;

public class RequestParameterBuilder {

	private static final RequestParameterBuilder INSTANCE = new RequestParameterBuilder();
	private final ParameterHeaderFactory factory = ParameterHeaderFactory.getInstance();
	private final CardValidator cardValidator = new CardValidator();
	private final BankAccountValidator bankAccountValidator = new BankAccountValidator();
	private final CurrencyValidator currencyValidator = new CurrencyValidator();

	private RequestParameterBuilder() {
	}

	public static RequestParameterBuilder getInstance() {
		return INSTANCE;
	}

	public RequestParameter buildCardRequestParameter(Card card) throws ServiceException {
		cardValidator.checkCardNumberForNull(card);
		RequestParameter cardParameter = new RequestParameter();
		cardParameter.setParameterHeader(factory.getParameterHeader(CARD_NUMBER_PARAMETER_HEADER));
		cardParameter.setValue(card.getNumber());
		return cardParameter;
	}

	public RequestParameter buildCardExpirationDateRequestParameter(CardExpiry cardExpiry) throws ServiceException {
		checkCardExpiryForNull(cardExpiry);
		RequestParameter cardExpirationDateParameter = new RequestParameter();
		cardExpirationDateParameter.setParameterHeader(factory.getParameterHeader(CARD_EXPIRY_PARAMETER_HEADER));
		cardExpirationDateParameter.setValue(String.valueOf(cardExpiry.getValue()));
		return cardExpirationDateParameter;
	}

	public RequestParameter buildPaymentSystemRequestParameter(PaymentSystem paymentSystem) throws ServiceException {
		checkPaymentSystemNameForNull(paymentSystem);
		RequestParameter paymentSystemParameter = new RequestParameter();
		paymentSystemParameter.setParameterHeader(factory.getParameterHeader(PAYMENT_SYSTEM_PARAMETER_HEADER));
		paymentSystemParameter.setValue(paymentSystem.getName());
		return paymentSystemParameter;
	}

	public RequestParameter buildCurrencyRequestParameter(Currency currency) throws ServiceException {
		currencyValidator.checkCurrencyNameForNull(currency);
		RequestParameter currencyParameter = new RequestParameter();
		currencyParameter.setParameterHeader(factory.getParameterHeader(CURRENCY_NAME_PARAMETER_HEADER));
		currencyParameter.setValue(currency.getName());
		return currencyParameter;
	}

	public RequestParameter buildBankAccountRequestParameter(BankAccount bankAccount) throws ServiceException {
		bankAccountValidator.checkBankAccountNumberForNull(bankAccount);
		RequestParameter bankAccountParameter = new RequestParameter();
		bankAccountParameter.setParameterHeader(factory.getParameterHeader(BANK_ACCOUNT_NUMBER_PARAMETER_HEADER));
		bankAccountParameter.setValue(bankAccount.getAccountNumber());
		return bankAccountParameter;
	}

	public List<RequestParameter> buildRequestParameterList(RequestParameter... requestParameters) {
		return requestParameters != null ? Arrays.asList(requestParameters) : null;
	}

	private void checkPaymentSystemNameForNull(PaymentSystem paymentSystem) throws ServiceException {
		if (paymentSystem == null || paymentSystem.getName() == null) {
			throw new ServiceException("Null paymentsystem name");
		}
	}

	private void checkCardExpiryForNull(CardExpiry cardExpiry) throws ServiceException {
		if (cardExpiry == null) {
			throw new ServiceException("Null card expiration date");
		}
	}
}
