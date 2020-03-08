package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.RequestStatus;
import by.training.payment.exception.DAOException;

public interface RequestStatusDAO {

	void addRequestStatus(RequestStatus requestStatus) throws DAOException;

	void updateRequestStatus(RequestStatus requestStatus) throws DAOException;

	void deleteRequestStatus(RequestStatus requestStatus) throws DAOException;

	List<RequestStatus> getAllRequestStatuses() throws DAOException;

	RequestStatus getRequestStatusById() throws DAOException;
}
