
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
<title>更改密码</title>
</head>
<body class="simple_body">
	<div class="content">
		<div class="header">
			<div class="stats">
				<p class="stat">
					<!--span class="number"></span-->
				</p>
			</div>
			<h1 class="page-title">更改密码</h1>
		</div>
		<ul class="breadcrumb">
			<li><a href="exchangePsw.jsp">更改密码 </a> <span class="divider">/</span></li>
			<li class="active">更改密码</li>
		</ul>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="bb-alert alert alert-info" style="display: none;">
					<span>操作成功</span>
				</div>
				<!-- START 以上内容不需更改，保证该TPL页内的标签匹配即可 -->
				<div class="well">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#home" data-toggle="tab">更改密码</a></li>
					</ul>

					<div id="myTabContent" class="tab-content">
						<div class="tab-pane active in" id="home">
						<span id="changepsw" style="color:red"></span>

							<form autocomplete="off">
								<label>输入旧密码</label>
								<input type="password" name="password" value="" class="input-xlarge" id="password" data-rule="required;" />
								<label>输入新密码</label>
								<input type="password" class="input-xlarge" id="password1" name="passwrod1" data-rule="required;password;"/>
								<label>确认新密码</label>
								<input type="password" class="input-xlarge" id="password2" onblur="checkpassword();"/>
								<span id="checkdiv"></span>
								<div class="btn-toolbar" id="btnsubmit1">	
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