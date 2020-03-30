package by.training.payment.command.language;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.training.payment.command.Command;
import by.training.payment.command.PageEnum;
import by.training.payment.command.RequestParameter;

public class LanguageCommand implements Command {

	private static final String ENGLISH_LANGUAGE = "en";
	private static final String RUSSIAN_LANGUAGE = "ru";
	private static final String DEFAULT_LANGUAGE = RUSSIAN_LANGUAGE;
	private static final int ONE_YEAR_IN_SECONDS = 60 * 60 * 24 * 365;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String language = request.getParameter(RequestParameter.LANGUAGE.toString());
		language = language == null ? DEFAULT_LANGUAGE : language;
		Cookie languageCookie = createCookieByLanguage(language);
		languageCookie.setMaxAge(ONE_YEAR_IN_SECONDS);
		request.setAttribute(RequestParameter.LANGUAGE.toString(), languageCookie.getValue());
		response.addCookie(languageCookie);
		return PageEnum.HOME_PAGE.getValue();
	}

	private Cookie createCookieByLanguage(String language) {
		switch (language) {
		case ENGLISH_LANGUAGE:
			return new Cookie(RequestParameter.LANGUAGE.toString(), ENGLISH_LANGUAGE);
		case RUSSIAN_LANGUAGE:
			return new Cookie(RequestParameter.LANGUAGE.toString(), RUSSIAN_LANGUAGE);
		default:
			return new Cookie(RequestParameter.LANGUAGE.toString(), DEFAULT_LANGUAGE);
		}
	}
}
