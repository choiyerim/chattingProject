package com.doyun.doyun.repositorys.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doyun.doyun.user.dto.UserVO;

public interface UserRepository extends JpaRepository<UserVO, Integer> {

	//ID로 유저 찾기
	public UserVO findByUserId(String userId);
	
	//ID,PWD로 유저 찾기(로그인)
	public UserVO fineByUserIdAndPassword(String userId,String password);
	
	
	
	
	
}
