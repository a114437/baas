/**
 * 功能：处理表格数据选中(单选：singleSelect，多选：manySelect)
 * 作者：lu
 * 日期：2017/10/12
 */
$(function(){
	window.TableHandle=function(){
		
		var _singleSelect=function(tableId){
			 var dataID= $('#'+tableId).bootstrapTable('getSelections');
	    	  if(dataID.length==0){
	    		  Notify.notify({msg:"请选中一项内容！"});//提示框
	    		  return false;
	    	  }else if(dataID.length>1){
	    		  Notify.notify({msg:"请选中一项内容进行修改！", type: 'warning'});//提示框
	    		  return false;
	    	  }else if(dataID[0].id==null||dataID[0].id==""){
	    		  Notify.notify({msg:"选中的数据无效！", type: 'warning'});//提示框
	    		  return false;
	    	  }else{
	    		  return true;
	    	  }
		};
		
		var _manySelect=function(tableId){
			
			 var dataID= $('#'+tableId).bootstrapTable('getSelections');
	    	  if(dataID.length==0){
	    		  Notify.notify({msg:"请选中一项内容！"});//提示框
	    		  return false;
	    	  }else{
	    		  return true;
	    	  }
		};
		
		return {
			singleSelect:_singleSelect,//单选
			manySelect:_manySelect//多选
		}
	}();
});