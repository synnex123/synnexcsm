<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.1.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/lib/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/stylesheets_default/theme.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/lib/font-awesome/css/font-awesome.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/lib/bootstrap/js/bootstrap-modal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/other.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/lib/bootstrap/js/bootstrap.js"></script>
<title>用户查询</title>
</head>
<body class="simple_body">
<script type="text/javascript">
	if(${not empty errorMsg}){
		alert("${errorMsg}");
		}
	
</script>
	<div class="content">
		<div class="header">
			<div class="stats">
				<p class="stat">
					<!--span class="number"></span-->
				</p>
			</div>
			<h1 class="page-title">用户查询</h1>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="bb-alert alert alert-info" style="display: none;">
					<span>操作成功</span>
				</div>
				<!--- START 以上内容不需更改，保证该TPL页内的标签匹配即可 --->
				
				
				<div id="search" >
					<form id="searchForm" class="form_search" action="<%=request.getContextPath() %>/UserSearch.action" method="POST" style="margin-bottom: 0px">
						<div style="float: left; margin-right: 5px">
							<label>选择用户类型</label>
							<select name="userType" id="userType" class="input-xlarge">
								<option label="请选择用户类型" value="">请选择用户类型</option>
								<option label="负责人" value="0">负责人</option>
								<option label="普通用户" value="1">普通用户</option>
								<option label="系统管理员" value="10">系统管理员</option>
							</select>
						</div>
						<div style="float: left; margin-right: 5px">
							<label>查询所有人请输入 <font style="color:red">*</font> 号</label>
							<input type="text" name="userName" id="userName" value="" placeholder="输入用户名"/>
						</div>
						<div class="btn-toolbar"
							style="padding-top: 25px; padding-bottom: 0px; margin-bottom: 0px">
							<button type="submit" class="btn btn-primary">查询</button>
						</div>
						<div style="clear: both;"></div>
					</form>
				</div>
		</div>
	</div>
	</div>
</body>
</html>