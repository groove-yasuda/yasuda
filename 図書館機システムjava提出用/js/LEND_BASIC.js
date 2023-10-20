
		var avail_Books_Count=0;//残り貸出可能な冊数   
        const max_Row_Count = 5;// 表示する行数
        var books = [];//書籍情報保持配列
        
        
		
		
		// ページが読み込まれたときに実行される関数

		function BIGIN(judge){
			if ($("#book_Data_List").val() != "1") //1の時は選択画面から、1出ないときは戻りボタンから画面遷移したとき
			{
				const data = JSON.parse($("#book_Data_List").val()); // JSONデータを変換する
				
				for (let i = 0; i < data.length; i++) {
					RECE_DATA(data[i],books);//戻りボタン押下時のlist追加処理
				}
			}
			
			if(judge === "0"){
				CHK_AVAIL(AFT_CHK_AVAIL,books);
			}
			
			// 書籍名入力欄の値が変更されたかチェック
			$("#book_Name").on("input", function() {
               CHK_SEARCH_BUTTON();
            });
            
            // 書籍番号入力欄の値が変更されたかチェック
            $("#book_Number").on("input", function() {
                NUM_DIG_CHK(CHK_SEARCH_BUTTON);
                NUM_FORM_CHK(CHK_SEARCH_BUTTON);
            });
            
         }
        
        // windowオブジェクトを変数に格納
        function DISPLAY() {
			
			var book_Number = $("#book_Number").val();
    		var book_Name = $("#book_Name").val();
    		var genre_Name = $("#genre_Name").val();
			$.ajax({
				url:"http://localhost:8080/SRCH_LIST",
				type: "POST",
				contentType: "application/json; charset=utf-8", 
				data:JSON.stringify({
				scrn_Var:{book_Number:book_Number,book_Name:book_Name,genre_Name:genre_Name},
				}) 
			}).done(function(list){
			const books_Text = list
                .map((book) => {
                    return `${book.book_Name},${book.book_Number},${book.book_Number_Branch},${book.genre_Name},${book.genre_Number}`;
                })
                .join(", ");
				$("#list").val(books_Text);
			})
            var obj_window = window.open("CHILD_SCRN", "name", 'width=550,height=1000');
        }
        
        
        
        // データを受け取る関数
        function RECE_DATA(data) {
            // 空の行にデータを挿入する
            const new_Book = {
                name: data.book_Name,
                number: data.book_Number,
                number_Branch: data.book_Number_Branch,
                genre_Name: data.genre_Name,
                genre_Number: data.genre_Number,
            };
            ADD_BOOK(new_Book,books); // 新しい書籍情報を追加する
        }
        
        
        
        
       
        

        
