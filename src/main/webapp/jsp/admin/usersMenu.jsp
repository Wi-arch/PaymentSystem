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
				<input type="hidden" name="COMMAND"
					value="SHOW_ADMIN_CURRENCIES_MENU">
				<button type="submit">
					<fmt:message key="button.currencyRates" />
				</button>
			</form>
		</li>

		<li>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND" value="SHOW_ADMIN_USERS_MENU">
				<button type="submit" class="active">
					<fmt:message key="button.users" />
				</button>
			</form>
		</li>

		<li>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND" value="SHOW_ADMIN_REQUESTS_MENU">
				<button type="submit">
					<fmt:message key="button.requests" />
				</button>
			</form>
		</li>

		<li>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND"
					value="SHOW_ADMIN_BANK_ACCOUNTS_MENU">
				<button type="submit">
					<fmt:message key="button.accounts" />
				</button>
			</form>
		</li>
	</ul>

	<div class="usersBox">

		<h2>
			<fmt:message key="label.listOfUsers" />
		</h2>
		<h4>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND" value="FIND_USER_BY_LOGIN">

				<fmt:message key="label.findUser" />
				<input type="text" name="USER_LOGIN" placeholder="Login" required
					title="" value=""
					onchange="this.setAttribute('value', this.value);"
					oninvalid="this.setCustomValidity('<fmt:message
						key="text.requiredField" />')"
					oninput="setCustomValidity('')" />
				<button type="submit">
					<i class="fa fa-search"></i>
				</button>
			</form>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND" value="SHOW_ALL_USERS">
				<button type="submit">
					<fmt:message key="button.findAllUsers" />
				</button>
			</form>
		</h4>
		<c:if test="${not empty USER_BY_LOGIN}">
			<table>
				<thead>
					<tr>
						<th scope="col"><fmt:message key="label.login" /></th>
						<th scope="col"><fmt:message key="label.email" /></th>
						<th scope="col"><fmt:message key="label.name" /></th>
						<th scope="col"><fmt:message key="label.surname" /></th>
						<th scope="col"></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td width="22%"><c:out value="${USER_BY_LOGIN.login}" /></td>
						<td width="22%"><c:out value="${USER_BY_LOGIN.email}" /></td>
						<td width="23%"><c:if test="${not empty USER_BY_LOGIN.name}">
								<c:out value="${USER_BY_LOGIN.name}" />
							</c:if> <c:if test="${empty USER_BY_LOGIN.name}">
								<c:out value="-" />
							</c:if></td>

						<td width="23%"><c:if
								test="${not empty USER_BY_LOGIN.surname}">
								<c:out value="${USER_BY_LOGIN.surname}" />
							</c:if> <c:if test="${empty USER_BY_LOGIN.surname}">
								<c:out value="-" />
							</c:if></td>

						<td width="10%"><c:if test="${not  USER_BY_LOGIN.isBlocked}">

								<form action="${pageContext.request.contextPath}/controller"
									method="post">
									<input type="hidden" name="COMMAND"
										value="ADMIN_DELETE_USER_ACCOUNT"> <input
										type="hidden" name="USER_LOGIN" value="${USER_BY_LOGIN.login}">
									<button type="submit" id="blockButton">
										<fmt:message key="button.block" />
									</button>
								</form>
							</c:if> <c:if test="${USER_BY_LOGIN.isBlocked}">
								<form action="${pageContext.request.contextPath}/controller"
									method="post">
									<input type="hidden" name="COMMAND" value="UNLOCK_USER_ACCOUNT">
									<input type="hidden" name="USER_LOGIN"
										value="${USER_BY_LOGIN.login}">
									<button type="submit" id="unblockButton">
										<fmt:message key="button.unlock" />
									</button>
								</form>
							</c:if></td>
					</tr>
				</tbody>
			</table>
		</c:if>

		<c:if test="${not empty USER_LIST}">

			<table>
				<thead>
					<tr>
						<th scope="col"><fmt:message key="label.login" /></th>
						<th scope="col"><fmt:message key="label.email" /></th>
						<th scope="col"><fmt:message key="label.name" /></th>
						<th scope="col"><fmt:message key="label.surname" /></th>
						<th scope="col"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${USER_LIST}" var="value">
						<tr>
							<td width="22%"><c:out value="${value.login}" /></td>
							<td width="22%"><c:out value="${value.email}" /></td>
							<td width="23%"><c:if test="${not empty value.name}">
									<c:out value="${value.name}" />
								</c:if> <c:if test="${empty value.name}">
									<c:out value="-" />
								</c:if></td>

							<td width="23%"><c:if test="${not empty value.surname}">
									<c:out value="${value.surname}" />
								</c:if> <c:if test="${empty value.surname}">
									<c:out value="-" />
								</c:if></td>

							<td width="10%"><c:if test="${not value.isBlocked}">

									<form action="${pageContext.request.contextPath}/controller"
										method="post">
										<input type="hidden" name="COMMAND"
											value="ADMIN_DELETE_USER_ACCOUNT"> <input
											type="hidden" name="USER_LOGIN" value="${value.login}">
										<button type="submit" id="blockButton">
											<fmt:message key="button.block" />
										</button>
									</form>

								</c:if> <c:if test="${value.isBlocked}">

									<form action="${pageContext.request.contextPath}/controller"
										method="post">
										<input type="hidden" name="COMMAND"
											value="UNLOCK_USER_ACCOUNT"> <input type="hidden"
											name="USER_LOGIN" value="${value.login}">
										<button type="submit" id="unblockButton">
											<fmt:message key="button.unlock" />
										</button>
									</form>

								</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:if test="${not empty ERROR_MESSAGE}">
			<h2 id="error">
				<fmt:message key="${ERROR_MESSAGE}" />
			</h2>
		</c:if>
		<c:if test="${not empty RESULT_MESSAGE}">
			<h2 id="result">
				<fmt:message key="${RESULT_MESSAGE}" />
			</h2>
		</c:if>
	</div>

	<div id="footer">
		<div>
			<ctg:copyright-tag />
		</div>
	</div>
</body>
</html>

