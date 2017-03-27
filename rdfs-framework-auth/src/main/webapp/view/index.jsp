<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
String juid = request.getParameter("juid");
if(juid!=null && !"".equals(juid)){
	request.setAttribute("juid", juid);
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>美利车金融-渠道管理系统</title>
    <jsp:include page="common/head.jsp"></jsp:include>
    <link href="${path }/static/weblib/charisma/css/bootstrap-cerulean.min.css" rel="stylesheet" type="text/css" id="bs-css">
</head>
<body>
	<script type="text/javascript">
		$(".sidebar-nav,#content").height($(window).height()-$(".navbar-static-top").height()-120);
		$.ajax({
			  type: 'post',
			  url: contextPath + '/permset/getMenuJson',
			  success: function(data){
				  $(".main-menu").append(loadMenu(data, data[0]));
			  },
			  dataType: 'json'
		});
		
		function loadMenu(data, subdata){
			 var html = '';
			 $.each(subdata, function(i){
				 $.each(subdata[i], function(j){
					 var obj = subdata[i][j];
					 if(obj.isParent){
						 var a = '<li class="accordion"><a href="javascript:;"><i class="'+obj.icon+'"></i>'+
				  			'<span class="arrow"> '+obj.name+'</span></a><ul class="nav nav-pills nav-stacked sub-menu">';
							console.log(obj);
				  			if(data[obj.id]){
					  			a += loadMenu(data, data[obj.id]) ;
							}
							a += '</ul></li>';
							html += a;
					 } else {
						  var b = '<li data-toggle="tooltip" data-placement="right" title="'+obj.name+'"><a href="javascript:;"'+
				  			' data-url="'+obj.url+'" data-id='+obj.id+' data-name='+obj.name+' class="menu-item">'+
				  			'<i class="'+obj.icon+'"></i><span class="arrow"> '+obj.name+'</span></a></li>';
							 html += b;
		  			 }
				 });
			 });
			 return html;
		}
	</script>
    <div class="navbar navbar-default" role="navigation">
        <div class="navbar-inner">
            <a class="navbar-brand" href="#"> <!-- <img alt="Charisma Logo" src="charisma/img/logo20.png" class="hidden-xs"/> -->
                <span>美利车金融</span>
            </a>
 
            <div class="btn-group pull-right">
                <button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <i class="glyphicon glyphicon-user"></i><span class="hidden-sm hidden-xs"> ${user.trueName}</span>
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="javascript:;" id="modify-info">信息维护</a></li>
                    <li><a href="javascript:;" id="modify-pwd">密码修改</a></li>
                    <li><a href="javascript:logout();">注销</a></li>
                </ul>
            </div>
            <div class="btn-group pull-right theme-container animated tada">
                <button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <i class="glyphicon glyphicon-tint"></i><span
                        class="hidden-sm hidden-xs"> 更改主题/皮肤</span>
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" id="themes">
                    <li><a data-value="classic" href="#"><i class="whitespace"></i> Classic</a></li>
                    <li><a data-value="cerulean" href="#"><i class="whitespace"></i> Cerulean</a></li>
                    <li><a data-value="cyborg" href="#"><i class="whitespace"></i> Cyborg</a></li>
                    <li><a data-value="simplex" href="#"><i class="whitespace"></i> Simplex</a></li>
                    <li><a data-value="darkly" href="#"><i class="whitespace"></i> Darkly</a></li>
                    <li><a data-value="lumen" href="#"><i class="whitespace"></i> Lumen</a></li>
                    <li><a data-value="slate" href="#"><i class="whitespace"></i> Slate</a></li>
                    <li><a data-value="spacelab" href="#"><i class="whitespace"></i> Spacelab</a></li>
                    <li><a data-value="united" href="#"><i class="whitespace"></i> United</a></li>
                </ul>
            </div>
        </div>
    </div>
<div class="ch-container">
    <div class="row">
        <div style="float:left;width:16%;" id="menu">
            <div class="sidebar-nav" style="overflow:auto">
                <div class="nav-canvas page-sidebar">
                    <ul class="nav nav-pills nav-stacked main-menu">
                        <li class="nav-header ul-colspan-on"><i class="glyphicon glyphicon-hand-left blue" style="margin-left:95%;"></i></li>
                        <li class=""><a href="${path }/view/index.jsp?juid=${juid}"><i class="glyphicon glyphicon-home"></i><span class="arrow"> 主页</span></a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div id="popWindow" class="popWindow" style="display: none;">
		</div>
		<div id="maskLayer" class="maskLayer" style="display: none;">
			<img src="${path }/static/image/loading.gif" />
		</div>
        <div id="content" style="float:right;width:84%;">
       		<ul class="nav nav-tabs page-tabs hide" style="margin-left:20px;margin-right:38px;">
            </ul>
        	<iframe id="sub-content" src="" style="width: 100%;height: 96%;border:none;" frameborder="0"></iframe>
    	</div>
	</div>
</div>
</body>
</html>
