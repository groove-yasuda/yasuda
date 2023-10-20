package com.example.demo;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class LEND_FUNC_Model {
	private JdbcTemplate jdbcTemplate;



	//貸出書籍の検索
	//引数:book_Number … 画面に入力された書籍番号
	//引数:book_Name … 画面に入力された書籍名
	//引数:genre_Name … 画面で選択されたジャンル名
	//戻り値:list … 書籍検索結果
	public List<Map<String, Object>>  srch_List_Model(String book_Number,String book_Name,String genre_Name) 
	{
		//変数宣言
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		
		//画面に入力された値によって検索条件の変更
		if(book_Number != "" && book_Name != "")
		{
			
			list = jdbcTemplate.queryForList("SELECT * FROM book_mst b INNER JOIN genre_mst g ON b.genre_Number = g.genre_Number"
					+ " WHERE book_Number LIKE ? AND book_name LIKE ? AND b.genre_Number = ?","%"+book_Number+"%","%"+book_Name+"%",genre_Name);
		}
		else if(book_Number == "" && book_Name != "")
		{
			list = jdbcTemplate.queryForList("SELECT * FROM book_mst b INNER JOIN genre_mst g ON b.genre_Number = g.genre_Number"
					+ " WHERE book_name LIKE ? AND b.genre_Number = ?","%"+book_Name+"%",genre_Name);
		}
		else if(book_Number != "" && book_Name == "")
		{
			list = jdbcTemplate.queryForList("SELECT * FROM book_mst b INNER JOIN genre_mst g ON b.genre_Number = g.genre_Number"
					+ " WHERE book_Number LIKE ? AND b.genre_Number = ?","%"+book_Number+"%",genre_Name);
		}
		
		
		return list;
	}
	
	
	//ジャンル名、ジャンルコードの取得
	//戻り値:list … 取得したジャンル名、ジャンルコード
	public List<Map<String, Object>>  srch_List_Genre_Model() 
	{
		//変数宣言
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		//ジャンル名、ジャンルコードの検索
		list = jdbcTemplate.queryForList("SELECT * FROM genre_mst");

		return list;
	}
	
	
	//貸し出せる残りの冊数チェック処理
	//引数:number … 画面で指定された値
	//戻り値:availBooksCount … 貸出済の冊数
	public int  chk_Avail_Model(LinkedHashMap number) 
	{
		//変数宣言
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		String card_Num = (String)number.get("card_Number");
		int card_Number = Integer.parseInt(card_Num);
		
		//貸出せる残りの冊数チェック
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM lending_status_table WHERE card_Number = " + card_Number + "");

		int availBooksCount = list.size();

		return availBooksCount;
	}
	
	
	//図書カード番号桁数チェックの処理
	//引数:number … //画面で指定された値
	//戻り値:true or false
	public boolean num_Dig_Chk_Model(LinkedHashMap number)
	{
		//変数宣言
		COM_CLASS com = new COM_CLASS();
		String card_Number = (String) number.get("book_Number");
		String word_Count = "0,13";
		String word_Form = "0-9";
		
		//桁数チェックの呼び出し
		boolean result = com.dig_Chk(card_Number,word_Count,word_Form);
		
		return result;
	}
	
	
	//図書カード番号桁数チェックの処理
	//引数:number … //画面で指定された値
	//戻り値:true or false
	public boolean num_Form_Chk_Model(LinkedHashMap number)
	{
		//変数宣言
		COM_CLASS com = new COM_CLASS();
		String card_Number = (String) number.get("book_Number");
		String word_Count = "0,";
		String word_Form = "0-9";
		
		//桁数チェックの呼び出し
		boolean result = com.dig_Chk(card_Number,word_Count,word_Form);
		
		return result;
	}
	
	
	//書籍貸出状況チェックの処理
	//引数:list … 貸出予定書籍のリスト
	//引数:card_Number … //画面で指定された図書カード番号
	//戻り値:judge … チェックの結果
	public int  lending_Avail_Check(List<String> list,int card_Number) 
	{
		//変数宣言
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		int judge = 0;
		
		
		for (int i = 0; i < list.size(); i += 5) {
			//listを5つの要素ごとにグループ化してlist_Listに格納
			String list_List = String.join(", ", list.subList(i, i + 5));
			// list_Listから5つの要素を切り出してそれぞれ配列に格納
			String[] parts =list_List.split(", ");
			
			//配列に格納した要素をチェックに必要な変数のみ取り出し
			String number = parts[1];
			String number_Branch = parts[2];
		
			//貸出状況の確認
			List<Map<String, Object>> search = jdbcTemplate.queryForList("SELECT * FROM lending_status_table WHERE card_Number = ? AND book_Number = ? AND book_Number_Branch = ?"
				,card_Number,number,number_Branch );
			
			int result = search.size();
			judge = judge + result;
		}
		
		return judge;
		
	}
	
	
	//貸出処理の実行
	//引数:list … 貸し出し予定の書籍リスト
	//引数:format_Date … 貸出日の日付情報
	//引数:card_Number … 画面で指定された図書カード番号
	public void  lending_Fin_Model(List<String> list,String format_Date,int card_Number) 
	{
		//変数宣言
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		
		//貸出処理を行うためにデータを適切な形にする
		return_Model(list,format_Date,card_Number);
	}
	
	
	
	//貸出処理のためのデータの整頓
	//引数:list … 貸し出し予定の書籍リスト
	//引数:format_Date … 貸出日の日付情報
	//引数:card_Number … 画面で指定された図書カード番号
	public void return_Model(List<String> list,String formatDate,int card_Number) {
		
		if (list != null) {
			// 5つの要素ごとにリストに格納
			for (int i = 0; i < list.size(); i += 5) {
				// リストから5つの要素を取り出しカンマ区切りの文字列を作成
				String book_Data_String = String.join(", ", list.subList(i, i + 5));
				
				// 作成した文字列を貸出処理
				parse_String_To_UserData(book_Data_String,formatDate,card_Number);
			}
		}
	}
	
	
	//SQLへ登録(貸出処理)
	//引数:book_Data_String … 貸し出し予定の書籍リストをカンマ区切りの文字列に変換したデータ
	//引数:format_Date … 貸出日の日付情報
	//引数:card_Number … 画面で指定された図書カード番号
	public void parse_String_To_UserData(String book_Data_String,String formatDate,int card_Number) {
		// カンマ区切りの文字列を分割
		String[] parts = book_Data_String.split(", ");
		
		// 貸出処理に必要な要素の取り出し
		String number = parts[1];
		String number_Branch = parts[2];
		
		// 貸出処理
		jdbcTemplate.update("INSERT INTO lending_status_table (card_Number, book_Number, book_Number_Branch, return_date) "
				+ "VALUES(?,?,?,?)",card_Number,number,number_Branch,formatDate );
	}

}