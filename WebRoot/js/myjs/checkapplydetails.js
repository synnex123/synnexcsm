function passapply(applyId,userId,clubId){
if(confirm('请确认是否通过')){
	$.ajax({
		type:"GET",
		dataType:"json",
		url:"processApply.action?applyId="+applyId+"&userId="+userId+"&clubId="+clubId,
		success:function(result){
			alert(result.msg);
				location.href="getApplyByManagerId.action?pageIndex=0";
				return true;
}
});
		}else{
		return false;
		}
}
function rejectapply(applyId){
	if (confirm('请确认是否驳回')) {
		var checkRes = document.getElementById("checkRes").value;
			if (checkRes.length<=8) {
				alert('驳回原因过短');
				return false;
			}else{
			$.ajax({
			type:"POST",
			dataType:"json",
			url:"rejectApply.action",
			data:{applyId:applyId,checkRes:checkRes},
			success:function(result){
				alert(result.msg);
					location.href="getApplyByManagerId.action?pageIndex=0";
					return true;
				}
			});
		}
		}else{
	return false;
}
}