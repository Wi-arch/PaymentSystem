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
				<button type="submit">
					<fmt:message key="button.personalData" />
				</button>
			</form>
		</li>

		<li><a href="#" class="active"><fmt:message
					key="button.myRequests" /></a>
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
	<form method="post" >
		<h3>Choose card to unlock:</h3>
		<input type="submit" id="chooseRequest" name="Change" value="Unlock card"><br><br>
		<input type="submit" id="chooseRequest" name="Change" value="Open new account"><br><br>
		<input type="submit" id="chooseRequest" name="Change" value="Open new card to existing account"><br><br>
		<input type="submit" id="chooseRequest" name="Change" value="Open new card with no account"><br><br>
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
</div>
 
 
		
</body>
</html>

