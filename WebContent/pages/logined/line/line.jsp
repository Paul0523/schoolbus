<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${baseUrl}/plugin/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${baseUrl}/plugin/easyui/themes/icon.css">
<script type="text/javascript">
	var baseUrl  = "${baseUrl}";
</script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=D0nfZPpsZvFtlgQMnpmMafzQFXF22QtV"></script>
<script type="text/javascript" src="${baseUrl}/plugin/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${baseUrl}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${baseUrl}/scripts/line/line.js"></script>
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
		<div data-options="region:'west',title:'线路信息',split:true"
			style="width: 200px;">
			<div id="p" class="easyui-panel"
				style="width: 100%; height: 100%; background: #fafafa;">
				<div style="background: #f1f1f1;">
					<a id="add_line" href="javascript:void(0)"
						class="easyui-linkbutton"
						data-options="iconCls:'icon-edit',plain:true">添加线路</a>
				</div>
				<ul id="line" class="easyui-tree" style="margin-top: 10px;"></ul>
			</div>
		</div>
		<div data-options="region:'center'"
			style="padding: 0px; background: #eee;">
			<div id="tabs" class="easyui-tabs" style="width: 100%; height: 100%;">
			</div>
		</div>
	</div>

	<div id="mm" class="easyui-menu" style="width: 120px;">
		<div id="removeLine">删除该节点</div>
	</div>
	<div id="win" style="height: 600px; padding: 30px 40px;">
		<form id="lineform" method="post">
			<div style="margin-bottom: 20px">
				<div>线路名:</div>
				<input class="" type="text"
					data-options="prompt:'请输入车牌号',required:true"
					style="width: 100%; height: 32px" name="line.name">
			</div>

			<div style="text-align: center;">
				<input type="reset" class="easyui-linkbutton" value="取消"
					style="width: 40%; heigth: 32px;"> <input id="send"
					type="button" class="easyui-linkbutton" value="提交"
					style="width: 40%; heigth: 32px;">
			</div>
			<input type="hidden" name="line.id">
		</form>
	</div>
</body>
</html>