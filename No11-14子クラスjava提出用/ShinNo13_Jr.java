import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShinNo13_Jr extends oyaNo{
	//doPostメソッドの引数にあるHttpServletRequestクラスの変数requestに画面情報がある
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		//文字コードの指定
		request.setCharacterEncoding("UTF-8");
		Hashtable<String, String> hshj = new Hashtable<String,String>();
		Hashtable<String, String> hshi = new Hashtable<String,String>();
		hshi.put("key0","syainID");
		hshi.put("key1","syainNAME");
		List<String> getlist = new ArrayList<String>();
		getlist.add("syainID");//0
		getlist.add("syainNAME");//1

		hshj = sengen(request, getlist,hshi);

		//request.getParamaterでjsp側で設定したvalueの値を取得し代入
		String jsp = "";//jsp行先
		int hantei_id = 1;;//IDのエラー判定用
		int hantei_name = 1;//NAMEのエラー判定用
		int add = 0;
		String kensakuTaisyou = "";//検索の際に使用する検索対象
		String kensakukaramu = "";//検索の際に使用する検索カラム
		String work = "";//片方だけ入力の検索の際に使用する検索する文字を代入する変数
		String IDhantei ="";
		String NAMEhantei = "";
		String workId = hshj.get("syainID");
		String workName = hshj.get("syainNAME");
		String No = "13";

		//エラーメッセージ用のテーブル
		List<String> Hanteilist = new ArrayList<String>();
		List<String> Sonzailist = new ArrayList<String>();
		List<String> Messlist = new ArrayList<String>();

		List<String> set = new ArrayList<String>(); //削除に使うリスト型の宣言
		set.add("error");
		set.add("hukusu");
		set.add("OK");
		set.add("ID&NAMEnon");
		set.add("ID&NAME");
		set.add("IDnon");
		set.add("NAMEnon");
		set.add("sonzainon");
		set.add("社員IDが未入力です");
		set.add("社員名が未入力です");


		//エラーメッセージ用のテーブル
		Hashtable hsh = new Hashtable();
		try {
			junbi jun = new junbi();
			if(workId != "" || workName != "")
			{
				Hanteilist = in_hantei(workId,workName,work,IDhantei,NAMEhantei,jsp);
				System.out.println(Hanteilist);
				if(Hanteilist.contains("IDnon") == true)
				{
					hantei_id = 0;
					System.out.println("IDNON");
				}
				if(Hanteilist.contains("NAMEnon") == true )
				{
					hantei_name = 0;
					System.out.println("NAMENON");
				}

				if(Hanteilist.contains("error") != true || Hanteilist.contains("hukusu") == true)
				{
					if(Hanteilist.contains("ID&NAME") == true)
					{
						Sonzailist = sonzaihantei(workId,workName);
						if(Sonzailist.contains("OK") == true && Hanteilist.contains("hukusu") == true)
						{
							System.out.println("f");
							Hanteilist.remove("社員名が複数件あります");
							Hanteilist.remove("error");
						}
					}
					else
					{
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
							workName = jun.katahoukensaku(kensakukaramu, kensakuTaisyou, work);
						}
						else if(hantei_id == 0 && hantei_name == 1)
						{
							workId = jun.katahoukensaku(kensakukaramu, kensakuTaisyou, work);
						}
					}
				}
			}
			else
			{
				Hanteilist.add("社員IDと社員名が未入力です");
				Hanteilist.add("error");
			}
			Messlist.addAll(Sonzailist);
			Messlist.addAll(Hanteilist);
			System.out.println(workId);
			System.out.println(workName);

			if(Messlist.contains("error") == true)//エラーメッセージがあるとき
			{
				hsh.put("key1",Messlist);
			}
			else
			{
				Messlist.add("削除する社員ID："+workId+"");
				Messlist.add("削除する社員名："+workName+"");
				hsh.put("key1",Messlist);
				System.out.println(hsh.get("key1"));
				//jspに返す社員情報
				add = 1;
			}
			Messlist.removeAll(set);
			jsp = ikisaki(No,add);
			List <String>ikisakilist = new ArrayList<String>();
			ikisakilist.add("kaesiP[]");
			ikisakilist.add("kaesiP");
			ikisakilist.add("kaesiID");
			ikisakilist.add("kaesiNAME");
			List kaesilist = new ArrayList();
			kaesilist.add (hsh.get("key1"));//0
			kaesilist.add(kensakuTaisyou);//入力またはボタン押下した判定用
			kaesilist.add (workId);//0
			kaesilist.add(workName);//
			kaesi(request,ikisakilist,kaesilist);
		}
		catch(Exception e)//何かしらのエラーの時
		{
			e. printStackTrace();
			System.out.println("error");
		}
		finally//何が起きても行う処理
		{
			RequestDispatcher disp = request.getRequestDispatcher(jsp);
			//画面遷移
			disp.forward(request, response);
		}
	}



	//オーバーライド
	public List<String> sonzaihantei(String workId,String workName)
	{
		List<String> list = new ArrayList<String>();
		junbi jun = new junbi();
		try {
			int check = jun.ID_NAMEsonzaihantei(workId, workName);

			if (check==0)//結びつきがないとき
			{
				list.add("社員IDと社員名の組み合わせが違います");//組み合わせエラーメッセージ
				list.add("error");
			}
			else
			{
				list.add("OK");
			}
		}
		catch(Exception e)//何かしらのエラーの時
		{
			e. printStackTrace();
			System.out.println("error");
		}
		return list;
	}

	public List<String> in_hantei(String workId,String workName,String point,String IDhantei,String NAMEhantei,String jsp) {

		List <String>list = new ArrayList<String>();
		junbi jun = new junbi();
		try {
			if(workId != "")//IDが入力されている
			{
				IDhantei = jun.IDhantei(workId);
				if(IDhantei.equals("false"))
				{
					list.add("社員IDの入力形式が間違っています");//ID入力エラーメッセージ
					list.add("error");
				}
				else
				{
					int sonzaicheck_Id;

					sonzaicheck_Id = jun.IDsonzaihantei(workId);

					if(sonzaicheck_Id==0)//IDが存在しない
					{
						list.add("社員IDが登録されていません");//ID存在なしエラーメッセージ
						list.add("error");
						list.add("sonzainon");
					}
				}
			}
			else//IDが入力されてない
			{
				list.add("社員IDが未入力です");//ID入力エラーメッセージ
				list.add("IDnon");
			}

			if(workName != "")//NAMEが入力されてる
			{
				NAMEhantei = jun.NAMEhantei(workName);
				if(NAMEhantei.equals("false"))//NAME入力エラーチェック
				{
					list.add("社員名の入力形式が間違っています");//NAME入力エラーメッセージ
					list.add("error");
				}
				else
				{
					int sonzaicheck_Name =  jun.NAMEsonzaihantei(workName);
					if(sonzaicheck_Name==0)//NAMEが存在しない
					{
						list.add("社員名がマスタに登録されていません");//NAME存在なしエラーメッセージ
						list.add("error");
						list.add("sonzainon");
					}
					else if(sonzaicheck_Name >1)//NAME重複してる
					{
						list.add("社員名が複数件あります");//NAME重複エラーメッセージ
						list.add("error");
						list.add("hukusu");
					}
				}
			}
			else
			{
				list.add("社員名が未入力です");//ID入力エラーメッセージ
				list.add("NAMEnon");
			}

			if(list.contains("IDnon")==true && list.contains("NAMEnon")==true)
			{
				list.add("ID&NAMEnon");
			}
			else if(list.contains("IDnon")!=true && list.contains("NAMEnon")!=true)
			{
				list.add("ID&NAME");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return list;
	}

}

//http://localhost:8080/11-14_yasuda/No13.jsp
