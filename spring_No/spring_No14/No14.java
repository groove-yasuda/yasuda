package com.example.demo;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class No14 extends parent_NoClass2{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6333738952170261078L;

	@RequestMapping("/SELECT")
	public ModelAndView  syainSelect(@ModelAttribute InsertSyainForm form) {
		String search = "";
		String con = "";
		String prime_Rank = "";
		search = form.getsearch();
		con = form.getcon();
		prime_Rank = form.getprime_Rank();
		ModelAndView mv = new ModelAndView();


		String search_Alt = "";//検索カラム指定の際に検索対象とは別のカラムを代入する変数
		String rank = "";//第一優先のカラム
		String rank_Alt = "";//第一優先ではないカラム
		String rank_Order0 = "";//第一優先のソート条件(昇順 or 降順)
		String rank_Order1 = "";//もう一つのソート条件(昇順 or 降順)
		int search_point = 0;
		String searchChara[] = {search};

		List <String>searchlist = new ArrayList<String>();
		String a ="";

		try {

			//検索対象の半角全角チェック準備

			if(search != "")//文字が入力されているとき
			{
				a = existCheck(searchChara);
				if(a == "true")//半角の時
				{
					search_Alt = "syainNAME";
				}
				else//全角の時
				{
					search_Alt = "syainID";
				}

				//検索判定
				if(prime_Rank.equals("syainID"))//第一優先がIDの時
				{
					rank = "syainID";
					rank_Alt = "syainNAME";
					rank_Order0 = form.getid_Order();
					rank_Order1 = form.getname_Order();
				}
				else//第一優先がNAMEの時
				{
					rank = "syainNAME";
					rank_Alt = "syainID";
					rank_Order0 = form.getname_Order();
					rank_Order1 = form.getid_Order();
				}

				Hashtable<String, Object> search_Variabul = new Hashtable<String,Object>();
				search_Variabul.put("key0",con);//検索条件(一致)
				search_Variabul.put("key1",search_Alt);//検索カラム(入力した文字の半角/全角でID/NAMEになる)
				search_Variabul.put("key2",searchChara[0]);//入力した文字
				search_Variabul.put("key3",rank);//第一優先のカラム
				search_Variabul.put("key4",rank_Alt);//第二優先のカラム

				Hashtable<String, Object> sortorder = new Hashtable<String,Object>();
				sortorder.put("key1",rank_Order0);//第一優先のソート順
				sortorder.put("key2",rank_Order1);//第二優先のソート順

				searchlist = dbAccess(search_Variabul,sortorder);

				if( searchlist.size() == 0 )
				{
					searchlist.add("検索結果が見つかりませんでした");
					search_point = 1;
				}
			}
			else
			{
				searchlist.add("検索対象が未入力です");
				search_point = 1;
			}

			mv.addObject("key01",search);
			mv.addObject("key02",search_point);
			mv.addObject("key03",searchlist);
			for(int i = 0;i<searchlist.size();i++)
			{
			mv.addObject("key0"+(4+i),searchlist.get(i));
			}
			mv.setViewName("No14_result");
		}
		catch(Exception e)
		{
			e. printStackTrace();
			System.out.println("error");
		}
	
		return mv;
	}
	
	 @RequestMapping("/MODORI14") 
	 public ModelAndView modori14Screen(@ModelAttribute InsertSyainForm form) {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("No14");
		 return mv;
	 }
	
}
