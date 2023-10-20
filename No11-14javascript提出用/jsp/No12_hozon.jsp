<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>No12_hozon</title>
</head>
<body>
	<form action="HOZON12" method="post">

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

		<br> <input type="hidden" name="kaesiID"value=<%=request.getAttribute("kaesiID")%>>
		<input type="hidden" name="kaesiNAME"value=<%=request.getAttribute("kaesiNAME")%>>
		<input type="hidden" name="hensinID"value=<%=request.getAttribute("hensinID")%>>
		<input type="hidden" name="hensinNAME"value=<%=request.getAttribute("hensinNAME")%>>
		<input type="submit" value="保存">

	</form>

	<form action="RETURN12" method="post">
		<br> <input type="hidden" name="hantei" value="0"> <%//No12.jspに戻る %>
		<input type="hidden" name="kaesiID"value=<%=request.getAttribute("kaesiID")%>>
		<input type="hidden" name="kaesiNAME"value=<%=request.getAttribute("kaesiNAME")%>>
		<input type="hidden" name="hensinID"value=<%=request.getAttribute("hensinID")%>>
		<input type="hidden" name="hensinNAME"value=<%=request.getAttribute("hensinNAME")%>>
		<input type="hidden" name="kaesi" value="1">
		<input type="hidden" name="kaesiH" value="1">

		<input type="submit" value="戻る">

	</form>
</body>
</html>