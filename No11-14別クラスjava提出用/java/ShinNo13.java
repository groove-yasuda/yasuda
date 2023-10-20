import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShinNo13 extends HttpServlet{
	//doPostメソッドの引数にあるHttpServletRequestクラスの変数requestに画面情報がある
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		//文字コードの指定
		request.setCharacterEncoding("UTF-8");

		//request.getParamaterでjsp側で設定したvalueの値を取得し代入
		String workId = request.getParameter("syainID");//ID用変数
		String workName = request.getParameter("syainNAME");//名前用の変数
		String jsp = "";//jsp行先
		int check = 0;//ID.NAME重複用の変数
		int sonzaicheck_Id = 1;//ID存在チェック用の変数
		int sonzaicheck_Name = 1;//NAME存在チェック用の変数
		int hantei_id = 1;;//IDのエラー判定用
		int hantei_name = 1;//NAMEのエラー判定用
		boolean data = true;
		int l = 0;
		String kensakuTaisyou = "";//検索の際に使用する検索対象
		String kensakukaramu = "";//検索の際に使用する検索カラム
		String work = "";//片方だけ入力の検索の際に使用する検索する文字を代入する変数
		String IDhantei ="";
		String NAMEhantei = "";

		//エラーメッセージ用のテーブル
		Hashtable hsh = new Hashtable();
		List list = new ArrayList();

		try {
			junbi jun = new junbi();
			if(workId != "" || workName != "")//片方でも入力がある時
			{
				if(workId != "")//IDが入力されている
				{
					IDhantei = jun.IDhantei(workId);
					if(IDhantei.equals("false"))
					{
						list.add("社員IDの入力形式が間違っています");//ID入力エラーメッセージ
						data = false;
					}
					else//ID入力正常
					{
						//ID存在チェック
						sonzaicheck_Id = jun.IDsonzaihantei(workId);

						if(sonzaicheck_Id==0)//IDが存在しない
						{
							list.add("社員IDが登録されていません");//ID存在なしエラーメッセージ
							data = false;
						}
					}
				}
				else//IDが入力されてない
				{
					hantei_id = 0;
				}

				if(workName != "")//NAMEが入力されてる
				{
					NAMEhantei = jun.NAMEhantei(workName);
					if(NAMEhantei.equals("false"))//NAME入力エラーチェック
					{
						list.add("社員名の入力形式が間違っています");//NAME入力エラーメッセージ
						data = false;
					}
					else//NAME入力正常
					{
						//NAME存在チェック
						sonzaicheck_Name =  jun.NAMEsonzaihantei(workName);

						if(sonzaicheck_Name==0)//NAMEが存在しない
						{
							list.add("社員名がマスタに登録されていません");//NAME存在なしエラーメッセージ
							data = false;
						}
						else if(sonzaicheck_Name >1)//NAME重複してる
						{
							list.add("社員名が複数件あります");//NAME重複エラーメッセージ
							data = false;
							l = 1;
						}
					}
				}
				else//入力されてない
				{
					hantei_name = 0;
				}

				if(data == true || l == 1 )//エラー無いとき
				{
					if(workId != "" && workName != "")//ID、NAME共に入力されているとき
					{
						//ID、NAMEの結びつきがあるかチェック
						check = jun.ID_NAMEsonzaihantei(workId, workName);
						if (check==0)//結びつきがないとき
						{
							list.add("社員IDと社員名の組み合わせが違います");//組み合わせエラーメッセージ
							data = false;
						}
						else if(l == 1 && check == 1)
						{
							list.remove("社員名が複数件あります");//メッセージの取り消し
							data = true;
						}
					}
					else//片方だけ入力のとき
					{
						if(data == true)
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
			}
			else//両方入力がないとき
			{
				list.add("社員IDと社員名が未入力です");//ID、NAME共に未入力のエラーメッセージ
				data = false;
			}
			hsh.put("key1",list);
			System.out.println(workId);
			System.out.println(workName);
			if(data == false)//エラーメッセージがあるとき
			{
				jsp = "No13.jsp";
			}
			else
			{
			list.add("削除する社員ID："+workId+"");
			list.add("削除する社員名："+workName+"");
			hsh.put("key1",list);
				//jspに返す社員情報
				request.setAttribute("kaesiID",workId);
				request.setAttribute("kaesiNAME", workName);
				jsp = "No13_delete.jsp";
			}
			System.out.println(hsh.get("key1"));
			request.setAttribute("kaesiP[]",hsh.get("key1"));
			request.setAttribute("kaesiP",data);//エラー表記の為にjspに返す
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
}

//http://localhost:8080/11-14_yasuda/No13.jsp
