package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.PaymentSystem;
import by.training.payment.exception.DAOException;

public interface PaymentSystemDAO {

	PaymentSystem getPaymentSystemByName(String name) throws DAOException;

	List<PaymentSystem> getAllPaymentSystems() throws DAOException;

}
