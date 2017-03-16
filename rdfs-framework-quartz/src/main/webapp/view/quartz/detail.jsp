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
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<script type="text/javascript">
	$(function(){
		$("#_quartz_type").change(function(){
			if($(this).val() == '01'){
				$(".ssh, .native").parent().parent().addClass("hide").hide();
				$(".ssh, .native").removeClass("required");
				$(".http").parent().parent().removeClass("hide").show();
				$(".http").addClass("required");
			} else if($(this).val() == '02'){
				$(".http, .native").parent().parent().addClass("hide").hide();
				$(".http, .native").removeClass("required");
				$(".ssh").parent().parent().removeClass("hide").show();
				$(".ssh").addClass("required");
			} else if($(this).val() == '03'){
				$(".ssh, .http").parent().parent().addClass("hide").hide();
				$(".ssh, .http").removeClass("required");
				$(".native").parent().parent().removeClass("hide").show();
				$(".native").addClass("required");
			} else {
				$(".ssh, .native, .http").parent().parent().addClass("hide").hide();
				$(".ssh, .native, .http").removeClass("required");
			}
		});
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
	            <a href="#">系统管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/quartz/list.jsp">定时任务管理</a>
	        </li>
	        <li>
	            <a href="#">定时任务维护</a>
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
			           <h2><i class="glyphicon glyphicon-edit"></i> 表单元素</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<form id="fn-save-form" class="form-horizontal" method="post" action="${path }/dict/save">
		                    <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">任务名称<i class="glyphicon glyphicon-star red"></i></label>
		                        <div class="col-sm-8">
		                        	<input type="hidden" name="id" value="${info.id }">
	                       	 		<input type="text" class="form-control required" name="name" maxlength="30" value="${info.name}">
		                        </div>
		                     </div>
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">任务分组<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
	                       	 		<input type="text" class="form-control required" readonly name="group" maxlength="10" value="默认分组">
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">执行计划<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
	                       	 		<input type="text" class="form-control required" name="cron" maxlength="100" value="${info.cron }">
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">任务类型<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
		                         	<cs:select class="form-control required chosen" id="_quartz_type" dicField="_quartz_type" name="type" allowBlank="true" value="${info.type }"/>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">是否有效<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
		                         	<cs:select class="form-control required chosen" dicField="_is" name="status" allowBlank="true" value="${info.status }"/>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">是否允许并发<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
		                         	<cs:select class="form-control required chosen" dicField="_is" name="batch" allowBlank="true" value="${info.batch }"/>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">执行调度主机名称</label>
		                         <div class="col-sm-8">
		                         	<cs:input class="form-control" name="ip" maxlength="30" value="${info.ip }"></cs:input>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8 hide">
		                        <label class="control-label col-sm-4">本地任务类</label>
		                         <div class="col-sm-8">
		                         	<cs:input class="form-control native" name="className" maxlength="100" value="${info.className }"></cs:input>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8 hide">
		                        <label class="control-label col-sm-4">HttpUrl</label>
		                         <div class="col-sm-8">
		                         	<cs:input class="form-control http" name="httpUrl" maxlength="100" value="${info.httpUrl }"/>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8 hide">
		                        <label class="control-label col-sm-4">远端SSH主机地址</label>
		                         <div class="col-sm-8">
		                         	<cs:input class="form-control ssh" name="host" maxlength="30" value="${info.host }"/>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8 hide">
		                        <label class="control-label col-sm-4">远端SSH主机用户名</label>
		                         <div class="col-sm-8">
		                         	<cs:input class="form-control ssh" name="userName" maxlength="30" value="${info.userName }"/>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8 hide">
		                        <label class="control-label col-sm-4">远端SSH主机密码</label>
		                         <div class="col-sm-8">
		                         	<cs:input class="form-control ssh" type="password" name="password" maxlength="30" value="${info.password }"/>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8 hide">
		                        <label class="control-label col-sm-4">远端SSH主机端口</label>
		                         <div class="col-sm-8">
		                         	<cs:input class="form-control ssh" type="int" maxlength="5" name="port" value="${info.port }"/>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8 hide">
		                        <label class="control-label col-sm-4">远端SSH命令</label>
		                         <div class="col-sm-8">
		                         	<textarea rows="" cols="" class="form-control autogrow ssh" maxlength="1000" name="cmd" placeholder="命令之间以;分隔">${info.cmd }</textarea>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">任务备注</label>
		                         <div class="col-sm-8">
		                         	<textarea rows="" cols="" class="form-control autogrow" maxlength="1000" name="remark">${info.remark }</textarea>
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
		</div>
	</div>
</body>
</html>
