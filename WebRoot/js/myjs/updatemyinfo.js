/**
 *@author walker cheng
 *2014/12/08
 */
function doSave(){
	var userName=document.getElementById("userName").value;
	var userPhone=document.getElementById("userPhone").value;
	var userEmail=document.getElementById("userEmail").value;
	var userPart=document.getElementById("userPart").value;
$.ajax({
	type:"POST",
	dataType:"json",
	data:{userName:userName,userPhone:userPhone,userEmail:userEmail,userPart:userPart},
	url:"UpdateUserInfo.action",
	success:function(result){
	if (result.status==1) {
		alert("信息更改成功！");
		location.href=result.url;
	}else{
		alert(result.msg);
		}
	}
});
}