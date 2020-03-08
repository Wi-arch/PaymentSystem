package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.RequestParameter;
import by.training.payment.entity.UserRequest;
import by.training.payment.exception.DAOException;

public interface UserRequestDAO extends DAO<UserRequest, Integer> {

	void saveUserRequest(UserRequest userRequest, List<RequestParameter> requestParameters) throws DAOException;
}
