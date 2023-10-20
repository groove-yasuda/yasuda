import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShinNo12 extends HttpServlet{
	//doPostメソッドの引数？にあるHttpServletRequestクラス？の変数requestに画面情報？がある？的な
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		//文字コードの指定
		request.setCharacterEncoding("UTF-8");

		//request.getParamaterでjsp側で設定したvalueの値を取得し代入
		//入力欄
		String newhantei = request.getParameter("hantei");//更新前か後か(0なら更新前、1なら更新後)
		String kousinHantei = null;//jspに返す際に更新後にするかの変数
		String workId = null;//ID用変数
		String workName = null;//名前用の変数
		String maeId = "";//更新前ID用の変数
		String maeName = "";//更新前NAME用の変数
		String IDhantei ="";
		String NAMEhantei = "";
		//エラーメッセージ用のテーブル
		List<String> list = new ArrayList<String>();
		Hashtable hsh = new Hashtable();


		if(newhantei.equals("0"))//更新前なら
		{
			//更新する対象の社員情報を代入
			workId = request.getParameter("syainID");
			workName = request.getParameter("syainNAME");
		}
		else//更新最中なら
		{
			//更新前、更新後用の社員情報の代入
			maeId = request.getParameter("watasiID");
			maeName = request.getParameter("watasiNAME");
			workId = request.getParameter("kousinID");
			workName = request.getParameter("kousinNAME");
		}
		String jsp = "";//jsp行先
		String point = "0";//エラー表記用の変数
		int check = 0;//ID重複用の変数

		try {
			junbi jun = new junbi();

			if(workId != "")//IDが入力されている
			{
				IDhantei = jun.IDhantei(workId);
			}
			else
			{
				list.add("社員IDを入力してください");
				point = "1";
			}

			if(workName != "")
			{
				NAMEhantei = jun.NAMEhantei(workName);
			}
			else
			{
				list.add("社員名を入力して下さい");
				point = "1";
			}
			if(IDhantei.equals("false"))
			{
				list.add("社員IDの入力形式が間違っています");//NAME入力エラーメッセージ
				point = "1";
			}
			if(NAMEhantei.equals("false"))
			{
				list.add("社員名の入力形式が間違っています");//NAME入力エラーメッセージ
				point = "1";
			}
			//社員マスタの重複チェック
			if(newhantei.equals("0"))//更新前
			{
				if(point == "0")
				{
					check = jun.ID_NAMEsonzaihantei(workId, workName);
					if(check==0)//重複がない場合（更新前はIDありがtrueなので今回の場合はエラー）
					{
						list.add("社員IDが使用されていないか社員IDと社員名の組み合わせが違います");
						point = "1";
					}
					else
					{
						kousinHantei = "1";//更新後入力の為にjspに返す用
						//jspに返す用の社員情報
						request.setAttribute("kaesiID",workId);
						request.setAttribute("kaesiNAME", workName);
						request.setAttribute("kaesiH",kousinHantei);
						jsp = "No12.jsp";
					}
				}
			}
			else//更新後
			{
				if(point == "0")
				{
					//社員マスタの重複チェック
					check = jun.IDsonzaihantei(workId);
					if(check>0)//重複がある場合（更新最中は重複なしがtrueなので今回の場合はエラー）
					{
						list.add("社員IDが使用されています");
						point = "1";
					}
					else
					{
						list.add("登録されている社員ID："+maeId+"　　　社員名："+maeName+"　を");
						list.add("更新する情報の社員ID："+workId+"　　　社員名："+workName+"　へ更新します");
						//jspに返す社員情報
						request.setAttribute("kaesiID",maeId);
						request.setAttribute("kaesiNAME", maeName);
						request.setAttribute("hensinID",workId);
						request.setAttribute("hensinNAME", workName);
						jsp = "No12_hozon.jsp";
					}
				}
			}
			hsh.put("key1",list);
			request.setAttribute("kaesiP",point);
			request.setAttribute("kaesiP[]",hsh.get("key1"));//エラー表記の為にjspに返す
			if(point!="0")
			{
				jsp = "No12.jsp";
			}
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

//http://localhost:8080/11-14_yasuda/No12.jsp
