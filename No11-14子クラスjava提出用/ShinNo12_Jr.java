import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShinNo12_Jr extends oyaNo{
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
		String newhantei = request.getParameter("hantei");//更新前か後か(0なら更新前、1なら更新後)
		String kousinHantei = null;//jspに返す際に更新後にするかの変数
		String workId = null;//ID用変数
		String workName = null;//名前用の変数
		String maeId = "";//更新前ID用の変数
		String maeName = "";//更新前NAME用の変数
		//エラーメッセージ用のテーブル
		List<String> Hanteilist = new ArrayList<String>();
		List<String> Sonzailist = new ArrayList<String>();
		List<String> Messlist = new ArrayList<String>();
		List <String>ikisakilist = new ArrayList<String>();
		List kaesilist = new ArrayList();

		List<String> set = new ArrayList<String>(); //削除に使うリスト型の宣言
		set.add("1");
		set.add("2");
		Hashtable hsh = new Hashtable();

		if(newhantei.equals("0"))//更新前なら
		{
			//更新する対象の社員情報を代入
			workId = request.getParameter("syainID");
			workName = request.getParameter("syainNAME");
		}
		else//更新最中なら
		{
			//更新前、更新後用の社員情報の代入
			maeId = request.getParameter("watasiID");
			maeName = request.getParameter("watasiNAME");
			workId = request.getParameter("kousinID");
			workName = request.getParameter("kousinNAME");
		}
		String jsp = "";//jsp行先
		String No = "12";
		int add = 0;
		String point = "0";//エラー表記用の変数

		Hanteilist = in_hantei(workId,workName);

		//社員マスタの重複チェック
		Sonzailist = sonzaihantei(workId,workName,newhantei);
		if(newhantei.equals("0"))//更新前
		{
			if(Hanteilist.contains("1") != true)
			{
				if(Sonzailist.contains("1") == true)
				{
					Messlist.add("社員IDが使用されていないか社員IDと社員名の組み合わせが違います");
					Messlist.add("1");
				}
				else if(Sonzailist.contains("2") == true)
				{
					kousinHantei = "1";//更新後入力の為にjspに返す用
					//jspに返す用の社員情報
					request.setAttribute("kaesiID",workId);
					request.setAttribute("kaesiNAME", workName);
					request.setAttribute("kaesiH",kousinHantei);
				}
			}
		}
		else//更新後
		{
			if(Hanteilist.contains("1") != true)
			{
				if(Sonzailist.contains("1") == true)//重複がある場合（更新最中は重複なしがtrueなので今回の場合はエラー）
				{
					Messlist.add("社員IDが使用されています");
					Messlist.add("1");
				}
				else if(Sonzailist.contains("2") == true)
				{
					Messlist.add("登録されている社員ID："+maeId+"　　　社員名："+maeName+"　を");
					Messlist.add("更新する情報の社員ID："+workId+"　　　社員名："+workName+"　へ更新します");
					//jspに返す社員情報
					request.setAttribute("kaesiID",maeId);
					request.setAttribute("kaesiNAME", maeName);
					request.setAttribute("hensinID",workId);
					request.setAttribute("hensinNAME", workName);

					ikisakilist.add("kaesiID");
					ikisakilist.add("kaesiNAME");
					ikisakilist.add("hensinID");
					ikisakilist.add("hensinNAME");

					kaesilist.add (maeId);//
					kaesilist.add(maeName);//
					kaesilist.add (workId);//
					kaesilist.add(workName);//
					add = 1;
				}
			}
		}
		Hanteilist.addAll(Messlist);
		Hanteilist.removeAll(set);
		hsh.put("key1",Hanteilist);
		jsp = ikisaki(No,add);

		ikisakilist.add("kaesiP");
		ikisakilist.add("kaesiP[]");

		kaesilist.add (point);
		kaesilist.add(hsh.get("key1"));
		kaesi(request,ikisakilist,kaesilist);
		RequestDispatcher disp = request.getRequestDispatcher(jsp);
		//画面遷移
		disp.forward(request, response);
	}
	public List<String> sonzaihantei(String workId,String workName,String newhantei) {
		List<String> list = new ArrayList<String>();
		try {
			junbi jun =new junbi();
			int check = 0;//ID重複用の変数

			check = jun.ID_NAMEsonzaihantei(workId, workName);
			if(newhantei.equals("0"))//更新前
			{
				if(check==0)//重複がない場合（更新前はIDありがtrueなので今回の場合はエラー）
				{
					list.add("1");
				}
				else
				{
					list.add("2");
				}
			}
			else//更新後
			{
				//社員マスタの重複チェック
				check = jun.IDsonzaihantei(workId);

				if(check>=1)//重複がある場合（更新最中は重複なしがtrueなので今回の場合はエラー）
				{
					list.add("1");
				}
				else if(check == 0)
				{
					list.add("2");
				}
			}
		}catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}

//http://localhost:8080/11-14_yasuda/No12.jsp
