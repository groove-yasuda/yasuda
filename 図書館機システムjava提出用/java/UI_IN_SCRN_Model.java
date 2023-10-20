package com.example.demo;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedCaseInsensitiveMap;


public class UI_IN_SCRN_Model {

	private JdbcTemplate jdbcTemplate;


	////図書カード番号と利用者氏名の組み合わせチェック
	//引数:card_Number … 入力された図書カード番号
	//引数:user_Name … 入力された利用者氏名
	//戻り値:result … 組み合わせチェックの結果件数
	public int  insert_Model(int card_Number,String user_Name) 
	{
		//変数宣言
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();

		//組み合わせチェックの呼び出し
		List<?> check = jdbcTemplate.queryForList("SELECT COUNT(*) "
				+ "FROM user_mst "
				+ "where user_Name = ? "
				+ "AND card_Number = ? "
				,user_Name,card_Number);
		LinkedCaseInsensitiveMap<?> list = (LinkedCaseInsensitiveMap<?>) check.get(0);
		int result = Integer.parseInt(list.get("COUNT(*)").toString());

		return result;
	}

	
	//図書カード番号桁数チェック
	//引数:number … //画面で指定された値
	//戻り値:true or false
	public boolean cn_Dig_Chk_Model(LinkedHashMap number)
	{
		//変数宣言
		COM_CLASS com = new COM_CLASS();
		String card_Number = (String) number.get("card_Number");
		String word_Count = "6";
		String word_Form = "0-9";
		
		//桁数チェックの呼び出し
		boolean result = com.dig_Chk(card_Number,word_Count,word_Form);
		
		return result;
	}




	//図書カード番号存在チェック
	//引数:number … //画面で指定された値
	//戻り値:true or false
	public boolean cn_Exist_Chk_Model(LinkedHashMap number)throws SQLException, ClassNotFoundException
	{
		//変数宣言
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		COM_CLASS com = new COM_CLASS();
		String column = "card_Number";
		String val = (String) number.get("card_Number");
		
		//存在チェックの呼び出し
		boolean judge = com.exist_Chk(column,val);

		return judge;
	}


	//利用者氏名桁数チェック
	//引数:number … //画面で指定された値
	//戻り値:true or false
	public boolean un_Dig_Chk_Model(LinkedHashMap number)
	{
		//変数宣言
		COM_CLASS com = new COM_CLASS();
		String user_Name = (String) number.get("user_Name");
		String word_Count = "1,255";
		String word_Form = "^0-9";
		
		//桁数チェックの呼び出し
		boolean result = com.dig_Chk(user_Name,word_Count,word_Form);

		return result;
	}




	//利用者氏名存在チェック
	//引数:number … //画面で指定された値
	//戻り値:true or false
	public boolean un_Exist_Chk_Model(LinkedHashMap number)throws SQLException, ClassNotFoundException
	{
		//変数宣言
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		COM_CLASS com = new COM_CLASS();
		String column = "user_Name";
		String val = (String) number.get("user_Name");
		
		//存在チェックの呼び出し
		boolean judge = com.exist_Chk(column,val);

		return judge;
	}




}
