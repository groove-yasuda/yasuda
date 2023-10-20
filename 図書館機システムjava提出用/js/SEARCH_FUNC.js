	
	
	//検索結果の取得
	function SRCH_LIST(){
		var list = window.opener.$("#list").val();
		
		if(list){
			let i = 0;
			var group_Size = 5; // グループの要素数
			var group_List = [];	
			var data = list.split(",");//listをカンマで区切る
			
			for (i = 0; i < data.length; i += group_Size) {
				var group = data.slice(i, i + group_Size); //カンマで区切った要素を取り出す
				group_List.push(group);
			}
			
			for(i = 0; i < group_List.length; i++){
				var group = group_List[i];
				for (var j = 0; j < group.length; j += 5) {
					//検索結果をテーブルに表示
					let trTag = $("<tr />");
					trTag.append($("<td></td>").text(decodeURI(group[0])));
					trTag.append($("<td></td>").text(decodeURI(group[1])));
					trTag.append($("<td></td>").text(decodeURI(group[2])));
					trTag.append($("<td></td>").text(decodeURI(group[3])));
					trTag.append($("<td></td>").text(decodeURI(group[4])));
					var button = $("<button>この本を借りる</button>"); 
					
					button.click(function () {
						var index = $(this).closest('tr').index();
						var clicked_Data = group_List[index - 1];
						SEND_DATA_PA(clicked_Data);
					});
					
					//選択されたデータを親画面に送信	
					function SEND_DATA_PA(data) {
						//書籍情報の形を揃える
						const bookObject = {
							book_Name: data[0],
							book_Number: data[1],
							book_Number_Branch: data[2],
							genre_Name: data[3],
							genre_Number: data[4]
						};
						
						//ボタンの行のデータを親画面に送信
						window.opener.RECE_DATA(bookObject);
					 }
					 
					 trTag.append($("<td></td>").append(button));
					 $('#book_Data_Tbl').append(trTag);
				}
			}
		}
		else
		{
			var elem = "検索結果が見つかりませんでした。違う文字で検索を行ってください";
			SCR_ERR_MESS(elem)
		}
	}
	
	
	//エラーメッセージの設定と出力
	function SCR_ERR_MESS(message) {
		// エラーメッセージを設定
		var errorDiv = $('<div class="error_Message">' + message + '</div>');
		// 既にあるエラーメッセージを削除
		$('.error_Message').remove();
		// メッセージを画面に追加
		$('#error_Container').empty().append(errorDiv);
		
		var table = document.getElementById("book_Data_Tbl");
		// 検索書籍一覧を非表示にする
		table.style.display = "none";
	}
	
	
	
	
	
	
	
	
	
	
	
	