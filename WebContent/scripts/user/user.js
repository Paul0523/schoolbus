$(document).ready(function() {
	$('#dg').datagrid({
		url : baseUrl+'/user_users'+param(),
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
            	    		url:baseUrl+"/user_removeUser?user.id="+id,
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
        		var account =  $(this).closest('tr').find("[field='account'] > div").text();
        		var username =  $(this).closest('tr').find("[field='username'] > div").text();
        		var email =  $(this).closest('tr').find("[field='email'] > div").text();
        		var phone =  $(this).closest('tr').find("[field='phone'] > div").text();
        		$("[name='checkpassword']").val('');
        		$("[name='user.password']").val('');
        		$("[name='user.id']").val(id);
        		$("[name='user.account']").val(account);
        		$("[name='user.username']").val(username);
        		$("[name='user.email']").val(email);
        		$("[name='user.phone']").val(phone);
        		$("[name='user.account']").attr({readonly:"readonly",disabled:true});
        		$('#win').window({
        			title : '修改用户'
        		});
        		$('#win').window('open');
        	});
        	$('.ppp').on('click',function(){
        		var record = $(this).closest('tr').data('row');
        		var id = record.id;
        		alert(id);
        	});
        },
		method : "get",
		toolbar : [ {
			iconCls : 'icon-add',
			text : '新增',
			handler : function() {
				$("[name='user.id']").val('');
				$("[name='user.account']").val('');
        		$("[name='user.username']").val('');
        		$("[name='user.email']").val('');
        		$("[name='user.phone']").val('');
        		$("[name='user.password']").val('');
        		$("[name='checkpassword']").val('');
        		$("[name='user.account']").removeAttr('readonly');
        		$("[name='user.account']").removeAttr('disabled');
        		$('#win').window({
        			title : '新增用户'
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
			text : "账号：<input type='text' id='account'/> 用户名：<input type='text' id='username'/>"
		},{
			text : "查询",
			handler : function() {
				var username = $("#username").val();
				var account = $("#account").val();
				var str = '';
				if(account != ''){
					str += "?user.account="+account;
					if(username != ''){
						str += "&user.username="+username;
					}
				}else{
					if(username != ''){
						str += "?user.username="+username;
					}
				}
				$('#dg').datagrid({url:'user_users'+str});
			}
		}],
		columns : [ [ {
			field : 'ck',
			checkbox : true
		},{
			field : 'id',
			title : 'id',
			hidden : true
		}, {
			field : 'userType',
			title : '类型',
			width : '100px',
			align : 'center',
			formatter : function(value, rowData, rowIndex) {  
                if (value == 1) {  
                    return  "管理员";  
                }else if(value == 2){
                	return "司机"
                }else if(value == 3){
                	return "用户";
                }  
            }
		}, {
			field : 'account',
			title : '账号',
			width : '100px',
			align : 'center'
		}, {
			field : 'username',
			title : '用户名',
			width : '100px',
			align : 'center'
		}, {
			field : 'state',
			title : '登陆状态',
			width : '100px',
			align : 'center',
			formatter : function(value, rowData, rowIndex) {  
                if (value == 0) {  
                    return  "未登录";  
                }else if(value == 1){
                	return "已登录"
                } 
            }
		},{
			field : 'email',
			title : '邮箱',
			width : '180px',
			align : 'center'
		}, {
			field : 'phone',
			title : '手机',
			width : '100px',
			align : 'center'
		},{
			field : 'busName',
			title : '车辆名称',
			width : '100px',
			align : 'center'
		},{
			field : 'operate',
			title : '操作',
			width : '100px',
			align : 'center',
			formatter : function(value, rowData, rowIndex) {  
				console.log(rowData.username+"****"+rowData.busId);
				if(rowData.busId != undefined){
					return "<a href='#' class='modify'>修改</a>";
				}
                return "<a href='#' class='delete'>删除</a>&nbsp;&nbsp;<a href='#' class='modify'>修改</a>" 
            }
		} ] ]
	});

	$('#win').window({
		width : 600,
		title:"新增用户",
		height : 550,
		closed : true,
		collapsible : false,
		modal : true
	});
	
	$('#userform').form({
	    url:baseUrl+'/user_updateUser',
	    onSubmit: function(){
			// do some check
			// return false to prevent submit;
			var account = $("[name='user.account']").val();
    		var userName = $("[name='user.username']").val();
    		var password = $("[name='user.password']").val();
    		var checkPassword = $("[name='checkpassword']").val();
    		if(account == ""){
    			alert("账户不能为空");
    			return false;
    		}else if(userName == ""){
    			alert("用户名不能为空");
    			return false;
    		}else if(password == ""){
    			alert("密码不能为空");
    			return false;
    		}else if(password != checkPassword){
    			alert("两次密码不一致");
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
		$('#userform').submit();
	});
	
	function param(){
		var userType = getUrlParam("user.userType");
		if(userType != null){
			return "?user.userType="+userType;
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