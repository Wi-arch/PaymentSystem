package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.BankAccount;
import by.training.payment.entity.Card;
import by.training.payment.entity.RequestParameter;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.DAOException;

public interface UserRequestDAO {

	void addUserRequest(UserRequest userRequest) throws DAOException;

	void deleteUserRequest(UserRequest userRequest) throws DAOException;

	void updateUserRequest(UserRequest userRequest) throws DAOException;

	List<UserRequest> getAllUserRequests() throws DAOException;

	UserRequest getUserRequestById(int id) throws DAOException;

	List<UserRequest> getAllUserRequestsInProcessing() throws DAOException;

	void saveUserRequestWithParameterList(UserRequest userRequest, List<RequestParameter> requestParameters)
			throws DAOException;

	List<UserRequest> getAllUserRequestsByUserLogin(String login) throws DAOException;

	void handleUserRequestToUnlockCard(UserRequest userRequest, Card card) throws DAOException;

	void handleUserRequestToOpenAccount(UserRequest userRequest, BankAccount bankAccount) throws DAOException;

	void handleUserRequestToOpenCard(UserRequest userRequest, Card card, BankAccount bankAccount) throws DAOException;

	void handleUserRequestToOpenCardToExistingAccount(UserRequest userRequest, Card card) throws DAOException;

}
