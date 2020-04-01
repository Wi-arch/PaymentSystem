package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.ParameterHeader;
import by.training.payment.exception.DAOException;

public interface ParameterHeaderDAO {

	ParameterHeader getParameterHeaderByValue(String value) throws DAOException;

	List<ParameterHeader> getAllParameterHeaders() throws DAOException;
}
