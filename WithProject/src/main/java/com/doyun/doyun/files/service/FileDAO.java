package com.doyun.doyun.files.service;

import java.util.HashMap;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FileDAO {
	@Inject
	SqlSessionTemplate sqlSession;

	public int insertFiles(HashMap<String, String> fileMap) {
		return sqlSession.insert("fileDAO.insertFiles",fileMap);
	}
}
