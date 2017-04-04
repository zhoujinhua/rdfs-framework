<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://javass.cn/common/" prefix="cs"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
String juid = request.getParameter("juid");
request.setAttribute("juid", juid);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>催收大平台-功能管理</title>
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
		$(document).ready(function(){
			$.ajax({
				  type: 'POST',
				  url: contextPath + "/menu/menuTree",
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
		function addMenu() {
			hideRMenu();
			var treeNode = zTree.getSelectedNodes()[0];
			if (treeNode) {
				if(treeNode.isParent){
					$("#menu-frame").attr("src", "${path}/view/menu/add.jsp?menuId="+treeNode.id+"&menuTitle="+treeNode.name+"&juid="+juid);
				} else {
					treeNode = treeNode.getParentNode();
					$("#menu-frame").attr("src", "${path}/view/menu/add.jsp?menuId="+treeNode.id+"&menuTitle="+treeNode.name+"&juid="+juid);
				}
			}
		}
		function editMenu() {
			hideRMenu();
			var treeNode = zTree.getSelectedNodes()[0];
			if (treeNode) {
				if(treeNode.isParent){
					$("#menu-frame").attr("src", "${path}/menu/edit?menuId="+treeNode.id+"&juid="+juid);
				} else {
					$("#menu-frame").attr("src", "${path}/resource/edit?itemId="+treeNode.id+"&juid="+juid);
				}
			}
		}
		function addResource() {
			hideRMenu();
			var treeNode = zTree.getSelectedNodes()[0];
			if (treeNode) {
				if(treeNode.isParent){
					$("#menu-frame").attr("src", "${path}/view/resource/add.jsp?menuId="+treeNode.id+"&menuTitle="+treeNode.name+"&juid="+juid);
				}else {
					treeNode = treeNode.getParentNode();
					$("#menu-frame").attr("src", "${path}/view/resource/add.jsp?menuId="+treeNode.id+"&menuTitle="+treeNode.name+"&juid="+juid);
				}
			}
		}
		function onClick(e, treeId, treeNode) {
			$("#menu-frame").attr("src", "${path}/view/menu/list.jsp?menuId="+treeNode.id+"&juid="+juid);
		}
	</script>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">系统管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/resource/list.jsp">功能管理</a>
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
			           <h2><i class="glyphicon glyphicon-th"></i> 功能模块树</h2>
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
           		<iframe id="menu-frame" src="${path }/view/menu/list.jsp?juid=${juid}" style="width: 100%;border:none;min-height:720px;" frameborder="0"></iframe>
	       </div>
       </div>
	</div>
	<div id="rMenu">
		<a href="#" class="list-group-item" onclick="addMenu();"><i class="glyphicon glyphicon-plus"></i>新增子菜单</a>
		<a href="#" class="list-group-item" onclick="addResource();"><i class="glyphicon glyphicon-plus"></i>新增功能</a>
		<a href="#" class="list-group-item" onclick="editMenu();"><i class="glyphicon glyphicon-edit"></i>修改</a>
	</div>
</body>
</html>
