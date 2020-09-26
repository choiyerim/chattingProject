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
	<span class="userName">	
		${fr.nickName}
	</span>
	<div class="wrap">
		<div class="chatMain">
		<table class="width100">
			<tbody>
				<c:forEach items="${messageList }" var="msg" >
					<c:choose>
						<c:when test="${(msg.nickName eq fr.nickName)  }">
						<tr class="other">
							<th><img class="birthImg" src="${not empty fr.imageUrl?fr.imageUrl:'/images/chat/defaultUserImg.png' }" alt="" />
								<span class="nameSpace">${fr.nickName }</span>
							</th>
						</tr>
						<tr class="other msgBox">
							<td><span class="msg">${msg.message }</span></td>
						</tr >
						</c:when>
						<c:when test="${msg.nickName ne fr.nickName }">
							<tr class="me">
								<th>
									<img class="birthImg" src="${not empty sessionScope.loginVO.imageUrl?sessionScope.loginVO.imageUrl:'/images/chat/defaultUserImg.png' }" alt="" />
									<span class="nameSpace">${nickName }</span>
								</th>
							</tr>
							<tr class="me msgBox">
								<td><span class="msg">${msg.message }</span></td>
							</tr >
						</c:when>
					</c:choose>
				</c:forEach>
				<tr>
					<th></th>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<th></th>
				</tr>
				<tr>
					<td></td>
				</tr>
			</tbody>
		</table>
		
	<%-- 	${msg };; --%>
	<%-- 	${fr } --%>
			
		</div>
	</div>
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
	//본인인경우
	if('${nickName}'===test.nickName){
		var html='<tr class="me">';
		html+='<th>';
		html+='<img class="birthImg" src="'+test.imageUrl+'" alt="" />';
		html+='<span class="nameSpace">'+test.nickName+'</span>';
		html+='</th>';
		html+='</tr>';
		html+='<tr class="me msgBox">';
		html+='<td><span class="msg">'+test.message+'</span></td>';
		html+='</tr >';
		$('.msgBox').last().after(html);
		}else{
			var html='<tr class="other">';
			html+='<th>';
			html+='<img class="birthImg" src="'+test.imageUrl+'" alt="" />';
			html+='<span class="nameSpace">'+test.nickName+'</span>';
			html+='</th>';
			html+='</tr>';
			html+='<tr class="other msgBox">';
			html+='<td><span class="msg">'+test.message+'</span></td>';
			html+='</tr >';
			$('.msgBox').last().after(html);
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