	
	
	//借りている冊数の変数
	var avail_Books_Count = 0;
	
	
	//すでに借りている冊数のチェック
	function CHK_AVAIL_LEND(callback) {
		var card_Number = $("#card_Number").val();
		$.ajax({
			 url: "http://localhost:8080/CHK_AVAIL_LEND",
			 type: "POST",
			 contentType: "application/json; charset=utf-8",
			 data: JSON.stringify({
				 scrn_Var: { card_Number: card_Number },
			}),
		}).done(function (avail_Books) {
			avail_Books_Count = avail_Books;
			
			callback();
		}).fail(function () {
			console.log("fail");
		});
	}
	
	
	//書籍情報を画面に表示するための準備
	function RET_BIGIN(){
	var epoc = [];
	var elem = "";
	
	//確認ボタンの非活性
	$("#RET_FUNC_CONFI").prop("disabled", true);
		
		if(avail_Books_Count > 0)//借りている書籍がある時
		{
			var table = $("#Mess")[0];
			
			table.style.display = "none";//エラーメッセージのテーブルを非表示
			$("#error_Mess").html(elem);

			const data = JSON.parse($("#list").val()); // JSONデータをパースする
			for (let i = 0; i < data.length; i++) {
				epoc.push(data[i].return_Date);
				receive_Data(data[i],epoc);
			}	
		}
		else if(avail_Books_Count == 0)//借りている書籍がないとき
		{
			var table = $("#book_Table")[0];
			// 検索書籍一覧を非表示にする
			table.style.display = "none";//書籍情報のテーブルを非表示
			elem = "貸し出している書籍はありません";
			$("#error_Mess").html(elem);
		}
	}
	
	
	// 画面からデータを受け取る関数
	function receive_Data(data,epoc) {
		// 空の行にデータを挿入する
		const newBook = {
			return_Disp: convert_Epoch_To_Disp(data.return_Date), // エポックタイムテーブルに表示する日付に変換
			return_Date: convert_Epoch_To_Date(data.return_Date), // エポックタイムを比較するための日付に変換
			name: data.book_Name,
			code: data.book_Number,
			code_Branch: data.book_Number_Branch,
			genre_Name: data.genre_Name
		};
		
		addBook(newBook,epoc); // 書籍情報を追加する
	}
	
	
	// ユーティリティ関数: エポックタイムをyyyy-MM-dd形式に変換する
	function convert_Epoch_To_Date(epochTime) {
		const dateObj = new Date(epochTime);
		const year = dateObj.getFullYear();
		const month = String(dateObj.getMonth() + 1).padStart(2, '0');
		const day = String(dateObj.getDate()).padStart(2, '0');
		return `${year}-${month}-${day}`;
	}
	
	
	// ユーティリティ関数: エポックタイムをMM/ddまでの形式に変換する
	function convert_Epoch_To_Disp(epochTime) {
		const dateObj = new Date(epochTime);
		const month = String(dateObj.getMonth() + 1).padStart(2, '0');
		const day = String(dateObj.getDate()).padStart(2, '0');
		return `${month}/${day}まで`;
	}
	
	
	// 書籍情報を追加する関数
	function addBook(bookData,epoc) {
		books.push(bookData); // 配列booksに書籍情報を追加
		return_Table(epoc); // テーブル更新
	}
	
	
	
	
	
	
	
    
    