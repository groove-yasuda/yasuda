import java.io.IOException;
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

		List<String> kekkalist = new ArrayList<String>();
		List<String> getlist = new ArrayList<String>();
		getlist.add("kaesiID");//0
		getlist.add("kaesiNAME");//1
		getlist.add("hensinID");//2
		getlist.add("hensinNAME");//3
		kekkalist = sengen(request, getlist);

		String kousinmaeId = kekkalist.get(0);
		String kousinmaeName = kekkalist.get(1);
		String kousingoId = kekkalist.get(2);
		String kousingoName = kekkalist.get(3);


		int check = 0;//更新前エラー用
		int check1 = 0;//更新後エラー用
		String jsp = "";//jsp行先
		//エラーメッセージ用のテーブル
		List<String> list = new ArrayList<String>();

		try {
			//社員マスタの重複チェック
			check = IDSonzaiHantei(kousinmaeId);
			check1 = IDSonzaiHantei(kousingoId);
			if(check == 0)
			{
				list.add("社員IDが登録されていません");
				jsp = "No12.jsp";
			}
			else if(check1 > 0)
			{
				list.add("社員IDが使用されています");
				jsp = "No12.jsp";
			}
			else if(check == 1 && check1 ==0)
			{
				//社員マスタを更新
				System.out.println("登録されている社員ID："+kousinmaeId+"、社員名："+kousinmaeName+"に更新する情報の社員ID："+kousingoId+"、社員名："+kousingoName+"へ更新します");
				Hashtable<String, Object> kousinhensu = new Hashtable<String,Object>();
				kousinhensu.put("key0",kousingoId);
				kousinhensu.put("key1",kousingoName);
				DBaccess(kousinhensu,kousinmaeId,kousinmaeName);
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
}

//http://localhost:8080/11-14_yasuda/No11.jsp
