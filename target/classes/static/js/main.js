$(document).ready(function() {
	localStorage.setItem("selectedCategory", "");
});

var slides = document.querySelector(".slides"),
	slide = document.querySelectorAll(".slides li"),
	currentIndex = 0,
	slideCount = slide.length,
	prevBtn = document.querySelector("#left_btn"),
	slideWidth = 250,
	slideMargin = 30,
	nextBtn = document.querySelector("#right_btn");
scrollBarPosition = document.querySelector(".main_how_list_bar");
scrollBarIn = document.querySelector(".main_how_list_bar_in");

slides.style.width = (slideWidth + slideMargin) * slideCount - slideMargin + "px";

function moveSlide(num) {
	slides.style.left = -num * 280 + "px";
	currentIndex = num;
}

function moveScroll() {
	var scrollPosition = (currentIndex / (slideCount - 4));
	scrollPosition = Math.max(0, scrollPosition);
	scrollPosition = Math.min(100, scrollPosition);

	var barWidth = scrollBarPosition.clientWidth - scrollBarIn.clientWidth;
	var newPosition = scrollPosition * barWidth;

	scrollBarIn.style.left = newPosition + "px";
}

nextBtn.addEventListener("click", function() {
	if (currentIndex < slideCount - 4) {
		moveSlide(currentIndex + 1);
	}
	moveScroll();
});

prevBtn.addEventListener("click", function() {
	if (currentIndex > 0) {
		moveSlide(currentIndex - 1);
	}
	moveScroll();
});

function handleMouseOver() {
	this.classList.add("hover-move-active");
	this.setAttribute("src", this.getAttribute("data-animated"));
}

function handleMoustOut() {
	this.classList.remove("hover-move-active");
	this.setAttribute("src", this.getAttribute("data-static"));
}

document.querySelectorAll(".hover-move").forEach(function(element) {
	element.addEventListener("mouseover", handleMouseOver);
	element.addEventListener("mouseout", handleMoustOut);
});

const mouseoverCategory = document.querySelectorAll(".category_img_box");

mouseoverCategory.forEach((category) => {
    const categoryMenuText = category.querySelector(".c_text_box");
    const categoryImage = category.querySelector(".c_img_box img");

    category.addEventListener("mouseover", () => {
        categoryMenuText.classList.add("c_text_box_hover");
        categoryImage.setAttribute("src", categoryImage.getAttribute("data-animated"));
    });

    category.addEventListener("mouseout", () => {
        categoryMenuText.classList.remove("c_text_box_hover");
        categoryImage.setAttribute("src", categoryImage.getAttribute("data-static"));
    });
});