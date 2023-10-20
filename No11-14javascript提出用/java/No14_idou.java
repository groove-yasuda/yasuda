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

		//マスタ接続設定
		RequestDispatcher disp = request.getRequestDispatcher("No14.jsp");
		//画面遷移
		disp.forward(request, response);
	}
}

//http://localhost:8080/11-14_yasuda/No13.jsp
