package com.example.demo;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;


public class No11_Model extends parent_NoClass2{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9047217655067294814L;

	//登録確認メッセージ追加判定
	//引数:syainID … 入力された社員ID
	//引数:syainNAME … 入力された社員名
	//引数:msgList … 画面出力メッセージの入れ子
	//戻り値:send[constants_No.ID_INDEX] … id_Judgeに代入する。(正常:0 ; 赤くなる:1)
	//戻り値:send[constants_No.NAME_INDEX] … name_Judgeに代入する。(正常:0 ; 赤くなる:1)
	//戻り値:send[constants_No.DIV_SCREEN_INDEX] … div_screen_Judgeに代入する。(正常:0 ;エラー:1)
	public int[]  msgAdd(String syainID,String syainNAME,List<String> msgList) throws ClassNotFoundException, SQLException 
	{
		//変数宣言
		int send[] = {0,0,1} ;
		if(syainID != null && syainNAME != null)
		{
			//社員ID入力、登録判定呼び出し
			send[constants_No.ID_INDEX] = inJudgeId(syainID,msgList);//parent_NoClass2

			//社員名入力、登録判定呼び出し
			send[constants_No.NAME_INDEX] = inJudgeName(syainNAME,msgList);//parent_NoClass2

			if(msgList.size() == 0)
			{
				msgList.add("社員IDに"+syainID+"");
				msgList.add("社員名に"+syainNAME+"　を登録します");
				send[constants_No.DIV_SCREEN_INDEX] = 0;
			}
		}
		return send;
	}

	//画面遷移URL判定
	//引数:div_screen … エラーの有無により変化する変数。値が1の場合次のURLを指定する
	//戻り値:dist … div_screenの値により行先のURLを設定する
	public String destination(int div_screen) 
	{
		//変数宣言
		String dist = "";
		int error = 1;//div_screenがエラーの場合、値が1になる
		if(div_screen == error)
		{
			dist = "No11";
		}
		else
		{
			dist = "No11_Confirm";
		}
		return dist;
	}

	//DB登録処理&画面遷移URL設定
	//引数:syainID … 入力された社員ID
	//引数:syainNAME … 入力された社員名
	//引数:check … 社員ID入力&登録判定の結果(true:0 ; false 1)
	//引数:mv … ModelAndViewのインスタンス化変数
	//引数:msgList … 画面出力メッセージの入れ子
	//戻り値:dist … 画面遷移URLの設定値
	public String insert_Function(String syainID,String syainNAME,int judgeCheck,
			ModelAndView mv,List<String> msgList) 
	{
		//変数宣言
		String dist = "";
		int OK = 0;
		if(judgeCheck == OK)
		{
			System.out.println("社員IDに"+syainID+"、社員名に"+syainNAME+"を登録します");

			//変数宣言
			Hashtable<String, Object> insert_Variable = new Hashtable<String,Object>();
			insert_Variable.put("key0", syainID);
			insert_Variable.put("key1", syainNAME);

			//DB登録処理の呼び出し
			dbAccess(insert_Variable);
			dist ="No11_Save";
			System.out.println("登録完了");
		}
		else
		{
			//画面出力メッセージ設定
			msgOut(msgList,mv);
			dist = "No11";
		}
		return dist;
	}

}
