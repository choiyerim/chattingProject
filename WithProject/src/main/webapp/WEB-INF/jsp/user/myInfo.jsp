<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="/js/jquery-3.5.1.js"></script>

<!-- // jQuery UI 라이브러리 js파일 -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>




<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/user/userInfo.css" />
<title>내 정보</title>
<script>
window.load =function(){
	console.log('sdfs');
	window.resize(500,700)
}

</script>
</head>
<body>
	<div class="top">
	<a href="${backUrl }"><img src="/images/chat/arrow.png" alt="" class="arrow"/></a>
		
	</div>
	<div id="joinMain">

	<form action="/user/userInfo" method="post" id="userFrm" name="userFrm">
		<div class="background-img" style="background-image: url('/images/test.jpg')" >
			
			<div class="myImgZone" >
				<img class="profileImg" src="${not empty sessionScope.loginVO.imageUrl?sessionScope.loginVO.imageUrl:'/images/chat/defaultUserImg.png' }" alt="" />
				<p class="userName">${not empty sessionScope.loginVO.nickName?sessionScope.loginVO.nickName:sessionScope.loginVO.userName }</p>
				<p class="status-msg">${sessionScope.loginVO.statusMessage }ddddddddddd</p>
				
			</div>
			<div id="bottomBar2">
				<table>
				<colgroup>
					<col width="50%">
					<col width="50%">
				</colgroup>
					<td><img src="/images/chat/talk.png" alt="" class="btmImg" /></td>
					<td><img src="/images/user/editImg.png" alt="" class="btmImg" onclick="editMyInfo('${sessionScope.loginVO.userSeq}')" /></td>
				</table>
			</div>
		</div>			
	</form>

	</div>
	
<script>
function editMyInfo(userSeq){
	document.getElementById('userFrm').submit();
}
</script>
</body>
</html>