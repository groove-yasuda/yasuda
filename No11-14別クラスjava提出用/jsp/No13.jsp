<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>No13</title>
</head>

<%
	String ID = "syainID";
	String NAME = "syainNAME";
%>
<body>
	<form action="DELETE" method="post">
		&emsp;&emsp;&emsp;&emsp;社員情報削除画面<br> 削除したい社員情報を入力して下さい<br>
		<br>
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

		社員ID:<input type="text" name="<%=ID%>"><br>
		社員IDをA001～Z999の間の半角英数字で入力して下さい<br>
		＊社員IDは大文字のアルファベット+3桁の数字の組み合わせです<br>(000の並びは使用できません)<br> <br>
		社員名:<input type="text" name="<%=NAME%>"><br>
		社員名を255文字以内で入力して下さい<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<input
			type="submit" value="削除">
	</form>
	<form action="IDOU" method="post">
		<br> <br>&emsp;&emsp;&emsp;&emsp;<input type="submit"
			value="メニュー画面へ戻る">
	</form>



</body>
</html>