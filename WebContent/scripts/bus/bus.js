$(document).ready(function() {
	$('#dg').datagrid({
		url : baseUrl+'/bus_buses'+param(),
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
        		$.messager.confirm('删除','确认删除这条记录吗？',function(r){
            	    if (r){
            	    	$.ajax({
            	    		url:baseUrl+"/bus_removeBus?bus.id="+id,
            	    		success : function(data){
            	    			if(data.success == true){
            	    				$.messager.alert("成功","删除成功！"+data);
            	    				$('#dg').datagrid('reload');
            	    			}else{
            	    				$.messager.alert("失败","删除失败，请稍后重试！");
            	    			}
            	    		}
            	    	})
            	    }
            	});
        	});
        	$('.modify').on('click',function(){
        		var id =  $(this).closest('tr').find("[field='id'] > div").text();
        		var name =  $(this).closest('tr').find("[field='name'] > div").text();
        		var userId =  $(this).closest('tr').find("[field='userId'] > div").text();
        		var userName =  $(this).closest('tr').find("[field='userName'] > div").text();
        		var lineId =  $(this).closest('tr').find("[field='lineId'] > div").text();
        		var lineName =  $(this).closest('tr').find("[field='lineName'] > div").text();
        		$("[name='bus.id']").val(id);
        		$("[name='bus.name']").val(name);
        		$("[name='bus.name']").attr({readonly:"readonly",disabled:true});
        		$(".driver").html("");
        		$(".lineName").html("");
        		if(userId != ""){
        			$(".driver").html("<option value='"+userId+"' selected='selected'>"+userName+"</option>");
        			$(".driver").append("<option value=''>空</option>");
        		}else{
        			$(".driver").append("<option value='' selected='selected'>空</option>");
        		}
        		if(lineId != ""){
        			$(".lineName").html("<option value='"+lineId+"' selected='selected'>"+lineName+"</option>");
        			$(".lineName").append("<option value=''>空</option>");
        		}else{
        			$(".lineName").append("<option value='' selected='selected'>空</option>");
        		}
        		
        		$.ajax({
        			url : baseUrl+'/user_users',
        			success : function(data){
        				var rows = data.rows;
        				var mo = $(".driver").find("[selected='selected']").val();
        				for(var i=0;i<rows.length;i++){
        					if(mo != rows[i].id && 2 == rows[i].userType){
        						if(rows[i].busId == "" || rows[i].busId == undefined){
            						$(".driver").append("<option value='"+rows[i].id+"'>"+rows[i].username+"</option>");
            					}
        					}
        				}
        				console.log("成功成功啦！！！"+rows[0].account+"*****"+rows.length + "****" +mo);
        			}	
        		});
        		$.ajax({
        			url : baseUrl+'/line_lines',
        			success : function(data){
        				var rows = data;
        				var mo = $(".lineName").find("[selected='selected']").val();
        				for(var i=0;i<rows.length;i++){
        					if(mo != rows[i].id){
        						$(".lineName").append("<option value='"+rows[i].id+"'>"+rows[i].text+"</option>");
        					}
        				}
        				console.log("成功成功啦！！！"+rows[0].account+"*****"+rows.length + "****" +mo);
        			}	
        		});
        		$('#win').window({
        			title : '修改车辆信息'
        		});
        		$('#win').window('open');
        	});
        	
        	$(".viewMap").on("click",function(){
        		$("#tb").css({display:"none"});
        		$("#showMap").css({display:"block"});
        		$.get(baseUrl+"/linestations_lineStationsForList?line.id=18&orientation=0",function(data){
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
            				var theIcon = new BMap.Icon(baseUrl+"/images/marker_blue.png", new BMap.Size(20,
            						25), {
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
        			var polyline = new BMap.Polyline(points,{strokeColor:"red", strokeWeight:6, strokeOpacity:0.5});  
        			map.centerAndZoom(points[0],15);
        		});
        		refreshGPS($(this));
        		interval = setInterval(_refreshGPS($(this)),5000);
        		drawLine();
        	});
        },
		method : "get",
		toolbar : [ {
			iconCls : 'icon-add',
			text : '新增',
			handler : function() {
				$("[name='bus.id']").val('-1');
				$("[name='bus.name']").val('');
        		$("[name='bus.name']").removeAttr('readonly');
        		$("[name='bus.name']").removeAttr('disabled');
        		$(".lineName").html("");
        		$(".driver").html("");
        		$.ajax({
        			url : baseUrl+'/user_users',
        			success : function(data){
        				var rows = data.rows;
        				$(".driver").append("<option value='' selected='selected'>空</option>");
        				for(var i=0;i<rows.length;i++){
        					if(2 == rows[i].userType){
        						if(rows[i].busId == "" || rows[i].busId == undefined){
        							$(".driver").append("<option value='"+rows[i].id+"'>"+rows[i].username+"</option>");
        						}
        					}
        				}
        			}	
        		});
        		$.ajax({
        			url : baseUrl+'/line_lines',
        			success : function(data){
        				var rows = data;
        				$(".lineName").append("<option value='' selected='selected'>空</option>")
        				for(var i=0;i<rows.length;i++){
        					$(".lineName").append("<option value='"+rows[i].id+"'>"+rows[i].text+"</option>");
        				}
        			}	
        		});
        		$('#win').window({
        			title : '新增车辆'
        		});
        		$('#win').window('open');
			}
		}, {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				alert('help');
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
			field : 'userId',
			title : 'userId',
			hidden : true
		},{
			field : 'lineId',
			title : 'lineId',
			hidden : true
		}, {
			field : 'state',
			title : '运行状态',
			width : '100px',
			align : 'center',
			formatter : function(value, rowData, rowIndex) {  
                if (value == 1) {  
                    return  "正在运行";  
                }else if(value == 0){
                	return  "未运行";
                }else if(value == 2){
                	return "闲置中";
                }  
            }
		}, {
			field : 'name',
			title : '车牌号',
			width : '100px',
			align : 'center'
		}, {
			field : 'lineName',
			title : '线路名',
			width : '100px',
			align : 'center'
		}, {
			field : 'userName',
			title : '司机',
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
				if(rowData.state == 1){
					return "<a href='#' class='viewMap'>地图查看</a>";
				}else{
					return "<a href='#' class='delete'>删除</a>&nbsp;&nbsp;<a href='#' class='modify'>修改</a>" 
				}
            }
		} ] ]
	});

	$('#win').window({
		width : 600,
		title:"新增站点",
		height : 550,
		closed : true,
		collapsible : false,
		modal : true
	});
	
	$('#busform').form({
	    url:baseUrl+'/bus_updateBus',
	    onSubmit: function(){
			// do some check
			// return false to prevent submit;
	    	var name = $("[name='bus.name']").val();
	    	if(name == ""){
	    		alert("车牌号不能为空");
	    		return false;
	    	}
	    },
	    success:function(data){
	    	$.messager.confirm('成功','提交成功！！',function(r){
	    	    if (r){
	    	    	$('#win').window('close');
	    	    	$('#dg').datagrid('reload');
	    	    }
	    	});
	    }
	});
	
	$("#send").on('click',function(){
		$("[name='user.account']").removeAttr('readonly');
		$("[name='user.account']").removeAttr('disabled');
		$('#busform').submit();
	});
	
	$("#back_to_table").on("click",function(){
		$("#tb").css({display:"block"});
		$("#showMap").css({display:"none"});
		if(typeof interval != "undefined"){
			window.clearInterval(interval);
		}
	});
	
	
	function param(){
		var state = getUrlParam("state");
		if(state != null){
			return "?bus.state="+state;
		}else{
			return "";
		}
	}
	function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }

	function _refreshGPS(btn){
		return function(){
			refreshGPS(btn);
		}
	}
	
	function refreshGPS(btn){
		var id = btn.closest("tr").find("[field='id'] div").text();
		$.get(baseUrl+"/bus_busById?bus.id="+id,function(data){
			var name = data.name;
			var longitude = data.longitude;
			var latitude = data.latitude;
			var nextStationId = data.nextStationId;
			var text;
			if(nextStationId != -1){
				text = "车辆运行中，现在的车辆位置是："+longitude+"，"+latitude;
			}else{
				text = "车辆已到达终点站";
			}
			var point = new BMap.Point(longitude,latitude);
			var opts = {    
				width : 250,     // 信息窗口宽度    
				height: 100,     // 信息窗口高度    
				title : "车辆位置：",  // 信息窗口标题   
				offset:new BMap.Size(0,-20)
			}; 
			var infoWindow = new BMap.InfoWindow(text,opts);
			if(typeof busMarker != "undefined"){
				map.removeOverlay(busMarker);
			}
			busMarker = new BMap.Marker(point);
			setTimeout(function(){
	    			map.addOverlay(busMarker);
//	    			map.openInfoWindow(infoWindow, point);
				},
				600
			);
			busMarker.addEventListener("click",function(){
				map.openInfoWindow(infoWindow, point);
			});
		});
		
	}
	
//	setInterval(function(){$("#dg").datagrid("reload")},3000);
});