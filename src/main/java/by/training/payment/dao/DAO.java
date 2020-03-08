package by.training.payment.dao;

import java.util.List;

import by.training.payment.exception.DAOException;

public interface DAO<T, Key> {

	void add(T t) throws DAOException;

	void update(T t) throws DAOException;

	void delete(T t) throws DAOException;

	List<T> getAll() throws DAOException;

	T getByKey(Key key) throws DAOException;
}
