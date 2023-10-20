import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class No12_hozon extends oyaNo{
	//doPostメソッドの引数？にあるHttpServletRequestクラス？の変数requestに画面情報？がある？的な
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
		Hashtable<String, String> hshi = new Hashtable<String,String>();
		hshi.put("key0","resID");
		hshi.put("key1","resNAME");
		hshi.put("key2","responsID");
		hshi.put("key3","responsNAME");
		List<String> getlist = new ArrayList<String>();
		getlist.add("resID");//0
		getlist.add("resNAME");//1
		getlist.add("responsID");//0
		getlist.add("responsNAME");//1


		boolean check = true;//更新前エラー用
		boolean check1 = true;//更新後エラー用
		String jsp = "";//jsp行先
		//エラーメッセージ用のテーブル
		List<String> list = new ArrayList<String>();

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
				kinou(kousingoId,kousingoName,kousinmaeId);
				jsp = "No12_choice.jsp";
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
	public void kinou(String kousingoId,String kousingoName,String kousinmaeId)
	{
		try {
		junbi jun = new junbi();
			jun.kousin(kousingoId,kousingoName,kousinmaeId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}

//http://localhost:8080/11-14_yasuda/No11.jsp
