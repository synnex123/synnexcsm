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
<title>菜单页面</title>
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
								<tr>
								   <td name=culbId id="${i}" hidden="hidden">${clubInfo["clubId"]}</td>
								  

									<td>${i }</td>
									<td>${clubInfo["clubName"] }</td>
									<td>${clubInfo["managerName"] }</td>
									<td>${clubInfo["managerPhone"] }</td>
									<td>${clubInfo["clubLocation"] }</td>
									<td><button class="btn btn-primary" style="float:left;width : 120px; height : 28px;" onclick="join(${clubInfo.clubId});">
										<font color="white">我要加入</font></button>
										<button class="btn btn-primary" style="float:left;width : 120px; height : 28px;">
										<a href="detail.jsp" title="详细"><font color="white">浏览详细</font></a></button> &nbsp; 							
										&nbsp; </td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						<!--- START 分页模板 --->

						<div class="pagination">
							<ul>
								<li><a href="javascript:void(0);" onclick="">&lt;&lt;</a></li>
								<li class="active"><span>1</span></li>
								<li><a href="javascript:void(0);" onclick="">&gt;&gt;</a></li>
								<li><span id="countclub">共3条</span></li>
							</ul>
						</div>
						<!--- END --->
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<SCRIPT type="text/javascript">
	function join(clubId){
		location.href="initApply.action?clubId="+clubId;
	}
</SCRIPT>
</html>