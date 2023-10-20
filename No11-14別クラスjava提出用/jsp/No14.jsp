<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

		<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>No14</title>
</head>

<%
	String resP = null;
	String search = "kensaku";
	if (request.getAttribute("kaesiP") != null) {
		resP = request.getAttribute("kaesiP").toString();
	}
%>


<body>
	<form action="SELECT" method="post">
	<div style="padding:background-color: #FFFFFF; width:300px; text-align: center;">
		社員情報検索画面<br> 削除したい社員情報を入力して下さい<br>
		<%
			if (request.getAttribute("kaesiP") != null) {
		%><font color="red"> <%
				@SuppressWarnings("unchecked")
 	List<String> list = (List<String>) request.getAttribute("kaesikekka");
 		for (int i = 0; i < list.size(); i++) {
 			out.println(list.get(i));
 %><br></font>
 <%
 }
 		}%>
		<input type="text" name="<%=search%>"><br>
		社員IDをA001～Z999の間の半角英数字で入力して下さい<br>
		＊社員IDは大文字のアルファベット+3桁の数字の組み合わせです<br>(000の並びは使用できません)<br>
		255文字以内で入力して下さい<br></div>
		　　　
		&emsp;&emsp;&emsp;&emsp;<input type="submit" value="検索">
　　　		<p>&emsp;&emsp;優先順位　　&emsp;&emsp;&emsp;検索条件</p>
　　　		<p>&emsp;<input type="radio" name="prime_juni" value="syainID" checked>社員ID&emsp;&emsp;
           <input type="radio" name="con" value="front" checked>前方一致
           <input type="radio" name="con" value="all">全て一致</p>
　　　		<p>&emsp;<input type="radio" name="prime_juni" value="syainNAME">社員名&emsp;&emsp;
           <input type="radio" name="con" value="back">後方一致
           <input type="radio" name="con" value="part" >部分一致</p>　　　
         <p>社員ID表示順&emsp;&emsp;&emsp;&emsp;社員名表示順
　　　<p>&emsp;<input type="radio" name="id_juni" value="ASC" checked>昇順&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
       <input type="radio" name="name_juni" value="ASC"checked>昇順</p>　　　
　　　<p>&emsp;<input type="radio" name="id_juni" value="DESC" >降順&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
       <input type="radio" name="name_juni" value="DESC">降順</p>　



	</form>

	<form action="IDOU" method="post">
	&emsp;&emsp;&emsp;&emsp;&emsp;<input type="submit" value="メニュー画面へ戻る">
	</form>

</body>
</html>