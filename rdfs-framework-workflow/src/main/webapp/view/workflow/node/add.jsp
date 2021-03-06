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
    <title>工作流节点管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<script type="text/javascript">
	$(function(){
	  	$("#fn-btn-save").click(function() {
	  		validate();
	  		if($(".error").length!=0){
	  			return false;
	  		}
	  		$.ajax({
				  type: 'POST',
				  url: '${pageContext.request.contextPath}/node/save',
				  data: $("#fn-save-form").serialize(),
				  success: function(data){
					  if(data.responseCode == 1){
						  $.alert("保存成功!");
						  var nodeId = "${node.id}";
						  var newNode = {id:"node-"+data.aaData.id,name:data.aaData.name};
						  if(nodeId){
							  parent.crudNode("edit", newNode);
						  } else{
							  parent.crudNode("add", newNode);
						  }
						  location.href="${path }/view/workflow/event/list.jsp?id="+data.aaData.id+"&juid="+juid;
					  } else {
						  $.alert(data.responseMsg);
					  }
				  },
				  dataType: 'json'
			});
         });
	});
</script>
<body>
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
	           <h2><i class="glyphicon glyphicon-edit"></i> 节点维护</h2>
	           <div class="box-icon">
	               <a href="#" class="btn btn-minimize btn-round btn-default"><i
	                       class="glyphicon glyphicon-chevron-up"></i></a>
	           </div>
	       </div>
           <div class="box-content">
           		<form id="fn-save-form" class="form-horizontal" method="post">
                    <div class="form-group col-sm-12 col-sm-8">
                        <label class="control-label col-sm-4">节点名称<i class="glyphicon glyphicon-star red"></i></label>
                        <div class="col-sm-8">
                        	<input type="hidden" name="id" value="${node.id }">
                        	<input type="hidden" name="processInfo.id" value="${id }">
                   	 		<input type="text" class="form-control required" name="name" maxlength="30" value="${node.name}">
                        </div>
                     </div>
                     <div class="form-group col-sm-12 col-sm-8">
                        <label class="control-label col-sm-4">节点代码<i class="glyphicon glyphicon-star red"></i></label>
                         <div class="col-sm-8">
                      	 		<input type="text" class="form-control required" name="code" maxlength="30" value="${node.code}">
                        </div>
                    </div>
                     <div class="form-group col-sm-12 col-sm-8">
                        <label class="control-label col-sm-4">节点类型<i class="glyphicon glyphicon-star red"></i></label>
                         <div class="col-sm-8">
                         	<cs:select class="form-control required chosen" dicField="_node_type" name="type" allowBlank="true" value="${node.type }"/>
                        </div>
                    </div>
                     <div class="form-group">
	                     <div class="col-sm-12">
		                    <p class="center-block">
		                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-save"><i class="glyphicon glyphicon-ok"></i> 保存</a>
		                    </p>
		                </div>
	                </div>
                </form>
              	</div>
      	</div>
    </div>
</body>
</html>
