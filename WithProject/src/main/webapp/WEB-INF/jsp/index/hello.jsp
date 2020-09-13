<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello!</title>
<script src="/js/jquery-3.5.1.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
$(function(){
	//날씨 api 연동 테스트
	$.getJSON("http://ipinfo.io",function(data){
		var str=data.loc;
		var arr=str.split(',');
		var x;
		var y;
		for(var i=0;i<arr.length;i++){
			if(i==0){
				x=Math.floor(arr[i]);
				}else{
					y=Math.ceil(arr[i]);
					}
			}
		let datas={
				'lat':x,
				'lon':y,
				'part':'current'
				};
		$.getJSON("http://doyuns.com/api/regionWeather",datas,function(data){
				console.log(data);
				var idx=data.daily.length;
				console.log(data.daily[idx-1].weather[0].icon);
				$('.weather').append('<img src="http://openweathermap.org/img/wn/'+data.daily[idx-1].weather[0].icon+'.png" alt="" />')

				});

		});
	});
</script> 

</head>
<body>

	<h2>Hello!!!
		
	</h2>
	오늘의 날씨 : 
		<div class="weather"></div>
</body>
</html>