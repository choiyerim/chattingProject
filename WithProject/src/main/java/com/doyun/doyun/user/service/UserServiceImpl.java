package com.doyun.doyun.user.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doyun.doyun.repositorys.user.UserRepository;
import com.doyun.doyun.user.dto.UserVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDAO;
	
//	@Inject
//	UserRepository repo;

	@Override
	public int addUser(Map paramMap) {
		int result=userDAO.addUser(paramMap);
		return result;
	}

	@Override
	public String selectCheckId(Map paramMap) {
		return userDAO.selectCheckId(paramMap);
	}

	@Override
	public UserVO selectUserInfo(Map paramMap) {
		return userDAO.selectUserInfo(paramMap);
	}

	@Override
	public void updateUserProfileImg(UserVO loginVO) {
		userDAO.updateUserProfileImg(loginVO);
	}

	@Override
	public HashMap<String, String> selectFilePath(int fileSeq) {
		return userDAO.selectFilePath(fileSeq);
	}

	@Override
	public void updateUserProfileInfo(Map paramMap) {
		userDAO.updateUserProfileInfo(paramMap);
	}

	@Override
	public String selectUserId(Map paramMap) {
		return userDAO.selectUserId(paramMap);
	}

	@Override
	public void insertUserFriend(Map param) {
		userDAO.insertUserFriend(param);
	}

//	@Override
//	public UserVO selectUserLoginInfo(Map paramMap) {
//		UserVO user=repo.fineByUserIdAndPassword(paramMap.get("userId").toString(), paramMap.get("password").toString());
//		return user;
//	}
}
