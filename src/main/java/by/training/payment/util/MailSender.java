package by.training.payment.util;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * The {@link MailSender} used to send messages to specified email addresses.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public class MailSender {

	/** Constant containing {@link Logger} */
	private static final Logger LOGGER = Logger.getLogger(MailSender.class);

	/** Constant {@link String} containing configuration file name */
	private static final String PROPERTIES_FILE = "mail.properties";

	/**
	 * Constant {@link String} containing host key, which is in the configuration
	 * properties file
	 */
	private static final String HOST_KEY = "mail.smtps.host";

	/**
	 * Constant {@link String} containing user key, which is in the configuration
	 * properties file
	 */
	private static final String FROM_KEY = "mail.smtps.user";

	/**
	 * Constant {@link String} containing password key, which is in the
	 * configuration properties file
	 */
	private static final String PASSWORD_KEY = "mail.smtp.password";

	/**
	 * Constant {@link String} containing port key, which is in the configuration
	 * properties file
	 */
	private static final String PORT_KEY = "mail.smtp.port";

	/**
	 * {@link Properties} to which values from the configuration file will be loaded
	 */
	private static final Properties PROPERTIES = new Properties();

	/**
	 * {@link Session} variable to be initialized when creating an instance of the
	 * class
	 */
	private final Session mailSession;

	/**
	 * Constant {@link String} containing host value, to be loaded from
	 * {@link MailSender#PROPERTIES} file
	 */
	private final String host;

	/**
	 * Constant {@link String} containing sender email address value, to be loaded
	 * from {@link MailSender#PROPERTIES} file
	 */
	private final String from;

	/**
	 * Constant {@link String} containing password value, to be loaded from
	 * {@link MailSender#PROPERTIES} file
	 */
	private final String password;

	/**
	 * Constant {@link String} containing port value, to be loaded from
	 * {@link MailSender#PROPERTIES} file
	 */
	private final int port;

	/** Constant {@link String} containing file name with internalized messages */
	private static final String MESSAGES_FILE_NAME = "messages";
	
	/** Constant containing key to registration subject */
	private static final String REGISTRATION_SUBJECT_KEY = "subject.registrationCompleted";
	
	/** Constant containing key to reset password subject */
	private static final String RESET_PASSWORD_SUBJECT_KEY = "subject.passwordRecovery";
	
	/** Constant containing key to registration message */
	private static final String REGISTRATION_TEXT_KEY = "text.registrationText";
	
	/** Constant containing key to reset password message */
	private static final String RESET_PASSWORD_TEXT_KEY = "text.resetPasswordText";
	
	/** Constant containing key to request processed subject */
	private static final String REQUEST_PROCESSED_SUCCESSFULLY_KEY = "subject.requestProcessedSuccessfully";
	
	/** Constant containing key to open account request message */
	private static final String OPEN_ACCOUNT_REQUEST_PROCESSED_KEY = "text.accountOpeningRequestProcessed";
	
	/** Constant containing key to open card to existing account request message */
	private static final String OPEN_CARD_TO_EXISTING_ACCOUNT_PROCESSED_KEY = "text.cardOpeningToExistingAccountRequestProcessed";
	
	/** Constant containing key to open card request message */
	private static final String OPEN_CARD_REQUEST_PROCESSED_KEY = "text.cardOpeningRequestProcessed";
	
	/** Constant containing key to unlock card request message */
	private static final String UNLOCK_CARD_REQUEST_PROCESSED_KEY = "text.cardUnlockRequestProcessed";
	
	/** Field containing an instance of {@link ResourceBundle}  */
	private final ResourceBundle resourceBundle = ResourceBundle.getBundle(MESSAGES_FILE_NAME);

	/**
	 * Private constructor, used to create a single instance of the class.
	 * Initializes all required fields.
	 */
	private MailSender() {
		try {
			PROPERTIES.load(MailSender.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
		} catch (IOException e) {
			LOGGER.fatal("Cannot initialize mail properties", e);
			throw new RuntimeException("Cannot initialize mail properties", e);
		}
		host = PROPERTIES.getProperty(HOST_KEY);
		from = PROPERTIES.getProperty(FROM_KEY);
		password = PROPERTIES.getProperty(PASSWORD_KEY);
		port = Integer.valueOf(PROPERTIES.getProperty(PORT_KEY));
		mailSession = Session.getDefaultInstance(PROPERTIES);
	}

	/**
	 * Private static class containing a single instance of class {@link MailSender}
	 *
	 */
	private static class MailSenderInstance {
		private static final MailSender INSTANCE = new MailSender();
	}

	/**
	 * @return single instance of the {@link MailSender}
	 */
	public static MailSender getInstance() {
		return MailSenderInstance.INSTANCE;
	}

	/**
	 * Sends an email to the specified email address. Each message is sent in a
	 * separate thread.
	 * 
	 * @param to      {@link String} containing the email address of the message
	 *                recipient
	 * @param subject {@link String} containing subject of the message
	 * @param text    {@link String} containing message text
	 */
	public void sendMessage(String to, String subject, String text) {
		new Thread() {
			@Override
			public void run() {
				sendSinglMessage(to, subject, text);
			}
		}.start();
	}
	
	/**
	 * @param to  {@link String} containing the email address of the message
	 *                recipient
	 * @param login {@link String} that will be indicated in the message text as login
	 */
	public void sendMessageRegistrationComplete(String to, String login) {
		sendMessage(to, resourceBundle.getString(REGISTRATION_SUBJECT_KEY), getRegistrationResultMessage(login));
	}

	/**
	 * @param to  {@link String} containing the email address of the message
	 *                recipient
	 * @param password {@link String} that will be indicated in the message text as password
	 */
	public void sendMessageResetPasswordComplete(String to, String password) {
		sendMessage(to, resourceBundle.getString(RESET_PASSWORD_SUBJECT_KEY), getResetPasswordResultMessage(password));
	}

	/**
	 * @param to {@link String} containing the email address of the message
	 *                recipient
	 * @param accountNumber {@link String} that will be indicated in the message text as account number
	 */
	public void sendMessageAccountOpenRequestComplete(String to, String accountNumber) {
		String text = getOpenAccountResultMessage(accountNumber);
		sendMessage(to, resourceBundle.getString(REQUEST_PROCESSED_SUCCESSFULLY_KEY), text);
	}

	/**
	 * 
	 * @param to {@link String} containing the email address of the message
	 *                recipient
	 * @param cardMask {@link String} that will be indicated in the message text as card number
	 */
	public void sendMessageUnlockCardRequestComplete(String to, String cardMask) {
		String text = getUnlockCardResultMessage(cardMask);
		sendMessage(to, resourceBundle.getString(REQUEST_PROCESSED_SUCCESSFULLY_KEY), text);
	}

	/**
	 * 
	 * @param to {@link String} containing the email address of the message
	 *                recipient
	 * @param cardNumber {@link String} that will be indicated in the message text as card number
	 * @param ccv {@link String} that will be indicated in the message text as CCV code
	 */
	public void sendMessageOpenCardRequestComplete(String to, String cardNumber, String ccv) {
		String text = getOpenCardResultMessage(cardNumber, ccv);
		sendMessage(to, resourceBundle.getString(REQUEST_PROCESSED_SUCCESSFULLY_KEY), text);
	}

	/**
	 * 
	 * @param to to {@link String} containing the email address of the message
	 *                recipient
	 * @param accountNumber {@link String} that will be indicated in the message text as account number
	 * @param cardNumber {@link String} that will be indicated in the message text as card number
	 * @param ccv  {@link String} that will be indicated in the message text as CCV code
	 */
	public void sendMessageOpenCardToExistingAccountRequestComplete(String to, String accountNumber, String cardNumber,
			String ccv) {
		String text = getOpenCardToExistingAccountResultMessage(accountNumber, cardNumber, ccv);
		sendMessage(to, resourceBundle.getString(REQUEST_PROCESSED_SUCCESSFULLY_KEY), text);
	}

	private String getOpenCardResultMessage(String cardNumber, String ccv) {
		return String.format(resourceBundle.getString(OPEN_CARD_REQUEST_PROCESSED_KEY), cardNumber, ccv);
	}

	private String getUnlockCardResultMessage(String cardNumber) {
		return String.format(resourceBundle.getString(UNLOCK_CARD_REQUEST_PROCESSED_KEY), cardNumber);
	}

	private String getOpenAccountResultMessage(String accountNumber) {
		return String.format(resourceBundle.getString(OPEN_ACCOUNT_REQUEST_PROCESSED_KEY), accountNumber);
	}

	private String getOpenCardToExistingAccountResultMessage(String accountNumber, String cardNumber, String ccv) {
		return String.format(resourceBundle.getString(OPEN_CARD_TO_EXISTING_ACCOUNT_PROCESSED_KEY), accountNumber,
				cardNumber, ccv);
	}

	private String getRegistrationResultMessage(String login) {
		return String.format(resourceBundle.getString(REGISTRATION_TEXT_KEY), login);
	}

	private String getResetPasswordResultMessage(String password) {
		return String.format(resourceBundle.getString(RESET_PASSWORD_TEXT_KEY), password);
	}

	/**
	 * Sends an email to the specified email address.
	 * 
	 * @param to      {@link String} containing the email address of the message
	 *                recipient
	 * @param subject {@link String} containing subject of the message
	 * @param text    {@link String} containing message text
	 */
	private void sendSinglMessage(String to, String subject, String text) {
		try (Transport transport = mailSession.getTransport()) {
			MimeMessage message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(text);
			transport.connect(host, port, from, password);
			transport.sendMessage(message, message.getAllRecipients());
		} catch (MessagingException e) {
			LOGGER.error("Cannot send message", e);
		}
	}
}
