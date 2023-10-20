package com.example.demo;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class No14_Ctrl extends parent_NoClass2{
	No14_Model no14 = new No14_Model();
	/**
	 * 
	 */
	private static final long serialVersionUID = -6333738952170261078L;

	//社員情報検索処理
	//引き数:InsertSyainForm.javaの変数
	//戻り値:変数mvに入っている画面遷移情報と変数
	@RequestMapping("/SELECT")
	public ModelAndView  select(@ModelAttribute InsertSyainForm form) throws SQLException {
		//変数宣言
		String search = "";
		String condition = "";
		String prime_Rank = "";
		String ID = "";
		String NAME = "";
		int search_Judge = 0;//検索文字入力欄を赤くするかの判定用
		ModelAndView mv = new ModelAndView();
		List <String>searchList = new ArrayList<String>();

		//画面からの入力値受け取り
		search = form.getsearch();
		condition = form.getcon();
		prime_Rank = form.getprime_Rank();
		ID = form.getid_Order();
		NAME = form.getname_Order();

		//DB検索処理
		no14.select_Function(search, prime_Rank, condition, ID, NAME, searchList);

		//検索確認メッセージ追加判定
		search_Judge = no14.msgAdd(search, searchList);

		//画面への返却値設定
		no14.scDataSendSelect(search, search_Judge, mv, searchList);

		//画面出力メッセージ設定の呼び出し
		msgOut(searchList, mv);
		mv.setViewName("No14_Result");

		return mv;
	}

	//戻るボタン押下時処理(初期画面[No14]へ戻る)
	//引き数:InsertSyainForm.javaの変数
	//戻り値:変数mvに入っている画面遷移情報と変数
	@RequestMapping("/SEL_BACK") 
	public ModelAndView select_Back(@ModelAttribute InsertSyainForm form) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("No14");
		return mv;
	}

}
