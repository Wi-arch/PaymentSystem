<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="ctg" uri="customtags"%>
<fmt:setLocale value="${cookie.LANGUAGE.value}" />
<c:if test="${not empty LANGUAGE}">
	<fmt:setLocale value="${LANGUAGE}" />
</c:if>
<fmt:setBundle basename="messages" />
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/styles.css">
<title><fmt:message key="title.contactUs" /></title>
</head>
<body class="background">

	<ul>
		<li><a href="#"><fmt:message key="button.language" /></a>
			<ul>
				<li>
					<form action="${pageContext.request.contextPath}/controller"
						method="post">
						<input type="hidden" name="COMMAND" value="SET_LOCALE"> <input
							type="hidden" name="LANGUAGE" value="en">
						<button type="submit" id="childButton">
							<fmt:message key="label.english" />
						</button>
					</form>
				</li>

				<li>
					<form action="${pageContext.request.contextPath}/controller"
						method="post">
						<input type="hidden" name="COMMAND" value="SET_LOCALE"> <input
							type="hidden" name="LANGUAGE" value="ru">
						<button type="submit" id="childButton">
							<fmt:message key="label.russian" />
						</button>
					</form>
				</li>
			</ul></li>
		<li><a href="${pageContext.request.contextPath}"><fmt:message
					key="button.home" /></a></li>
		<li><a href="#"><fmt:message key="button.news" /></a></li>
		<li><a
			href="${pageContext.request.contextPath}/jsp/contactUs.jsp"
			class="active"><fmt:message key="button.contact" /></a></li>
		<li><a href="#"><fmt:message key="button.about" /></a></li>
	</ul>

	<div class="contactUs">
		<form action="${pageContext.request.contextPath}/controller"
			method="post" class="contact">
			<input type="hidden" name="COMMAND" value="CONTACT_US">
			<h3>
				<fmt:message key="label.contactUs" />
			</h3>
			<h4>
				<fmt:message key="text.contactUs" />
			</h4>
			<fieldset>
				<input name="USER_NAME" type="text" required autofocus
					placeholder="<fmt:message key="label.name" />" title=""
					onchange="this.setAttribute('value', this.value);"
					oninvalid="this.setCustomValidity('<fmt:message
						key="text.requiredField" />')"
					oninput="setCustomValidity('')">
			</fieldset>
			<fieldset>
				<input name="USER_EMAIL" required value="" title=""
					placeholder="<fmt:message key="label.email" />" type="email"
					pattern="[a-zA-Z0-9]{1}[a-zA-Z0-9_.-]{1,29}[a-zA-Z0-9]{1}@[a-zA-Z]{2,10}\.[a-zA-Z]{2,}"
					oninvalid="this.setCustomValidity('<fmt:message key="*Status1007*" />')"
					onchange="this.setAttribute('value', this.value); this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message key="*Status1007*" />' : '');">
			</fieldset>
			<fieldset>
				<textarea name="USER_TEXT" required title=""
					placeholder="<fmt:message key="label.message" />"
					onchange="this.setAttribute('value', this.value);"
					oninvalid="this.setCustomValidity('<fmt:message
						key="text.requiredField" />')"
					oninput="setCustomValidity('')"></textarea>
			</fieldset>
			<fieldset>
				<button name="submit" type="submit">
					<fmt:message key="button.submit" />
				</button>
			</fieldset>
			<div class="centerText">
				<c:if test="${not empty ERROR_MESSAGE}">
					<h4 id="error">
						<fmt:message key="${ERROR_MESSAGE}" />
					</h4>
				</c:if>
				<c:if test="${not empty RESULT_MESSAGE}">
					<h4 id="result">
						<fmt:message key="${RESULT_MESSAGE}" />
					</h4>
				</c:if>
			</div>
		</form>
	</div>

	<div id="footer">
		<div>
			<ctg:copyright-tag />
		</div>
	</div>

</body>
</html>

