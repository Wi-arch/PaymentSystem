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
					value="SHOW_USER_CURRENCIES_MENU">
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
		<li>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND"
					value="SHOW_USER_BANK_ACCOUNTS_MENU">
				<button type="submit" class="active">
					<fmt:message key="button.myAccounts" />
				</button>
			</form>
		</li>
		<li>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND" value="SHOW_USER_CARDS_MENU">
				<button type="submit">
					<fmt:message key="button.myCards" />
				</button>
			</form>
		</li>
	</ul>


	<div class="userBankAccountsBox">

		<c:if test="${empty BANK_ACCOUNT_LIST}">
			<h2>
				<fmt:message key="label.label.accountsNotFound" />
			</h2>
		</c:if>

		<c:if test="${not empty BANK_ACCOUNT_LIST}">
			<h2>
				<fmt:message key="label.myAccounts" />
			</h2>
			<table>
				<thead>
					<tr>
						<th scope="col"><fmt:message key="label.accountNumber" /></th>
						<th scope="col"><fmt:message key="label.balance" /></th>
						<th scope="col"><fmt:message key="label.currency" /></th>
						<th scope="col"><fmt:message key="label.status" /></th>
						<th scope="col"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${BANK_ACCOUNT_LIST}" var="value">
						<tr>
							<td width="35%"><c:out value="${value.accountNumber}" /></td>
							<td width="20%"><c:out value="${value.balance}" /></td>
							<td width="17%"><c:out value="${value.currency.name}" /></td>
							<c:if test="${value.isBlocked }">
								<td width="18%" id="error"><fmt:message key="label.blocked" /></td>
							</c:if>
							<c:if test="${not value.isBlocked }">
								<td width="18%" id="success"><fmt:message
										key="label.active" /></td>
							</c:if>
							<td width="10%">
								<form action="${pageContext.request.contextPath}/controller"
									method="post">
									<input type="hidden" name="COMMAND"
										value="SHOW_USER_ACCOUNT_TRANSACTIONS_TABLE"> <input
										type="hidden" name="BANK_ACCOUNT_NUMBER"
										value="${value.accountNumber}">
									<button type="submit">
										<fmt:message key="button.statement" />
									</button>
								</form>
							</td>
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
	<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>

