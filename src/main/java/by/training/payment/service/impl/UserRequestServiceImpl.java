package by.training.payment.service.impl;

import java.util.List;

import by.training.payment.dao.UserRequestDAO;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.DAOException;
import by.training.payment.exception.ServiceException;
import by.training.payment.factory.DAOFactory;
import by.training.payment.service.UserRequestService;

public class UserRequestServiceImpl implements UserRequestService {

	private final UserRequestDAO userRequestDAO = DAOFactory.getInstance().getUserRequestDAO();

	@Override
	public List<UserRequest> getAllUserRequestsByUserLogin(String login) throws ServiceException {
		List<UserRequest> userRequestList = null;
		if (login != null) {
			try {
				userRequestList = userRequestDAO.getAllUserRequestsByUserLogin(login);
			} catch (DAOException e) {
				throw new ServiceException(e);
			}
		}
		return userRequestList;
	}
}
