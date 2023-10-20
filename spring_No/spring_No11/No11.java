package com.example.demo;

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
public class No11 extends parent_NoClass2{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8717936357217903023L;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping("/INSERT1")
	public ModelAndView  syainInsert(@ModelAttribute InsertSyainForm form) {
		String syainID = "";
		String syainNAME = "";
		String hantei = "";
		int add = 0;
		int id_point = 0;
		int name_point = 0;
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		hantei = form.gethantei();
		ModelAndView mv = new ModelAndView();
		List<String> messList = new ArrayList<String>();
		if((syainID == null && syainNAME == null) || hantei == "1" )
		{
			add = 0;
		}
		else if(syainID != "" && syainNAME != "")
		{
			id_point = inJudgeId(syainID,messList);
			name_point = inJudgeName(syainNAME,messList);
			if(messList.size() == 0)
			{
				messList.add("社員IDに"+syainID+"");
				messList.add("社員名に"+syainNAME+"　を登録します");
				add = 1;
			}
			mv.addObject("id_point",id_point);
			mv.addObject("name_point",name_point);
			mv.addObject("key01",syainID);
			mv.addObject("key02",syainNAME);
			mv.addObject("key03",messList);
			for(int i = 0;i<messList.size();i++)
			{
				mv.addObject("key0"+(4+i),messList.get(i));
			}
		}
		if(add == 0)
		{
			mv.setViewName("No11");
		}
		else
		{
			mv.setViewName("No11_kakunin");
		}
		return mv;
	}

	@RequestMapping("/KAKUNIN_11") 
	public ModelAndView kakuninScreen(@ModelAttribute InsertSyainForm form) {
		String syainID = "";
		String syainNAME = "";
		ModelAndView mv = new ModelAndView();
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		List<String> messList = new ArrayList<String>();
		envFile e = new envFile();
		setJdbcTemplate(e.jdbcTemplate());
		int check1 = inJudgeId(syainID,messList);
		if(check1 == 0)
		{
			//社員マスタに登録
			System.out.println("社員IDに"+syainID+"、社員名に"+syainNAME+"を登録します");
			Hashtable<String, Object> tourokuhensu = new Hashtable<String,Object>();
			tourokuhensu.put("key0", syainID);
			tourokuhensu.put("key1", syainNAME);
			dbAccess(tourokuhensu);
			mv.setViewName("No11_hozon");
			System.out.println("登録完了");
		}
		else
		{
			mv.addObject("key03",messList);
			for(int i = 0;i<messList.size();i++)
			{
				mv.addObject("key0"+(4+i),messList.get(i));
			}
			mv.setViewName("No11");
		}
		return mv;
	}

	@RequestMapping("/MODORI") 
	public ModelAndView modoriScreen(@ModelAttribute InsertSyainForm form) {
		String syainID = "";
		String syainNAME = "";
		ModelAndView mv = new ModelAndView();
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		mv.addObject("key01",syainID);
		mv.addObject("key02",syainNAME);
		System.out.println(syainID);
		System.out.println(syainNAME);
		mv.setViewName("No11");
		return mv;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
