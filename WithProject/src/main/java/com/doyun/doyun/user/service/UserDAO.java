package com.doyun.doyun.user.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doyun.doyun.user.dto.UserVO;

@Repository
public class UserDAO {
	@Inject
	private SqlSessionTemplate sqlSession;
	
	
	
	public int addUser(Map paramMap) {
		return sqlSession.insert("usersDAO.insertUsers",paramMap);
	}

	public String selectCheckId(Map paramMap) {
		String result=sqlSession.selectOne("usersDAO.selectCheckId",paramMap);
		return result;
	}

	public UserVO selectUserInfo(Map paramMap) {
		
		return sqlSession.selectOne("usersDAO.selectUserInfo", paramMap);
	}

	public void updateUserProfileImg(UserVO loginVO) {
		sqlSession.update("usersDAO.updateUserProfileImage",loginVO);
	}

	public HashMap<String, String> selectFilePath(int fileSeq) {
		return sqlSession.selectOne("usersDAO.selectFilePath", fileSeq);
	}

	public void updateUserProfileInfo(Map paramMap) {
		sqlSession.update("usersDAO.updateUserProfileInfo",paramMap);
	}

	public String selectUserId(Map paramMap) {
		return sqlSession.selectOne("usersDAO.selectUserId",paramMap);
	}

}
