
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShinNo14_over  extends oyaNo implements inter{

	//Interface(入力チェック)
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

	//Interface(忘れ防止)
	public int yarukoto()
	{
		return 0;
	}


	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		//文字コードの指定
		request.setCharacterEncoding("UTF-8");

		List<String> kekkalist = new ArrayList<String>();
		List<String> getlist = new ArrayList<String>();
		getlist.add("kensaku");//0
		getlist.add("con");//1
		getlist.add("prime_juni");//2
		getlist.add("id_juni");//3
		getlist.add("name_juni");//4
		kekkalist = sengen(request, getlist);


		String kensaku_Alt = "";//検索カラム指定の際に検索対象とは別のカラムを代入する変数
		String No = "14";
		String juni = "";//第一優先のカラム
		String juni_Alt = "";//第一優先ではないカラム
		String juni_Jun0 = "";//第一優先のソート条件(昇順 or 降順)
		String juni_Jun1 = "";//もう一つのソート条件(昇順 or 降順)
		int kensu =0;//検索の際の件数取得変数
		int kensaku_point = 0;
		String kensakumoji[] = {kekkalist.get(0)};
		int add = 1;

		List <String>kensakulist = new ArrayList<String>();
		String a ="";
		int error =0;

		try {

			//検索対象の半角全角チェック準備
			a = Sonzaicheck(kensakumoji);

			if(kekkalist.get(0) != "")//文字が入力されているとき
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
				if(kekkalist.get(2).equals("syainID"))//第一優先がIDの時
				{
					juni = "syainID";
					juni_Alt = "syainNAME";
					juni_Jun0 = kekkalist.get(3);
					juni_Jun1 = kekkalist.get(4);
				}
				else//第一優先がNAMEの時
				{
					juni = "syainNAME";
					juni_Alt = "syainID";
					juni_Jun0 = kekkalist.get(4);
					juni_Jun1 = kekkalist.get(3);
				}
				String con = kekkalist.get(1);

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
					error = 1;
				}
				//jspに返す社員情報
			}
			else
			{
				kensakulist.add("検索対象が未入力です");
				add = 0;
				error = 1;
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
			back.put("kaesimoji",kekkalist.get(0));
			back.put("kaesikekka",kensakulist);
			back.put("kaesiP",kensu);
			back.put("point",error);
			back.put("kensaku_point",kensaku_point);
			kaesi(request,back);
		}
		catch(Exception e)
		{
			e. printStackTrace();
			System.out.println("error");
		}
		finally
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

}

//http://localhost:8080/11-14_yasuda/No14.jsp
