var setting_input = {
	check : {
		enable : true,
		chkStyle : "radio",
		radioType : "all"
	},
	view : {
		dblClickExpand : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		onClick : onClick,
		onCheck : onCheck
	}
};
var setting_modal = {
	view : {
		selectedMulti : false
	},
	check : {
		enable : true
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {}
}; 
var i = 0;
function ztree_input(type, url, obj){
	var date = moment(new Date()).format("YYYYMMDDHHMISS") + i;
	i ++;
	var contentId = "ztree_input_content_"+date;
	var areaId = "ztree_input_area_"+date;
	setting_input.check.chkStyle = type;
	$.ajax({
		  type: 'POST',
		  url: url,
		  success: function(data){
			  var html = '<div id="'+contentId+'" class="ztree_input_content" style="display:none; position: absolute;">'+
					  		'<ul id="'+areaId+'" class="ztree" style="margin-top:0; width:360px; height: auto; border-radius: 4px; background: white none repeat scroll 0% 0%; border: 1px solid;"></ul>'+
					  		'</div>';
			  $("body").append(html);
			  obj.attr("data-ztree-id",contentId);
			  obj.attr("onclick","javascript:ztree_show(this)");
			  $.fn.zTree.init($("#"+areaId), setting_input, eval(data));
		  },
		  dataType: 'text'
	});
}
function ztree_show(obj){
	var cityOffset = $(obj).offset();
	var ztreeId = $(obj).attr("data-ztree-id");
	if(ztreeId && ztreeId != undefined){
		$("#"+ztreeId).css({left:cityOffset.left + 5 + "px", top:cityOffset.top + $(obj).outerHeight() + "px"}).slideDown("fast");
		$(document).bind('click', 'body',onBodyDown);
	}
}
function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function onCheck(e, treeId, treeNode) {
	var data_ztree_id = $("#"+treeId).parents(".ztree_input_content").attr("id");
	var zTree = $.fn.zTree.getZTreeObj(treeId),
	nodes = zTree.getCheckedNodes(true),
	v = ""; d = "";
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
		d += nodes[i].id + ",";
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (d.length > 0 ) d = d.substring(0, d.length-1);
	$("input[data-ztree-id='"+data_ztree_id+"']").val(v);
	$("input[data-ztree-id='"+data_ztree_id+"']").siblings(":hidden").val(d);
}
function hideMenu() {
	$(".ztree_input_content").fadeOut("fast");
	$(document).unbind('click', 'body',onBodyDown);
}

function onBodyDown(event) {
	event = getEvent();
	var id = $(event.target).attr("data-ztree-id");
	if (!($(event.target).parents(".ztree_input_content").length>0)) {
		if(id){
			if(!(event.target.id == id || id.indexOf("ztree_input_content")!=-1)){
				hideMenu();
			}
		} else {
			hideMenu();
		}
	}
}

function getEvent(){
    if(window.event){
        return window.event;
    }
    var f = arguments.callee.caller;
    do{
        var e = f.arguments[0];
        if(e && (e.constructor === Event || e.constructor===MouseEvent || e.constructor===KeyboardEvent)){
    　　　　return e;
        }
    }while(f=f.caller);
}
function getElementByAttr(tag,attr,value)
{
    var aElements=document.getElementsByTagName(tag);
    var aEle=[];
    for(var i=0;i<aElements.length;i++)
    {
        if(aElements[i].getAttribute(attr)==value)
            aEle.push( aElements[i] );
    }
    return aEle;
}
function ztree_modal(type, url, obj, callback){
	setting_modal.check.chkStyle = type;
	$.ajax({
		  type: 'POST',
		  url: url,
		  success: function(data){
			  var html = $('<div class="modal fade" id="tree-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">'
						+'  <div class="modal-dialog" role="document" style="width:360px;">'
						+'    <div class="modal-content">'
						+'      <div class="modal-header">'
						+'        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
						+'        <h4 class="modal-title" id="myModalLabel">树</h4>'
						+'      </div>'
						+'      <div class="modal-body ztree" id="tree-area">'
						+'      </div>'
						+'      <div class="modal-footer">'
						+'        <button type="button" class="btn btn-primary" id="submit-modal">确定</button>'
						+'        <button type="button" class="btn btn-default" data-dismiss="modal" id="colse-model">关闭</button>'
						+'      </div>'
						+'    </div>'
						+'  </div>'
						+'</div>');
			  html.modal("show");
			  var zTree ;
			  html.on('shown.bs.modal', function () {
				  zTree = $.fn.zTree.init(html.find("#tree-area"), setting_modal, eval(data));
			  });
			  html.find("#submit-modal").bind("click",function(){
				  var nodes = zTree.getCheckedNodes(true);
				  if(callback && callback!=undefined){
					  eval(callback+"(zTree, nodes, obj)");
				  }
			  });
		  },
		  dataType: 'text'
	});
}