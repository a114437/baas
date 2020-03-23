/*
 * 弹窗模态框
 * 作者：lu
 * 2017/7/25
 */
;(function($){
	$.fn.extend({
		/**
		 * 追加绑定按钮函数到jQery
		 */
		'createDialog':function(options){
			//设置默认值
			options = $.extend({
				id:'_modal',//id
				content: "提示内容", 
                title: "操作提示",
                height:'80',
				width:'300',
				closeBtn:true,
				buttons:[]
			},options);
			
			var _closeBtn = '';
			if(options.closeBtn){
				_closeBtn = '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>';
			}
			
			var _content='';
			
			if(options.contentUrl){
				$.ajax({
	                cache: true,
	                type: "POST",
	                dataType:"html",
	                url:options.contentUrl,
	                data:options.data,
	                async: false,
	                error: function(request) {
	                    alert("出错了");
	                },
	                success: function(data) {
	                	
	                	_content =''+data+'';
	                }
	            });
			}else{
				_content=options.content;
			}
			
			var _toolBar = options.buttons;
			var _concel = '';
			if($.isArray(_toolBar.items)&&_toolBar.items.length>0){
				
				var length = _toolBar.items.length;
				for(var i=0;i<length;i++){
					_concel = '<button type="button" id="'+_toolBar.items[i].id+'" class="'+_toolBar.items[i].iconCls+'">'+_toolBar.items[i].text+'</button>'+_concel;
				}
			}
			
			var winHeight = window.innerHeight;
			var _top=(winHeight-options.height-60-120)/2;
			if(_top<0){
				_top=0;
			}
			var _dialog ='<div class="modal fade" id="'+options.id+'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
							+'<div class="modal-dialog" style="width:'+options.width+'px;top:'+_top+'px;">'
								+'<div class="modal-content" style="width:'+options.width+'px;">'
									+'<div class="modal-header">'
										+''+_closeBtn+''
										+'<h4 class="modal-title">'+options.title+'</h4>'
									+'</div>'
									+'<div class="modal-body" style="width:'+(options.width-3)+'px;height:'+options.height+'px;overflow: auto;">'+_content+'</div>'
									+'<div class="modal-footer">'+_concel+'</div>'
								+'</div>'
							+'</div>'
						+'</div>';
			if($('body').find(".modal.fade[id="+options.id+"]")){
				$(".modal.fade[id="+options.id+"]").remove();
			}
			
			
			
			$('body').append(_dialog);
			/**
			 * dialog底部toolBar绑定函数
			 */
			if($.isArray(_toolBar.items)){
				var length = _toolBar.items.length;
				for(var i=0;i<length;i++){
					$('#'+_toolBar.items[i].id).bind("click",_toolBar.items[i].handler);
				}
			}
			
			$("#"+options.id).modal("show"); 
			
			/*	关闭后执行事件*/
			 $('#'+options.id).on('hide.bs.modal', function () {
				$(this).remove();
			});
		}
	});
	
})(jQuery);

function closeModal(modalId){
	$(modalId).modal('hide');
	/*modalId.remove();
	$(".modal-backdrop.fade.in").remove();*/
	
}

