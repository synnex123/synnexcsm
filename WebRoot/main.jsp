<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<title>菜单页面</title>
<SCRIPT type="text/javascript">
	function join(clubId){
		location.href="initApply.action?clubId="+clubId;
	}
	function enterClub(clubUrl){
		if(clubUrl==""){
			alert("对不起，该俱乐部暂时还没有主页！");
			return false;
		}
		var result=clubUrl.indexOf("http://");
		if(result==0){
			window.open(clubUrl);
		}else{
			window.open("http://"+clubUrl);
		}
	}
</SCRIPT>
</head>
<body class="simple_body">
	<div class="content">
		<div class="header">
			<div class="stats">
				<p class="stat">
					<!--span class="number"></span-->
				</p>
			</div>

			<h1 class="page-title">俱乐部列表</h1>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="bb-alert alert alert-info" style="display: none;">
					<span>操作成功</span>
				</div>
				<!--- START 以上内容不需更改，保证该TPL页内的标签匹配即可 --->				
				<div class="block">
					<a href="#page-stats" class="block-heading" data-toggle="collapse">俱乐部列表</a>
					<div id="page-stats" class="block-body collapse in">
						<table class="table table-striped">
							<thead>
								<tr>
								    <th style="width: 20px" hidden="hidden">#</th>
									<th style="width: 20px">#</th>
									<th style="width: 80px">俱乐部</th>
									<th style="width: 100px">负责人</th>
									<th style="width: 100px">联系方式</th>
									<th style="width: 100px">地点</th>
									<th style="width: 80px">操作</th>
								</tr>
							</thead>
							<tbody>
							<c:set var="i" value="0"/>
							<c:forEach items="${clubList}" var="clubInfo">
								<c:set var="i" value="${i+1 }"/>
								<c:set var="location" value="${clubInfo.clubLocation }" />
								<tr>
								   <td name=culbId id="${i}" hidden="hidden">${clubInfo["clubId"]}</td>
								  

									<td>${i }</td>
									<td>${clubInfo["clubName"] }</td>
									<td>${clubInfo["managerName"] }</td>
									<td>${clubInfo["managerPhone"] }</td>
									<td>${clubInfo["clubLocation"] }</td>
									<td><button class="btn btn-primary" style="float:left;width : 120px; height : 28px;" onclick="join(${clubInfo.clubId});">
										<font color="white">我要加入</font></button>
										 <button class="btn btn-primary" style="float:left;width : 120px; height : 28px;" onclick="return enterClub('${clubInfo.clubUrl}');">
										<font color="white">浏览主页</font></button>&nbsp; 							
										&nbsp; </td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						<!--- START 分页模板 --->

						<div class="pagination">
							<ul>
								<li><a href="javascript:void(0);" onclick='previousPage("${location}","init");'>&lt;&lt;</a></li>
								<li class="active" ><span id="currentPage">${currentPage }</span></span></li>
								<li><a href="javascript:void(0);" onclick='nextPage("${location}","init");'>&gt;&gt;</a></li>
								<li ><span>共<span id="totalPage">${totalPage }</span>页</span></span></li>
							</ul>
						</div>
						<!--- END --->
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
