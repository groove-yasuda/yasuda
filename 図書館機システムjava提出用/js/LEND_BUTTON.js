
	// ボタンの状態と残り冊数チェック
	function CHK_BTN(books) {
		
		//残り貸出冊数を計算(最大貸出冊数-すでに借りている冊数-選択された書籍数)
		const remaining_Books = max_Row_Count - avail_Books_Count - books.length;
		$("#avail_Send").val(avail_Books_Count);
		console.log(avail_Books_Count);
		
		//残り貸出冊数の値によって処理を振り分ける
		if (remaining_Books > 0) {
			$("#remaining_Books").text(remaining_Books); // 残り冊数を表示
			elem = ""
			$("#LEND_FUNC_EM").html(elem);
		}
		else if(avail_Books_Count == max_Row_Count)//貸し出してる冊数がMAXの時
		{
			$("#remaining_Books").text(0);//残り貸出冊数が0冊
			$("#card_Number").css("background-color", "red");
			elem = "<font color=red> ";
			elem += "<li>" + "貸出冊数の上限に達しました　これ以上貸出ができません" + "</li>";
			elem += "</font>";
			$("#LEND_FUNC_EM").html(elem);
		}
		else
		{
			$("#remaining_Books").text(remaining_Books); // 残り冊数を表示
		}
		
		//登録ボタンの活性、非活性の判定
		if (books.length > 0) {
			$("#LEND_FUNC_CONFI").prop("disabled", false); // 活性化
		} else {
			$("#LEND_FUNC_CONFI").prop("disabled", true); // 非活性化
		}
		
		//取り消しボタンの活性化の数を定める
		$(".cancel-button").prop("disabled", (index) => index >= max_Row_Count - avail_Books_Count);
	}
	
	
	//検索ボタンの活性、非活性の制御
	function CHK_SEARCH_BUTTON() {
		var book_Name = $("#book_Name").val();
		var book_Number = $("#book_Number").val();
		
		if (book_Name != "" || book_Number != "") {
			$("#SRCH_LIST").prop("disabled", false); // 活性化
		} else {
			$("#SRCH_LIST").prop("disabled", true); // 非活性化
		}
	}
        
 