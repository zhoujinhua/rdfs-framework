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
    <title>数据字典管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<body>
	<script type="text/javascript">
		function operator(data, type, full, meta){
			var html = '';
    		html += ' <a class="link" href= "'+contextPath+'/job/detail?id='+data+'">查看</a>';
    		html += ' <a class="link" href= "'+contextPath+'/job/edit?id='+data+'">修改</a>';
    		html += ' <a class="link runOnce" data-id="'+data+'" href= "javascript:;">跑一次</a>';
	    	return	data=html;
		}
		$(document).delegate('#fn-btn-search','click',function() {
		    reload("data-table");	
	    });
	    $(document).delegate('#fn-btn-add','click',function() {
		    window.location.href = contextPath + '/view/quartz/add.jsp?juid='+juid;
	    });
	    $(document).delegate('.runOnce','click',function() {
	    	var id = $(this).attr("data-id");
	    	$.confirm("跑一次?",function(ok){
	    		if(ok){
	    			location.href = contextPath + '/job/run?id='+id;
	    		}
	    	});
	    });
	</script>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">系统管理</a>
	        </li>
	        <li>
	            <a href="#">定时任务管理</a>
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
			                        <label class="control-label col-sm-4">任务名称</label>
			                        <div class="col-sm-7">
		                       	 		<cs:input type="text" class="form-control" name="name" maxlength="20" renderId="data-table"/>
			                        </div>
		                        </div>
		                        <div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">任务类型</label>
			                         <div class="col-sm-7">
			                        	<cs:select class="form-control chosen" dicField="_quartz_type" name="type" allowBlank="true" renderId="data-table"/>
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
		           		<cs:table id="data-table" class="table table-striped table-bordered responsive" onrender="${path }/job/list">
		           			<cs:column dataField="name" name="任务名称"/>
		           			<cs:column dataField="type" name="任务类型" type="dict" format="_quartz_type"/>
		           			<cs:column dataField="status" name="任务状态" type="dict" format="_is"/>
		           			<cs:column dataField="cron" name="任务计划"/>
		           			<cs:column dataField="batch" name="是否允许并发" type="dict" format="_is"/>
		           			<cs:column dataField="ip" name="任务执行主机"/>
		           			<cs:column dataField="remark" name="备注"/>
		           			<cs:column dataField="id" name="操作" renderFn="operator"/>
		           		</cs:table>
		           </div>
		       </div>
	       </div>
       </div>
	</div>
</body>
</html>
