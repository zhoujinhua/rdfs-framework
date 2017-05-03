<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://javass.cn/common/" prefix="cs"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
String menuId = request.getParameter("menuId");
if(menuId!=null && !"".equals(menuId)){
	request.setAttribute("menuId", menuId);
}
String menuTitle = request.getParameter("menuTitle"); 
if(menuTitle!=null && !"".equals(menuTitle)){
	menuTitle = new String(menuTitle.getBytes("ISO8859_1")); 
	request.setAttribute("menuTitle", menuTitle);
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>催收大平台-权限集管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<script type="text/javascript">
	$(function(){
		$("#fn-btn-save").click(function(){
			validate();
			if($(".error").length!=0){
				return false;
			}
			$.ajax({
				  type: 'POST',
				  url: '${pageContext.request.contextPath}/resource/save',
				  data: $("#fn-save-form").serialize(),
				  success: function(data){
					  if(data.responseCode == 1){
						  parent.location.href="${path}/view/resource/list.jsp?juid="+juid;
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
	           <h2><i class="glyphicon glyphicon-edit"></i> 功能维护</h2>
	           <div class="box-icon">
	               <a href="#" class="btn btn-minimize btn-round btn-default"><i
	                       class="glyphicon glyphicon-chevron-up"></i></a>
	           </div>
	       </div>
           <div class="box-content">
           		<form id="fn-save-form" class="form-horizontal" method="post">
                    <div class="form-group col-sm-12 col-sm-8">
                        <label class="control-label col-sm-4">功能名称<i class="glyphicon glyphicon-star red"></i></label>
                        <div class="col-sm-8">
                        	<input type="hidden" name="itemId" value="${resource.itemId }"	>
                      	 		<input type="text" class="form-control required" name="itemTitle" maxlength="20" value="${resource.itemTitle }">
                        </div>
                     </div>
                     <div class="form-group col-sm-12 col-sm-8">
                        <label class="control-label col-sm-4">是否启用<i class="glyphicon glyphicon-star red"></i></label>
                         <div class="col-sm-8">
                         	<cs:select class="form-control required chosen" dicField="_is" name="status" allowBlank="true" value="${resource.status }"/>
                        </div>
                    </div>
                     <div class="form-group col-sm-12 col-sm-8">
                        <label class="control-label col-sm-4">所属菜单</label>
                         <div class="col-sm-8">
                        	<input class="form-control" name="menu.menuTitle" value="${menuTitle != null ? menuTitle : resource.menu.menuTitle }" readonly>
                        	<input type="hidden" name="menu.menuId" value="${menuId != null ? menuId : resource.menu.menuId }">
                        </div>
                    </div>
                     <div class="form-group col-sm-12 col-sm-8">
                        <label class="control-label col-sm-4">请求地址</label>
                         <div class="col-sm-8">
                        	<cs:input class="form-control" maxlength="200" name="itemLocation" value="${resource.itemLocation }"/>
                        </div>
                    </div>
                     <div class="form-group col-sm-12 col-sm-8">
                        <label class="control-label col-sm-4">排序号</label>
                         <div class="col-sm-8">
                        	<cs:input type="int" class="form-control" maxlength="2" name="sortNo" value="${resource.sortNo }"/>
                        </div>
                    </div>
                     <div class="form-group col-sm-12 col-sm-8">
                        <label class="control-label col-sm-4">图标</label>
                         <div class="col-sm-8">
                        	<cs:input class="form-control" maxlength="50" name="itemIcon" value="${resource.itemIcon }"/>
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
