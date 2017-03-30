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
    <title>工作流管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<body>
	<script type="text/javascript">
		function operator(data, type, full, meta){
			var html = '';
    		html += ' <a class="link" href= "'+contextPath+'/process/edit?id='+data+'">修改</a>';
    		html += ' <a class="link copy" href= "javascript:;" data-id="'+data+'">复制</a>';
    		html += ' <a class="link" href= "'+contextPath+'/process/addVersion?id='+data+'">更新版本</a>';
    		html += ' <a class="link" href= "'+contextPath+'/view/workflow/process/tree/list.jsp?id='+data+'">节点管理</a>';
	    	return	data=html;
		}
		$(document).delegate('#fn-btn-search', 'click', function() {
		    reload("data-table");
	    });
		$(document).delegate('.copy','click',function() {
			$.confirm("复制流程将同步复制流程下的节点和节点关联的事件，确定吗?", function(ok){
				if(ok){
					
				}
			});
	    });
	    $(document).delegate('#fn-btn-add', 'click', function() {
		    window.location.href = contextPath + '/view/workflow/process/add.jsp?juid='+juid;
	    });
	</script>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">流程管理</a>
	        </li>
	        <li>
	            <a href="#">工作流管理</a>
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
			           <h2><i class="glyphicon glyphicon-edit"></i> 查询条件</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<form id="fn-search-form" class="form-horizontal" method="post">
		                    <div class="form-group">
		                    	<div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">流程名称</label>
			                        <div class="col-sm-7">
		                       	 		<cs:input type="text" class="form-control" name="name" maxlength="20" renderId="data-table"/>
			                        </div>
		                        </div>
		                        <div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">流程代码</label>
			                         <div class="col-sm-7">
			                        	<cs:input type="text" class="form-control" name="code" maxlength="20" renderId="data-table"/>
			                        </div>
		                        </div>
		                    </div>
		                     <div class="form-group">
		                     	 <div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">是否有效</label>
			                         <div class="col-sm-7">
			                         	<cs:select class="form-control chosen" dicField="_is" name="status" allowBlank="true" renderId="data-table"/>
			                        </div>
		                        </div>
		                     </div>
		                     <div class="form-group">
			                     <div class="col-sm-12">
				                    <p class="center-block">
				                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-search"><i class="glyphicon glyphicon-search"></i> 查询</a>
				                        <a href="#" class="btn btn-default btn-mini" id="fn-btn-add"><i class="glyphicon glyphicon-plus"></i> 新增</a>
				                    </p>
				                </div>
			                </div>
		                </form>
	               	</div>
		      	</div>
		    </div>
		</div>
	    <div class="row">
	    	<div class="box col-md-12">
		      	<div class="box-inner">
		      	   <div class="box-header well" data-original-title="">
			           <h2><i class="glyphicon glyphicon-th"></i> 查询结果</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<cs:table id="data-table" class="table table-striped table-bordered responsive" onrender="${path }/process/list">
		           			<cs:column dataField="name" name="流程名称"/>
		           			<cs:column dataField="code" name="流程代码"/>
		           			<cs:column dataField="version" name="流程版本"/>
		           			<cs:column dataField="status" name="流程状态" type="dict" format="_is"/>
		           			<cs:column dataField="createTime" name="创建时间" type="date" format="YYYY-MM-DD"/>
		           			<cs:column dataField="id" name="操作" renderFn="operator"/>
		           		</cs:table>
		           </div>
		       </div>
	       </div>
       </div>
	</div>
</body>
</html>
