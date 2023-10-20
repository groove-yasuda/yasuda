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
	/**
	 * 
	 */
	private static final long serialVersionUID = 4317509414419897342L;
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
		List<?> check = jdbcTemplate.queryForList("SELECT COUNT(*) FROM syainmst where syainID = \"" + ID + "\"");
		LinkedCaseInsensitiveMap<?> syainList = (LinkedCaseInsensitiveMap<?>) check.get(0);
		check2 = Integer.parseInt(syainList.get("COUNT(*)").toString());
		return check2;
	}

	//オーバーロード(IDとNAMEでの組み合わせ存在チェック)
	public int existCheck(String ID,String NAME) throws SQLException
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

	public int message11(String syainID,String syainNAME,List<String> Messlist)
	{//No11
		int add = 0;
		if(Messlist.size() == 0)
		{
			if(syainID != "" && syainNAME != "")
			{
				//社員マスタの重複チェック
				int check1;
				try {
					check1 = existCheck(syainID);
					if(check1>0)
					{
						Messlist.add("社員IDが登録されています");//ID存在エラーメッセージ
					}
					else if (check1 == 0)
					{
						Messlist.add("社員IDに"+syainID+"");
						Messlist.add("社員名に"+syainNAME+"　を登録します");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(Messlist.contains("社員IDに"+syainID+"") == true)
			{
				add = 1;
			}
		}
		return add;
	}

	public Hashtable<String, Integer> message12_Before(String syainID,String syainNAME,List<String> Messlist)
	{//No12
		Hashtable<String, Integer> hsh = new Hashtable<String, Integer>();
		//社員マスタの重複チェック
		int check = 0;//ID重複用の変数
		int kousinHantei = 0;
		int add = 0;
		try {
			check = existCheck(syainID, syainNAME);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		List<String> list = new ArrayList<String>();

		if(check==0)//重複がない場合（更新前はIDありがtrueなので今回の場合はエラー）
		{
			list.add("1");
		}
		else
		{
			list.add("2");
		}

		if(Messlist.size() == 0)
		{
			if(list.contains("1") == true)
			{
				Messlist.add("社員IDが使用されていないか社員IDと社員名の組み合わせが違います");
			}
			else if(list.contains("2") == true)
			{
				kousinHantei = 1;
				add = 1;
			}
		}
		hsh.put("key1", add);
		hsh.put("key2", kousinHantei);
		return hsh;
	}

	public Hashtable<String, Integer> message12_After(String syainID,String syainNAME,String maeID,String maeNAME,List<String> Messlist)
	{//No12
		Hashtable<String, Integer> hsh = new Hashtable<String, Integer>();
		//社員マスタの重複チェック
		int check = 0;//ID重複用の変数
		int add=0;
		try {
			check = existCheck(syainID, syainNAME);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		List<String> list = new ArrayList<String>();

		//社員マスタの重複チェック
		try {
			check = existCheck(syainID);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if(check>=1)//重複がある場合（更新最中は重複なしがtrueなので今回の場合はエラー）
		{
			list.add("1");
		}
		else if(check == 0)
		{
			list.add("2");
		}
		int kousinHantei = 1;
		if(Messlist.size() == 0)
		{
			if(list.contains("1") == true)//重複がある場合（更新最中は重複なしがtrueなので今回の場合はエラー）
			{
				Messlist.add("社員IDが使用されています");
			}
			else if(list.contains("2") == true)
			{
				Messlist.add("登録されている社員ID："+maeID+"　　　社員名："+maeNAME+"　を");
				Messlist.add("更新する情報の社員ID："+syainID+"　　　社員名："+syainNAME+"　へ更新します");
				add = 1;
			}
		}
		hsh.put("key1", add);
		hsh.put("key2", kousinHantei);
		return hsh;
	}

	public Hashtable<String, Integer> message13(String syainID,String syainNAME,List<String> Messlist)
	{//No13
		Hashtable<String, Integer> hsh = new Hashtable<String, Integer>();
		//変数宣言
		int hantei_id = 1;;//IDのエラー判定用
		int hantei_name = 1;//NAMEのエラー判定用
		int huriwake = 0;
		int add = 0;
		String work = "";
		String kensakuTaisyou = "";//検索の際に使用する検索対象
		String kensakukaramu = "";//検索の際に使用する検索カラム
		List<String> deletelist = new ArrayList<String>();
		deletelist.add("OK");
		deletelist.add("katahou");
		try
		{
			if(syainID != "" || syainNAME != "")
			{
				if(Messlist.contains("社員IDが未入力です") == true)
				{
					hantei_id = 0;
				}
				if(Messlist.contains("社員名が未入力です") == true )
				{
					hantei_name = 0;
				}
				if(Messlist.contains("社員名が複数件存在します") == true )
				{
					hantei_name = 0;
				}
					if( Messlist.contains("社員名が複数件存在します") == true || Messlist.contains("katahou") == true || Messlist.size() == 0)
					{
						if(Messlist.contains("katahou") != true )
						{
							int check = existCheck(syainID, syainNAME);
							if (check==0)//結びつきがないとき
							{
								Messlist.add("社員IDと社員名の組み合わせが違います");//組み合わせエラーメッセージ
							}
							else
							{
								Messlist.add("OK");
							}
							if(Messlist.contains("OK") == true && Messlist.contains("社員名が複数件存在します") == true)
							{
								Messlist.remove("社員名が複数件存在します");//3
							}
							System.out.println("check="+check);
						}
						else
						{
							//"0"=入力されていない、"1"=入力されている
							if(hantei_id == 1 && hantei_name == 0)
							{
								kensakukaramu = "syainNAME";
								kensakuTaisyou = "syainID";
								work = syainID;
							}
							else if(hantei_id == 0 && hantei_name == 1)
							{
								kensakukaramu = "syainID";
								kensakuTaisyou = "syainNAME";
								work = syainNAME;
							}
							//未入力の項目を検索
							//"0"=入力されていない、"1"=入力されている
							if(hantei_id == 1 && hantei_name == 0)
							{
								syainNAME = KatahouKensaku(kensakukaramu, kensakuTaisyou, work);
								Messlist.remove("社員名が未入力です");
								huriwake = 1;
							}
							else if(hantei_id == 0 && hantei_name == 1)
							{
								syainID = KatahouKensaku(kensakukaramu, kensakuTaisyou, work);
								Messlist.remove("社員IDが未入力です");
								huriwake = 2;
							}
						}
					}
			}
			else
			{
				Messlist.add("社員IDと社員名が未入力です");
			}
			
			Messlist.removeAll(deletelist);
			if(Messlist.size() == 0)
			{
				Messlist.add("削除する社員ID："+syainID+"");
				Messlist.add("削除する社員名："+syainNAME+"");

				add = 1;
			}
			System.out.println(Messlist.size());
			hsh.put("key1",add);
			hsh.put("key2", huriwake);
		}
		catch(Exception e)//何かしらのエラーの時
		{
			e. printStackTrace();
			System.out.println("error");
		}
		return hsh;
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
