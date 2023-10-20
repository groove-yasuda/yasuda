<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>menu</title>
</head>




<body>

	<form action="MENU" method="post">
		社員情報登録画面は登録ボタンを<br> <input type="submit" value="登録"><br>
		<input type="hidden" name="hantei" value="0">
		<%
			//menu判定に進む(登録)
		%>
	</form>
	<form action="MENU" method="post">
		社員情報更新画面は更新ボタンを<br> <input type="submit" value="更新"><br>
		<input type="hidden" name="hantei" value="1">
		<%
			//menu判定に進む(更新)
		%>
	</form>
	<form action="MENU" method="post">
		社員情報削除画面は削除ボタンを<br> <input type="submit" value="削除"><br>
		<input type="hidden" name="hantei" value="2">
		<%
			//menu判定に進む (削除)
		%>
	</form>
	<form action="MENU" method="post">
		社員情報検索画面は検索ボタンを<br> <input type="submit" value="検索"><br>
		<input type="hidden" name="hantei" value="3">
		<%
			//menu判定に進む (検索)
		%>
	</form>

</body>
</html>