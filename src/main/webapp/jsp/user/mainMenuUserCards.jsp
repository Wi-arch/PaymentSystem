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
				<button type="submit">
					<fmt:message key="button.myAccounts" />
				</button>
			</form>
		</li>
		<li>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND" value="SHOW_USER_CARDS_MENU">
				<button type="submit" class="active">
					<fmt:message key="button.myCards" />
				</button>
			</form>
		</li>
	</ul>

	<div class="userCardsBox" id="userCardsBox">
		<c:if test="${empty CARD_LIST}">
			<h2>
				<fmt:message key="label.cardsNotFound" />
			</h2>
		</c:if>
		<c:if test="${not empty CARD_LIST}">
			<h2>
				<fmt:message key="label.myCards" />
			</h2>
			<table>
				<thead>
					<tr>
						<th scope="col"><fmt:message key="label.cardNumber" /></th>
						<th scope="col"><fmt:message key="label.balance" /></th>
						<th scope="col"><fmt:message key="label.currency" /></th>
						<th scope="col"><fmt:message key="label.paymentSystem" /></th>
						<th scope="col"><fmt:message key="label.expiryDate" /></th>
						<th scope="col"><fmt:message key="label.status" /></th>
						<th scope="col"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${CARD_LIST}" var="value">
						<tr>
							<td width="19%"><c:out value="${value.numberMask}" /></td>
							<td width="15%"><c:out value="${value.bankAccount.balance}" /></td>
							<td width="13%"><c:out
									value="${value.bankAccount.currency.name}" /></td>
							<td width="14%"><c:out value="${value.paymentSystem.name}" /></td>
							<td width="13%"><fmt:formatDate
									value="${value.validUntilDate}" pattern="dd-MM-yyyy" /></td>
							<c:if test="${value.isBlocked }">
								<td width="14%" id="error"><fmt:message key="label.blocked" /></td>
								<td width="12%">
									<button disabled>
										<fmt:message key="button.operations" />
									</button>
								</td>
							</c:if>
							<c:if test="${not value.isBlocked }">
								<td width="14%" id="success"><fmt:message
										key="label.active" /></td>
								<td width="12%">
									<button
										onclick="openCardOperationsForm('${value.number}', '${value.numberMask}', '<fmt:formatDate
									value="${value.openingDate}" pattern="dd-MM-yyyy" />', '<fmt:formatDate
									value="${value.validUntilDate}" pattern="dd-MM-yyyy" />', '${value.bankAccount.accountNumber}')">
										<fmt:message key="button.operations" />
									</button>
								</td>
							</c:if>
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

	<div class="cardOperationsForm" id="cardOperationsForm">
		<form action="${pageContext.request.contextPath}/controller"
			class="form-container" method="post">
			<input type="hidden" id="operationCommand" name="COMMAND" value="">
			<input type="hidden" id="cardNumber" name="CARD_NUMBER" value="">
			<div class="centerText">
				<h3>
					<fmt:message key="label.cardOperations" />
					<input type="text" id="cardNumberMask" value="" size="13" readonly
						name="CARD_NUMBER_MASK">
				</h3>
			</div>
			<button type="button" class="btn" onclick="openCardInfoForm()">
				<fmt:message key="label.cardInformation" />
			</button>

			<button type="submit" class="btn"
				onclick="setOperationCommand('SHOW_USER_CARD_TRANSACTIONS_TABLE')">
				<fmt:message key="button.statement" />
			</button>
			<button type="button" class="btn" onclick=" openCardWriteOffForm()">
				<fmt:message key="button.makePayment" />
			</button>
			<button type="button" class="btn" onclick="openCardDepositForm()">
				<fmt:message key="button.rechargeCard" />
			</button>
			<button type="button" class="btn" onclick="openCardTransferForm()">
				<fmt:message key="button.transferToCard" />
			</button>
			<button type="submit" class="btn"
				onclick="setOperationCommand('LOCK_CARD'); return confirm('<fmt:message key="text.cardLockConfirmation" />');">
				<fmt:message key="button.block" />
			</button>
			<button type="submit" class="btn"
				onclick="setOperationCommand('SHOW_USER_CARDS_MENU')">
				<fmt:message key="button.cancel" />
			</button>
		</form>
	</div>

	<div class="cardInfoForm" id="cardInfoForm">
		<div class="centerText">
			<h3>
				<fmt:message key="label.cardInformation" />
				<input type="text" id="cardNumberMaskInfo" value="" size="13"
					readonly>
			</h3>
		</div>
		<br>
		<h4>
			<fmt:message key="label.creatingDate" />
			:<input type="text" id="cardCreationDate" value="" readonly size="7">
		</h4>
		<br>
		<h4>
			<fmt:message key="label.expiryDate" />
			:<input type="text" id="validUntilDate" value="" readonly size="7">
		</h4>
		<br>
		<h4>
			<fmt:message key="label.accountNumber" />
			:<input type="text" id="bankAccountNumber" value="" readonly
				size="26">
		</h4>
		<form action="${pageContext.request.contextPath}/controller"
			class="form-container" method="post">
			<input type="hidden" id="infoCommand" name="COMMAND" value="">
			<button type="submit" class="btn"
				onclick="setInfoCommand('SHOW_USER_CARDS_MENU')">
				<fmt:message key="button.cancel" />
			</button>
		</form>
	</div>

	<div class="cardDepositForm" id="cardDepositForm">
		<div class="centerText">
			<h3>
				<fmt:message key="button.rechargeCard" />
				<input type="text" id="cardNumberMaskDeposit" value="" size="13"
					readonly>
			</h3>
		</div>
		<form action="${pageContext.request.contextPath}/controller"
			class="form-container" method="post">
			<input type="hidden" name="COMMAND" value="RECHARGE_CARD"> <input
				type="hidden" id="depositCardNumber" name="CARD_NUMBER" value="">
			<h4>
				<fmt:message key="label.chooseCurrency" />
				<select name="CURRENCY">
					<option selected value="BYN">BYN</option>
					<option value="USD">USD</option>
					<option value="EUR">EUR</option>
					<option value="RUB">RUB</option>
				</select>
			</h4>
			<br>
			<h4>
				<fmt:message key="label.amount" />
				<input type="text" id="inputCheckForm" value="" size="9"
					pattern="\d+([.]\d{0,2})?" required name="AMOUNT" title=""
					placeholder="0.00"
					oninvalid="this.setCustomValidity('text.enterValidAmount')"
					oninput="setCustomValidity('')">
			</h4>
			<h4>
				<fmt:message key="label.CVCCode" />
				<input type="password" id="inputCheckForm" value="" size="2"
					required maxlength=3 pattern="\d{3}" name="CVC_CODE" title=""
					placeholder="cvv/cvc"
					oninvalid="this.setCustomValidity('<fmt:message key="text.enterValidCVCCode" />')"
					oninput="setCustomValidity('')">
			</h4>
			<h4>
				<fmt:message key="label.paymentPurpose" />
				<textarea rows="5" cols="29" name="PAYMENT_PURPOSE" required
					title="" onchange="this.setAttribute('value', this.value);"
					oninvalid="this.setCustomValidity('<fmt:message
						key="text.requiredField" />')"
					oninput="setCustomValidity('')"></textarea>
			</h4>
			<button type="submit" class="btn">
				<fmt:message key="button.confirmPayment" />
			</button>
		</form>
		<form action="${pageContext.request.contextPath}/controller"
			class="form-container" method="post">
			<input type="hidden" name="COMMAND" value="SHOW_USER_CARDS_MENU">
			<button type="submit" class="btn cancel">
				<fmt:message key="button.cancel" />
			</button>
		</form>
	</div>

	<div class="cardWriteOffForm" id="cardWriteOffForm">
		<div class="centerText">
			<h3>
				<fmt:message key="label.cardPayment" />
				<input type="text" id="cardNumberMaskWriteOff" value="" size="13"
					readonly>
			</h3>
		</div>
		<form action="${pageContext.request.contextPath}/controller"
			class="form-container" method="post">
			<input type="hidden" name="COMMAND" value="MAKE_CARD_PAYMENT">
			<input type="hidden" id="writeOffCardNumber" name="CARD_NUMBER"
				value="">

			<h4>
				<fmt:message key="label.chooseCurrency" />
				<select name="CURRENCY">
					<option selected value="BYN">BYN</option>
					<option value="USD">USD</option>
					<option value="EUR">EUR</option>
					<option value="RUB">RUB</option>
				</select>
			</h4>
			<br>
			<h4>
				<fmt:message key="label.amount" />
				<input type="text" id="inputCheckForm" value="" size="9"
					pattern="\d+([.]\d{0,2})?" required name="AMOUNT" title=""
					placeholder="0.00"
					oninvalid="this.setCustomValidity('text.enterValidAmount')"
					oninput="setCustomValidity('')">
			</h4>
			<h4>
				<fmt:message key="label.CVCCode" />
				<input type="password" id="inputCheckForm" value="" size="2"
					required maxlength=3 pattern="\d{3}" name="CVC_CODE" title=""
					placeholder="cvv/cvc"
					oninvalid="this.setCustomValidity('<fmt:message key="text.enterValidCVCCode" />')"
					oninput="setCustomValidity('')">
			</h4>
			<h4>
				<fmt:message key="label.paymentPurpose" />
				<textarea rows="5" cols="29" name="PAYMENT_PURPOSE" required
					title="" onchange="this.setAttribute('value', this.value);"
					oninvalid="this.setCustomValidity('<fmt:message
						key="text.requiredField" />')"
					oninput="setCustomValidity('')"></textarea>
			</h4>
			<button type="submit" class="btn">
				<fmt:message key="button.confirmPayment" />
			</button>
		</form>
		<form action="${pageContext.request.contextPath}/controller"
			class="form-container" method="post">
			<input type="hidden" name="COMMAND" value="SHOW_USER_CARDS_MENU">
			<button type="submit" class="btn cancel">
				<fmt:message key="button.cancel" />
			</button>
		</form>
	</div>

	<div class="cardTransferForm" id="cardTransferForm">
		<div class="centerText">
			<h3>
				<fmt:message key="label.transferFromCard" />
				<input type="text" id="cardNumberMaskTransfer" value="" size="13"
					readonly>
			</h3>
		</div>
		<form action="${pageContext.request.contextPath}/controller"
			class="form-container" method="post">
			<input type="hidden" name="COMMAND"
				value="TRANSFER_FROM_CARD_TO_CARD"> <input type="hidden"
				id="senderCardNumber" name="SENDER_CARD_NUMBER" value="">
			<h4>
				<fmt:message key="label.receiverCardNumber" />
				<input type="text" id="receiverCardNumber" maxlength=16
					pattern="\d{16}" required title="" value="" size="14"
					name="RECEIVER_CARD_NUMBER" oninput="setCustomValidity('')"
					oninvalid="this.setCustomValidity('<fmt:message key="text.enterValidReceiverCardNumber" />')">
			</h4>
			<h4>
				<fmt:message key="label.chooseCurrency" />
				<select name="CURRENCY">
					<option selected value="BYN">BYN</option>
					<option value="USD">USD</option>
					<option value="EUR">EUR</option>
					<option value="RUB">RUB</option>
				</select>
			</h4>
			<br>
			<h4>
				<fmt:message key="label.amount" />
				<input type="text" id="inputCheckForm" value="" size="9"
					pattern="\d+([.]\d{0,2})?" required name="AMOUNT" title=""
					placeholder="0.00"
					oninvalid="this.setCustomValidity('text.enterValidAmount')"
					oninput="setCustomValidity('')">
			</h4>
			<h4>
				<fmt:message key="label.CVCCode" />
				<input type="password" id="inputCheckForm" value="" size="1"
					required maxlength=3 pattern="\d{3}" name="CVC_CODE" title=""
					placeholder="cvv/cvc"
					oninvalid="this.setCustomValidity('<fmt:message key="text.enterValidCVCCode" />')"
					oninput="setCustomValidity('')">
			</h4>
			<h4>
				<fmt:message key="label.paymentPurpose" />
				<textarea rows="4" cols="29" name="PAYMENT_PURPOSE" required
					title="" onchange="this.setAttribute('value', this.value);"
					oninvalid="this.setCustomValidity('<fmt:message
						key="text.requiredField" />')"
					oninput="setCustomValidity('')"></textarea>
			</h4>
			<button type="submit" class="btn">
				<fmt:message key="button.confirmPayment" />
			</button>
		</form>
		<form action="${pageContext.request.contextPath}/controller"
			class="form-container" method="post">
			<input type="hidden" name="COMMAND" value="SHOW_USER_CARDS_MENU">
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

