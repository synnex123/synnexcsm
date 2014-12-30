<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.1.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/lib/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/stylesheets_default/theme.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/lib/font-awesome/css/font-awesome.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/myjs/login.js"></script>
<title>登陆页面</title>

<style type="text/css">
	.brand {
		font-family: georgia, serif;
	}
	.brand .first {
		color: #fff;
        font-weight: bold;

	}
</style>
<script type="text/javascript">
if(window != top){
	top.location.href = window.location.href;
	}
</script>
</head>
<body class="simple_body">

	<div class="navbar">
		<div class="navbar-inner">
			<ul class="nav pull-right">

			</ul>
			<a class="brand" href="#"><span class="first">公司俱乐部管理</span></a>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row-fluid">

			<div class="dialog">
				<div class="block">
				<span style="color:red;" id = "errmsg">${requestScope.errmsg}</span>
					<p class="block-heading">登陆系统</p><span style="color:red">${securitymsg}</span>
					<div class="block-body">
						<form name="loginForm" method="post" >
							<%
								String userName="";
								String password="";
								Cookie[] cookies=request.getCookies();
								if(cookies!=null){
									for(Cookie cookie:cookies){
										if(cookie.getName().equals("userName")){
											userName=cookie.getValue();
										}
										
										else if(cookie.getName().equals("password")){
											password=cookie.getValue();
										}
										else ;
									}
								}
							%>
							<label>账号</label>
							<input type="text" class="span12" name="userName" id="userName" value="<%=userName%>"/>
							<label>密码</label>
							<input type="password" class="span12" name="userPassword" id="password" value="<%=password%>"/>
							<div class="clearfix">
								<input type="checkbox" name="remember" value="remember-me" />
								记住我 <span class="label label-info">一个月内不用再次登入</span> 
									<button type="button" class="btn btn-primary pull-right" name="registerbtn" id="registerbtn" value="注册" onclick="doregister();">注册</button>
									<input
									type="button" class="btn btn-primary pull-right"
									name="loginSubmit" value="登入" onclick="checklogin();"/>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>