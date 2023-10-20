<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
		<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>No11_kakunin</title>
</head>


<body>
	<form action="HOZON" method="post">
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


		<br> <input type="hidden" name="kaesiID" value=<%=request.getAttribute("kaesiID")%>>
		<br> <input type="hidden" name="kaesiNAME" value=<%=request.getAttribute("kaesiNAME")%>>

		<br> <input type="submit" value="保存">

	</form>

	<form action="RETURN" method="post">
		<br> <input type="hidden" name="kaesiID" value=<%=request.getAttribute("kaesiID")%>>
		<br> <input type="hidden" name="kaesiNAME" value=<%=request.getAttribute("kaesiNAME")%>>
			<br> <input type="hidden" name="release" value="1">
		<input type="submit" value="戻る">

	</form>
</body>
</html>