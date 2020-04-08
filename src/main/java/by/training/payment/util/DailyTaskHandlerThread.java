package by.training.payment.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import by.training.payment.exception.ServiceException;
import by.training.payment.factory.ServiceFactory;
import by.training.payment.service.CardService;
import by.training.payment.service.CurrencyService;

public class DailyTaskHandlerThread extends Thread {

	private static final Logger LOGGER = Logger.getLogger(DailyTaskHandlerThread.class);
	private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private final CardService cardService = serviceFactory.getCardService();
	private final CurrencyService currencyService = serviceFactory.getCurrencyService();
	private static final int ZERO = 0;
	private Date eventDate = new Date();

	public DailyTaskHandlerThread() {
		this.setDaemon(true);
	}

	@Override
	public void run() {
		while (true) {
			if (isEventDateComing()) {
				handleChainOfTasks();
			}
		}
	}

	private void handleChainOfTasks() {
		updateAllCurrencies();
		blockAllExpiredCards();
		eventDate = getNextDayDate(getCurrentDate());
		LOGGER.info("Daily tasks completed successfully, next event date " + eventDate);
	}

	private boolean isEventDateComing() {
		return getCurrentDate().after(eventDate);
	}

	private Date getNextDayDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int currentDayNumber = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, ++currentDayNumber);
		calendar.set(Calendar.HOUR, ZERO);
		calendar.set(Calendar.MINUTE, ZERO);
		calendar.set(Calendar.SECOND, ZERO);
		return calendar.getTime();
	}

	private Date getCurrentDate() {
		return new Date();
	}

	private void blockAllExpiredCards() {
		try {
			cardService.blockAllExpiredCards();
		} catch (ServiceException e) {
			LOGGER.error("Cannot handle block all expired cards daily task", e);
		}
	}

	private void updateAllCurrencies() {
		try {
			currencyService.updateAllCurrenciesFromNationalBank();
		} catch (ServiceException e) {
			LOGGER.error("Cannot handle update all currencies daily task", e);
		}
	}

}
