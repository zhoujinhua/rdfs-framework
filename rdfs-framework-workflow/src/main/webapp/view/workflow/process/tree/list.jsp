<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://javass.cn/common/" prefix="cs"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
String id = request.getParameter("id");
if(id!=null && !"".equals(id)){
	request.setAttribute("id", id);
}
String juid = request.getParameter("juid");
request.setAttribute("juid", juid);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>流程管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
   	<style type="text/css">
		div#rMenu {position:absolute; visibility:hidden; top:0; text-align: left;padding:4px;}  
	    div#rMenu a{  
	        padding: 3px 15px 3px 15px;  
	        background-color:#cad4e6;  
	        vertical-align:middle;  
	    }  
	</style>
</head>
<body>
	<script type="text/javascript">
		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback : {
				onClick : onClick,
				onRightClick: OnRightClick
			}
		};
		var zTree, rMenu;
		var id = "${id}";
		$(document).ready(function(){
			$.ajax({
				  type: 'POST',
				  url: contextPath + "/process/nodeTree?id=${id}",
				  success: function(data){
					  $.fn.zTree.init($("#ztree"), setting, eval(data));
					  zTree = $.fn.zTree.getZTreeObj("ztree");
				  },
				  dataType: 'text'
			});
			rMenu = $("#rMenu");
		});
		function OnRightClick(event, treeId, treeNode) {
			if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
				zTree.cancelSelectedNode();
				//showRMenu("root", event.clientX, event.clientY);
			} else if (treeNode && !treeNode.noR) {
				zTree.selectNode(treeNode);
				showRMenu("node", event.clientX, event.clientY);
			}
		}
		function showRMenu(type, x, y) {
			$("#rMenu ul").show();
			if (type=="root") {
				$("#m_del").hide();
			} else {
				$("#m_del").show();
			}
			rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

			$("body").bind("mousedown", onBodyMouseDown);
		}
		function hideRMenu() {
			if (rMenu) rMenu.css({"visibility": "hidden"});
			$("body").unbind("mousedown", onBodyMouseDown);
		}
		function onBodyMouseDown(event){
			if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
				rMenu.css({"visibility" : "hidden"});
			}
		}
		function add() {
			hideRMenu();
			var treeNode = zTree.getSelectedNodes()[0];
			if (treeNode) {
				if(!treeNode.getParentNode()){
					$("#process-frame").attr("src", "${path}/view/workflow/node/add.jsp?id="+treeNode.id+"&juid="+juid);
				}
				var nodeId = treeNode.id;
				var nodeName = treeNode.name;
				if(nodeId.indexOf("node")!=-1){
					nodeId = nodeId.substring(5, nodeId.length);
					$("#process-frame").attr("src", "${path}/view/workflow/event/add.jsp?id="+nodeId+"&name="+nodeName+"&juid="+juid);
				}
				if(nodeId.indexOf("event")!=-1){
					var parentNode = treeNode.getParentNode();
					nodeId = parentNode.id;
					if(nodeId.indexOf("node")!=-1){
						nodeId = nodeId.substring(5, nodeId.length);
						$("#process-frame").attr("src", "${path}/view/workflow/event/add.jsp?id="+nodeId+"&name="+nodeName+"&juid="+juid);
					}
				}
			}
		}
		function edit() {
			hideRMenu();
			var treeNode = zTree.getSelectedNodes()[0];
			if (treeNode) {
				if(!treeNode.getParentNode()){
					$("#process-frame").attr("src", "${path}/process/edit?id="+treeNode.id+"&juid="+juid);
				}
				var nodeId = treeNode.id;
				if(nodeId.indexOf("node")!=-1){
					nodeId = nodeId.substring(5, nodeId.length);
					$("#process-frame").attr("src", "${path}/node/edit?id="+nodeId+"&juid="+juid);
				}
				if(nodeId.indexOf("event")!=-1){
					nodeId = nodeId.substring(6, nodeId.length);
					$("#process-frame").attr("src", "${path}/event/edit?id="+nodeId+"&juid="+juid);
				}
			}
		}
		function remove() {
			hideRMenu();
			var treeNode = zTree.getSelectedNodes()[0];
			if (treeNode) {
				if(!treeNode.getParentNode()){
					$.alert("禁止删除根节点.");
				}
				var nodeId = treeNode.id;
				if(nodeId.indexOf("node")!=-1){
					nodeId = nodeId.substring(5, nodeId.length);
					var parId = treeNode.getParentNode().id;
					parId = parId.substring(5, parId.length);
					$.confirm("删除节点将同步删除节点下事件，确定吗?", function(ok){
						if(ok){
							$.ajax({
								  type: 'POST',
								  url: '${pageContext.request.contextPath}/node/delete?id='+nodeId,
								  success: function(data){
									  if(data.responseCode == 1){
										  $.alert("删除节点成功!");
										  crudNode("delete");
										  $("#process-frame").attr("src", "${path }/view/workflow/node/list.jsp?id="+parId+"&juid="+juid);
									  } else {
										  $.alert(data.responseMsg);
									  }
								  },
								  dataType: 'json'
							});
						}
					});
				}
				if(nodeId.indexOf("event")!=-1){
					nodeId = nodeId.substring(6, nodeId.length);
					var parId = treeNode.getParentNode().id;
					parId = parId.substring(5, parId.length);
					$.confirm("将删除该节点事件，确定吗?", function(ok){
						if(ok){
							$.ajax({
								  type: 'POST',
								  url: '${pageContext.request.contextPath}/event/delete?id='+nodeId,
								  success: function(data){
									  if(data.responseCode == 1){
										  $.alert("删除节点事件成功!");
										  crudNode("delete");
										  $("#process-frame").attr("src", "${path }/view/workflow/event/list.jsp?id="+parId+"&juid="+juid);
									  } else {
										  $.alert(data.responseMsg);
									  }
								  },
								  dataType: 'json'
							});
						}
					});
				}
			}
		}
		function onClick(e, treeId, treeNode) {
			if(!treeNode.getParentNode()){
				$("#process-frame").attr("src", "${path }/view/workflow/node/list.jsp?id="+treeNode.id+"&juid="+juid);
			}
			var nodeId = treeNode.id;
			if(nodeId.indexOf("node")!=-1){
				nodeId = nodeId.substring(5, nodeId.length);
				$("#process-frame").attr("src", "${path}/view/workflow/event/list.jsp?id="+nodeId+"&juid="+juid);
			}
			if(nodeId.indexOf("event")!=-1){
				nodeId = nodeId.substring(6, nodeId.length);
				$("#process-frame").attr("src", "${path}/event/detail?id="+nodeId+"&juid="+juid);
			}
		}
		function crudNode(method, node){
			var treeNode = zTree.getSelectedNodes()[0];
			if(!treeNode.getParentNode()){
				if(method == "add"){
					zTree.addNodes(treeNode, node, true);
				}
				if(method == "edit"){
					treeNode.name = node.name;
					zTree.updateNode(treeNode);
				}
				if(method == "delete"){
					zTree.removeNode(treeNode);
				}
			}
			var nodeId = treeNode.id;
			if(nodeId.indexOf("node")!=-1){
				if(method == "add"){
					zTree.addNodes(treeNode, node, true);
				}
				if(method == "edit"){
					treeNode.name = node.name;
					zTree.updateNode(treeNode);
				}
				if(method == "delete"){
					zTree.removeNode(treeNode);
				}
			}
			if(nodeId.indexOf("event")!=-1){
				if(method == "add"){
					zTree.addNodes(treeNode.getParentNode(), node);
				}
				if(method == "edit"){
					treeNode.name = node.name;
					zTree.updateNode(treeNode);
				}
				if(method == "delete"){
					zTree.removeNode(treeNode);
				}
			}
		}
	</script>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">流程管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/workflow/process/list.jsp">流程管理</a>
	        </li>
	    </ul>
		<div class="row">
			<c:if test="${msg!=null && msg!=''}">
				<div class="col-md-12">
					<div class="alert alert-info alert-dismissible col-md-12" role="alert">
					  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					  ${msg }
					</div>
				</div>
			</c:if>
		</div>
	    <div class="row">
	    	<div class="box col-md-2">
	    		<div class="box-inner">
		      	   <div class="box-header well" data-original-title="">
			           <h2><i class="glyphicon glyphicon-th"></i> 流程节点树</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
			    		<div class="ztree" id="ztree" style="min-height:667px;"></div>
		           </div>
		        </div>
	    	</div>
	    	<div class="box col-md-10">
           		<iframe id="process-frame" src="${path }/view/workflow/node/list.jsp?id=${id }&juid=${juid}" style="width: 100%;border:none;min-height:720px;" frameborder="0"></iframe>
	       </div>
       </div>
	</div>
	<div id="rMenu">
		<a href="#" class="list-group-item" onclick="add();"><i class="glyphicon glyphicon-plus"></i>新增</a>
		<a href="#" class="list-group-item" onclick="edit();"><i class="glyphicon glyphicon-edit"></i>修改</a>
		<a href="#" class="list-group-item" onclick="remove();"><i class="glyphicon glyphicon-remove"></i>删除</a>
	</div>
</body>
</html>
