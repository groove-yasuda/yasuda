import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class No12_idou extends oyaNo {

	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		//文字コードの指定
		request.setCharacterEncoding("UTF-8");
		Hashtable<String, Object> back = new Hashtable<String,Object>();
		String kaesiH = request.getParameter("kaesiH");
		String kaesi = request.getParameter("kaesi");
		int i = 1;

		if(kaesi != null)
		{
			if(kaesi.equals("0"))
			{
				String ID = request.getParameter("kaesiID");
				String NAME = request.getParameter("kaesiNAME");
				back.put("kaesiID",ID);
				back.put("kaesiNAME",NAME);
				System.out.println("ID="+ID);
			}
			else if(kaesi.equals("1"))
			{
				String ID_before = request.getParameter("kaesiID");
				String NAME_before = request.getParameter("kaesiNAME");
				String ID_after = request.getParameter("hensinID");
				String NAME_after = request.getParameter("hensinNAME");
				back.put("kaesiID",ID_before);
				back.put("kaesiNAME",NAME_before);
				back.put("hensinID",ID_after);
				back.put("hensinNAME",NAME_after);
				back.put("kaesiH",kaesiH);
				i = 2;
			}
			System.out.println(kaesiH);
			back.put("release",i);
			kaesi(request,back);
			System.out.println(back);
		}


		//マスタ接続設定
		RequestDispatcher disp = request.getRequestDispatcher("No12.jsp");
		//画面遷移
		disp.forward(request, response);

	}
}

//http://localhost:8080/11-14_yasuda/No11.jsp
