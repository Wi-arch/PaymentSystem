package by.training.payment.factory;

import java.util.HashMap;
import java.util.Map;

import by.training.payment.entity.RequestStatus;

public class RequestStatusFactory {

	private static final RequestStatusFactory INSTANCE = new RequestStatusFactory();
	public static final String IN_PROCESSING_STATUS = "In processing";
	public static final String PROCESSED_STATUS = "Processed";
	public static final String REJECTED_STATUS = "Rejected";
	private final Map<String, RequestStatus> prototypes = new HashMap<>();

	private RequestStatusFactory() {
		prototypes.put(IN_PROCESSING_STATUS, new RequestStatus(IN_PROCESSING_STATUS));
		prototypes.put(PROCESSED_STATUS, new RequestStatus(PROCESSED_STATUS));
		prototypes.put(REJECTED_STATUS, new RequestStatus(REJECTED_STATUS));
	}

	public static RequestStatusFactory getInstance() {
		return INSTANCE;
	}

	public RequestStatus getRequestStatus(String requestStatusValue) {
		return new RequestStatus(prototypes.get(requestStatusValue));
	}
}
