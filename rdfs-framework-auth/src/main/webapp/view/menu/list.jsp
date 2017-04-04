<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://javass.cn/common/" prefix="cs"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
String menuId = request.getParameter("menuId");
request.setAttribute("menuId", menuId);
String juid = request.getParameter("juid");
if(juid!=null && !juid.equals("")){
	request.setAttribute("juid", juid);
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>催收大平台-功能管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<body>
		<div class="box col-md-12">
			<div class="box-inner">
	      	   <div class="box-header well" data-original-title="">
		           <h2><i class="glyphicon glyphicon-th"></i> 菜单列表</h2>
		           <div class="box-icon">
		               <a href="#" class="btn btn-minimize btn-round btn-default"><i
		                       class="glyphicon glyphicon-chevron-up"></i></a>
		           </div>
		       </div>
	           <div class="box-content">
					<cs:table id="menu-table" class="table table-striped table-bordered responsive" onrender="${path }/menu/list?menuId=${menuId }&juid=${juid }">
						<cs:column dataField="menuTitle" name="模块名称"/>
						<cs:column dataField="status" name="是否有效" type="dict" format="_is"/>
						<cs:column dataField="parMenu.menuTitle" name="上级菜单"/>
						<cs:column dataField="sortNo" name="排序号"/>
						<cs:column dataField="menuIcon" name="图标"/>
						<cs:column dataField="createTime" name="创建时间" type="date" format="YYYY-MM-DD"/>
					</cs:table>
				</div>
			</div>
		</div>
		<div class="box col-md-12">
			<div class="box-inner">
	      	   <div class="box-header well" data-original-title="">
		           <h2><i class="glyphicon glyphicon-th"></i> 功能列表</h2>
		           <div class="box-icon">
		               <a href="#" class="btn btn-minimize btn-round btn-default"><i
		                       class="glyphicon glyphicon-chevron-up"></i></a>
		           </div>
		       </div>
	           <div class="box-content">
					<cs:table id="resource-table" class="table table-striped table-bordered responsive" onrender="${path }/resource/list?menu.menuId=${menuId }&juid=${juid }">
						<cs:column dataField="itemTitle" name="模块名称"/>
						<cs:column dataField="status" name="是否有效" type="dict" format="_is"/>
						<cs:column dataField="itemLocation" name="请求地址"/>
						<cs:column dataField="menu.menuTitle" name="上级菜单"/>
						<cs:column dataField="sortNo" name="排序号"/>
						<cs:column dataField="itemIcon" name="图标"/>
						<cs:column dataField="createTime" name="创建时间" type="date" format="YYYY-MM-DD"/>
					</cs:table>
				</div>
			</div>
		</div>
</body>
</html>
