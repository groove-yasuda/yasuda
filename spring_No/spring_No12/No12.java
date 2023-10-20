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
public class No12 extends parent_NoClass2 {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7609996593087623039L;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping("/UPDATE_mae")
	public ModelAndView  syainUpdate_mae(@ModelAttribute InsertSyainForm form) throws SQLException {
		String syainID = "";
		String syainNAME = "";
		int add = 0;
		int check = 0;
		int id_point = 0;
		int name_point = 0;
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		ModelAndView mv = new ModelAndView();
		List<String> messList = new ArrayList<String>();

		if(syainID != null && syainNAME != null)
		{
			id_point = inJudgeId_Exist(syainID,messList);
			name_point = inJudgeName(syainNAME,messList);
			check = existCheck(syainID, syainNAME, messList);
			if(messList.size() == 0)
			{
				if(check == 0)
				{
					messList.add("社員IDと社員名の組み合わせが違います");
				}
				else if(check == 1)
				{
					add = 1;
				}
			}
			mv.addObject("id_point",id_point);
			mv.addObject("name_point",name_point);
			mv.addObject("key01",syainID);
			mv.addObject("key02",syainNAME);
			if(messList.size()>0)
			{
				mv.addObject("key03",messList);
				for(int i = 0;i<messList.size();i++)
				{
					mv.addObject("key0"+(4+i),messList.get(i));
				}
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
	public ModelAndView  syainUpdate_ato(@ModelAttribute InsertSyainForm form) throws SQLException {
		String syainID = "";
		String syainNAME = "";
		String maeID = "";
		String maeNAME = "";
		int add = 0;
		int check = 0;
		int id_point = 0;
		int name_point = 0;
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		maeID = form.getmaeID();
		maeNAME = form.getmaeNAME();
		ModelAndView mv = new ModelAndView();
		List<String> messList = new ArrayList<String>();
		if(syainID != null && syainNAME != null)
		{
			id_point = inJudgeId(syainID,messList);
			name_point = inJudgeName(syainNAME,messList);
			check = existCheck(syainID, syainNAME, messList);
			if(messList.size() == 0 )
			{
				if(check == 0)
				{
					messList.add("登録されている社員ID："+maeID+"　　　社員名："+maeNAME+"　を");
					messList.add("更新する情報の社員ID："+syainID+"　　　社員名："+syainNAME+"　へ更新します");
					add = 1;
				}
				else if(check == 1)
				{
					messList.add("社員IDと社員名の組み合わせが違います");
				}
			}

			mv.addObject("id_point",id_point);
			mv.addObject("name_point",name_point);
			mv.addObject("mae01",maeID);
			mv.addObject("mae02",maeNAME);
			mv.addObject("ato01",syainID);
			mv.addObject("ato02",syainNAME);
			mv.addObject("key03",messList);
			for(int i = 0;i<messList.size();i++)
			{
				mv.addObject("key0"+(4+i),messList.get(i));
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
		int id_point = 0;
		List<String> messList = new ArrayList<String>();
		ModelAndView mv = new ModelAndView();
		try {

			int check = idExistJudge(maeID);
			int check1 = idExistJudge(syainID);
			System.out.println(maeID);
			System.out.println(syainID);
			if(check == 0)
			{
				messList.add("社員IDが登録されていません");
				mv.setViewName("No12_ato");
				id_point = 1;
			}
			else if(check1 > 0)
			{
				messList.add("社員IDが使用されています");
				mv.setViewName("No12_ato");
				id_point = 1;
			}
			else if(check == 1 && check1 ==0)
			{
				//社員マスタを更新
				System.out.println("登録されている社員ID："+maeID+"、社員名："+maeNAME+"に更新する情報の社員ID："+syainID+"、社員名："+syainNAME+"へ更新します");
				Hashtable<String, Object> kousinhensu = new Hashtable<String,Object>();
				kousinhensu.put("key0",syainID);
				kousinhensu.put("key1",syainNAME);
				dbAccess(kousinhensu,maeID,maeNAME);
				mv.setViewName("No12_hozon");
				System.out.println("登録完了");
			}
		}
		catch(Exception e)
		{
			e. printStackTrace();
			System.out.println("error");
		}
		mv.addObject("id_point",id_point);
		mv.addObject("mae01",maeID);
		mv.addObject("mae02",maeNAME);
		mv.addObject("ato01",syainID);
		mv.addObject("ato02",syainNAME);
		mv.addObject("key03",messList);
		for(int i = 0;i<messList.size();i++)
		{
			mv.addObject("key0"+(4+i),messList.get(i));
		}
		return mv;
	}

	public int inJudgeId_Exist(String syainID,List<String> messList) 
	{
		String idHantei = "";
		judge_Master jum = new judge_Master();
		int check = 0;

		if(syainID != "")//IDが入力されている
		{
			idHantei = jum.idJudge(syainID);
			if(idHantei.equals("false"))
			{
				messList.add("社員IDの入力形式に誤りがあります");//ID入力エラーメッセージ
				check = 1;
			}
		}
		else//IDが入力されてない
		{
			messList.add("社員IDが未入力です");//ID入力エラーメッセージ
			check = 1;
		}
		if(messList.size()==0)
		{
			int check1 = idExistJudge(syainID);
			if(check1 == 0)
			{
				messList.add("社員IDが登録されていません");
				check = 1;
			}
		}
		return check;
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
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		maeID = form.getmaeID();
		maeNAME = form.getmaeNAME();
		mv.addObject("ato01",syainID);
		mv.addObject("ato02",syainNAME);
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

}
