import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShinNo11_over extends oyaNo  implements inter{


	//Interface(入力チェック)
	public List<String> in_hantei(String workId,String workName) {
		String IDhantei = "";
		String NAMEhantei = "";

		//Interface(入力チェック)
		List <String>list = new ArrayList<String>();
		junbi jun = new junbi();

		if(workId != "")//IDが入力されている
		{
			IDhantei = jun.IDhantei(workId);
			if(IDhantei.equals("false"))
			{
				list.add("社員IDの入力形式に誤りがあります");//ID入力エラーメッセージ
			}
		}
		else//IDが入力されてない
		{
			list.add("社員IDが未入力です");//ID入力エラーメッセージ
		}

		if(workName != "")//NAMEが入力されてる
		{
			NAMEhantei = jun.NAMEhantei(workName);
			if(NAMEhantei.equals("false"))//NAME入力エラーチェック
			{
				list.add("社員名の入力形式に誤りがあります");//NAME入力エラーメッセージ
			}
		}
		else
		{
			list.add("社員名が未入力です");//ID入力エラーメッセージ
		}
		return list;
	}

	//Interface(忘れ防止)
	public int yarukoto()
	{
		return 0;
	}


	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException{
		//文字コードの指定
		request.setCharacterEncoding("UTF-8");

		List<String> kekkalist = new ArrayList<String>();
		List<String> getlist = new ArrayList<String>();
		getlist.add("syainID");//0
		getlist.add("syainNAME");//1
		kekkalist = sengen(request, getlist);
		String workId = kekkalist.get(0);
		String workName = kekkalist.get(1);
		String point = "0";
		String No = "11";
		int add = 0;
		int id_point = 0;
		int name_point = 0;

		//エラーメッセージ用のテーブル
		List<String> Messlist = new ArrayList<String>();
		Messlist = in_hantei(workId,workName);

		if(Messlist.size() == 0)
		{
			if(workId != "" && workName != "")
			{
				//社員マスタの重複チェック
				int check1;
				try {
					check1 = Sonzaicheck(workId);
					if(check1>0)
					{
						Messlist.add("社員IDが登録されています");//ID存在エラーメッセージ
					}
					else if (check1 == 0)
					{
						Messlist.add("社員IDに"+workId+"");
						Messlist.add("社員名に"+workName+"　を登録します");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(Messlist.contains("社員IDに"+workId+"") == true)
			{
				add = 1;
			}
		}
		for (int i = 0; i < Messlist.size(); i++) {
		if(Messlist.contains("社員IDが未入力です")==true ||
		Messlist.contains("社員IDの入力形式に誤りがあります")==true ||
		Messlist.contains("社員IDが登録されています")==true)
		{
			id_point = 1;
			point = "1";
		}
		if(Messlist.contains("社員名が未入力です")==true ||
		Messlist.contains("社員名の入力形式に誤りがあります")==true)
		{
			name_point = 1;
			point = "1";
		}
		}
		System.out.println("id_point="+id_point);
		System.out.println("name_point="+name_point);
		Hashtable<String, Object> back = new Hashtable<String,Object>();
		back.put("kaesiP[]",Messlist);
		back.put("kaesiP",point);
		back.put("kaesiID",workId);
		back.put("kaesiNAME",workName);
		back.put("release",No);
		back.put("point",point);
		back.put("id_point",id_point);
		back.put("name_point",name_point);
		System.out.println(Messlist);
		kaesi(request,back);

		ikisaki(request,response,No,add);
}


	//Interface(画面遷移)
	public void ikisaki(HttpServletRequest request,HttpServletResponse response,String No,int add){

		String basyo = "";

		if(No.equals("11"))
		{
			if(add == 0)
			{
			basyo = "No11.jsp";
			}
			else
			{
			basyo = "No11_kakunin.jsp";
			}
		}
		else if(No.equals("12"))
		{
			if(add == 0)
			{
			basyo = "No12.jsp";
			}
			else
			{
			basyo = "No12_hozon.jsp";
			}
		}
		else if(No.equals("13"))
		{
			if(add == 0)
			{
			basyo = "No13.jsp";
			}
			else
			{
			basyo = "No13_delete.jsp";
			}
		}
		else if(No.equals("14"))
		{
			if(add == 0)
			{
			basyo = "No14.jsp";
			}
			else
			{
			basyo = "No14_result.jsp";
			}
		}
		try {
		//返却先の指定[今回は("No11.jsp")]
		RequestDispatcher disp = request.getRequestDispatcher(basyo);
		//画面遷移
			disp.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}

//http://localhost:8080/11-14_yasuda/No11.jsp
