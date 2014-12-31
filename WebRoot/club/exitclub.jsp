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
<title>我的俱乐部</title>
</head>
<body class="simple_body">
	<div class="content">
		<div class="header">
			<div class="stats">
				<p class="stat">
					<!--span class="number"></span-->
				</p>
			</div>

			<h1 class="page-title">我的俱乐部</h1>
		</div>
		<ul class="breadcrumb">
			<li><a href="<%=request.getContextPath() %>/init.action?location=chengdu">首页 </a> <span class="divider">/</span></li>
			<li class="active">我的俱乐部管理</li>
		</ul>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="bb-alert alert alert-info" style="display: none;">
					<span>操作成功</span>
				</div>
						<div style="clear: both;"></div>
				</div>
				<div class="block">
					<a href="#page-stats" class="block-heading" data-toggle="collapse">俱乐部列表</a>
					<div id="page-stats" class="block-body collapse in">
						<table class="table table-striped">
							<thead>
								<tr>
								    <th style="width: 20px" hidden="hidden">#</th>
									<th style="width: 120px">俱乐部名称</th>
									<th style="width: 120px">俱乐部所在地</th>
									<th style="width: 120px">俱乐部负责人</th>
									<th style="width: 120px">负责人邮箱</th>
									<th style="width: 120px">操作</th>
								</tr>
							</thead>
							<tbody>
							<c:set var="i" value="0"/>
								<c:forEach items="${clubList}" var="clubInfo">
								<c:set var="i" value="${i+1 }"/>
								<tr>
								   <td name=culbId id="${i}" hidden="hidden">${clubInfo.clubId}</td>
									<td>${clubInfo.clubName}</td>
									<td>${clubInfo.clubLocation}</td>
									<td>${clubInfo.managerName}</td>
									<td>${clubInfo.managerEmail}</td>
									<td><button  class="btn btn-primary" style="float:left;" onclick="return exitClub(${clubInfo.clubId},'${clubInfo.clubName}','${clubInfo.managerName}','${clubInfo.managerEmail}',${clubInfo.userId},${clubInfo.managerId});">
										<font color="white">退出</font></button> &nbsp;&nbsp;
									 </td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						<!--- START 分页模板 --->

						<div class="pagination">
						<% 
						Integer pageIndex=((Integer)request.getAttribute("pageIndex"));
						%>
							<ul>
								<li><a href="javascript:void(0);" onclick="lastpage(<%=pageIndex%>)">&lt;&lt;</a></li>
							    <li class="active"><span><%=pageIndex%></span></li>
								<li><a href="javascript:void(0);" onclick="nextpage(<%=pageIndex%>);">&gt;&gt;</a></li>
								<li><span>共${requestScope.listNumber}条</span></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
<SCRIPT type="text/javascript">
	function searchUser(userName,userType){
		location.href="searchUser?userName="+clubId+"&userType="+userType;
	}
	function exitClub(clubId,clubName,managerName,managerEmail,userId,managerId){
		if(userId==managerId){
		alert("你是该俱乐部的负责人，若要退出请先更换负责人");
		return false;
		}
		$.ajax({
				type:"post",
				data:{clubId:clubId,clubName:clubName,managerName:managerName,managerEmail:managerEmail,userId:userId,managerId:managerId},
				dataType:"JSON",
				url:"<%=request.getContextPath()%>/exitClub.action",
				success:function(result){
						if(result.status==1){
								alert("退出成功！");
								location.href="<%=request.getContextPath()%>/searchMyClub.action?pageIndex=0";	
						}
				}
			});	
	}

	function lastpage(p){
		if (p===1) {
			alert("there is no previous page");
		}else{
			p=p-1;
			location.href="<%=request.getContextPath() %>/searchMyClub.action?pageIndex="+p;
		}
	}
	function nextpage(p){
			p=p+1;
			location.href="<%=request.getContextPath() %>/searchMyClub.action?pageIndex="+p;
	}
</SCRIPT>
</html>