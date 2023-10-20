	
	
	
	
	//図書カード番号の桁数チェック
	function NUM_DIG_CHK(chk_In) {
		var book_Number = $("#book_Number").val();
		 $.ajax({
			 url:"http://localhost:8080/NUM_DIG_CHK",
			 type: "POST",
			 contentType: "application/json; charset=utf-8",
			 data: JSON.stringify({
				 scrn_Var: {book_Number : book_Number},
			})
		}).done(function(num_Dig_Result) {
			var elem = "";
			
			if (num_Dig_Result == true) //桁数が合っている場合
			{
				$("#card_Number").css("background-color", "white");
				elem = "";
				$("#NUM_DIG_EM").html(elem);
				flag_CN = true;
			} 
			else if (num_Dig_Result == false) //桁数が間違っている場合
			{
				$("#card_Number").css("background-color", "red");
				elem = "<font color=red> ";
				elem += "<li>" + "書籍番号の桁数は13桁以内で入力してください。" + "</li>";
				elem += "</font>";
				$("#NUM_DIG_EM").html(elem);
				flag_CN = false;
			}
			
			chk_In();
		}).fail(function() {
			console.log("fail");
		});		
	}
	
	//図書カード番号の形式チェック
	function NUM_FORM_CHK(chk_In) {
		var book_Number = $("#book_Number").val();
		 $.ajax({
			 url:"http://localhost:8080/NUM_FORM_CHK",
			 type: "POST",
			 contentType: "application/json; charset=utf-8",
			 data: JSON.stringify({
				 scrn_Var: {book_Number : book_Number},
				 })
		}).done(function(num_Form_Result) {
			var elem = "";
			if (num_Form_Result == true) //桁数が合っている場合
			{
				$("#book_Number").css("background-color", "white");
				$("#NUM_FORM_EM").html(elem);
				flag_CN = true;
			}
			else if (num_Form_Result == false) //桁数が間違っている場合
			{
				$("#book_Number").css("background-color", "red");
				elem = "<font color=red> ";
				elem += "<li>" + "書籍番号は半角数字で入力して下さい。" + "</li>";
				elem += "</font>";
				$("#NUM_FORM_EM").html(elem);
				flag_CN = false;
			}		
			chk_In();
		}).fail(function() {
			console.log("fail");
		});
	}
	
	 // 既に同じ書籍が存在するかチェック
	 function EXIST_CHK(book,books) {
		return books.some((exist_Book) => {
			return (
				exist_Book.name === book.name &&
				exist_Book.number === book.number &&
				exist_Book.number_Branch === book.number_Branch &&
				exist_Book.genre_Name === book.genre_Name &&
				exist_Book.genre_Number === book.genre_Number
			);
		});
	}
	
	//すでに借りている冊数チェック
	function CHK_AVAIL(callback,books) {
		var card_Number = $("#card_Number").val();
		$.ajax({
			 url: "http://localhost:8080/CHK_AVAIL",
			 type: "POST",
			 contentType: "application/json; charset=utf-8",
			 data: JSON.stringify({
				 scrn_Var: { card_Number: card_Number },
			}),
		}).done(function (avail_Books) {
			avail_Books_Count = avail_Books;
			callback(books);
		}).fail(function () {
			console.log("fail");
		});
	}
		
	function AFT_CHK_AVAIL(books) { 
		//検索ボタンの状態チェックと貸出残り冊数チェック
		CHK_BTN(books);
		TABLE(books);
	}
         
         
         
          
          
          
          
          
          