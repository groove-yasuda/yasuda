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
	String red = "0";
	String resP = "";
	String search = "kensaku";
	System.out.println("resP="+resP);
	if(request.getAttribute("kaesiP")!=null)
	{
		resP = request.getAttribute("kaesiP").toString();
	}
%>
<input type="hidden" id="kaesimoji" value= "<%=request.getAttribute("kaesimoji")%>">
<input type="hidden" id="resP" value= "<%=resP%>">
<body onload="color1();modori()">
	<script>
	function chg(id)
    {
 	   document.No14.action = id;
 	   document.No14.submit();
    }

	function modori()
	{
		if (document.getElementById("resP").value != "")
			{
			document.getElementById("kensaku").value = document.getElementById("kaesimoji").value;
			}
	}

	function color1()
    {
		if(document.getElementById("red").value == "1")
			{
    		document.getElementById("kensaku").style.backgroundColor = "red";
			}
    }
	function active()
    {
		//alert("a")
		if(document.getElementById("kensaku").value != "")
			{
			document.getElementById("SELECT").disabled = false;
			}
		else
			{
			document.getElementById("SELECT").disabled = true;
			}

 	}

   </script>

	<form action="SELECT" method="post" name="No14">
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
 		red = request.getAttribute("kensaku_point").toString();
 		}
 		System.out.println("resP="+resP);
 		%>
 		<input type="hidden" id="red" value= "<%=red%>" onchange = "color1()">
 		<input type="hidden" id="resP" value= "<%=resP%>" onchange = "modori()">
		<input type="text" name="<%=search%>" id="kensaku" onchange = "active()"><br>
		社員IDをA001～Z999の間の半角英数字で入力して下さい<br>
		＊社員IDは大文字のアルファベット+3桁の数字の組み合わせです<br>(000の並びは使用できません)<br>
		255文字以内で入力して下さい<br></div>
		&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
		<input type="button" value="検索" id="SELECT" onclick="chg(this.id)" disabled>
　　　		<p>&emsp;&emsp;優先順位　　&emsp;&emsp;&emsp;検索条件</p>
<p>&emsp;<input type="radio" name="prime_juni" value="syainID" checked>社員ID
&emsp;&emsp;<input type="radio" name="con" value="front" checked>前方一致
         <input type="radio" name="con" value="all">全て一致</p>
　　　		<p>&emsp;
		<input type="radio" name="prime_juni" value="syainNAME">社員名&emsp;&emsp;
        <input type="radio" name="con" value="back">後方一致
        <input type="radio" name="con" value="part" >部分一致</p>　　　
         <p>&emsp;社員ID表示順&emsp;&emsp;&emsp;&emsp;社員名表示順
　　　<p>&emsp;<input type="radio" name="id_juni" value="ASC" checked>昇順
		&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
        <input type="radio" name="name_juni" value="ASC"checked>昇順</p>　　　
　　　<p>&emsp;<input type="radio" name="id_juni" value="DESC" >降順
		&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
        <input type="radio" name="name_juni" value="DESC">降順</p>　


	&emsp;&emsp;&emsp;
	<input type="button" value="メニュー画面へ戻る" id="IDOU" onclick="chg(this.id)">
	</form>

</body>
</html>