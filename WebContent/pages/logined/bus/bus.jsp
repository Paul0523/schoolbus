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
	var baseUrl = "${baseUrl}";
</script>
<script type="text/javascript" src="${baseUrl}/plugin/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${baseUrl}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=D0nfZPpsZvFtlgQMnpmMafzQFXF22QtV"></script>
<script src="${baseUrl}/scripts/bus/bus.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	points = [];
	tempInfos = [];
	map = new BMap.Map("map");
	map.centerAndZoom("武汉",14);
	map.disableDoubleClickZoom();
	map.addControl(new BMap.NavigationControl());
	map.enableScrollWheelZoom();
});

function removeMarker(e,ee,marker){
	var id = marker.info.id;
	$.get(baseUrl+"/lineinfo_removeLineInfoById?lineInfo.id="+id,function(data){
		console.log(data.msg);
		drawLine();
	});
}

function drawLine(){
	map.clearOverlays();
	points = [];
	markersAndAddresses = {};
	$.get(baseUrl+"/lineinfo_lineInfo?lineInfo.lineId=18&lineInfo.orientation=0",function(data){
		var length = data.length;
		for(var i = 0;i < length;i++){
			function drawMarker(){
				var longitude = data[i].longitude;
				var latitude = data[i].latitude;
				var point = new BMap.Point(longitude,latitude);
				var marker = new BMap.Marker(point);
				marker.info = data[i];
				marker.addEventListener("click",function(){
					tempInfos = [];
					tempInfos.push(marker.info);
					console.log(marker.info.longitude);
				});
				marker.enableDragging();
				marker.addEventListener("dragend",function(e){
					var point = e.point;
					var info = this.info;
					$.get(baseUrl+"/lineinfo_updateLineInfoGPSById?lineInfo.id="
							+info.id+"&lineInfo.longitude="+point.lng+"&lineInfo.latitude="+point.lat,function(data){
						console.log(data.msg);
						drawLine();
					});
				});
				var menuItem = new BMap.MenuItem("删除该点",removeMarker.bind(marker)); 
				var contextMenu = new BMap.ContextMenu();
				contextMenu.addItem(menuItem);
				marker.addContextMenu(contextMenu);
//				map.addOverlay(marker);
				points.push(point);
			}
			drawMarker();
		}
		var polyline = new BMap.Polyline(points,{strokeColor:"red", strokeWeight:6, strokeOpacity:0.5});
		map.addOverlay(polyline);
	});
}
</script>
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
</head>
<body>
	<div id="tb" style="width:100%;height:100%;">
		<table id="dg" style="width: 100%; height: 100%;">
		</table>
	</div>
	<div id="showMap" style="display:none">
		<div style="margin:5px;">
			<a id="back_to_table" class="easyui-linkbutton" style="width:100px;">返回</a>
		</div>
		<div id="map" style="height:500px; margin-top:10px;"></div>
	</div>
	<div id="win" style="height: 600px; padding: 30px 60px;">
		<form id="busform" method="post">
			<div style="margin-bottom: 20px">
				<div>车牌号:</div>
				<input class="" type="text"
					data-options="prompt:'请输入车牌号',required:true"
					style="width: 100%; height: 32px" name="bus.name">
			</div>
			<div style="margin-bottom: 20px">
				<div>线路名:</div>
				<select class="lineName" data-options="prompt:'请选择线路'"
					style="width: 100%; height: 32px" name="bus.lineId">
				</select>
			</div>
			<div style="margin-bottom: 20px">
				<div>司机:</div>
				<select class="driver" data-options="prompt:'请选择司机'"
					style="width: 100%; height: 32px" name="bus.userId">
				</select>
			</div>

			<div style="text-align: center;">
				<input type="reset" class="easyui-linkbutton" value="取消"
					style="width: 40%; heigth: 32px;"> <input id="send"
					type="button" class="easyui-linkbutton" value="提交"
					style="width: 40%; heigth: 32px;">
			</div>
			<input type="hidden" name="bus.id">
		</form>
	</div>
</body>
</html>