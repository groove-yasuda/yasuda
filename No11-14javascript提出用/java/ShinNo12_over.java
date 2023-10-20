import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShinNo12_over extends oyaNo implements inter{

	//Interface(入力チェック)
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
		//変数宣言
		List<String> teigilist = new ArrayList<String>();
		List<String> gainlist = new ArrayList<String>();
		teigilist.add("hantei");//0
		gainlist = sengen(request, teigilist);
		String newhantei = gainlist.get(0);//更新前か後か(0なら更新前、1なら更新後)
		String kousinHantei = "0";//jspに返す際に更新後にするかの変数
		String workId = null;//ID用変数
		String workName = null;//名前用の変数
		String maeId = "";//更新前ID用の変数
		String maeName = "";//更新前NAME用の変数
		int id_point = 0;
		int name_point = 0;
		int error = 0;
		//エラーメッセージ用のテーブル
		List<String> Messlist = new ArrayList<String>();

		List<String> kekkalist = new ArrayList<String>();
		List<String> getlist = new ArrayList<String>();
		try {
			if(newhantei == null)//更新前なら
			{
				getlist.add("syainID");//0
				getlist.add("syainNAME");//1
				kekkalist = sengen(request, getlist);
				workId = kekkalist.get(0);
				workName = kekkalist.get(1);
			}
			else if(newhantei.equals("1"))///更新最中なら
			{
				System.out.println("usiro");
				getlist.add("kaesiID");//0
				getlist.add("kaesiNAME");//1
				getlist.add("kousinID");//2
				getlist.add("kousinNAME");//3
				kekkalist = sengen(request, getlist);
				maeId = kekkalist.get(0);
				maeName = kekkalist.get(1);
				workId = kekkalist.get(2);
				workName = kekkalist.get(3);
			}
			System.out.println(kekkalist);

			Hashtable<String, Object> back = new Hashtable<String,Object>();
			String No = "12";
			int add = 0;
			String point = "0";//エラー表記用の変数
			Messlist = in_hantei(workId,workName);
			if(Messlist.contains("社員名の入力形式に誤りがあります")==true || Messlist.contains("社員IDの入力形式に誤りがあります")==true)
			{
				error = 1;
			}
			String OK = "0";
			//社員マスタの重複チェック
			int check = 0;//ID重複用の変数

			check = Sonzaicheck(workId, workName);
			List<String> list = new ArrayList<String>();

			if(newhantei == null)//更新前
			{
				if(check==0)//重複がない場合（更新前はIDありがtrueなので今回の場合はエラー）
				{
					list.add("1");
					error = 1;
				}
				else
				{
					list.add("2");
				}
			}
			else//更新後
			{
				System.out.println("kousingo");
				//社員マスタの重複チェック
				check = Sonzaicheck(workId);
				if(check>=1)//重複がある場合（更新最中は重複なしがtrueなので今回の場合はエラー）
				{
					list.add("1");
					error = 1;
				}
				else if(check == 0)
				{
					list.add("2");
				}
				kousinHantei = "1";
				back.put("kaesiH",kousinHantei);
			}

			if(newhantei == null)//更新前
			{
				if(Messlist.size() == 0)
				{
					if(list.contains("1") == true)
					{
						Messlist.add("社員IDが使用されていないか社員IDと社員名の組み合わせが違います");
						error = 1;
					}
					else if(list.contains("2") == true)
					{
						kousinHantei = "1";//更新後入力の為にjspに返す用
						back.put("kaesiH",kousinHantei);
					}
				}
				back.put("OK",OK);
				back.put("kaesi_kirikae",kousinHantei);
				back.put("kaesiID",workId);
				back.put("kaesiNAME",workName);
			}
			else//更新後
			{
				point = "1";
				if(Messlist.size() == 0)
				{
					if(list.contains("1") == true)//重複がある場合（更新最中は重複なしがtrueなので今回の場合はエラー）
					{
						Messlist.add("社員IDが使用されています");
						error = 1;
					}
					else if(list.contains("2") == true)
					{
						Messlist.add("登録されている社員ID："+maeId+"　　　社員名："+maeName+"　を");
						Messlist.add("更新する情報の社員ID："+workId+"　　　社員名："+workName+"　へ更新します");
						//jspに返す社員情報
						add = 1;
					}
				}
				back.put("kaesiID",maeId);
				back.put("kaesiNAME",maeName);
				back.put("hensinID",workId);
				back.put("hensinNAME",workName);
			}

			for (int i = 0; i < Messlist.size(); i++) {
 			if(Messlist.get(i) == "社員IDが未入力です" ||
 			Messlist.get(i) == "社員IDの入力形式に誤りがあります" ||
 			Messlist.get(i) == "社員IDが登録されています"||
 			Messlist.get(i) == "社員IDが使用されています"||
 			Messlist.get(i) == "社員IDが使用されていないか社員IDと社員名の組み合わせが違います")
 			{
 				id_point = 1;
 			}
 			if(Messlist.get(i) == "社員名が未入力です" ||
 			Messlist.get(i) == "社員名の入力形式に誤りがあります"||
 			Messlist.get(i) == "社員IDが使用されていないか社員IDと社員名の組み合わせが違います")
 			{
 				name_point = 1;
 			}
			}

			System.out.println("point="+error);
			System.out.println("workId="+workId);
			System.out.println("workName="+workName);
			back.put("kaesiP[]",Messlist);
			back.put("kaesiP",point);
			back.put("point",error);
			back.put("id_point",id_point);
			back.put("name_point",name_point);
			System.out.println(Messlist);
			kaesi(request,back);
			ikisaki(request,response,No,add);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	//オーバーライド
	public List<String> sonzaihantei(String workId,String workName,String newhantei) {
		List<String> list = new ArrayList<String>();
		try {
			junbi jun =new junbi();
			int check = 0;//ID重複用の変数

			check = jun.ID_NAMEsonzaihantei(workId, workName);
			if(newhantei.equals("0"))//更新前
			{
				if(check==0)//重複がない場合（更新前はIDありがtrueなので今回の場合はエラー）
				{
					list.add("1");
				}
				else
				{
					list.add("2");
				}
			}
			else//更新後
			{
				//社員マスタの重複チェック
				check = jun.IDsonzaihantei(workId);
				if(check>=1)//重複がある場合（更新最中は重複なしがtrueなので今回の場合はエラー）
				{
					list.add("1");
				}
				else if(check == 0)
				{
					list.add("2");
				}
			}
		}catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}

//http://localhost:8080/11-14_yasuda/No12.jsp
