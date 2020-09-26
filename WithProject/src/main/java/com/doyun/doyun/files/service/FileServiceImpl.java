package com.doyun.doyun.files.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	FileDAO fileDAO;

	@Override
	public HashMap<String, Object>  insertFiles(HashMap<String, Object> fileMap) {
		return fileDAO.insertFiles(fileMap);
	}
}
