<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.1.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/validator-0.7.3/jquery.validator.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validator-0.7.3/jquery.validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validator-0.7.3/local/zh_CN.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/lib/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/stylesheets_default/theme.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/lib/font-awesome/css/font-awesome.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/lib/datepicker/css/datepicker.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/lib/bootstrap/js/bootstrap-modal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/other.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/lib/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/lib/datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/myjs/change_psw.js"></script>

<title>注册用户</title>
</head>
<body class="simple_body">
	<div class="content">
		<div class="header">
			<div class="stats">
				<p class="stat">
					<!--span class="number"></span-->
				</p>
			</div>
			<h1 class="page-title">注册用户</h1>
		</div>
		<ul class="breadcrumb">
			<li><a href="http://demo.osadmin.org/index.php"> 控制面板 </a> <span class="divider">/</span></li>
			<li><a href="http://demo.osadmin.org/admin/users.php"> 用户列表
			</a> <span class="divider">/</span></li>
			<li class="active">注册用户</li>
		</ul>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="bb-alert alert alert-info" style="display: none;">
					<span >操作成功</span>
				</div>
				<div style="color:red">${requestScope.errmsg}</div>
				<!-- START 以上内容不需更改，保证该TPL页内的标签匹配即可 -->
				<div class="well">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#home" data-toggle="tab">请填写用户资料</a></li>
					</ul>

					<div id="myTabContent" class="tab-content">
						<div class="tab-pane active in" id="home">

							<form id="tab" method="post" action="<%=request.getContextPath()%>/UserRegist.action" autocomplete="off">
								<label>登录用户名</label>
								<input type="text" name="userName" value="" class="input-xlarge" data-rule="required;username;"data-tip="请输入3-12位数字、字母、下划线"/>
								<label>电话</label>
								<input type="text" id="userPhone" name="userPhone" class="input-xlarge" data-rule="required;mobile;" data-tip="手机号由11位数字构成"/>
								<label>Email</label>
								<input type="text" id="userEmail" name="userEmail" value="" class="input-xlarge" data-rule="required;email;" data-tip="邮箱是重要的找回密码途径"/>
								<label>所在部门</label>
								<input type="text" id="userDept" name="userPart" value="" class="input-xlarge" data-rule="required;" data-tip="请认真填写所在部门"/>
								<label>密码</label>
								<input type="password" id="password1" name="userPassword" value="" class="input-xlarge" data-rule="required;password;" data-tip="密码由6-16位数字、字母组成"/>
								<label>确认密码</label>
								<input type="password" id="password2" value="" class="input-xlarge" onblur="checkpassword();"/>	<span id="checkdiv"></span>
								<div class="btn-toolbar" id ="btnsubmit1">
									<div class="btn-group"></div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>