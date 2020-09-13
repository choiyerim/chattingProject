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
<link rel="stylesheet" href="/css/userJoin.css" />
<title>회원가입</title>
<script>
function checkId(){

	  var str=$('#userId').val();

	  var reg=/^[a-zA-Z0-9\S]{4,80}$/g;
	  //c=> 화살표함수, finc(function dssds)==> each문을 돌리는것과 동일하다. c라는 것은 파라미터(function(c))와 같고 인자 하나를 꺼내서 그의 id값을 검사 후 true이면 
	  //영어와 숫자로 이루어진 4~15자리 
	  var bool=regTest(reg,'userId','아이디가 부적절합니다.4자 이상 입력해주세요');
	  if(bool==true){
			var url="/user/checkId";
			$.ajax({
				url:url,
				data:{
					userId:str
					},
				type:'post',
				success:function(data){
						if(data.result==true){
								alert('존재하지 않는 아이디입니다.\n 회원가입 절차를 진행해주세요.');
								$('#check').prop('disabled',true);	
							}else{
								alert('존재하는 아이디입니다. 다시 입력해주세요.');
								$('#userId').val('');
								return false;
								}
					}	
				
				});	
			}
}
//validate 에서 호출할 함수(리턴값 확인용)
function regTest(reg,info,msg){
if(reg.test($('#'+info).val())){
  return true;
}else{
  alert(msg);
  return false;
}
}
</script>
</head>
<body>
		<div id="joinMain">
	<span id="title">회원 가입</span>

		<form action="/user/signInUser" method="post" id="enrollFrm" name="enrollFrm">
			<table id='join'>
				<colgroup>
					<col style="width: 30%" />
					<col style="width: 50%" />
					<col />
				</colgroup>
				<tbody>
					<tr>
						<th style="width:15px;">아이디</th>
						<td><input minlength="4" type="email" name="userId" id="userId"  placeholder="이메일 주소 ex)abcde@naver.com " required="required">
						<button type='button' onclick="checkId()" >ID중복검사</button></td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td><input maxlength="15" minlength="4" type="password"
							name="userPwd" id="userPwd" placeholder="특수,영문,숫자포함 4~15자리" style="font-size: 13px;" required="required" ></td>
					</tr>
					<tr>
						<th>비밀번호 확인</th>
						<td><input maxlength="15" minlength="4" type="password"
							name="re_userPwd" id="re_userPwd"  placeholder="비밀번호 재입력" required="required" ></td>
					</tr>
					<tr>
						<th>이름</th>
						<td><input minlength='2' type="text" name="userName" id="userName" ></td>
					</tr>
					<tr>
						<th>생년월일</th>
						<td>
						<input type="text" readonly name="birth" id="birth" style="width:60%;" required/>
					<!-- 	<input type="date" name="birth" id="birth" style="width:60%;" required /> -->
						</td>
					</tr>
					<tr>
						<th>연락처</th>
						<td><select  id="tel1" class='userInfo' name='phone_1' required >
								<option value="010" selected>010</option>
								<option value="011">011</option>
								<option value="070">070</option>
						</select> - 
						<input type="text" id="tel2" name='phone_2' maxlength="8" class="tel" required  > 
					</tr>
					<tr>
					<th></th>
					<td colspan="2">
					<input type="submit" id='submitBtn' class="btn btn-outline-secondary" value="회원가입"/>
					<!-- <button type="submit" id='submitBtn' onclick="return validate();">회원가입</button> --></td>
					</tr>

				</tbody>
			</table>

		</form>



	</div>
	
<script>


$(function(){
	
	
	$('#birth').datepicker({
		changeMonth: true, 
      changeYear: true,
      nextText: '다음 달',
      prevText: '이전 달' ,
     	closeText: '닫기', 
     	minDate: '-100y',
      yearRange: 'c-100:c+20',
      dateFormat: "yy-mm-dd",
      monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
      monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
	});
	
});

//유효성 검사





//form 확인 및 submit버튼 활성화
function validate(){
var result=true;
if($('#check').prop('disabled')==false){
	result=false;
}
if(result==false){
	alert('아이디를 입력해주세요.');
	return false;
}
var reg=/^.*(?=^.*\S{4,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g;
bool=regTest(reg,'userPwd','비밀번호를 다시 입력해주세요.');
if(!bool){
  result=false;
}else{
	      reg=/^[\d]{8}$/;
	      bool=regTest(reg,'tel2','전화번호는 숫자만 입력가능합니다. 다시 입력해주세요.');
	      if(!bool){
	      result=false;
	      }
	}

var pwdIn=$('#userPwd').val();
var pwdCheck=$('#re_userPwd').val();
if(pwdIn!==pwdCheck){
	  alert('입력하신 비밀번호와 비밀번호 확인 부분이 동일하지 않습니다. 다시 입력해주세요');
	  result=false;
}
return result;
}

</script>
</body>
</html>