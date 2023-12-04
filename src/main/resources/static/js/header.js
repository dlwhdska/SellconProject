
const searchBtn = document.querySelector(".search_icon");
const searchForm = document.querySelector(".search_form_hd");

searchBtn.addEventListener("click", () => {

    if (searchBtn.classList.contains("move")) {

        searchBtn.classList.remove("move");
        searchForm.classList.remove("open");

    } else {
        
        searchBtn.classList.add("move");
        searchForm.classList.add("open");

    }
    
});


