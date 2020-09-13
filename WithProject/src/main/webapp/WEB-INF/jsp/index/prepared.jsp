<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page session="true" %>
<%

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:if test="${empty sessionScope.userKey}">

<html>
<head>
<script src="/js/jquery-3.5.1.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>냥챗</title>
<link rel="stylesheet" href="/css/index.css" />
</head>
<body >
	<div id="login-container">
		<div id="logo">
			<img src="/images/chat/chat3.jpg" alt="" />
		</div>
		<div id="input">
		<form action="/user/login" method="post" id="loginFrm" onsubmit="return check();">
		<table>
		<colgroup>
		<col width="150px"/>
		<col width="300px"/>		
		</colgroup>
		<tr>
			<th>아이디</th>
			<td><input type="email" name="userId" id="id" placeholder="아이디를 입력해주세요" /></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type="password" name="password" id="password" /></td>

		</tr>
		<tr>
			<td colspan="2"><input type="checkbox" name="saveId" id="saveId" /><label for="saveId">아이디 저장</label></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="로그인"  /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="button" value="회원가입" onclick="userEnroll();" /></td>
		</tr>
		
		</table>
		</form>
		</div>
	
	
	</div>

</c:if>
<c:if test="${not empty sessionScope.userKey }">

	<script>
			var url="/user/friendList/${sessionScope.userKey.userKey}";
			var title="채팅목록";
			var specs="width=500, height=700, left=600, top=200,location=no,toolbar=no,menubar=no,resizable=no,directories=no,status=no";
			
			window.open(url, title,specs);	//팝업의 최상위 윈도우 객체를 리턴함


			
				
			
// 			self.close();
	
	</script>
</c:if>



<script>

function userEnroll(){
	var url="/user/signIn";
	location.href=url;
	self.resizeTo(700,700);
}

function check(){
	if($('input[name=userId]').val().length==0){
		alert('아이디를 입력해주세요');
		return false;
	}else if($('input[name=password]').val().length==0){
		alert('비밀번호를 입력해주세요');
		return false;
	}else{
	
	
// 	var url="${pageContext.request.contextPath}/chat/chatList";
// 	var title="NyangTalk";
// 	var specs="width=500px, height=700px, left=600px, top=200px";
	
// 	window.open(url, title,specs);	//팝업의 최상위 윈도우 객체를 리턴함
	}
	return true;
}


</script>
</body>
</html>