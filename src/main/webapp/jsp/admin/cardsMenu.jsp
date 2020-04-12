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

		<li>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND" value="SHOW_ADMIN_CARDS_MENU">
				<button type="submit" class="active">
					<fmt:message key="button.cards" />
				</button>
			</form>
		</li>
	</ul>

	<div class="adminCardsBox">

		<h2>
			<fmt:message key="label.userPaymentCards" />
		</h2>
		<h4>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND"
					value="ADMIN_FIND_ALL_USER_CARDS_BY_LOGIN">
				<fmt:message key="label.findUserCards" />
				<input type="text" name="USER_LOGIN" placeholder="Login" required
					title="" value="" />
				<button type="submit">
					<i class="fa fa-search"></i>
				</button>
			</form>
			<p>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND" value="ADMIN_FIND_ALL_CARDS">
				<button type="submit">
					<fmt:message key="label.findAllPaymentCards" />
				</button>
			</form>
		</h4>
		<c:if test="${not empty CARD_LIST}">
			<table>
				<thead>
					<tr>
						<th scope="col"><fmt:message key="label.login" /></th>
						<th scope="col"><fmt:message key="label.cardNumber" /></th>
						<th scope="col"><fmt:message key="label.creatingDate" /></th>
						<th scope="col"><fmt:message key="label.expiryDate" /></th>
						<th scope="col"><fmt:message key="label.balance" /></th>
						<th scope="col"><fmt:message key="label.currency" /></th>
						<th scope="col"><fmt:message key="label.paymentSystem" /></th>
						<th scope="col"><fmt:message key="label.status" /></th>
						<th scope="col"></th>
						<th scope="col"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${CARD_LIST}" var="value">
						<tr>
							<td width="11%"><c:out value="${value.user.login}" /></td>
							<td width="11%"><c:out value="${value.numberMask}" /></td>
							<td width="10%"><fmt:formatDate value="${value.openingDate}"
									pattern="dd-MM-yyyy" /></td>
							<td width="10%"><fmt:formatDate
									value="${value.validUntilDate}" pattern="dd-MM-yyyy" /></td>
							<td width="9%"><c:out value="${value.bankAccount.balance}" /></td>
							<td width="8%"><c:out
									value="${value.bankAccount.currency.name}" /></td>
							<td width="11%"><c:out value="${value.paymentSystem.name}" /></td>
							<c:if test="${value.isBlocked }">
								<td width="10%" id="error"><fmt:message key="label.blocked" /></td>
							</c:if>
							<c:if test="${not value.isBlocked }">
								<td width="10%" id="success"><fmt:message
										key="label.active" /></td>
							</c:if>
							<td width="10%"><c:if test="${not value.isBlocked}">
									<form action="${pageContext.request.contextPath}/controller"
										method="post">
										<input type="hidden" name="COMMAND" value="ADMIN_BLOCK_CARD">
										<input type="hidden" name="CARD_NUMBER"
											value="${value.number}">
										<button type="submit" id="blockButton">
											<fmt:message key="button.block" />
										</button>
									</form>
								</c:if> <c:if test="${value.isBlocked}">
									<form action="${pageContext.request.contextPath}/controller"
										method="post">
										<input type="hidden" name="COMMAND" value="ADMIN_UNBLOCK_CARD">
										<input type="hidden" name="CARD_NUMBER"
											value="${value.number}">
										<button type="submit" id="unblockButton">
											<fmt:message key="button.unlock" />
										</button>
									</form>
								</c:if></td>
							<td width="10%">
								<form action="${pageContext.request.contextPath}/controller"
									method="post">
									<input type="hidden" name="COMMAND"
										value="ADMIN_SHOW_ALL_TRANSACTIONS_BY_CARD_NUMBER"> <input
										type="hidden" name="CARD_NUMBER" value="${value.number}">
									<input type="hidden" name="CARD_NUMBER_MASK"
										value="${value.numberMask}">
									<button type="submit" id="handleButton">
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
</body>
</html>
