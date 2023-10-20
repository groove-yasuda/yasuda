package com.example.demo;
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
public class RET_FUNC_Ctrl {
	private JdbcTemplate jdbcTemplate;
	RET_FUNC_Model RFModel = new RET_FUNC_Model();
	LEND_FUNC_Model LFModel = new LEND_FUNC_Model();
	
	
	//返却画面の表示
	//引数:form … 画面からサーバに送信された情報
	//戻り値:mv … 変数mvに入っている画面遷移情報と変数
	@RequestMapping("/RET_FUNC")
	public ModelAndView ret(@ModelAttribute PARAM_FORM form) {
		ModelAndView mv = new ModelAndView();
		int card_Number = form.getCard_Number();
		String user_Name = form.getUser_Name();
		
		//貸出状況マスタから利用者の借りている書籍を検索
		List<Map<String, Object>> list = (List<Map<String, Object>>) RFModel.return_List_Model(card_Number);
		
		//書籍情報をJson形式に変換
		String json = RFModel.return_Json_Model_Object(list);
		
		mv.addObject("card_Number", card_Number);
		mv.addObject("user_Name", user_Name);
		mv.addObject("function","return");
		mv.addObject("list",json);
		mv.addObject("judge", "0");
		mv.setViewName("ret_func");
		return mv;
	}
	
	
	//書籍貸出チェック
	//引数:form … 画面からサーバに送信された情報
	//戻り値:true or false
	@RequestMapping(value="/BOOK_AVAIL_CHK", method = RequestMethod.POST)
	@ResponseBody
	public boolean book_Avail_Chk_Ctrl(@RequestBody PARAM_FORM form)
	{
		//変数宣言
		LinkedHashMap number =(LinkedHashMap) form.getScrn_Var();
		
		//図書カード番号桁数チェック
		boolean result = LFModel.num_Dig_Chk_Model(number);
		
		return result;
	}
	
	
	//貸出残り冊数チェック
	//引数:form … 画面からサーバに送信された情報
	//戻り値:availBooksCount … 貸出済の冊数
	@RequestMapping(value="/CHK_AVAIL_LEND", method = RequestMethod.POST)
	@ResponseBody
	public int chk_Avail_Lend_Ctrl(@RequestBody PARAM_FORM form)
	{
		//変数宣言
		LinkedHashMap number =(LinkedHashMap) form.getScrn_Var();

		//図書カード番号桁数チェック
		int avail_Books = RFModel.chk_Avail_Lend_Model(number);

		return avail_Books;
	}
	
	
	//返却確認画面の表示
	//引数:form … 画面からサーバに送信された情報
	//戻り値:mv … 変数mvに入っている画面遷移情報と変数
	@RequestMapping("/RET_FUNC_CONFI")
	public ModelAndView ret_Func_Confi(@ModelAttribute PARAM_FORM form) {
		ModelAndView mv = new ModelAndView();
		int card_Number = form.getCard_Number();
		String user_Name = form.getUser_Name();
		List<String> data_Send = form.getData_Send();
		
		mv.addObject("card_Number", card_Number);
		mv.addObject("user_Name", user_Name);
		mv.addObject("function","confi");
		mv.addObject("list", data_Send );
		
		mv.setViewName("ret_func");
		return mv;
	}
	
	
	//返却完了画面の表示
	//引数:form … 画面からサーバに送信された情報
	//戻り値:mv … 変数mvに入っている画面遷移情報と変数
	@RequestMapping("/RET_FUNC_FIN")
	public ModelAndView ret_Func_Fin(@ModelAttribute PARAM_FORM form) {
		//変数宣言
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		ModelAndView mv = new ModelAndView();
		int card_Number = form.getCard_Number();
		String user_Name = form.getUser_Name();
		List<String> list = (List<String>) form.getList_Send();
		
		//書籍貸出状況チェック(重複があるか)
		boolean judge = RFModel.ret_Avail_Check(list,card_Number);
		if(judge == true)
		{
			//書籍の貸出処理
			RFModel.ret_Fin_Model(list,card_Number);

			// 日付を指定した形式で画面に表示する
			mv.addObject("card_Number",card_Number);
			mv.addObject("user_Name",user_Name);
			mv.addObject("function", "fin");
			mv.addObject("judge", "1");
			mv.setViewName("ret_func");
		}
		else if(judge == false)//既に借りている書籍情報がある時(書籍情報の重複)
		{
			String elem = "選択された本の中に返却されている書籍があります";
			mv.addObject("card_Number",card_Number);
			mv.addObject("user_Name",user_Name);
			mv.addObject("list", list);
			mv.addObject("error_Mess",elem);
			mv.addObject("function", "confi");
			mv.addObject("judge", "1");
			mv.setViewName("ret_func");
		}
		return mv;
	}
	
	
}