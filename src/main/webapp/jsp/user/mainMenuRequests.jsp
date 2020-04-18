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

	<div class="requestsBox">
		<c:if test="${not empty USER_REQUEST_LIST}">
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND"
					value="SHOW_MENU_FOR_CREATING_REQUESTS">
				<h4>
					<fmt:message key="label.requests" />
					<button type="submit">
						<fmt:message key="button.addNewRequest" />
					</button>
				</h4>
			</form>
			<table>
				<thead>
					<tr>
						<th scope="col"><fmt:message key="label.creatingDate" /></th>
						<th scope="col"><fmt:message key="label.processingDate" /></th>
						<th scope="col"><fmt:message key="label.description" /></th>
						<th scope="col"><fmt:message key="label.status" /></th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${USER_REQUEST_LIST}" var="value">
						<tr>
							<td><fmt:formatDate value="${value.creationDate}"
									pattern="dd-MM-yyyy HH:mm" /></td>
							<td><c:if test="${not empty value.handlingDate}">
									<fmt:formatDate value="${value.handlingDate}"
										pattern="dd-MM-yyyy HH:mm" />
								</c:if> <c:if test="${empty value.handlingDate}">
									<c:out value="-" />
								</c:if></td>
							<td><c:if test="${value.requestType.value=='Unlock card'}">
									<fmt:message key="label.cardUnlockRequest" />
								</c:if> <c:if test="${value.requestType.value=='Account opening'}">
									<fmt:message key="label.accountOpeningRequest" />
								</c:if> <c:if
									test="${value.requestType.value=='Opening a card to an existing account'}">
									<fmt:message key="label.cardIssueToExistingAccount" />
								</c:if> <c:if
									test="${value.requestType.value=='Card opening with opening a new account'}">
									<fmt:message key="label.cardIssueWithOpeningNewAccount" />
								</c:if></td>
							<c:if test="${value.requestStatus.value=='In processing'}">
								<td id="default"><fmt:message key="label.inProcessing" /></td>
							</c:if>
							<c:if test="${value.requestStatus.value=='Processed'}">
								<td id="success"><fmt:message key="label.completed" /></td>
							</c:if>
							<c:if test="${value.requestStatus.value=='Rejected'}">
								<td id="error"><fmt:message key="label.rejected" /></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<c:if test="${empty USER_REQUEST_LIST}">
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND"
					value="SHOW_MENU_FOR_CREATING_REQUESTS">
				<h4>
					<fmt:message key="label.noRequests" />
					<button type="submit">
						<fmt:message key="button.addNewRequest" />
					</button>
				</h4>
			</form>
		</c:if>
		<c:if test="${not empty ERROR_MESSAGE}">
			<h3 id="error">
				<fmt:message key="${ERROR_MESSAGE}" />
			</h3>
		</c:if>
	</div>

	<div id="footer">
		<div>
			<ctg:copyright-tag />
		</div>
	</div>
</body>
</html>

