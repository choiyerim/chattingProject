package com.doyun.doyun.chat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.doyun.doyun.chat.dto.MessageVO;
import com.doyun.doyun.log.CustomLogger;
import com.doyun.doyun.log.MongoLogger;

@Repository
public class ChatContentDAO {
	
	@Inject
	MongoTemplate mongo;
	private CustomLogger cLogger;
	Logger logger;
	
	public List<MessageVO> selectChatList(String roomCode) {
		String code=roomCode;
		cLogger=new MongoLogger(logger,mongo);
		JSONObject obj=new JSONObject();
		//쿼리 문 작성, where절
		Criteria cri=Criteria.where("chatroomNo").is(code).where("deleteStatus").is("N");
		Query query=new Query(cri);
		
		//mongo.find(쿼리,받아올 resultVO,컬렉션 이름)
		List<MessageVO> list=mongo.find(query,MessageVO.class,"chatting");
		return list;
	}

	public void insertMessage(MessageVO message) {
//		ArrayList<MessageVO> mList=new ArrayList<MessageVO>();
		mongo.insert(message, "chatting");
	}

	public MessageVO selectLastMessage(String chatroomNo) {
		Query query=new Query();
		Criteria cri=Criteria.where("chatroomNo").is(chatroomNo);
		query.with(new Sort(Direction.DESC,"insertDate"));
		query.addCriteria(cri).limit(1);
		
		MessageVO message=mongo.findOne(query, MessageVO.class,"chatting");
		return message;
	}
	
	//update예시
//	Query query = new Query();
    //업데이트 할 항목 정의
//    Update update = new Update();
//    update.set("deleteStatus", "Y");
    
//    mongoTemplate.updateFirst(query, update, "chatting");

}
