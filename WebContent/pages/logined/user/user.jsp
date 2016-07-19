<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
* {
	margin: 0;
	padding: 0
}

body, html {
	width: 100%;
	height: 100%
}
</style>
<link rel="stylesheet" type="text/css"
	href="${baseUrl}/plugin/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${baseUrl}/plugin/easyui/themes/icon.css">
<script type="text/javascript">
	var baseUrl = "${baseUrl}";
</script>
<script type="text/javascript" src="${baseUrl}/plugin/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${baseUrl}/plugin/easyui/jquery.easyui.min.js"></script>
<script src="${baseUrl}/scripts/user/user.js"></script>
</head>
<body>
	<table id="dg" style="width: 100%; height: 100%;">
	</table>
	<div id="win" style="height: 600px; padding: 30px 60px;">
		<form id="userform" method="post">
			<div style="margin-bottom: 20px">
				<div>账号:</div>
				<input class=""
					data-options="prompt:'请输入账号',required:true"
					style="width: 100%; height: 32px" name="user.account">
			</div>
			<div style="margin-bottom: 20px">
				<div>用户名:</div>
				<input class="" data-options="prompt:'请输入用户名'"
					style="width: 100%; height: 32px" name="user.username">
			</div>
			<div style="margin-bottom: 20px">
				<div>用户类型:</div>
				<select class=""  style="width: 100%; height: 32px" name="user.userType">
					<option value="1">管理员</option>
					<option value="2">司机</option>
				</select>
			</div>
			<div style="margin-bottom: 20px">
				<div>密码:</div>
				<input class="" data-options="required:true"
					type="password" style="width: 100%; height: 32px"
					name="user.password">
			</div>
			<div style="margin-bottom: 20px">
				<div>确认密码:</div>
				<input class="" data-options="required:true"
					type="password" style="width: 100%; height: 32px"
					name="checkpassword">
			</div>
			<div style="margin-bottom: 20px">
				<div>Email:</div>
				<input class=""
					data-options="prompt:'请输入邮箱',validType:'email'"
					style="width: 100%; height: 32px" name="user.email">
			</div>
			<div style="margin-bottom: 20px">
				<div>手机号:</div>
				<input class="" style="width: 100%; height: 32px"
					name="user.phone" value="15071335527">
			</div>
			<div style="text-align: center;">
				<input type="reset" class="easyui-linkbutton" value="取消" style="width: 40%; heigth: 32px;"> 
				<input id="send" type="button" class="easyui-linkbutton" value="提交" style="width: 40%; heigth: 32px;">
			</div>
			<input type="hidden" name="user.id">
		</form>
	</div>
</body>
</html>