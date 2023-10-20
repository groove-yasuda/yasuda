package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedCaseInsensitiveMap;

public class parent_NoClass2 extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4317509414419897342L;
	@Autowired
	private JdbcTemplate jdbcTemplate;


	public int inJudgeId(String syainID,List<String> messList) {
		String idHantei = "";
		judge_Master jum = new judge_Master();
		int check = 0;

		if(syainID != "")//IDが入力されている
		{
			idHantei = jum.idJudge(syainID);
			if(idHantei.equals("false"))
			{
				messList.add("社員IDの入力形式に誤りがあります");//ID入力エラーメッセージ
				check = 1;
			}
		}
		else//IDが入力されてない
		{
			messList.add("社員IDが未入力です");//ID入力エラーメッセージ
			check = 1;
		}
		if(messList.size()==0)
		{
			int check1 = idExistJudge(syainID);
			if(check1>0)
			{
				messList.add("社員IDが登録されています");
				check = 1;
			}
		}
		return check;
	}

	public int inJudgeName(String syainNAME,List<String> messList ) {
		String nameHantei = "";
		judge_Master jum = new judge_Master();
		int check = 0;

		if(syainNAME != "")//NAMEが入力されてる
		{
			nameHantei = jum.nameJudge(syainNAME);
			if(nameHantei.equals("false"))//NAME入力エラーチェック
			{
				messList.add("社員名の入力形式に誤りがあります");//NAME入力エラーメッセージ
				check = 1;
			}
		}
		else
		{
			messList.add("社員名が未入力です");//ID入力エラーメッセージ
			check = 1;
		}
		return check;
	}

	public int idExistJudge(String workId)
	{
		judge_Master jum = new judge_Master();
		int check = 0;
		try {
			check = jum.idExistJudge(workId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	public int nameExistJudge(String workName)
	{
		judge_Master jum = new judge_Master();
		int check = 0;
		try {
			check = jum.nameExistJudge(workName);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	//オーバーロード(登録機能)
	public void dbAccess(Hashtable<String, Object> hsh)
	{
		jdbcTemplate.execute("INSERT INTO syainmst(syainID,syainNAME) VALUES(\"" + hsh.get("key0") + "\",\"" + hsh.get("key1") + "\")");
	}

	//オーバーロード(更新機能)
	public void dbAccess(Hashtable <String, Object> hsh,String ID,String NAME) throws SQLException
	{
		jdbcTemplate.execute("UPDATE syainmst SET syainID = \""+ hsh.get("key0") + "\",syainNAME = \""+ hsh.get("key1")  + "\" WHERE syainID = \"" + ID + "\"AND syainNAME = \"" +NAME+"\"");
	}

	//オーバーロード(削除機能)
	public void dbAccess(String data[]) throws SQLException
	{		
		jdbcTemplate.execute("DELETE FROM syainmst WHERE syainID = \"" + data[0] + "\" AND syainNAME = \"" +data[1]+"\"");
	}

	//オーバーライド(検索機能)
	public List<String> dbAccess(Hashtable<String, Object> zyouken,Hashtable<String, Object> sort) throws SQLException
	{
		String mae="";
		String usiro ="";
		//社員マスタ検索
		if(zyouken.get("key0").equals("front"))
		{
			mae = "";
			usiro = "%";
		}
		else if(zyouken.get("key0").equals("back"))
		{
			mae = "%";
			usiro = "";
		}
		else if(zyouken.get("key0").equals("all"))
		{
			mae = "";
			usiro = "";
		}
		else if(zyouken.get("key0").equals("part"))
		{
			mae = "%";
			usiro = "%";
		}
		List <String>kensakulist = new ArrayList<String>();

		//NAME文字数判定
		String SQL = "SELECT * FROM syainmst where "+zyouken.get("key1")+" like \""+mae+zyouken.get("key2")+usiro+"\"ORDER BY "+zyouken.get("key3")+" "+sort.get("key1")+""+","+zyouken.get("key4")+" "+sort.get("key2")+"";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(SQL);
		for(int l = 0;l < list.size();l++)
		{
			kensakulist.add(list.get(l).toString().replaceAll("^\\{|\\}$", ""));
		}
		return kensakulist;
	}

	//オーバーロード(IDとNAMEでの組み合わせ存在チェック)
	public int existCheck(String ID,String NAME,List<String> messList) throws SQLException
	{
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		int check2 ;
		List<?> check = jdbcTemplate.queryForList("SELECT COUNT(*) FROM syainmst where syainID = \"" + ID + "\" AND syainNAME = \""+NAME+"\"");
		LinkedCaseInsensitiveMap<?> syainList = (LinkedCaseInsensitiveMap<?>) check.get(0);
		check2 = Integer.parseInt(syainList.get("COUNT(*)").toString());

		return check2;
	}


	//オーバーライド(全角半角チェック)
	public String existCheck(String workName[])
	{
		String b = "";
		//NAME文字数判定
		Pattern nameMojisu = Pattern.compile("^[^a-zA-Z0-9]*${1,256}");
		Matcher nameMojisum = nameMojisu.matcher(workName[0]);
		if(nameMojisum.matches() == true)
		{
			b = "true";
		}
		else
		{
			b = "false";
		}
		return b;
	}

	public Hashtable<String, String> messCheck(String kensakukaramu,String  kensakuTaisyou,String work) throws SQLException, ClassNotFoundException
	{
		int a = 0;
		Hashtable<String, String> hsh = new Hashtable<String, String>();
		if(a==0)
		{

		}
		else if(a == 1)
		{

		}
		else if(a == 2)
		{

		}
		return hsh;
	}
	public String KatahouKensaku(String kensakukaramu,String  kensakuTaisyou,String work) throws SQLException, ClassNotFoundException
	{
		String SQL = "SELECT "+kensakukaramu+" FROM syainmst where "+kensakuTaisyou+" = \"" + work + "\"";
		String kensaku = jdbcTemplate.queryForObject(SQL,String.class);
		return kensaku;
	}
}



//http://localhost:8080/11-14_yasuda/No11.jsp
