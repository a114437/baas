//图片上传基类
//接口路径，文件，上传成功后的回调函数
function FileUploader(url, formData, fn) {
	$.ajax({
		url : url,
		type : "POST",
		processData : false,
		async:false,
		contentType : false,
		data : formData,
		success : function(data) {
			fn(data);
		}
	});
}

//文件上传
function fileUpload(files,url,fn){
	
	if (!window.FormData) {
		console.log("浏览器不支持");
		return false;
	}
	
	var formData = new FormData();
	var num = 0;
	for ( var i = 0; i < files.length; i++) {
		var file = files[i];
		console.log(file.name + "\t" + file.type + "\t" + file.size + "B");
		formData.append(num++, file);
	}
	if (!formData || num < 1) {
		console.log("未选择文件");
		return false;
	}
	
	new FileUploader(url, formData, function(d) {
		var data=JSON.parse(d);
		if (data.ok == 'true') {
			fn(data);
		} else {
			console.log("图片上传失败");
		}
	});
	
}





