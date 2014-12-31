	function doDelete(){
			var clubName=document.getElementById("clubName").value;
			var clubLocation=document.getElementById("clubLocation").value;
			if(clubName==0){
				alert("请选择俱乐部！");
				return false;
			}
			$.ajax({
				type:"post",
				data:"clubName="+clubName+"&clubLocation="+clubLocation,
				dataType:"JSON",
				url:"DeleteClub.action",
				success:function(result){
						if(result.status==0){
								alert(result.msg);
								return false;
						}else{
								 alert("俱乐部删除成功！");
								location.href=result.url+"?location="+clubLocation;
						}
				}
			});
		}