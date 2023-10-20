import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShinNo13_over  extends oyaNo implements inter{

	//Interface(入力チェック)
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

	//Interface(忘れ防止)
	public int yarukoto()
	{
		return 0;
	}



	//doPostメソッドの引数にあるHttpServletRequestクラスの変数requestに画面情報がある
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		//文字コードの指定
		request.setCharacterEncoding("UTF-8");
		List<String> deletelist = new ArrayList<String>();
		deletelist.add("OK");
		deletelist.add("NO");
		deletelist.add("katahou");

		//画面情報取得
		List<String> kekkalist = new ArrayList<String>();
		List<String> getlist = new ArrayList<String>();
		getlist.add("syainID");//0
		getlist.add("syainNAME");//1
		kekkalist = sengen(request, getlist);

		//変数宣言
		int hantei_id = 1;;//IDのエラー判定用
		int hantei_name = 1;//NAMEのエラー判定用
		int add = 0;
		int huriwake = 0;
		int id_point = 0;
		int name_point = 0;
		String error = "0";
		String kensakuTaisyou = "";//検索の際に使用する検索対象
		String kensakukaramu = "";//検索の際に使用する検索カラム
		String work = "";//片方だけ入力の検索の際に使用する検索する文字を代入する変数
		String workId = kekkalist.get(0);
		String workName = kekkalist.get(1);
		String No = "13";

		//エラーメッセージ用のテーブル
		List<String> Messlist = new ArrayList<String>();

		try {
			if(workId != "" || workName != "")
			{
				Messlist = in_hantei(workId,workName);
				System.out.println(Messlist);
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
							System.out.println("7");
							int check = Sonzaicheck(workId, workName);

							if (check==0)//結びつきがないとき
							{
								Messlist.add("社員IDと社員名の組み合わせが違います");//組み合わせエラーメッセージ
								error = "1";
							}
							else
							{
								Messlist.add("OK");
							}
							System.out.println(Messlist+"1");
							if(Messlist.contains("OK") == true && Messlist.contains("社員名が複数件存在します") == true)
							{
								Messlist.remove("社員名が複数件存在します");//3
									error = "1";
							}
						}
						else
						{
							System.out.println("c");
							//"0"=入力されていない、"1"=入力されている
							if(hantei_id == 1 && hantei_name == 0)
							{
								kensakukaramu = "syainNAME";
								kensakuTaisyou = "syainID";
								work = workId;
							}
							else if(hantei_id == 0 && hantei_name == 1)
							{
								kensakukaramu = "syainID";
								kensakuTaisyou = "syainNAME";
								work = workName;
							}
							//未入力の項目を検索
							//"0"=入力されていない、"1"=入力されている
							if(hantei_id == 1 && hantei_name == 0)
							{
								workName = KatahouKensaku(kensakukaramu, kensakuTaisyou, work);
								Messlist.remove("社員名が未入力です");
								huriwake = 1;
							}
							else if(hantei_id == 0 && hantei_name == 1)
							{
								workId = KatahouKensaku(kensakukaramu, kensakuTaisyou, work);
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
			System.out.println(workId);
			System.out.println(workName);
			System.out.println(Messlist);
			Messlist.removeAll(deletelist);
			if(Messlist.size() == 0)//エラーメッセージがあるとき
			{
				Messlist.add("削除する社員ID："+workId+"");
				Messlist.add("削除する社員名："+workName+"");
				//jspに返す社員情報
				add = 1;
			}

			for (int i = 0; i < Messlist.size(); i++) {
 			if(Messlist.get(i) == "社員IDが未入力です" ||
 				Messlist.get(i) == "社員IDの入力形式に誤りがあります" ||
 				Messlist.get(i) == "社員IDが登録されています"||
 				Messlist.get(i) == "社員IDが使用されています"||
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
			Hashtable<String, Object> back = new Hashtable<String,Object>();
			back.put("kaesiP[]",Messlist);
			back.put("kaesiP",kensakuTaisyou);
			back.put("point",error);
			back.put("id_point",id_point);
			back.put("name_point",name_point);
			back.put("release",No);
			if(huriwake == 0)
			{
			back.put("kaesiID",workId);
			back.put("kaesiNAME",workName);
			}
			else if(huriwake == 1)
			{
				String kara = "";
			back.put("kaesiID",workId);
			back.put("kaesiNAME",kara);
			}
			else if(huriwake == 2)
			{
				String kara = "";
			back.put("kaesiID",kara);
			back.put("kaesiNAME",workName);
			}
			System.out.println(Messlist);
			kaesi(request,back);
		}
		catch(Exception e)//何かしらのエラーの時
		{
			e. printStackTrace();
			System.out.println("error");
		}
		finally//何が起きても行う処理
		{
			ikisaki(request,response,No,add);
		}
	}

	//Interface(画面遷移)
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

//http://localhost:8080/11-14_yasuda/No13.jsp
