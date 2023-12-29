/**
 * 
 */
function modify() {
        	var theForm = document.getElementById("modify");
        	
        	if (!confirm("수정하시겠습니까?")) {
				return;
			}
        	
        	var form = document.getElementById("modify");
        	var formData = new FormData(form);
        	
        	var xhr = new XMLHttpRequest();
        	xhr.open("POST", "/modify", true);
        	xhr.onreadystatechange = function() {
        		if (xhr.readyState === XMLHttpRequest.DONE) {
        			if (xhr.status === 200) {
        				alert("수정성공!")
        			} else {
        				alert("수정실패!")
        			}
        		}
        	};
        	xhr.send(formData);
        }