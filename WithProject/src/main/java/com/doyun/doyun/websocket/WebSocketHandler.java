package com.doyun.doyun.websocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.doyun.doyun.user.dto.UserVO;

import lombok.Data;



@Data
@Component
public class WebSocketHandler extends TextWebSocketHandler {

	//접속자 전체
	private List<WebSocketSession> sessionList=new ArrayList();
	//1:1 의 경우
	private Map<String,WebSocketSession> userSession=new HashMap<String,WebSocketSession>();
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	//서버 접속 했을 때
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessionList.add(session);
		UserVO user=userInfo(session);
		userSession.put("user", session);
		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22");
		logger.info("웹소켓 연결됨@@@@@"+session.getId()+" : "+user.toString());
		System.out.println(userSession);
		
	}
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// TODO Auto-generated method stub
		super.handleTextMessage(session, message);
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionClosed(session, status);
	}
	
	public UserVO userInfo(WebSocketSession session) {
		Map<String, Object> httpSession=session.getAttributes();
		UserVO user=(UserVO) httpSession.get("UserVO");
		if(user==null) {
			return null;
		}else {
			return user;
		}
	}

	
}
