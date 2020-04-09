package by.training.payment.factory;

import java.util.EnumMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import by.training.payment.command.Command;
import by.training.payment.command.CommandName;
import by.training.payment.command.RequestParameter;
import by.training.payment.command.RestoreUserPassword;
import by.training.payment.command.UserAuthorization;
import by.training.payment.command.UserLogin;
import by.training.payment.command.UserLogout;
import by.training.payment.command.UserRegistration;
import by.training.payment.command.admin.account.LockUserBankAccount;
import by.training.payment.command.admin.account.ShowAdminBankAccountTransactions;
import by.training.payment.command.admin.currency.UpdateCurrenciesFromNationalBank;
import by.training.payment.command.admin.redirect.ShowAdminAccountsMenu;
import by.training.payment.command.admin.redirect.ShowAdminCurrenciesMenu;
import by.training.payment.command.admin.redirect.ShowAdminRequestsMenu;
import by.training.payment.command.admin.redirect.ShowAdminUsersMenu;
import by.training.payment.command.admin.request.FindAllUserRequests;
import by.training.payment.command.admin.request.FindAllUserRequestsInProcessing;
import by.training.payment.command.admin.request.FindUserRequestsByLogin;
import by.training.payment.command.admin.user.FindUserByLogin;
import by.training.payment.command.admin.user.ShowAllUsers;
import by.training.payment.command.language.LanguageCommand;
import by.training.payment.command.user.DeleteUserAccount;
import by.training.payment.command.user.UpdateUserName;
import by.training.payment.command.user.UpdateUserPassword;
import by.training.payment.command.user.UpdateUserSurname;
import by.training.payment.command.user.card.CardDepositOperation;
import by.training.payment.command.user.card.CardWriteOffOperation;
import by.training.payment.command.user.card.LockUserCard;
import by.training.payment.command.user.card.TransferFromCardToCard;
import by.training.payment.command.user.redirect.ShowMenuForCreatingRequest;
import by.training.payment.command.user.redirect.ShowUserBankAccountMenu;
import by.training.payment.command.user.redirect.ShowUserCardsMenu;
import by.training.payment.command.user.redirect.ShowUserCurrenciesMenu;
import by.training.payment.command.user.redirect.ShowUserDataMenu;
import by.training.payment.command.user.redirect.ShowUserRequestsMenu;
import by.training.payment.command.user.request.CreateRequestOpenNewAccount;
import by.training.payment.command.user.request.CreateRequestOpenNewCard;
import by.training.payment.command.user.request.CreateRequestOpenNewCardToExistingAccount;
import by.training.payment.command.user.request.CreateRequestUnlockCard;

public class CommandFactory {

	private final Map<CommandName, Command> commandMap = new EnumMap<>(CommandName.class);

	public CommandFactory() {
		commandMap.put(CommandName.REGISTRATION, new UserRegistration());
		commandMap.put(CommandName.AUTHORIZATION, new UserAuthorization());
		commandMap.put(CommandName.LOGIN, new UserLogin());
		commandMap.put(CommandName.LOGOUT, new UserLogout());
		commandMap.put(CommandName.RESTORE_PASSWORD, new RestoreUserPassword());
		commandMap.put(CommandName.SHOW_USER_CURRENCIES_MENU, new ShowUserCurrenciesMenu());
		commandMap.put(CommandName.SET_LOCALE, new LanguageCommand());
		commandMap.put(CommandName.SHOW_USER_DATA, new ShowUserDataMenu());
		commandMap.put(CommandName.UPDATE_USER_NAME, new UpdateUserName());
		commandMap.put(CommandName.UPDATE_USER_SURNAME, new UpdateUserSurname());
		commandMap.put(CommandName.UPDATE_USER_PASSWORD, new UpdateUserPassword());
		commandMap.put(CommandName.DELETE_USER_ACCOUNT, new DeleteUserAccount());
		commandMap.put(CommandName.SHOW_ALL_USER_REQUESTS, new ShowUserRequestsMenu());
		commandMap.put(CommandName.SHOW_MENU_FOR_CREATING_REQUESTS, new ShowMenuForCreatingRequest());
		commandMap.put(CommandName.CREATE_REQUEST_UNLOCK_CARD, new CreateRequestUnlockCard());
		commandMap.put(CommandName.CREATE_REQUEST_OPEN_NEW_ACCOUNT, new CreateRequestOpenNewAccount());
		commandMap.put(CommandName.CREATE_REQUEST_OPEN_NEW_CARD, new CreateRequestOpenNewCard());
		commandMap.put(CommandName.CREATE_REQUEST_OPEN_NEW_CARD_TO_EXISTING_ACCOUNT, new CreateRequestOpenNewCardToExistingAccount());		
		commandMap.put(CommandName.SHOW_USER_CARDS_MENU, new ShowUserCardsMenu());
		commandMap.put(CommandName.SHOW_USER_BANK_ACCOUNTS_MENU, new ShowUserBankAccountMenu());		
		commandMap.put(CommandName.RECHARGE_CARD, new CardDepositOperation());
		commandMap.put(CommandName.MAKE_CARD_PAYMENT, new CardWriteOffOperation());
		commandMap.put(CommandName.TRANSFER_FROM_CARD_TO_CARD, new TransferFromCardToCard());
		commandMap.put(CommandName.LOCK_CARD, new LockUserCard());
		commandMap.put(CommandName.LOCK_BANK_ACCOUNT, new LockUserBankAccount());		
		commandMap.put(CommandName.SHOW_ADMIN_CURRENCIES_MENU, new ShowAdminCurrenciesMenu());
		commandMap.put(CommandName.UPDATE_CURRENCIES_FROM_NATIONAL_BANK, new UpdateCurrenciesFromNationalBank());
		commandMap.put(CommandName.SHOW_ADMIN_USERS_MENU, new ShowAdminUsersMenu());
		commandMap.put(CommandName.FIND_USER_BY_LOGIN, new FindUserByLogin());
		commandMap.put(CommandName.SHOW_ALL_USERS, new ShowAllUsers());		
		commandMap.put(CommandName.SHOW_ADMIN_REQUESTS_MENU, new ShowAdminRequestsMenu());
		commandMap.put(CommandName.FIND_ALL_USER_REQUESTS, new FindAllUserRequests());
		commandMap.put(CommandName.FIND_ALL_USER_REQUESTS_BY_LOGIN, new FindUserRequestsByLogin());
		commandMap.put(CommandName.FIND_ALL_USER_REQUESTS_IN_PROCESSING, new FindAllUserRequestsInProcessing());
		commandMap.put(CommandName.SHOW_ADMIN_ACCOUNTS_MENU, new ShowAdminAccountsMenu());
		commandMap.put(CommandName.SHOW_ADMIN_ACCOUNT_TRANSACTIONS_TABLE, new ShowAdminBankAccountTransactions());
	}

	public Command defineCommand(HttpServletRequest request) {
		return commandMap.get(CommandName.valueOf(request.getParameter(RequestParameter.COMMAND.toString())));
	}	
}
