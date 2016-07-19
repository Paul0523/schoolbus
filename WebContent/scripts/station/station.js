$(document).ready(function() {
	$('#dg').datagrid({
		url : baseUrl+'/station_stations'+param(),
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
            	    		url:baseUrl+"/station_removeStation?station.id="+id,
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
        	});
        	$('.modify').on('click',function(){
        		var id =  $(this).closest('tr').find("[field='id'] > div").text();
        		var name =  $(this).closest('tr').find("[field='name'] > div").text();
        		var longitude =  $(this).closest('tr').find("[field='longitude'] > div").text();
        		var latitude =  $(this).closest('tr').find("[field='latitude'] > div").text();
        		$("[name='station.id']").val(id);
        		$("[name='station.name']").val(name);
        		$("[name='station.name']").attr({readonly:"readonly",disabled:true});
        		$("[name='station.longitude']").val(longitude);
        		$("[name='station.latitude']").val(latitude);
        		$('#win').window({
        			title : '修改站点'
        		});
        		$('#win').window('open');
        	});
        	$(".viewMap").on("click",function(){
        		$("#tb").css({display:"none"});
        		$("#showMap").css({display:"block"});
        		var name = $(this).closest("tr").find("[field='name'] div").text();
        		var longitude = $(this).closest("tr").find("[field='longitude'] div").text();
        		var latitude = $(this).closest("tr").find("[field='latitude'] div").text();
        		var point = new BMap.Point(longitude,latitude);
        		var opts = {    
    				width : 250,     // 信息窗口宽度    
    				height: 100,     // 信息窗口高度    
    				title : "站点信息：",  // 信息窗口标题   
    				offset:new BMap.Size(0,-20)
        		}; 
        		var infoWindow = new BMap.InfoWindow("当前站点是："+name,opts);
        		var marker = new BMap.Marker(point);
        		map.clearOverlays();
        		setTimeout(function(){
        				map.centerAndZoom(point,16);
	        			map.addOverlay(marker);
	        			map.openInfoWindow(infoWindow, point);
        			},
        			600
        		);
        		marker.addEventListener("click",function(){
        			map.openInfoWindow(infoWindow, point);
        		});
        	});
        },
		method : "get",
		toolbar : [ {
			iconCls : 'icon-add',
			text : '新增',
			handler : function() {
				$("[name='station.id']").val('-1');
				$("[name='station.name']").val('');
				$("[name='station.longitude']").val('');
				$("[name='station.latitude']").val('');
        		$("[name='station.name']").removeAttr('readonly');
        		$("[name='station.name']").removeAttr('disabled');
        		$('#win').window({
        			title : '新增站点'
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
			width : '150px',
			align : 'center',
			formatter : function(value, rowData, rowIndex) {  
				if(rowData.state == 1){
					return "<a href='#' class='viewMap'>地图查看</a>&nbsp;&nbsp;<a href='#' class='modify'>修改</a>";
				}
                return "<a href='#' class='viewMap'>地图查看</a>&nbsp;&nbsp;<a href='#' class='delete'>删除</a>&nbsp;&nbsp;<a href='#' class='modify'>修改</a>" 
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
	
	$('#stationform').form({
	    url:baseUrl+'/station_updateStation',
	    onSubmit: function(){
			// do some check
			// return false to prevent submit;
	    	var name = $("[name='station.name']").val();
			var longitude = $("[name='station.longitude']").val();
			var latitude = $("[name='station.latitude']").val();
			if(name == ""){
				alert("车站名不能为空！");
				return false;
			}else if(longitude == ""){
				alert("经度不能为空！");
				return false;
			}else if(latitude == ""){
				alert("纬度不能为空！");
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
		$('#stationform').submit();
	});
	
	$("#back_to_table").on("click",function(){
		$("#tb").css({display:"block"});
		$("#showMap").css({display:"none"});
	});
	
	function param(){
		var state = getUrlParam("state");
		if(state != null){
			return "?station.state="+state;
		}else{
			return "";
		}
	}
	function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }
	
});