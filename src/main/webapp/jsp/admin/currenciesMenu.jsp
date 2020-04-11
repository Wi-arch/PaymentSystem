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
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.3.0/css/flag-icon.min.css"
	rel="stylesheet" />
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
				<button type="submit" class="active">
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

	<div class="ratesBox">
		<c:if test="${not empty CURRENCIES_LIST }">
			<h4>
				<fmt:message key="label.officialExchangeRageAt" />
				<fmt:formatDate value="${CURRENT_DATE}" pattern="dd-MM-yyyy" />
			</h4>
			<table>
				<thead>
					<tr>
						<th scope="col"><fmt:message key="label.currency" /></th>
						<th scope="col"><fmt:message key="label.scale" /></th>
						<th scope="col"><fmt:message key="label.rate" /></th>
						<th scope="col"><fmt:message key="label.updateDate" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${CURRENCIES_LIST}" var="value">
						<tr>
							<td><c:choose>
									<c:when test="${value.name == 'BYN'}">
										<i class="flag-icon flag-icon-by"></i>
										<c:out value=" ${value.name}" />
									</c:when>
									<c:when test="${value.name == 'CHF'}">
										<i class="flag-icon flag-icon-ch"></i>
										<c:out value=" ${value.name}" />
									</c:when>
									<c:when test="${value.name == 'CNY'}">
										<i class="flag-icon flag-icon-cn"></i>
										<c:out value=" ${value.name}" />
									</c:when>
									<c:when test="${value.name == 'EUR'}">
										<i class="flag-icon flag-icon-eu"></i>
										<c:out value=" ${value.name}" />
									</c:when>
									<c:when test="${value.name == 'JPY'}">
										<i class="flag-icon flag-icon-jp"></i>
										<c:out value=" ${value.name}" />
									</c:when>
									<c:when test="${value.name == 'PLN'}">
										<i class="flag-icon flag-icon-pl"></i>
										<c:out value=" ${value.name}" />
									</c:when>
									<c:when test="${value.name == 'RUB'}">
										<i class="flag-icon flag-icon-ru"></i>
										<c:out value=" ${value.name}" />
									</c:when>
									<c:when test="${value.name == 'SEK'}">
										<i class="flag-icon flag-icon-se"></i>
										<c:out value=" ${value.name}" />
									</c:when>
									<c:when test="${value.name == 'UAH'}">
										<i class="flag-icon flag-icon-ua"></i>
										<c:out value=" ${value.name}" />
									</c:when>
									<c:when test="${value.name == 'USD'}">
										<i class="flag-icon flag-icon-us"></i>
										<c:out value=" ${value.name}" />
									</c:when>
									<c:otherwise>
										<c:out value=" ${value.name}" />
									</c:otherwise>
								</c:choose></td>
							<td><c:out value="${value.scale}" /></td>
							<td><c:out value="${value.rate}" /></td>
							<td><fmt:formatDate value="${value.updateDate}"
									pattern="dd-MM-yyyy HH:mm" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:if test="${not empty ERROR_MESSAGE}">
			<h3 id="error">
				<fmt:message key="${ERROR_MESSAGE}" />
			</h3>
		</c:if>
		<c:if test="${empty ERROR_MESSAGE}">
			<c:if test="${empty CURRENCIES_LIST }">
				<h3 id="error">
					<fmt:message key="label.noAvailableCurrencies" />
				</h3>
			</c:if>
		</c:if>
		<br> <br>
		<form action="${pageContext.request.contextPath}/controller"
			method="post">
			<input type="hidden" name="COMMAND"
				value="UPDATE_CURRENCIES_FROM_NATIONAL_BANK">
			<button type="submit">
				<fmt:message key="button.updateRates" />
			</button>
		</form>

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

	<div id="footer">
		<div>
			<ctg:copyright-tag />
		</div>
	</div>

</body>
</html>

