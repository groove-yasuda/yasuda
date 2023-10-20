package com.example.demo;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

public class No13_Model extends parent_NoClass2  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8906185320399511133L;

	
	//削除確認メッセージ追加判定
	//引数:syainID … 入力された社員ID
	//引数:syainNAME … 入力された社員名
	//引数:msgList … 画面出力メッセージの入れ子
	//戻り値:send[constants_No.ID_INDEX] … id_Judgeに代入する。(正常:0 ; 赤くなる:1)
	//戻り値:send[constants_No.NAME_INDEX] … name_Judgeに代入する。(正常:0 ; 赤くなる:1)
	//戻り値:send[constants_No.DIV_SCREEN_INDEX] … div_screen_Judgeに代入する。(正常:0 ;エラー:1)
	public int[] msgAdd(String syainID,String syainNAME,List<String> msgList) throws ClassNotFoundException, SQLException 
	{
		int send[] = {0,0,0};
		int check = 0;//社員ID、社員名登録判定用(正常:1 ;エラー:0)
		if(syainID != null && syainNAME != null)
		{
			//社員ID入力、登録判定呼び出し
			send[constants_No.ID_INDEX] = inJudgeId(syainID,msgList);//No13_Model

			//社員名入力、登録判定呼び出し
			send[constants_No.NAME_INDEX] = inJudgeName(syainNAME,msgList);//parent_NoClass2

			//社員ID、社員名登録判定呼び出し
			check = existCheck(syainID, syainNAME, msgList);//parent_NoClass2
			if(msgList.size() == 0)
			{
				if(check == 0)
				{
					msgList.add("社員IDと社員名の組み合わせが違います");
					send[constants_No.ID_INDEX] = 1;
					send[constants_No.NAME_INDEX] = 1;
					send[constants_No.DIV_SCREEN_INDEX] = 0;
				}
				else if(check == 1)
				{
					msgList.add("削除する社員ID："+syainID+"");
					msgList.add("削除する社員名："+syainNAME+"");
					send[constants_No.ID_INDEX] = 0;
					send[constants_No.NAME_INDEX] = 0;
					send[constants_No.DIV_SCREEN_INDEX] = 1;
				}
			}
		}
		return send;
	}
	
	
	//社員ID入力＆登録判定
	//引数:syainID … 入力された社員ID
	//引数:msgList … 画面出力メッセージの入れ子
	//戻り値:社員IDが正常入力されているか(正常:0 ; エラー:1)
	public int inJudgeId(String syainID,List<String> msgList) throws ClassNotFoundException, SQLException 
	{
		//変数宣言
		boolean id_Judge = false;
		judge_Master jum = new judge_Master();
		int check = 0;//エラー判定用(正常:0 ; エラー:1)

		if(syainID != "")
		{
			//社員ID入力形式判定呼び出し
			id_Judge = jum.idJudge(syainID);
			if(id_Judge == false)
			{
				msgList.add("社員IDの入力形式に誤りがあります");
				check = 1;
			}
		}
		else
		{
			msgList.add("社員IDが未入力です");
			check = 1;
		}
		//社員ID入力、登録判定呼び出し(正常:1 ; エラー:0)
		int idExistCheck = idExistJudge(syainID);
		if(idExistCheck == 0)
		{
			msgList.add("社員IDが登録されていません");
			check = 1;
		}
		return check;
	}
	
	
	//削除画面遷移URL判定
	//引数:div_screen … エラーの有無により変化する変数。値が1の場合次のURLを指定する
	//戻り値:dist … div_screenの値により行先のURLを設定する
	public String destination(int div_screen) 
	{
		//変数宣言
		String dist = "";
		if(div_screen == 0)
		{
			dist = "No13";
		}
		else if(div_screen == 1)
		{
			dist = "No13_Confirm";
		}
		return dist;
	}


	//DB削除処理&画面遷移URL設定
	//引数:syainID … 入力された社員ID
	//引数:syainNAME … 入力された社員名
	//引数:mv … ModelAndViewのインスタンス化変数
	//引数:msgList … 画面出力メッセージの入れ子
	//戻り値:voidのため無し
	public void delete_Function(String syainID,String syainNAME,ModelAndView mv,List<String> msgList) 
	{
		try {
			//社員ID登録判定呼び出し
			int check = idExistJudge(syainID); //エラー判定用(正常:1 ; エラー:0)
			if(check==0)
			{
				msgList.add("社員IDが使用されていません");
				mv.setViewName("No13");
			}
			else
			{
				//変数宣言
				String[] data = { syainID,syainNAME};

				//DB削除処理呼び出し
				dbAccess(data);
				System.out.println("登録されている社員ID："+ syainID +"、社員名："+ syainNAME +"を社員マスタから削除します");
				mv.setViewName("No13_Delete");
				System.out.println("削除完了");
			}
		}
		catch(Exception e)
		{
			e. printStackTrace();
			System.out.println("error");
		}

	}
}
