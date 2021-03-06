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
<script type="text/javascript">
	$(function(){
		var id = "${process.id}";
	  	$("#fn-btn-save").click(function() {
	  		validate();
	  		if($(".error").length!=0){
	  			return false;
	  		}
            $("#fn-save-form").submit();
         });
	});
</script>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">流程管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/workflow/process/list.jsp">工作流管理</a>
	        </li>
	        <li>
	            <a href="#">流程维护</a>
	        </li>
	    </ul>
	    <div class="btn-bar">
	    	<a href="#" class="btn btn-primary btn-mini" id="fn-btn-save"><i class="glyphicon glyphicon-ok"></i> 保存</a>
	    </div>
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
		           		<form id="fn-save-form" class="form-horizontal" method="post" action="${path }/process/save">
		                    <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">流程名称<i class="glyphicon glyphicon-star red"></i></label>
		                        <div class="col-sm-8">
		                        	<input type="hidden" name="id" value="${process.id }">
	                       	 		<input type="text" class="form-control required" name="name" maxlength="30" value="${process.name}">
		                        </div>
		                     </div>
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">流程代码<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
	                       	 		<input type="text" class="form-control required" name="code" maxlength="30" value="${process.code}">
		                        </div>
		                    </div>
		                    <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">流程类型<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
	                       	 		<cs:select class="form-control required chosen" dicField="_app_type" name="type" allowBlank="true" value="${process.type }"/>
		                        </div>
		                    </div>
		                    <%--  <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">是否有效<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
		                         	<cs:select class="form-control required chosen" dicField="_is" name="status" allowBlank="true" value="${process.status }"/>
		                        </div>
		                    </div> --%>
		                </form>
	               	</div>
		      	</div>
		    </div>
		</div>
	</div>
</body>
</html>
