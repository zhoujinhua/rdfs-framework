<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="${path }/static/weblib/ztrees/css/metroStyle/metroStyle.css" rel="stylesheet" type="text/css">
<link href="${path }/static/weblib/charisma/css/bootstrap-cerulean.min.css" rel="stylesheet" type="text/css">
<link href="${path }/static/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css">
<link href="${path }/static/weblib/charisma/css/charisma-app.css" rel="stylesheet" type="text/css">
<link href="${path }/static/css/fileinput.css" rel="stylesheet" type="text/css">
<link href="${path }/static/css/dialog.css" rel="stylesheet" type="text/css">
<link href="${path }/static/css/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${path }/static/css/chosen.min.css" rel="stylesheet" type="text/css">
<link href="${path }/static/css/chosen.bootstrap3.css" rel="stylesheet" type="text/css">
<link href="${path }/static/weblib/bootstrap/css/bootstrap-360.css" rel="stylesheet" type="text/css">
<link href="${path }/static/css/rdfs.ext.css" rel="stylesheet" type="text/css">
<script src="${path }/static/js/jquery.min.js"  type="text/javascript"></script>
<script src="${path }/static/js/jquery.form.js"  type="text/javascript"></script>
<script src="${path }/static/js/fileinput.js" type="text/javascript"></script>
<script src="${path }/static/js/fileinput_locale_zh.js" type="text/javascript"></script>
<script src="${path }/static/weblib/bootstrap/js/bootstrap.min.js"  type="text/javascript"></script>
<script src="${path }/static/weblib/charisma/js/jquery.cookie.js"  type="text/javascript"></script>
<script src="${path }/static/weblib/charisma/js/jquery.autogrow-textarea.js"  type="text/javascript"></script>
<script src="${path }/static/js/chosen.jquery.min.js" type="text/javascript"></script>
<script src="${path }/static/js/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="${path }/static/js/dataTables.bootstrap.min.js" type="text/javascript"></script>
<script src="${path }/static/js/moment.min.js" type="text/javascript"></script>
<script src="${path }/static/js/numeral.min.js" type="text/javascript"></script>
<script src="${path }/static/js/number.ext.js" type="text/javascript"></script>
<script src="${path }/static/js/rdfs.ext.js" type="text/javascript"></script>
<script src="${path }/static/weblib/charisma/js/charisma.js"  type="text/javascript"></script>
<script src="${path }/static/js/dataTables.js" type="text/javascript"></script>
<script src="${path }/static/js/dialog.js" type="text/javascript"></script>
<script src="${path }/static/js/validator-helper.js" type="text/javascript"></script>
<script src="${path }/static/weblib/ztrees/js/jquery.ztree.zddm.js" type="text/javascript"></script>
<script src="${path }/static/weblib/ztrees/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
<script src="${path }/static/weblib/ztrees/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
<script src="${path }/static/weblib/ztrees/js/jquery.ztree.exhide-3.5.js" type="text/javascript"></script>
<script src="${path }/static/js/ztree.ext.js" type="text/javascript"></script>
<script src="${path }/static/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<script type="text/javascript">
	var contextPath = "${pageContext.request.contextPath}";
	var href = parent.top.$("#bs-css").attr("href");
	if(href!=null && href!=""){
		href = href.substring(href.indexOf("/",2),href.length);
		$("#sub-bs-css").attr("href","${path}"+href);
	}
</script>