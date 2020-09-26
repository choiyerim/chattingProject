package com.doyun.doyun.chat.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.doyun.doyun.user.dto.FriendVO;
import com.doyun.doyun.user.dto.UserVO;

public interface ChatService {

	List<FriendVO> selectUserFriendList(UserVO user);

	int selectFriendCnt(UserVO user);

	int selectFriendSeq(HashMap<String, Object> param);

	String selectChatroom(HashMap<String, Object> param);

	String insertChatroom(HashMap<String, Object> param);

	String selectFriendName(HashMap<String, Object> param);

	Map selectFriendNameByRoomCode(Map param);

	List selectMyChatroomList(UserVO user);

}
