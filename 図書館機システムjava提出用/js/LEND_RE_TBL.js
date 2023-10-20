
	//新しい書籍情報をbooksに追加する関数
	function ADD_BOOK(book_Data,books) {
		//書籍の数が(最大貸出冊数-既に借りている数)より小さいときのみ借りれる
		if (books.length < max_Row_Count - avail_Books_Count) {
			if (!EXIST_CHK(book_Data,books)) {//重複チェックの結果がfalseの時のみ借りることができる
			books.push(book_Data); // 配列booksに新しい書籍情報を追加
			}
			TABLE(books); // テーブル更新
		}else{
			$("#card_Number").css("background-color", "red");
			elem = "<font color=red> ";
			elem += "<li>" + "貸出冊数の上限に達しました　これ以上貸出ができません" + "</li>";
			elem += "</font>";
			$("#LEND_FUNC_EM").html(elem);
		}
		CHK_BTN(books); // 登録ボタンと貸出残り冊数の状態を更新
	}
	
	
	// 書籍情報を削除する関数
	function REMOVE_BOOK(index,books) {
		books.splice(index, 1); // 選択した書籍情報をbooksから削除
		CHK_BTN(books); // 登録ボタンと貸出残り冊数の状態を更新
		TABLE(books); // テーブルを更新して削除を反映
	}
	
	
	//書籍表示テーブル
	function TABLE(books) {
		const book_Table = $("#book_Table")[0];
		const tbody = book_Table.querySelector("tbody");
    	tbody.innerHTML = "";
    	
    	SEND(books);
    	
    	for (let i = 0; i < max_Row_Count; i++) {
			const row = tbody.insertRow();
			const book = books[i];
			
			//取り消しボタンの設定
			const cancel_Button = document.createElement("button");
			cancel_Button.innerText = "取り消し";
			cancel_Button.classList.add("cancel-button");
			cancel_Button.disabled = i >= (max_Row_Count - avail_Books_Count);
			
			//取り消しボタン押下時の設定
			cancel_Button.addEventListener("click", function() {
				REMOVE_BOOK(i, books);
				CHK_BTN(books);
			});
			
			if(book){
				UPD_TBL(row,book);//bookにデータがある時に要素ごとのセルを追加
			}else{
				NML_TBL(row);//bookにデータがないときに空文字のセルを追加
			}
			
			row.insertCell(5).appendChild(cancel_Button);
			
			// 既に借りている書籍数に応じて表の背景色を設定
			if (i < (max_Row_Count - avail_Books_Count)) {
				row.style.backgroundColor = "white";
			} else {
				row.style.backgroundColor = "#909090";
			}		
		}
	}                   
     
     
     //借りたい書籍情報をsubmitで送信するための準備
     function SEND(books){
		// books配列から書籍情報をまとめてテキストに変換
		const books_Text = books.map((book) => {
			return `${book.name},${book.number},${book.number_Branch},${book.genre_Name},${book.genre_Number}`;
		}).join(", ");
		
		// booksの情報をテキストボックスに表示
		$("#list_Send").val(books_Text);
	 }
	 
	 
	 //空文字のセルをテーブルに一行分追加する
	 function NML_TBL(row){
		for (let j = 0; j < 5; j++) {
			row.insertCell(j).innerText = "";
			}
	 }
	 
	 
	 //bookのデータをテーブルに追加する
	 function UPD_TBL(row,book)
     {
		row.insertCell(0).innerText = book.name;
		row.insertCell(1).innerText = book.number;
		row.insertCell(2).innerText = book.number_Branch;
		row.insertCell(3).innerText = book.genre_Name;
		row.insertCell(4).innerText = book.genre_Number;   
	 }
	 
	 
	 
	 
	 
  