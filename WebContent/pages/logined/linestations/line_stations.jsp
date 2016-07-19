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
<script src="${baseUrl}/scripts/linestations/line_stations.js"></script>
<script type="text/javascript">
var baseUrl = "${baseUrl}";
$(document).ready(function(){
	points = [];
	tempInfos = [];
	map = new BMap.Map("map");
	map.centerAndZoom("武汉",14);
	map.disableDoubleClickZoom();
	map.addControl(new BMap.NavigationControl());
	map.enableScrollWheelZoom();
	map.addEventListener("dblclick",function(e){
		var length = tempInfos.length;
		if(length == 1){
			var point = e.point;
			var tempInfo = tempInfos[0];
			var lineId = tempInfo.lineId;
			var longitude = point.lng;
			var latitude = point.lat;
			var orientation = tempInfo.orientation;
			var priority = parseInt(tempInfo.priority) + 1;
			$.get(baseUrl+"/lineinfo_saveLineInfo?lineInfo.lineId="+lineId+"&lineInfo.orientation="
					+orientation+"&lineInfo.longitude="+longitude+"&lineInfo.latitude="+latitude+"&lineInfo.priority="
					+priority,function(data){
				drawLine(lineId,orientation);
				drawStation(lineId,orientation);
			});
		}else{
			alert("未选择前一个标注");
		}
		tempInfos = [];
	});
});

function removeMarker(e,ee,marker){
	var id = marker.info.id;
	$.get(baseUrl+"/lineinfo_removeLineInfoById?lineInfo.id="+id,function(data){
		console.log(data.msg);
		var lineId = getUrlParam('line.id');
		var orientation = $('input[name="orientation"]:checked ').val();
		drawLine(lineId,orientation);
		drawStation(lineId,orientation);
	});
}

function drawLine(lineId,orientation){
	map.clearOverlays();
	points = [];
	markersAndAddresses = {};
	$.get(baseUrl+"/lineinfo_lineInfo?lineInfo.lineId="+lineId+"&lineInfo.orientation="+orientation,function(data){
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
						drawLine(lineId,orientation);
						drawStation(lineId,orientation);
					});
				});
				var menuItem = new BMap.MenuItem("删除该点",removeMarker.bind(marker)); 
				var contextMenu = new BMap.ContextMenu();
				contextMenu.addItem(menuItem);
				marker.addContextMenu(contextMenu);
				map.addOverlay(marker);
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
	<div id="tb" style="width:100%; height:100%;">
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
		<form id="linestationsform" method="post">
			<div style="margin-bottom: 20px">
				<div>在哪个站点之前添加（不选默认添加到最后）:</div>
				<select class="curStations" data-options="prompt:'请选择现有站点'"
					style="width: 100%; height: 32px" name="curStationId">
					<option value="-1" selected="selected">空</option>
				</select>
			</div>
			<div style="margin-bottom: 20px">
				<div>新增站点:</div>
				<select class="addStations" data-options="prompt:'请选择新增站点'"
					style="width: 100%; height: 32px" name="newStationId">
				</select>
			</div>

			<div style="text-align: center;">
				<input type="reset" class="easyui-linkbutton" value="取消"
					style="width: 40%; heigth: 32px;"> <input id="send"
					type="button" class="easyui-linkbutton" value="提交"
					style="width: 40%; heigth: 32px;">
			</div>
			<input type="hidden" name="lineId">
			<input type="hidden" name="orientation" id="ori">
		</form>
	</div>
</body>
</html>