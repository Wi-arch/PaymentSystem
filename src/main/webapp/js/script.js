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




function next(cardList){  
	var i = cardList.length -1;
  if (i == a.length -1) {
    i = 0;
  } else {
    i++
  }
  document.getElementById('output').value = cardList[i]; 
}
function previous(cardList){
	var i = cardList.length -1;
  if (i == 0) {
    i = a.length -1;
  } else {
    i--
  }
    document.getElementById('output').value = cardList[i]; 
}













