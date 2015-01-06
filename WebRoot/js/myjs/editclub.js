function editClub(){
		var clubName=document.getElementById("clubName").value;
		var clubLocation=document.getElementById("clubLocation").value;
		var clubUrl=document.getElementById("clubUrl").value;
		var clubId=document.getElementById("clubId").value;
		var managerId=document.getElementById("managerId").value;
		var clubDescription=document.getElementById("clubDescription").value;
		if(clubName==""){
			alert("请输入俱乐部名称！");
			return false;
		}
		if(clubDescription==""){
			alert("请输入俱乐部的描述信息！");
			return false;
		}
		if(clubUrl==""){
			alert("请尽快将俱乐部主页设计并提交上来！");
		}
		$.ajax({
				type:"post",
				data:{clubName:clubName,clubLocation:clubLocation,clubUrl:clubUrl,clubId:clubId,managerId:managerId,clubDescription:clubDescription},
				dataType:"JSON",
				url:"editClub.action",
				success:function(result){
						if(result.status==0){
								alert(result.msg);
								return false;
						}else{
								alert("修改成功！");
								location.href="init.action?location=chengdu";
						}
				}
			});
		
	}