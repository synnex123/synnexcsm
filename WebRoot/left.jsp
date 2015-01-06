<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.1.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="./assets/lib/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="./assets/stylesheets_default/theme.css" />
<link rel="stylesheet" href="./assets/lib/font-awesome/css/font-awesome.css" />
<script type="text/javascript" src="./assets/lib/bootstrap/js/bootstrap-modal.js"></script>
<script type="text/javascript" src="./assets/js/other.js"></script>
<script type="text/javascript" src="./assets/js/jquery-ui.js"></script>
<script type="text/javascript" src="./assets/lib/bootstrap/js/bootstrap.js"></script>
<title>菜单页面</title>

</head>
<body class="simple_body">
	<div class="sidebar-nav">
		<a href="#sidebar_menu_2" class="nav-header collapsed" data-toggle="collapse"><i class="icon-pushpin"></i>地区 <i class="icon-chevron-up"></i></a>
		<ul id="sidebar_menu_2" class="nav nav-list collapse">
			<li><a href="<%=request.getContextPath()%>/init.action?location=chengdu" target="main">成都</a></li>
			<li><a href="<%=request.getContextPath()%>/init.action?location=beijing" target="main">北京</a></li>
		</ul>
		<a href="#sidebar_menu_1" class="nav-header collapsed" data-toggle="collapse"><i class="icon-eye-open"></i>个人管理 <i class="icon-chevron-up"></i></a>
		<ul id="sidebar_menu_1" class="nav nav-list collapse in">
			<li><a href="<%=request.getContextPath()%>/getMyApply.action?pageIndex=0" target="main">我的申请</a></li>
			<li><a href="<%=request.getContextPath()%>/searchMyClub.action?pageIndex=0" target="main">我的俱乐部</a></li>
			<li><a href="<%=request.getContextPath()%>/user/change_psw.jsp" target="main">更改密码</a></li>
		</ul>
		<a href="#sidebar_menu_6" class="nav-header collapsed" data-toggle="collapse"><i class="icon-book"></i>相关信息<i class="icon-chevron-up"></i></a>
		<ul id="sidebar_menu_6" class="nav nav-list collapse">
			<li><a href="<%=request.getContextPath()%>/user/search.jsp" target="main">用户查询</a></li>
			<li><a href="<%=request.getContextPath()%>/getPromotion.action" target="main">负责人更换投票</a></li>
		</ul>
		<c:if test="${sessionScope.usertype==0 }">
			<a href="#sidebar_menu_7" class="nav-header collapsed" data-toggle="collapse"><i class="icon-globe"></i>俱乐部管理<i class="icon-chevron-up"></i></a>
		<ul id="sidebar_menu_7" class="nav nav-list collapse">
			<li><a href="<%=request.getContextPath()%>/getApplyByManagerId.action?pageIndex=0" target="main">受理申请</a></li>
			<li><a href="<%=request.getContextPath()%>/initPromotion.action" target="main">更换管理员</a></li>
		</ul>
		</c:if>
	   <c:if test="${sessionScope.usertype==10 }">	
	   <a href="#sidebar_menu_7" class="nav-header collapsed" data-toggle="collapse"><i class="icon-key"></i>俱乐部系统管理<i class="icon-chevron-up"></i></a>
		<ul id="sidebar_menu_7" class="nav nav-list collapse">	
				<li><a href="<%=request.getContextPath()%>/club/addclub.jsp" target="main">新建俱乐部</a></li>
				<li><a href="<%=request.getContextPath()%>/InitDeleteClub.action" target="main">删除俱乐部</a></li>
				<li><a href="<%=request.getContextPath()%>/user/addsystemmanager.jsp" target="main">添加系统管理员</a></li>
				</ul>
		</c:if>
	</div>
</body>
</html>
