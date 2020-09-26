<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
window.oncontextmenu = function () {
	  return false;
	};

function findFriend(){
	var userKey='${sessionScope.userKey.userKey}';
	console.log(userKey);
	document.getElementById('userSeq').value=userKey;
	document.getElementById('userForm').action='/user/findFriend';
	document.getElementById('userForm').submit();
	
	}
function doNotReload(){
    if( (event.ctrlKey == true && (event.keyCode == 78 || event.keyCode == 82)) || (event.keyCode == 116) ) {
        event.keyCode = 0;
        event.cancelBubble = true;
        event.returnValue = false;
    } 
}
document.onkeydown = doNotReload;
function mainView(){
	location.href='/user/friendList/${sessionScope.userKey.userKey}';
}
function chatroomList(){
	location.href='/chat/chatroomList/${sessionScope.userKey.userKey}';
}

</script>
<footer>
<div id="bottomBar">
	<ul>
		<li><img class="birthImg" src="/images/chat/friend.png" alt="" onclick="mainView();" /></li>
		<li><img src="/images/chat/talk.png" alt="" class="birthImg" onclick="chatroomList();" /></li>
		<li><img src="/images/chat/search.png" alt="" class="birthImg" onclick="findFriend();" /></li>
		<li><img src="/images/chat/setting.png" alt="" class="birthImg" /></li>
	</ul>
</div>
</footer>