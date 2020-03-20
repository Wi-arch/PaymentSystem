package by.training.payment.dao;

import java.util.List;

import by.training.payment.entity.PaymentSystem;
import by.training.payment.exception.DAOException;

public interface PaymentSystemDAO {

	PaymentSystem getPaymentSystemById(int id) throws DAOException;

	List<PaymentSystem> getAllPaymentSystems() throws DAOException;

}
