package by.training.payment.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class CopyrightTag extends TagSupport {

	private static final String COPYRIGHT_TAG = "Copyright \u00a9 2020 Online Payment System. All rights reserved.";

	@Override
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().write(COPYRIGHT_TAG);
		} catch (IOException e) {
			throw new JspException(e);
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

}
