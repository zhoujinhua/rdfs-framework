$(function(){
	$(".datatables").each(function(){
		load($(this));
	});
});

function getData(d, table_id) {
	$("input,select").each(function(){
		if($(this).attr("render-id") && $(this).attr("render-id")!=undefined && $(this).attr("render-id") == table_id){
			d[''+$(this).attr("name")] =  $(this).val();
		}
	})
	return d;
}

function reload(id){
	load($("#"+id))	;
}

function load($this){
	var obj = new Array();
	var table_id = $this.attr("id");
	obj.table_id = table_id;
	var url = $this.attr("url");
	obj.url = url;
	var pageSize = $this.attr("size");
	if(!pageSize || pageSize == undefined){
		pageSize = 10;
	}
	obj.pageSize = parseInt(pageSize);
	var prerender = $this.attr("prerender");
	if(prerender){
		obj.prerender = prerender;
	}
	
	var data = new Array();
	$("#"+table_id).find("th").each(function(){
		var mData = $(this).attr("dataField");
		var sWidth = $(this).attr("width");
		var type = $(this).attr("type");
		var format = $(this).attr("format");
		var renderFn = $(this).attr("renderFn");
	    var abc = {};
	 	abc.mData = mData;
	 	abc.sWidth = sWidth;
	 	abc.sDefaultContent = "";
	 	abc.orderable = false;
	 	if(!type || !renderFn){
	 		abc.render = function(data, dtype, full, meta){
	 			var html = data;
	 			if(renderFn){
	 				html = eval(renderFn+"(data, type, full, meta)")
	 			}
 				if(type){
 					if(type == "number"){
 						if(data){
 							html = numeral(data).format(format);
 						}
 					}
 					else if(type == "date"){
 						if(data){
 							html = moment(data).format(format);
 						}
 					} 
 					else if(type == "dict"){
 						if(data){
 							if($("input[dict-code='"+format+"']").length!=0){
 								var dictData = $("input[dict-code='"+format+"']").val();
 								var dictArray = eval(dictData);
 								var part = data.split(",");
 								var cont = " ";
 								
 								for(var i = 0;i<dictArray.length;i++){
 									for(var j = 0; j<part.length; j++){
 										if(dictArray[i].code == part[j]){
 											cont += dictArray[i].desc + ",";
 										}
 									}
 								}
								return cont.substring(0, cont.length-1);
 							}
 						}
 						
 					}
 					else if(type == "region"){
 						if(data){
 							if($(".region-all").length!=0){
 								var regionData = $(".region-all").val();
 								var regionArray = eval(regionData);
 								for(var i = 0;i<regionArray.length;i++){
 									if(regionArray[i].regCode == data){
 										return regionArray[i].regName;
 									}
 								}
 							}
 						}
 					}
	 			}
	 			return data = html ;
	 		};
	 	}
	    data.push(abc);
	});
	obj.field = data;
	datetable(obj);
}

function datetable(obj){
	 $("#" + obj.table_id).DataTable({
			/*"aLengthMenu":[10,20,40,60],*/
			"bLengthChange":false,
			"searching":false,//禁用搜索
			"lengthChange":true,
			"paging": true,//开启表格分页
			"bProcessing" : true,
			"bServerSide" : true,
			"bAutoWidth" : false,
			"sort" : "position",
			"deferRender":true,//延迟渲染
			"bStateSave" : false, //在第三页刷新页面，会自动到第一页
			"iDisplayLength" : obj.pageSize,
			"iDisplayStart" : 0,
			"destroy": true,
			"dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
			"ordering": false,//全局禁用排序
			"ajax": {
		            "type": "POST",
		            "url":obj.url,
		            "data":function(d){
		            	getData(d, obj.table_id);
		            },
					"dataSrc": function (data){
						if(obj.prerender){
							eval(obj.prerender + "(data)")
						}
				        return data.aaData;
					}
		    },
			"aoColumns" : obj.field,
			"oLanguage" : { // 国际化配置
				"sProcessing" : "正在获取数据，请稍后...",
				"sLengthMenu" : "显示 _MENU_ 条",
				"sZeroRecords" : "没有找到数据",
				"sInfo" : "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
				"sInfoEmpty" : "记录数为0",
				"sInfoFiltered" : "(全部记录数 _MAX_ 条)",
				"sInfoPostFix" : "",
				"sSearch" : "搜索",
				"sUrl" : "",
				"oPaginate" : {
					"sFirst" : "第一页",
					"sPrevious" : "上一页",
					"sNext" : "下一页",
					"sLast" : "最后一页"
				}
			},
			"fnRowCallback": function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
				$(nRow).find("a").each(function(){
			    	var href = $(this).attr("href");
			    	if(href && href.indexOf("/")!=-1 && href.indexOf("juid")==-1){
			    		if(href.indexOf("?")!=-1){
			    			$(this).attr("href",href+"&juid="+juid);
			    		} else {
			    			$(this).attr("href",href+"?juid="+juid);
			    		}
			    	}
			    });
	        }
	 });
}

