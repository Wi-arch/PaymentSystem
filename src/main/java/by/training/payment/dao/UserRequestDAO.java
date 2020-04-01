package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.RequestParameter;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.DAOException;

public interface UserRequestDAO {

	void addUserRequest(UserRequest userRequest) throws DAOException;

	void deleteUserRequest(UserRequest userRequest) throws DAOException;

	void updateUserRequest(UserRequest userRequest) throws DAOException;

	List<UserRequest> getAllUserRequests() throws DAOException;

	UserRequest getUserRequestById(int id) throws DAOException;

	void saveUserRequestWithParameterList(UserRequest userRequest, List<RequestParameter> requestParameters)
			throws DAOException;

	List<UserRequest> getAllUserRequestsByUserLogin(String login) throws DAOException;
	
	
}
