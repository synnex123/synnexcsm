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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/myjs/dividepage.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/lib/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/stylesheets_default/theme.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/lib/font-awesome/css/font-awesome.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/lib/bootstrap/js/bootstrap-modal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/other.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/lib/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/myjs/addsystemmanager.js"></script>
<title>添加系统管理员</title>
</head>
<body class="simple_body">
	<div class="content">
		<div class="header">
			<div class="stats">
				<p class="stat">
					<!--span class="number"></span-->
				</p>
			</div>

			<h1 class="page-title">添加系统管理员</h1>
		</div>
		<ul class="breadcrumb">
			<li><a href="<%=request.getContextPath() %>/init.action?location=chengdu">首页 </a> <span class="divider">/</span></li>
			<li><a href="<%=request.getContextPath() %>/user/addsystemmanager.jsp">返回 </a> <span class="divider">/</span></li>
			<li class="active">用户信息查询</li>
		</ul>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="bb-alert alert alert-info" style="display: none;">
					<span>操作成功</span>
				</div>
						<div style="clear: both;"></div>
				</div>
				<div class="block">
					<a href="#page-stats" class="block-heading" data-toggle="collapse">普通用户列表</a>
					<div id="page-stats" class="block-body collapse in">
						<table class="table table-striped">
							<thead>
								<tr>
								    <th style="width: 20px" hidden="hidden">#</th>
									<th style="width: 80px">用户名</th>
									<th style="width: 80px">所属部门</th>
									<th style="width: 120px">邮箱</th>
									<th style="width: 100px">联系电话</th>
									<th style="width: 120px">操作</th>
								</tr>
							</thead>
							<tbody>
							<c:set var="i" value="0"/>
								<c:forEach items="${resultList}" var="searchDto">
								<c:set var="i" value="${i+1 }"/>
								<tr>
								   <td name=culbId id="${i}" hidden="hidden">${searchDto["userId"]}</td>
									<td>${searchDto.userName }</td>
									<td>${searchDto.userPart }</td>
									<td>${searchDto.userEmail}</td>
									<c:if test="${not empty searchDto.userPhone}">
										<td>${searchDto.userPhone}</td>
									</c:if>
									<c:if test="${empty searchDto.userPhone}">
										<td>无</td>
									</c:if>
									<td> 
										<a class="btn btn-primary" href="<%=request.getContextPath() %>/UserSearch.action?userName=${searchDto.userName}&purpose=addmanagerlist" title="详细"><font color="white">浏览详细</font></a> &nbsp; 							
										&nbsp;
							        	<button type="button" class="btn btn-primary" onclick="return addManagerByList(${searchDto.userId});"><font color="white">任命</font></button>
										</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						<!--- START 分页模板 --->

						<div class="pagination">
						<% 
						Integer pageIndex=((Integer)request.getAttribute("pageIndex")/5)+1;
						%>
							<ul>
								<li><a href="javascript:void(0);" onclick='previousPage("1","InitAddSystemManager");'>&lt;&lt;</a></li>
								<li class="active" ><span id="currentPage">${currentPage }</span></span></li>
								<li><a href="javascript:void(0);" onclick='nextPage("1","InitAddSystemManager");'>&gt;&gt;</a></li>
								<li ><span>共<span id="totalPage">${totalPage }</span>页</span></span></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>