import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class oyaNo extends HttpServlet{


	public void kinou ()
	{

	}





	public List<String> in_hantei(String workId,String workName) {

		String IDhantei = "";
		String NAMEhantei = "";

		List <String>list = new ArrayList<String>();
		junbi jun = new junbi();

		if(workId != "")//IDが入力されている
		{
			IDhantei = jun.IDhantei(workId);
			if(IDhantei.equals("false"))
			{
				list.add("社員IDの入力形式が間違っています");//ID入力エラーメッセージ
				list.add("1");
			}
		}
		else//IDが入力されてない
		{
			list.add("社員IDが未入力です");//ID入力エラーメッセージ
			list.add("1");
		}

		if(workName != "")//NAMEが入力されてる
		{
			NAMEhantei = jun.NAMEhantei(workName);
			if(NAMEhantei.equals("false"))//NAME入力エラーチェック
			{
				list.add("社員名の入力形式が間違っています");//NAME入力エラーメッセージ
				list.add("1");
			}
		}
		else
		{
			list.add("社員名が未入力です");//ID入力エラーメッセージ
			list.add("1");
		}
		return list;
	}

	public List<String> sonzaihantei(String workId,String workName){
		List <String>list = new ArrayList<String>();
		junbi jun = new junbi();
		if(workId != "" && workName != "")
		{
			//社員マスタの重複チェック
			int check1;
			try {
				check1 = jun.IDsonzaihantei(workId);
				if(check1>0)
				{
					list.add("社員IDが登録されています");//ID存在エラーメッセージ
					list.add("2");
				}
				else if (check1 == 0)
				{
					list.add("社員IDに"+workId+"");
					list.add("社員名に"+workName+"　を登録します");
				}
			} catch (SQLException | ClassNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		return list;
	}

	public Hashtable<String, String> sengen(HttpServletRequest request,List<String> list,Hashtable<String, String> hshi){
		Hashtable<String, String> hsh = new Hashtable<String,String>();
		for(int i = 0; i<list.size(); i++ ) {//listで送る場合はlistの項目分繰り返す
			hsh.put(hshi.get("key"+i+""),request.getParameter((String) list.get(i)));//検索する文字用変数
		}
		return hsh;
	}


	public Hashtable<String, String> kaesi(HttpServletRequest request,List<String>list,List<Object> list1){
		Hashtable<String, String> hsh = new Hashtable<String,String>();
		for(int i = 0; i<list.size(); i++ ) {//listで送る場合はlistの項目分繰り返す
			request.setAttribute((String) list.get(i),list1.get(i));//検索する文字用変数
			System.out.println(list1.get(i));
		}
		return hsh;
	}


	public String ikisaki(String jsp,int add){
		String basyo = "";
		if(jsp.equals("11"))
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
		else if(jsp.equals("12"))
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
		else if(jsp.equals("13"))
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
		else if(jsp.equals("14"))
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
		return basyo;
	}



}



//http://localhost:8080/11-14_yasuda/No11.jsp
