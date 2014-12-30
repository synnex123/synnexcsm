/*
 *@author joeyy,modified by petep
 *2014/11/24
 */
function checklogin(){
	var userName = document.getElementById("userName").value;
	var userPassword = document.getElementById("password").value;	
$.ajax({
	type:"POST",
	dataType:"json",
	data:{userName:userName,userPassword:userPassword},
	url:"UserLogin.action",
	success:function(result){
		if (result.status==0) {
		alert(result.msg);	
		}else if(result.status==1){
		alert("succeed to login");
		var rememberMe=document.getElementsByName("remember");
		if(rememberMe[0].checked==true)location.href="init.action?addCookie=1";
		else location.href="init.action?addCookie=0";
		}
	}
});
}
function doregister(){
	location.href="../user/user_add.jsp";
}

