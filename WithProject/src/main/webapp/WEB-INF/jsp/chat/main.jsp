<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/chat/friendList.css" />
<title>친구 목록</title>
<script src="/js/jquery-3.5.1.js"></script>
<script src="/js/stomp.js"></script>
<script src="/js/sockjs.js"></script>
<script>
function doNotReload(){
    if( (event.ctrlKey == true && (event.keyCode == 78 || event.keyCode == 82)) || (event.keyCode == 116) ) {
        event.keyCode = 0;
        event.cancelBubble = true;
        event.returnValue = false;
    } 
}
document.onkeydown = doNotReload;
$(function(){
	window.resize(500,700)
})
</script>
</head>
<body>
<h2>친구</h2>


<div id="section">
	<table class="mainField">
		<thead>
			<colgroup>
				<col width="20%" />
				<col width="80%" />
			</colgroup>
			<tr class="lineBtm">
				<th class="touch" onclick="updateMyInfo();">
<!-- 					profileImage -->
					<img src="${empty sessionScope.loginVO.imageUrl?'/images/chat/defaultUserImg.png':sessionScope.loginVO.imageUrl }" class="profileImg" alt="프로필"  />
				</th>
					<td>
						<span class="userName">
							${not empty sessionScope.loginVO.nickName?sessionScope.loginVO.nickName:sessionScope.loginVO.userName  }
						</span>
						<p class="statusMessage">${sessionScope.loginVO.statusMessage }피곤하다....</p>
					</td>
			</tr>
			<tr class="lineBtm" >
				<th><img src="/images/chat/birth.png" alt="생일인 친구" class="birthImg" /></th>
				<td>
					<span class="birthText">
							생일인 친구 보기
					</span>
				</td>
			</tr>
			<tr >
				<th class="grayText">친구 ${friendCnt }</th>
			</tr>

			<c:forEach var="fr" items="${friendList }">
<!-- 			<tr> -->
				<tr onmousedown="menu(this);" class="friendList" >
				<input type="hidden" name="friendNo" value="${fr.friendNo }" />
				<th >
<!-- 					profileImage -->
					<img src="${empty fr.imageUrl?'/images/chat/defaultUserImg.png':fr.imageUrl }" class="profileImg" alt="프로필"  />
					<td>
						<span class="userName">
							${not empty fr.nickName?fr.nickName:fr.userName  }
						</span>
						<p class="statusMessage"><nobr>${fr.statusMessage }피곤하다....dddddddddddddddddddddddddddddddddddddddddddddddd</nobr></p>
						<div class="hiddenBox" >
							<ul class="noneList">
								<li onclick="talkWithFriend('${fr.friendNo}')">대화하기</li>
								<li onclick="blackFriend('${fr.friendNo}')" >차단하기</li>
								<li onclick="changeFriendName('${fr.friendNo}')">이름 변경</li>
							</ul>
						</div>
					</td>
				</th>
<!-- 			</tr> -->
			</c:forEach>
			<tr>
				<th style="padding:35px;"><img src="" alt="" /></th>
				<td><p></p></td>
			</tr>
		</thead>
	</table>
	
</div>
<div id="bottomBar">
	<ul>
		<li><img class="birthImg" src="/images/chat/friend.png" alt="" /></li>
		<li><img src="/images/chat/talk.png" alt="" class="birthImg" /></li>
		<li><img src="/images/chat/search.png" alt="" class="birthImg" onclick="findFriend();" /></li>
		<li><img src="/images/chat/setting.png" alt="" class="birthImg" /></li>
	</ul>
</div>
<form action="/user/myInfoView" method="post" id="userForm">
	<input type="hidden" name="userSeq" id="userSeq"/>
</form>
</body>


<script>
function updateMyInfo(){
	document.getElementById('userForm').submit();
}


// sockJS
var socket=null;
var stompClient=null;

window.onload=function(){
	if(${flag}){
			connected();
			setTimeout(function() {
				console.log('메세지 보낸다.');
				sendTest();
				}, 3000);

			setTimeout(function() {
				console.log('두번째메세지 보낸다.');
				sendTest2();
				}, 10000);
			
		}
	
}
function connected(){
	socket=new SockJS("/chat");
	 stompClient=Stomp.over(socket);
	stompClient.connect({},function(frame){
		console.log('연결중...')
	
		});
}

function sendTest(){

	var dt={
			'name':'test',
			'message':'테스트입니다.',
			'roomId':'test'
			}
	stompClient.send('/send/chatting',{},JSON.stringify(dt));
}
function sendTest2(){

	var dt={
			'name':'test222',
			'message':'2222테스트입니다.',
			'roomId':'test'
			}
	//send 메소드의 첫번째는 매핑 주소, 두번째는 header, 세번째는 body(파라미터 값)
	stompClient.send('/send/chatting',{},JSON.stringify(dt));
}
function disCon(){
	 if (stompClient !== null) {
		    stompClient.disconnect();
		  }
		  console.log("Disconnected");
}

function menu(obj){
// 	$('.hiddenBox').css('display','none');
	 if ((event.button == 2) || (event.which == 3)) {
		 $(obj).children().eq(2).children().eq(2).css('display','block')
	  }
}

function talkWithFriend(friendNo){
	$('.hiddenBox').css('display','none');
	//친구와의 채팅방이 있는지 확인 후에 팝업창 띄운다.
	var data={
				'userSeq':'${sessionScope.loginVO.userSeq}',
				'friendNo':friendNo
			}
	$.ajax({
		url:'/chat/findChatroom',
		data:data,
		async:false,
		type:'post',
// 		contentType:'application/json',
// 		dataType:'json',
		success:function(dt){
			//채팅방 번호 리턴받는다.
			//친구의 이름도 리턴받는다(닉네임이 있다면 닉네임, 없다면 친구 닉네임, 없다면 친구 이름)
			if(dt){
					window.open('/chatroom/'+dt.chatroomNo,dt.friendName,'width=500, height=700, left=600, top=300,location=no,toolbar=no,menubar=no,resizable=no,directories=no,status=no')
				}
			}
		});
	
}

window.oncontextmenu = function () {
	  return false;
	};

function findFriend(){
	var userKey='${userKey}';
	document.getElementById('userSeq').value=userKey;
	document.getElementById('userForm').action='/user/findFriend';
	document.getElementById('userForm').submit();
	
	}
	
</script>
</html>