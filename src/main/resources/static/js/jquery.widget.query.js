/**
 * dialog
 * 
 * @version 2.0
 * @author yaoyao
 * @date 2017/02/09
 */
;(function($){
	$.fn.extend({
		/**
		 * 追加绑定按钮函数到jQery
		 */
		'queryTable':function(options){
			//设置默认值
			options = $.extend({
				
			},options);
			var _self = this;
			var option = _self.bootstrapTable('getOptions');
			option.pageNumber = 1;
			_self.bootstrapTable('refresh', option);
		}

	});
	
})(jQuery);















