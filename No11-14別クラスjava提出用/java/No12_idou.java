import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class No12_idou extends HttpServlet{
	//doPostメソッドの引数？にあるHttpServletRequestクラス？の変数requestに画面情報？がある？的な
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		//文字コードの指定
		request.setCharacterEncoding("UTF-8");

		String jsp ="" ;//行先
		String hanteiJ = request.getParameter("hantei");//No12_choiceで選択したボタンのhantei読み取り

		if(hanteiJ.equals("1"))
		{
			jsp = "menu.jsp";//メニュー画面へ
		}
		else
		{
			jsp = "No12.jsp";//更新画面に戻る
		}

		//マスタ接続設定
		RequestDispatcher disp = request.getRequestDispatcher(jsp);
		//画面遷移
		disp.forward(request, response);

	}
}

//http://localhost:8080/11-14_yasuda/No11.jsp
