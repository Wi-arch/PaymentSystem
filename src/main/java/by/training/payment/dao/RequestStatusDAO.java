package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.RequestStatus;
import by.training.payment.exception.DAOException;

public interface RequestStatusDAO {

	RequestStatus getRequestStatusByValue(String value) throws DAOException;

	List<RequestStatus> getRequestStatusList() throws DAOException;
}
