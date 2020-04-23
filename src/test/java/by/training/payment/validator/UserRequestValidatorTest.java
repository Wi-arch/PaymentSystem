package by.training.payment.validator;

import static by.training.payment.factory.RequestStatusFactory.IN_PROCESSING_STATUS;
import static by.training.payment.factory.RequestStatusFactory.REJECTED_STATUS;
import static by.training.payment.factory.RequestTypeFactory.UNLOCK_CARD_REQUEST;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import by.training.payment.entity.RequestStatus;
import by.training.payment.entity.RequestType;
import by.training.payment.entity.User;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.RequestStatusFactory;
import by.training.payment.factory.RequestTypeFactory;

public class UserRequestValidatorTest {

	private static final UserRequestValidator USER_REQUEST_VALIDATOR = new UserRequestValidator();	
	private static final User VALID_USER = new User("validLogin111");
	private static final RequestType VALID_REQUEST_TYPE = RequestTypeFactory.getInstance().getRequestType(UNLOCK_CARD_REQUEST);
	private static final RequestStatus IN_PROCESSING = RequestStatusFactory.getInstance().getRequestStatus(IN_PROCESSING_STATUS);
	private static final RequestStatus REJECTED = RequestStatusFactory.getInstance().getRequestStatus(REJECTED_STATUS);
	private UserRequest validUserRequest = new UserRequest();

	@Before
	public void init() {
		validUserRequest.setCreationDate(new Date());
		validUserRequest.setRequestType(VALID_REQUEST_TYPE);
		validUserRequest.setUser(VALID_USER);
		validUserRequest.setRequestStatus(IN_PROCESSING);
	}

	@Test
	public void testCheckRequiredUserRequestFieldsForNullPositive() throws ServiceException {
		USER_REQUEST_VALIDATOR.checkRequiredUserRequestFieldsForNull(validUserRequest);
	}

	@Test(expected = ServiceException.class)
	public void testCheckRequiredUserRequestFieldsForNullNegative() throws ServiceException {
		validUserRequest.setRequestType(null);
		USER_REQUEST_VALIDATOR.checkRequiredUserRequestFieldsForNull(validUserRequest);
	}

	@Test
	public void testCheckRequestStatusIsInProcessingPositive() throws ServiceException {
		USER_REQUEST_VALIDATOR.checkRequestStatusIsInProcessing(validUserRequest);
	}

	@Test(expected = ServiceException.class)
	public void testCheckRequestStatusIsInProcessingNegative() throws ServiceException {
		validUserRequest.setRequestStatus(REJECTED);
		USER_REQUEST_VALIDATOR.checkRequestStatusIsInProcessing(validUserRequest);
	}
		
	@Test
	public void testCheckUserRequestForNullPositive()throws ServiceException{
		USER_REQUEST_VALIDATOR.checkUserRequestForNull(validUserRequest);
	}
	
	@Test(expected = ServiceException.class)
	public void testCheckUserRequestForNullNegative()throws ServiceException{
		validUserRequest = null;
		USER_REQUEST_VALIDATOR.checkUserRequestForNull(validUserRequest);
	}	
}
