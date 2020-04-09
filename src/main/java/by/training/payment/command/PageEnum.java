package by.training.payment.command;

public enum PageEnum {

	HOME_PAGE("/index.jsp"), LOGIN_PAGE("/jsp/login.jsp"), USER_MAIN_MENU("/jsp/user/mainMenu.jsp"),
	USER_CURRENCIES_MENU("/jsp/user/mainMenuCurrencies.jsp"), USER_CARDS_MENU("/jsp/user/mainMenuUserCards.jsp"),
	USER_PERSONAL_DATA_MENU("/jsp/user/mainMenuPersonalData.jsp"), ADMIN_MAIN_MENU("/jsp/admin/mainMenu.jsp"),
	USER_REQUESTS_MENU("/jsp/user/mainMenuRequests.jsp"), USER_ACCOUNTS_MENU("/jsp/user/mainMenuUserAccounts.jsp"),
	USER_CREATING_REQUEST_MENU("/jsp/user/mainMenuForCreatingRequests.jsp"), REGISTRATION_PAGE("/jsp/registration.jsp"),
	USER_TRANSACTION_TABLE("/jsp/user/transactionTable.jsp"), ADMIN_CURRENCIES_MENU("/jsp/admin/currenciesMenu.jsp"),
	ADMIN_USERS_MENU("/jsp/admin/usersMenu.jsp"), ADMIN_REQUESTS_MENU("/jsp/admin/requestsMenu.jsp"),
	ADMIN_ACCOUNTS_MENU("/jsp/admin/accountsMenu.jsp"), ADMIN_TRANSACTIONS_TABLE("/jsp/admin/transactionsTable.jsp");

	private String value;

	private PageEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
