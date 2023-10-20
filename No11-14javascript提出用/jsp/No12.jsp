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
System.out.println("------------");
	String kaesi = "0";
	String id_red = "";
	String name_red = "";
	String resP = "";
	String kousinkakunin = "";
	String kousin = "";
	String workID = "";
	String workNAME = "";
	String maeID = "";
	String maeNAME = "";
	String ID = "syainID";
	String NAME = "syainNAME";
	String release ="";
	String kaesiP = "";
	String point = "";
	//エラー表記のため
	//更新前か後かの判定
	if (request.getAttribute("kaesiH") != null)
	{
		kousinkakunin = request.getAttribute("kaesiH").toString();
		ID = "kousinID";
		NAME = "kousinNAME";
		maeID = request.getAttribute("kaesiID").toString();
	 	maeNAME = request.getAttribute("kaesiNAME").toString();
		System.out.println("更新後判定=OK");
	}

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

		if((kousinkakunin == "" && point != "")||(kousinkakunin == ""&&release!=""))
		{
			workID = request.getAttribute("kaesiID").toString();
			workNAME = request.getAttribute("kaesiNAME").toString();
			System.out.println("mae");
		}
		else if((kousinkakunin.equals("1") && point.equals("1"))||(kousinkakunin.equals("1")&&release!=""))
		{
			workID = request.getAttribute("hensinID").toString();
			workNAME = request.getAttribute("hensinNAME").toString();
			System.out.println("ato");
		}

	System.out.println("workID="+workID);
	System.out.println("kaesiH="+request.getAttribute("kaesiH"));
	System.out.println("kousinkakunin = "+kousinkakunin);
	System.out.println("release = "+release);
	System.out.println("maeID="+maeID);
	System.out.println("maeNAME="+maeNAME);
%>


<body onload="color1();color2();modori();kousin()">
		<input type="hidden" id="release" value= "<%=release%>"  onchange = "modori()">
		<input type="hidden" id="ID" name="ID" value= "<%=workID%>"  onchange = "modori()">
		<input type="hidden" id="NAME" name="NAME" value= "<%=workNAME%>"  onchange = "modori()">
		<input type="hidden" id="kousinkakunin" value= "<%=kousinkakunin%>" onchange = "kousin()">
	<script>
	function kousin()
	{
		 if(document.getElementById("kousinkakunin").value == "1")
			{
			document.getElementById("UPDATE").value = "確認"
			document.getElementById("RETURN12").disabled = false;
			}
	}

	function insert_id()
	{
		if(document.getElementById("kousinkakunin").value == "1")
			{
			document.getElementById("hensinID").value = document.getElementById("syainID").value;
			}
		else
			{
			document.getElementById("kaesiID").value = document.getElementById("syainID").value;
			}
	}
	function insert_name()
	{
		if(document.getElementById("kousinkakunin").value == "1")
			{
			document.getElementById("hensinNAME").value = document.getElementById("syainNAME").value;
			}
		else
			{
			document.getElementById("kaesiNAME").value = document.getElementById("syainNAME").value;
			}
	}

	function modori()
	{
		//alert("ko")
		if(document.getElementById("kousinkakunin").value == "")
			{
			//alert("ma")
				if (document.getElementById("release").value != "" ||
					document.getElementById("kaesiP").value != "")
					{
					//alert("ti")
					document.getElementById("syainID").value = document.getElementById("ID").value;
					document.getElementById("syainNAME").value = document.getElementById("NAME").value;
					}
			}
		else if(document.getElementById("kousinkakunin").value == "1")
		{
			//alert("i")
				if (document.getElementById("release").value != "" ||
					document.getElementById("kaesiP").value == "1")
					{
					//alert("ru")
					document.getElementById("syainID").value = document.getElementById("ID").value;
					document.getElementById("syainNAME").value = document.getElementById("NAME").value;
					}
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

	function chg(id)
    {
 	   document.No12.action = id;
 	   document.No12.submit();
    }

	function active()
    {
		//alert("a")
		if(document.getElementById("syainID").value != "" || document.getElementById("syainNAME").value != "")
			{
			document.getElementById("UPDATE").disabled = false;
			}
		else
			{
			document.getElementById("UPDATE").disabled = true;
			}

 	}

   </script>

	<form action="UPDATE" method="post" name="No12">
		社員情報更新画面<br> 更新したい情報を入力して下さい<br>
		<%
			System.out.println("release = "+release);

			if (request.getAttribute("kaesiP") != null) {

		%><font color="red"> <%
				@SuppressWarnings("unchecked")
 	List<String> list = (List<String>) request.getAttribute("kaesiP[]");
 		for (int i = 0; i < list.size(); i++) {
 			out.println(list.get(i));
 %><br>
			<%
				}
 		id_red = request.getAttribute("id_point").toString();
 		name_red = request.getAttribute("name_point").toString();
			%></font><br>
		<%
			}
		%>

		<input type="hidden" name="kaesiID" id="kaesiID" value="<%=maeID%>">
		<input type="hidden" name="kaesiNAME" id="kaesiNAME" value="<%=maeNAME%>">

		社員ID:<input type="text" name="<%=ID%>" id="syainID" onchange = "active();insert_id()"><br>
		社員IDをA001～Z999の間の半角英数字で入力して下さい<br>
		＊社員IDは大文字のアルファベット+3桁の数字の組み合わせです(000の並びは使用できません)<br> <br>
		社員名:<input type="text" name="<%=NAME%>" id="syainNAME" onchange = "active();insert_name()"><br>
		社員名を255文字以内で入力して下さい<br>


		<input type="hidden" id="id_red" value= "<%=id_red%>" onchange = "color1()">
		<input type="hidden" id="name_red" value= "<%=name_red%>"  onchange = "color2()">
		<input type="hidden" id="kaesiP" value= "<%=kaesiP%>"  onchange = "modori()">

		<input type="button" value="更新" id="UPDATE" onclick="chg(this.id)" disabled>

		<input type="hidden" name="kaesi" value="<%=kaesi%>">
		<%
		System.out.println("更新確認　nullなら更新前　１なら更新後　="+kousinkakunin);
		%>
		<%
		if (("1").equals(kousinkakunin)) {
				kousin = kousinkakunin;
				System.out.println("更新中");
				System.out.println("maeID= "+maeID);
		%>

		更新後
			<input type="hidden" name="hantei" value="1"><br>
			<input type="hidden" name="han" value="0">
					<%
				}
			 if(("0").equals(kousinkakunin))
				{
				 kaesi = "1";
				}

			 System.out.println("javaに送るID="+maeID);
				%>
			<input type="button" value="戻る" id="RETURN12" onclick="chg(this.id)" disabled>
				</form>
<br>
	<form action="IDOU" method="post">
		<input type="submit" value="メニュー画面へ戻る">
	</form>



</body>
</html>