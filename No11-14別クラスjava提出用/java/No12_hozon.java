import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class No12_hozon extends HttpServlet{
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
		String kousinmaeId = request.getParameter("resID");
		String kousinmaeName = request.getParameter("resNAME");
		String kousingoId = request.getParameter("responsID");
		String kousingoName = request.getParameter("responsNAME");
		boolean check = true;//更新前エラー用
		boolean check1 = true;//更新後エラー用
		String jsp = "";//jsp行先
		//エラーメッセージ用のテーブル
		List<String> list = new ArrayList<String>();
		Hashtable hsh = new Hashtable();

		try {
			junbi jun = new junbi();
			//社員マスタの重複チェック

			check = jun.IDsonzaihantei_b(kousinmaeId);
			check1 = jun.IDsonzaihantei_b(kousingoId);
			System.out.println(check);
			System.out.println(check1);
			System.out.println(kousinmaeId);
			System.out.println(kousingoId);
			if(check != true)
			{
				list.add("社員IDが登録されていません");
				jsp = "No12.jsp";
			}
			else if(check1 == true)
			{
				list.add("社員IDが使用されています");
				jsp = "No12.jsp";
			}
			else if(check == true && check1 != true)
			{
				//社員マスタを更新
				System.out.println("登録されている社員ID："+kousinmaeId+"、社員名："+kousinmaeName+"に更新する情報の社員ID："+kousingoId+"、社員名："+kousingoName+"へ更新します");
				jun.kousin(kousingoId,kousingoName,kousinmaeId);
				jsp = "No12_choice.jsp";
			}
			hsh.put("key1",list);
			request.setAttribute("kaesiP",check);
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
