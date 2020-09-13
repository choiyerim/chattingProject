package com.doyun.doyun.chat.web;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.doyun.doyun.chat.dto.MessageVO;
import com.doyun.doyun.chat.service.ChatContentService;

@RestController
public class MessageController {
	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private ChatContentService chatService;
	
	@MessageMapping("/personal")
	public void broadCast(MessageVO message) {
		Date date=new Date();
		message.setInsertDate(date);
		//몽고 db에 저장
		chatService.insertMessage(message);
		 template.convertAndSend("/personal/room/"+message.getChatroomNo(), message);
	}



}
