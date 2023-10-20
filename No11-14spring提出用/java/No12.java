package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class No12 extends oyaNo  implements inter{
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
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping("/UPDATE_mae")
	public ModelAndView  syainInsert_mae(@ModelAttribute InsertSyainForm form) {
		String syainID = "";
		String syainNAME = "";
		String hantei = "";
		String newhantei = "";
		String kousinHantei = "0";
		String maeID = "";
		String maeNAME = "";
		int error = 0;
		int add = 0;
		int id_point = 0;
		int name_point = 0;
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		newhantei = form.gethantei();
		ModelAndView mv = new ModelAndView();
		List<String> Messlist = new ArrayList<String>();

		if(syainID != null && syainNAME != null)
		{
			Messlist = in_hantei(syainID,syainNAME);
			if(Messlist.contains("社員名の入力形式に誤りがあります")==true || Messlist.contains("社員IDの入力形式に誤りがあります")==true)
			{
				error = 1;
			}
			String OK = "0";
			//社員マスタの重複チェック
			int check = 0;//ID重複用の変数

			try {
				check = Sonzaicheck(syainID, syainNAME);
			} catch (SQLException e2) {
				// TODO 自動生成された catch ブロック
				e2.printStackTrace();
			}
			List<String> list = new ArrayList<String>();

			if(check==0)//重複がない場合（更新前はIDありがtrueなので今回の場合はエラー）
			{
				list.add("1");
				error = 1;
			}
			else
			{
				list.add("2");
			}

			if(Messlist.size() == 0)
			{
				if(list.contains("1") == true)
				{
					Messlist.add("社員IDが使用されていないか社員IDと社員名の組み合わせが違います");
					error = 1;
				}
				else if(list.contains("2") == true)
				{
					kousinHantei = "1";
					add = 1;
				}
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

			System.out.println("kousinHantei="+kousinHantei);

			mv.addObject("id_point",id_point);
			mv.addObject("name_point",name_point);
			mv.addObject("key01",syainID);
			mv.addObject("key02",syainNAME);
			mv.addObject("key03",Messlist);
			mv.addObject("kousin",kousinHantei);
			for(int i = 0;i<Messlist.size();i++)
			{
				mv.addObject("key0"+(4+i),Messlist.get(i));
			}
		}

		if(add == 0)
		{
			mv.setViewName("No12_mae");
		}
		else
		{
			mv.setViewName("No12_ato");
		}

		envFile e = new envFile();
		setJdbcTemplate(e.jdbcTemplate());
		return mv;
	}

	@RequestMapping("/UPDATE_ato")
	public ModelAndView  syainInsert_ato(@ModelAttribute InsertSyainForm form) {
		String syainID = "";
		String syainNAME = "";
		String hantei = "";
		String newhantei = "";
		String kousinHantei = "0";
		String maeID = "";
		String maeNAME = "";
		int error = 0;
		int add = 0;
		int id_point = 0;
		int name_point = 0;
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		maeID = form.getmaeID();
		maeNAME = form.getmaeNAME();
		newhantei = form.gethantei();
		ModelAndView mv = new ModelAndView();
		List<String> Messlist = new ArrayList<String>();

		if(syainID != null && syainNAME != null)
		{
			Messlist = in_hantei(syainID,syainNAME);
			if(Messlist.contains("社員名の入力形式に誤りがあります")==true || Messlist.contains("社員IDの入力形式に誤りがあります")==true)
			{
				error = 1;
			}
			//社員マスタの重複チェック
			int check = 0;//ID重複用の変数

			try {
				check = Sonzaicheck(syainID, syainNAME);
			} catch (SQLException e2) {
				// TODO 自動生成された catch ブロック
				e2.printStackTrace();
			}
			List<String> list = new ArrayList<String>();

			//社員マスタの重複チェック
			try {
				check = Sonzaicheck(syainID);
			} catch (SQLException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
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
			if(Messlist.size() == 0)
			{
				if(list.contains("1") == true)//重複がある場合（更新最中は重複なしがtrueなので今回の場合はエラー）
				{
					Messlist.add("社員IDが使用されています");
					error = 1;
				}
				else if(list.contains("2") == true)
				{
					Messlist.add("登録されている社員ID："+maeID+"　　　社員名："+maeNAME+"　を");
					Messlist.add("更新する情報の社員ID："+syainID+"　　　社員名："+syainNAME+"　へ更新します");
					add = 1;
				}
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

			System.out.println("kousinHantei="+kousinHantei);

			mv.addObject("id_point",id_point);
			mv.addObject("name_point",name_point);
			mv.addObject("mae01",maeID);
			mv.addObject("mae02",maeNAME);
			mv.addObject("key01",syainID);
			mv.addObject("key02",syainNAME);
			mv.addObject("key03",Messlist);
			mv.addObject("kousin",kousinHantei);
			for(int i = 0;i<Messlist.size();i++)
			{
				mv.addObject("key0"+(4+i),Messlist.get(i));
			}
		}

		if(add == 0)
		{
			mv.setViewName("No12_ato");
		}
		else
		{
			mv.setViewName("No12_kakunin");
		}

		envFile e = new envFile();
		setJdbcTemplate(e.jdbcTemplate());
		return mv;
	}

	@RequestMapping("/KAKUNIN_12") 
	public ModelAndView kakuninScreen(@ModelAttribute InsertSyainForm form) {
		String syainID = "";
		String syainNAME = "";
		String maeID = "";
		String maeNAME = "";
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		maeID = form.getmaeID();
		maeNAME = form.getmaeNAME();
		List<String> list = new ArrayList<String>();
		ModelAndView mv = new ModelAndView();
		try {

			int check = IDSonzaiHantei(maeID);
			int check1 = IDSonzaiHantei(syainID);
			System.out.println(maeID);
			System.out.println(syainID);
			if(check == 0)
			{
				list.add("社員IDが登録されていません");
				mv.setViewName("No12_ato");
				System.out.println("あ");
			}
			else if(check1 > 0)
			{
				list.add("社員IDが使用されています");
				mv.setViewName("No12_ato");
				System.out.println("い");
			}
			else if(check == 1 && check1 ==0)
			{
				//社員マスタを更新
				System.out.println("登録されている社員ID："+maeID+"、社員名："+maeNAME+"に更新する情報の社員ID："+syainID+"、社員名："+syainNAME+"へ更新します");
				Hashtable<String, Object> kousinhensu = new Hashtable<String,Object>();
				kousinhensu.put("key0",syainID);
				kousinhensu.put("key1",syainNAME);
				DBaccess(kousinhensu,maeID,maeNAME);
				mv.setViewName("No12_hozon");
				System.out.println("登録完了");
			}
		}
		catch(Exception e)
		{
			e. printStackTrace();
			System.out.println("error");
		}
		finally
		{
		}
		return mv;
	}

	@RequestMapping("/MODORI12") 
	public ModelAndView modoriScreen(@ModelAttribute InsertSyainForm form) {
		String maeID = "";
		String maeNAME = "";
		ModelAndView mv = new ModelAndView();
		maeID = form.getmaeID();
		maeNAME = form.getmaeNAME();
		mv.addObject("key01",maeID);
		mv.addObject("key02",maeNAME);
		System.out.println(maeID);
		System.out.println(maeNAME);
		mv.setViewName("No12_mae");
		return mv;
	}
	
	@RequestMapping("/MODORI12_ato") 
	public ModelAndView modori_atoScreen(@ModelAttribute InsertSyainForm form) {
		String maeID = "";
		String maeNAME = "";
		String syainID = "";
		String syainNAME = "";
		ModelAndView mv = new ModelAndView();
		syainID = form.getmaeID();
		syainNAME = form.getmaeNAME();
		syainID = form.getmaeID();
		maeNAME = form.getmaeNAME();
		mv.addObject("key01",syainID);
		mv.addObject("key02",syainNAME);
		mv.addObject("mae01",maeID);
		mv.addObject("mae02",maeNAME);
		System.out.println(syainID);
		System.out.println(maeNAME);
		mv.setViewName("No12_ato");
		return mv;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int yarukoto() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}
}
