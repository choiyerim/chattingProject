package com.doyun.doyun.user.service;

import java.util.HashMap;
import java.util.Map;

import com.doyun.doyun.user.dto.UserVO;

public interface UserService {

	int addUser(Map paramMap);

	String selectCheckId(Map paramMap);

//	UserVO selectUserLoginInfo(Map paramMap);

	UserVO selectUserInfo(Map paramMap);

	void updateUserProfileImg(UserVO loginVO);

	HashMap<String, String> selectFilePath(int fileSeq);

	void updateUserProfileInfo(Map paramMap);

	String selectUserId(Map paramMap);

}
