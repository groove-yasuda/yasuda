package com.example.demo;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class junbi {

	public String IDhantei(String workId)
	{
		String a = "";
		//ID規定判定
		Pattern idHantei = Pattern.compile("[A-Z]{1}[0-9]{3}");
		Matcher idHanteim = idHantei.matcher(workId);

		//X000判定準備
		Pattern hukusuu = Pattern.compile("^(?!.*000).+$");
		Matcher hukusuum = hukusuu.matcher(workId);

		if(idHanteim.matches() == true && hukusuum.matches() == true)
		{
			a = "true";
		}
		else
		{
			a = "false";
		}
		return a;
	}

	public String NAMEhantei(String workName)
	{
		String b = "";
		//NAME文字数判定
		Pattern nameMojisu = Pattern.compile("^[^a-zA-Z0-9]*${1,256}");
		Matcher nameMojisum = nameMojisu.matcher(workName);
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

	public int ID_NAMEsonzaihantei(String workId,String workName) throws SQLException, ClassNotFoundException
	{
		//マスタとの接続ようの宣言
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		ResultSet rset = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yasuda?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9:00&rewriteBatchedStatements=true","root", "root");
		stmt = ((java.sql.Connection) conn).createStatement();

		rset = stmt.executeQuery("SELECT COUNT(*) FROM syainmst where syainID = \"" + workId + "\" AND syainNAME = \""+workName+"\"");
		rset.next();
		int check1 = rset.getInt("count(*)");
		return check1;
	}

	public int IDsonzaihantei(String workId) throws SQLException, ClassNotFoundException
	{
		//マスタとの接続ようの宣言
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		ResultSet rset = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yasuda?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9:00&rewriteBatchedStatements=true","root", "root");
		stmt = ((java.sql.Connection) conn).createStatement();

		//NAME文字数判定
		rset = stmt.executeQuery("SELECT COUNT(*) FROM syainmst where syainID = \"" + workId + "\"");
		rset.next();
		int check1 = rset.getInt("count(*)");
		return check1;
	}
	public int NAMEsonzaihantei(String workName) throws SQLException, ClassNotFoundException
	{
		//マスタとの接続ようの宣言
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		ResultSet rset = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yasuda?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9:00&rewriteBatchedStatements=true","root", "root");
		stmt = ((java.sql.Connection) conn).createStatement();

		//NAME文字数判定
		rset = stmt.executeQuery("SELECT COUNT(*) FROM syainmst where syainNAME = \"" + workName + "\"");
		rset.next();
		int check1 = rset.getInt("count(*)");
		return check1;
	}

	public boolean IDsonzaihantei_b(String workId) throws SQLException, ClassNotFoundException
	{
		//マスタとの接続ようの宣言
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		ResultSet rset = null;
		boolean b = true;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yasuda?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9:00&rewriteBatchedStatements=true","root", "root");
		stmt = ((java.sql.Connection) conn).createStatement();

		//NAME文字数判定
		rset = stmt.executeQuery("SELECT * FROM syainmst where syainID = \"" + workId + "\"");
		b = rset.next();
		return b;
	}

	public String katahoukensaku(String kensakukaramu,String  kensakuTaisyou,String work) throws SQLException, ClassNotFoundException
	{
		//マスタとの接続ようの宣言
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		ResultSet rset = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yasuda?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9:00&rewriteBatchedStatements=true","root", "root");
		stmt = ((java.sql.Connection) conn).createStatement();

		//NAME文字数判定
		rset = stmt.executeQuery("SELECT "+kensakukaramu+" FROM syainmst where "+kensakuTaisyou+" = \"" + work + "\"");
		rset.next();
		String kensaku = rset.getString(kensakukaramu);
		return kensaku;
	}

	public int touroku(String workId,String  workName) throws SQLException, ClassNotFoundException
	{
		//マスタとの接続ようの宣言
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yasuda?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9:00&rewriteBatchedStatements=true","root", "root");
		stmt = ((java.sql.Connection) conn).createStatement();

		//NAME文字数判定
		stmt.executeUpdate("INSERT INTO syainmst(syainID,syainNAME) VALUES(\"" + workId + "\",\"" + workName + "\")");
		int check1 = 1;
		return check1;
	}

	public int kousin(String workId,String  workName,String kousinmaeId) throws SQLException, ClassNotFoundException
	{
		//マスタとの接続ようの宣言
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yasuda?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9:00&rewriteBatchedStatements=true","root", "root");
		stmt = ((java.sql.Connection) conn).createStatement();

		//NAME文字数判定
		stmt.executeUpdate("UPDATE syainmst SET syainID = \""+ workId + "\",syainNAME = \""+ workName + "\" WHERE syainID = \"" + kousinmaeId + "\"");
		int check1 = 1;
		return check1;
	}

	public int sakujo(String workId) throws SQLException, ClassNotFoundException
	{
		//マスタとの接続ようの宣言
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yasuda?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9:00&rewriteBatchedStatements=true","root", "root");
		stmt = ((java.sql.Connection) conn).createStatement();

		//NAME文字数判定
		stmt.executeUpdate("DELETE FROM syainmst WHERE syainID = \"" +  workId + "\"");
		int check1 = 1;
		return check1;
	}

	public List<String> kensakulist(String kensaku,String mae,String kensakumoji,String usiro,String juni, String juni_Jun0,String juni_Alt,String juni_Jun1) throws SQLException, ClassNotFoundException
	{
		//マスタとの接続ようの宣言
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		ResultSet rset = null;
		List <String>kensakulist = new ArrayList<String>();
		String syaindata[] = {"syainID","syainNAME"};
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yasuda?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9:00&rewriteBatchedStatements=true","root", "root");
		stmt = ((java.sql.Connection) conn).createStatement();

		//NAME文字数判定
		rset = stmt.executeQuery
				("SELECT * FROM syainmst where "+kensaku+" like \""+mae+kensakumoji+usiro+"\"ORDER BY "+juni+" "+juni_Jun0+""+","+juni_Alt+" "+juni_Jun1+"");
		while(rset.next())
		{
			Hashtable<String, String> kensakudata = new Hashtable<String, String>();//社員データ
			for(int i = 0;i < syaindata.length;i++)
			{
			kensakudata.put(syaindata[i],rset.getString(syaindata[i]));
			}
			kensakulist.add(kensakudata.toString());
		}
		return kensakulist;

	}



	Hashtable<String, String> hsh = new Hashtable<String,String>();
	Hashtable<String, String> hshi = new Hashtable<String,String>();
	Hashtable<String, List<String>> hshj = new Hashtable<String, List<String>>();



}