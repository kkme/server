
//下一页
function gotoNextPage() {
	var totalPageNum = document.getElementById("totalPage").value;
	var currentPage = document.getElementById("currentPage");
	var currentPageNum = parseInt(currentPage.value);
	if (currentPageNum < totalPageNum) {
		currentPage.value = currentPageNum + 1;
		document.forms.item(0).submit();
	}
}

//上一页
function gotoPrePage() {
	var currentPage = document.getElementById("currentPage");
	var currentPageNum = parseInt(currentPage.value);
	if (currentPageNum > 0) {
		currentPage.value = currentPageNum - 1;
		document.forms.item(0).submit();
	}
}