/**
 * 
 */
function checkCode() {
			var code = document.getElementById("code");
			var email = document.getElementById("email").textContent;
			
			if (code.value == "") {
				alert("인증번호를 입력해 주세요.");
				code.focus();
				return false;
			}
        }