package com.example.demo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedCaseInsensitiveMap;

@Controller
public class COM_CLASS {
	private JdbcTemplate jdbcTemplate;


	//共通桁数チェック
	//引数:word … チェック対象の文字
	//引数:word_Count … 桁数チェックを行うための基準となる桁数、範囲
	//引数:word_Form … 桁数チェックを行うための基準となる文字の形式
	//戻り値:true or false
	public boolean dig_Chk(String word,String word_Count,String word_Form)
	{
		//利用者氏名桁数チェック
		Pattern un_DigitCheck = Pattern.compile("["+ word_Form +"]{"+ word_Count +"}");
		Matcher un_DigitCheckm = un_DigitCheck.matcher(word);

		return un_DigitCheckm .matches();
	}
	
	
	//共通存在チェック
	//引数:column … 存在チェックを行うためのカラム名
	//引数:val … チェック対象の値
	//戻り値:true or false
	public boolean exist_Chk(String column,String val)throws SQLException, ClassNotFoundException
	{
		//変数宣言
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		boolean judge = true;

		//図書カード番号存在チェック
		List<?> check = jdbcTemplate.queryForList("SELECT COUNT(*) FROM user_mst "
				+ "WHERE "+ column +" = ?",val);
		LinkedCaseInsensitiveMap<?> list = (LinkedCaseInsensitiveMap<?>) check.get(0);
		int result = Integer.parseInt(list.get("COUNT(*)").toString());
		if(result == 0)
		{
			judge = false;
		}

		return judge;
	}
	
	
	//各要素を引き出すために書籍情報を整頓
	//引数:list … 書籍情報の入っている引き数
	//戻り値:book_Data_List … 整頓した書籍情報
	public List<String> return_Model(List<String> list) {

		List<String> book_Data_List = new ArrayList<>();

		if (list != null) {
			// 5つの要素ごとにグループ化してuserDataListに格納
			for (int i = 0; i < list.size(); i += 5) {
				// リストから5つの要素を切り出してカンマ区切りの文字列を作成
				String book_Data_String = String.join(", ", list.subList(i, i + 5));

				// 作成した文字列をuserDataListに追加
				book_Data_List.add(parse_String_To_UserData(book_Data_String));
			}
		}
		return book_Data_List;
	}

	
	//作成した文字列に変数を設定する
	//引数:book_Data_String … 作成した文字列
	//戻り値:book_Data_StringFormatted … 変数を設定した文字列
		public static String parse_String_To_UserData(String book_Data_String) {
			// カンマ区切りの文字列を分割
			String[] parts = book_Data_String.split(", ");

			// UserDataの各フィールドを取得
			String name = parts[0];
			String number = parts[1];
			String number_Branch = parts[2];
			String genre_Name = parts[3];
			String genre_Number = parts[4];

			// UserDataオブジェクトの文字列表現を作成
			String userDataStringFormatted = "{" +
					"\"book_Name\":\"" + name + "\"," +
					"\"book_Number\":\"" + number + "\"," +
					"\"book_Number_Branch\":\"" + number_Branch + "\"," +
					"\"genre_Name\":\"" + genre_Name + "\"," +
					"\"genre_Number\":\"" + genre_Number + "\"" +
					"}";

			return userDataStringFormatted;
		}

}






