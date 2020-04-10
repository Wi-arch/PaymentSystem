package by.training.payment.command;

public enum CommandName {

	REGISTRATION, AUTHORIZATION, LOGIN, LOGOUT, RESTORE_PASSWORD, SET_LOCALE, SHOW_USER_CURRENCIES_MENU, SHOW_USER_DATA,
	UPDATE_USER_NAME, UPDATE_USER_SURNAME, UPDATE_USER_PASSWORD, DELETE_USER_ACCOUNT, SHOW_ALL_USER_REQUESTS,
	SHOW_MENU_FOR_CREATING_REQUESTS, CREATE_REQUEST_OPEN_NEW_ACCOUNT, CREATE_REQUEST_OPEN_NEW_CARD_TO_EXISTING_ACCOUNT,
	CREATE_REQUEST_OPEN_NEW_CARD, CREATE_REQUEST_UNLOCK_CARD, SHOW_USER_BANK_ACCOUNTS_MENU, SHOW_USER_CARDS_MENU,
	RECHARGE_CARD, MAKE_CARD_PAYMENT, TRANSFER_FROM_CARD_TO_CARD, LOCK_CARD, LOCK_BANK_ACCOUNT, UNLOCK_BANK_ACCOUNT,
	SHOW_USER_ACCOUNT_TRANSACTIONS_TABLE, SHOW_ADMIN_CURRENCIES_MENU, UPDATE_CURRENCIES_FROM_NATIONAL_BANK,
	SHOW_ADMIN_USERS_MENU, FIND_USER_BY_LOGIN, SHOW_ALL_USERS, SHOW_ADMIN_REQUESTS_MENU, ADMIN_DELETE_USER_ACCOUNT,
	FIND_ALL_USER_REQUESTS_BY_LOGIN, FIND_ALL_USER_REQUESTS, FIND_ALL_USER_REQUESTS_IN_PROCESSING, UNLOCK_USER_ACCOUNT,
	SHOW_ADMIN_ACCOUNTS_MENU, SHOW_ADMIN_ACCOUNT_TRANSACTIONS_TABLE, REJECT_USER_REQUEST, HANDLE_USER_REQUEST;

}
