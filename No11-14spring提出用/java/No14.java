package com.example.demo;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class No14 extends oyaNo  implements inter{

	public List<String> in_hantei(String workId,String workName) {
		String IDhantei = "";
		String NAMEhantei = "";

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

	@RequestMapping("/SELECT")
	public ModelAndView  syainInsert(@ModelAttribute InsertSyainForm form) {
		String kensaku = "";
		String con = "";
		String prime_juni = "";
		String id_juni = "";
		String name_juni = "";
		int add = 0;
		int id_point = 0;
		int name_point = 0;
		kensaku = form.getkensaku();
		con = form.getcon();
		prime_juni = form.getprime_juni();
		ModelAndView mv = new ModelAndView();


		String kensaku_Alt = "";//検索カラム指定の際に検索対象とは別のカラムを代入する変数
		String juni = "";//第一優先のカラム
		String juni_Alt = "";//第一優先ではないカラム
		String juni_Jun0 = "";//第一優先のソート条件(昇順 or 降順)
		String juni_Jun1 = "";//もう一つのソート条件(昇順 or 降順)
		int kensu =0;//検索の際の件数取得変数
		int kensaku_point = 0;
		String kensakumoji[] = {kensaku};

		List <String>kensakulist = new ArrayList<String>();
		String a ="";

		try {

			//検索対象の半角全角チェック準備
			a = Sonzaicheck(kensakumoji);
			System.out.println(a);

			if(kensaku != "")//文字が入力されているとき
			{
				if(a == "true")//半角の時
				{
					kensaku_Alt = "syainNAME";
				}
				else//全角の時
				{
					kensaku_Alt = "syainID";
				}

				//検索判定
				if(prime_juni.equals("syainID"))//第一優先がIDの時
				{
					juni = "syainID";
					juni_Alt = "syainNAME";
					juni_Jun0 = form.getid_juni();
					juni_Jun1 = form.getname_juni();
				}
				else//第一優先がNAMEの時
				{
					juni = "syainNAME";
					juni_Alt = "syainID";
					juni_Jun0 = form.getname_juni();
					juni_Jun1 = form.getid_juni();
				}

				Hashtable<String, Object> kensakuhensu = new Hashtable<String,Object>();
				kensakuhensu.put("key0",con);//検索条件(一致)
				kensakuhensu.put("key1",kensaku_Alt);//検索カラム(入力した文字の半角/全角でID/NAMEになる)
				kensakuhensu.put("key2",kensakumoji[0]);//入力した文字
				kensakuhensu.put("key3",juni);//第一優先のカラム
				kensakuhensu.put("key4",juni_Alt);//第二優先のカラム

				Hashtable<String, Object> sortorder = new Hashtable<String,Object>();
				sortorder.put("key1",juni_Jun0);//第一優先のソート順
				sortorder.put("key2",juni_Jun1);//第二優先のソート順

				kensakulist = DBaccess(kensakuhensu,sortorder);
				System.out.println(kensakulist);

				if( kensakulist.size() == 0 )
				{
					kensakulist.add("検索結果が見つかりませんでした");
					add = 0;
				}

			}
			else
			{
				kensakulist.add("検索対象が未入力です");
				add = 0;
			}

	 		for (int i = 0; i < kensakulist.size(); i++)
	 		{
	 			if(kensakulist.get(i) == "検索対象が未入力です" ||
	 			kensakulist.get(i) == "検索結果が見つかりませんでした" )
	 			{
	 				kensaku_point = 1;
	 			}
	 		}

			Hashtable<String, Object> back = new Hashtable<String,Object>();
			back.put("kaesimoji",kensaku);
			back.put("kaesikekka",kensakulist);
			back.put("kaesiP",kensu);
			back.put("kensaku_point",kensaku_point);
		}
		catch(Exception e)
		{
			e. printStackTrace();
			System.out.println("error");
		}
		finally
		{
		}
	
		return mv;
	}

	@Override
	public int yarukoto() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}
	
	//呼び出し
	public String KatahouKensaku(String kensakukaramu,String  kensakuTaisyou,String work) throws SQLException, ClassNotFoundException
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
}
