package by.training.payment.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import by.training.payment.exception.ServiceException;
import by.training.payment.factory.ServiceFactory;
import by.training.payment.service.CardService;
import by.training.payment.service.CurrencyService;

/**
 * The {@link DailyTaskHandlerThread} used to complete a chain of daily tasks.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public class DailyTaskHandlerThread extends Thread {

	/** Constant containing {@link Logger} */
	private static final Logger LOGGER = Logger.getLogger(DailyTaskHandlerThread.class);

	/** Constant containing {@link ServiceFactory} */
	private final ServiceFactory serviceFactory = ServiceFactory.getInstance();

	/** Constant containing {@link CardService} */
	private final CardService cardService = serviceFactory.getCardService();

	/** Constant containing {@link CurrencyService} */
	private final CurrencyService currencyService = serviceFactory.getCurrencyService();

	/** Constant containing integer zero */
	private static final int ZERO = 0;

	/**
	 * {@link Date} containing the date of the next event. Gets the value of the
	 * next day after completing the daily task
	 */
	private Date eventDate = new Date();

	/**
	 * Constructor marks this thread as either a {@linkplain #isDaemon daemon}
	 * thread.
	 */
	public DailyTaskHandlerThread() {
		this.setDaemon(true);
	}

	/**
	 * Starts the chain of daily tasks on condition of a new day.The first run of
	 * the task chain occurs when the application starts.
	 */
	@Override
	public void run() {
		while (true) {
			if (isEventDateComing()) {
				handleChainOfTasks();
			}
		}
	}

	/**
	 * Performs a sequence of daily tasks. After completing the last task, assigns
	 * the value of the next day to field {@link DailyTaskHandlerThread#eventDate}.
	 */
	private void handleChainOfTasks() {
		updateAllCurrencies();
		blockAllExpiredCards();
		eventDate = getNextDayDate(getCurrentDate());
		LOGGER.info("Daily tasks completed successfully, next event date " + eventDate);
	}

	/**
	 * @return <code>true</code> if and only if current date is strictly later than
	 *         the event {@link DailyTaskHandlerThread#eventDate}.
	 */
	private boolean isEventDateComing() {
		return getCurrentDate().after(eventDate);
	}

	/**
	 * @param date which is assigned the value of the next day
	 * @return date of which the next day value is set
	 * @throws NullPointerException if indicated date null
	 */
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

	/**
	 * @return {@link Date} containing the current date
	 */
	private Date getCurrentDate() {
		return new Date();
	}

	/**
	 * Start method {@link CardService#blockAllExpiredCards()}
	 */
	private void blockAllExpiredCards() {
		try {
			cardService.blockAllExpiredCards();
		} catch (ServiceException e) {
			LOGGER.error("Cannot handle block all expired cards daily task", e);
		}
	}

	/**
	 * Start method {@link CurrencyService#updateAllCurrenciesFromNationalBank()}
	 */
	private void updateAllCurrencies() {
		try {
			currencyService.updateAllCurrenciesFromNationalBank();
		} catch (ServiceException e) {
			LOGGER.error("Cannot handle update all currencies daily task", e);
		}
	}

}
