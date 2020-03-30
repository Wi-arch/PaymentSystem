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
<title><fmt:message key="title.onlineBanking" /></title>
</head>

<body class="background">

	<ul>
		<li>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND" value="LOGOUT">
				<button type="submit">
					<fmt:message key="button.logout" />
				</button>
			</form>
		</li>

		<li>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND" value="SHOW_CURRENCIES">
				<button type="submit">
					<fmt:message key="button.currencyRates" />
				</button>
			</form>
		</li>

		<li>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND" value="SHOW_USER_DATA">
				<button type="submit" class="active">
					<fmt:message key="button.personalData" />
				</button>
			</form>
		</li>

		<li><a href="#"><fmt:message key="button.myRequests" /></a>
			<ul>
				<li>
					<form action="${pageContext.request.contextPath}/controller"
						method="post">
						<input type="hidden" name="COMMAND"
							value="SHOW_MENU_FOR_CREATING_REQUESTS">
						<button type="submit" id="childButton">
							<fmt:message key="button.addNewRequest" />
						</button>
					</form>
				</li>
				<li>
					<form action="${pageContext.request.contextPath}/controller"
						method="post">
						<input type="hidden" name="COMMAND" value="SHOW_ALL_USER_REQUESTS">
						<button type="submit" id="childButton">
							<fmt:message key="button.showAllRequests" />
						</button>
					</form>
				</li>
			</ul></li>

		<li><a href="#"><fmt:message key="button.myAccounts" /></a></li>
		<li><a href="#"><fmt:message key="button.myCards" /></a></li>
	</ul>


	<div class="userDataBox">
		<h3>
			<fmt:message key="label.personalData" />
		</h3>
		<h4>
			<fmt:message key="label.loginWithColon" />
			${USER.login}
		</h4>
		<h4>
			<fmt:message key="label.emailWithColon" />
			${USER.email}
		</h4>
		<form action="${pageContext.request.contextPath}/controller"
			method="post">
			<input type="hidden" name="COMMAND" value="UPDATE_USER_NAME">
			<h4>
				<fmt:message key="label.nameWithColon" />
				<input type="text" name="USER_NAME" value="${USER.name}" size="15">
				<button type="submit">
					<fmt:message key="button.changeName" />
				</button>
			</h4>
		</form>
		<form action="${pageContext.request.contextPath}/controller"
			method="post">
			<input type="hidden" name="COMMAND" value="UPDATE_USER_SURNAME">
			<h4>
				<fmt:message key="label.surnameWithColon" />
				<input type="text" name="USER_SURNAME" value="${USER.surname}"
					size="15">
				<button type="submit">
					<fmt:message key="button.changeSurname" />
				</button>
			</h4>
		</form>

		<h4>
			<button onclick="openForm()">
				<fmt:message key="button.changePassword" />
			</button>
		</h4>


		<form action="${pageContext.request.contextPath}/controller"
			method="post">
			<input type="hidden" name="COMMAND" value="DELETE_USER_ACCOUNT">
			<br>
			<h4>
				<button type="submit"
					onclick="return confirm('<fmt:message key="text.accountDeletionConfirmation" />');">
					<fmt:message key="button.deleteAccount" />
				</button>
			</h4>
			<br>
		</form>

		<div class="form-popup" id="inputPasswordForm">
			<form action="${pageContext.request.contextPath}/controller"
				class="form-container" method="post">
				<input type="hidden" name="COMMAND" value="UPDATE_USER_PASSWORD">
				<h1>
					<fmt:message key="label.passwordUpdate" />
				</h1>
				<label for="OLD_PASSWORD"><b><fmt:message
							key="label.oldPassword" /></b></label> <input type="password"
					placeholder="<fmt:message key="label.oldPassword" />"
					name="OLD_PASSWORD" title="" required value="" id="oldPassword"
					onchange="this.setAttribute('value', this.value);"
					oninvalid="this.setCustomValidity('<fmt:message
						key="text.requiredField" />')"
					oninput="setCustomValidity('')"> <label for="NEW_PASSWORD"><b><fmt:message
							key="label.newPassword" /></b></label> <input type="password"
					placeholder="<fmt:message key="label.newPassword" />"
					name="NEW_PASSWORD" required
					pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*([^\s\w]|[_]))[\S]{6,}$"
					value="" id="newPassword"
					title="<fmt:message key="text.newPasswordInfo" />"
					oninvalid="this.setCustomValidity('<fmt:message key="*Status1006*" />')"
					onchange="this.setAttribute('value', this.value); this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message key="*Status1006*" />' : '');
			if(this.checkValidity()) form.confirmNewPassword.pattern = this.value;" />

				<label for="CONFIRM_NEW_PASSWORD"><b><fmt:message
							key="label.confirmNewPassword" /></b></label> <input type="password"
					placeholder="<fmt:message key="label.confirmNewPassword" />"
					name="CONFIRM_NEW_PASSWORD" required
					pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*([^\s\w]|[_]))[\S]{6,}$"
					value="" id="confirmNewPassword"
					title="<fmt:message key="text.newPasswordInfo" />"
					oninvalid="this.setCustomValidity('<fmt:message key="*Status1002*" />')"
					onchange="this.setAttribute('value', this.value); this.setCustomValidity(this.validity.patternMismatch ? '<fmt:message key="*Status1002*" />' : '');" />
				<button type="submit" class="btn">
					<fmt:message key="button.changePassword" />
				</button>
				<button type="button" class="btn cancel" onclick="closeForm()">
					<fmt:message key="button.cancel" />
				</button>
			</form>
		</div>


		<c:if test="${not empty ERROR_MESSAGE}">
			<h3 id="error">
				<fmt:message key="${ERROR_MESSAGE}" />
			</h3>
		</c:if>
		<c:if test="${not empty RESULT_MESSAGE}">
			<h3 id="result">
				<fmt:message key="${RESULT_MESSAGE}" />
			</h3>
		</c:if>

	</div>


	<div id="footer">
		<div>
			<ctg:copyright-tag />
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>

