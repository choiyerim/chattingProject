package com.doyun.doyun.chat.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doyun.doyun.user.dto.FriendVO;
import com.doyun.doyun.user.dto.UserVO;

@Service
public class ChatServiceImpl implements ChatService{

	@Autowired
	ChatDAO chatDAO;
	
	@Override
	public List<FriendVO> selectUserFriendList(UserVO user) {
		return chatDAO.selectUserFriendList(user);
	}

	@Override
	public int selectFriendCnt(UserVO user) {
		return chatDAO.selectFriendCnt(user);
	}

	@Override
	public int selectFriendSeq(HashMap<String, Object> param) {
		return chatDAO.selectFriendSeq(param);
	}

	@Override
	public String selectChatroom(HashMap<String, Object> param) {
		return chatDAO.selectChatroom(param);
	}

	@Override
	public String insertChatroom(HashMap<String, Object> param) {
		return chatDAO.insertChatroom(param);
	}

	@Override
	public String selectFriendName(HashMap<String, Object> param) {
		return chatDAO.selectFriendName(param);
	}

	@Override
	public Map selectFriendName(String roomCode) {
		return chatDAO.selectFriendName(roomCode);
	}

}
