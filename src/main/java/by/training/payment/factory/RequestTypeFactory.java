package by.training.payment.factory;

import java.util.HashMap;
import java.util.Map;

import by.training.payment.entity.RequestType;

public class RequestTypeFactory {

	private static final RequestTypeFactory INSTANCE = new RequestTypeFactory();
	public static final String OPEN_NEW_ACCOUNT_REQUEST = "Account opening";
	public static final String OPEN_NEW_CARD_REQUEST = "Card opening with opening a new account";
	public static final String OPEN_NEW_CARD_TO_EXISTING_ACCOUNT_REQUEST = "Opening a card to an existing account";
	public static final String UNLOCK_CARD_REQUEST = "Unlock card";

	private final Map<String, RequestType> prototypes = new HashMap<>();

	private RequestTypeFactory() {
		prototypes.put(OPEN_NEW_ACCOUNT_REQUEST, new RequestType(OPEN_NEW_ACCOUNT_REQUEST));
		prototypes.put(OPEN_NEW_CARD_REQUEST, new RequestType(OPEN_NEW_CARD_REQUEST));
		prototypes.put(OPEN_NEW_CARD_TO_EXISTING_ACCOUNT_REQUEST, new RequestType(OPEN_NEW_CARD_TO_EXISTING_ACCOUNT_REQUEST));
		prototypes.put(UNLOCK_CARD_REQUEST, new RequestType(UNLOCK_CARD_REQUEST));
	}

	public static RequestTypeFactory getInstance() {
		return INSTANCE;
	}
	
	public RequestType getRequestType (String requestType) {
		return new RequestType(prototypes.get(requestType));
	}
}
