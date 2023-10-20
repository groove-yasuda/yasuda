

	//すでに借りている冊数
	var avail_Books_Count=0;
	
	//画面読み込み開始時に呼び出される処理
	function CONFI_BIGIN(){
		CONFI_CHK_AVAIL(CONFI_RE_TBL)
	} 
	
	//すでに借りている冊数チェック
	function CONFI_CHK_AVAIL(callback) {
		var card_Number = $("#card_Number").val();
		$.ajax({
			url: "http://localhost:8080/CHK_AVAIL",
			type: "POST",
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({
				scrn_Var: { card_Number: card_Number },
			}),
		}).done(function (availBooksCount) {
			avail_Books_Count = availBooksCount;
			callback();
		}).fail(function () {
			console.log("fail");
		});
	}
	
	//貸出確認用のテーブルを表示
	function CONFI_RE_TBL() {
		const book_Table = $("#book_Table")[0];
		const tbody = book_Table.querySelector("tbody");
		const books_Text = $("#list").val().replace(/\[|\]/g, ""); // [ ]を削除
		const books_Array = books_Text.split(", ");
		
		// booksの情報をテキストボックスに表示
		$("#list_Send").val(books_Text);
		console.log(books_Text)
		
		// テーブルの内容をクリア
		tbody.innerHTML = "";
		
		// 書籍情報の再表示
		for (let i = 0; i < books_Array.length; i += 5) {
			const book = {
				name: books_Array[i],
				number: books_Array[i + 1],
				number_Branch: books_Array[i + 2],
				genre_Name: books_Array[i + 3],
				genre_Number: books_Array[i + 4],
			};
			
			const row = tbody.insertRow();
			UPD_TBL(row,book);
			
			if (book) {
				const cancel_Button = document.createElement("button");
				cancel_Button.innerText = "取り消し";
				cancel_Button.classList.add("cancel-button");
				cancel_Button.disabled =true ; // ボタンを非活性化
				row.insertCell(5).appendChild(cancel_Button);
			}
		}
	}
    