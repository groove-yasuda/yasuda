
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShinNo14_Jr extends oyaNo{
	//doPostメソッドの引数にあるHttpServletRequestクラスの変数requestに画面情報がある
	@SuppressWarnings("unused")
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		//文字コードの指定
		request.setCharacterEncoding("UTF-8");

		Hashtable<String, String> hsh = new Hashtable<String,String>();
		Hashtable<String, String> hshi = new Hashtable<String,String>();
		hshi.put("key0","kensakumoji");
		hshi.put("key1","con");
		hshi.put("key2","prime_juni");
		hshi.put("key3","id_juni");
		hshi.put("key4","name_juni");
		List<String> list = new ArrayList<String>();
		list.add("kensaku");//0
		list.add("con");//1
		list.add("prime_juni");//2
		list.add("id_juni");//3
		list.add("name_juni");//4

		hsh = sengen(request, list,hshi);

		//request.getParamaterでjsp側で設定したvalueの値を取得し代入
		String kensaku = "";//検索対象指定の為の変数
		String kensaku_Alt = "";//検索カラム指定の際に検索対象とは別のカラムを代入する変数
		String jsp = "";//jsp行先
		String No = "14";
		String juni = "";//第一優先のカラム
		String juni_Alt = "";//第一優先ではないカラム
		String juni_Jun0 = "";//第一優先のソート条件(昇順 or 降順)
		String juni_Jun1 = "";//もう一つのソート条件(昇順 or 降順)
		String mae = "";//検索の際に検索条件を入れるための変数(変数の前に入る)
		String usiro = "";//検索の際に検索条件を入れるための変数(変数の後ろに入る)
		int kensu =0;//検索の際の件数取得変数
		String kensakumoji = hsh.get("kensakumoji");
		String yusen = hsh.get("prime_juni");
		int add = 0;

		List <String>kensakulist = new ArrayList<String>();
		String a ="";

		try {
			junbi jun =new junbi();

			//検索対象の半角全角チェック準備
			a = jun.NAMEhantei(hsh.get("kensakumoji"));

			if(hsh.get("kensakumoji") != "")//文字が入力されているとき
			{
				if(a == "true")//半角の時
				{
					kensaku = "syainID";
					kensaku_Alt = "syainNAME";
				}
				else//全角の時
				{
					kensaku = "syainNAME";
					kensaku_Alt = "syainID";
				}

				//検索判定
				if(hsh.get("id_juni").equals("syainID"))//第一優先がIDの時
				{
					juni = "syainID";
					juni_Alt = "syainNAME";
					juni_Jun0 = hsh.get("id_juni");
					juni_Jun1 = hsh.get("name_juni");
				}
				else//第一優先がNAMEの時
				{
					juni = "syainNAME";
					juni_Alt = "syainID";
					juni_Jun0 = hsh.get("name_juni");
					juni_Jun1 = hsh.get("id_juni");
				}

				System.out.println(juni);//第一優先のカラム
				System.out.println(juni_Alt);//第二優先のカラム
				System.out.println(juni_Jun0);//第一優先のソート順
				System.out.println(juni_Jun1);//第二優先のソート順
				System.out.println(kensakumoji);//入力した文字
				System.out.println(hsh.get("con"));//検索条件
				String con = hsh.get("con");

				kensakulist = kinou(con,kensaku_Alt, kensakumoji, juni, juni_Jun0, juni_Alt, juni_Jun1);
						System.out.println(kensakulist);
				add = 1;

				if( kensakulist.size() == 0 )
				{
					kensakulist.add("検索結果がありませんでした");
					add = 0;
				}
				//jspに返す社員情報
			}
			else
			{
				kensakulist.add("検索する文字が未入力です");
				add = 0;
			}
			List <String>ikisakilist = new ArrayList<String>();
			List<Object> kaesilist = new ArrayList<Object>();
			ikisakilist.add("kaesimoji");
			ikisakilist.add("kaesikekka");
			ikisakilist.add("kaesiP");

			kaesilist.add (hsh.get("kensakumoji"));//0
			kaesilist.add (kensakulist);//0
			kaesilist.add(kensu);//入力またはボタン押下した判定用
			kaesi(request,ikisakilist,kaesilist);
			jsp = ikisaki(No,add);
		}
		catch(Exception e)
		{
			e. printStackTrace();
			System.out.println("error");
		}
		finally
		{
			RequestDispatcher disp = request.getRequestDispatcher(jsp);
			//画面遷移
			disp.forward(request, response);
		}
	}
	public List<String> kinou(String con,String kensaku_Alt,String kensakumoji,String juni,String juni_Jun0,String juni_Alt,String juni_Jun1)
	{
		junbi jun = new junbi();
		List <String>kensaku = new ArrayList<String>();
		String mae="";
		String usiro ="";
		try {
		//社員マスタ検索
		if(con.equals("front"))
		{
			mae = "";
			usiro = "%";
		}
		else if(con.equals("back"))
		{
			mae = "%";
			usiro = "";
		}
		else if(con.equals("all"))
		{
			mae = "";
			usiro = "";
		}
		else if(con.equals("part"))
		{
			mae = "%";
			usiro = "%";
		}
			kensaku = jun.kensakulist(kensaku_Alt, mae, kensakumoji, usiro, juni, juni_Jun0, juni_Alt, juni_Jun1);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return kensaku;
	}
}

//http://localhost:8080/11-14_yasuda/No14.jsp
