package com.doyun.doyun.files.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FileDAO {
	@Inject
	SqlSessionTemplate sqlSession;

	public HashMap<String, Object> insertFiles(HashMap<String, Object> fileMap) {
		sqlSession.insert("fileDAO.insertFiles",fileMap);
		return fileMap;
	}
}
