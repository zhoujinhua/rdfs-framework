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
    <title>定时调度管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<script type="text/javascript">
	function setJob(zTree, nodes, obj){
		$("#qz_tbody").children().remove();
		var ids = "";
		if(nodes!=null&&nodes.length!=0){
			for(var i=0;i<nodes.length;i++){
				if(!nodes[i].isParent){
					ids += nodes[i].id+",";
					$("#qz_tbody").prepend("<tr id=''><td class='seq'></td><td><input type='hidden' class='qzid' value='"
							+nodes[i].id+"'><input type='hidden' class='qzname' value='"
							+nodes[i].name+"'>"+nodes[i].name+"</td><td><input class='form-control qseq' maxlength='2'></td></tr>");
				}
			}
		} 
		if(ids == ""){
			$("#qz_table").addClass("hide").hide();
		} else {
			$("#qz_table").removeClass("hide").show();
			sort();
		}
		$('.modal').map(function() {
		    $(this).modal('hide');
		});
	}
	function sort(){
		var i = 1;
		$(".seq").each(function(){
			$(this).html(i);
			$(this).parent().find(".qzid").attr("name","relations["+(i-1)+"].jobInfo.id");
			$(this).parent().find(".qzname").attr("name","relations["+(i-1)+"].jobInfo.name");
			$(this).parent().find(".qseq").attr("name","relations["+(i-1)+"].seq");
			i++;
		});
	}
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
		$(document).delegate('#send_mail', 'change', function() {
			var value = $(this).val();
			if(value == "01" || value == ""){
				$("#email").parent().parent().addClass("hide").hide();
			} else {
				$("#email").parent().parent().removeClass("hide").show();
			}
		});
		$(document).delegate('.qseq', 'keyup', function() {
			if($(this).hasClass("error"))
				$(this).fieldErrorClear();
			if ( !/^[1-9]\d?$/g.test($(this).val())){
				$(this).val("");
				$(this).fieldError("只能输入1-99之间的数字")
			}
		});
	  	$("#fn-btn-save").click(function() {
	  		validate();
	  		if($(".error").length!=0){
	  			return false;
	  		}
            $("#fn-save-form").submit();
         });
	  	$("#cron_btn").click(function(){
		  	ztree_modal("checkbox",contextPath+"/job/jobTree?id=${info.id}",$("#cron_after"),"setJob");
	  	});
	  	var relationName = "${info.relationName}";
	  	if(relationName){
	  		$("#qz_table").removeClass("hide").show();
	  	}
	  	$("#_quartz_type").change();
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
		           		<form id="fn-save-form" class="form-horizontal" method="post" action="${path }/job/save">
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
		                        <label class="control-label col-sm-4">执行计划</label>
		                         <div class="col-sm-8">
	                       	 		<input type="text" class="form-control" name="cron" maxlength="100" value="${info.cron }">
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
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">邮件我<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
		                         	<cs:select class="form-control required chosen" dicField="_quartz_mail" name="" allowBlank="true" value="${info.sendMail }" id="send_mail"/>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8 hide">
		                        <label class="control-label col-sm-4">邮件地址<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
		                         	<textarea rows="" cols="" class="form-control required autogrow" maxlength="1000" name="email" id="email" style="min-height:57px;" placeholder="多个邮件地址使用英文分号;分割">${info.email }</textarea>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">继续执行</label>
		                         <div class="col-sm-8">
		                         	<div class="input-group">
			                         	<input class="form-control" id="cron_after" readonly value="${info.relationName }">
		                         		<a class="btn btn-primary input-group-addon" id="cron_btn" href="javascript:;">
		                         			<i class="glyphicon glyphicon-user red"></i>
		                         		</a>
		                         	</div>
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
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4"></label>
		                         <div class="col-sm-8">
		                         	<table class="table table-striped table-bordered responsive no-footer hide" id="qz_table">
		                         		<thead>
		                         			<tr role="row">
		                         				<th>任务序号</th>
		                         				<th>任务名称</th>
		                         				<th>优先级</th>
		                         			</tr>
		                         		</thead>
		                         		<tbody id="qz_tbody">
		                         			<c:forEach items="${info.relations }" var="item" varStatus="status">
		                         				<tr role="row">
		                         					<td>${status.index+1 }</td>
		                         					<td>
		                         						<input type="hidden" class="qzid" name="relations[${status.index }].jobInfo.id" value="${item.jobInfo.id }">
		                         						<input type="hidden" class="qzname" name="relations[${status.index }].jobInfo.name" value="${item.jobInfo.name }">
		                         						${item.jobInfo.name }
		                         					</td>
		                         					<td><input maxlength="2" class="form-control qseq" value="${item.seq }" name="relations[${status.index }].seq"></td>
		                         				</tr>
		                         			</c:forEach>
		                         		</tbody>
	                         		</table>
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
