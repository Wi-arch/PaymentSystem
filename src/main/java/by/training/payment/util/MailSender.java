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

public class MailSender {

	private static final Logger LOGGER = Logger.getLogger(MailSender.class);
	private static final String PROPERTIES_FILE = "mail.properties";
	private static final String HOST_KEY = "mail.smtps.host";
	private static final String FROM_KEY = "mail.smtps.user";
	private static final String PASSWORD_KEY = "mail.smtp.password";
	private static final String PORT_KEY = "mail.smtp.port";
	private static final Properties PROPERTIES = new Properties();
	private final Session mailSession;
	private final String host;
	private final String from;
	private final String password;
	private final int port;

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

	private static class MailSenderInstance {
		private static final MailSender INSTANCE = new MailSender();
	}

	public static MailSender getInstance() {
		return MailSenderInstance.INSTANCE;
	}

	public void sendMessage(String to, String subject, String text) {
		new Thread() {
			@Override
			public void run() {
				sendSinglMessage(to, subject, text);
			}
		}.start();
	}

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
