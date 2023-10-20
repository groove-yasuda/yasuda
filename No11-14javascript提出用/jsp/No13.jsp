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
	String id_red = "0";
	String name_red = "0";
	String workID = "";
	String workNAME = "";
	String ID = "syainID";
	String NAME = "syainNAME";
	String release = "";
	String kaesiP = "";
	String point = "";
	if(request.getAttribute("release")!=null)
	{
	release = request.getAttribute("release").toString();
	}
	if(request.getAttribute("kaesiP")!=null)
	{
	kaesiP = request.getAttribute("kaesiP").toString();
	}
	System.out.println(kaesiP);

	if(request.getAttribute("point")!=null)
	{
	point = request.getAttribute("point").toString();
	}
	System.out.println("point="+point);

	if(point != ""||release!="")
	{
		workID = request.getAttribute("kaesiID").toString();
		workNAME = request.getAttribute("kaesiNAME").toString();
		System.out.println("mae");
	}
%>
<body onload = "color1();color2();modori()">
		<input type="hidden" id="ID" name="ID" value= "<%=workID%>"  onchange = "modori()">
		<input type="hidden" id="NAME" name="NAME" value= "<%=workNAME%>"  onchange = "modori()">
	<script>
	function chg(id)
    {
 	   document.No13.action = id;
 	   document.No13.submit();
    }

	function modori()
	{
	if (document.getElementById("release").value != "" || document.getElementById("kaesiP").value != "")
		{
		document.getElementById("syainID").value = document.getElementById("ID").value;
		document.getElementById("syainNAME").value = document.getElementById("NAME").value;
		}
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
		if(document.getElementById("syainID").value != "" || document.getElementById("syainNAME").value != "")
			{
			document.getElementById("DELETE").disabled = false;
			}
		else
			{
			document.getElementById("DELETE").disabled = true;
			}

 	}

   </script>

	<form action="DELETE" method="post" name="No13">
		&emsp;&emsp;&emsp;&emsp;社員情報削除画面<br> 削除したい社員情報を入力して下さい<br>
		<br>
		<%
			if (request.getAttribute("kaesiP") != null) {
				release = request.getAttribute("release").toString();
				kaesiP = request.getAttribute("kaesiP").toString();
		%><font color="red"> <%
				@SuppressWarnings("unchecked")
 	List<String> list = (List<String>) request.getAttribute("kaesiP[]");
 		for (int i = 0; i < list.size(); i++) {
 			out.println(list.get(i));
%><br> <%
				}
 		id_red = request.getAttribute("id_point").toString();
 		name_red = request.getAttribute("name_point").toString();
			%></font><br><%
			}
		%>

		社員ID:<input type="text" name="<%=ID%>" id="syainID" onchange = "active()"><br>
		社員IDをA001～Z999の間の半角英数字で入力して下さい<br>
		＊社員IDは大文字のアルファベット+3桁の数字の組み合わせです<br>(000の並びは使用できません)<br> <br>
		社員名:<input type="text" name="<%=NAME%>" id="syainNAME" onchange = "active()"><br>
		社員名を255文字以内で入力して下さい<br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
		<input type="hidden" id="id_red" value= "<%=id_red%>" onchange = "color1()">
		<input type="hidden" id="name_red" value= "<%=name_red%>"  onchange = "color2()">
				<input type="hidden" id="release" value= "<%=release%>"  onchange = "modori()">
		<input type="hidden" id="kaesiP" value= "<%=kaesiP%>"  onchange = "modori()">
		<input type="button" value="削除" id="DELETE" onclick="chg(this.id)" disabled>


		<br> <br>&emsp;&emsp;&emsp;&emsp;
		<input type="button" value="メニュー画面へ戻る" id="IDOU" onclick="chg(this.id)">
	</form>



</body>
</html>