package com.example.demo;



import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedCaseInsensitiveMap;

public class judge_Master {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	//社員ID入力形式判定
	//引数:syainNAME … 入力された社員名
	//戻り値:true or false
	public boolean idJudge(String syainID)
	{
		//変数宣言
		boolean a = false;
		//ID規定判定
		Pattern id_Judge = Pattern.compile("[A-Z]{1}[0-9]{3}");
		Matcher id_Judgem = id_Judge.matcher(syainID);

		//X000判定準備
		Pattern plural = Pattern.compile("^(?!.*000).+$");
		Matcher pluralm = plural.matcher(syainID);

		if(id_Judgem.matches() == true && pluralm.matches() == true)
		{
			a = true;
		}

		return a;
	}

	//社員名入力形式判定
	//引数:syainNAME … 入力された社員名
	//戻り値:true or false
	public boolean nameJudge(String syainNAME)
	{
		//NAME文字数判定
		Pattern nameMojisu = Pattern.compile("^[^a-zA-Z0-9]*${1,256}");
		Matcher nameMojisum = nameMojisu.matcher(syainNAME);
		return nameMojisum.matches();
	}

	//社員ID存在判定
	//引数:syainID … 入力された社員ID
	//戻り値:社員IDが社員マスタに登録されている数
	public int idExistJudge(String syainID) throws SQLException, ClassNotFoundException
	{
		//変数宣言
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		int check2 = 1;

		//登録判定の呼び出し
		List<?> check = jdbcTemplate.queryForList("SELECT COUNT(*) FROM syainmst where syainID = \""+ syainID +"\"");
		LinkedCaseInsensitiveMap<?> syainList = (LinkedCaseInsensitiveMap<?>) check.get(0);
		
		//判定結果の数値を返却
		check2 = Integer.parseInt(syainList.get("COUNT(*)").toString());
		return check2;
	}
	
	//社員名登録判定
	///引数:syainNAME … 入力された社員名
	//戻り値:社員名が社員マスタに登録されている数
	public int nameExistJudge(String syainNAME) throws SQLException, ClassNotFoundException
	{
		//変数宣言
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		int check2 = 1;
		
		//登録判定の呼び出し
		List<?> check = jdbcTemplate.queryForList("SELECT COUNT(*) FROM syainmst where syainNAME = \"" + syainNAME + "\"");
		LinkedCaseInsensitiveMap<?> syainList = (LinkedCaseInsensitiveMap<?>) check.get(0);
		
		//判定結果の数値を返却
		check2 = Integer.parseInt(syainList.get("COUNT(*)").toString());
		return check2;
	}

}