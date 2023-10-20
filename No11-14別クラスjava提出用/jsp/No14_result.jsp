<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>No14_result</title>
</head>

<body>
	<form action="RESULT" method="post">
		<div
			style="padding: 10px; margin-top: 10px; margin-bottom: 10px;border: 1px solid #333333; background-color: #FFFFFF; width: 700px; text-align: center;">

			<%
				if (request.getAttribute("kaesiP") != null) {
			%>検索結果<br> 文字:<%=request.getAttribute("kaesimoji")%><br>
			<%
			@SuppressWarnings("unchecked")
				List<String> list = (List<String>) request.getAttribute("kaesikekka");
					for (int i = 0; i < list.size(); i++) {
			%>
			<div
				style="padding: 10px; margin-bottom: 5px; border: 1px solid #333333; background-color: #FFFFFF; width: 600px; text-align: center; margin-left: 48px;">
				<%
					out.println(list.get(i));
				%>
			</div>
			<%
				}
				}
			%>

			<input type="submit" value="戻る"> <br> <input
				type="hidden" name="hantei" value="0">
			<%
				//No12.jspに戻る
			%>
		</div>
	</form>

	<form action="RESULT" method="post">
	<div style="padding:background-color: #FFFFFF; width:720px; text-align: center;">
		<br> <input type="hidden" name="hantei" value="1">
		<%
			//No12.jspに戻る
		%>
		<input type="submit" value="終了">
		</div>
	</form>

</body>
</html>