package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.UserRole;
import by.training.payment.exception.DAOException;

public interface UserRoleDAO {

	void addUserRole(UserRole userRole) throws DAOException;

	void updateUserRole(UserRole userRole) throws DAOException;

	void deleteUserRole(UserRole userRole) throws DAOException;

	List<UserRole> getAllUserRoles() throws DAOException;

	UserRole getUserRoleById(int id) throws DAOException;
}
