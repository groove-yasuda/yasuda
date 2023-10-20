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

public class parent_NoClass extends HttpServlet{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public List<String> inJudge(String workId,String workName) {
		String idHantei = "";
		String nameHantei = "";

		//Interface(入力チェック)
		List <String>list = new ArrayList<String>();
		judge_Master jum = new judge_Master();

		if(workId != "")//IDが入力されている
		{
			idHantei = jum.idJudge(workId);
			if(idHantei.equals("false"))
			{
				list.add("社員IDの入力形式に誤りがあります");//ID入力エラーメッセージ
			}
		}
		else//IDが入力されてない
		{
			list.add("社員IDが未入力です");//ID入力エラーメッセージ
		}

		if(workName != "")//NAMEが入力されてる
		{
			nameHantei = jum.nameJudge(workName);
			if(nameHantei.equals("false"))//NAME入力エラーチェック
			{
				list.add("社員名の入力形式に誤りがあります");//NAME入力エラーメッセージ
			}
		}
		else
		{
			list.add("社員名が未入力です");//ID入力エラーメッセージ
		}
		return list;
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

	//オーバーロード(ID存在チェック)
	public int existCheck(String ID) throws SQLException
	{
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		int check2 ;
		List check = jdbcTemplate.queryForList("SELECT COUNT(*) FROM syainmst where syainID = \"" + ID + "\"");
			LinkedCaseInsensitiveMap syainList = (LinkedCaseInsensitiveMap) check.get(0);
			check2 = Integer.parseInt(syainList.get("COUNT(*)").toString());
		return check2;
	}

	//オーバーロード(IDとNAMEでの組み合わせ存在チェック)
	public int existCheck(String ID,String NAME) throws SQLException
	{
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		int check2 ;
		List check = jdbcTemplate.queryForList("SELECT COUNT(*) FROM syainmst where syainID = \"" + ID + "\" AND syainNAME = \""+NAME+"\"");
			LinkedCaseInsensitiveMap syainList = (LinkedCaseInsensitiveMap) check.get(0);
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

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}



//http://localhost:8080/11-14_yasuda/No11.jsp
