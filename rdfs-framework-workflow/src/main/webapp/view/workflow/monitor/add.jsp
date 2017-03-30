<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://javass.cn/common/" prefix="cs"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>流程监控管理</title>
    <style type="text/css">
    	.monitor-content .content,.monitor-title .content{
    		width:80%;float:left;padding-left:5px;padding-right:5px;
    	}
    	.monitor-content .oper,.monitor-title .oper{
    		width:10%;float:left;padding-left:5px;padding-right:5px;
    	}
    </style>
</head>
<body>
	<script type="text/javascript">
		function sort(){
			var i = 1;
			$(".seq").each(function(){
				$(this).html(i);
				$(this).parent().find(".monitor-class").attr("name","taskMonitors["+(i-1)+"].className");
				$(this).parent().find(".monitor-event").attr("name","taskMonitors["+(i-1)+"].eventId");
				i++;
			});
		}
		$(function(){
			$("#fn-btn-save").click(function(){
				validate();
				if($(".error").length!=0){
					$.alert("存在不可为空的表单项，请检查.");
					return false;
				}
				$.ajax({
					  type: 'POST',
					  url: '${pageContext.request.contextPath}/monitor/save?id=${nodeEvent.id}',
					  data: $("#fn-save-form").serialize(),
					  success: function(data){
						  if(data.responseCode == 1){
							  $.alert("保存事件监听成功!");
						  } else {
							  $.alert(data.responseMsg);
						  }
					  },
					  dataType: 'json'
				});
			});
			$(document).delegate('.add-monitor','click',function() {
				var html = $("#monitor-templete").html();
				$(".monitor-title").after(html);
				sort();
			});
			$(document).delegate('.remove-monitor','click',function() {
				$(this).closest("ul").remove();
				sort();
			});
		});
	</script>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title" id="myModalLabel">事件监听</h4>
	</div>
	<form id="fn-save-form" class="form-horizontal" method="post">
		<div class="from-group">
			<ul class="col-sm-12 monitor-title">
				<li class="oper">序号</li>
				<li class="content">监听类<i class="glyphicon glyphicon-star red"></i></li>
				<li class="oper"><a class="btn btn-info btn-sm add-monitor"><i class="glyphicon glyphicon-plus"></i></a></li>
			</ul>
			<c:forEach items="${nodeEvent.taskMonitors }" var="item" varStatus="status">
				<ul class="col-sm-12 monitor-content">
					<li class="oper seq">${status.index+1 }</li>
					<li class="content">
						<cs:input class="form-control monitor-class required" name="taskMonitors[${status.index }].className" value="${item.className }"/>
						<input type="hidden" class="monitor-event" name="taskMonitors[${status.index }].eventId" value="${item.eventId }">
					</li>
					<li class="oper"><a class="btn btn-info btn-sm remove-monitor"><i class="glyphicon glyphicon-remove"></i></a></li>
				</ul>
			</c:forEach>
		</div>
	</form>
	<div id="monitor-templete" class="hide">
		<ul class="col-sm-12 monitor-content">
			<li class="oper seq"></li>
			<li class="content">
				<cs:input class="form-control monitor-class required"/>
				<input type="hidden" class="monitor-event" value="${nodeEvent.id }">
			</li>
			<li class="oper"><a class="btn btn-info btn-sm remove-monitor"><i class="glyphicon glyphicon-remove"></i></a></li>
		</ul>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-info" id="fn-btn-save">提交</button>
		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	</div>
</body>
</html>
