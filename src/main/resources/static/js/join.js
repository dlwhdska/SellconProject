/**
 * 
 */

 var isIdChecked = false;	    
	    document.getElementById("idCheck").addEventListener('click', function() {
	        var idCheck = document.getElementById("id");
	        var xhr = new XMLHttpRequest();
	        var value = 'id=' + idCheck.value;
	        xhr.open('POST', '/idCheck', true);
	        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	        xhr.send(value);
	        xhr.onload = function() {
	            var color;
	            if (xhr.status == 200) {
	            	
	            	if (idCheck.value == "") {
	                    alert("아이디를 입력해 주세요.")
	                } else {
	                    if (xhr.response == "true") {
	                        alert("사용 가능한 아이디입니다.");
	                        isIdChecked = true;
	                    } else {
	                        alert("아이디가 존재합니다. 다른 아이디를 입력해 주세요.");
	                    }
	                }
	            }
	        }
	    })
	    
	    var isEmailChecked = false;
	    document.getElementById("emailCheck").addEventListener('click', function() {
	    	var emailCheck = document.getElementById("email");
	    	var xhr = new XMLHttpRequest();
	    	var value = "email=" + emailCheck.value;
	    	xhr.open('POST', '/emailCheck', true);
	    	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	        xhr.send(value);
	        xhr.onload = function() {
	            var color;
	            if (xhr.status == 200) {
	            	
	            	if (emailCheck.value == "") {
	                    alert("이메일을 입력해 주세요.")
	                } else {
	                    if (xhr.response == "true") {
	                        alert("사용 가능한 이메일입니다.");
	                        isEmailChecked = true;
	                    } else {
	                        alert("이메일이 존재합니다. 다른 이메일을 입력해 주세요.");
	                    }
	                }
	            }
	        }
	    })
	  
	    
	    function goSave() {
	    	
	    	var id = document.getElementById("id");
	    	var idCheck = document.getElementById("idCheck");
	    	var pwd = document.getElementById("pwd");
	    	var passwordPattern = /^(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&])[a-z\d@$!%*?&]{8,16}$/;
	    	var pwdCheck = document.getElementById("pwdCheck");
	    	var name = document.getElementById("name");
	    	var email = document.getElementById("email");
	    	var phone = document.getElementById("phone");
	    	
	    	if (!isIdChecked) {
	            alert("아이디 중복 확인을 해주세요.");
	            return false;
	        } else if (!isEmailChecked) {
	            alert("이메일 중복 확인을 해주세요.");
	            return false;
	        } else if (id.value == "") {
	    		alert("아이디를 입력해 주세요.");
	    		id.focus();
	    		return false;
	    	} else if (id.value == " ") {
	    		alert("아이디에 공백이 있으면 안됩니다.");
	    		id.focus();
	    		return false;
	    	} else if (pwd.value == "") {
	    		alert("비밀번호를 입력해 주세요.");
	    		pwd.focus();
	    		return false;
	    	} else if (pwd.value.length < 8 || pwd.value.length > 16) {
	            alert("비밀번호는 8자에서 16자 사이어야 합니다.");
	            pwd.focus();
	            return false;
	        } else if (!passwordPattern.test(pwd.value)) {
	            alert("영문, 숫자, 특수문자를 모두 포함하여 8자~16자로 입력해주세요.");
	            pwd.focus();
	            return false;
	        } else if (pwd.value != pwdCheck.value) {
	    		alert("비밀번호가 일치하지 않습니다.");
	    		pwd.focus();
	    		return false;
	    	} else if (name.value == "") {
	    		alert("이름을 입력해 주세요.");
	    		name.focus();
	    		return false;
	    	} else if (email.value == "") {
	    		alert("이메일을 입력해 주세요.");
	    		email.focus();
	    		return false;
            } else if (phone.value == "") {
	    		alert("전화번호를 입력해 주세요.");
	    		phone.focus();
	    		return false;
            } else {
	    		var theForm = document.getElementById("join");
	    		alert("회원가입 완료");
	    		theForm.action = "/join";
	    		theForm.submit();
	    		return false;
	    	}
	    	
	    	return true;
	    }