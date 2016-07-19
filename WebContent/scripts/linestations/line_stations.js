$(document).ready(function(){
	$('#dg').datagrid({
		url : baseUrl+'/linestations_lineStations?orientation=0&line.id='+getUrlParam('line.id'),
		rownumbers : true,
		pagination : true,
		striped : true,
		singleSelect : false,
		selectOnCheck : false,
		checkOnSelect : false,
		onClickRow: function (rowIndex, rowData) { 
            $(this).datagrid('unselectRow', rowIndex); 
        },
        onLoadSuccess : function(){
        	$('.delete').on('click',function(){
        		var id =  $(this).closest('tr').find("[field='id'] > div").text();
        		var orientation = $('input[name="orientation"]:checked ').val();
        		$.messager.confirm('删除','确认删除这条记录吗？',function(r){
            	    if (r){
            	    	$.ajax({
            	    		url:baseUrl+"/linestations_removeLineStations?orientation="+orientation+"&curStationId="+id+"&lineId="+getUrlParam('line.id'),
            	    		success : function(data){
            	    			if(data.success == true){
            	    				$.messager.alert("成功","删除成功！");
            	    				$('#dg').datagrid('reload');
            	    			}else{
            	    				$.messager.alert("失败","删除失败，请稍后重试！");
            	    			}
            	    		}
            	    	})
            	    }
            	});
        		$('#dg').datagrid('reload');
        	});
        	
        	$("#radio0").on("click",function(){
        		$('#dg').datagrid({
        			url : baseUrl+'/linestations_lineStations?orientation=0&line.id='+getUrlParam('line.id'),
        		});
        		$("#radio0").attr("checked","checked");
        	});
        	$("#radio1").on("click",function(){
        		$('#dg').datagrid({
        			url : baseUrl+'/linestations_lineStations?orientation=1&line.id='+getUrlParam('line.id'),
        		});
        		$("#radio1").attr("checked","checked");
        	});
        	
        	$(".viewMap").on("click",function(){
        		$("#tb").css({display:"none"});
        		$("#showMap").css({display:"block"});
        	});
        },
		method : "get",
		toolbar : [ {
			iconCls : 'icon-add',
			text : '新增站点',
			handler : function() {
				var orientation = $('input[name="orientation"]:checked ').val();
        		$("#ori").val(orientation);
				$(".curStations").html("<option value='-1' selected='selected'>空</option>");
				$(".addStations").html("");
				$("[name='lineId']").val(getUrlParam('line.id'))
				$.ajax({
        			url : baseUrl+'/linestations_lineStations?orientation='+orientation+'&line.id='+getUrlParam('line.id'),
        			success : function(data){
        				var rows = data.rows;
        				for(var i=0;i<rows.length;i++){
        					$(".curStations").append("<option value='"+rows[i].id+"'>"+rows[i].name+"</option>");
        				}
        			}	
        		});
        		$.ajax({
        			url : baseUrl+'/station_stations',
        			success : function(data){
        				var rows = data.rows;
        				for(var i=0;i<rows.length;i++){
        					$(".addStations").append("<option value='"+rows[i].id+"'>"+rows[i].name+"</option>");
        				}
        			}	
        		});
        		
        		$('#win').window({
        			title : '新增'+orientation+"站点"
        		});
        		$('#win').window('open');
			}
		}, {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				alert('help');
			}
		}, '-', {
			text : "<label><input id='radio0' value='0' type='radio' name='orientation' checked='checked'/>0方向</label>  &nbsp;"
		}, {
			text : "<label><input id='radio1' value='1' type='radio' name='orientation'/>1方向</label>"
		}, '-',{
			text : "地图查看",
			handler:function(){
				$("#tb").css({display:"none"});
        		$("#showMap").css({display:"block"});
        		var lineId = getUrlParam('line.id');
        		var orientation = $('input[name="orientation"]:checked ').val();
        		$.get(baseUrl+"/linestations_lineStationsForList?line.id="+lineId+"&orientation="+orientation,function(data){
        			var length = data.length;
        			var i = 0;
        			var points = []
        			var opts = {
    					width : 250,     // 信息窗口宽度    
    					height: 100,     // 信息窗口高度    
    					title : "站点",  // 信息窗口标题	
    					offset:new BMap.Size(0,-20)
        			};
        			for(i;i<length;i++){
        				function drawMarker(){
        					var longitude = data[i].longitude;
            				var latitude = data[i].latitude;
            				var point = new BMap.Point(longitude,latitude);
            				var theIcon = new BMap.Icon(baseUrl+"/images/marker_blue.png", new BMap.Size(31,
            						29), {
            					offset : new BMap.Size(10, 25)
            				});
            				var marker = new BMap.Marker(point,{
            					icon : theIcon
            				});
            				var infoWindow = new BMap.InfoWindow(data[i].name,opts);
            				marker.addEventListener("click",function(){
            					map.openInfoWindow(infoWindow, point);
            				});
            				points.push(point);
            				map.addOverlay(marker);
        				};
        				drawMarker();
        			}
        			var polyline = new BMap.Polyline(points,{strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});  
        			map.centerAndZoom(points[0],15);
        			map.addOverlay(polyline);
        		});
        		
				$("#tb").css({display:"none"});
        		$("#showMap").css({display:"block"});
				drawLine(lineId,orientation);
				setTimeout(function(){map.setCenter(points[0])},1000);
			}
		}],
		columns : [ [ {
			field : 'ck',
			checkbox : true
		},{
			field : 'id',
			title : 'id',
			hidden : true
		},{
			field : 'state',
			title : '使用状态',
			width : '100px',
			align : 'center',
			formatter : function(value, rowData, rowIndex) {  
                if (value == 1) {  
                    return  "使用中";  
                }else{
                	return  "未使用";
                }  
            }
		}, {
			field : 'name',
			title : '车站名',
			width : '100px',
			align : 'center'
		}, {
			field : 'longitude',
			title : '经度',
			width : '100px',
			align : 'center'
		}, {
			field : 'latitude',
			title : '纬度',
			width : '100px',
			align : 'center'
		},{
			field : 'operate',
			title : '操作',
			width : '100px',
			align : 'center',
			formatter : function(value, rowData, rowIndex) {  
                return "<a href='#' class='delete easyui-linkbutton'>删除</a>" 
            }
		} ] ]
	});

	$('#win').window({
		width : 500,
		title:"新增站点",
		height : 300,
		closed : true,
		collapsible : false,
		modal : true
	});
	
	$('#linestationsform').form({
	    url:baseUrl+'/linestations_updateLineStations',
	    onSubmit: function(){
			// do some check
			// return false to prevent submit;
	    	
	    },
	    success:function(data){
			var obj = eval('(' + data + ')');
			var msg = obj.msg;
	    	$.messager.confirm('提交结果',msg,function(r){
	    	    if (r){
	    	    	$('#win').window('close');
	    	    	$('#dg').datagrid('reload');
	    	    }
	    	});
	    }
	});
	
	$("#send").on('click',function(){
		$('#linestationsform').submit();
	});
	
	$("#back_to_table").on("click",function(){
		$("#tb").css({display:"block"});
		$("#showMap").css({display:"none"});
		if(typeof interval != "undefined"){
			window.clearInterval(interval);
		}
	});
	
	
});


function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	if (r != null) return unescape(r[2]); return null; //返回参数值
}

function drawStation(lineId,orientation){
	$.get(baseUrl+"/linestations_lineStationsForList?line.id="+lineId+"&orientation="+orientation,function(data){
		var length = data.length;
		var i = 0;
		var points = []
		var opts = {
			width : 250,     // 信息窗口宽度    
			height: 100,     // 信息窗口高度    
			title : "站点",  // 信息窗口标题	
			offset:new BMap.Size(0,-20)
		};
		for(i;i<length;i++){
			function drawMarker(){
				var longitude = data[i].longitude;
				var latitude = data[i].latitude;
				var point = new BMap.Point(longitude,latitude);
				var theIcon = new BMap.Icon(baseUrl+"/images/marker_blue.png", new BMap.Size(31,
						29), {
					offset : new BMap.Size(10, 25)
				});
				var marker = new BMap.Marker(point,{
					icon : theIcon
				});
				var infoWindow = new BMap.InfoWindow(data[i].name,opts);
				marker.addEventListener("click",function(){
					map.openInfoWindow(infoWindow, point);
				});
				points.push(point);
				map.addOverlay(marker);
			};
			drawMarker();
		}
		var polyline = new BMap.Polyline(points,{strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});  
		map.addOverlay(polyline);
	});
}