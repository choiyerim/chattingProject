package com.doyun.doyun.chat.service;

import java.util.List;
import java.util.Map;

import com.doyun.doyun.chat.dto.MessageVO;

public interface ChatContentService {

	List<MessageVO> selectChatList(String roomCode);

	void insertMessage(MessageVO message);

}
