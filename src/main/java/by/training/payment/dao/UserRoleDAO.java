package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.UserRole;
import by.training.payment.exception.DAOException;

public interface UserRoleDAO {

	UserRole getUserRoleByValue(String value) throws DAOException;

	List<UserRole> getAllUserRoles() throws DAOException;
}
