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
				<button type="submit" class="active">
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

	<div class="adminRequetsBox">
		<h2>
			<fmt:message key="label.userRequests" />
		</h2>
		<h4>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<input type="hidden" name="COMMAND"
					value="FIND_ALL_USER_REQUESTS_BY_LOGIN">
				<fmt:message key="label.findAllUserRequests" />
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
			<p>
			<form action="${pageContext.request.contextPath}/controller"
				method="post">
				<button type="submit">
					<fmt:message key="label.findRequestsOfAllUsers" />
				</button>
				<p>
				<p>
					<input type="checkbox" class="checkbox" id="checkbox"
						name="COMMAND" value="FIND_ALL_USER_REQUESTS_IN_PROCESSING" /> <label
						for="checkbox"> <fmt:message
							key="label.showOnlyRawRequests" />
					</label> <input type="hidden" name="COMMAND" value="FIND_ALL_USER_REQUESTS">
			</form>
		</h4>
		<c:if test="${not empty USER_REQUEST_LIST}">
			<table>
				<thead>
					<tr>
						<th scope="col"><fmt:message key="label.login" /></th>
						<th scope="col"><fmt:message key="label.creatingDate" /></th>
						<th scope="col"><fmt:message key="label.processingDate" /></th>
						<th scope="col"><fmt:message key="label.description" /></th>
						<th scope="col"><fmt:message key="label.status" /></th>
						<th scope="col"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${USER_REQUEST_LIST}" var="USER_REQUEST">
						<tr>
							<td width="10%"><c:out value="${USER_REQUEST.user.login}" /></td>

							<td width="12%"><fmt:formatDate
									value="${USER_REQUEST.creationDate}" pattern="dd-MM-yyyy HH:mm" />
							</td>


							<td width="12%"><c:if
									test="${not empty USER_REQUEST.handlingDate}">
									<fmt:formatDate value="${USER_REQUEST.handlingDate}"
										pattern="dd-MM-yyyy HH:mm" />
								</c:if> <c:if test="${empty USER_REQUEST.handlingDate}">
									<c:out value="-" />
								</c:if></td>

							<td width="24%"><c:if
									test="${USER_REQUEST.requestType.value=='Unlock card'}">
									<fmt:message key="label.cardUnlockRequest" />
								</c:if> <c:if
									test="${USER_REQUEST.requestType.value=='Account opening'}">
									<fmt:message key="label.accountOpeningRequest" />
								</c:if> <c:if
									test="${USER_REQUEST.requestType.value=='Opening a card to an existing account'}">
									<fmt:message key="label.cardIssueToExistingAccount" />
								</c:if> <c:if
									test="${USER_REQUEST.requestType.value=='Card opening with opening a new account'}">
									<fmt:message key="label.cardIssueWithOpeningNewAccount" />
								</c:if></td>


							<c:if test="${USER_REQUEST.requestStatus.value=='In processing'}">
								<td id="default" width="20%"><fmt:message
										key="label.inProcessing" /></td>
							</c:if>
							<c:if test="${USER_REQUEST.requestStatus.value=='Processed'}">
								<td id="success" width="20%"><fmt:message
										key="label.completed" /></td>
							</c:if>
							<c:if test="${USER_REQUEST.requestStatus.value=='Rejected'}">
								<td id="error" width="20%"><fmt:message
										key="label.rejected" /></td>
							</c:if>
							<td width="22%"><c:if
									test="${USER_REQUEST.requestStatus.value=='In processing'}">

									<form action="${pageContext.request.contextPath}/controller"
										method="post">
										<input type="hidden" name="COMMAND"
											value="HANDLE_USER_REQUEST"> <input type="hidden"
											name="USER_REQUEST_ID" value="${USER_REQUEST.id}">
										<button type="submit" id="handleButton">
											<fmt:message key="button.handle" />
										</button>
									</form>

									<form action="${pageContext.request.contextPath}/controller"
										method="post">
										<input type="hidden" name="COMMAND"
											value="REJECT_USER_REQUEST"> <input type="hidden"
											name="USER_REQUEST_ID" value="${USER_REQUEST.id}">
										<button type="submit">
											<fmt:message key="button.reject" />
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

