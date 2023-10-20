package com.example.demo;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class RET_FUNC_Model {
	private JdbcTemplate jdbcTemplate;
	
	
	//利用者の借りた書籍情報の検索
	//引数:card_Number … 画面で設定された図書カード番号
	//戻り値:list … 書籍検索結果
	public List<Map<String, Object>> return_List_Model(int card_Number) {
		
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM lending_status_table l INNER JOIN book_mst b "
				+ "ON l.book_Number = b.book_Number INNER JOIN genre_mst g ON b.genre_Number = g.genre_Number WHERE card_Number = ? ORDER BY return_Date ASC",card_Number);
		
		return list;
	}
	
	
	//list<Map<String,Object>>をJsonに変換
	//引数:list … 取得した利用者の貸出済み書籍の情報
	//戻り値:json … Json形式に変換した書籍の情報
	public String return_Json_Model_Object(List<Map<String, Object>> list) {
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		String json = "";
		// JSON形式の文字列に変換
		ObjectMapper objectMapper = new ObjectMapper();
		try 
		{
			json = objectMapper.writeValueAsString(list);
		} 
		catch (JsonProcessingException E)
		{
			E.printStackTrace();
		}
		return json;
	}
	
	
	//list<String>をJsonに変換
	//引数:list … 返却画面から確認画面に送信した返却予定の書籍情報
	//戻り値:json … Json形式に変換した書籍の情報
	public String return_Json_Model_String(List<String> list) {
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		String json = "";
		// JSON形式の文字列に変換
		ObjectMapper objectMapper = new ObjectMapper();
		try
		{
			json = objectMapper.writeValueAsString(list);
		} 
		catch (JsonProcessingException E) 
		{
			E.printStackTrace();
		}
		return json;
	}
	
	
	//貸し出し済み書籍の冊数チェック
	//引数:number … 画面で指定された値
	//戻り値:availBooksCount … 貸し出し済の冊数
	public int  chk_Avail_Lend_Model(LinkedHashMap number) 
	{
		//変数宣言
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		String card_Num = (String)number.get("card_Number");
		int card_Number = Integer.parseInt(card_Num);
		
		//貸出せる残りの冊数チェック
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM lending_status_table WHERE card_Number = ?",card_Number);
		int avail_Books = list.size();
		
		return avail_Books;
	}
	
	
	//貸出状況テーブルに返却予定の書籍があるかのチェック
	//引数:list … 返却予定の書籍リスト
	//引数:card_Number … //画面で指定された図書カード番号
	//戻り値:judge … チェックの結果
	public boolean ret_Avail_Check(List<String> list,int card_Number) 
	{
		//変数宣言
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		List<String> length =new ArrayList<String>();//5つの要素ごとにグループ化したlistの長さを計測する為
		int judge = 0;
		boolean decision = false;
		
		for (int i = 0; i < list.size(); i += 5) {
			//listを5つの要素ごとにグループ化してlist_Listに格納
			String list_List = String.join(", ", list.subList(i, i + 5));
			// list_Listから5つの要素を切り出してそれぞれ配列に格納
			String[] parts =list_List.split(", ");
			length.add(list_List);
			
			//配列に格納した要素をチェックに必要な変数のみ取り出し
			String number = parts[2];
			String number_Branch = parts[3];
			
			//貸出状況の確認
			List<Map<String, Object>> search = jdbcTemplate.queryForList("SELECT * FROM lending_status_table WHERE card_Number = ? AND book_Number = ? AND book_Number_Branch = ?"
				,card_Number,number,number_Branch );
			
			int result = search.size();
			judge = judge + result;
		}
		
		//返却予定の書籍情報がすべて貸出状況テーブルに存在するとき:True
		//返却予定の書籍情報が一冊でも貸出状況テーブルに存在しない時:False
		if(judge == length.size())
		{
			decision = true;
		}
		
		return decision;
		
	}
	
	
	//返却処理の実行
	//引数:list … 返却予定の書籍リスト
	//引数:card_Number … 画面で指定された図書カード番号
	public void  ret_Fin_Model(List<String> list,int card_Number) 
	{
		//変数宣言
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		//返却処理を行うためにデータを適切な形にする
		ret_Model(list,card_Number);
	}
	
	
	//返却出処理のためのデータの整頓
	//引数:list … 返却予定の書籍リスト
	//引数:card_Number … 画面で指定された図書カード番号
	public void ret_Model(List<String> list,int card_Number) 
	{
		if (list != null) {
			// 5つの要素ごとにリストに格納
			for (int i = 0; i < list.size(); i += 5) {
				// リストから5つの要素を取り出しカンマ区切りの文字列を作成
				String book_Data_String = String.join(", ", list.subList(i, i + 5));
				// 作成した文字列を貸出処理
				ret_Parse_String_To_UserData(book_Data_String,card_Number);
			}
		}
	}
	
	
	//SQLから削除(返却処理)
	//引数:book_Data_String … 貸し出し予定の書籍リストをカンマ区切りの文字列に変換したデータ
	//引数:format_Date … 貸出日の日付情報
	//引数:card_Number … 画面で指定された図書カード番号
	public void ret_Parse_String_To_UserData(String book_Data_String,int card_Number) {
		// カンマ区切りの文字列を分割
		String[] parts = book_Data_String.split(", ");
		
		// 返却処理に必要な要素の取り出し
		String number = parts[2];
		String number_Branch = parts[3];
		
		// 返却処理
		jdbcTemplate.update("DELETE FROM lending_status_table WHERE card_Number = ? AND book_Number = ? AND book_Number_Branch = ?",
			    card_Number, number, number_Branch);
	}

}