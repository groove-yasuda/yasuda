import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class No13_idou extends oyaNo{
	//doPostメソッドの引数？にあるHttpServletRequestクラス？の変数requestに画面情報？がある？的な
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		//文字コードの指定
		request.setCharacterEncoding("UTF-8");
		Hashtable<String, Object> back = new Hashtable<String,Object>();
		int i = 1;
		String ID = request.getParameter("kaesiID");
		String NAME = request.getParameter("kaesiNAME");
		if(ID != null || NAME != null)
		{
		back.put("kaesiID",ID);
		back.put("kaesiNAME",NAME);
		back.put("release",i);
		kaesi(request,back);
		}

		//マスタ接続設定
		RequestDispatcher disp = request.getRequestDispatcher("No13.jsp");
		//画面遷移
		disp.forward(request, response);
	}
}

//http://localhost:8080/11-14_yasuda/No13.jsp
