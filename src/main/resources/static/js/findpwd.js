/**
 * 
 */
function checkEmail() {
	event.preventDefault();
	var email = document.getElementById("email").value;
	if (email == "") {
		alert("이메일을 입력해 주세요.");
		email.focus();
		return false;
	}

	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState === XMLHttpRequest.DONE) {
			if (xhr.status === 200) {
				var response = xhr.responseText;
				if (response === "exists") {
					alert("임시 비밀번호 발급 완료");
					window.location.href = "/login";
				} else {
					alert("입력한 이메일이 존재하지 않습니다. 다시 시도해 주세요.");
				}
			} else {
				alert("입력한 이메일이 존재하지 않습니다. 다시 시도해 주세요.");
			}

		}
	};
	xhr.open("POST", "/findpwd");
	xhr.setRequestHeader("Content-Type",
		"application/x-www-form-urlencoded");
	xhr.send("email=" + email);
}