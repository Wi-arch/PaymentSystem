package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.RequestParameter;
import by.training.payment.exception.DAOException;

public interface RequestParameterDAO {

	void addRequestParameter(RequestParameter requestParameter) throws DAOException;

	void deleteRequestParameter(RequestParameter requestParameter) throws DAOException;

	void updateRequestParameter(RequestParameter requestParameter) throws DAOException;

	RequestParameter getRequestParameterById(int id) throws DAOException;

	List<RequestParameter> getAllRequestParameters() throws DAOException;

	List<RequestParameter> getAllRequestParametersByUserRequestId(int userRequestId) throws DAOException;
}
