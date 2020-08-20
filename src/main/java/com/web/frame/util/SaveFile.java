package com.web.frame.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

@Scope("prototype")
@Component
public class SaveFile {
	
		public static List<MultipartFile> getMultipartFile(HttpServletRequest request) {
			//定义一个集合，定义类型
			List<MultipartFile> list = new ArrayList<MultipartFile>();
			//创建一个通用的多部分解析器.  
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 判断 request 是否有文件上传,即多部分请求
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 取得上传文件
					MultipartFile file = multiRequest.getFile(iter.next());
					list.add(file);
				}
				
			}
			return list;
		}
	
		/**
		 * 解析request中的所有文件
		 * @param request
		 * @param realPath 
		 * @param realPath
		 * @param type
		 * @param date
		 * @param num 
		 * @return显示路径列表
		 */
		public List<Map<String, String>> multiFileList(HttpServletRequest request, String realPath) {
			
			List<Map<String, String>> filelist = new ArrayList<Map<String, String>>();
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 判断 request 是否有文件上传,即多部分请求
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 取得上传文件
					MultipartFile file = multiRequest.getFile(iter.next());
					// 数据封装操作 MultipartFile reqeust
					// 取得当前上传文件的文 件名称
					// logger.debug("图片上传：{}", JsonUtil.toString(map));
					try {
						String fileName = file.getOriginalFilename();
						String type = file.getContentType();
						String size = file.getSize()+"";//文件大小
						String id = Uuid.getUuid();
						String savepath = saveFile(file, realPath, id,fileName,type);
						Map<String, String>map = new HashMap<String, String>();
						map.put("ID", id);
						map.put("URL", savepath);
						map.put("NAME", fileName);
						map.put("TYPE", type);
						map.put("SIZE", size);
						filelist.add(map);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return filelist;
		}

		public String saveFile(MultipartFile file, String realPath, String id,String fileName, String type) throws Exception {

			//String fileName = file.getOriginalFilename();
			byte[] b = file.getBytes();
			
			/*if(type.contains("audio")) {
				MpegAudioFileReader mp=new MpegAudioFileReader();
				AudioInputStream audioInputStream = mp.getAudioInputStream(file.getInputStream());
				AudioFormat baseFormat=audioInputStream.getFormat();
				AudioFormat targetFormat=new AudioFormat(
		          AudioFormat.Encoding.PCM_SIGNED,
		          baseFormat.getSampleRate(),
		          16,
		          baseFormat.getChannels(),
		          baseFormat.getChannels() * 2,
		          baseFormat.getSampleRate(),
		          false);
		      audioInputStream=AudioSystem.getAudioInputStream(targetFormat, audioInputStream);
		      b =  IOUtils.toByteArray(audioInputStream);
			}*/
			
			// 得到保存的路径
			String tomcatPath = realPath + "/upLoadResource/";
			tomcatPath = tomcatPath + "/" + id + "/";
			File ff = new File(tomcatPath);
			ff.mkdirs();
			tomcatPath = tomcatPath + "/" + fileName;
			File f = new File(tomcatPath);
			OutputStream os = new FileOutputStream(f);
			os.write(b);
			os.close();
			
			String savePath = "/upLoadResource/" + id + "/"+ fileName;
			
//			Image srcFile = ImageIO.read(f);
//			
//			
//			if (srcFile == null || srcFile.getWidth(null) <= 0 || srcFile.getHeight(null) <= 0) {//非图片文件  
//				return savePath;  
//	        }  
//            int w = srcFile.getWidth(null);
//            int h = srcFile.getHeight(null);
//            BufferedImage tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//            tag.getGraphics().drawImage(srcFile, 0, 0, w, h, null);
//            FileOutputStream out = new FileOutputStream(f);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
//            /** 压缩质量 */
//            jep.setQuality(0.5f, true);
//            encoder.encode(tag, jep);
//            out.close();
            
			return savePath;
		}
		
		/**
		 * 删除文件夹及文件夹下的所有内容
		 * @param sPath
		 * @return
		 */
		public static boolean deleteDirectory(String sPath){
		    if (!sPath.endsWith(File.separator)) {  
		        sPath = sPath + File.separator;  
		    }  
		//	sPath = "C:/Program Files (x86)/Apache Software Foundation/Tomcat6.0.35/webapps/yframe/upLoadResource/03cc5fc138e24b0ebac56b72a75fc7fe";
		    File dirFile = new File(sPath);  
		    //如果dir对应的文件不存在，或者不是一个目录，则退出  
		    if (!dirFile.exists() || !dirFile.isDirectory()) { 
		    	System.out.println("文件夹不存在");
		        return false;  
		    }  
		    boolean flag = true;  
		    //删除文件夹下的所有文件(包括子目录)  
		    File[] files = dirFile.listFiles();  
		    for (int i = 0; i < files.length; i++) {  
		        //删除子文件  
		        if (files[i].isFile()) {  
		            flag = deleteFile(files[i].getAbsolutePath());  
		            if (!flag) break;  
		        } //删除子目录  
		        else {  
		            flag = deleteDirectory(files[i].getAbsolutePath());  
		            if (!flag) break;  
		        }  
		    }  
		    if (!flag) return false;  
		    //删除当前目录  
		    if (dirFile.delete()) {  
		        return true;  
		    } else {  
		        return false;  
		    }  
		}

		/**
		 * 删除文件
		 * @param sPath
		 * @return
		 */
		public static boolean deleteFile(String sPath) {  
		   boolean flag = false;
		   File file = new File(sPath);  
		    // 路径为文件且不为空则进行删除  
		    if (file.isFile() && file.exists()) {  
		        file.delete();  
		        flag = true;  
		    }  
		    return flag;  
		}
}
