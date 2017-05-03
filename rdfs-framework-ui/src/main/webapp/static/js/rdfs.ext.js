var contextPath = "${pageContext.request.contextPath}";

function IdValid(code) {
	var city = {
		11 : "北京",
		12 : "天津",
		13 : "河北",
		14 : "山西",
		15 : "内蒙古",
		21 : "辽宁",
		22 : "吉林",
		23 : "黑龙江 ",
		31 : "上海",
		32 : "江苏",
		33 : "浙江",
		34 : "安徽",
		35 : "福建",
		36 : "江西",
		37 : "山东",
		41 : "河南",
		42 : "湖北 ",
		43 : "湖南",
		44 : "广东",
		45 : "广西",
		46 : "海南",
		50 : "重庆",
		51 : "四川",
		52 : "贵州",
		53 : "云南",
		54 : "西藏 ",
		61 : "陕西",
		62 : "甘肃",
		63 : "青海",
		64 : "宁夏",
		65 : "新疆",
		71 : "台湾",
		81 : "香港",
		82 : "澳门",
		91 : "国外 "
	};

	if (!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)) {
		return "身份证号格式错误";
	}

	else if (!city[code.substr(0, 2)]) {
		return "地址编码错误";
	} else {
		// 18位身份证需要验证最后一位校验位
		if (code.length == 18) {
			code = code.split('');
			// ∑(ai×Wi)(mod 11)
			// 加权因子
			var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
			// 校验位
			var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
			var sum = 0;
			var ai = 0;
			var wi = 0;
			for (var i = 0; i < 17; i++) {
				ai = code[i];
				wi = factor[i];
				sum += ai * wi;
			}
			var last = parity[sum % 11];
			if (parity[sum % 11] != code[17]) {
				return "校验位错误";
			}
		}
	}
	return "success";
}

function validate(){
	if($(".error").length!=0){
		$(".error").fieldErrorClear();
	}
	//必输项校验
	$(".required").not(":hidden").each(function(){
		if($(this).hasClass("chosen-container")){
			if(!$(this).siblings(".required").hasClass("uncheck")){
				if($(this).siblings(".required").val()==null||$(this).siblings(".required").val()==""){
					$(this).fieldError("不能为空.");
				}
			}
		} else {
			if(!$(this).hasClass("uncheck") && ($(this).val()==null || $(this).val()=="")){
				$(this).fieldError("不能为空.");
			}
		}
	});
	//最小长度校验
	$("input").each(function(){
		var minlength = $(this).attr('minlength');
		if(minlength && minlength != undefined && parseInt(minlength) && parseInt(minlength) != undefined && parseInt(minlength)!=0){
			var length = $(this).val().trim().length;
			if(length < minlength){
				$(this).fieldError("至少需要输入"+minlength+"长度的字符.");
			}
		}
	});
	//手机号码校验
	$("input[type='mobile']").each(function(){
		var value = $(this).val().trim();
		var re = /^1\d{10}$/;
		if(value && !re.test(value)){
			$(this).fieldError("手机号码输入有误.");
		}
	});
	//电话校验
	$("input[type='tel']").each(function(){
		var value = $(this).val().trim();
		var re = /^0\d{2,3}-?\d{7,8}$/;
		if(value && !re.test(value)){
			$(this).fieldError("电话号码输入有误(区号+号码).");
		}
	});
	//电话校验
	$("input[type='email']").each(function(){
		var value = $(this).val().trim();
		var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
		if(value && !re.test(value)){
			$(this).fieldError("邮箱输入有误.");
		}
	});
	//身份证校验
	$("input[type='card']").each(function(){
		var value = $(this).val().trim();
		if(value){
			var tip = IdValid(value);
			if(tip && tip != "success"){
				$(this).fieldError(tip);
			}
		}
	});
	//数字最大值和最小值校验
	$("input[xtype='int'],input[xtype='float']").each(function(){
		validNu($(this));
	});
}
function validateForm(id){
	if($("#"+id).find(".error").length!=0){
		$("#"+id).find(".error").fieldErrorClear();
	}
	//必输项校验
	$("#"+id).find(".required").each(function(){
		if($(this).hasClass("chosen-container")){
			if(!$(this).siblings(".required").hasClass("uncheck")){
				if($(this).siblings(".required").val()==null||$(this).siblings(".required").val()==""){
					$(this).fieldError("不能为空.");
				}
			}
		} else {
			if($(this).siblings(".chosen-container").length == 0){
				if(!$(this).hasClass("uncheck") && ($(this).val()==null || $(this).val()=="")){
					$(this).fieldError("不能为空.");
				}
			}
		}
	});	
	//最小长度校验
	$("#"+id).find("input").each(function(){
		var minlength = $(this).attr('minlength');
		if(minlength && minlength != undefined && parseInt(minlength) && parseInt(minlength) != undefined && parseInt(minlength)!=0){
			var length = $(this).val().trim().length;
			if(length < minlength){
				$(this).fieldError("至少需要输入"+minlength+"长度的字符.");
			}
		}
	});
	//手机号码校验
	$("#"+id).find("input[type='mobile']").each(function(){
		var value = $(this).val().trim();
		var re = /^1\d{10}$/;
		if(value && !re.test(value)){
			$(this).fieldError("手机号码输入有误.");
		}
	});
	//电话校验
	$("#"+id).find("input[type='tel']").each(function(){
		var value = $(this).val().trim();
		var re = /^0\d{2,3}-?\d{7,8}$/;
		if(value && !re.test(value)){
			$(this).fieldError("电话号码输入有误(区号+号码).");
		}
	});
	//电话校验
	$("#"+id).find("input[type='email']").each(function(){
		var value = $(this).val().trim();
		var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
		if(value && !re.test(value)){
			$(this).fieldError("邮箱输入有误.");
		}
	});
	//身份证校验
	$("#"+id).find("input[type='card']").each(function(){
		var value = $(this).val().trim();
		if(value){
			var tip = IdValid(value);
			if(tip && tip != "success"){
				$(this).fieldError(tip);
			}
		}
	});
	//数字最小值和最大值校验
	$("#"+id).find("input[xtype='int'],input[xtype='float']").each(function(){
		validNu($(this));
	});
}
function chosen(){
	$(".chosen").each(function(){
		if($(this).is("select")){
			$(this).chosen({
			    no_results_text: "未发现匹配的字符串!",
				allow_single_deselect: true,
				width:"100%"
			});
		}
	});
}
function getUrlParam(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r!=null) 
    	return unescape(r[2]); 
    return null;
}
function validNu(obj){
	var min;
	var max;
	var value = obj.val().trim();
	if(value){
		try{
			value = parseFloat(value);
		}catch(err){
			obj.fieldError("必须输入数字.");
		}
		if(value){
			try{
				if(obj.attr("min") && parseFloat(obj.attr("min"))){
					min = parseFloat(obj.attr("min"));
				}
			}catch(err){
				console.log("数字输入框的上限值或者输入框的值不是数字.");
			}
			try{
				if(obj.attr("max") && parseFloat(obj.attr("max"))){
					max = parseFloat(obj.attr("max"));
				}
			}catch(err){
				console.log("数字输入框的下限值或者输入框的值不是数字.");
			}
			if(min){
				if(min > parseFloat(obj.val())){
					obj.fieldError("不可输入小于"+min+"的数字.");
				}
			}
			if(max){
				if(max < parseFloat(obj.val())){
					obj.fieldError("不可输入大于"+max+"的数字.");
				}
			}
		}
	}
}
var juid = getUrlParam("juid");
$(function(){
	chosen();
	//var pop = $('<img src="'+contextPath+'/static/image/loading.gif" style="position: fixed; z-index: 999; padding-left: 48%;"/>');
	$.ajaxSetup({
        beforeSend: function (event, jqxhr, settings) {
        	//pop.modal("show");
        	var href = jqxhr.url;
        	if(href && href.indexOf("/")!=-1 && href.indexOf("juid")==-1){
        		if(href.indexOf("?")!=-1){
        			href = href+"&juid="+juid;
        		} else {
        			href = href+"?juid="+juid;
        		}
        	}
        	jqxhr.url = href;
        },
        complete: function (event, jqxhr, settings) {
        	//pop.modal("hide");
        },
        error: function (event, jqxhr, settings) {
        	//pop.modal("hide");
        }
    });
	$("input[xtype='int']").each(function(){
		var negative = $(this).attr("negative");
		if(negative && negative == "true"){
			$.Input.BoundInt("#" + $(this).attr("id"),true);
		} else {
			$.Input.BoundInt("#" + $(this).attr("id"));
		}
	});
	$("input[xtype='float']").each(function(){
		var negative = $(this).attr("negative");
		if(negative && negative == "true"){
			$.Input.BoundFloat("#" + $(this).attr("id"),true);
		} else {
			$.Input.BoundFloat("#" + $(this).attr("id"));
		}
	});
	$(document).delegate('input[xtype="int"],input[xtype="float"]','blur',function(){
		validNu($(this));
	});
	$(document).delegate('.file-upload','click',function() {
		var obj = $(this).parent().siblings("[xtype='file']");
		if(obj && obj != undefined){
			var url = obj.attr("uploadUrl");
			var fileType = obj.attr("fileType");
			var fileSize = obj.attr("fileSize");
			var callback = obj.attr("callback");
			
			if(url && url != undefined && fileType && fileType != undefined && fileSize && fileSize != undefined && parseInt(fileSize) &&parseInt(fileSize) != undefined){
				if(url && url.indexOf("/")!=-1 && url.indexOf("juid")==-1){
					if(url.indexOf("?")!=-1){
						url = url+"&juid="+juid;
					} else {
						url = href+"?juid="+juid;
					}
				}
				var html = $('<div class="modal fade" id="file-input-upload-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">'
						+'  <div class="modal-dialog" role="document">'
						+'    <div class="modal-content">'
						+'      <div class="modal-header">'
						+'        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
						+'        <h4 class="modal-title" id="myModalLabel">文件上传</h4>'
						+'      </div>'
						+'      <div class="modal-body file-input-body">'
						+'        	<input id="file-input-area" name="file" class="file-loading" type="file">'
						+'        	<p class="help-block">支持'+fileType+'格式,大小不超过'+fileSize+'KB.</p>'
						+'      	<p id="error-msg"></p>'
						+'      </div>'
						+'      <div class="modal-footer">'
						+'        <button type="button" class="btn btn-default" data-dismiss="modal" id="colse-model">关闭</button>'
						+'      </div>'
						+'    </div>'
						+'  </div>'
						+'</div>');
				html.modal("show");
				html.on('shown.bs.modal', function () {
					  html.find("#file-input-area").fileinput({
							uploadUrl: url,
							uploadAsync: true,
							showPreview: false,
							allowedFileExtensions: fileType.split(","),
							maxFileCount: 1,
							maxFileSize:parseInt(fileSize),
							elErrorContainer:"#error-msg",
							language:"zh"
						}).on("fileuploaded", function(event, data, previewId, index) { //上传后
							if(callback && callback != undefined ){
					 			eval(callback+"(obj, data)")
					 		}
						});
				});
			}
		}
	});
	$("input[type='date']").each(function(){
		var fmt = $(this).attr("format");
		if(fmt && fmt != undefined){
			$(this).datetimepicker({format: fmt,autoclose: true,todayBtn: true,clearBtn:true,minView:2});
		} else {
			$(this).datetimepicker({format: "yyyy-mm-dd",autoclose: true,todayBtn: true,clearBtn:true,minView:2});
		}
	});
	$("label[type='number']").each(function(){
		var value = $(this).text();
		var format = $(this).attr("format");
		var v = numeral(value).format(format)
		if(v && v != undefined){
			$(this).text(v);
		}
	});
	$("label[type='int']").each(function(){
		var value = $(this).text();
		var format = $(this).attr("format");
		var v = numeral(value).format(format)
		if(v && v != undefined){
			$(this).text(v);
		}
	});
	$("label[type='float']").each(function(){
		var value = $(this).text();
		var format = "0.00";
		var v = numeral(value).format(format)
		if(v && v != undefined){
			$(this).text(v);
		}
	});

	$("label[type='date']").each(function(){
		var value = $(this).text();
		var format = $(this).attr("format");
		if(value && value != undefined){
			if(format && format != undefined){
				var v = moment(value).format(format)
				if(v && v != undefined){
					$(this).text(v);
				}
			} else {
				var v = moment(value).format("yyyy-mm-dd")
				if(v && v != undefined){
					$(this).text(v);
				}
			}
		}
	});
	
	$("select[type='region']").each(function(){
		var provinceId = $(this).attr("provinceId");
		var city = $(this);
		if(provinceId && provinceId != undefined && $("#"+provinceId) && $("#"+provinceId) != undefined){
			$("#"+provinceId).on("change",function(){
				city.children().not(".empty").remove();
				var value = $(this).val();
				if(value){
					$.ajax({
						type: 'POST',
						url: contextPath + '/region/loadPC',
						data:{"regCode":value},
						success: function(data){
							$.each(data,function(i){
								city.append("<option value='"+data[i].regCode+"'>"+data[i].regName+"</option>");
							});
							
							city.val(city.attr("value"));
							city.trigger("chosen:updated");
						},
						dataType: 'json'
					});
				}
			});
			$("#"+provinceId).change();
		}
	});
	$(".breadcrumb a, table a").each(function(){
    	var href = $(this).attr("href");
    	if(href && href.indexOf("/")!=-1 && href.indexOf("juid")==-1){
    		if(href.indexOf("?")!=-1){
    			$(this).attr("href",href+"&juid="+juid);
    		} else {
    			$(this).attr("href",href+"?juid="+juid);
    		}
    	}
    });
    $("form").each(function(){
    	var href = $(this).attr("action");
    	if(href && href.indexOf("/")!=-1 && href.indexOf("juid")==-1){
    		if(href.indexOf("?")!=-1){
    			$(this).attr("action",href+"&juid="+juid);
    		} else {
    			$(this).attr("action",href+"?juid="+juid);
    		}
    	}
    });
    $(".ch-container").find(".row:first").css("padding-top","30px");
});
