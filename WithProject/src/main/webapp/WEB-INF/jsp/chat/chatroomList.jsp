<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Title</title>
<script src="/js/jquery-3.5.1.js"></script>
<link rel="stylesheet" href="/css/chat/friendList.css" />
<script>

</script>
<style>
.line{border-bottom:1px solid #eee;position: relative;padding:15px 2px 15px 1px;}
.chatroomList{list-style:none;width:100%;padding-inline-start:0px;}
.chatroomList li{list-style:none;width:100%;height: 3%;}
#searchBox{width:70%;border-radius:10px;height: 30px;border:1px solid #a59e9e;}
.searchBar{margin-left: 5%;}
.chatroomImg{width:50px;height: 50px;border-radius: 50%;margin:2px 3px 2px 3px;}
.last-msg{max-width: 360px;padding-left: 27px;}
.room{position: absolute;max-width: 360px;overflow: hidden;text-overflow: ellipsis;left: 15%;font-size: 16px;font-weight: 550;}
</style>
</head> 
<body>
	<h2>채팅 목록</h2> 
	<div class="searchBar">
		<input type="text" name="searchKeyword" id="searchBox" placeholder="채팅방 이름, 참여자 검색" />
	</div>
	<div id="section">
			<ul class="chatroomList">
				<c:forEach items="${chatroomList }" var="list">
					<li class="chat" onclick="viewChatroom('${list.chatroomNo}');">
						<div class="line">
							<img src="${list.chatroomImg }" alt="" class="chatroomImg" />
							<span class="time">${list.lastMsgTime }</span>
							<span class="room"><nobr>${list.userlist }</nobr></span>
							<span class="last-msg">${list.lastmessage }</span>
						</div>
						
					</li>
				</c:forEach>
			</ul>
	</div>
	<jsp:include page="/WEB-INF/jsp/common/footer.jsp"></jsp:include>
	<form action="/user/myInfoView" method="post" id="userForm">
		<input type="hidden" name="userSeq" id="userSeq"/>
	</form>
	
</body>
</html>