/**
 * 
 */
function memberListDelete(button) {
    var row = button.closest('.member-row');
    var id = row.querySelector('.id').innerText;
	var scamyn = row.querySelector('.scamyn option:checked').value;
    
    var formData = new FormData();
    formData.append("id", id);
    formData.append("scamyn", scamyn);
    
    console.log("id", id)
    console.log("scamyn", scamyn)
    console.log("FormData", FormData)
    
    if (!confirm("삭제하시겠습니까?")) {
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/memberListDelete", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                alert("삭제성공!");
                row.remove(); 
            } else {
                alert("삭제실패!");
            }
        }
    };
    xhr.send(formData);
}