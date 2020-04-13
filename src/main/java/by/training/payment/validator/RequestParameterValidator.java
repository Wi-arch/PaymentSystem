package by.training.payment.validator;

import static by.training.payment.factory.ParameterHeaderFactory.CARD_EXPIRY_PARAMETER_HEADER;
import static by.training.payment.factory.ParameterHeaderFactory.CURRENCY_NAME_PARAMETER_HEADER;
import static by.training.payment.factory.ParameterHeaderFactory.PAYMENT_SYSTEM_PARAMETER_HEADER;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import by.training.payment.entity.ParameterHeader;
import by.training.payment.entity.RequestParameter;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.ParameterHeaderFactory;

/**
 * The {@link RequestParameterValidator} class validates
 * {@link RequestParameter} entity.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public class RequestParameterValidator {

	/** Field containing {@link ParameterHeaderFactory} instance */
	private final ParameterHeaderFactory headerFactory = ParameterHeaderFactory.getInstance();

	/**
	 * Field containing {@link ParameterHeaderFactory#CARD_EXPIRY_PARAMETER_HEADER}
	 * value
	 */
	private final ParameterHeader cardExpiryHeader = headerFactory.getParameterHeader(CARD_EXPIRY_PARAMETER_HEADER);

	/**
	 * Field containing
	 * {@link ParameterHeaderFactory#PAYMENT_SYSTEM_PARAMETER_HEADER} value
	 */
	private final ParameterHeader paymentSystemHeader = headerFactory.getParameterHeader(PAYMENT_SYSTEM_PARAMETER_HEADER);

	/**
	 * Field containing
	 * {@link ParameterHeaderFactory#CURRENCY_NAME_PARAMETER_HEADER} value
	 */
	private final ParameterHeader currencyHeader = headerFactory.getParameterHeader(CURRENCY_NAME_PARAMETER_HEADER);

	/**
	 * 
	 * @param requestParameterList {@link List<RequestParameter>} to be validated
	 * @throws ServiceException if {@link List<RequestParameter>} null or one of
	 *                          {@link List<RequestParameter>} elements null
	 */
	public void checkRequestParameterListForNull(List<RequestParameter> requestParameterList) throws ServiceException {
		if (requestParameterList == null) {
			throw new ServiceException("Null request parameter list");
		}
		for (RequestParameter parameter : requestParameterList) {
			checkRequestParameterValueForNull(parameter);
		}
	}

	/**
	 * 
	 * @param parameter {@link RequestParameter} to be validated
	 * @throws ServiceException if {@link RequestParameter} null or
	 *                          {@link RequestParameter} is not
	 *                          {@link RequestParameterValidator#cardExpiryHeader}
	 */
	public void checkIsCardExpiryRequestParameter(RequestParameter parameter) throws ServiceException {
		checkRequestParameterValueForNull(parameter);
		if (!cardExpiryHeader.equals(parameter.getParameterHeader()) || !StringUtils.isNumeric(parameter.getValue())) {
			throw new ServiceException("Invalid card expity request parameter");
		}
	}

	/**
	 * 
	 * @param parameter {@link RequestParameter} to be validated
	 * @throws ServiceException if {@link RequestParameter} null or
	 *                          {@link RequestParameter} is not
	 *                          {@link RequestParameterValidator#paymentSystemHeader}
	 */
	public void checkIsPaymentSystemRequestParameter(RequestParameter parameter) throws ServiceException {
		checkRequestParameterValueForNull(parameter);
		if (!paymentSystemHeader.equals(parameter.getParameterHeader())) {
			throw new ServiceException("Invalid payment system request parameter");
		}
	}

	/**
	 * 
	 * @param parameter {@link RequestParameter} to be validated
	 * @throws ServiceException if {@link RequestParameter} null or
	 *                          {@link RequestParameter} is not
	 *                          {@link RequestParameterValidator#currencyHeader}
	 */
	public void checkIsCurrencyRequestParameter(RequestParameter parameter) throws ServiceException {
		checkRequestParameterValueForNull(parameter);
		if (!currencyHeader.equals(parameter.getParameterHeader())) {
			throw new ServiceException("Invalid payment system request parameter");
		}
	}

	/**
	 * 
	 * @param parameter {@link RequestParameter} to be validated
	 * @throws ServiceException if {@link RequestParameter} null or
	 *                          {@link RequestParameter#getValue()} return null
	 *                          value
	 */
	public void checkRequestParameterValueForNull(RequestParameter parameter) throws ServiceException {
		if (parameter == null || parameter.getValue() == null) {
			throw new ServiceException("Null request parameter value");
		}
	}
}
