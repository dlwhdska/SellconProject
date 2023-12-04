const searchArr = document.querySelectorAll(".arr");
const searchMenu = document.querySelectorAll(".footer_menu_sub");

searchArr.forEach((arrow, index) => {
    arrow.addEventListener("click", () => {
        if (searchMenu[index].style.display === 'block') {
            searchMenu[index].style.display = 'none';
            arrow.classList.remove('on');
        } else {
            searchMenu[index].style.display = 'block';
            arrow.classList.add('on');
        }
    });
});