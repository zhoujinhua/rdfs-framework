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
    <title>工作流管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<body>
	<script type="text/javascript">
		function operator(data, type, full, meta){
			var html = '';
    		html += ' <a class="link" href= "'+contextPath+'/node/edit?id='+data+'">修改</a>';
    		html += ' <a class="link delete" href= "javascript:;" data-id="'+data+'">删除</a>';
	    	return	data=html;
		}
		$(document).delegate('#fn-btn-search','click',function() {
		    reload("data-table");	
	    });
		$(document).delegate('.delete','click',function() {
			var nodeId = $(this).attr("data-id");
			$.confirm("删除节点将同步删除节点关联的事件，确定吗?", function(ok){
				if(ok){
					$.ajax({
						  type: 'POST',
						  url: '${pageContext.request.contextPath}/node/delete?id='+nodeId,
						  success: function(data){
							  if(data.responseCode == 1){
								  $.alert("删除节点成功!");
								  parent.crudNode("delete");
								  location.href = "${path }/view/workflow/node/list.jsp?id=${id}&juid="+juid;
							  } else {
								  $.alert(data.responseMsg);
							  }
						  },
						  dataType: 'json'
					});
				}
			});
	    });
	    $(document).delegate('#fn-btn-add','click',function() {
		    window.location.href = contextPath + '/view/workflow/node/add.jsp?id=${id}&juid='+juid;
	    });
	</script>
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
			                        <label class="control-label col-sm-1">节点名称</label>
			                        <div class="col-sm-3">
		                       	 		<cs:input type="text" class="form-control" name="name" maxlength="20" renderId="data-table"/>
			                        </div>
			                        <label class="control-label col-sm-1">节点代码</label>
			                         <div class="col-sm-3">
			                        	<cs:input type="text" class="form-control" name="code" maxlength="20" renderId="data-table"/>
			                        </div>
		                        </div>
		                    </div>
		                     <div class="form-group">
			                     <div class="col-sm-12">
				                    <p class="center-block">
				                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-search"><i class="glyphicon glyphicon-search"></i> 查询</a>
				                        <!-- <a href="#" class="btn btn-default btn-mini" id="fn-btn-add"><i class="glyphicon glyphicon-plus"></i> 新增</a> -->
				                    </p>
				                </div>
			                </div>
		                </form>
	               	</div>
		      	</div>
		    </div>
	    	<div class="box col-md-12">
		      	<div class="box-inner">
		      	   <div class="box-header well" data-original-title="">
			           <h2><i class="glyphicon glyphicon-th"></i> 流程节点</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<cs:table id="data-table" class="table table-striped table-bordered responsive" onrender="${path }/node/list?processInfo.id=${id }&juid=${juid }">
		           			<cs:column dataField="name" name="节点名称"/>
		           			<cs:column dataField="code" name="节点代码"/>
		           			<cs:column dataField="status" name="节点类型" type="dict" format="_node_type"/>
		           			<cs:column dataField="id" name="操作" renderFn="operator"/>
		           		</cs:table>
		           </div>
		       </div>
	       </div>
</body>
</html>
