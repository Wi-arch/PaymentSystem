package by.training.payment.service;

import java.util.List;

import by.training.payment.entity.UserRequest;
import by.training.payment.exception.ServiceException;

public interface UserRequestService {

	List<UserRequest> getAllUserRequestsByUserLogin(String login) throws ServiceException;
}
