package com.doyun.doyun.chat.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doyun.doyun.chat.dto.MessageVO;

@Service
public class ChatContentServiceImpl implements ChatContentService{
	
	@Autowired
	ChatContentDAO chatContentDAO;

	@Override
	public List<MessageVO> selectChatList(String roomCode) {
		return chatContentDAO.selectChatList(roomCode);
	}

	@Override
	public void insertMessage(MessageVO message) {
		chatContentDAO.insertMessage(message);
	}

}
