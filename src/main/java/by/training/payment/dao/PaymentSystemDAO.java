package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.PaymentSystem;
import by.training.payment.exception.DAOException;

public interface PaymentSystemDAO {

	void addPaymentSystem(PaymentSystem paymentSystem) throws DAOException;

	void updatePaymentSystem(PaymentSystem paymentSystem) throws DAOException;

	void deletePaymentSystem(PaymentSystem paymentSystem) throws DAOException;

	List<PaymentSystem> getAllPaymentSystems() throws DAOException;

	PaymentSystem getPaymentSystemById(int id) throws DAOException;
}
