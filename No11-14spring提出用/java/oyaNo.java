package com.example.demo;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class oyaNo extends HttpServlet{


	public void kinou ()
	{

	}

	public void ikisaki(HttpServletRequest request,HttpServletResponse response,String No,int add){

		String basyo = "";

		if(No.equals("11"))
		{
			if(add == 0)
			{
			basyo = "No11.jsp";
			}
			else
			{
			basyo = "No11_kakunin.jsp";
			}
		}
		else if(No.equals("12"))
		{
			if(add == 0)
			{
			basyo = "No12.jsp";
			}
			else
			{
			basyo = "No12_hozon.jsp";
			}
		}
		else if(No.equals("13"))
		{
			if(add == 0)
			{
			basyo = "No13.jsp";
			}
			else
			{
			basyo = "No13_delete.jsp";
			}
		}
		else if(No.equals("14"))
		{
			if(add == 0)
			{
			basyo = "No14.jsp";
			}
			else
			{
			basyo = "No14_result.jsp";
			}
		}
		try {
		//返却先の指定[今回は("No11.jsp")]
		RequestDispatcher disp = request.getRequestDispatcher(basyo);
		//画面遷移
			disp.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> in_hantei(String workId,String workName) {
		String IDhantei = "";
		String NAMEhantei = "";

		//Interface(入力チェック)
		List <String>list = new ArrayList<String>();
		junbi jun = new junbi();

		if(workId != "")//IDが入力されている
		{
			IDhantei = jun.IDhantei(workId);
			if(IDhantei.equals("false"))
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
			NAMEhantei = jun.NAMEhantei(workName);
			if(NAMEhantei.equals("false"))//NAME入力エラーチェック
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

	public int IDSonzaiHantei(String workId)
	{
		junbi jun = new junbi();
			int check = 0;
			try {
				check = jun.IDsonzaihantei(workId);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		return check;
	}


	public List<String> sonzaihantei(String workId,String workName){
		List <String>list = new ArrayList<String>();
		junbi jun = new junbi();
		if(workId != "" && workName != "")
		{
			//社員マスタの重複チェック
			int check1;
			try {
				check1 = jun.IDsonzaihantei(workId);
				if(check1>0)
				{
					list.add("社員IDが登録されています");//ID存在エラーメッセージ
				}
				else if (check1 == 0)
				{
					list.add("社員IDに"+workId+"");
					list.add("社員名に"+workName+"　を登録します");
				}
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return list;
	}


	public  List<String> sengen(HttpServletRequest request,List<String> list){
		List<String> kekkalist = new ArrayList<String>();
		for(int i = 0; i<list.size(); i++ ) {//listで送る場合はlistの項目分繰り返す
			kekkalist.add(request.getParameter(list.get(i)));//検索する文字用変数
		}
		return kekkalist;
	}

	public void kaesi(HttpServletRequest request,Hashtable<String, Object> back ){

		Enumeration<String> e = back.keys();
		while(e.hasMoreElements()) {
			String strKey = e.nextElement().toString();
			request.setAttribute(strKey, back.get(strKey));//検索する文字用変数
			//System.out.println("strKey:"+strKey);
		}
	}



	//オーバーロード(登録機能)
	public static void DBaccess(Hashtable<String, Object> hsh)
	{
		try {
			java.sql.Connection conn = null;
			java.sql.Statement stmt = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yasuda?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9:00&rewriteBatchedStatements=true","root", "root");
			stmt = ((java.sql.Connection) conn).createStatement();

			//NAME文字数判定
			stmt.executeUpdate("INSERT INTO syainmst(syainID,syainNAME) VALUES(\"" + hsh.get("key0") + "\",\"" + hsh.get("key1") + "\")");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	//オーバーロード(更新機能)
	public void DBaccess(Hashtable <String, Object> hsh,String ID,String NAME) throws SQLException
	{
			java.sql.Connection conn = null;
			java.sql.Statement stmt = null;
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yasuda?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9:00&rewriteBatchedStatements=true","root", "root");
			stmt = ((java.sql.Connection) conn).createStatement();

			stmt.executeUpdate("UPDATE syainmst SET syainID = \""+ hsh.get("key0") + "\",syainNAME = \""+ hsh.get("key1")  + "\" WHERE syainID = \"" + ID + "\"AND syainNAME = \"" +NAME+"\"");
	}

	//オーバーロード(削除機能)
	public void DBaccess(String data[]) throws SQLException
	{		//マスタとの接続ようの宣言
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yasuda?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9:00&rewriteBatchedStatements=true","root", "root");
		stmt = ((java.sql.Connection) conn).createStatement();

		//NAME文字数判定
		stmt.executeUpdate("DELETE FROM syainmst WHERE syainID = \"" + data[0] + "\" AND syainNAME = \"" +data[1]+"\"");
	}

	//オーバーライド(検索機能)
	public List<String> DBaccess(Hashtable<String, Object> zyouken,Hashtable<String, Object> sort) throws SQLException
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
			java.sql.Connection conn = null;
			java.sql.Statement stmt = null;
			ResultSet rset = null;
			List <String>kensakulist = new ArrayList<String>();
			String syaindata[] = {"syainID","syainNAME"};
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yasuda?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9:00&rewriteBatchedStatements=true","root", "root");
			stmt = ((java.sql.Connection) conn).createStatement();

			//NAME文字数判定
			rset = stmt.executeQuery
					("SELECT * FROM syainmst where "+zyouken.get("key1")+" like \""+mae+zyouken.get("key2")+usiro+"\"ORDER BY "+zyouken.get("key3")+" "+sort.get("key1")+""+","+zyouken.get("key4")+" "+sort.get("key2")+"");
			while(rset.next())
			{
				Hashtable<String, String> kensakudata = new Hashtable<String, String>();//社員データ
				for(int i = 0;i < syaindata.length;i++)
				{
				kensakudata.put(syaindata[i],rset.getString(syaindata[i]));
				//System.out.println(syaindata[i]);
				}
				kensakulist.add(kensakudata.toString());
				//System.out.println(kensakulist);
			}
		return kensakulist;
	}

	//オーバーロード(ID存在チェック)
	public static int Sonzaicheck(String ID) throws SQLException
	{
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		ResultSet rset = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yasuda?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9:00&rewriteBatchedStatements=true","root", "root");
		stmt = ((java.sql.Connection) conn).createStatement();

		//NAME文字数判定
		rset = stmt.executeQuery("SELECT COUNT(*) FROM syainmst where syainID = \"" + ID + "\"");
		rset.next();
		int check1 = rset.getInt("count(*)");
		return check1;
	}

	//オーバーロード(IDとNAMEでの組み合わせ存在チェック)
	public static int Sonzaicheck(String ID,String NAME) throws SQLException
	{
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		ResultSet rset = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yasuda?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9:00&rewriteBatchedStatements=true","root", "root");
		stmt = ((java.sql.Connection) conn).createStatement();

		//NAME文字数判定
		rset = stmt.executeQuery("SELECT COUNT(*) FROM syainmst where syainID = \"" + ID + "\" AND syainNAME = \""+NAME+"\"");
		rset.next();
		int check1 = rset.getInt("count(*)");
		return check1;
	}


	//オーバーライド(全角半角チェック)
	public String Sonzaicheck(String workName[])
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

}



//http://localhost:8080/11-14_yasuda/No11.jsp
