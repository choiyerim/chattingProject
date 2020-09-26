package com.doyun.doyun.files.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.doyun.doyun.files.service.FileService;
import com.doyun.doyun.user.dto.UserVO;
@Controller
public class FileUtils {
	Properties prop=new Properties();
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	FileService fileService;

	
	public int uploadFile(HttpServletRequest request, MultipartFile files,String pathName) throws IOException {
		UserVO loginVO=(UserVO) request.getSession().getAttribute("loginVO");
		String path=request.getSession().getServletContext().getRealPath("/upload/");
		if(loginVO.getImageUrl()!=null) {
			String delPath=path+(loginVO.getImageUrl().replace("/upload/", "").replace("/", "\\"));
			System.out.println(delPath);
			File delFile=new File(delPath);
			if(delFile.exists()) {
				delFile.delete();
			}
		}
		FileReader read;
		ServletContext sc=request.getSession().getServletContext();
		try {
			read = new FileReader(sc.getRealPath(sc.getInitParameter("globals")));
			this.prop.load(read);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		int fileSeq=0;
		
		String originalFileName=files.getOriginalFilename();
		String extension=originalFileName.substring(originalFileName.lastIndexOf("."));
		String saveFilePath=prop.getProperty(pathName);
		String saveFileName=System.currentTimeMillis()+extension;
		
		File file=new File(path+saveFilePath+saveFileName);
		if(!file.exists()) {
			file.mkdir();
		}
		files.transferTo(file);
		
		
		HashMap<String, Object> fileMap=new HashMap<String, Object>();
		fileMap.put("saveFilePath","/upload/"+saveFilePath);
		fileMap.put("saveFileName", saveFileName);
		fileMap.put("originalFileName", originalFileName);
		fileMap.put("userSeq", String.valueOf(loginVO.getUserSeq()));
		
		HashMap<String, Object> result=fileService.insertFiles(fileMap);
		int res=Integer.parseInt(result.get("fileSeq").toString());
		
		return res;
	}




}
