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

public class RequestParameterValidator {

	private final ParameterHeaderFactory headerFactory = ParameterHeaderFactory.getInstance();
	private final ParameterHeader cardExpiryHeader = headerFactory.getParameterHeader(CARD_EXPIRY_PARAMETER_HEADER);
	private final ParameterHeader paymentSystemHeader = headerFactory.getParameterHeader(PAYMENT_SYSTEM_PARAMETER_HEADER);
	private final ParameterHeader currencyHeader = headerFactory.getParameterHeader(CURRENCY_NAME_PARAMETER_HEADER);

	public void checkRequestParameterListForNull(List<RequestParameter> requestParameterList) throws ServiceException {
		if (requestParameterList == null) {
			throw new ServiceException("Null request parameter list");
		}
		for (RequestParameter parameter : requestParameterList) {
			checkRequestParameterValueForNull(parameter);
		}
	}

	public void checkIsCardExpiryRequestParameter(RequestParameter parameter) throws ServiceException {
		checkRequestParameterValueForNull(parameter);
		if (!cardExpiryHeader.equals(parameter.getParameterHeader()) || !StringUtils.isNumeric(parameter.getValue())) {
			throw new ServiceException("Invalid card expity request parameter");
		}
	}

	public void checkIsPaymentSystemRequestParameter(RequestParameter parameter) throws ServiceException {
		checkRequestParameterValueForNull(parameter);
		if (!paymentSystemHeader.equals(parameter.getParameterHeader())) {
			throw new ServiceException("Invalid payment system request parameter");
		}
	}

	public void checkIsCurrencyRequestParameter(RequestParameter parameter) throws ServiceException {
		checkRequestParameterValueForNull(parameter);
		if (!currencyHeader.equals(parameter.getParameterHeader())) {
			throw new ServiceException("Invalid payment system request parameter");
		}
	}

	public void checkRequestParameterValueForNull(RequestParameter parameter) throws ServiceException {
		if (parameter == null || parameter.getValue() == null) {
			throw new ServiceException("Null request parameter value");
		}
	}
}
