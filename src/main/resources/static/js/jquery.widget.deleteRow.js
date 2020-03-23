/**
 * dialog(删除表格数据)
 * 
 * @version 2.0
 * @author lu
 * @date 2017/02/09
 */
;(function($){
	$.fn.extend({
		/**
		 * 追加绑定按钮函数到jQery
		 */
		'deleteRow':function(options){
			//设置默认值
			options = $.extend({
				url:'',
				tableUrl:''
			},options);
			
			
			var winHeight = window.innerHeight;
			var _top=(winHeight-180-60-120)/2;
			
			/*选择条数*/
			var _self = this;
			var rows = _self.bootstrapTable('getSelections');
			
			var hint = options.hint == undefined?'确定删除'+rows.length+'条记录吗？':options.hint;
			var handle = options.hint == undefined?"删除":"处理";
			
			var _modal='<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
						+'<div class="modal-dialog" style="width: 300px;top:'+_top+'px;"">'
							+'<div class="modal-content">'
								+'<div class="modal-header">'
									+'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'
										+'<h4 class="modal-title" id="myModalLabel">操作提示</h4>'
								+'</div>'
								+'<div class="modal-body">'+hint+'</div>'
								+'<div class="modal-footer">'
									+'<button type="button" class="btn btn-default" id="btnOk" >确定</button>'
									+'<button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>'
								+'</div>'
							+'</div><!-- /.modal-content -->'
						+'</div><!-- /.modal -->';
			
			
			
			var row=$.map(rows,function(row){
				return row ;
			});
			
			var ids="";
			var delParam = options.delParam;//自定义删除参数
			var delParamVal="";//自定义删除参数的值
			for(var i=0;i<row.length;i++){
				if(row.length==1 || row.length=="1"){
					ids=row[i].id;
					delParamVal=row[i][delParam];
				}else{
					ids+=row[i].id+",";
					delParamVal+=row[i][delParam]+",";
				}
			}
			/*row(mychart1).length==0 || row(mychart1).length=="0"*/
			/*_self.datagrid('clearSelections');*/
			if(ids==undefined){
				ids = "";
			}
			if(delParamVal == undefined){
				delParamVal = "";
			}
			if(ids.length==0&&delParamVal.length==0){
				
				$.notify({  
	  		        title: '<strong>提示!</strong>',  
	  		        message:'请选中至少一项内容！'  
	  		    },{  
	  		        type: 'info',
	  		      	offset:{
		        			x:20,
		        			y:85
		        		},
		        		delay: 2000
	  		    });
				
			}else{
				if($("#deleteModal")){
					$("#deleteModal").remove();
				}
				$('body').append(_modal);
				$('#deleteModal').modal('show');
			}
			$('#deleteModal').on("click","#btnOk",function(){
				$.ajax({
	 	              cache: true,
	 	              type: "POST",
	 	              url:''+options.url+'', 
	 	              data:{
	 	            	  ids:ids,
	 	            	  delParam:delParamVal
	 	              },
	 	              async: false,
	 	              error: function(request) {
	 	                  alert("出错了！");
	 	              },
	 	              success: function(data) {
						 if(data.success){
							 $.notify({  
	  		        				title: '<strong>提示!</strong>',  
		  	  		    			message:handle+'成功！'  
		  		    			},{  
		  		        			type: 'success',
		  		    		      	offset:{
		  			        			x:20,
		  			        			y:85
		  			        		},
		  			        		delay: 2000 
		  		    			});
						 }else{
							 $.notify({  
	  		        				title: '<strong>提示!</strong>',  
		  	  		    			message:handle+'失败！'  
		  		    		 },{  
		  		        			type: 'danger',
		  		    		      	offset:{
		  			        			x:20,
		  			        			y:85
		  			        		},
		  			        		delay: 2000 
		  		    		 });
						 }
	 	            	 _self.bootstrapTable('refresh',{url:options.tableUrl}); 
	 	            	
	 	            	$("#deleteModal").modal('hide');
	 	            	
	 	              }
	 	         });
			});
			
			
			/*	关闭后执行事件*/
			 $('#'+options.id).on('hide.bs.modal', function () {
				$(this).remove();
			});
		}

	});
	
})(jQuery);














