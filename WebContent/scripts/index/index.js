$(document).ready(function() {
	$('#user').tree({
		onClick: function(node){
			var url = node.attributes.url;
			var title = node.attributes.title;
			addTab(title,url);
		}
	});
	$('#station').tree({
		onClick: function(node){
			var url = node.attributes.url;
			var title = node.attributes.title;
			addTab(title,url);
		}
	});
	$('#bus').tree({
		onClick: function(node){
			var url = node.attributes.url;
			var title = node.attributes.title;
			addTab(title,url);
		}
	});
	$('#line').tree({
		onClick: function(node){
			var url = node.attributes.url;
			var title = node.attributes.title;
			addTab(title,url);
		}
	});
	
	$("#exit").on("click",function(){
		var r=confirm("确定退出吗?");
		if(r == true){
			window.location.href = "/SchoolBus/index.jsp";
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
	
	addTab('全部车辆',baseUrl+'/pages/logined/bus/bus.jsp');
	 $("#bus li:eq(0)").find("div").addClass("tree-node-selected"); 
});