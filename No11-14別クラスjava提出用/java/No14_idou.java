import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class No14_idou extends HttpServlet{
	//doPostメソッドの引数？にあるHttpServletRequestクラス？の変数requestに画面情報？がある？的な
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		//文字コードの指定
		request.setCharacterEncoding("UTF-8");

		String jsp ="" ;
		String hanteiJ = request.getParameter("hantei");

		if(hanteiJ.equals("1"))//hanteiが1の時は終了
		{
			jsp = "menu.jsp";//メニュー画面へ
		}
		else
		{
			jsp = "No14.jsp";//検索画面へ
		}

		//マスタ接続設定
		RequestDispatcher disp = request.getRequestDispatcher(jsp);
		//画面遷移
		disp.forward(request, response);
	}
}

//http://localhost:8080/11-14_yasuda/No13.jsp
