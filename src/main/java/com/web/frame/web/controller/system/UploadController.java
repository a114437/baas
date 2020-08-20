package com.web.frame.web.controller.system;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.frame.entity.base.ResponseBase;
import com.web.frame.service.system.UploadService;
import com.web.frame.util.Out;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 上传文件
 * @author lj
 */
@Controller
@RequestMapping(value = "upload")
//@Api(tags= {"文件上传接口"},description="相关接口")
public class UploadController{

	@Autowired
	private UploadService uploadService;
	/**
	 * 文件夹路径
	 */
	@Value("${FILEPATH}")
    private String folderPath;
	
	@Autowired
	private Out out;
	
	
	/**
	 * 文件单独上传（仅返回路径）
	 * @param request
	 * @param response
	 */
	@PostMapping("uploadOneFile")
	@ResponseBody
//	@ApiOperation(value="1、文件上传")
	public ResponseEntity<String> uploadOneFile(HttpServletRequest request,HttpServletResponse response){
		
		String data = uploadService.uploadFile(request);
		String[] arr = data.split(",");
		
		return ResponseBase.createResponseOnlyData("{\"ok\":\"true\",\"URL\":\""+arr[0]+"\",\"NAME\":\""+arr[1]+"\"}", HttpStatus.OK);
	}
	
	/**
	 * 文件删除
	 * @param request
	 * @param response
	 * @param sourceId
	 * @param id
	 */
	@RequestMapping("deleteFile")
	public void deleteFile(HttpServletRequest request,HttpServletResponse response,String src){
		
		try {
			uploadService.fileDelete(request,src);
			out.outTrue(response);
		} catch (Exception e) {
			e.printStackTrace();
			out.outFalse(response);
		}
	}
	
	/**
	 * 加载文件
	 * @param request
	 * @param response
	 * @param path
	 */
	@GetMapping("loadFile")
//	@ApiOperation(value="2、文件显示")
	public void loadFile(HttpServletRequest request,HttpServletResponse response,
			@ApiParam(value="文件路径") @RequestParam String path){
		
		try {
			File file = new File(folderPath+path);// path是根据日志路径和文件名拼接出来的
			if(!file.exists()){
				return;
			}
			String filename = file.getName();// 获取日志文件名称
			InputStream fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			response.reset();
			if(uploadService.isImage(file)){//判断是否为图片
				response.setContentType("text/html; charset=UTF-8");
				response.setContentType("image/jpeg");
			}else{
				// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
				response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"),"iso8859-1"));
				response.addHeader("Content-Length", "" + file.length());
				response.setContentType("application/octet-stream");
			}
			OutputStream os = new BufferedOutputStream(response.getOutputStream());
			os.write(buffer);// 输出文件
			os.flush();
			os.close();
		} catch (Exception e) {
		}
	}
	
}








