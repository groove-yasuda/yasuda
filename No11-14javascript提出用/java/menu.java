import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class menu extends HttpServlet{
	//doPostメソッドの引数？にあるHttpServletRequestクラス？の変数requestに画面情報？がある？的な
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		//文字コードの指定
		request.setCharacterEncoding("UTF-8");
		Sub.jsp ="" ;
		String hanteiJ = request.getParameter("hantei");

		if(hanteiJ.equals("0"))//hanteiが0の時は登録
		{
			Sub.jsp = "No11.jsp";//社員情報登録画面へ
		}
		else if(hanteiJ.equals("1"))//hanteiが1の時は更新
		{
			Sub.jsp = "No12.jsp";//社員情報更新画面へ
		}
		else if(hanteiJ.equals("2"))//hanteiが２の時は削除
		{
			Sub.jsp = "No13.jsp";//社員情報削除画面へ
		}
		else if(hanteiJ.equals("3"))//hanteiが3の時は検索
		{
			Sub.jsp = "No14.jsp";//社員情報検索画面へ
		}

		//マスタ接続設定
		RequestDispatcher disp = request.getRequestDispatcher(Sub.jsp);
		//画面遷移
		disp.forward(request, response);

	}
}
class Sub {
	public static final String[] hantei = null;
	private static int num;
	static int check1;
	public static String workId;
	public static String workName;
	public static String jsp;

	public static int getNum() {
		return num;
	}
}
//http://localhost:8080/11-14_yasuda/No11.jsp
