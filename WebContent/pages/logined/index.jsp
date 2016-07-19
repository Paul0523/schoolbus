<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>校园公交管理平台</title>
<link rel="stylesheet" type="text/css"
	href="${baseUrl}/plugin/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${baseUrl}/plugin/easyui/themes/icon.css">
<script type="text/javascript">
	var baseUrl = "${baseUrl}";
</script>
<script type="text/javascript" src="${baseUrl}/plugin/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${baseUrl}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${baseUrl}/scripts/index/index.js"></script>
<script type="text/javascript">
function collapseWest(){
	$("#cc").layout('collapse','west');  
}
</script>
<style>
* {
	padding: 0;
	margin: 0;
}

html {
	height: 100%;
}

body {
	height: 100%;
}

.hell {
	overflow: hidden;
}
</style>
</head>
<body>
	<div id="cc" class="easyui-layout" style="width: 100%; height: 100%;">
		<div data-options="region:'north'" style="width: 100%; height:50px;background:#e7f0ff; line-height:50px; overflow:hidden;position:relative;">
			<h1>欢迎使用校园公交系统</h1>
			<div style="position:absolute; top:0px; right:20px;">
				用户名：<span>${userName}</span>&nbsp;&nbsp;
				<a href="#" id="exit">退出</a>
			</div>
		</div>
		<div id="west" data-options="region:'west',title:'导航',split:true"
			style="width: 230px;">
			<div id="aa" class="easyui-accordion"
				style="width: 100%; height: 100%;">
				<div title="车辆信息" data-options="selected:true" style="padding: 10px;">
					<ul id="bus" class="easyui-tree">
						<li data-options="attributes:{'url':'${baseUrl}/pages/logined/bus/bus.jsp','title':'全部车辆'},checked:true"><span>全部车辆</span>
						<li data-options="attributes:{'url':'${baseUrl}/pages/logined/bus/bus.jsp?state=1','title':'正在运行车辆'}"><span>正在运行车辆</span></li>
						<li data-options="attributes:{'url':'${baseUrl}/pages/logined/bus/bus.jsp?state=0','title':'未运行车辆'}"><span>未运行车辆</span></li>
						<li data-options="attributes:{'url':'${baseUrl}/pages/logined/bus/bus.jsp?state=2','title':'闲置车辆'}"><span>闲置车辆</span></li>
					</ul>
				</div>
				<div title="车站信息">
					<ul id="station" class="easyui-tree" style="padding: 10px;">
						<li
							data-options="attributes:{'url':'${baseUrl}/pages/logined/station/station.jsp','title':'全部车站信息'}"><span>全部车站信息</span></li>
						<li data-options="attributes:{'url':'${baseUrl}/pages/logined/station/station.jsp?state=1','title':'在使用车站'}"><span>在使用车站</span></li>
						<li data-options="attributes:{'url':'${baseUrl}/pages/logined/station/station.jsp?state=0','title':'未使用车站'}"><span>未使用车站</span></li>
					</ul>
				</div>
				<div title="线路信息" data-options="">
					<ul id="line" class="easyui-tree" style="padding: 10px;">
						<li
							data-options="attributes:{'url':'${baseUrl}/pages/logined/line/line.jsp','title':'线路信息'}"><span>线路信息</span></li>
					</ul>
				</div>
				<div title="用户信息" data-options=""
					style="padding: 10px;">
					<ul id="user" class="easyui-tree">
						<li
							data-options="attributes:{'url':'${baseUrl}/pages/logined/user/user.jsp','title':'全部用户信息'}"><span>全部用户信息</span></li>
						<li data-options="attributes:{'url':'${baseUrl}/pages/logined/user/user.jsp?user.userType=2','title':'司机端'}"><span>司机端</span></li>
						<li data-options="attributes:{'url':'${baseUrl}/pages/logined/user/user.jsp?user.userType=1','title':'管理员'}"><span>管理员</span></li>
						<li data-options="attributes:{'url':'${baseUrl}/pages/logined/user/user.jsp?user.userType=3','title':'用户'}"><span>用户</span></li>
					</ul>

				</div>
			</div>
		</div>
		<div data-options="region:'center'"
			style="padding: 0px; background: #eee;">
			<div id="tabs" class="easyui-tabs" style="width: 100%; height: 100%;">
			</div>
		</div>
	</div>
</body>
</html>