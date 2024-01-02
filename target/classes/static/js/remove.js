/**
 * 
 */
function remove() {
	
	var id = document.getElementById("id").value;
	var formData = new FormData();
    formData.append("id", id);
    
	if (!confirm("삭제하시겠습니까?")) {
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/remove", true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                alert("삭제성공!");
                window.location.href = "/main";
            } else {
                alert("삭제실패!");
            }
        }
    };
    xhr.send(formData);
}