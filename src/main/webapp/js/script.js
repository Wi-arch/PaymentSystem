function openForm() {
	document.getElementById("inputPasswordForm").style.display = "block";
	document.getElementById("oldPassword").value = "";
	document.getElementById("newPassword").value = "";
	document.getElementById("confirmNewPassword").value = "";
}

function closeForm() {
	document.getElementById("inputPasswordForm").style.display = "none";
}