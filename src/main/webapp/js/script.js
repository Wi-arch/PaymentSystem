function openForm() {
	document.getElementById("userDataBox").style.display = "none";
	document.getElementById("inputPasswordForm").style.display = "block";
	document.getElementById("oldPassword").value = "";
	document.getElementById("newPassword").value = "";
	document.getElementById("confirmNewPassword").value = "";
}

function closeForm() {
	document.getElementById("inputPasswordForm").style.display = "none";
	document.getElementById("userDataBox").style.display = "block";
}

function openFormNewAccount() {
	document.getElementById("userCreateRequestBox").style.display = "none";
	document.getElementById("openFormNewAccount").style.display = "block";
	document.getElementById("checkedButton").checked = "checked";
}

function closeFormNewAccount() {
	document.getElementById("openFormNewAccount").style.display = "none";
	document.getElementById("userCreateRequestBox").style.display = "block";
}

function openFormNewCardExistingAccount() {
	document.getElementById("userCreateRequestBox").style.display = "none";
	document.getElementById("openFormNewCardExistingAccount").style.display = "block";
}

function closeFormNewCardExistingAccount() {
	document.getElementById("openFormNewCardExistingAccount").style.display = "none";
	document.getElementById("userCreateRequestBox").style.display = "block";
}

function openFormNewCard() {
	document.getElementById("userCreateRequestBox").style.display = "none";
	document.getElementById("openFormNewCard").style.display = "block";
}

function closeFormNewCard() {
	document.getElementById("openFormNewCard").style.display = "none";
	document.getElementById("userCreateRequestBox").style.display = "block";
}

function openFormCardUnlockRequest() {
	document.getElementById("userCreateRequestBox").style.display = "none";
	document.getElementById("unlockCardRequestForm").style.display = "block";
}

function openCardOperationsForm(cardNumber, cardNumberMask, cardCreationDate, validUntilDate, bankAccountNumber) {
	document.getElementById("userCardsBox").style.display = "none";
	document.getElementById("cardOperationsForm").style.display = "block";
	document.getElementById("cardNumber").value=cardNumber;
	document.getElementById("cardNumberMask").value=cardNumberMask;
	document.getElementById("cardCreationDate").value=cardCreationDate;
	document.getElementById("validUntilDate").value=validUntilDate;
	document.getElementById("bankAccountNumber").value=bankAccountNumber;
}

function setOperationCommand(command){
	document.getElementById("operationCommand").value=command;
}

function setInfoCommand(command){
	document.getElementById("infoCommand").value=command;
}

function openCardInfoForm() {
	document.getElementById("cardOperationsForm").style.display = "none";
	document.getElementById("cardInfoForm").style.display = "block";
	document.getElementById("cardNumberMaskInfo").value= document.getElementById("cardNumberMask").value;
}

function openCardDepositForm() {
	document.getElementById("cardOperationsForm").style.display = "none";
	document.getElementById("cardDepositForm").style.display = "block";
	document.getElementById("cardNumberMaskDeposit").value= document.getElementById("cardNumberMask").value;
	document.getElementById("depositCardNumber").value= document.getElementById("cardNumber").value;
}

function openCardWriteOffForm() {
	document.getElementById("cardOperationsForm").style.display = "none";
	document.getElementById("cardWriteOffForm").style.display = "block";
	document.getElementById("cardNumberMaskWriteOff").value= document.getElementById("cardNumberMask").value;
	document.getElementById("writeOffCardNumber").value= document.getElementById("cardNumber").value;
}

function openCardTransferForm() {
	document.getElementById("cardOperationsForm").style.display = "none";
	document.getElementById("cardTransferForm").style.display = "block";
	document.getElementById("cardNumberMaskTransfer").value= document.getElementById("cardNumberMask").value;
	document.getElementById("senderCardNumber").value= document.getElementById("cardNumber").value;
}








