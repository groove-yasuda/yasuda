package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class No12_Ctrl extends parent_NoClass2 {
	No12_Model no12 = new No12_Model();
	/**
	 * 
	 */
	private static final long serialVersionUID = -7609996593087623039L;


	//社員情報更新前処理
	//引き数:InsertSyainForm.javaの変数
	//戻り値:変数mvに入っている画面遷移情報と変数
	@RequestMapping("/UPD_BF")
	public ModelAndView  update_Before(@ModelAttribute InsertSyainForm form) throws SQLException {
		//変数宣言
		String syainID = "";
		String syainNAME = "";
		int id_Judge = 0;//ID入力欄を赤くするかの判定用
		int name_Judge = 0;//NAME入力欄を赤くするかの判定用
		int div_screen_Judge = 0;//行先URLを変更する判定用
		int reception[];//id_Judge,name_judge,div_screen_Judgeの設定用
		ModelAndView mv = new ModelAndView();
		List<String> msgList = new ArrayList<String>();
		
		try {
			//画面からの入力値受け取り
			syainID = form.getSyainID();
			syainNAME = form.getSyainNAME();
			
			//更新前確認コメント追加判定
			reception = no12.msgAdd_Before(syainID,syainNAME,msgList);
			id_Judge = reception[constants_No.ID_INDEX];
			name_Judge = reception[constants_No.NAME_INDEX];
			div_screen_Judge = reception[constants_No.DIV_SCREEN_INDEX]; 
			
			//画面への返却値設定の呼び出し
			scDataSend(syainID,syainNAME,id_Judge,name_Judge,mv,msgList);
			
			//画面出力メッセージ設定の呼び出し
			msgOut(msgList,mv);
			
			//画面遷移URL判定の呼び出し
			String dist = no12.destination(div_screen_Judge);
			mv.setViewName(dist);
			
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return mv;
	}

	//社員情報更新後処理
	//引き数:InsertSyainForm.javaの変数
	//戻り値:変数mvに入っている画面遷移情報と変数
	@RequestMapping("/UPD_AFT")
	public ModelAndView  update_After(@ModelAttribute InsertSyainForm form){
		//変数宣言
		String syainID = "";
		String syainNAME = "";
		String beforeID = "";
		String beforeNAME = "";
		int id_Judge = 0;//ID入力欄を赤くするかの判定用
		int name_Judge = 0;//NAME入力欄を赤くするかの判定用
		int div_screen_Judge = 0;//行先URLを変更する判定用
		int reception[];
		ModelAndView mv = new ModelAndView();
		List<String> msgList = new ArrayList<String>();
		
		try {
			//画面からの入力値受け取り
			syainID = form.getSyainID();
			syainNAME = form.getSyainNAME();
			beforeID = form.getbeforeID();
			beforeNAME = form.getbeforeNAME();

			//更新後確認メッセージ追加判定
			reception = no12.msgAdd_After(syainID, syainNAME,beforeID,beforeNAME,msgList);
			id_Judge = reception[constants_No.ID_INDEX];
			name_Judge = reception[constants_No.NAME_INDEX];
			div_screen_Judge = reception[constants_No.DIV_SCREEN_INDEX]; 
			
			//画面への返却値設定の呼び出し
			no12.scDataSend_No12After(syainID,syainNAME,beforeID,beforeNAME,id_Judge,name_Judge,mv,msgList);
			
			//画面出力メッセージ設定の呼び出し
			msgOut(msgList,mv);

			//画面遷移URL判定の呼び出し
			String dist = no12.destinationAfter(div_screen_Judge);
			mv.setViewName(dist);
			
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		return mv;
	}

	//社員情報更新確認
	//引き数:InsertSyainForm.javaの変数
	//戻り値:変数mvに入っている画面遷移情報と変数
	@RequestMapping("/UPD_CONF") 
	public ModelAndView update_Confirm(@ModelAttribute InsertSyainForm form) throws SQLException {
		//変数宣言
		String syainID = "";
		String syainNAME = "";
		String beforeID = "";
		String beforeNAME = "";
		int id_Judge = 0;
		int name_Judge = 0;
		List<String> msgList = new ArrayList<String>();
		ModelAndView mv = new ModelAndView();
		
		//画面からの入力値受け取り
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		beforeID = form.getbeforeID();
		beforeNAME = form.getbeforeNAME();

		//DB更新処理&画面遷移URL設定の呼び出し
		id_Judge = no12.update_Function(syainID,syainNAME,beforeID,beforeNAME,mv,msgList);
		no12.scDataSend_No12After(syainID, syainNAME, beforeID, beforeNAME, id_Judge, name_Judge, mv, msgList);
		msgOut(msgList,mv);
		return mv;
	}

	//戻るボタン押下時処理(更新前情報入力画面[No12_Before]へ戻る)
	//引き数:InsertSyainForm.javaの変数
	//戻り値:変数mvに入っている画面遷移情報と変数
	@RequestMapping("/BF_BACK") 
	public ModelAndView before_Back(@ModelAttribute InsertSyainForm form) {
		//変数宣言
		String beforeID = "";
		String beforeNAME = "";
		ModelAndView mv = new ModelAndView();
		
		//画面からの入力値受け取り
		beforeID = form.getbeforeID();
		beforeNAME = form.getbeforeNAME();
		
		//画面への返却値設定
		mv.addObject("key01",beforeID);
		mv.addObject("key02",beforeNAME);
		mv.setViewName("No12_Before");
		return mv;
	}

	//戻るボタン押下時処理(更新後情報入力画面[No12_After]へ戻る)
	//引き数:InsertSyainForm.javaの変数
	//戻り値:変数mvに入っている画面遷移情報と変数
	@RequestMapping("/AFT_BACK") 
	public ModelAndView after_Back(@ModelAttribute InsertSyainForm form) {
		//変数宣言
		String beforeID = "";
		String beforeNAME = "";
		String syainID = "";
		String syainNAME = "";
		ModelAndView mv = new ModelAndView();
		
		//画面からの入力値受け取り
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();
		beforeID = form.getbeforeID();
		beforeNAME = form.getbeforeNAME();
		
		//画面への返却値設定
		mv.addObject("after01",syainID);
		mv.addObject("after02",syainNAME);
		mv.addObject("before01",beforeID);
		mv.addObject("before02",beforeNAME);
		mv.setViewName("No12_After");
		return mv;
	}

}
