package com.doyun.doyun.chat.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doyun.doyun.chat.dto.MessageVO;
import com.doyun.doyun.user.dto.FriendVO;
import com.doyun.doyun.user.dto.UserVO;

@Service
public class ChatServiceImpl implements ChatService{

	@Autowired
	ChatDAO chatDAO;
	
	@Autowired
	ChatContentDAO chatContentDAO;
	
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
	public Map selectFriendNameByRoomCode(Map param) {
		return chatDAO.selectFriendNameByRoomCode(param);
	}

	@Override
	public List selectMyChatroomList(UserVO user) {
		List list=chatDAO.selectMyChatroomList(user);
		List resultList=new ArrayList();
		for(int i=0;i<list.size();i++) {
			Map temp=(Map) list.get(i);
			//마지막 메세지 가져오기
			MessageVO message=chatContentDAO.selectLastMessage(temp.get("chatroomNo").toString());
			temp.put("lastMessage",message);
			SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm");
			temp.put("lastMsgTime", message.getInsertDate());
			//채팅방 참여유저 리스트
			String userList=chatDAO.selectChatroomUserList(temp.get("chatroomNo").toString());
			temp.put("userList", userList);
			resultList.add(temp);
		}
		return resultList;
	}

}
