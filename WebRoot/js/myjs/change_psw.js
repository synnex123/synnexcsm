/**
 *@author joeyy 
 *2014/11/17
 *function check if password1==password2
 */
function checkpassword(){
	var password1=document.getElementById("password1").value;
	var password2=document.getElementById("password2").value;
$.ajax({
	type:"POST",
	dataType:"json",
	data:{password1:password1,password2:password2},
	url:"CheckPassword.action",
	success:function(result){
	if (result.status==0) {
		var checkdiv=document.getElementById("checkdiv");
		var msg="<span class=\"msg-box n-right\" style=\"\" for=\"password2\">"+"<span class=\"msg-wrap n-error\" role=\"alert\">"+"<span class=\"n-icon\">"+"</span>"+"<span class=\"n-msg\">"+result.msg+"</span></span></span>";
		checkdiv.innerHTML=msg;
	}else{
		var checkdiv=document.getElementById("checkdiv");
		var btnsubmit1 = document.getElementById("btnsubmit1");
		var btn="<button type=\"submit\" class=\"btn btn-primary\" onclick=\"updatepassword();\">"+"<strong>"+"提交"+"</strong>"+"</button>";
		var msg="<span class=\"msg-box n-right\" style=\"\" for=\"password2\">"+"<span class=\"msg-wrap n-ok\" role=\"alert\">"+"<span class=\"n-icon\">"+"</span>"+"<span class=\"n-msg\">"+"</span></span></span>";
		checkdiv.innerHTML=msg;	
		btnsubmit1.innerHTML=btn;
		
		}
	}
});
}
/**
 *@author joeyy 
 *2014/11/25
 *function changepassword
 */
function updatepassword(){
		var password1=document.getElementById("password1").value;
		var password=document.getElementById("password").value;
		$.ajax({
			type:"POST",
			dataType:"json",
			data:{password1:password1,oldpassword:password},
			url:"UserChangePassword.action",
			success:function(result){
				if (result.status==0) {
					var changepsw = document.getElementById("changepsw");
					changepsw.innerHTML = result.msg;	
				}else if(result.status==1){
					alert('succeed to changepassword,please relogin');
					window.parent.location.href="../user/login.jsp";
				}	
			}
		});
	}