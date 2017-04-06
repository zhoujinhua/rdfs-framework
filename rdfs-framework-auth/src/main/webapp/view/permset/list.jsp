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
	<script type="text/javascript">
		function operator(data, type, full, meta){
			var html = '<a class="link" href= "'+contextPath+'/permset/view?id='+data+'">查看</a>';
    		html += ' <a class="link" href= "'+contextPath+'/permset/edit?id='+data+'">修改</a>';
    		html += ' <a class="link view-menu" href="javascript:;" permId="'+data+'">菜单维护</a>';
	    	return	data=html;
		}
		function setPerm(zTree, nodes, obj){
			var ids = "";
			if(nodes!=null&&nodes.length!=0){
				for(var i=0;i<nodes.length;i++){
					if(!nodes[i].isParent && !nodes[i].isHidden){
						ids += nodes[i].id+",";
					}
				}
			} 
			if(ids == ""){
				$.alert("您没有为该权限集设置任何菜单,请选择权限集对应菜单.");
				return false;
			}
			location.href= contextPath + "/permset/setItem?id="+obj.attr("permId")+"&ids="+ids+"&juid="+juid;
		}
	   $(document).delegate('#fn-btn-search','click',function() {
		   reload("data-table");
	   });
	   $(document).delegate('#fn-btn-add','click',function() {
		   window.location.href = contextPath + '/view/permset/add.jsp?juid='+juid;
	   });
	   $(document).delegate('.view-menu','click',function() {
			var id = $(this).attr("permId");
		    ztree_modal("checkbox",contextPath+"/permset/viewMenuTree?id="+id,$(this),"setPerm");
		});
	   
	</script>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">系统管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/permset/list.jsp">权限集管理</a>
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
			                        <label class="control-label col-sm-4">权限集名称</label>
			                        <div class="col-sm-7">
			                        	<cs:input class="form-control" maxlength="20" name="permName" renderId="data-table"/>
			                        </div>
		                        </div>
		                        <div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">是否启用</label>
			                         <div class="col-sm-7">
			                         	<cs:select class="form-control chosen" dicField="_is" name="permStatus" allowBlank="true" renderId="data-table"/>
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
		           		<cs:table id="data-table" class="table table-striped table-bordered responsive" onrender="${path }/permset/list">
		           			<cs:column dataField="permName" name="权限集名称"/>
		           			<cs:column dataField="permStatus" name="权限集状态" type="dict" format="_is"/>
		           			<cs:column dataField="permDesc" name="备注"/>
		           			<cs:column dataField="createUser" name="创建人"/>
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
