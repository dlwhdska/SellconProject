/**
 * 
 */
function checkNameEmail() {
    		event.preventDefault();
    		var name = document.getElementById("name").value;
    		var email = document.getElementById("email").value;
    		
    		if (name == ""){
    			alert("이름을 입력해 주세요.");
    			name.focus();
    			return false;
    		} else if (email == "") {
    			alert("이메일을 입력해 주세요.");
    			email.focus();
    			return false;
            } 
    		
    		
    		var xhr = new XMLHttpRequest();
    		xhr.onreadystatechange = function() {
    			if (xhr.readyState === XMLHttpRequest.DONE) {
    				if (xhr.status === 200) {
    					var response = xhr.responseText;
    					if (response === "success"){
    						alert("인증번호 전송완료")
    						window.location.href = "/verifycode?name=" + name + "&email=" + email;
    					} else {
    						alert("이름과 이메일이 일치하지 않습니다.");
    					}
    				} else {
    					alert("이름과 이메일이 일치하지 않습니다.");
    				}
    			}
    		};
    		
    		xhr.open("POST", "/findid");
    		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    		xhr.send("name=" + name + "&email=" + email);
    		
    	}