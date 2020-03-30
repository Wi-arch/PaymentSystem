package by.training.payment.command;

public enum PageEnum {

	HOME_PAGE("/index.jsp"), LOGIN_PAGE("/jsp/login.jsp"), USER_MAIN_MENU("/jsp/user/mainMenu.jsp"),
	REGISTRATION_PAGE("/jsp/registration.jsp"), USER_CURRENCIES_MENU("/jsp/user/mainMenuCurrencies.jsp"),
	USER_PERSONAL_DATA_MENU("/jsp/user/mainMenuPersonalData.jsp"), ADMIN_MAIN_MENU("/jsp/admin/mainMenu.jsp");

	private String value;

	private PageEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
