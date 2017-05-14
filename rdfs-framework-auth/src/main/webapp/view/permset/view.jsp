<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://javass.cn/common/" prefix="cs"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>催收大平台-权限集管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">系统管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/permset/list.jsp">权限集管理</a>
	        </li>
	        <li>
	            <a href="#">权限集查看</a>
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
			<div class="box col-md-12">
			   	<div class="box-inner">
		      	   <div class="box-header well" data-original-title="">
			           <h2><i class="glyphicon glyphicon-edit"></i> 表单元素</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<form id="fn-save-form" class="form-horizontal" method="post">
		                    <div class="form-group">
		                    	<div class=" col-sm-12 col-sm-8">
			                        <label class="control-label col-sm-4">权限集名称</label>
			                        <div class="col-sm-8">
			                        	<span class="info text-show form-control">${permSet.permName }</span>
			                        </div>
		                        </div>
		                     </div>
		                     <div class="form-group">
		                     	<div class=" col-sm-12 col-sm-8">
			                        <label class="control-label col-sm-4">是否有效</label>
			                         <div class="col-sm-8">
			                         	<cs:label class="form-control" value="${permSet.permStatus }" type="dict" format="_is"/>
			                        </div>
		                        </div>
		                    </div>
		                     <div class="form-group">
		                     	<div class=" col-sm-12 col-sm-8">
			                        <label class="control-label col-sm-4">权限集描述</label>
			                         <div class="col-sm-8">
			                         	<span class="info text-show form-control" style="height:auto">${permSet.permDesc }</span>
			                        </div>
		                        </div>
		                    </div>
		                </form>
	               	</div>
		      	</div>
		    </div>
		</div>
	</div>
</body>
</html>
