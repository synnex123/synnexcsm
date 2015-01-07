<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/lib/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/stylesheets_default/theme.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/lib/font-awesome/css/font-awesome.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/lib/datepicker/css/datepicker.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.1.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/validator-0.7.3/jquery.validator.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validator-0.7.3/jquery.validator.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/validator-0.7.3/local/zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/lib/bootstrap/js/bootstrap-modal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/other.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/lib/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/lib/datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/myjs/addsystemmanager.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/myjs/deleteUser.js"></script>
<title>查询用户</title>
</head>
<body class="simple_body">

<script type="text/javascript">
	if(${not empty errorUserType}){
		alert("${errorUserType}");
	}
</script>
	<div class="content">
		<div class="header">
			<div class="stats">
				<p class="stat">
					<!--span class="number"></span-->
				</p>
			</div>
			<h1 class="page-title">查询用户</h1>
		</div>
		<ul class="breadcrumb">
			<li><a href="<%=request.getContextPath() %>/init.action?location=chengdu">首页 </a> <span class="divider">/</span></li>
			<li class="active">用户信息查询</li>
		</ul>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="bb-alert alert alert-info" style="display: none;">
					<span>操作成功</span>
				</div>
				<div style="color:red">${requestScope.msg}</div>
				<!-- START 以上内容不需更改，保证该TPL页内的标签匹配即可 -->
				<div class="well">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#home" data-toggle="tab">${searchDto.userName}的详细信息</a></li>
					</ul>

					<div id="myTabContent" class="tab-content">
						<div class="tab-pane active in" id="home">
						
							<form id="tab" method="post" action="#" autocomplete="off">
							<DIV>
							<div style="float:left;margin: 20px">
								<!-- 页面为添加系统管理员实现了复用， -->
								<!-- 下面两个值均为添加系统管理员预留 -->
							    <input type="hidden" name="userId" id="userId" value="${searchDto.userId}"  class="input-xlarge"/>
							    <input type="hidden" name="userType" id="userType" value="${searchDto.userType}"  class="input-xlarge"/>
							    <label>用户名</label>
								<input type="text" name="userName" id="userName" value="${searchDto.userName}" disabled="disabled"  class="input-xlarge"/>
								<label>所属部门</label>
								<input type="text" name="userPart" id="userPart" value="${searchDto.userPart}" disabled="disabled"  class="input-xlarge"/>
								<label>Email</label>
								<input type="text" name="email" id="email" value="${searchDto.userEmail}" disabled="disabled"  class="input-xlarge"/>
								<label>联系电话</label>
								<input type="text" name="phone" id="phone" value="${searchDto.userPhone}" disabled="disabled"  class="input-xlarge"/>
								<c:if test="${searchDto.userType==10}">
								     <label>所担任职位</label>
								     <input type="text" name="userType" id="userType" value="系统管理员" disabled="disabled"  class="input-xlarge"/>
								</c:if>  
					        </div>
					        <div style="float:left;margin-left: 20px;margin-top: 20px">
					        <div class="block">
					      	<a href="#page-stats" class="block-heading" data-toggle="collapse">所加俱乐部列表</a>
					    	<div id="page-stats" class="block-body collapse in">
							<table class="table table-striped">
								<thead>
									<tr>
									 	<th style="width: 120px">所加俱乐部</th>
									 	<th style="width: 100px">俱乐部职位</th>
									  	<th style="width: 100px">俱乐部所在地</th>
									</tr>
								</thead>
								<tbody>
								<c:set var="i" value="0"/>
									<c:forEach items="${clubList}" var="clubInfo">
									<c:set var="i" value="${i+1 }"/>
								    <c:set var="userId" value="${searchDto.userId}"/>
									<tr>
										<td name=culbId id="${i}" hidden="hidden">${clubInfo.clubId}</td>
										<td>${clubInfo.clubName}</td>
										<c:if test="${clubInfo.managerId==userId}">
										    <td>负责人</td>
										</c:if>
										<c:if test="${clubInfo.managerId!=userId}">
										    <td>普通会员</td>
										</c:if>
										<td>${clubInfo.clubLocation}</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
							</div>
							</div>
							</DIV >
							<DIV style="clear: left;margin-left: 200px">
							    <div class="btn-toolbar">
							    	<c:if test="${not empty purpose}">
										<DIV style="float: left">
							        		<button type="button" class="btn btn-primary" onclick="return addManager('${purpose}');"><strong>任命为系统管理员</strong></button>
							            </DIV>
									</c:if>&nbsp;&nbsp;&nbsp;
									<c:choose >
										<c:when test="${not empty purpose}">
											<c:choose>
												<c:when test="${purpose eq 'addmanagerlist'}">
													<DIV style="float: left;margin-left: 100px">
													<a href="<%=request.getContextPath() %>/InitAddSystemManager.action?userType=1" class="btn btn-primary">返回</a>
													</DIV>
												</c:when>
												<c:otherwise>
													<DIV style="float: left;margin-left: 100px">
													<a href="<%=request.getContextPath() %>/user/addsystemmanager.jsp" class="btn btn-primary">返回</a>
													</DIV>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
												<c:if test="${not empty viewNeed}">
													<DIV style="float: left;margin-left: 100px">
									    				<a href="<%=request.getContextPath() %>/UserSearch.action?userType=${userType}&userName=" class="btn btn-primary">返回</a>
													</DIV>
												</c:if>
												<c:if test="${sessionScope.usertype==10 }">	
													<button class="btn btn-primary" type="button" onclick="return doDeleteUser(${searchDto.userId});">
													<span><strong><font color="red">
													注销此用户
													</font></strong></span>
													</button>
												</c:if>
												<DIV style="float: left;margin-left: 100px">
								    				<a href="<%=request.getContextPath() %>/user/search.jsp" class="btn btn-primary">返回查询页</a>
												</DIV>
										</c:otherwise>
									</c:choose>
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