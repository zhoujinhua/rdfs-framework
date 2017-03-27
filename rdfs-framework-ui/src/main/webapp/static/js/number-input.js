(function($){
		     //扩展数学函数
		    $.Math = {
		        //保留两位小数   
		        //功能：将浮点数四舍五入，取小数点后2位  
		        toDecimal : function (x) {
		            var f = parseFloat(x);
		            if (isNaN(f)) {
		                return;
		            }
		            f = Math.round(x * 100) / 100;
		            return f;
		        },
		        //强制保留2位小数，如：2，会在2后面补上00.即2.00  
		        toDecimal2 : function (x) {
		            var f = parseFloat(x);
		            if (isNaN(f)) {
		                return false;
		            }
		            var f = Math.round(x * 100) / 100;
		            var s = f.toString();
		            var rs = s.indexOf('.');
		            if (rs < 0) {
		                rs = s.length;
		                s += '.';
		            }
		            while (s.length <= rs + 2) {
		                s += '0';
		            }
		            return s;
		        },
		        //保留任意位小数
		        fomatFloat : function (src, pos) {
		            return Math.round(src * Math.pow(10, pos)) / Math.pow(10, pos);
		        }
		    }
		    //输入辅助
		    $.Input = {
		        //整数限定,指定是否允许为负数
		        BoundInt: function (input_id,negative) {
		            var allowkeycode = [8, 37, 39,  48, 49, 50, 51, 52, 53, 54, 55, 56, 57];
		            $(input_id).bind("keypress", function (e) {
		                var event = e || window.event;
		                if (!event.keyCode || event.keyCode == 0) {
		                    event.keyCode = event.which;
		                }
		                var value = $(this).val();
		                 if (allowkeycode.indexOf(event.keyCode) >= 0) {
		                    return true;
		                 } else {
		                     //允许负数
		                     if (negative&&event.keyCode==45) {
		                         if (value.length == 0 || !(value.indexOf('-') >= 0)) {
		                             return true;
		                         }
		                     }
		                    return false;
		                }
		            }).focusout(function () {
		                var input = $(this).val();
		                var num = parseInt(input,10);
		                if (isNaN(num)) {
		                    num = 0;
		                }
		                $(this).val(num);
		            }).css({"ime-mode":  "disabled"});
		        },
		        //小数限定，可以指定保留小数位,是否允许负数
		        BoundFloat: function (input_id, pos, negative) {
		            var allowkeycode = [8,37,39,46,48,49,50,51,52,53,54,55,56,57];
		            $(input_id).bind("keypress", function (e) {
		                var event = e || window.event;
		                if (!event.keyCode || event.keyCode == 0) {
		                    event.keyCode = event.which;
		                }
		                var value = $(this).val();
		                if (allowkeycode.indexOf(event.keyCode)>=0) {
		                    return true;
		                } else {
		                    //允许负数
		                    if (negative && event.keyCode == 45) {
		                        if (value.length == 0||!(value.indexOf('-')>=0)) {
		                            return true;
		                        }
		                    }
		                    return false;
		                }
		            }).focusout(function () {
		                var input = $(this).val();
		                var num = parseFloat(input);
		                if (isNaN(num)) {
		                    num = 0;
		                }
		                if (pos) {
		                    num = $.Math.fomatFloat(num, pos);
		                }
		                $(this).val(num);
		            }).css({"ime-mode":  "disabled"});
		        }
		    }
		})(jQuery);