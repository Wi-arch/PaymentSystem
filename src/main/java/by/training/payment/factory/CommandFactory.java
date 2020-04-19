package by.training.payment.factory;

import java.util.EnumMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import by.training.payment.command.Command;
import by.training.payment.command.CommandName;
import by.training.payment.command.ContactUsCommand;
import by.training.payment.command.RequestParameter;
import by.training.payment.command.RestoreUserPasswordCommand;
import by.training.payment.command.UserAuthorizationCommand;
import by.training.payment.command.UserLoginCommand;
import by.training.payment.command.UserLogoutCommand;
import by.training.payment.command.UserRegistrationCommand;
import by.training.payment.command.admin.account.AdminLockUserBankAccountCommand;
import by.training.payment.command.admin.account.AdminShowBankAccountTransactionsCommand;
import by.training.payment.command.admin.account.AdminShowAllBankAccountsCommand;
import by.training.payment.command.admin.account.AdminShowAllUserAccountsByLoginCommand;
import by.training.payment.command.admin.account.AdminUnlockUserBankAccountCommand;
import by.training.payment.command.admin.card.AdminLockUserCardCommand;
import by.training.payment.command.admin.card.AdminUnlockUserCardCommand;
import by.training.payment.command.admin.card.AdminShowCardTransactionsCommand;
import by.training.payment.command.admin.card.AdminShowAllCardsCommand;
import by.training.payment.command.admin.card.AdminShowAllCardsByUserLoginCommand;
import by.training.payment.command.admin.currency.AdminUpdateCurrenciesFromNationalBankCommand;
import by.training.payment.command.admin.redirect.ShowAdminAccountsMenuCommand;
import by.training.payment.command.admin.redirect.ShowAdminCardsMenuCommand;
import by.training.payment.command.admin.redirect.ShowAdminCurrenciesMenuCommand;
import by.training.payment.command.admin.redirect.ShowAdminRequestsMenuCommand;
import by.training.payment.command.admin.redirect.ShowAdminUsersMenuCommand;
import by.training.payment.command.admin.request.AdminFindAllUserRequestsCommand;
import by.training.payment.command.admin.request.AdminFindAllUserRequestsInProcessingCommand;
import by.training.payment.command.admin.request.AdminFindUserRequestsByLoginCommand;
import by.training.payment.command.admin.request.AdminHandleUserRequestCommand;
import by.training.payment.command.admin.request.AdminRejectUserRequestCommand;
import by.training.payment.command.admin.user.AdminLockUserAccountCommand;
import by.training.payment.command.admin.user.AdminFindUserByLoginCommand;
import by.training.payment.command.admin.user.AdminShowAllUsersCommand;
import by.training.payment.command.admin.user.AdminUnlockUserAccountCommand;
import by.training.payment.command.language.LanguageCommand;
import by.training.payment.command.user.UserDeleteAccountCommand;
import by.training.payment.command.user.UserUpdateNameCommand;
import by.training.payment.command.user.UserUpdatePasswordCommand;
import by.training.payment.command.user.UserUpdateSurnameCommand;
import by.training.payment.command.user.account.UserShowBankAccountTransactionsCommand;
import by.training.payment.command.user.card.UserCardDepositOperationCommand;
import by.training.payment.command.user.card.UserCardWriteOffOperationCommand;
import by.training.payment.command.user.card.UserLockCardCommand;
import by.training.payment.command.user.card.UserShowCardTransactionsCommand;
import by.training.payment.command.user.card.UserTransferFromCardToCardCommand;
import by.training.payment.command.user.redirect.ShowUserMenuForCreatingRequestCommand;
import by.training.payment.command.user.redirect.ShowUserBankAccountMenuCommand;
import by.training.payment.command.user.redirect.ShowUserCardsMenuCommand;
import by.training.payment.command.user.redirect.ShowUserCurrenciesMenuCommand;
import by.training.payment.command.user.redirect.ShowUserDataMenuCommand;
import by.training.payment.command.user.redirect.ShowUserRequestsMenuCommand;
import by.training.payment.command.user.request.UserCreateRequestOpenNewAccountCommand;
import by.training.payment.command.user.request.UserCreateRequestOpenNewCardCommand;
import by.training.payment.command.user.request.UserCreateRequestOpenCardToExistingAccountCommand;
import by.training.payment.command.user.request.UserCreateRequestUnlockCardCommand;

public class CommandFactory {

	private final Map<CommandName, Command> commandMap = new EnumMap<>(CommandName.class);

	public CommandFactory() {
		commandMap.put(CommandName.REGISTRATION, new UserRegistrationCommand());
		commandMap.put(CommandName.AUTHORIZATION, new UserAuthorizationCommand());
		commandMap.put(CommandName.LOGIN, new UserLoginCommand());
		commandMap.put(CommandName.LOGOUT, new UserLogoutCommand());
		commandMap.put(CommandName.RESTORE_PASSWORD, new RestoreUserPasswordCommand());
		commandMap.put(CommandName.SHOW_USER_CURRENCIES_MENU, new ShowUserCurrenciesMenuCommand());
		commandMap.put(CommandName.SET_LOCALE, new LanguageCommand());
		commandMap.put(CommandName.SHOW_USER_DATA, new ShowUserDataMenuCommand());
		commandMap.put(CommandName.UPDATE_USER_NAME, new UserUpdateNameCommand());
		commandMap.put(CommandName.UPDATE_USER_SURNAME, new UserUpdateSurnameCommand());
		commandMap.put(CommandName.UPDATE_USER_PASSWORD, new UserUpdatePasswordCommand());
		commandMap.put(CommandName.DELETE_USER_ACCOUNT, new UserDeleteAccountCommand());
		commandMap.put(CommandName.SHOW_ALL_USER_REQUESTS, new ShowUserRequestsMenuCommand());
		commandMap.put(CommandName.SHOW_MENU_FOR_CREATING_REQUESTS, new ShowUserMenuForCreatingRequestCommand());
		commandMap.put(CommandName.CREATE_REQUEST_UNLOCK_CARD, new UserCreateRequestUnlockCardCommand());
		commandMap.put(CommandName.CREATE_REQUEST_OPEN_NEW_ACCOUNT, new UserCreateRequestOpenNewAccountCommand());
		commandMap.put(CommandName.CREATE_REQUEST_OPEN_NEW_CARD, new UserCreateRequestOpenNewCardCommand());
		commandMap.put(CommandName.CREATE_REQUEST_OPEN_NEW_CARD_TO_EXISTING_ACCOUNT, new UserCreateRequestOpenCardToExistingAccountCommand());		
		commandMap.put(CommandName.SHOW_USER_CARDS_MENU, new ShowUserCardsMenuCommand());
		commandMap.put(CommandName.SHOW_USER_BANK_ACCOUNTS_MENU, new ShowUserBankAccountMenuCommand());
		commandMap.put(CommandName.SHOW_USER_ACCOUNT_TRANSACTIONS_TABLE, new UserShowBankAccountTransactionsCommand());
		commandMap.put(CommandName.SHOW_USER_CARD_TRANSACTIONS_TABLE, new UserShowCardTransactionsCommand());
		commandMap.put(CommandName.RECHARGE_CARD, new UserCardDepositOperationCommand());
		commandMap.put(CommandName.MAKE_CARD_PAYMENT, new UserCardWriteOffOperationCommand());
		commandMap.put(CommandName.TRANSFER_FROM_CARD_TO_CARD, new UserTransferFromCardToCardCommand());
		commandMap.put(CommandName.LOCK_CARD, new UserLockCardCommand());
		commandMap.put(CommandName.ADMIN_BLOCK_BANK_ACCOUNT, new AdminLockUserBankAccountCommand());		
		commandMap.put(CommandName.SHOW_ADMIN_CURRENCIES_MENU, new ShowAdminCurrenciesMenuCommand());
		commandMap.put(CommandName.UPDATE_CURRENCIES_FROM_NATIONAL_BANK, new AdminUpdateCurrenciesFromNationalBankCommand());
		commandMap.put(CommandName.SHOW_ADMIN_USERS_MENU, new ShowAdminUsersMenuCommand());
		commandMap.put(CommandName.FIND_USER_BY_LOGIN, new AdminFindUserByLoginCommand());
		commandMap.put(CommandName.SHOW_ALL_USERS, new AdminShowAllUsersCommand());		
		commandMap.put(CommandName.SHOW_ADMIN_REQUESTS_MENU, new ShowAdminRequestsMenuCommand());
		commandMap.put(CommandName.FIND_ALL_USER_REQUESTS, new AdminFindAllUserRequestsCommand());
		commandMap.put(CommandName.FIND_ALL_USER_REQUESTS_BY_LOGIN, new AdminFindUserRequestsByLoginCommand());
		commandMap.put(CommandName.FIND_ALL_USER_REQUESTS_IN_PROCESSING, new AdminFindAllUserRequestsInProcessingCommand());
		commandMap.put(CommandName.SHOW_ADMIN_BANK_ACCOUNTS_MENU, new ShowAdminAccountsMenuCommand());
		commandMap.put(CommandName.ADMIN_SHOW_ALL_TRANSACTIONS_BY_ACCOUNT_NUMBER, new AdminShowBankAccountTransactionsCommand());
		commandMap.put(CommandName.ADMIN_UNBLOCK_BANK_ACCOUNT, new AdminUnlockUserBankAccountCommand());
		commandMap.put(CommandName.REJECT_USER_REQUEST, new AdminRejectUserRequestCommand());
		commandMap.put(CommandName.HANDLE_USER_REQUEST, new AdminHandleUserRequestCommand());
		commandMap.put(CommandName.ADMIN_DELETE_USER_ACCOUNT, new AdminLockUserAccountCommand());
		commandMap.put(CommandName.UNLOCK_USER_ACCOUNT, new AdminUnlockUserAccountCommand());
		commandMap.put(CommandName.FIND_ALL_USER_BANK_ACCOUNTS_BY_LOGIN, new AdminShowAllUserAccountsByLoginCommand());
		commandMap.put(CommandName.FIND_ALL_BANK_ACCOUNTS, new AdminShowAllBankAccountsCommand());
		commandMap.put(CommandName.SHOW_ADMIN_CARDS_MENU, new ShowAdminCardsMenuCommand());
		commandMap.put(CommandName.ADMIN_FIND_ALL_USER_CARDS_BY_LOGIN, new AdminShowAllCardsByUserLoginCommand());
		commandMap.put(CommandName.ADMIN_FIND_ALL_CARDS, new AdminShowAllCardsCommand());
		commandMap.put(CommandName.ADMIN_BLOCK_CARD, new AdminLockUserCardCommand());
		commandMap.put(CommandName.ADMIN_UNBLOCK_CARD, new AdminUnlockUserCardCommand());
		commandMap.put(CommandName.ADMIN_SHOW_ALL_TRANSACTIONS_BY_CARD_NUMBER, new AdminShowCardTransactionsCommand());
		commandMap.put(CommandName.CONTACT_US, new ContactUsCommand());
	}

	public Command defineCommand(HttpServletRequest request) {
		return commandMap.get(CommandName.valueOf(request.getParameter(RequestParameter.COMMAND.toString())));
	}	
}
