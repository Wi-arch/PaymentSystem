package by.training.payment.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

/**
 * The {@link RequestSender} used to send requests to various URLs.
 * 
 * @author Alexandr Borovets
 * @since JDK1.8
 */
public class RequestSender {

	/**
	 * Constant containing URL address for receiving rates of the national bank of
	 * the republic of Belarus as of the current date.
	 */
	private static final String NBRN_RATES_API_URL = "http://www.nbrb.by/api/exrates/rates?periodicity=0";

	/** Constant {@link String} containing UTF-8 value */
	private static final String UTF_8 = "UTF-8";

	/**
	 * Private constructor prohibiting the creation of class instances.
	 */
	private RequestSender() {

	}

	/**
	 * @see http://www.nbrb.by/apihelp/exrates
	 * @return {@link String} containing array of objects containing the following
	 *         properties: Cur_ID - internal code Date - the date on which the
	 *         course is requested Cur_Abbreviation - letter code Cur_Scale - the
	 *         number of units of foreign currency Cur_Name - the name of the
	 *         currency in Russian in the plural or singular, depending on the
	 *         number of units Cur_OfficialRate - course.
	 * @throws IOException if an exception occurred when calling a methods
	 *                     {@link IOUtils#toString(InputStream, java.nio.charset.Charset)},
	 *                     {@link URL#openConnection()}
	 */
	public static String getCurrencyRatesStringFromNationalBank() throws IOException {
		URL url = new URL(NBRN_RATES_API_URL);
		URLConnection con = url.openConnection();
		InputStream stream = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? UTF_8 : encoding;
		return IOUtils.toString(stream, encoding);
	}
}
