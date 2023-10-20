import java.io.IOException;
import java.sql.SQLException;
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
		//request.getParamaterでjsp側で設定したvalueの値を取得し代入
		//入力欄
		Hashtable<String, String> hshj = new Hashtable<String,String>();
		Hashtable<String, String> hshi = new Hashtable<String,String>();
		hshi.put("key0","resID");
		hshi.put("key1","resNAME");
		List<String> getlist = new ArrayList<String>();
		getlist.add("resID");//0
		getlist.add("resNAME");//1

		hshj = sengen(request, getlist,hshi);
		int check = 0;
		String jsp = "";
		String point =null;
		//エラーメッセージ用のテーブル
		List<String> list = new ArrayList<String>();
		Hashtable<String, List<String>> hsh = new Hashtable<String, List<String>>();

		try {
			junbi jun = new junbi();
			//社員マスタの重複チェック

			check = jun.IDsonzaihantei(hshj.get("resID"));
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
				kinou(hshj.get("resID"));
				System.out.println("登録されている社員ID："+hshj.get("resID")+"、社員名："+hshj.get("resID")+"を社員マスタから削除します");
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


	public void kinou(String workId)
	{
		junbi jun = new junbi();

		try {
			jun.sakujo(workId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}

//http://localhost:8080/11-14_yasuda/No13.jsp
