
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShinNo14 extends HttpServlet{
	//doPostメソッドの引数にあるHttpServletRequestクラスの変数requestに画面情報がある
	@SuppressWarnings("unused")
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		//文字コードの指定
		request.setCharacterEncoding("UTF-8");

		//request.getParamaterでjsp側で設定したvalueの値を取得し代入
		String kensakumoji = request.getParameter("kensaku");//検索する文字用変数
		String kensaku = "";//検索対象指定の為の変数
		String kensaku_Alt = "";//検索カラム指定の際に検索対象とは別のカラムを代入する変数
		String jsp = "";//jsp行先
		String bunki = request.getParameter("con");//検索条件(前方一致 or 後方一致 or 全て一致 or 部分一致)
		String juni = "";//第一優先のカラム
		String juni_Alt = "";//第一優先ではないカラム
		String juni_Jun0 = "";//第一優先のソート条件(昇順 or 降順)
		String juni_Jun1 = "";//もう一つのソート条件(昇順 or 降順)
		String mae = "";//検索の際に検索条件を入れるための変数(変数の前に入る)
		String usiro = "";//検索の際に検索条件を入れるための変数(変数の後ろに入る)
		int kensu =0;//検索の際の件数取得変数
		List <String>kensakulist = new ArrayList<String>();
		String a ="";

		try {
			junbi jun =new junbi();


			//検索対象の半角全角チェック準備
			a = jun.NAMEhantei(kensakumoji);

			if(kensakumoji != "")//文字が入力されているとき
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
				if(request.getParameter("prime_juni").equals("syainID"))//第一優先がIDの時
				{
					juni = "syainID";
					juni_Alt = "syainNAME";
					juni_Jun0 = request.getParameter("id_juni");
					juni_Jun1 = request.getParameter("name_juni");
				}
				else//第一優先がNAMEの時
				{
					juni = "syainNAME";
					juni_Alt = "syainID";
					juni_Jun0 = request.getParameter("name_juni");
					juni_Jun1 = request.getParameter("id_juni");
				}

				System.out.println(juni);//第一優先のカラム
				System.out.println(juni_Alt);//第二優先のカラム
				System.out.println(juni_Jun0);//第一優先のソート順
				System.out.println(juni_Jun1);//第二優先のソート順
				System.out.println(kensakumoji);//入力した文字
				System.out.println(bunki);//検索条件

				//社員マスタ検索
				if(bunki.equals("front"))
				{
					mae = "";
					usiro = "%";
				}
				else if(bunki.equals("back"))
				{
					mae = "%";
					usiro = "";
				}
				else if(bunki.equals("all"))
				{
					mae = "";
					usiro = "";
				}
				else if(bunki.equals("part"))
				{
					mae = "%";
					usiro = "%";
				}


				kensakulist = jun.kensakulist(kensaku_Alt, mae, kensakumoji, usiro, juni, juni_Jun0, juni_Alt, juni_Jun1);

				 System.out.println(kensakulist);

				jsp = "No14_result.jsp";
				if( kensakulist.size() == 0 )
				{
					kensakulist.add("検索結果がありませんでした");
					jsp = "No14.jsp";
				}
				//jspに返す社員情報
				jsp = "No14_result.jsp";
			}
			else
			{
				kensakulist.add("検索する文字が未入力です");
				jsp = "No14.jsp";
			}
			request.setAttribute("kaesimoji",kensakumoji);
			request.setAttribute("kaesikekka",kensakulist);
			request.setAttribute("kaesiP",kensu);
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
}

//http://localhost:8080/11-14_yasuda/No14.jsp
