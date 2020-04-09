package by.training.payment.validator;

import by.training.payment.entity.UserRequest;
import by.training.payment.exception.ServiceException;
import static by.training.payment.factory.RequestStatusFactory.IN_PROCESSING_STATUS;

public class UserRequestValidator {

	public void checkRequiredUserRequestFieldsForNull(UserRequest userRequest) throws ServiceException {
		if (userRequest == null || userRequest.getUser() == null || userRequest.getUser().getLogin() == null
				|| userRequest.getRequestType() == null || userRequest.getRequestType().getValue() == null) {
			throw new ServiceException("Null user request fields");
		}
	}

	public void checkRequestStatusIsInProcessing(UserRequest userRequest) throws ServiceException {
		checkRequiredUserRequestFieldsForNull(userRequest);
		if (!IN_PROCESSING_STATUS.equals(userRequest.getRequestStatus().getValue())) {
			throw new ServiceException("Request has already been processed *Status1027*");
		}
	}
}
