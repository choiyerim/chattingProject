<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script>
	

</script>
</head>
<body>
<form action="" method="post" id="frm">
	<c:forEach items="${pNames }" var="pr" varStatus="st" >
		<input type="hidden" name="${pr }"  value="${pv[st.index] }" />
	</c:forEach>
</form>
<script>

	var message='${message}';
	alert(message);
	console.log('${url}');
	document.getElementById('frm').action='${url}';
	document.getElementById('frm').submit();
</script>
</body>
</html>