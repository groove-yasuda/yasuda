<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>No11</title>
</head>

<%
%>


<body>
	<form action="INSERT" method="post">
		社員情報登録画面<br>
		<%
			if (request.getAttribute("kaesiP") != null) {
		%><font color="red"> <%
				@SuppressWarnings("unchecked")
 	List<String> list = (List<String>) request.getAttribute("kaesiP[]");
 		for (int i = 0; i < list.size(); i++) {
 			out.println(list.get(i));
 %><br>
			<%
				}
			%></font><br>
		<%
			}
		%>
		社員ID:<input type="text" name="syainID"><br>
		社員IDをA001～Z999の間の半角英数字で入力して下さい<br>
		＊社員IDは大文字のアルファベット+3桁の数字の組み合わせです(000の並びは使用できません)<br>
		<br>
		社員名:<input type="text" name="syainNAME"><br>
		社員名を255文字以内で入力して下さい<br>
		<input type="submit" value="登録">
	</form>
		<form action="IDOU" method="post">
		<input type="submit" value="メニュー画面へ戻る">
	</form>



</body>
</html>