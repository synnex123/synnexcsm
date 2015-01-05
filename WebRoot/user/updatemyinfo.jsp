<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.1.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/validator-0.7.3/jquery.validator.css"/>
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
<script type="text/javascript" src="<%=request.getContextPath() %>/js/myjs/updatemyinfo.js"></script>
<title>编辑用户信息</title>
</head>
<body class="simple_body">
	<div class="content">
		<div class="header">
			<div class="stats">
				<p class="stat">
					<!--span class="number"></span-->
				</p>
			</div>
			<h1 class="page-title">个人信息</h1>
		</div>
		<ul class="breadcrumb">
			<li class="active">个人信息管理</li>
		</ul>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="bb-alert alert alert-info" style="display: none;">
					<span>操作成功</span>
				</div>
				<!-- START 以上内容不需更改，保证该TPL页内的标签匹配即可 -->
				<div class="well">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#home" data-toggle="tab">编辑个人信息</a></li>
					</ul>
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane active in" id="home">
						<form id="tab" method="post" action="#" autocomplete="off">
							<div style="float:left;margin: 20px">
								<label>登录用户名</label>
								<input type="text" id="userName" name="userName" value="${user.userName}" class="input-xlarge" data-rule="required;username;" placeholder="请输入3-12位数字、字母、下划线"/>
								<label>电话</label>
								<input type="text" id="userPhone" name="userPhone" value="${user.userPhone}" class="input-xlarge" data-rule="required;mobile;" placeholder="手机号由11位数字构成"/>
								<label>Email</label>
								<input type="text" id="userEmail" name="userEmail" value="${user.userEmail}" class="input-xlarge" data-rule="required;email;" placeholder="邮箱是重要的找回密码途径"/>
								<label>所在部门</label>
								<input type="text" id="userPart" name="userPart" value="${user.userPart}"  class="input-xlarge" data-rule="required;" placeholder="请认真填写所在部门"/>
							</div>
							<div style="float:left;margin-left:100px;margin-top: 20px">
							    <c:if test="${user.userType==0}">
								     <label>用户类型</label>
								     <input type="text" name="userType" id="userType" value="负责人" disabled="disabled"  class="input-xlarge"/>
								</c:if>
								<c:if test="${user.userType==1}">
								     <label>用户类型</label>
								     <input type="text" name="userType" id="userType" value="普通用户" disabled="disabled"  class="input-xlarge"/>
								</c:if>        
							</div>
								<span id="checkdiv"></span>
								<div class="btn-toolbar" id ="btnsubmit1">
									<div class="btn-group"></div>
								</div>
								<DIV style="clear: left;margin-left: 200px">
							    <div class="btn-toolbar">
							        <DIV style="float: left">
							        <button type="button" class="btn btn-primary" onclick="doSave();"><strong>提交</strong></button>
							        </DIV>
									<DIV style="float: left;margin-left: 100px">
									    <a href="<%=request.getContextPath()%>/init.action?location=chengdu" class="btn btn-primary">返回</a>
									</DIV>
									
									<div class="btn-group"></div>
								</div>
							</DIV>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
