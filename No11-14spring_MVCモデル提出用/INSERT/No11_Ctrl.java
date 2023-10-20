package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class No11_Ctrl extends parent_NoClass2{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8717936357217903023L;

	//社員情報登録処理
	//引き数:InsertSyainForm.javaの変数
	//戻り値:変数mvに入っている画面遷移情報と変数
	@RequestMapping("/INSERT")
	public ModelAndView  insert(@ModelAttribute InsertSyainForm form){
		//変数宣言
		No11_Model no11 = new No11_Model();
		ModelAndView mv = new ModelAndView();
		String syainID = "";
		String syainNAME = "";
		int id_Judge = 0;//ID入力欄を赤くするかの判定用
		int name_Judge = 0;//NAME入力欄を赤くするかの判定用
		int div_screen_Judge = 1;//行先URLを変更する判定用
		int reception[];
		List<String> msgList = new ArrayList<String>();
		
		try {
			//画面からの入力値受け取り
			syainID = form.getSyainID();
			syainNAME = form.getSyainNAME();

			//登録確認メッセージ追加判定
			reception = no11.msgAdd(syainID,syainNAME,msgList);
			id_Judge = reception[constants_No.ID_INDEX];
			name_Judge = reception[constants_No.NAME_INDEX];
			div_screen_Judge = reception[constants_No.DIV_SCREEN_INDEX]; 

			//画面への返却値設定の呼び出し
			scDataSend(syainID,syainNAME,id_Judge,name_Judge,mv,msgList);

			//画面出力メッセージ設定の呼び出し
			msgOut(msgList,mv);

			//画面遷移URL判定の呼び出し
			String dist = no11.destination(div_screen_Judge);
			mv.setViewName(dist);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mv;
	}

	//社員情報登録確認
	//引き数:InsertSyainForm.javaの変数
	//戻り値:変数mvに入っている画面遷移情報と変数
	@RequestMapping("/INS_CONF") 
	public ModelAndView insertConfirm(@ModelAttribute InsertSyainForm form) {
		//変数宣言
		String syainID = "";
		String syainNAME = "";
		int judgeCheck;
		No11_Model no11 = new No11_Model();
		ModelAndView mv = new ModelAndView();
		List<String> msgList = new ArrayList<String>();
		

		try {
			//画面からの入力値受け取り
			syainID = form.getSyainID();
			syainNAME = form.getSyainNAME();

			//社員ID入力&登録判定の呼び出し
			judgeCheck = inJudgeId(syainID,msgList);
			//DB登録処理&画面遷移URL設定の呼び出し
			String dist = no11.insert_Function(syainID,syainNAME,judgeCheck,mv,msgList);
			mv.setViewName(dist);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mv;
	}

	//戻るボタン押下時処理(初期画面[No11]へ戻る)
	//引き数:InsertSyainForm.javaの変数
	//戻り値:変数mvに入っている画面遷移情報と変数
	@RequestMapping("/INS_BACK") 
	public ModelAndView insertBack(@ModelAttribute InsertSyainForm form) {
		//変数宣言
		String syainID = "";
		String syainNAME = "";
		ModelAndView mv = new ModelAndView();

		//画面からの入力値の呼び出し
		syainID = form.getSyainID();
		syainNAME = form.getSyainNAME();

		//画面への返却値、画面遷移のURLの設定
		mv.addObject("key01",syainID);
		mv.addObject("key02",syainNAME);
		mv.setViewName("No11");
		return mv;
	}



}
