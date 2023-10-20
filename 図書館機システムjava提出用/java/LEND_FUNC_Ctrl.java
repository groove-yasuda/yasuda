package com.example.demo;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LEND_FUNC_Ctrl {
	private JdbcTemplate jdbcTemplate;

	LEND_FUNC_Model LFModel = new LEND_FUNC_Model();

	//貸出画面の表示
	//引数:form … 画面からサーバに送信された情報
	//戻り値:mv … 変数mvに入っている画面遷移情報と変数
	@RequestMapping("/LEND_FUNC")
	public ModelAndView lending_Ctrl(@ModelAttribute PARAM_FORM form) {

		//変数宣言
		ModelAndView mv = new ModelAndView();
		COM_CLASS com = new COM_CLASS();
		int card_Number = form.getCard_Number();
		String user_Name = form.getUser_Name();
		String return_Confi = form.getReturn_Confi();
		String judge = "true";
		String name = "genre_Name";
		String number = "genre_Number";
		
		//judgeの値によって貸出ボタン押下時の処理か戻りボタン押下時の処理かを分ける
		if(return_Confi.equals(judge))
		{
			List<String> list = (List<String>) form.getList_Send();
			List<String> book_Data_List = com.return_Model(list);
			mv.addObject("book_Data_List", book_Data_List);
		}
		else
		{
			mv.addObject("book_Data_List", "1");
		}
		
		//ジャンル名、ジャンルコードの取得
		List<Map<String, Object>> genre_List = LFModel.srch_List_Genre_Model();
		
		//画面にジャンル名とジャンルコード,その他必要な情報を送信
		for(int i = 0;i < genre_List.size();i++)
		{
			Map<String, Object> genre = genre_List.get(i);
			mv.addObject("genre_Name_"+i+"", genre.get(name));
			mv.addObject("genre_Number_"+i+"", genre.get(number));
		}

		mv.addObject("card_Number", card_Number);
		mv.addObject("user_Name", user_Name);
		mv.addObject("function","lend");
		mv.addObject("judge", "0");
		mv.setViewName("lend_func");
		return mv;
	}



	//図書カード番号桁数チェックの実行
	//引数:form … 画面からサーバに送信された情報
	//戻り値:true or false
	@RequestMapping(value="/NUM_DIG_CHK", method = RequestMethod.POST)
	@ResponseBody
	public boolean cn_Dig_Chk_Ctrl(@RequestBody PARAM_FORM form)
	{
		//変数宣言
		LinkedHashMap number =(LinkedHashMap) form.getScrn_Var();

		//図書カード番号桁数チェック
		boolean result = LFModel.num_Dig_Chk_Model(number);

		return result;
	}


	//図書カード番号形式チェックの実行
	//引数:form … 画面からサーバに送信された情報
	//戻り値:true or false
	@RequestMapping(value="/NUM_FORM_CHK", method = RequestMethod.POST)
	@ResponseBody
	public boolean cn_Form_Chk_Ctrl(@RequestBody PARAM_FORM form)
	{
		//変数宣言
		LinkedHashMap number =(LinkedHashMap) form.getScrn_Var();

		//図書カード番号形式チェック
		boolean result = LFModel.num_Dig_Chk_Model(number);

		return result;
	}


	//子画面の呼び出し
	//引数:form … 画面からサーバに送信された情報
	//戻り値:画面遷移のURL
	@RequestMapping("/CHILD_SCRN")
	public ModelAndView  child_Screen(@ModelAttribute PARAM_FORM form)throws SQLException, ClassNotFoundException
	{
		//変数宣言
		ModelAndView mv = new ModelAndView();
		mv.addObject("book_Data_List", "1");
		mv.setViewName("srch_list");
		return mv;
	}	


	//検索画面押下時の処理
	//引数:form … 画面からサーバに送信された情報
	//戻り値:list … 検索結果
	@RequestMapping(value="/SRCH_LIST", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> srch_List_Ctrl(@RequestBody PARAM_FORM form)
	{
		//変数宣言
		LinkedHashMap number =(LinkedHashMap) form.getScrn_Var();
		String book_Number = (String) number.get("book_Number");
		String book_Name = (String) number.get("book_Name");
		String genre_Name = (String) number.get("genre_Name");
		
		//貸出書籍の検索書処理
		List<Map<String, Object>> list = LFModel.srch_List_Model(book_Number,book_Name,genre_Name);
		return list;
	}


	//貸出残り冊数チェック
	//引数:form … 画面からサーバに送信された情報
	//戻り値:availBooksCount … 貸出済の冊数
	@RequestMapping(value="/CHK_AVAIL", method = RequestMethod.POST)
	@ResponseBody
	public int chk_Avail_Ctrl(@RequestBody PARAM_FORM form)
	{
		//変数宣言
		LinkedHashMap number =(LinkedHashMap) form.getScrn_Var();

		//図書カード番号桁数チェック
		int availBooksCount = LFModel.chk_Avail_Model(number);

		return availBooksCount;
	}




	//貸出確認画面の表示
	//引数:form … 画面からサーバに送信された情報
	//戻り値:mv … 変数mvに入っている画面遷移情報と変数+
	@RequestMapping("/LEND_FUNC_CONFI")
	public ModelAndView  lending_Confirmation_Ctrl(@ModelAttribute PARAM_FORM form) 
	{

		//変数宣言
		ModelAndView mv = new ModelAndView();
		int card_Number = form.getCard_Number();
		String user_Name = form.getUser_Name();
		List<String> list = (List<String>) form.getList_Send();
		

		mv.addObject("card_Number", card_Number);
		mv.addObject("user_Name", user_Name);
		mv.addObject("list", list);
		mv.addObject("function", "confi");
		mv.addObject("judge", "1");
		mv.setViewName("lend_func");
		return mv;
	}


	//貸出完了画面の表示
	//引数:form … 画面からサーバに送信された情報
	//戻り値:mv … 変数mvに入っている画面遷移情報と変数
	@RequestMapping("/LEND_FUNC_FIN")
	public ModelAndView  lending_Fin_Ctrl(@ModelAttribute PARAM_FORM form) 
	{
		//変数宣言
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		ModelAndView mv = new ModelAndView();
		int card_Number = form.getCard_Number();
		String user_Name = form.getUser_Name();
		List<String> list = (List<String>) form.getList_Send();

		//書籍貸出状況チェック(重複があるか)
		int judge = LFModel.lending_Avail_Check(list,card_Number);
		if(judge == 0)
		{
			LocalDate current_Date = LocalDate.now();//現在の日付
			LocalDate future_Date = current_Date.plusDays(7);//7日後の日付
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String format_Date = future_Date.format(format);//表示する日付の型にする

			//書籍の貸出処理
			LFModel.lending_Fin_Model(list,format_Date,card_Number);

			// 日付を指定した形式で画面に表示する
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("返却期限は　MM月dd日　です");
			String formatted_Date = future_Date.format(formatter);
			mv.addObject("card_Number",card_Number);
			mv.addObject("user_Name",user_Name);
			mv.addObject("formatted_Date",formatted_Date);
			mv.addObject("function", "fin");
			mv.addObject("judge", "1");
			mv.setViewName("lend_func");
		}
		else if(judge != 0)//既に借りている書籍情報がある時(書籍情報の重複)
		{
			String elem = "選択された本の中に貸し出されている書籍があります";
			mv.addObject("card_Number",card_Number);
			mv.addObject("user_Name",user_Name);
			mv.addObject("list", list);
			mv.addObject("error_Mess",elem);
			mv.addObject("function", "confi");
			mv.addObject("judge", "1");
			mv.setViewName("lend_func");
		}
		return mv;
	}

}