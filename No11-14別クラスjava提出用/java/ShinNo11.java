import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShinNo11 extends HttpServlet{
	//doPostメソッドの引数？にあるHttpServletRequestクラス？の変数requestに画面情報？がある？的な
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		//文字コードの指定
		request.setCharacterEncoding("UTF-8");

		//request.getParamaterでjsp側で設定したvalueの値を取得し代入
		//入力欄
		String workId = request.getParameter("syainID");
		String workName = request.getParameter("syainNAME");
		String jsp = "";
		String point = "0";
		String IDhantei ="";
		String NAMEhantei = "";
		//エラーメッセージ用のテーブル
		List<String> list = new ArrayList<String>();
		Hashtable<String, List<String>> hsh = new Hashtable<String, List<String>>();

		try {
			junbi jun = new junbi();

				if(workId != "")//IDが入力されている
				{
					IDhantei = jun.IDhantei(workId);
					if(IDhantei.equals("false"))
					{
						list.add("社員IDの入力形式が間違っています");//ID入力エラーメッセージ
						point = "1";
					}
				}
				else//IDが入力されてない
				{
					list.add("社員IDが未入力です");//ID入力エラーメッセージ
					point = "1";
				}

				if(workName != "")//NAMEが入力されてる
				{
					NAMEhantei = jun.NAMEhantei(workName);
					if(NAMEhantei.equals("false"))//NAME入力エラーチェック
					{
						list.add("社員名の入力形式が間違っています");//NAME入力エラーメッセージ
						point = "1";
					}
				}
				else
				{
					list.add("社員名が未入力です");//ID入力エラーメッセージ
				}
				if(point == "0") {
					if(workId != "" && workName != "")
					{
						//社員マスタの重複チェック
						int check1 = jun.IDsonzaihantei(workId);
						if(check1>0)
						{
							list.add("社員IDが登録されています");//ID存在エラーメッセージ
							point = "1";
						}
						else if (check1 == 0)
						{
							list.add("社員IDに"+workId+"");
							list.add("社員名に"+workName+"　を登録します");
						}
					}
				}
				hsh.put("key1",list);
				request.setAttribute("kaesiP",point);
				request.setAttribute("kaesiP[]",hsh.get("key1"));
				if(point=="0")
				{
					request.setAttribute("kaesiID",workId);
					request.setAttribute("kaesiNAME", workName);
					jsp = "No11_kakunin.jsp";
				}
				else
				{
					jsp = "No11.jsp";
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

//http://localhost:8080/11-14_yasuda/No11.jsp
