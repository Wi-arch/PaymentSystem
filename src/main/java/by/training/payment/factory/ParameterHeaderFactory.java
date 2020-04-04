package by.training.payment.factory;

import java.util.HashMap;
import java.util.Map;

import by.training.payment.entity.ParameterHeader;

public class ParameterHeaderFactory {

	private static final ParameterHeaderFactory INSTANCE = new ParameterHeaderFactory();
	public static final String CARD_EXPIRY_DATE_PARAMETER_HEADER = "Card expiry date";
	public static final String CARD_NUMBER_PARAMETER_HEADER = "Card number";
	public static final String CURRENCY_NAME_PARAMETER_HEADER = "Currency name";
	public static final String PAYMENT_SYSTEM_PARAMETER_HEADER = "Payment system";
	public static final String BANK_ACCOUNT_NUMBER_PARAMETER_HEADER = "Bank account number";
	private final Map<String, ParameterHeader> prototypes = new HashMap<>();

	private ParameterHeaderFactory() {
		prototypes.put(CARD_EXPIRY_DATE_PARAMETER_HEADER, new ParameterHeader(CARD_EXPIRY_DATE_PARAMETER_HEADER));
		prototypes.put(CARD_NUMBER_PARAMETER_HEADER, new ParameterHeader(CARD_NUMBER_PARAMETER_HEADER));
		prototypes.put(CURRENCY_NAME_PARAMETER_HEADER, new ParameterHeader(CURRENCY_NAME_PARAMETER_HEADER));
		prototypes.put(PAYMENT_SYSTEM_PARAMETER_HEADER, new ParameterHeader(PAYMENT_SYSTEM_PARAMETER_HEADER));
		prototypes.put(BANK_ACCOUNT_NUMBER_PARAMETER_HEADER, new ParameterHeader(BANK_ACCOUNT_NUMBER_PARAMETER_HEADER));
	}

	public static ParameterHeaderFactory getInstance() {
		return INSTANCE;
	}

	public ParameterHeader getParameterHeader(String parameterHeaderType) {
		return new ParameterHeader(prototypes.get(parameterHeaderType));
	}

}
