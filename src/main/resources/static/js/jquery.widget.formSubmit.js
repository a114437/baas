/**
 * form表单提交，包括验证
 * 作者：lu
 * 时间：2017/10/12
 */
;(function($){
	$.fn.extend({
		/*
		 * ajax提交表单，可控制是否加验证
		 * 
		 * 参数：
		 * 	validator:true,//判断表单是否有验证，默认为true为加验证，false为不加验证
		 * 	url:"",//提交ajax地址url,必填
		 * 	type:"POST",//提交方式type="post","get"
		 *	data:$("#defaultForm").serialize(),  //如果需要自己定义data，可传入此参数，若默认为提交整个from，可以不加此参数，插件默认提交from，提交的参数可以是字符串也可以是json，例如{"id":1}
		 *	success:function(data){},//成功执行函数
		 *	error:function(data){},//失败执行函数
		 *	ajaxOther:{}//ajax其他参数，若插件内定义参数不够可用此参数扩展
		 * */
		
		'formSubmit':function(options){
			options=$.extend({
				validator:true,//是否有表单验证true,false
				url:"",//提交ajax地址url,必填
				type:"POST",//提交方式type="post","get"
				success:function(data){console.log("成功啦！"+data)},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
				       console.log(XMLHttpRequest.status);
				       console.log(XMLHttpRequest.readyState);
				       console.log(textStatus);
				     },
				ajaxOther:{}//ajax其他参数
			},options);
			
			
			var _self = this;//表单from
			var validate=false;//验证是否通过
			
			if(options.validator){ //如果表单需要验证
				var bootstrapValidator = _self.data('bootstrapValidator');//获取表单对象
				bootstrapValidator.validate();//手动触发验证
				
				if(bootstrapValidator.isValid()){//验证
					validate=true;
				}else{
					validate=false;
				}
			}
			if(options.validator){//如果表单需要验证
				if(validate){ 
					submitFu();
					}
			}else{ 
				submitFu(); 
				}
			
			
			function submitFu(){//表单提交的方法

				var ajaxData=_self.serialize(); //表单提交默认是序列化当前的from
				
				if(options.data){ //先判断一下data是否存在，data为用户自定义提交的参数
					ajaxData=options.data;//如果用户自己定义这个data，便替换此data
				}
				
				var sumbitData={
				                type: options.type,
				                url:options.url,
				                data:ajaxData,
				                error: options.error,
				                success: options.success
				            };//ajax提交基本参数
				
				var endData={};//最终ajax提交参数
				
				if(!isEmptyObject(options.ajaxOther)){
					endData=$.extend(sumbitData,options.ajaxOther);//如果需要传入其他参数,整合一下
				}else{
					endData=sumbitData;
				}
				
				$.ajax(endData);//ajax提交
			}
			
			
			function isEmptyObject(obj){//判断对象是否为空
				     for(var key in obj){
				         return false;
				     };
				     return true;
				 };
				 
		}
	});
})(jQuery);