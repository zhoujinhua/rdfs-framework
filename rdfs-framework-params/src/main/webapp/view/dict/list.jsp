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
    <title>催收大平台-数据字典管理</title>
    <jsp:include page="../common/head.jsp"></jsp:include>
</head>
<body>
	<script type="text/javascript">
		function operator(data, type, full, meta){
			var html = '';
			if(full.group == '2'){
	    		html += ' <a class="link" href= "'+contextPath+'/dict/edit?id='+data+'">修改</a>';
			}
	    	return	data=html;
		}
		$(document).delegate('#fn-btn-search','click',function() {
		    reload("data-table");	
	    });
	    $(document).delegate('#fn-btn-add','click',function() {
		    window.location.href = contextPath + '/view/dict/add.jsp?juid='+juid;
	    });
	</script>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">系统管理</a>
	        </li>
	        <li>
	            <a href="#">数据字典管理</a>
	        </li>
	    </ul>
	    <div class="btn-bar">
	    	<a href="#" class="btn btn-primary btn-mini" id="fn-btn-search"><i class="glyphicon glyphicon-search"></i> 查询</a>
            <a href="#" class="btn btn-default btn-mini" id="fn-btn-add"><i class="glyphicon glyphicon-plus"></i> 新增</a>
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
			           <h2><i class="glyphicon glyphicon-edit"></i> 查询条件</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<form id="fn-search-form" class="form-horizontal" method="post">
		                    <div class="form-group">
		                    	<div class="col-sm-12">
			                        <label class="control-label col-sm-1">字典名称</label>
			                        <div class="col-sm-3">
		                       	 		<cs:input type="text" class="form-control" name="name" maxlength="30" renderId="data-table"/>
			                        </div>
			                        <label class="control-label col-sm-1">字典KEY</label>
			                        <div class="col-sm-3">
		                       	 		<cs:input type="text" class="form-control" name="key" maxlength="30" renderId="data-table"/>
			                        </div>
			                        <label class="control-label col-sm-1">对照码</label>
			                         <div class="col-sm-3">
			                        	<cs:input type="text" class="form-control" name="code" maxlength="20" renderId="data-table"/>
			                        </div>
			                    </div>
			                </div>
			                <div class="form-group">
		                    	<div class="col-sm-12">
			                        <label class="control-label col-sm-1">对照值</label>
			                         <div class="col-sm-3">
			                        	<cs:input type="text" class="form-control" name="desc" maxlength="30" renderId="data-table"/>
			                        </div>
			                        <label class="control-label col-sm-1">是否有效</label>
			                         <div class="col-sm-3">
			                         	<cs:select class="form-control chosen" dicField="_is" name="status" allowBlank="true" renderId="data-table"/>
			                        </div>
			                        <label class="control-label col-sm-1">参数类型</label>
			                         <div class="col-sm-3">
			                         	<cs:select class="form-control chosen" dicField="_param_group" name="group" allowBlank="true" renderId="data-table"/>
			                        </div>
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
		           		<cs:table id="data-table" class="table table-striped table-bordered responsive" onrender="${path }/dict/list">
		           			<cs:column dataField="name" name="字典名称"/>
		           			<cs:column dataField="key" name="字典KEY"/>
		           			<cs:column dataField="code" name="对照码"/>
		           			<cs:column dataField="desc" name="对照值"/>
		           			<cs:column dataField="status" name="是否启用" type="dict" format="_is"/>
		           			<cs:column dataField="group" name="所属分组" type="dict" format="_param_group"/>
		           			<cs:column dataField="sortNo" name="排序号"/>
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
