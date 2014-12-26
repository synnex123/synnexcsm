	function doApply(){
			var clubName=document.getElementById("clubName").value;
			var managerName=document.getElementById("managerName").value;
			var clubLocation=document.getElementById("clubLocation").value;
			var clubDescription=document.getElementById("clubDescription").value;
			if(clubName==""){
				alert("请输入俱乐部名称！");
				return false;
			}
			if(managerName==""){
				alert("请输入俱乐部负责人！");
				return false;
			}
			if(clubLocation==""){
				alert("请输入俱乐部所在地！");
				return false;
			}
			if(clubDescription.length<=8){
				alert("请输入关于俱乐部的相关描述！");
				return false;
			}
			$.ajax({
				type:"post",
				data:"clubName="+clubName+"&managerName="+managerName+"&clubLocation="+clubLocation+"&clubDescription="+clubDescription,
				dataType:"JSON",
				url:"AddClub.action",
				success:function(result){
						if(result.status==0){
								alert(result.msg);
								return false;
						}else{
								 alert("创建成功！");
								location.href=result.url+"?location="+clubLocation;
						}
				}
			});
		}