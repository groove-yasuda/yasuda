import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class No11_hozon extends HttpServlet{
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
		String workId = request.getParameter("resID");
		String workName = request.getParameter("resNAME");
		String jsp = "";
		String point =null;
		//エラーメッセージ用のテーブル
		List<String> list = new ArrayList<String>();
		Hashtable hsh = new Hashtable();

		try {
			junbi jun = new junbi();
			int check1 =  jun.IDsonzaihantei(workId);

			if(check1>0)
			{
				list.add("社員IDが使用されています");
				jsp = "No11.jsp";
				point = "1";
			}
			else
			{
				//社員マスタに登録
				System.out.println("社員IDに"+workId+"、社員名に"+workName+"を登録します");
				jun.touroku(workId,workName);
				jsp = "No11_choice.jsp";
			}
			hsh.put("key1",list);
			request.setAttribute("kaesiP",point);
			request.setAttribute("kaesiP[]",hsh.get("key1"));//エラー表記の為にjspに返す
		}
		catch(Exception e)
		{
			e. printStackTrace();
			System.out.println("error");
		}
		finally
		{
			//返却先の指定[今回は("No11.jsp")]
			RequestDispatcher disp = request.getRequestDispatcher(jsp);
			//画面遷移
			disp.forward(request, response);
			System.out.println("登録完了");

		}
	}
}

//http://localhost:8080/11-14_yasuda/No11.jsp
