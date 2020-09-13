<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/chat/chatroom.css" />
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
// document.onkeydown = doNotReload;
</script>
</head>
<body>
<c:set value="${not empty sessionScope.loginVO.nickName?sessionScope.loginVO.nickName:sessionScope.loginVO.userName}" var="nickName"></c:set>
<div id="section">
	<table class="mainField">
		<thead>
			<colgroup>
				<col width="20%" />
				<col width="80%" />
			</colgroup>
			<tr class="lineBtm">
<!-- 					profileImage -->
					<td colspan="2">
						<span class="userName">	
							${fr.userName}
						</span>
					</td>
				</th>
			</tr>
		</thead>
	</table>
	<div class="chatMain">
	<ul >
	<c:forEach items="${messageList }" var="msg" >
		<c:choose>
			<c:when test="${(msg.nickName eq fr.nickName)  }">
				<li class="other">
				<img class="birthImg" src="${not empty fr.imageUrl?fr.imageUrl:'/images/chat/defaultUserImg.png' }" alt="" />
					<span class="msg">${msg.message }</span>
				</li>
			</c:when>
			<c:when test="${msg.nickName ne fr.nickName }">
				<li class="me">
					<ol class="chatline">
						<li>
							<img class="birthImg" src="${not empty sessionScope.loginVO.imageUrl?sessionScope.loginVO.imageUrl:'/images/chat/defaultUserImg.png' }" alt="" />
							<span class="nameSpace">${nickName }</span>
						</li>
						<li class="msgBack">
							<span class="msg">${msg.message }</span>
						</li>
					</ol>
				</li>
			</c:when>
		</c:choose>
	</c:forEach>
		
	</ul>
	</div>
	
				
<%-- 			<c:forEach var="msg" items="${messageList }"> --%>
				
<%-- 			</c:forEach> --%>
</div>
<div id="bottomBar">
	<textarea style="resize:none;" rows="10" onkeydown="check(this);" id="messageBox" ></textarea>
	<button type="button" onclick="sendMessage(this);"  >전송</button>
</div>

</body>


<script>
// sockJS
var stompClient=opener.stompClient;
stompClient.subscribe('/personal/room/${chatroomNo}' , function (chat) {
	var test=JSON.parse(chat.body);
	console.log(test);
	//본인인경우
	if('${nickName}'===test.nickName){
		var html="<li>";
		html+='<img class="birthImg" src="'+test.imageUrl+'" alt="" />';
		html+='<span class="nameSpace">'+test.nickName+'</span>';
		html+='</li>';
		html+='<li class="msgBack">';
		html+='<span class="msg">'+test.message+'</span>';
		html+='</li>';
		$('.chatline').append(html);
		}else{
			
			}
	 });
window.oncontextmenu = function () {
	  return false;
	};
function check(obj){
	 if( event.keyCode==13) {
		 sendMsg(obj);
	    } 
}
function sendMessage(obj){
	sendMsg(obj.previousSibling.previousSibling);
}
function sendMsg(obj){
	var text=obj.value;
	var src="${not empty sessionScope.loginVO.imageUrl?sessionScope.loginVO.imageUrl:'/images/chat/defaultUserImg.png' }";
	var dt={
			'nickName':'${not empty sessionScope.loginVO.nickName?sessionScope.loginVO.nickName:sessionScope.loginVO.userName}',
			'message':text,
			'chatroomNo':'${chatroomNo}',
			'imageUrl':src
			}
	stompClient.send('/send/personal',{},JSON.stringify(dt));
	document.getElementById('messageBox').value='';
}

</script>
</html>