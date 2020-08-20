package com.web.frame.service.system;

import java.awt.Image;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.web.frame.util.JsonFormat;
import com.web.frame.util.SaveFile;
import com.web.frame.util.Uuid;

/**
 * 文件上传
 * */
@Service
public class UploadService {

	@Autowired 
	private JsonFormat jsonFormat;
	@Autowired
	private SaveFile saveFile;
	/**
	 * 文件夹路径
	 */
	@Value("${FILEPATH}")
    private String folderPath;
	

	/**
	 * 删除文件
	 * @param request
	 * @param sourceId
	 * @param id
	 * @return
	 */
	public void fileDelete(HttpServletRequest request,
			String src) {
		
		String path = folderPath+src;
		SaveFile.deleteFile(path);
	}

	
	/** 
    * 通过读取文件并获取其width及height的方式，来判断判断当前文件是否图片，这是一种非常简单的方式。 
    *  
    * @param imageFile 
    * @return 
    */  
   public boolean isImage(File imageFile) {  
       if (!imageFile.exists()) {  
           return false;  
       }  
       Image img = null;  
       try {  
           img = ImageIO.read(imageFile);  
           if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {  
               return false;  
           }  
           return true;  
       } catch (Exception e) {  
           return false;  
       } finally {  
           img = null;  
       }  
   }

   /**
    * 文件上传
    * @param request
    * @return
    */
	public String uploadFile(HttpServletRequest request) {
		
		File folderFile = new File(folderPath);
		if(!folderFile.exists()||!folderFile.isDirectory()){
			folderFile.mkdirs();
		}
		List<Map<String, String>> list = saveFile.multiFileList(request,folderPath);
		String url = list.get(0).get("URL");
		String name = list.get(0).get("NAME");
		return url+","+name;
	}  
}

