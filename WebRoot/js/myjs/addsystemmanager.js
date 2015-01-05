/**
 *@author walker cheng 
 *2015/01/05
 *function add system manager
 */
function addManagerByList(userId){
	$.ajax({
		type:"POST",
		data:{userId:userId},
		dataType:"JSON",
		url:"AddSystemManager.action",
		success:function(result){
			if(result.status==0){
				alert(result.msg);
				return false;
			}else{
				alert("任命成功！");
				location.href="InitAddSystemManager.action?userType=1";
			}	
		}
	});
	}

/**
 *@author walker cheng 
 *2015/01/05
 *function Determine whether qualified and add system manager
 */
function addManager(){
	var userId=document.getElementById("userId").value;
	var userType=document.getElementById("userType").value;
	if(userType==0){
		alert("俱乐部负责人不能成为系统管理员！");
		return false;
	}
	if(userType==10){
		alert("他已经是系统管理员！");
		return false;
	}
	$.ajax({
		type:"POST",
		data:{userId:userId},
		dataType:"JSON",
		url:"AddSystemManager.action",
		success:function(result){
			if(result.status==0){
				alert(result.msg);
				return false;
			}else{
				alert("任命成功！");
				location.href="UserSearch.action?userType=10&userName=";
			}	
		}
	});
	}