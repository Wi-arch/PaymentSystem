package by.training.payment.factory;

import by.training.payment.service.CurrencyService;
import by.training.payment.service.UserService;
import by.training.payment.service.impl.CurrencyServiceImpl;
import by.training.payment.service.impl.UserServiceImpl;

public class ServiceFactory {

	private final UserService userService = new UserServiceImpl();
	private final CurrencyService currencyService = new CurrencyServiceImpl();

	private ServiceFactory() {
	}

	private static class ServiceFactoryInstance {
		private final static ServiceFactory INSTANCE = new ServiceFactory();
	}

	public static ServiceFactory getInstance() {
		return ServiceFactoryInstance.INSTANCE;
	}

	public UserService getUserService() {
		return userService;
	}

	public CurrencyService getCurrencyService() {
		return currencyService;
	}

}
