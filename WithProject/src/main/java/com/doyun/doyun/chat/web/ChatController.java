
package com.doyun.doyun.chat.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import com.doyun.doyun.chat.dto.MessageVO;
import com.doyun.doyun.chat.service.ChatContentService;
import com.doyun.doyun.chat.service.ChatService;
import com.doyun.doyun.log.CustomLogger;
import com.doyun.doyun.user.dto.FriendVO;
import com.doyun.doyun.user.dto.UserKey;
import com.doyun.doyun.user.dto.UserVO;

@Controller
public class ChatController {
	@Inject
	BCryptPasswordEncoder pwd;
	Logger log=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ChatContentService chatContentService;
	
	
	
	@Autowired
	ChatService chatService;
	
	@RequestMapping(value="/user/friendList/{userKey:.+}",method = RequestMethod.GET)
	public String getFriendList(@PathVariable String userKey,HttpSession session,Model model) {
		//userSeq로 친구 목록 탐색
		UserVO user=(UserVO) session.getAttribute("loginVO");
		UserKey key=(UserKey)session.getAttribute("userKey");
		String userKeys=userKey.replace("$$$", "/");
		if(user==null) {
			model.addAttribute("url","/prepared");
			model.addAttribute("message","회원가입 완료.");
		}
		
		boolean bool=pwd.matches("user_key"+user.getUserSeq(), userKeys);
		log.info("result--"+bool);
		if(!bool) {
			model.addAttribute("message","잘못된 접근입니다.");
			model.addAttribute("url","/prepared");
			return "common/message";
		}else {
			//친구 목록을 가져온다.
			List<FriendVO> friendList=chatService.selectUserFriendList(user);
			int friendCnt=chatService.selectFriendCnt(user);
			model.addAttribute("friendCnt", friendCnt);
			model.addAttribute("friendList",friendList);
			model.addAttribute("userKey",userKey);
		}
		
		//sockJS 최초 연결을 위한 처리
		model.addAttribute("flag",true);
		
		return "chat/main";
	}
	
	@ResponseBody
	@RequestMapping(value="/chat/findChatroom",method=RequestMethod.POST)
	public HashMap<String,Object> findChatroom(@RequestParam HashMap<String,Object> param,HttpServletRequest request) {
//		friendNo와 userSeq로 친구의 seq를 가져온다
		int friendSeq=chatService.selectFriendSeq(param);
		param.put("friendSeq",friendSeq);
		String chatroomNo=chatService.selectChatroom(param);
		String friendName=chatService.selectFriendName(param);
		List userList=new ArrayList();
		if(chatroomNo==null) {
//			채팅방 생성 및 chatroom_join테이블에 데이터 insert
//			채팅룸을 생성하기 전, 채팅 방 코드 지정. 1:1 채팅의 경우 맨 앞에 P, 단체 채팅의 경우 M을 붙인다.
			//30자까지 하게 했으므로 그 이후 랜덤 문자열을 11자까지 지정 후 그 뒤에 숫자를 붙인다.
			String code="P";
			StringBuffer buf=new StringBuffer();
			Random rdm=new Random();
			for(int i=0;i<11;i++) {
				buf.append((char)((int)(rdm.nextInt(26))+65));
			}
			code+=buf;
			param.put("code", code);
			userList.add(param.get("userSeq"));
			userList.add(param.get("friendSeq"));
			param.put("list",userList);
			String roomCode=chatService.insertChatroom(param);
			param.put("chatroomNo", roomCode);
			param.put("friendName",friendName);
		}else {
			param.put("friendName",friendName);
			param.put("chatroomNo", chatroomNo);
		}
		
		return param;
	}
	
	@RequestMapping("/chatroom/{roomCode}")
	public ModelAndView chatroom(@RequestParam Map param,ModelAndView mav,@PathVariable String roomCode,HttpSession session) {
		//pathValue가 채팅방 이름
		//mongoDB에서 json형태로 된 데이터를 가져오게 한다.
		UserVO user=(UserVO) session.getAttribute("loginVO");
		param.put("roomCode",roomCode);
		param.put("userSeq",user.getUserSeq());
		List<MessageVO> chatList=chatContentService.selectChatList(roomCode);
		Map friend=chatService.selectFriendNameByRoomCode(param);
		mav.addObject("messageList",chatList);
		mav.addObject("chatroomNo",roomCode);
		mav.addObject("fr",friend);
		mav.setViewName("chat/chatroom");
		return mav;
	}
	
	@RequestMapping("/chat/chatroomList/{userKey:.+}")
	public ModelAndView chatroomList(@RequestParam Map paramMap,ModelAndView mav,@PathVariable String userKey,HttpSession session) {
		//userKey와 loginSession을 비교한다.
		//혹시 모르는 해킹을 대비한다.
		UserVO user=(UserVO) session.getAttribute("loginVO");
		//에러 처리를 위해 $$$로 replace처리를 한 userkey를 다시 원래대로 변환
		String userKeys=userKey.replace("$$$", "/");
		boolean bool=pwd.matches("user_key"+user.getUserSeq(), userKeys);
		if(bool) {
			List chatroomList=chatService.selectMyChatroomList(user);
			mav.addObject("chatroomList",chatroomList);
			mav.setViewName("chat/chatroomList");
		}else {
			mav.addObject("message","잘못된 접근입니다.");
			mav.addObject("url","/prepared");
			mav.setViewName("common/message");
		}
		
		return mav;
	}



}
