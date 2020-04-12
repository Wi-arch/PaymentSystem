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
	private UserRequest validUserRequest = new UserRequest();
	private final User validUser = new User("validLogin111");
	private final RequestType validRequestType = RequestTypeFactory.getInstance().getRequestType(UNLOCK_CARD_REQUEST);
	private final RequestStatus inProcessingStatus = RequestStatusFactory.getInstance().getRequestStatus(IN_PROCESSING_STATUS);
	private final RequestStatus rejectedStatus = RequestStatusFactory.getInstance().getRequestStatus(REJECTED_STATUS);

	@Before
	public void init() {
		validUserRequest.setCreationDate(new Date());
		validUserRequest.setRequestType(validRequestType);
		validUserRequest.setUser(validUser);
		validUserRequest.setRequestStatus(inProcessingStatus);
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
		validUserRequest.setRequestStatus(rejectedStatus);
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
