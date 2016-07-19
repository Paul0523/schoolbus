$(document).ready(function() {
	
	$('#lineform').form({
	    url:baseUrl+'/line_updateLine',
	    onSubmit: function(){
			// do some check
			// return false to prevent submit;
	    	var name = $("[name='line.name']").val();
	    	if(name == ""){
	    		alert("线路名不能为空！");
	    		return false;
	    	}
	    },
	    success:function(data){
	    	$('#win').window('close');
	    	$.messager.alert("成功","添加成功");
	    	$('#line').tree('reload');
	    	$('#dg').datagrid('reload');
	    }
	});
	
	$("#send").on('click',function(){
		$('#lineform').submit();
	});
	
	$('#line').tree({
		url : baseUrl+"/line_lines",
		onClick: function(node){
			var id = node.id;
			var url = baseUrl+"/pages/logined/linestations/line_stations.jsp?line.id="+id;
			var title = node.text;
			addTab(title,url);
		},
		onContextMenu: function(e, node){
			e.preventDefault();
			// select the node
			$('#tt').tree('select', node.target);
			// display context menu
			$('#mm').menu({
				onClick: function(item){
					$.get(baseUrl+"/line_removeLine?line.id="+node.id,{},function(ret){
						$.messager.alert("删除成功","删除成功");
						$("#line").tree("reload");
					});
				}
			});
			$('#mm').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
		}
	});
	
	function addTab(title, url){
		if ($('#tabs').tabs('exists', title)){
			$('#tabs').tabs('select', title);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			$('#tabs').tabs('add',{
				title:title,
				content:content,
				closable:true,
				bodyCls:'hell'
			});
		}
	}
	
	$('#win').window({
		width : 400,
		title:"新增线路",
		height : 200,
		closed : true,
		collapsible : false,
		modal : true
	});
	
	 $("#bus li:eq(0)").find("div").addClass("tree-node-selected"); 
	 $("#add_line").on("click",function(){
		 $("[name='line.name']").val('');
		 $('#win').window('open');
	 });
	 
	 window.parent.collapseWest();
});