<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>No13_delete</title>
</head>


<body>
	<form action="KAKUNIN13" method="post">
		社員情報削除画面 <br>
		削除確認 <br>
		<%
			if (request.getAttribute("kaesiP") != null) {
				@SuppressWarnings("unchecked")
 	List<String> list = (List<String>) request.getAttribute("kaesiP[]");
 		for (int i = 0; i < list.size(); i++) {
 			out.println(list.get(i));
 %><br>
			<%
 		}
			}
		%>
		<br>以上の情報を削除します。<br>削除するなら確認ボタンを、削除しない場合は戻るボタンを押してください <br>
		<input type="hidden" name="kaesiID" value=<%=request.getAttribute("kaesiID")%>><br>
		<input type="hidden" name="kaesiNAME"value=<%=request.getAttribute("kaesiNAME")%>>
		<input type="submit" value="確認">

	</form>

	<form action="RETURN13" method="post">
		<br>
		<input type="hidden" name="kaesiID" value=<%=request.getAttribute("kaesiID")%>><br>
		<input type="hidden" name="kaesiNAME"value=<%=request.getAttribute("kaesiNAME")%>>
		<input type="submit" value="戻る">

	</form>
</body>
</html>