/**
 * 提示框(bootstrop notify)二次封装
 * lu
 * 2017/9/18
 */
$(function () {
	window.Notify=function(){
		
		var _notify=function(options){
			var prompt=_prompt(options);
			
			var promptObj={  
	  		        type: prompt.type,
	  		      	offset:prompt.offset,
		        	delay: prompt.delay,  //提示框出现时间
		        	z_index:prompt.z_index
	  		    };
			if(options.icon_type!=""){
				promptObj.icon_type=options.icon_type;
			}
			if(options.otherImg){//包含图片
				promptObj.template= '<div data-notify="container" class="col-xs-11 col-sm-3 alert alert-minimalist" role="alert">'+
										'<img data-notify="icon" class="img-circle pull-left">'+
										'<span data-notify="title">{1}</span>'+
										'<span data-notify="message">{2}</span>'+
									'</div>';
			}
			
			$.notify({
						icon:prompt.icon,
		  		        title: prompt.title,  
		  		        message: prompt.msg 
  		    		},promptObj);
			
		};
		
		var _prompt = function (options) {
            var ops = {
            	msg: "提示内容",					//提示详情
                title: "<strong>提示!</strong>",	//提示开头标题
                type: "info",					//提示框类型即提示框颜色，info（普通，蓝色),danger(严重提示，红色),success(成功，绿色)，warning(警告，黄色);
                offset:{x:20,y:90},				//界面偏移距离，现在是统一的距离不要轻易改动
                icon:"",						//icon,图标，若有需要加图标，可用此属性，也可是图片的url(但请设置下面的icon_type属性为image)
                icon_type:"", 					//如果是图片，此值为image
                delay: 2000,  //提示框出现时间
    	        z_index:2000,	//弹窗出现层数
    	        otherImg:false
            };
            	
           $.extend(ops, options);
            
            return {
            	msg: ops.msg,  
                title: ops.title,
                type: ops.type,  
                offset:ops.offset,
                icon:ops.icon,
                icon_type:ops.icon_type,
                delay: ops.delay,  //提示框出现时间
    	        z_index:ops.z_index
            };
		};
		
		return {notify:_notify}
		
	}();
});