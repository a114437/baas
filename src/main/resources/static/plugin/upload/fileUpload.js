//文件上传
$(function(){
	
	//-----------------------------多张图片处理 开始---------------------------------------
	 //查询已保存的文件
	 $('.file_form').each(function(){
	   	var $this = $(this);
	   	var sourceId = $(this).val();
	   	if(sourceId!=''&&sourceId!=null){
	   		$.ajax({
	   			type:"POST",
	   			data:{
	   				sourceId:sourceId
	   			},
	   			url:'/upload/loadSource',
	   			dataType:'json',
	   			async:false,
	   			success:function(data){
	   				for(var i = 0 ; i < data.length;i++){
	   					var id = data[i].ID;
	   					var url = "/upload/loadFile?path="+data[i].URL;
	   					var fileName = data[i].NAME;
	   					var item = ['				<div class="fileItem">',
	   					            '					<div class="imgShow" data-url="'+url+'" data-id="'+id+'" data-path="'+data[i].URL+'">',
	   					            '						<img src="'+url+'"> ',
	   					            '					</div>',
	   					            '					<div class="status file_delete">',
	   					            '						<i class="iconfont icon-shanchu"></i>',
	   					            '					</div> ',
	   					            '					<div class="fileName">'+fileName+'</div>',
	   					            '				</div>'].join("");
	   					
	   					$("#"+$this.attr("data-id")).find(".box").append(item);
	   				}
	   			}
	   		});
	   	}
    });
	
	//上传按钮点击事件
	$('.uploadBtn').on('click',function(){
		$(this).siblings('.file_upload').click();
	});
	//文件上传
	$('.file_upload').on('change',function(){
		var $this = $(this);
		var files = $this.get(0).files;
		var sourceID = $this.siblings(".file_form").val();
		if(sourceID == undefined){
			sourceID = "";
		}
		fileUpload(files,'/upload/uploadFile?sourceID='+sourceID,function(data){
			var sourceId = data.sourceId;//资源id
			$this.attr('data-source',sourceId);
			var filelist = data.filelist;//所有的上传文件资源
			for(var i = 0 ; i < filelist.length;i++){
				var fileName = filelist[i].NAME;//文件名
				var url = "/upload/loadFile?path="+filelist[i].URL;//文件保存路径
				var item = ['				<div class="fileItem">',
				            '					<div class="imgShow" data-url="'+url+'" data-id="'+filelist[i].ID+'" data-path="'+filelist[i].URL+'">',
				            '						<img src="'+url+'"> ',
				            '					</div>',
				            '					<div class="status file_delete">',
				            '						<i class="iconfont icon-shanchu"></i>',
				            '					</div> ',
				            '					<div class="fileName">'+fileName+'</div>',
				            '				</div>'].join("");
				
				$("#"+$this.attr("data-id")).find(".box").append(item);
				$this.siblings('.file_form').attr('data-src',filelist[i].URL);
			}
			$this.siblings('.file_form').val(sourceId);//表单赋值
		});
	});
	
	//打开文件
	$('body').off("click").on('click','.imgShow',function(){
		window.open($(this).attr('data-url'));
	});
	
	//文件删除
	$('body').on('click','.file_delete',function(e){
		
		var $this = $(this);
		var $li = $(this).siblings(".imgShow");
		var id = $li.attr('data-id');
		var src = $li.attr('data-path');
		$.ajax({
			type:"POST",
			data:{
				id:id,
				src:src
			},
			url:"/upload/deleteFile",
			async:false,
			success:function(data){
				$this.parent().remove();
			}
		});
		
		e.stopPropagation();
	});
	
	//-----------------------------多张图片处理 结束---------------------------------------
	
	//-------------------------------单张图片处理  开始--------------------------------------------
	 //查询已保存的文件
	 $('.file_form_item').each(function(){
		var $this = $(this);
    	if($this.val() == ""){
    		return;
    	}
		var url = "/upload/loadFile?path="+$this.val();
		var arr = $this.val().split("/");
		var fileName = arr[arr.length-1];
		var item = ['				<div class="fileItem">',
		            '					<div class="imgShow" data-url="'+url+'" data-path="'+$this.val()+'">',
		            '						<img src="'+url+'"> ',
		            '					</div>',
		            '					<div class="status file_delete_item">',
		            '						<i class="iconfont icon-shanchu"></i>',
		            '					</div> ',
		            '					<div class="fileName">'+fileName+'</div>',
		            '				</div>'].join("");
			
		$("#"+$this.attr("data-id")).find(".box").append(item);
   });
	
	//上传按钮点击事件
	$('.uploadBtn_item').on('click',function(){
		$(this).siblings('.file_upload_item').click();
	});
	
	//文件上传（单张，直接保存路径）
	$('.file_upload_item').on('change',function(){
		var $this = $(this);
		var files = $this.get(0).files;
		if($("#"+$this.attr("data-id")).find(".fileItem").length>0){
			alert("请先删除已有图片");
			return;
		}
		
		fileUpload(files,'/upload/uploadOneFile',function(data){
			var fileName = data.NAME;
			var url = data.URL;
			url = "/upload/loadFile?path="+url;//文件保存路径
			
			var item = ['				<div class="fileItem">',
			            '					<div class="imgShow" data-url="'+url+'" data-path="'+data.URL+'">',
			            '						<img src="'+url+'"> ',
			            '					</div>',
			            '					<div class="status file_delete_item">',
			            '						<i class="iconfont icon-shanchu"></i>',
			            '					</div> ',
			            '					<div class="fileName">'+fileName+'</div>',
			            '				</div>'].join("");
			
			$("#"+$this.attr("data-id")).find(".box").append(item);
			$this.siblings('.file_form_item').attr('data-src',url);
			$this.siblings('.file_form_item').val(data.URL);//表单赋值
		});
	});
	
	//打开文件
	$('body').on('click','ul.file_text_item li',function(){
		window.open($(this).attr('data-url'));
	});
	
	//文件删除
	$('body').on('click','.file_delete_item',function(e){
		
		var $this = $(this);
		var $li = $(this).siblings(".imgShow");
		var src = $li.attr('data-path');
		$.ajax({
			type:"POST",
			data:{
				src:src
			},
			url:"/upload/deleteFile",
			async:false,
			success:function(data){
				var dataId = $this.parents(".fileUploadContent").attr("id");
				//if($(this).parents(".fileUploadContent").find(".fileItem").length==0){
				$(".file_form_item[data-id='"+dataId+"']").val("");
				//}
				$this.parent().remove();
			}
		});
		
		e.stopPropagation();
	});
	
	//--------------------------------单张图片处理  结束-------------------------------------------
	
	
});