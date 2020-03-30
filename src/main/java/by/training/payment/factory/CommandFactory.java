package by.training.payment.factory;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import by.training.payment.command.Command;
import by.training.payment.command.CommandName;
import by.training.payment.command.RequestParameter;
import by.training.payment.command.language.LanguageCommand;
import by.training.payment.command.user.DeleteUserAccount;
import by.training.payment.command.user.RestoreUserPassword;
import by.training.payment.command.user.ShowUserCurrenciesMenu;
import by.training.payment.command.user.ShowUserDataMenu;
import by.training.payment.command.user.UpdateUserName;
import by.training.payment.command.user.UpdateUserPassword;
import by.training.payment.command.user.UpdateUserSurname;
import by.training.payment.command.user.UserAuthorization;
import by.training.payment.command.user.UserLogin;
import by.training.payment.command.user.UserLogout;
import by.training.payment.command.user.UserRegistration;

public class CommandFactory {

	private final Map<CommandName, Command> commandMap = new HashMap<>();

	public CommandFactory() {
		commandMap.put(CommandName.REGISTRATION, new UserRegistration());
		commandMap.put(CommandName.AUTHORIZATION, new UserAuthorization());
		commandMap.put(CommandName.LOGIN, new UserLogin());
		commandMap.put(CommandName.LOGOUT, new UserLogout());
		commandMap.put(CommandName.RESTORE_PASSWORD, new RestoreUserPassword());
		commandMap.put(CommandName.SHOW_CURRENCIES, new ShowUserCurrenciesMenu());
		commandMap.put(CommandName.SET_LOCALE, new LanguageCommand());
		commandMap.put(CommandName.SHOW_USER_DATA, new ShowUserDataMenu());
		commandMap.put(CommandName.UPDATE_USER_NAME, new UpdateUserName());
		commandMap.put(CommandName.UPDATE_USER_SURNAME, new UpdateUserSurname());
		commandMap.put(CommandName.UPDATE_USER_PASSWORD, new UpdateUserPassword());
		commandMap.put(CommandName.DELETE_USER_ACCOUNT, new DeleteUserAccount());
		// TODO put more...
	}

	public Command defineCommand(HttpServletRequest request) {
		return commandMap.get(CommandName.valueOf(request.getParameter(RequestParameter.COMMAND.toString())));
	}
}
