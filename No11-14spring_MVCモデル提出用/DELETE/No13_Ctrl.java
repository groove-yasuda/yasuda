package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class No13_Ctrl extends parent_NoClass2  {
	No13_Model no13 = new No13_Model();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1792531920867335431L;

	//社員情報削除処理
	//引き数:InsertSyainForm.javaの変数
	//戻り値:変数mvに入っている画面遷移情報と変数
	@RequestMapping("/DELETE")
	public ModelAndView  delete(@ModelAttribute InsertSyainForm form) throws SQLException {
		//変数宣言
		String syainID = "";
		String syainNAME = "";
		int id_Judge = 0;//ID入力欄を赤くするかの判定用
		int name_Judge = 0;//NAME入力欄を赤くするかの判定用
		int div_screen_Judge = 1;//行先URLを変更する判定用
		int reception[];
		List<String> msgList = new ArrayList<String>();
		ModelAndView mv = new ModelAndView();
		
		try {
			//画面からの入力値受け取り
			syainID = form.getSyainID();
			syainNAME = form.getSyainNAME();

			//削除確認メッセージ追加判定
			reception = no13.msgAdd(syainID,syainNAME,msgList);
			id_Judge = reception[constants_No.ID_INDEX];
			name_Judge = reception[constants_No.NAME_INDEX];
			div_screen_Judge = reception[constants_No.DIV_SCREEN_INDEX]; 
			
			//画面への返却値設定の呼び出し
			scDataSend(syainID,syainNAME,id_Judge,name_Judge,mv,msgList);
			
			//画面出力メッセージ設定の呼び出し
			msgOut(msgList, mv);
			String dist = no13.destination(div_screen_Judge);
			mv.setViewName(dist);
			System.out.println(msgList);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return mv;
	}


	//社員情報削除確認
	//引き数:InsertSyainForm.javaの変数
	//戻り値:変数mvに入っている画面遷移情報と変数
	@RequestMapping("/DEL_CONF") 
	public ModelAndView delete_Confirm(@ModelAttribute InsertSyainForm form) {
		//変数宣言
		String syainID = "";
		String syainNAME = "";
		List<String> msgList = new ArrayList<String>();
		ModelAndView mv = new ModelAndView();
		
		//画面からの入力値受け取り
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		
		//DB削除処理&画面遷移URL設定の呼び出し
		no13.delete_Function(syainID,syainNAME,mv,msgList);

		return mv;
	}

	//戻るボタン押下時処理(初期画面[No.13]へ戻る)
	//引き数:InsertSyainForm.javaの変数
	//戻り値:変数mvに入っている画面遷移情報と変数
	@RequestMapping("/DEL_BACK") 
	public ModelAndView delete_back(@ModelAttribute InsertSyainForm form) {
		//変数宣言
		String syainID = "";
		String syainNAME = "";
		
		//画面からの入力値受け取り
		ModelAndView mv = new ModelAndView();
		syainID = form.getSyainID();
		
		//画面への返却値設定
		syainNAME = form.getSyainNAME();
		mv.addObject("key01",syainID);
		mv.addObject("key02",syainNAME);
		mv.setViewName("No13");
		return mv;
	}



}
