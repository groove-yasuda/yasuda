import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class No11_hozon extends oyaNo{
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
		String jsp = "";
		//エラーメッセージ用のテーブル
		List<String> list = new ArrayList<String>();

		try {
			junbi jun = new junbi();
			int check1 =  jun.IDsonzaihantei(workId);

			if(check1>0)
			{
				list.add("社員IDが使用されています");
				jsp = "No11.jsp";
			}
			else
			{
				//社員マスタに登録
				System.out.println("社員IDに"+workId+"、社員名に"+workName+"を登録します");
				kinou(workId,workName);
				jsp = "No11_choice.jsp";
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
			System.out.println("登録完了");

		}
	}
	public void kinou(String workId,String workName)
	{
		try {
		junbi jun =new junbi();
			jun.touroku(workId,workName);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}

//http://localhost:8080/11-14_yasuda/No11.jsp
