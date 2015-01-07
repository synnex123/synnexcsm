function doDeleteUser(userId){
if (confirm('请确认是否注销此用户,请注意：将删除有关此人的一切信息（申请，投票，俱乐部关系等）')) {
	$.ajax({
		type:"POST",
		dataType:"json",
		data:{userId:userId},
		url:"deleteUser.action",
		success:function(result){
				if (result.status==0) {
					alert(result.msg);
					location.reload();
					return false;
				}else if(result.status==1){
					alert(result.msg);
					location.reload();
					return true;
				}
			}
		});

}else{
return false;
}

}