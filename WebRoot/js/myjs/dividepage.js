function previousPage(location,action){
	var currentPage=parseInt(document.getElementById("currentPage").innerHTML);
	if(currentPage==1){
		alert("没有前一页了");
		return ;
	}
	currentPage=currentPage-1;
	document.location.href=action+".action?location="+location+"&currentPage="+currentPage;
}
function nextPage(location,action){
	var currentPage=parseInt(document.getElementById("currentPage").innerHTML);
	var totalPage=parseInt(document.getElementById("totalPage").innerHTML);
	if (currentPage==totalPage){
		alert("没有后一页了");
		return ;
	}
	currentPage=currentPage+1;
	var url=action+".action?location="+location+"&currentPage="+currentPage; 
	document.location.href=url;
}