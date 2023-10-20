	
	
	
	//すでに借りている冊数
	var avail_Books_Count=0;
	
	
	//確認画面の書籍情報をテーブルに表示
	function RE_TBL_CONFI() {
		const book_Table = $("#book_Table")[0];
		const tbody = book_Table.querySelector("tbody");
		const books_Text = $("#list").val().replace(/\[|\]/g, ""); // [ ]を削除
		const books_Array = books_Text.split(", ");
		
		// booksの情報をテキストボックスに表示
		$("#list_Send").val(books_Text);
		$("#avail_Send").val(avail_Books_Count);
		
		// テーブルの内容をクリア
		tbody.innerHTML = "";
		
		// 書籍情報の表示
		for (let i = 0; i < books_Array.length; i += 5) {
			//books_Arrayの前から五つ分のデータを取り出してbookに格納
			const book = {
				return_Date: books_Array[i],
				name: books_Array[i + 1],
				code: books_Array[i + 2],
				code_Branch: books_Array[i + 3],
				genre_Name: books_Array[i + 4],
			};
			
			// テキスト形式の日付をDateオブジェクトに変換
			const target_Date = new Date(book.return_Date);
			const current_Date = new Date(); // 現在の日付
			const tomorrow_Date = new Date(current_Date);
			tomorrow_Date.setDate(current_Date.getDate() - 1);//昨日の日付
			
			//格納したデータを表示
			const row = tbody.insertRow();
			row.insertCell(0).innerText = book.name;
			row.insertCell(1).innerText = book.code;
			row.insertCell(2).innerText = book.code_Branch;
			row.insertCell(3).innerText = book.genre_Name;
			
			//書籍の貸出期限チェック
			if (tomorrow_Date > target_Date) {
				row.style.backgroundColor = "red";
			} else {
				row.style.backgroundColor = "white";
			}
		}
	}
	
	
