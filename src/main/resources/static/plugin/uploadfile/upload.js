(function($) {
  $.extend($.fn, {
    fileUpload: function(opts) {
      this.each(function() {
        var $self = $(this);
        var doms = {
          "fileToUpload": $self.find(".fileUpload"),
          "sourceId":$self.find(".form_img")
        };
        var funs = {
          //选择文件，获取文件大小，也可以在这里获取文件格式，限制用户上传非要求格式的文件
          "fileSelected": function() {
            funs.uploadFile();
          },
          //异步上传文件
          uploadFile: function() {
            var fd = new FormData();//创建表单数据对象
            var files = (doms.fileToUpload)[0].files;
            var count = files.length;
            for (var index = 0; index < count; index++) {
              var file = files[index];
              fd.append(index, file);//将文件添加到表单数据中
            }
            var xhr = new XMLHttpRequest();
            xhr.addEventListener("load", funs.uploadComplete, false);
            xhr.addEventListener("error", opts.uploadFailed, false);
            xhr.open("POST", opts.url+"?sourceID="+doms.sourceId.val());
            xhr.send(fd);
          },
          "uploadComplete": function(evt) {
        	  var data = evt.target.responseText;
        	  data=JSON.parse(data);
        	  var sourceId = data.sourceId;//资源id
        	  doms.sourceId.val(sourceId);//表单赋值
        	  var filelist = data.filelist;//所有的上传文件资源
              opts.success(filelist,$self);//回调成功的函数
          }
        };
        doms.fileToUpload.on("change", function() {
          funs.fileSelected();
        });
      });
    }
  });
})(Zepto);