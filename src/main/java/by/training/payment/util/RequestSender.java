package by.training.payment.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

public class RequestSender {

	private static final String NBRN_RATES_API_URL = "http://www.nbrb.by/api/exrates/rates?periodicity=0";
	private static final String UTF_8 = "UTF-8";

	private RequestSender() {

	}

	public static String getCurrencyRatesStringFromNationalBank() throws IOException {
		URL url = new URL(NBRN_RATES_API_URL);
		URLConnection con = url.openConnection();
		InputStream stream = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? UTF_8 : encoding;
		return IOUtils.toString(stream, encoding);
	}
}
