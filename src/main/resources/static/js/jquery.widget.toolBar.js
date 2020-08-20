/**
 *按钮定义
 * lu
 * 2017/7/26
 */
 ;(function($){
	 $.fn.extend({
		 'createToolBar':function(options){
				//设置默认值
				options = $.extend({
					buttons:[]
				},options);
				
				var _tool = this;
				var _items = options.buttons.items;
				
				
				if($.isArray(_items)&&_items.length>0){
					var length = _items.length;
					for(var i=0;i<length;i++){
						if($.inArray(_items[i].id, btnResource)==-1){
							continue;
						}
						_tool.append('<a  id="'+_items[i].id+'"  class="'+_items[i].iconCls+'" >'+_items[i].text+'</a>');
						$('#'+_items[i].id).off("click").on("click",_items[i].handler);
					}
				}
		 	},
		 /**
		 * remove toolBar
		 */
		'removeToolBar':function(){
			this.remove();	
		}
	 });
 })(jQuery);
 