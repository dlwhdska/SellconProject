
$(document).ready(function() {
	localStorage.setItem("selectedCategory", "");
	$(".product_sub ul li .new_catebox").click(function(e) {
		e.preventDefault();

		var categoryKind = $(this).data("category");

		$.ajax({
			url: "/updatenew",
			method: "GET",
			data: { category: categoryKind },
			dataType: "html",
			success: function(data) {
				$(".product_list_elements").html(data);
			},
			error: function(error) {
				console.log(error);
			}
		});
	});
});