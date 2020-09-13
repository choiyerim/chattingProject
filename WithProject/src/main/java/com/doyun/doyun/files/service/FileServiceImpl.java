package com.doyun.doyun.files.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	FileDAO fileDAO;

	@Override
	public int insertFiles(HashMap<String, String> fileMap) {
		return fileDAO.insertFiles(fileMap);
	}
}
