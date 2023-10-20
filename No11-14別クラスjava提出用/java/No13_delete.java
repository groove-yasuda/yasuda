import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class No13_delete extends HttpServlet{
	//doPostメソッドの引数？にあるHttpServletRequestクラス？の変数requestに画面情報？がある？的な
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
		int check = 0;
		String jsp = "";
		String point =null;
		//エラーメッセージ用のテーブル
		List<String> list = new ArrayList<String>();
		Hashtable<String, List<String>> hsh = new Hashtable<String, List<String>>();

		try {
			junbi jun = new junbi();
			//社員マスタの重複チェック

			check = jun.IDsonzaihantei(workId);
			System.out.println("削除確認");
			if(check==0)//重複がない場合（重複がある場合がtrueなので今回はfalse）
			{
				list.add("社員IDが使用されていません");
				point = "1";
				System.out.println(hsh.get("key1"));
				request.setAttribute("kaesiP[]",hsh.get("key1"));
				request.setAttribute("kaesiP",point);//エラー表記の為にjspへ返す
			}
			else
			{
				//社員マスタから削除
				jun.sakujo(workId);
				System.out.println("登録されている社員ID："+workId+"、社員名："+workName+"を社員マスタから削除します");
				jsp = "No13_choice.jsp";
			}
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
			System.out.println("削除完了");
		}
	}
}

//http://localhost:8080/11-14_yasuda/No13.jsp
