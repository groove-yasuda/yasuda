package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class No13 extends parent_NoClass2  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1792531920867335431L;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@RequestMapping("/DELETE")
	public ModelAndView  syainDelete(@ModelAttribute InsertSyainForm form) throws SQLException {
		String syainID = "";
		String syainNAME = "";
		int add = 0;
		int id_point = 0;
		int name_point = 0;
		int huriwake = 0;
		int check = 0;
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		ModelAndView mv = new ModelAndView();
		System.out.println(syainID);

		List<String> messList = new ArrayList<String>();
		if(syainID != null && syainNAME != null)
		{
			id_point = inJudgeId(syainID,messList);
			name_point = inJudgeName(syainNAME,messList);
			check = existCheck(syainID, syainNAME, messList);
			if(messList.size() == 0)
			{
				if(check == 0)
				{
					messList.add("社員IDと社員名の組み合わせが違います");
					id_point = 1;
					name_point = 1;
				}
				else if(check == 1)
				{
					messList.add("削除する社員ID："+syainID+"");
					messList.add("削除する社員名："+syainNAME+"");
					add = 1;
				}
			}
			mv.addObject("id_point",id_point);
			mv.addObject("name_point",name_point);
			mv.addObject("key03",messList);
			for(int i = 0;i<messList.size();i++)
			{
				mv.addObject("key0"+(4+i),messList.get(i));
			}

			if(huriwake == 0)
			{
				mv.addObject("key01",syainID);
				mv.addObject("key02",syainNAME);
			}
			else if(huriwake == 1)
			{
				String kara = "";
				mv.addObject("key01",syainID);
				mv.addObject("key02",kara);
			}
			else if(huriwake == 2)
			{
				String kara = "";
				mv.addObject("key01",kara);
				mv.addObject("key02",syainNAME);
			}
		}
		if(add == 0)
		{
			mv.setViewName("No13");
		}
		else
		{
			mv.setViewName("No13_kakunin");
		}

		return mv;
	}
	@RequestMapping("/KAKUNIN_13") 
	public ModelAndView kakuninScreen(@ModelAttribute InsertSyainForm form) {
		String syainID = "";
		String syainNAME = "";
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		List<String> list = new ArrayList<String>();
		ModelAndView mv = new ModelAndView();
		try {
			//社員マスタの重複チェック

			int check = idExistJudge(syainID);
			System.out.println("削除確認");
			if(check==0)//重複がない場合（重複がある場合がtrueなので今回はfalse）
			{
				list.add("社員IDが使用されていません");
				mv.setViewName("No13");
			}
			else
			{
				String[] data = { syainID,syainNAME};
				//社員マスタから削除
				dbAccess(data);
				System.out.println("登録されている社員ID："+ syainID +"、社員名："+ syainNAME +"を社員マスタから削除します");
				mv.setViewName("No13_delete");
			}
		}
		catch(Exception e)
		{
			e. printStackTrace();
			System.out.println("error");
		}
		finally
		{
			System.out.println("削除完了");
		}
		return mv;
	}

	@RequestMapping("/MODORI13") 
	public ModelAndView modori13Screen(@ModelAttribute InsertSyainForm form) {
		String syainID = "";
		String syainNAME = "";
		ModelAndView mv = new ModelAndView();
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		mv.addObject("key01",syainID);
		mv.addObject("key02",syainNAME);
		System.out.println(syainID);
		System.out.println(syainNAME);
		mv.setViewName("No13");
		return mv;
	}

	public int inJudgeId(String syainID,List<String> messList) 
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
		int check1 = idExistJudge(syainID);
		if(check1 == 0)
		{
			messList.add("社員IDが登録されていません");
			check = 1;
		}
		return check;
	}


	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
