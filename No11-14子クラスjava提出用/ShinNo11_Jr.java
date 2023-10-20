import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShinNo11_Jr extends oyaNo{
	//doPostメソッドの引数？にあるHttpServletRequestクラス？の変数requestに画面情報？がある？的な
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		//文字コードの指定
		request.setCharacterEncoding("UTF-8");

		//request.getParamaterでjsp側で設定したvalueの値を取得し代入
		//入力欄
		String workId = request.getParameter("syainID");
		String workName = request.getParameter("syainNAME");
		String point = "0";
		String No = "11";
		int add = 0;
		String jsp = "";
		List<String> set = new ArrayList<String>(); //削除に使うリスト型の宣言
		set.add("1");
		set.add("2");
		//エラーメッセージ用のテーブル
		List<String> list = new ArrayList<String>();
		Hashtable<String, List<String>> hsh = new Hashtable<String, List<String>>();

		list = in_hantei(workId,workName);

		if(list.contains("1") != true)
		{
			list = sonzaihantei(workId,workName);
			if(list.contains("2") != true)
			{
				add = 1;
			}
		}
		hsh.put("key1",list);
		request.setAttribute("kaesiP",point);
		request.setAttribute("kaesiP[]",hsh.get("key1"));
		request.setAttribute("kaesiID",workId);
		request.setAttribute("kaesiNAME", workName);

		List <String>ikisakilist = new ArrayList<String>();
		ikisakilist.add("kaesiP[]");
		ikisakilist.add("kaesiP");
		ikisakilist.add("kaesiID");
		ikisakilist.add("kaesiNAME");
		List <Object>kaesilist = new ArrayList<Object>();
		kaesilist.add (hsh.get("key1"));//0
		kaesilist.add(point);//入力またはボタン押下した判定用
		kaesilist.add (workId);//0
		kaesilist.add(workName);//
		kaesi(request,ikisakilist,kaesilist);

		list.removeAll(set);
		System.out.println(list);
		jsp = ikisaki(No,add);
		RequestDispatcher disp = request.getRequestDispatcher(jsp);
		//画面遷移
		disp.forward(request, response);
	}
}

//http://localhost:8080/11-14_yasuda/No11.jsp
