	
	
	
	// 書籍情報を画面のテーブルに表示する
	function return_Table(epoc) {
		const bookTable = $("#book_Table")[0];
		const tbody = bookTable.querySelector("tbody");
		
		// テーブルの内容をクリア
		tbody.innerHTML = "";
		
		// 書籍情報を表示
		for (let i = 0; i < books.length; i++) {
			const book = books[i];
			const row = tbody.insertRow();
			
			//チェックボックスの設定
			const selectCheckbox = document.createElement("input");
			selectCheckbox.type = "checkbox";
			selectCheckbox.classList.add("select-checkbox"); // クラスを追加
			selectCheckbox.dataset.index = i; // インデックスをデータ属性として保持
			
			//bookのデータをテーブルに追加する
			const statusCell = row.insertCell(0);
			statusCell.innerText = book.return_Disp;
			row.insertCell(1).innerText = book.name;
			row.insertCell(2).innerText = book.code;
			row.insertCell(3).innerText = book.code_Branch;
			row.insertCell(4).innerText = book.genre_Name;
			row.insertCell(5).appendChild(selectCheckbox);
			
			//日付の情報を比較できるように変換
			const current_Epoch_Time = Date.now(); // 現在のエポックタイム
			const one_Day_Seconds = 24 * 60 * 60 * 1000; // 1日のミリ秒数
			const yesterday_Epoch_Time = current_Epoch_Time - one_Day_Seconds;//昨日のエポックタイム
			const target_Epoch_Time = epoc[i]; // 貸出書籍のエポックタイム
			
			//書籍の返却期限チェック
			if (yesterday_Epoch_Time > target_Epoch_Time) {
				//書籍の返却期限が処理日の一日前より前の日付の時
				row.style.backgroundColor = "red";
				statusCell.style.backgroundColor = "white";
				statusCell.style.color = "red";
				statusCell.innerText = "延滞";
			} else {
				//書籍の返却期限が処理日の一日前より後の日付の時
				row.style.backgroundColor = "white";
				statusCell.style.backgroundColor = "";
				statusCell.style.color = "";
			}
		}
	}
	
	
	//チェックボックスに変化があった時の処理
	$(document).on("change", ".select-checkbox", function() {
		const selected_Index = $(this).data("index");
		const selected_Book = books[selected_Index];
			
		if (this.checked) {
			// チェックされた場合、選択された行の情報を取得し配列に追加
			selected_Books.push(selected_Book);
				
		} else {
			// チェックが外れた場合、配列から削除
			const index = selected_Books.findIndex(book => book === selected_Book);
			if (index !== -1) {
				selected_Books.splice(index, 1);
			}
		}
		
		// 選択された行の情報をテキストボックスに表示
		const selected_Books_Text = selected_Books.map((book) => {
			return `${book.return_Date},${book.name},${book.code},${book.code_Branch},${book.genre_Name}`;
		}).join(", ");
		
		$("#data_Send").val(selected_Books_Text);
		
		if (selected_Books.length > 0) {
			$("#RET_FUNC_CONFI").prop("disabled", false); // 活性化
		} else {
			$("#RET_FUNC_CONFI").prop("disabled", true); // 非活性化
	}
	});
	
	
	
	
	
