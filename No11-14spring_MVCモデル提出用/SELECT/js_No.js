
	function chg(id)
    {
 	   document.No.action = id;
 	   document.No.submit();
    }

	function color1()
    {
	if(document.getElementById("id_red").value == "1")
		{
			document.getElementById("syainID").style.backgroundColor = "red";
		}
	if(document.getElementById("name_red").value == "1")
		{
			document.getElementById("syainNAME").style.backgroundColor = "red";
		}
    }
	function active(button)
    {
		//alert("a")
	if(document.getElementById("syainID").value != "" &&
			document.getElementById("syainNAME").value != "")
		{
			document.getElementById(button).disabled = false;
		}
	else
		{
			document.getElementById(button).disabled = true;
		}
 	}
