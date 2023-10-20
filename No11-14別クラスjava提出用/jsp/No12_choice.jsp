<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>No12_choice</title>
</head>

<%
	String res = "";
%>



<body>
	<form action="RETURN12" method="post">
			<br> <input type="hidden" name="hantei" value="0"><%//No12.jspに戻る %>
		<input type="submit" value="戻る">
	</form>

	<form action="RETURN12" method="post">
				<br> <input type="hidden" name="hantei" value="1"><%//menu.jspに戻る %>
		<input type="submit" value="終わる">

	</form>
</body>
</html>