<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="/js/jquery-3.5.1.js"></script>
<!-- // jQuery UI 라이브러리 js파일 -->
<link rel="stylesheet" href="/css/user/userInfo.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>내 정보</title>
<script>
$(document).keypress(function(e) { if (e.keyCode == 13) e.preventDefault(); });
window.load =function(){
	window.resize(500,700)
}
$(function(){
	$('.inputText').focus(function(){
		$(this).text('');

		});
})
</script>
</head>
<body>
	<div class="top">
	<a href="${backUrl }"><img src="/images/chat/arrow.png" alt="" class="arrow"/></a>
		
	</div>
	<div id="joinMain">
	
	<form action="/user/userInfoUpdate" method="post" id="userFrm" name="userFrm" enctype="multipart/form-data">
	<input type="hidden" name="userSeq" value="${sessionScope.loginVO.userSeq }" />
	<input type="hidden" name="nickName" id="" />
	<input type="hidden" name="statusMsg" id="" />
		<div class="background-img" style="background-image: url('/images/test.jpg')" >
			
			<div class="myImgZone" >
<%-- 			${sessionScope.loginVO } --%>
			<div class="imgWrap">
				<img class="profileImg" id="profileImg" onclick="openProfileFile();" src="${not empty sessionScope.loginVO.imageUrl?sessionScope.loginVO.imageUrl:'/images/chat/defaultUserImg.png' }" alt="" />
				<img src="/images/user/photo.png" class="profileFile-edit" alt=""  onclick="openProfileFile();"  />
				<input type="file" name="profileImg" id="profileImgIntput" onchange="editMyProfilePhoto();" accept="image/*" style="display: none;"  />
			</div>
				<p class="userName inputText" contenteditable="true" >${not empty sessionScope.loginVO.nickName?sessionScope.loginVO.nickName:sessionScope.loginVO.userName }
					<img src="/images/user/edit2.png"  alt="" class="miniIcon" onclick="editUserNickName();" />
				</p>
				<p class="status-msg inputText" contenteditable="true" ><nobr>${not empty sessionScope.loginVO.statusMessage? sessionScope.loginVO.statusMessage:'상태 메세지를 입력해주세요.' }</nobr>
				<img src="/images/user/edit2.png" alt=""  class="miniIcon2"/>
				</p>
				
			</div>
			<div id="bottomBar2">
				<table>
				<colgroup>
					<col width="50%">
					<col width="50%">
				</colgroup>
					<td><img src="/images/user/photo.png" alt="" class="btmImg" title="배경 이미지 등록" /></td>
					<td><img src="/images/user/save.png" alt="" class="btmImg" id="save" title="저장" /></td>
				</table>
			</div>
		</div>			
	</form>

	</div>
	
<script>
$(function(){
	$('#save').on('click',function(){
		var nickName=$('.userName').text();
		var status=$('.status-msg').text();
		$('input[name=statusMsg]').val(status);
		$('input[name=nickName]').val(nickName);
		document.getElementById('userFrm').submit();

		});
});
function openProfileFile(){
	$('#profileImgIntput').click();
}
function editMyProfilePhoto(){
	var data = new FormData(document.getElementById('userFrm'));
	$.ajax({
		enctype:'multipart/form-data',
		processData: false,
        contentType: false,
		url:'/user/userPhotoEdit',
		data:data,
		type:'post',
		success:function(data){
			var dt=data.saveFilePath+data.saveFileName;
			$('profileImg').attr('src',dt);
		}

		});
}
function editMyInfo(){
	document.getElementById('userFrm').submit();
}
// function hide(){
// 	$('.miniIcon').css('display','none');
// }
// function hide2(obj){
// 	$(obj).children().eq(0).html('');
// 	$('.miniIcon2').css('display','none');
// }
</script>
</body>
</html>