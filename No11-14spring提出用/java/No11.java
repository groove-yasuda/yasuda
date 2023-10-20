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
public class No11 extends oyaNo  implements inter{
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
		
		List<String> Messlist = new ArrayList<String>();
		if((syainID == null && syainNAME == null) || hantei == "1" )
		{
			add = 0;
		}
		else
		{
		Messlist = in_hantei(syainID,syainNAME);
		
		if(Messlist.size() == 0)
		{
			if(syainID != "" && syainNAME != "")
			{
				//社員マスタの重複チェック
				int check1;
				try {
					check1 = Sonzaicheck(syainID);
					if(check1>0)
					{
						Messlist.add("社員IDが登録されています");//ID存在エラーメッセージ
					}
					else if (check1 == 0)
					{
						Messlist.add("社員IDに"+syainID+"");
						Messlist.add("社員名に"+syainNAME+"　を登録します");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(Messlist.contains("社員IDに"+syainID+"") == true)
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
		}
		if(Messlist.contains("社員名が未入力です")==true ||
		Messlist.contains("社員名の入力形式に誤りがあります")==true)
		{
			name_point = 1;
		}
		}

		System.out.println(add);

		mv.addObject("id_point",id_point);
		mv.addObject("name_point",name_point);
		mv.addObject("key01",syainID);
		mv.addObject("key02",syainNAME);
		mv.addObject("key03",Messlist);
		for(int i = 0;i<Messlist.size();i++)
		{
		mv.addObject("key0"+(4+i),Messlist.get(i));
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
		
		envFile e = new envFile();
		setJdbcTemplate(e.jdbcTemplate());
		
		return mv;
		
	}
	
	 @RequestMapping("/KAKUNIN_11") 
	 public ModelAndView kakuninScreen(@ModelAttribute InsertSyainForm form) {
			String syainID = "";
			String syainNAME = "";
			ModelAndView mv = new ModelAndView();
			syainID = form.getSyainID();
			syainNAME = form.getSyainNAME();
		 List<String> Messlist = new ArrayList<String>();

			try {
				int check1 = IDSonzaiHantei(syainID);

				if(check1>0)
				{
					Messlist.add("社員IDが使用されています");
					mv.setViewName("No11");
				}
				else
				{
					//社員マスタに登録
					System.out.println("社員IDに"+syainID+"、社員名に"+syainNAME+"を登録します");
					Hashtable<String, Object> tourokuhensu = new Hashtable<String,Object>();
					tourokuhensu.put("key0", syainID);
					tourokuhensu.put("key1", syainNAME);
					DBaccess(tourokuhensu);
					mv.setViewName("No11_hozon");
				}
			}
			catch(Exception e)
			{
				e. printStackTrace();
				System.out.println("error");
			}
			finally
			{
				System.out.println("登録完了");
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

	@Override
	public int yarukoto() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}
}
