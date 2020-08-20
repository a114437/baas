/**
 * 简单弹窗
 * lu
 * 2107/9/18
 */
$(function () {
	 window.Modal = function () {
		 
		 var winHeight = window.innerHeight;
			console.log(winHeight);
			var _top=(winHeight-30-60-120)/2;
			if(_top<0){
				_top=0;
			}
			
		 var alertHtml='<div class="modal fade" id="[Id]" tabindex="-1"  style="top:'+_top+'px; padding:0; margin:0 auto;width:[Width]px;height:[Height]px;"role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
							+'<div class="modal-dialog" style="width:100%;margin:0;">'
							+'<div class="modal-content">'
								+'<div class="modal-header">'
									+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'
									+'<h4 class="modal-title">[Title]</h4>'
								+'</div>'
								+'<div class="modal-body" style="overflow: auto;"><p>[Message]</p></div>'
								+'<div class="modal-footer">'
								+'<button type="button" class="btn btn-primary ok" data-dismiss="modal">[BtnOk]</button>' 
					            +'<button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>' 
					            +'</div>'
							+'</div>'
						+'</div>'
					+'</div>';
		 
		 var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
		 
		 var _alert = function (options) {
	            var id = _dialog(options);
	            var modal = $('#' + id);
	            modal.find('.ok').removeClass('btn-success').addClass('btn-primary');
	            modal.find('.cancel').hide();

	            return {
	                id: id,
	                on: function (callback) {
	                    if (callback && callback instanceof Function) {
	                        modal.find('.ok').click(function () {
	                        	callback(true); 
	                        });
	                    }
	                },
	                hide: function (callback) {
	                    if (callback && callback instanceof Function) {
	                        modal.on('hide.bs.modal', function (e) {
	                            callback(e);
	                        });
	                    }
	                }
	            };
	        };
		 
		 var _confirm = function (options) {
	            var id = _dialog(options);
	            var modal = $('#' + id);
	            modal.find('.ok').removeClass('btn-primary').addClass('btn-success');
	            modal.find('.cancel').show();

	            return {
	                id: id,
	                on: function (callback) {
	                    if (callback && callback instanceof Function) {
	                        modal.find('.ok').click(function () { 
                        		callback(true); 
                        	});
	                    }
	                },
	                hide: function (callback) {
	                    if (callback && callback instanceof Function) {
	                        modal.on('hide.bs.modal', function (e) {
	                            callback(e);
	                        });
	                    }
	                }
	            };
	        };
	        
	        var _getId = function () {
	            var date = new Date();
	            return 'mdl' + date.valueOf();
	        }
	        
	        var _dialog = function (options) {
	            var ops = {
	                id:modalId,
	            	msg: "提示内容",
	                title: "操作提示",
	                btnok: "确定",
	                btncl: "取消",
	                width: 300,
	                height:190,
	                auto: false,
	                cancel:function(){}
	            };

	            $.extend(ops, options);
	            
	            var modalId = _getId();
	            
	            var html = alertHtml.replace(reg, function (node, key) {
	                return {
	                    Id: modalId,
	                    Title: ops.title,
	                    Message: ops.msg,
	                    BtnOk: ops.btnok,
	                    BtnCancel: ops.btncl,
	                    Width:ops.width,
	                    Height:ops.height
	                }[key];
	            });
	            $('body').append(html);
	            $("#"+modalId).modal("show"); 
	            $('#' + modalId).on('hide.bs.modal', function (e) {
	                /*$(this).parent('.modal-scrollable').next().remove();
	                $(this).parent('.modal-scrollable').remove();*/
	               /* $('body').modalmanager('toggleModalOpen');*/
	            	$(this).remove();
	            	ops.cancel();
	            });

	            return modalId;
	        };
	        
	        return {
	        	alert:_alert,
	            confirm: _confirm
	        }
	 }();
});