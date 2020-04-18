package by.training.payment.validator;

import static by.training.payment.factory.RequestStatusFactory.IN_PROCESSING_STATUS;

import by.training.payment.entity.UserRequest;
import by.training.payment.exception.ServiceException;

/**
 * The {@link UserRequestValidator} class validates {@link UserRequest} entity.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public class UserRequestValidator {

	/**
	 * Checks for required fields in entity {@link UserRequest} for null.
	 * 
	 * @param userRequest {@link UserRequest} to be validated
	 * @throws ServiceException if {@link UserRequest} or one of required fields are
	 *                          null
	 */
	public void checkRequiredUserRequestFieldsForNull(UserRequest userRequest) throws ServiceException {
		if (userRequest == null || userRequest.getUser() == null || userRequest.getUser().getLogin() == null
				|| userRequest.getRequestType() == null || userRequest.getRequestType().getValue() == null) {
			throw new ServiceException("Null user request fields");
		}
	}

	/**
	 * Check if the {@link UserRequest#getRequestStatus()} value is different from
	 * {@link RequestStatusFactory#IN_PROCESSING_STATUS} then throws an
	 * ServiceException
	 * 
	 * @param userRequest {@link UserRequest} to be validated
	 * @throws ServiceException if {@link UserRequest} null or if
	 *                          {@link UserRequest#getRequestStatus()} value not
	 *                          equals to
	 *                          {@link RequestStatusFactory#IN_PROCESSING_STATUS}
	 */
	public void checkRequestStatusIsInProcessing(UserRequest userRequest) throws ServiceException {
		checkRequiredUserRequestFieldsForNull(userRequest);
		if (!IN_PROCESSING_STATUS.equals(userRequest.getRequestStatus().getValue())) {
			throw new ServiceException("Request has already been processed *Status1027*");
		}
	}

	/**
	 * 
	 * @param userRequest {@link UserRequest} to be validated
	 * @throws ServiceException if {@link UserRequest} null
	 */
	public void checkUserRequestForNull(UserRequest userRequest) throws ServiceException {
		if (userRequest == null) {
			throw new ServiceException("Null user request");
		}
	}
}
