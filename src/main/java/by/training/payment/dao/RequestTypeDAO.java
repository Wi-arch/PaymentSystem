package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.RequestType;
import by.training.payment.exception.DAOException;

public interface RequestTypeDAO {

	RequestType getRequestTypeByValue(String value) throws DAOException;

	List<RequestType> getAllRequestTypes() throws DAOException;

}
