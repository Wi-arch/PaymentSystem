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
		<li>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND"
					value="SHOW_USER_BANK_ACCOUNTS_MENU">
				<button type="submit">
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

	<div class="userCreateRequestBox" id="userCreateRequestBox">
		<h3>
			<fmt:message key="label.chooseRequestType" />
		</h3>
		<br>
		<button onclick="openFormNewAccount()">
			<fmt:message key="button.openNewAccount" />
		</button>
		<br> <br>
		<c:if test="${not empty BANK_ACCOUNT_LIST}">
			<button onclick="openFormNewCardExistingAccount()">
				<fmt:message key="button.openNewCardToExistingAccount" />
			</button>
			<br>
			<br>
		</c:if>
		<button onclick="openFormNewCard()">
			<fmt:message key="button.openNewCard" />
		</button>
		<br> <br>
		<c:if test="${not empty CARD_LIST}">
			<button onclick="openFormCardUnlockRequest()">
				<fmt:message key="button.requestToUnlockCard" />
			</button>
			<br>
			<br>
		</c:if>
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
	<div class="newAccountRequestForm" id="openFormNewAccount">
		<form action="${pageContext.request.contextPath}/controller"
			class="form-container" method="post">
			<input type="hidden" name="COMMAND"
				value="CREATE_REQUEST_OPEN_NEW_ACCOUNT">
			<h3>
				<fmt:message key="label.openNewAccount" />
			</h3>
			<h5>
				<fmt:message key="label.chooseCurrency" />
			</h5>
			<br> <label class="container">BYN <input type="radio"
				id="checkedButton" checked="checked" name="CURRENCY" value="BYN">
				<span class="checkmark"></span>
			</label> <label class="container">USD <input type="radio"
				name="CURRENCY" value="USD"> <span class="checkmark"></span>
			</label> <label class="container">EUR <input type="radio"
				name="CURRENCY" value="EUR"> <span class="checkmark"></span>
			</label> <label class="container">RUB <input type="radio"
				name="CURRENCY" value="RUB"> <span class="checkmark"></span>
			</label><br> <br>
			<button type="submit" class="btn">
				<fmt:message key="button.submitRequest" />
			</button>
			<button type="button" class="btn cancel"
				onclick="closeFormNewAccount() ">
				<fmt:message key="button.cancel" />
			</button>
		</form>
	</div>
	<div class="newCardExistingAccountRequestForm"
		id="openFormNewCardExistingAccount">
		<form action="${pageContext.request.contextPath}/controller"
			class="form-container" method="post">
			<input type="hidden" name="COMMAND"
				value="CREATE_REQUEST_OPEN_NEW_CARD_TO_EXISTING_ACCOUNT">
			<h3>
				<fmt:message key="label.openNewCardToExistingAccount" />
			</h3>
			<br>
			<h5>
				<fmt:message key="label.chooseBankAccount" />
				<select name="BANK_ACCOUNT_NUMBER">
					<c:forEach items="${BANK_ACCOUNT_LIST}" var="value">
						<option value="${value.accountNumber}">
							<c:out value="${value.accountNumber}" /></option>
					</c:forEach>
				</select>
			</h5>
			<br>
			<h5>
				<fmt:message key="label.chooseCardExpiryDate" />
				<select name="CARD_EXPIRATION_DATE">
					<option value="1">1</option>
					<option value="3">3</option>
					<option selected value="5">5</option>
				</select>
			</h5>
			<br>
			<h5>
				<fmt:message key="label.choosePaymentSystem" />
				<select name="PAYMENT_SYSTEM">
					<option id="checkedButton" selected value="Visa">Visa</option>
					<option value="MasterCard">MasterCard</option>
					<option value="БелКарт">БелКарт</option>
					<option value="UnionPay">UnionPay</option>
					<option value="American Express">AmericanExpress</option>
				</select>
			</h5>
			<br>
			<button type="submit" class="btn">
				<fmt:message key="button.submitRequest" />
			</button>
			<button type="button" class="btn cancel"
				onclick="closeFormNewCardExistingAccount() ">
				<fmt:message key="button.cancel" />
			</button>
		</form>
	</div>
	<div class="newCardRequestForm" id="openFormNewCard">
		<form action="${pageContext.request.contextPath}/controller"
			class="form-container" method="post">
			<input type="hidden" name="COMMAND"
				value="CREATE_REQUEST_OPEN_NEW_CARD">
			<h3>
				<fmt:message key="label.openNewCard" />
			</h3>
			<br>
			<h5>
				<fmt:message key="label.chooseCurrency" />
				<select name="CURRENCY">
					<option selected value="BYN">BYN</option>
					<option value="USD">USD</option>
					<option value="EUR">EUR</option>
					<option value="RUB">RUB</option>
				</select>
			</h5>
			<br>
			<h5>
				<fmt:message key="label.chooseCardExpiryDate" />
				<select name="CARD_EXPIRATION_DATE">
					<option value="1">1</option>
					<option value="3">3</option>
					<option selected value="5">5</option>
				</select>
			</h5>
			<br>
			<h5>
				<fmt:message key="label.choosePaymentSystem" />
				<select name="PAYMENT_SYSTEM">
					<option selected value="Visa">Visa</option>
					<option value="MasterCard">MasterCard</option>
					<option value="БелКарт">БелКарт</option>
					<option value="UnionPay">UnionPay</option>
					<option value="American Express">AmericanExpress</option>
				</select>
			</h5>
			<br> <br>
			<button type="submit" class="btn">
				<fmt:message key="button.submitRequest" />
			</button>
			<button type="button" class="btn cancel"
				onclick="closeFormNewCard() ">
				<fmt:message key="button.cancel" />
			</button>
		</form>
	</div>
	<div class="unlockCardRequestForm" id="unlockCardRequestForm">
		<form action="${pageContext.request.contextPath}/controller"
			class="form-container" method="post">
			<input type="hidden" name="COMMAND"
				value="CREATE_REQUEST_UNLOCK_CARD">
			<h3>
				<fmt:message key="label.requestToUnlockCard" />
			</h3>
			<br>
			<h5>
				<fmt:message key="label.chooseCardToUnlock" />
				<select name="CARD_NUMBER">
					<c:forEach items="${CARD_LIST}" var="value">
						<option value="${value.number}">
							<c:out value="${value.numberMask}" /></option>
					</c:forEach>
				</select>
			</h5>
			<br>
			<button type="submit" class="btn">
				<fmt:message key="button.submitRequest" />
			</button>
		</form>
		<form action="${pageContext.request.contextPath}/controller"
			class="form-container" method="post">
			<input type="hidden" name="COMMAND"
				value="SHOW_MENU_FOR_CREATING_REQUESTS">
			<button type="submit" class="btn cancel">
				<fmt:message key="button.cancel" />
			</button>
		</form>
	</div>

	<div id="footer">
		<div>
			<ctg:copyright-tag />
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>

