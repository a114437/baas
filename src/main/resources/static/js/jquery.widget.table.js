/**
 * 初始化 BootStrap Table 的封装
 *
 * @author lu
 */
;(function($){
	$.fn.extend({
		'createTable':function(options){
			//设置默认值
			options = $.extend({
					id:'dataGridId',
	                method: 'post',                    	//请求方式（*）
	                classes:"table  table-hover table-no-bordered",
	                contentType: 'application/x-www-form-urlencoded',//这里我就加了个utf-8
	                striped: true,                      //是否显示行间隔色
	                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	                pagination: true,                   //是否显示分页（*）
	                sortable: true,                    //是否启用排序
	                sortOrder: "asc",                   //排序方式
	                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
	                pageNumber:1,                       //初始化加载第一页，默认第一页
	                pageSize: 10,                       //每页的记录行数（*）
	                pageList: [10, 25, 50, 100, 200, 300, 400, 500],        //可供选择的每页的行数（*）
	                strictSearch: true,
	                clickToSelect: true,                //是否启用点击选中行
	                uniqueId: "id",                     //每一行的唯一标识，一般为主键列
	                cardView: false,                    //是否显示详细视图
	                detailView: false                 //是否显示父子表
			},options);

			/**
			 * bootstrapTable加载数据表格
			*/
		$('#'+options.id).bootstrapTable({
				
				url:options.url,	/** 从远程站点请求数据的 URL **/
                method: options.method,                      //请求方式（*）
                classes:options.classes,
                contentType: options.contentType,			//这里我就加了个utf-8
                striped: options.striped,                	//是否显示行间隔色
                cache: options.cache,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: options.pagination,             //是否显示分页（*）
                sortable: options.sortable,                 //是否启用排序
                sortOrder: options.sortOrder,               //排序方式
                queryParams: function(params){
                	var option = $('#'+options.id).bootstrapTable('getOptions');
                	var arr = $("#" + options.formId).serializeArray();
                	var page = params.offset/params.limit+1;
                	if(option.pageNumber!= undefined){
                		page = option.pageNumber;
                	}
                	var jsonStr = "";
	                jsonStr += '{';
	                for (var i = 0; i < arr.length; i++) {
	                    jsonStr += '"' + arr[i].name + '":"' + arr[i].value + '",';
	                }
	                var sortName = option.sortName==undefined?"":option.sortName;
	                var sortOrder = option.sortOrder==undefined?"":option.sortOrder;
	                jsonStr += '"sortName":"'+sortName+'",'+'"sortOrder":"'+sortOrder+'",';
	                jsonStr += '"page":"'+page+'",'+'"rows":"'+params.limit+'"}';
	                var json = JSON.parse(jsonStr);
                	return json; 
                },//传递参数（*）
                sidePagination: options.sidePagination,     //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:options.pageNumber,              //初始化加载第一页，默认第一页
                pageSize: options.pageSize,                 //每页的记录行数（*）
                pageList: options.pageList,        			//可供选择的每页的行数（*）
                strictSearch: options.strictSearch,
                clickToSelect: options.clickToSelect,       //是否启用点击选中行
                uniqueId: options.uniqueId,                 //每一行的唯一标识，一般为主键列
                cardView: options.cardView,                 //是否显示详细视图
                detailView: options.detailView,             //是否显示父子表
                onLoadSuccess:options.onLoadSuccess         //数据表格加载完成
			});	
			
		}	
	});
})(jQuery);



/* 序号 */
function indexFormatter(value, row, index) {  
    return index+1;  
} 

