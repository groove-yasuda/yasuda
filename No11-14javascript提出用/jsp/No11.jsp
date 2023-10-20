<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>No11</title>
</head>

<%
System.out.println("------------");
String id_red = "0";
String name_red = "0";
String release = "";
String kaesiP = "";
String ID = "";
String NAME = "";
if(request.getAttribute("release") != null)
{
	release = request.getAttribute("release").toString();
	ID = request.getAttribute("kaesiID").toString();
	NAME = request.getAttribute("kaesiNAME").toString();
}
%>
		<input type="hidden" id="ID" value= "<%=ID%>"  onchange = "modori()">
		<input type="hidden" id="NAME" value= "<%=NAME%>"  onchange = "modori()">


<body onload="color1();color2();modori()">
	<script>

	function modori()
	{
	if (document.getElementById("release").value != "" ||
		document.getElementById("kaesiP").value != "")
		{
		document.getElementById("syainID").value = document.getElementById("ID").value;
		document.getElementById("syainNAME").value = document.getElementById("NAME").value;
		}
	}

	function chg(id)
    {
 	   document.No11.action = id;
 	   document.No11.submit();
    }

	function color1()
    {
		if(document.getElementById("id_red").value == "1")
			{
			document.getElementById("syainID").style.backgroundColor = "red";
			}
    }
	function color2()
    {
		if(document.getElementById("name_red").value == "1")
			{
			document.getElementById("syainNAME").style.backgroundColor = "red";
			}
     }
	function active()
    {
		//alert("a")
		if(document.getElementById("syainID").value != "" ||
				document.getElementById("syainNAME").value != "")
			{
			document.getElementById("INSERT").disabled = false;
			}
		else
			{
			document.getElementById("INSERT").disabled = true;
			}
 	}

   </script>

	<form action="INSERT" method="post" name="No11">
		社員情報登録画面<br>
		<%
		System.out.println("release="+release);
		System.out.println("kaesiP="+kaesiP);
			if (request.getAttribute("kaesiP") != null) {
		%><font color="red"> <%
				@SuppressWarnings("unchecked")
 	List<String> list = (List<String>) request.getAttribute("kaesiP[]");
 		for (int i = 0; i < list.size(); i++)
 			{
 			out.println(list.get(i));
 			}
 		id_red = request.getAttribute("id_point").toString();
 		name_red = request.getAttribute("name_point").toString();
			%><br> <%
 		}
			System.out.println("id_red="+id_red);
			System.out.println("name_red="+name_red);
%><br> </font><br>
		社員ID:<input type="text" id="syainID" name="syainID" onchange = "active()"><br>
		社員IDをA001～Z999の間の半角英数字で入力して下さい<br>
		＊社員IDは大文字のアルファベット+3桁の数字の組み合わせです(000の並びは使用できません)<br> <br>
		社員名:<input type="text" id="syainNAME" name="syainNAME" onchange = "active()"><br>
		社員名を255文字以内で入力して下さい<br>
		<input type="button" value="登録" id="INSERT" onclick="chg(this.id)" disabled>
		<input type="hidden" id="id_red" value= "<%=id_red%>" onchange = "color1()">
		<input type="hidden" id="name_red" value= "<%=name_red%>"  onchange = "color2()">
		<input type="hidden" id="release" value= "<%=release%>"  onchange = "modori()">
		<input type="hidden" id="kaesiP" value= "<%=kaesiP%>"  onchange = "modori()">
		<input type="button" value="メニュー画面へ戻る" id="IDOU" onclick="chg(this.id)">
	</form>



</body>
</html>