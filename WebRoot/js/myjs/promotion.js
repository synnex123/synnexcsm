	function dopromote(promotionId){
	if (confirm('你确认提交么？')) {		
		var voteduser=document.getElementsByName("promotionuser");
		var params="";
		for(var i=0;i<voteduser.length;i++){
			if(voteduser[i].checked){
				params=voteduser[i].value;
				break;
			}
		}
		if(params===""){
			alert("没有选择成员");
			return;
		}
		$.ajax({
			type:"POST",
			dataType:"json",
			data:{promotionUser:params,promotionId:promotionId},
			url:"doPromotion.action",
			success:function(result){
					alert(result.msg);
					location.reload();
					return true;
			}
		});
	}else{
	return false;
	}
	}