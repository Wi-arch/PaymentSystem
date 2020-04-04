package by.training.payment.factory;

import by.training.payment.service.BankAccountService;
import by.training.payment.service.CurrencyService;
import by.training.payment.service.UserRequestService;
import by.training.payment.service.UserService;
import by.training.payment.service.impl.BankAccountServiceImpl;
import by.training.payment.service.impl.CurrencyServiceImpl;
import by.training.payment.service.impl.UserRequestServiceImpl;
import by.training.payment.service.impl.UserServiceImpl;

public class ServiceFactory {

	private final UserService userService = new UserServiceImpl();
	private final CurrencyService currencyService = new CurrencyServiceImpl();
	private final UserRequestService userRequestService = new UserRequestServiceImpl();
	private final BankAccountService bankAccountService = new BankAccountServiceImpl();

	private ServiceFactory() {
	}

	private static class ServiceFactoryInstance {
		private static final ServiceFactory INSTANCE = new ServiceFactory();
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

	public UserRequestService getUserRequestService() {
		return userRequestService;
	}

	public BankAccountService getBankAccountService() {
		return bankAccountService;
	}

}
