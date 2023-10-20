import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
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

		List<String> kekkalist = new ArrayList<String>();
		List<String> getlist = new ArrayList<String>();
		getlist.add("kaesiID");//0
		getlist.add("kaesiNAME");//1
		kekkalist = sengen(request, getlist);
		String workId = kekkalist.get(0);
		String workName = kekkalist.get(1);
		String jsp = "";
		//エラーメッセージ用のテーブル
		List<String> Messlist = new ArrayList<String>();

		try {
			int check1 = IDSonzaiHantei(workId);

			if(check1>0)
			{
				Messlist.add("社員IDが使用されています");
				jsp = "No11.jsp";
			}
			else
			{
				//社員マスタに登録
				System.out.println("社員IDに"+workId+"、社員名に"+workName+"を登録します");
				Hashtable<String, Object> tourokuhensu = new Hashtable<String,Object>();
				tourokuhensu.put("key0", workId);
				tourokuhensu.put("key1", workName);
				DBaccess(tourokuhensu);
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


}

//http://localhost:8080/11-14_yasuda/No11.jsp
