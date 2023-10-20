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
public class No13 extends oyaNo  implements inter{

	public List<String> in_hantei(String workId,String workName) {
		List <String>list = new ArrayList<String>();
		junbi jun = new junbi();
		
		try {
			String IDhantei;
			String NAMEhantei;
			if(workId != "")//IDが入力されている
			{
				IDhantei = jun.IDhantei(workId);
				if(IDhantei.equals("false"))
				{
					list.add("社員IDの入力形式に誤りがあります");
					list.add("NO");
				}
				else
				{
					int sonzaicheck_Id;

					sonzaicheck_Id = jun.IDsonzaihantei(workId);

					if(sonzaicheck_Id==0)//IDが存在しない
					{
						list.add("社員IDが存在しません");
						list.add("NO");
					}
				}
			}
			else//IDが入力されてない
			{
				list.add("社員IDが未入力です");
				list.add("katahou");
			}

			if(workName != "")//NAMEが入力されてる
			{
				NAMEhantei = jun.NAMEhantei(workName);
				if(NAMEhantei.equals("false"))//NAME入力エラーチェック
				{
					list.add("社員名の入力形式に誤りがあります");
					list.add("NO");
				}
				else
				{
					int sonzaicheck_Name =  jun.NAMEsonzaihantei(workName);
					if(sonzaicheck_Name==0)//NAMEが存在しない
					{
						list.add("社員名が存在しません");
						list.add("NO");
					}
					else if(sonzaicheck_Name >1)//NAME重複してる
					{
						list.add("社員名が複数件存在します");
					}
				}
			}
			else
			{
				list.add("社員名が未入力です");
				list.add("katahou");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping("/DELETE")
	public ModelAndView  syainInsert(@ModelAttribute InsertSyainForm form) {
		String syainID = "";
		String syainNAME = "";
		String hantei = "";
		int add = 0;
		int id_point = 0;
		int name_point = 0;
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		hantei = form.gethantei();
		ModelAndView mv = new ModelAndView();

		List<String> deletelist = new ArrayList<String>();
		deletelist.add("OK");
		deletelist.add("NO");
		deletelist.add("katahou");

		//変数宣言
		int hantei_id = 1;;//IDのエラー判定用
		int hantei_name = 1;//NAMEのエラー判定用
		int huriwake = 0;
		String error = "0";
		String kensakuTaisyou = "";//検索の際に使用する検索対象
		String kensakukaramu = "";//検索の際に使用する検索カラム
		String work = "";//片方だけ入力の検索の際に使用する検索する文字を代入する変数
		String No = "13";

		//エラーメッセージ用のテーブル
		List<String> Messlist = new ArrayList<String>();
		try {
			if(syainID != null && syainNAME != null)
			{
			if(syainID != "" || syainNAME != "")
			{
				Messlist = in_hantei(syainID,syainNAME);
				if(Messlist.contains("社員IDが未入力です") == true)
				{
					hantei_id = 0;
					error = "1";
				}
				if(Messlist.contains("社員名が未入力です") == true )
				{
					hantei_name = 0;
					error = "1";
				}
				if(Messlist.contains("社員名が複数件存在します") == true )
				{
					hantei_name = 0;
					error = "1";
				}

				if(Messlist.contains("NO") == true)
				{
					error = "1";
				}
				else
				{
					if( Messlist.contains("社員名が複数件存在します") == true || Messlist.contains("katahou") == true || Messlist.size() == 0)
					{
						if(Messlist.contains("katahou") != true )
						{
							int check = Sonzaicheck(syainID, syainNAME);
							if (check==0)//結びつきがないとき
							{
								Messlist.add("社員IDと社員名の組み合わせが違います");//組み合わせエラーメッセージ
								error = "1";
							}
							else
							{
								Messlist.add("OK");
							}
							if(Messlist.contains("OK") == true && Messlist.contains("社員名が複数件存在します") == true)
							{
								Messlist.remove("社員名が複数件存在します");//3
									error = "1";
							}
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
			}
			else
			{
				Messlist.add("社員IDと社員名が未入力です");
				error = "1";
			}
			Messlist.removeAll(deletelist);
			if(Messlist.size() == 0)
			{
				Messlist.add("削除する社員ID："+syainID+"");
				Messlist.add("削除する社員名："+syainNAME+"");

				add = 1;
			}

			for (int i = 0; i < Messlist.size(); i++) {
 			if(Messlist.get(i) == "社員IDが未入力です" ||
 				Messlist.get(i) == "社員IDの入力形式に誤りがあります" ||
				Messlist.get(i) == "社員IDが存在しません" ||
 				Messlist.get(i) == "社員IDと社員名が未入力です" |
				Messlist.get(i) == "社員IDと社員名の組み合わせが違います")
 			{
 				id_point = 1;
 			}
 			if(Messlist.get(i) == "社員名が未入力です" ||
 			Messlist.get(i) == "社員名が存在しません" ||
 			Messlist.get(i) == "社員名が複数件存在します" ||
 		 	Messlist.get(i) == "社員名の入力形式に誤りがあります"||
 		 	Messlist.get(i) == "社員IDと社員名が未入力です" ||
 		 	Messlist.get(i) == "社員IDと社員名の組み合わせが違います")
 			{
 				name_point = 1;
 			}
			}
			mv.addObject("id_point",id_point);
			mv.addObject("name_point",name_point);
			mv.addObject("key03",Messlist);
			for(int i = 0;i<Messlist.size();i++)
			{
				mv.addObject("key0"+(4+i),Messlist.get(i));
			}
			if(huriwake == 0)
			{
				mv.addObject("key01",syainID);
				mv.addObject("key02",syainNAME);
			}
			else if(huriwake == 1)
			{
				String kara = "";
				mv.addObject("key01",syainID);
				mv.addObject("key02",kara);
			}
			else if(huriwake == 2)
			{
				String kara = "";
				mv.addObject("key01",kara);
				mv.addObject("key02",syainNAME);
			}
			
			System.out.println(Messlist);
			}
		}
		catch(Exception e)//何かしらのエラーの時
		{
			e. printStackTrace();
			System.out.println("error");
		}
		finally//何が起きても行う処理
		{
			if(add == 0)
			{
			mv.setViewName("No13");
			}
			else
			{
			mv.setViewName("No13_kakunin");
			}
		}
		return mv;
	}

	@Override
	public int yarukoto() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}
	@RequestMapping("/KAKUNIN_13") 
	public ModelAndView kakuninScreen(@ModelAttribute InsertSyainForm form) {
		String syainID = "";
		String syainNAME = "";
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		List<String> list = new ArrayList<String>();
		ModelAndView mv = new ModelAndView();
		try {
			//社員マスタの重複チェック

			int check = IDSonzaiHantei(syainID);
			System.out.println("削除確認");
			if(check==0)//重複がない場合（重複がある場合がtrueなので今回はfalse）
			{
				list.add("社員IDが使用されていません");
				mv.setViewName("No13");
			}
			else
			{
				String[] data = { syainID,syainNAME};
				//社員マスタから削除
				DBaccess(data);
				System.out.println("登録されている社員ID："+ syainID +"、社員名："+ syainNAME +"を社員マスタから削除します");
				mv.setViewName("No13_delete");
			}
		}
		catch(Exception e)
		{
			e. printStackTrace();
			System.out.println("error");
		}
		finally
		{
			System.out.println("削除完了");
		}
		return mv;
	}
	
	 @RequestMapping("/MODORI13") 
	 public ModelAndView modori13Screen(@ModelAttribute InsertSyainForm form) {
			String syainID = "";
			String syainNAME = "";
			ModelAndView mv = new ModelAndView();
			syainID = form.getSyainID();
			syainNAME = form.getSyainNAME();
			mv.addObject("key01",syainID);
			mv.addObject("key02",syainNAME);
			System.out.println(syainID);
			System.out.println(syainNAME);
			mv.setViewName("No13");
		 return mv;
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
