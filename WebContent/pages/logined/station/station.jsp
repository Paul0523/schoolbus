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
<script type="text/javascript" src="${baseUrl}/plugin/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${baseUrl}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=D0nfZPpsZvFtlgQMnpmMafzQFXF22QtV"></script>

<style type="text/css">
* {
	padding: 0;
	margin: 0;
}

html, body {
	width: 100%;
	height: 100%;
}
</style>
<script type="text/javascript">
var baseUrl = "${baseUrl}";
$(document).ready(function(){
	map = new BMap.Map("map");
	map.centerAndZoom("武汉",14);
	map.disableDoubleClickZoom();
	map.addControl(new BMap.NavigationControl());
	map.enableScrollWheelZoom();
});
</script>
<script src="${baseUrl}/scripts/station/station.js"></script>

</head>
<body>
	<div id="tb" style="width:100%; height:100%; display:block">
		<table id="dg" style="width: 100%; height: 100%; display:none;">
		</table>
	</div>
	<div id="showMap" style="display:none">
		<div style="margin:5px;">
			<a id="back_to_table" class="easyui-linkbutton" style="width:100px;">返回</a>
		</div>
		<div id="map" style="height:500px; margin-top:10px;"></div>
	</div>
	<div id="win" style="height: 600px; padding: 30px 60px;">
		<form id="stationform" method="post">
			<div style="margin-bottom: 20px">
				<div>车站名:</div>
				<input class="" type="text"
					data-options="prompt:'请输入车牌号',required:true"
					style="width: 100%; height: 32px" name="station.name">
			</div>
			<div style="margin-bottom: 20px">
				<div>经度:</div>
				<input type="text" class="lineName" data-options="prompt:'请选择线路'"
					style="width: 100%; height: 32px" name="station.longitude">
			</div>
			<div style="margin-bottom: 20px">
				<div>纬度:</div>
				<input type="text" class="driver" data-options="prompt:'请选择司机'"
					style="width: 100%; height: 32px" name="station.latitude">
			</div>

			<div style="text-align: center;">
				<input type="reset" class="easyui-linkbutton" value="取消"
					style="width: 40%; heigth: 32px;"> <input id="send"
					type="button" class="easyui-linkbutton" value="提交"
					style="width: 40%; heigth: 32px;">
			</div>
			<input type="hidden" name="station.id">
		</form>
	</div>
</body>
</html>