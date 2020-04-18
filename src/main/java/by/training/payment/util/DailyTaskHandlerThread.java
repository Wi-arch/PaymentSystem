package by.training.payment.util;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
		setDaemon(true);
		setName(DailyTaskHandlerThread.class.getSimpleName());
	}

	/**
	 * Starts the chain of daily tasks on condition of a new day. Checks if a new
	 * day has arrived, if so, it starts the chain of daily tasks. The first run of
	 * the task chain occurs when the thread starting.
	 */
	@Override
	public void run() {
		while (true) {
			if (isEventDateComing()) {
				handleChainOfTasks();
			}
			putCurrentThreadToSleepUntilNextEventDateIsComing();
		}
	}

	/**
	 * Performs a sequence of daily tasks. After completing the last task, assigns
	 * the value of the next day to field {@link DailyTaskHandlerThread#eventDate}.
	 */
	private void handleChainOfTasks() {
		updateAllCurrencies();
		blockAllExpiredCards();
		setNextEventDayDate();
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
	 * Set to {@link DailyTaskHandlerThread#eventDate} next day date value.
	 * 
	 * @throws NullPointerException if indicated date null
	 */
	private void setNextEventDayDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getCurrentDate());
		int currentDayNumber = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, ++currentDayNumber);
		calendar.set(Calendar.HOUR_OF_DAY, ZERO);
		calendar.set(Calendar.MINUTE, ZERO);
		calendar.set(Calendar.SECOND, ZERO);
		eventDate = calendar.getTime();
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

	/**
	 * Causes the currently executing thread to sleep until
	 * {@link DailyTaskHandlerThread#eventDate} is coming.
	 */
	private void putCurrentThreadToSleepUntilNextEventDateIsComing() {
		long currentTime = getCurrentDate().getTime();
		long eventTime = eventDate.getTime();
		try {
			TimeUnit.MILLISECONDS.sleep(eventTime - currentTime);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			LOGGER.warn("Thread is interrupted", e);
		}
	}

}
