import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class No11_idou extends oyaNo{
	//doPostメソッドの引数？にあるHttpServletRequestクラス？の変数requestに画面情報？がある？的な
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		List<String> kekkalist = new ArrayList<String>();
		List<String> getlist = new ArrayList<String>();
		getlist.add("kaesiID");//0
		getlist.add("kaesiNAME");//1
		kekkalist = sengen(request, getlist);
		int i = 1;
		String workId = kekkalist.get(0);
		String workName = kekkalist.get(1);
		Hashtable<String, Object> back = new Hashtable<String,Object>();
		if(workId != null || workName != null)
		{
		back.put("kaesiID",workId);
		back.put("kaesiNAME",workName);
		back.put("release",i);
		kaesi(request,back);
		}
		//文字コードの指定
		request.setCharacterEncoding("UTF-8");
			//マスタ接続設定
			RequestDispatcher disp = request.getRequestDispatcher("No11.jsp");
			//画面遷移
			disp.forward(request, response);

	}
}

//http://localhost:8080/11-14_yasuda/No11.jsp
