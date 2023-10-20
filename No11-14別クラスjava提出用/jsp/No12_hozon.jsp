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

		<br> <input type="hidden" name="resID"value=<%=request.getAttribute("kaesiID")%>> <br>
		<input type="hidden" name="resNAME"value=<%=request.getAttribute("kaesiNAME")%>> <br>
		<input type="hidden" name="responsID"value=<%=request.getAttribute("hensinID")%>> <br>
		<input type="hidden" name="responsNAME"value=<%=request.getAttribute("hensinNAME")%>> <br>
		<input type="submit" value="保存">

	</form>

	<form action="RETURN12" method="post">
		<br> <input type="hidden" name="hantei" value="0"> <%//No12.jspに戻る %>
		<input type="submit" value="戻る">

	</form>
</body>
</html>