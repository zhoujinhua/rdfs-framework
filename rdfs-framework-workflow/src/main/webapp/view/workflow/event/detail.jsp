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
    <title>工作流节点管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<body>
	<div class="box col-md-12">
	   	<div class="box-inner">
      	   <div class="box-header well" data-original-title="">
	           <h2><i class="glyphicon glyphicon-edit"></i> 节点事件查看</h2>
	           <div class="box-icon">
	               <a href="#" class="btn btn-minimize btn-round btn-default"><i
	                       class="glyphicon glyphicon-chevron-up"></i></a>
	           </div>
	       </div>
           <div class="box-content">
           		<div class="form-horizontal">
                    <div class="form-group">
                    	<div class=" col-sm-12 col-sm-8">
	                        <label class="control-label col-sm-4">事件名称</label>
	                        <div class="col-sm-8">
	                      	 	<cs:label class="form-control" value="${event.name}"/>
	                        </div>
                        </div>
                     </div>
                    <div class="form-group">
                    	<div class=" col-sm-12 col-sm-8">
	                        <label class="control-label col-sm-4">当前节点</label>
	                        <div class="col-sm-8">
	                      	 		<cs:label class="form-control" value="${event.currNode.name }"/>
	                        </div>
                        </div>
                     </div>
                     <div class="form-group">
                     	<div class=" col-sm-12 col-sm-8">
	                        <label class="control-label col-sm-4">执行动作</label>
	                         <div class="col-sm-8">
	                      	 		<cs:label class="form-control" value="${event.action }" type="dict" format="_node_action"/>
	                        </div>
                        </div>
                    </div>
                     <div class="form-group">
                     	<div class=" col-sm-12 col-sm-8">
	                        <label class="control-label col-sm-4">下一节点</label>
	                         <div class="col-sm-8">
	                      	 		<cs:label class="form-control" value="${event.nextNode.name }"/>
	                        </div>
                        </div>
                    </div>
                     <div class="form-group">
                     	<div class=" col-sm-12 col-sm-8">
	                        <label class="control-label col-sm-4">下一节点状态码</label>
	                         <div class="col-sm-8">
	                     	 		<cs:label class="form-control" value="${event.route}"/>
	                        </div>
                        </div>
                    </div>
                     <div class="form-group">
                     	<div class=" col-sm-12 col-sm-8">
	                        <label class="control-label col-sm-4">分配组</label>
	                         <div class="col-sm-8">
	                         	<cs:label class="form-control" value="${event.group }"/>
	                        </div>
                        </div>
                    </div>
                     <div class="form-group">
                     	<div class=" col-sm-12 col-sm-8">
	                        <label class="control-label col-sm-4">分配用户</label>
	                         <div class="col-sm-8">
	                         	<cs:label class="form-control" value="${event.user }"/>
	                        </div>
                        </div>
                    </div>
                </div>
           	</div>
      	</div>
    </div>
</body>
</html>
