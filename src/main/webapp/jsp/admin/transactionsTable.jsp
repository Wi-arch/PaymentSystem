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
<link rel="stylesheet" href="css/styles.css">
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
				<button type="submit">
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

	<div class="transactionsBox">
		<c:if test="${not empty TRANSACTION_LIST}">
			<h4>
				<fmt:message key="label.statementFromAccount" />
				${param.BANK_ACCOUNT_NUMBER}
			</h4>
			<table>
				<thead>
					<tr>
						<th scope="col"><fmt:message key="label.operationDate" /></th>
						<th scope="col"><fmt:message key="label.description" /></th>
						<th scope="col"><fmt:message key="label.operationType" /></th>
						<th scope="col"><fmt:message key="label.amount" /></th>
						<th scope="col"><fmt:message key="label.currency" /></th>
						<th scope="col"><fmt:message key="label.status" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${TRANSACTION_LIST}" var="value">
						<tr>
							<td width="16%"><fmt:formatDate value="${value.date}"
									pattern="dd-MM-yyyy HH:mm" /></td>
							<td width="25%"><c:out value="${value.paymentPurpose}" /></td>
							<td width="22%"><c:if test="${value.isWriteOff }">
									<fmt:message key="label.writeOff" />
								</c:if> <c:if test="${not value.isWriteOff }">
									<fmt:message key="label.crediting" />
								</c:if></td>
							<td width="11%"><c:out value="${value.amount}" /></td>
							<td width="11%"><c:out value="${value.currency.name}" /></td>
							<td width="15%"><c:if test="${value.isCompleted }">
									<fmt:message key="label.completed" />
								</c:if> <c:if test="${not value.isCompleted }">
									<fmt:message key="label.notCompleted" />
								</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:if test="${empty TRANSACTION_LIST}">
			<h4>
				<fmt:message key="label.noAccountTransactions" />
			</h4>
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
