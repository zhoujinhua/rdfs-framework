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
    		html += ' <a class="link" href= "'+contextPath+'/event/edit?id='+data+'">修改</a>';
    		html += ' <a class="link delete" href= "javascript:;" data-id="'+data+'">删除</a>';
    		html += ' <a class="link monitor" href= "javascript:;" data-id="'+data+'">监听管理</a>';
	    	return	data=html;
		}
		$(document).delegate('#fn-btn-search','click',function() {
		    reload("data-table");	
	    });
		$(document).delegate('.delete','click',function() {
			var nodeId = $(this).attr("data-id");
			$.confirm("将要删除该节点事件，确定吗?", function(ok){
				if(ok){
					$.ajax({
						  type: 'POST',
						  url: '${pageContext.request.contextPath}/event/delete?id='+nodeId,
						  success: function(data){
							  if(data.responseCode == 1){
								  $.alert("删除节点事件成功!");
								  parent.crudNode("delete");
								  location.href = "${path }/view/workflow/event/list.jsp?id=${id}&juid="+juid;
							  } else {
								  $.alert(data.responseMsg);
							  }
						  },
						  dataType: 'json'
					});
				}
			});
	    });
		$(document).delegate('.monitor','click',function() {
			var id = $(this).attr("data-id");
			$("#monitor-modal").modal({
				remote : "${path}/monitor/edit?id="+id
			});
		});
		$(document).delegate("#monitor-modal","hidden.bs.modal", function() {  
	        $(this).removeData("bs.modal");  
	    });
	    $(document).delegate('#fn-btn-add','click',function() {
		    window.location.href = contextPath + '/view/workflow/event/add.jsp?id=${id}&juid='+juid;
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
		                    	<div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">起始节点</label>
			                        <div class="col-sm-7">
		                       	 		<cs:input type="text" class="form-control" name="name" maxlength="20" renderId="data-table"/>
			                        </div>
		                        </div>
		                        <div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">到达节点</label>
			                         <div class="col-sm-7">
			                        	<cs:input type="text" class="form-control" name="code" maxlength="20" renderId="data-table"/>
			                        </div>
		                        </div>
		                        <div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">执行动作</label>
			                         <div class="col-sm-7">
			                        	<cs:select class="form-control chosen" name="action" dicField="_node_action" allowBlank="true" renderId="data-table"/>
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
			           <h2><i class="glyphicon glyphicon-th"></i> 事件列表</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<cs:table id="data-table" class="table table-striped table-bordered responsive" onrender="${path }/event/list?currNode.id=${id }">
		           			<cs:column dataField="name" name="事件名称"/>
		           			<cs:column dataField="currNode.name" name="当前节点"/>
		           			<cs:column dataField="action" name="执行动作" type="dict" format="_node_action"/>
		           			<cs:column dataField="nextNode.name" name="下一节点"/>
		           			<cs:column dataField="route" name="下一节点状态"/>
		           			<cs:column dataField="group" name="分配组"/>
		           			<cs:column dataField="user" name="分配用户"/>
		           			<cs:column dataField="remark" name="备注"/>
		           			<cs:column dataField="id" name="操作" renderFn="operator"/>
		           		</cs:table>
		           </div>
		       </div>
	       </div>
	       <div class="modal fade" id="monitor-modal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel">
				<div class="modal-dialog" role="document" style="width:60%;">
					<div class="modal-content">
					</div>
				</div>
			</div>
</body>
</html>
