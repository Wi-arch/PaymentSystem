<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="ctg" uri="customtags"%>
<fmt:setLocale value="${cookie.LANGUAGE.value}" />
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

<title><fmt:message key="title.registration" /></title>
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
		<li><a href="${pageContext.request.contextPath}" class="active"><fmt:message
					key="button.home" /></a></li>
		<li><a href="#"><fmt:message key="button.news" /></a></li>
		<li><a href="#"><fmt:message key="button.contact" /></a></li>
		<li><a href="#"><fmt:message key="button.about" /></a></li>
	</ul>

	<div class="box">
		<div class="linkBar">
			<div class="flex">
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="COMMAND" value="LOGIN">
					<button type="submit" id="linkBarLoginButton">
						<fmt:message key="button.login" />
					</button>
				</form>
				<a href="${pageContext.request.contextPath}/jsp/registration.jsp"
					class="active"><fmt:message key="button.registration" /></a>
			</div>
			<br> <br>
		</div>
		<form action="${pageContext.request.contextPath}/controller"
			method="post">
			<div class="inputBox">
				<input type="text" name="USER_LOGIN" required
					pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[\S]{6,}$" value=""
					title="<fmt:message key="text.loginInfo" />"
					oninvalid="this.setCustomValidity('<fmt:message key="*Status1005*" />')"
					onchange="this.setAttribute('value', this.value); this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message key="*Status1005*" />' : '');">
				<label class="fa fa-user"><fmt:message key="label.username" /></label>
			</div>
			<div class="inputBox">
				<input type="password" name="USER_PASSWORD" required
					pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*([^\s\w]|[_]))[\S]{6,}$"
					value="" id="password"
					title="<fmt:message key="text.passwordInfo" />"
					oninvalid="this.setCustomValidity('<fmt:message key="*Status1006*" />')"
					onchange="this.setAttribute('value', this.value); this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message key="*Status1006*" />' : ''); if(this.checkValidity()) form.passwordConfirm.pattern = this.value;" /><label
					class="fa fa-key"><fmt:message key="label.password" /></label>
			</div>
			<div class="inputBox">
				<input type="password" name="USER_PASSWORD_CONFIRM" required
					pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*([^\s\w]|[_]))[\S]{6,}$"
					value="" id="passwordConfirm"
					title="<fmt:message key="text.passwordInfo" />"
					oninvalid="this.setCustomValidity('<fmt:message key="*Status1002*" />')"
					onchange="this.setAttribute('value', this.value); this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message key="*Status1002*" />' : '');" /><label
					class="fa fa-key"><fmt:message key="label.passwordConfirm" /></label>
			</div>
			<div class="inputBox">
				<input type="email" name="USER_EMAIL" required
					pattern="[a-zA-Z0-9]{1}[a-zA-Z0-9_.-]{1,29}[a-zA-Z0-9]{1}@[a-zA-Z]{2,10}\.[a-zA-Z]{2,}"
					value="" title=""
					oninvalid="this.setCustomValidity('<fmt:message key="*Status1007*" />')"
					onchange="this.setAttribute('value', this.value); this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message key="*Status1007*" />' : '');" /><label
					class="fa fa-envelope"><fmt:message key="label.email" /></label>
			</div>
			<input type="hidden" name="USER_ROLE_VALUE" value="User"> <input
				type="hidden" name="COMMAND" value="REGISTRATION">

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

			<button type="submit">
				<fmt:message key="button.registration" />
			</button>
		</form>
	</div>
	<div id="footer">
		<div>
			<ctg:copyright-tag />
		</div>
	</div>

</body>
</html>

