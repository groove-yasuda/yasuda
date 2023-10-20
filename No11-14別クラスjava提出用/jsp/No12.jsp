<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>No12</title>
</head>

<%
	String resP = null;
	String kousinkakunin = null;
	String hogoID = "";
	String hogoNAME = "";
	String ID = "syainID";
	String NAME = "syainNAME";

	//エラー表記のため
	if (request.getAttribute("kaesiP") != null) {
		resP = request.getAttribute("kaesiP").toString();
	}
	//更新前か後かの判定
	if (request.getAttribute("kaesiH") != null) {
		kousinkakunin = request.getAttribute("kaesiH").toString();
		hogoID = request.getAttribute("kaesiID").toString();
		hogoNAME = request.getAttribute("kaesiNAME").toString();
		ID = "kousinID";
		NAME = "kousinNAME";
	}
%>


<body>
	<form action="UPDATE" method="post">
		社員情報更新画面<br> 更新したい情報を入力して下さい<br>

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
		＊社員IDは大文字のアルファベット+3桁の数字の組み合わせです(000の並びは使用できません)<br> <br>
		社員名:<input type="text" name="<%=NAME%>"><br>
		社員名を255文字以内で入力して下さい<br>

		<%
			if (kousinkakunin == null) {
		%>
		更新前
		<input type="hidden" name="hantei" value="0"><%//更新前判定に進む %>
		<input type="submit" value="更新">
</form>
		<%
			} else if (kousinkakunin == "1") {
		%>
		更新後
		<input type="hidden" name="hantei" value="1"><%//更新中判定に進む %>
		<input type="hidden" name="watasiID" value="<%=hogoID%>">
		<input type="hidden" name="watasiNAME" value="<%=hogoNAME%>">
		<input type="submit" value="確認">
				<form action="RETURN12" method="post">
				<br>
		<input type="submit" value="戻る">
				<input type="hidden" name="hantei" value="0">
	</form>
		<%
}
%>
<br>
	<form action="IDOU" method="post">
		<input type="submit" value="メニュー画面へ戻る">
	</form>



</body>
</html>