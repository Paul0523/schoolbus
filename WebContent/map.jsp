<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="plugin/jquery/jquery-1.12.1.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=D0nfZPpsZvFtlgQMnpmMafzQFXF22QtV"></script>
<script>
$(document).ready(function(){
	var map = new BMap.Map("map");  
	points = [];
	tempInfos = [];
	map.centerAndZoom("武汉", 15);
	map.enableScrollWheelZoom();
	map.disableDoubleClickZoom()
	map.addControl(new BMap.NavigationControl());
	
	$("#show").on("click",function(){
		drawLine();
		setTimeout(function(){map.setCenter(points[0])},1000);
	});
	
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
			$.get("lineinfo_saveLineInfo?lineInfo.lineId="+lineId+"&lineInfo.orientation="
					+orientation+"&lineInfo.longitude="+longitude+"&lineInfo.latitude="+latitude+"&lineInfo.priority="
					+priority,function(data){
				drawLine();
			});
		}else{
			alert("未选择前一个标注");
		}
		tempInfos = [];
	});
	
	function drawLine(){
		map.clearOverlays();
		points = [];
		markersAndAddresses = {};
		$.get("lineinfo_lineInfo?lineInfo.lineId=18&lineInfo.orientation=0",function(data){
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
						$.get("lineinfo_updateLineInfoGPSById?lineInfo.id="
								+info.id+"&lineInfo.longitude="+point.lng+"&lineInfo.latitude="+point.lat,function(data){
							console.log(data.msg);
							drawLine();
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
	
	function removeMarker(e,ee,marker){
		var id = marker.info.id;
		$.get("lineinfo_removeLineInfoById?lineInfo.id="+id,function(data){
			console.log(data.msg);
			drawLine();
		});
	}
});
</script>
</head>
<body>
	<div><button id="show">显示</button></div>
	<div id="map" style="height:500px; background:#f1f1f1;"></div>
</body>
</html>