package by.training.payment.validator;

import static by.training.payment.factory.ParameterHeaderFactory.CARD_EXPIRY_PARAMETER_HEADER;
import static by.training.payment.factory.ParameterHeaderFactory.CURRENCY_NAME_PARAMETER_HEADER;
import static by.training.payment.factory.ParameterHeaderFactory.PAYMENT_SYSTEM_PARAMETER_HEADER;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import by.training.payment.entity.ParameterHeader;
import by.training.payment.entity.RequestParameter;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.ParameterHeaderFactory;

public class RequestParameterValidatorTest {

	private static final RequestParameterValidator REQUEST_PARAMETER_VALIDATOR = new RequestParameterValidator();
	private static final String VALID_CARD_EXPIRY_VALUE = "5";
	private static final String VALID_PAYMENT_SYSTEM_VALUE = "Visa";
	private static final String VALID_CURRENCY_VALUE = "USD";
	private static final ParameterHeaderFactory HEADER_FACTORY = ParameterHeaderFactory.getInstance();
	private static final ParameterHeader CARD_EXPIRY_HEADER = HEADER_FACTORY.getParameterHeader(CARD_EXPIRY_PARAMETER_HEADER);
	private static final ParameterHeader PAYMENT_SYSTEM_HEADER = HEADER_FACTORY.getParameterHeader(PAYMENT_SYSTEM_PARAMETER_HEADER);
	private static final ParameterHeader CURRENCY_HEADER = HEADER_FACTORY.getParameterHeader(CURRENCY_NAME_PARAMETER_HEADER);
	private List<RequestParameter> validRequestParameterList;
	private RequestParameter validCardExpiryRequestParameter = new RequestParameter();
	private RequestParameter validPaymentSystemRequestParameter = new RequestParameter();
	private RequestParameter validCurrencyRequestParameter = new RequestParameter();

	@Before
	public void init() {
		validRequestParameterList = new ArrayList<>();
		validCardExpiryRequestParameter.setValue(VALID_CARD_EXPIRY_VALUE);
		validCardExpiryRequestParameter.setParameterHeader(CARD_EXPIRY_HEADER);
		validCurrencyRequestParameter.setValue(VALID_CURRENCY_VALUE);
		validCurrencyRequestParameter.setParameterHeader(CURRENCY_HEADER);
		validPaymentSystemRequestParameter.setValue(VALID_PAYMENT_SYSTEM_VALUE);
		validPaymentSystemRequestParameter.setParameterHeader(PAYMENT_SYSTEM_HEADER);
		validRequestParameterList.add(validCardExpiryRequestParameter);
		validRequestParameterList.add(validCurrencyRequestParameter);
		validRequestParameterList.add(validPaymentSystemRequestParameter);
	}

	@Test
	public void testCheckRequestParameterListForNullPositive() throws ServiceException {
		REQUEST_PARAMETER_VALIDATOR.checkRequestParameterListForNull(validRequestParameterList);
	}

	@Test(expected = ServiceException.class)
	public void testCheckRequestParameterListForNullNegative() throws ServiceException {		
		REQUEST_PARAMETER_VALIDATOR.checkRequestParameterListForNull(null);
	}
	
	@Test
	public void testCheckIsCardExpiryRequestParameterPositive()throws ServiceException{
		REQUEST_PARAMETER_VALIDATOR.checkIsCardExpiryRequestParameter(validCardExpiryRequestParameter);
	}
	
	@Test(expected = ServiceException.class)
	public void testCheckIsCardExpiryRequestParameterNegative()throws ServiceException{
		validCardExpiryRequestParameter.setParameterHeader(CURRENCY_HEADER);
		REQUEST_PARAMETER_VALIDATOR.checkIsCardExpiryRequestParameter(validCardExpiryRequestParameter);
	}
		
	@Test
	public void testCheckIsPaymentSystemRequestParameterPositive()throws ServiceException{
		REQUEST_PARAMETER_VALIDATOR.checkIsPaymentSystemRequestParameter(validPaymentSystemRequestParameter);
	}
	
	@Test(expected = ServiceException.class)
	public void testCheckIsPaymentSystemRequestParameterNegative()throws ServiceException{
		validPaymentSystemRequestParameter.setParameterHeader(CURRENCY_HEADER);
		REQUEST_PARAMETER_VALIDATOR.checkIsPaymentSystemRequestParameter(validPaymentSystemRequestParameter);
	}
	
	@Test
	public void testCheckIsCurrencyRequestParameterPositive()throws ServiceException{
		REQUEST_PARAMETER_VALIDATOR.checkIsCurrencyRequestParameter(validCurrencyRequestParameter);
	}
	
	@Test(expected = ServiceException.class)
	public void testCheckIsCurrencyRequestParameterNegative()throws ServiceException{
		validCurrencyRequestParameter.setParameterHeader(PAYMENT_SYSTEM_HEADER);
		REQUEST_PARAMETER_VALIDATOR.checkIsCurrencyRequestParameter(validCurrencyRequestParameter);
	}
	
	@Test
	public void testCheckRequestParameterValueForNullPositive()throws ServiceException{
		REQUEST_PARAMETER_VALIDATOR.checkRequestParameterValueForNull(validCardExpiryRequestParameter);
	}
	
	@Test(expected = ServiceException.class)
	public void testCheckRequestParameterValueForNullNegative()throws ServiceException{
		validCardExpiryRequestParameter.setValue(null);
		REQUEST_PARAMETER_VALIDATOR.checkRequestParameterValueForNull(validCardExpiryRequestParameter);
	}	
}
