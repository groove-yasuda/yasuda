package com.example.demo;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UI_IN_SCRN_Ctrl {

	UI_IN_SCRN_Model UIModel = new UI_IN_SCRN_Model();

	//利用者情報入力欄へ画面遷移
	//戻り値:画面遷移情報
	@RequestMapping("/UI_IN_SCRN") 
	public String menu_Screen() 
	{
		return "ui_in_scrn";
	}	
	

	//図書館システム選択画面へ遷移
	//引き数:paramForm.javaの変数
	//戻り値:変数mvに入っている画面遷移情報と変数
	@RequestMapping("/SEL_FUNC")
	public ModelAndView  insert_Ctrl(@ModelAttribute PARAM_FORM form) 
	{
		//変数宣言
		ModelAndView mv = new ModelAndView();
		int card_Number = form.getCard_Number();
		String user_Name = form.getUser_Name();
		
		//図書カード番号と利用者氏名の組み合わせチェック(存在しない:0,存在する:1)
		int judge = UIModel.insert_Model(card_Number, user_Name);


		//組み合わせチェックの結果によって処理を振り分ける
		if(judge == 1)
		{
			mv.addObject("card_Number",card_Number);
			mv.addObject("user_Name",user_Name);
			mv.setViewName("sel_func");
		}
		else
		{
			mv.setViewName("ui_in_scrn");
			mv.addObject("error_Mess","カード番号と利用者氏名の組み合わせが違います");
		}

		return mv;
	}

	//図書カード番号桁数チェックの実行
	//引数:form … 画面からサーバに送信された情報
	//戻り値:true or false
	@RequestMapping(value="/CN_DIG_CHK", method = RequestMethod.POST)
	@ResponseBody
	public boolean cn_Dig_Chk_Ctrl(@RequestBody PARAM_FORM form)
	{
		//画面で指定された値の受け取り
		LinkedHashMap number =(LinkedHashMap) form.getScrn_Var();

		//図書カード番号桁数チェック
		boolean result = UIModel.cn_Dig_Chk_Model(number);

		return result;
	}


	//図書カード番号存在チェックの実行
	//引数:form … 画面からサーバに送信された情報
	//戻り値:true or false
	@RequestMapping(value="/CN_EXIST_CHK", method = RequestMethod.POST)
	@ResponseBody
	public boolean cn_Exist_Chk_Ctrl(@RequestBody PARAM_FORM form)throws SQLException, ClassNotFoundException
	{
		//画面で指定された値の受け取り
		LinkedHashMap number =(LinkedHashMap) form.getScrn_Var();

		//図書カード番号存在チェック
		boolean result = UIModel.cn_Exist_Chk_Model(number);


		return result;
	}

	
	//利用者氏名桁数チェックの実行
	//引数:form … 画面からサーバに送信された情報
	//戻り値:true or false
	@RequestMapping(value="/UN_DIG_CHK", method = RequestMethod.POST)
	@ResponseBody
	public boolean un_Dig_Chk_Ctrl(@RequestBody PARAM_FORM form)
	{
		//画面で指定された値の受け取り
		LinkedHashMap number =(LinkedHashMap) form.getScrn_Var();

		//利用者氏名桁数チェック
		boolean result = UIModel.un_Dig_Chk_Model(number);

		return result;
	}
	

	//利用者氏名存在チェックの実行
	//引数:form … 画面からサーバに送信された情報
	//戻り値:true or false
	@RequestMapping(value="/UN_EXIST_CHK", method = RequestMethod.POST)
	@ResponseBody
	public boolean un_Exist_Chk_Ctrl(@RequestBody PARAM_FORM form)throws SQLException, ClassNotFoundException
	{
		//画面で指定された値の受け取り
		LinkedHashMap number =(LinkedHashMap) form.getScrn_Var();

		//利用者氏名存在チェック
		boolean result = UIModel.un_Exist_Chk_Model(number);

		return result;
	}

}


