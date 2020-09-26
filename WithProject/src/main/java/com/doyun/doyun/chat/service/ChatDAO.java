package com.doyun.doyun.chat.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.doyun.doyun.user.dto.FriendVO;
import com.doyun.doyun.user.dto.UserVO;

@Repository
public class ChatDAO {
	Logger log=LoggerFactory.getLogger(this.getClass());
	
	@Inject
	SqlSessionTemplate sqlSession;

	public List<FriendVO> selectUserFriendList(UserVO user) {
		return sqlSession.selectList("chatDAO.selectUserFriendList",user);
	}

	public int selectFriendCnt(UserVO user) {
		return sqlSession.selectOne("chatDAO.selectFriendCnt",user);
	}

	public int selectFriendSeq(HashMap<String, Object> param) {
		return sqlSession.selectOne("chatDAO.selectFriendSeq",param);
	}

	public String selectChatroom(HashMap<String, Object> param) {
		return sqlSession.selectOne("chatDAO.selectChatroom",param);
	}

	public String insertChatroom(HashMap<String, Object> param) {
		int result=sqlSession.insert("chatDAO.insertChatroom",param);
		String str=(String) param.get("code")+result;
		//chatroom_join테이블에도 insert
		param.put("code", str);
		List list=(List) param.get("list");
		for(int i=0;i<list.size();i++) {
			param.put("friendSeq", list.get(i));
			sqlSession.insert("chatDAO.insertChatroomJoin",param);			
		}
		return  str;
	}

	public String selectFriendName(HashMap<String, Object> param) {
		return (String) sqlSession.selectOne("chatDAO.selectFriendName",param);
	}

	public Map selectFriendNameByRoomCode(Map roomCode) {
		return (Map) sqlSession.selectOne("chatDAO.selectFriendNameByChatroomNo",roomCode);
	}

	public List selectMyChatroomList(UserVO user) {
		return sqlSession.selectList("chatDAO.selectMyChatroomList", user);
	}

	public String selectChatroomUserList(String chatroomNo) {
		return sqlSession.selectOne("chatDAO.selectChatroomUserList",chatroomNo);
	}
}
