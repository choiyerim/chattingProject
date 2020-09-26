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
</script>
<style>
	td{display: table-caption;}
	
</style>
</head>
<body>
<h2>친구 찾기</h2>


<div id="section">
	<table class="mainFieldFriend">
		<thead>
			<colgroup>
				<col width="20%" />
				<col width="80%" />
			</colgroup>
			<tr class="lineBtm">
				<p>아이디 검색</p>
				
				<input type="text" name="friendId" class="findFriend" id="friendId"  />
				<img src="/images/chat/search.png" alt="" class="findBtn" onclick="findFriends('#friendId');" />
			</tr>
<!-- 			<hr  class="nextLine" /> -->
			<tr class="nextLine">
				<th ></th><td></td>
			</tr>
			
			<tr>
				<th style="padding:35px;"><img src="" alt="" /></th>
				<td><p></p></td>
			</tr>
		</thead>
	</table>
	
</div>
<!-- <div id="bottomBar"> -->
<!-- 	<ul> -->
<!-- 		<li><img class="birthImg" src="/images/chat/friend.png" alt="" /></li> -->
<!-- 		<li><img src="/images/chat/talk.png" alt="" class="birthImg" /></li> -->
<!-- 		<li><img src="/images/chat/search.png" alt="" class="birthImg" onclick="findFriend();" /></li> -->
<!-- 		<li><img src="/images/chat/setting.png" alt="" class="birthImg" /></li> -->
<!-- 	</ul> -->
<!-- </div> -->
<jsp:include page="/WEB-INF/jsp/common/footer.jsp"></jsp:include>

<form action="/user/addFriend" method="post" id="userForm">
	<input type="hidden" name="frId" id="frId"/>
</form>
</body>


<script>
function updateMyInfo(){
	document.getElementById('userForm').submit();
}

function menu(obj){
// 	$('.hiddenBox').css('display','none');
	 if ((event.button == 2) || (event.which == 3)) {
		 $(obj).children().eq(2).children().eq(2).css('display','block')
	  }

}

window.oncontextmenu = function () {
	  return false;
	};

function findFriends(obj){
	var userId=$(obj).val();
	$('.nextLine').after('');
	$('.viewFriend').remove();
	$.ajax({
		url:'/user/findFriendById',
		data:{'userId':userId},
		type:'post',
		success:function(data){
				var src='';
				if(data.imageUrl==null || data.imageUrl=='' || data.imageUrl===false){
					src='/images/chat/defaultUserImg.png';
					}else{
						src=data.imageUrl;
						}
				var name;
				if(data.nickName =='' || data.nickName ==null ){
						name=data.userName;
					}else{
						name=data.nickName;
						}
				
				var html='';
				 html+='<tr onmousedown="menu(this);" class="viewFriend">';
				 html+='<th><img src="'+src+'" class="profileImg" alt="" /></th>';
				 html+='<td>';
				html+='<span class="userName">';
				html+=name+'</span>';
				html+='<p class="statusMessage"><nobr>'+(data.statusMessage==null?"            sdfsf":data.statusMessage)+'<img src="/images/user/plusImage.png" class="plusImg" onclick="addFriendBtn(\''+data.userId+'\');" alt="" /></nobr></p>';
				html+='</td></tr>';
			$('.nextLine').after(html);
				
			}
		
		});
	
	
	}
function menu(obj){
// 	$('.hiddenBox').css('display','none');
	 if ((event.button == 2) || (event.which == 3)) {
		 $(obj).children().eq(2).children().eq(2).css('display','block')
	  }
}
function addFriendBtn(friendId){
	var frId=friendId;
	document.getElementById('frId').value=friendId;
	document.getElementById('userForm').submit();
	
}
</script>
</html>