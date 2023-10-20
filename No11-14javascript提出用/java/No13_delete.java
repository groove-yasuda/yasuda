import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class No13_delete extends oyaNo{
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
		int check = 0;
		String jsp = "";
		String point =null;
		//エラーメッセージ用のテーブル
		List<String> list = new ArrayList<String>();
		Hashtable<String, List<String>> result = new Hashtable<String, List<String>>();

		try {
			//社員マスタの重複チェック

			check = IDSonzaiHantei(kekkalist.get(0));
			System.out.println("削除確認");
			if(check==0)//重複がない場合（重複がある場合がtrueなので今回はfalse）
			{
				list.add("社員IDが使用されていません");
				point = "1";
				System.out.println(result.get("key1"));
				Hashtable<String, Object> back = new Hashtable<String,Object>();
				back.put("kaesiP[]",list);
				back.put("kaesiP",point);
				kaesi(request,back);
			}
			else
			{
				String[] data = { kekkalist.get(0),kekkalist.get(1)};
				//社員マスタから削除
				DBaccess(data);
				System.out.println("登録されている社員ID："+ kekkalist.get(0)+"、社員名："+ kekkalist.get(1)+"を社員マスタから削除します");
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
