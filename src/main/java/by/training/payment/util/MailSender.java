package by.training.payment.util;

import java.io.IOException;
import java.util.Properties;

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
