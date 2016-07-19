<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我是一个公交车哦</title>
<link rel="stylesheet" type="text/css"
	href="${baseUrl}/plugin/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${baseUrl}/plugin/easyui/themes/icon.css">
<script type="text/javascript" src="${baseUrl}/plugin/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${baseUrl}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
var baseUrl = "${baseUrl}"
$(document).ready(function(){
	var testGPS = [[114.341242,30.517451],[114.342895,30.517004],[114.343366,30.516884],[114.343582,30.516908],[114.345805,30.518439],[114.346668,30.519162],[114.348244,30.519571],[114.35045,30.519353],[114.351613,30.519104],
	               [114.352385,30.52304],[114.352709,30.525085],[114.353643,30.526761],[114.355022,30.527959],[114.357012,30.528067],[114.358224,30.52806]];
	time = 0;
	tag = true;
	$('#busform').form({
	    url:baseUrl+'/user_login',
	    onSubmit: function(){
	    },
	    success:function(data){
	    	var obj = eval("("+data+")");
	    	var code = obj.code;
	    	if(code == 1){
		    	$("#busform").hide();
		    	$("#login_state").show();
		    	$("#username").text(obj.user.username);
		    	$("#busId").text(obj.user.busId);
		    	getGPS();
	    	}
	    }
	});
	

	$("#logout").on("click",function(){
		$.get(baseUrl+"/user_logout?user.account=Paul",function(data){
			if(data.code == 1){
				$("#busform").show();
		    	$("#login_state").hide();
			}
		});
	});
	
	$("#start").on("click",function(){
		var busId = $("#busId").text();
		$.get(baseUrl+"/driver_start?driver.longitude="+testGPS[time][0]+"&driver.latitude="+testGPS[time][1]+"&driver.busId="+busId,function(data){
			if(data.code == 1){
				alert(data.msg);
				
				if(typeof tt != "undefined"){
					window.clearInterval(tt)
				}
				tt = setInterval(updateTime,5000);
			}
		});
	});
	
	function getGPS(){
		var i = 0;
		var length = testGPS.length
		for(i;i<length;i++){
			var text = "<div>经度："+testGPS[i][0] + "纬度：" +testGPS[i][1] + "</div>";
			$("#testPoint").append(text);
		}
	}
	
	$("#stopTime").on("click",function(){
		var busId = $("#busId").text();
		if(typeof tt != 'undefined'){
			window.clearInterval(tt);
		}
		$.get(baseUrl+"/driver_stop?driver.busId="+busId,function(data){
			alert(data.msg);
		});
	});

	function updateTime(){
		if(time < testGPS.length && time >-1){
			var busId = $("#busId").text();
			var text = "当前车辆位置是："+testGPS[time][0]+","+testGPS[time][1];
			$.get(baseUrl+"/driver_updateGPS?driver.busId="+busId+"&driver.longitude="+testGPS[time][0]+"&driver.latitude="+testGPS[time][1],function(data){
				if(data.code == 1){
					$("#result").text(data.msg);
				}				
			});		
		}else{
			text = "车辆已到达终点站";
			window.clearInterval(tt);
			if(time == testGPS.length){
				tag = false;
			}else{
				tag = true;
			}
		}
		
		if(tag){
			time += 1;
		}else{
			time -= 1;
		}
		
		$("#time").text(text+"  当前运行次数是："+time+"   标志位是："+tag+"车辆坐标长度是："+testGPS.length);
		
	}
});
</script>
</head>
<body>
	<form id="busform" method="post">
		<div style="margin-bottom: 20px">
			<div>账号:</div>
			<input class="" type="text"
				data-options="prompt:'请输入车牌号',required:true"
				style="width: 100%; height: 32px" name="user.account">
		</div>
		<div style="margin-bottom: 20px">
			<div>密码:</div>
			<input class="" type="text"
				data-options="prompt:'请输入车牌号',required:true"
				style="width: 100%; height: 32px" name="user.password">
		</div>
		<div style="text-align: center;">
			<input type="reset" class="easyui-linkbutton" value="取消"
				style="width: 40%; heigth: 32px;"> <input id="send"
				type="submit" class="easyui-linkbutton" value="提交"
				style="width: 40%; heigth: 32px;">
		</div>
	</form>
	<div id="login_state" style="display:none">
		<span id="username" style="color:blue;"></span>登陆成功<button id="logout">退出</button><br>
		车辆Id是：<span id="busId"></span>&nbsp;<button id="start">发车</button>&nbsp;<button id="stopTime">停车</button><br>
		运行状态：<span id="time"></span><br>
		站点信息：<span id="result"></span><br><br>
		测试点gps数据:
		<div id="testPoint"></div>
	</div>
	
</body>
</html>