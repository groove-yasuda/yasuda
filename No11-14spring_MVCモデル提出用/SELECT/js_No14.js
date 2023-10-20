
	function color1()
    {
	if(document.getElementById("red").value == "1")
		{
    		document.getElementById("kensaku").style.backgroundColor = "red";
		}
    }
	
	function active(button)
    {
		//alert("a")
	if(document.getElementById("kensaku").value != "")
		{
			document.getElementById(button).disabled = false;
		}
	else
		{
			document.getElementById(button).disabled = true;
		}

 	}
	