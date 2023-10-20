package com.example.demo;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

public class No12_Model extends parent_NoClass2 {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7609996593087623039L;


	//確認メッセージ追加判定(更新前)
	//引数:syainID … 入力された更新前社員ID
	//引数:syainNAME … 入力された更新前社員名
	//引数:msgList … 画面出力メッセージの入れ子
	//戻り値:send[constants_No.ID_INDEX] … id_Judgeに代入する。(正常:0 ; 赤くなる:1)
	//戻り値:send[constants_No.NAME_INDEX] … name_Judgeに代入する。(正常:0 ; 赤くなる:1)
	//戻り値:send[constants_No.DIV_SCREEN_INDEX] … div_screen_Judgeに代入する。(正常:0 ;エラー:1)
	public int[] msgAdd_Before(String syainID,String syainNAME,List<String> msgList) throws ClassNotFoundException, SQLException 
	{
		//変数宣言
		int check = 0;
		int send[] = {0,0,0} ;
		if(syainID != null && syainNAME != null)
		{
			//更新前社員ID入力、登録判定呼び出し
			send[constants_No.ID_INDEX] = inJudgeId_Exist(syainID,msgList);
			
			//更新前社員名入力、登録判定呼び出し
			send[constants_No.NAME_INDEX] = inJudgeName(syainNAME,msgList);
			
			//更新前社員ID、更新前社員名登録判定呼び出し
			check = existCheck(syainID, syainNAME, msgList);
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
					send[constants_No.ID_INDEX] = 0;
					send[constants_No.NAME_INDEX] = 0;
					send[constants_No.DIV_SCREEN_INDEX] = 1;
				}
			}
		}
		return send;
	}
	
	
	//社員ID入力＆登録判定
	//引数:syainID … 入力された更新前社員ID
	//引数:msgList … 画面出力メッセージの入れ子
	//戻り値:社員IDが正常入力されているか(正常:0 ; エラー:1)
	public int inJudgeId_Exist(String syainID,List<String> msgList) throws ClassNotFoundException, SQLException 
	{
		//変数宣言
		boolean id_Judge = true;
		judge_Master jum = new judge_Master();
		int check = 0;

		if(syainID != "")
		{
			//更新前社員ID入力形式判定呼び出し
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
		if(msgList.size()==0)
		{
			//更新前社員ID入力、登録判定呼び出し(正常:1 ; エラー:0)
			int idExistCheck = idExistJudge(syainID);
			if(idExistCheck == 0)
			{
				msgList.add("社員IDが登録されていません");
				check = 1;
			}
		}
		return check;
	}
	
	
	//画面遷移URL判定(更新前)
	//引数:div_screen … エラーの有無により変化する変数。値が1の場合次のURLを指定する
	//戻り値:dist … div_screenの値により行先のURLを設定する
	public String destination(int div_screen) 
	{
		//変数宣言
		String dist = "";
		int error = 0;//div_screenがエラーの場合、値が0になる
		if(div_screen == error)
		{
			dist = "No12_Before";
		}
		else
		{
			dist = "No12_After";
		}
		return dist;
	}
	

	//確認メッセージ追加判定(更新後)
	//引数:syainID … 入力された更新後社員ID
	//引数:syainNAME … 入力された更新後社員名
	//引数:beforeID … 入力された更新前社員ID
	//引数:beforeNAME … 入力された更新前社員名
	//引数:msgList … 画面出力メッセージの入れ子
	//戻り値:send[constants_No.ID_INDEX] … id_Judgeに代入する。(正常:0 ; 赤くなる:1)
	//戻り値:send[constants_No.NAME_INDEX] … name_Judgeに代入する。(正常:0 ; 赤くなる:1)
	//戻り値:send[constants_No.DIV_SCREEN_INDEX] … div_screen_Judgeに代入する。(正常:0 ;エラー:1)
	public int[] msgAdd_After(String syainID,String syainNAME,String beforeID,String beforeNAME,List<String> msgList) throws SQLException, ClassNotFoundException
	{
		//変数宣言
		int check = 0;
		int send[] = {0,0,0};
		if(syainID != null && syainNAME != null)
		{

			//更新後社員ID入力、登録判定呼び出し
			send[constants_No.ID_INDEX] = inJudgeId(syainID,msgList);//parent_NoClass2

			//更新後社員名入力、登録判定呼び出し
			send[constants_No.NAME_INDEX] = inJudgeName(syainNAME,msgList);//parent_NoClass2
			
			//更新後社員ID、更新後社員名登録判定呼び出し
			check = existCheck(syainID, syainNAME, msgList);//parent_NoClass2
			if(msgList.size() == 0)
			{
				if(check == 0)
				{
					msgList.add("登録されている社員ID："+beforeID+"　　　社員名："+beforeNAME+"　を");
					msgList.add("更新する情報の社員ID："+syainID+"　　　社員名："+syainNAME+"　へ更新します");
					send[constants_No.ID_INDEX] = 0;
					send[constants_No.NAME_INDEX] = 0;
					send[constants_No.DIV_SCREEN_INDEX] = 1;
				}
				else if(check == 1)
				{
					msgList.add("社員IDと社員名の組み合わせが違います");
					send[constants_No.ID_INDEX] = 1;
					send[constants_No.NAME_INDEX] = 1;
					send[constants_No.DIV_SCREEN_INDEX] = 0;
				}
			}
		}
		return send;
	}

	
	//画面遷移URL判定(更新後)
	//引数:div_screen … エラーの有無により変化する変数。値が1の場合次のURLを指定する
	//戻り値:dist … div_screenの値により行先のURLを設定する
	public String destinationAfter(int div_screen) 
	{
		//変数宣言
		String dist = "";
		int error = 0;//div_screenがエラーの場合、値が0になる
		if(div_screen == error)
		{
			dist = "No12_After";
		}
		else
		{
			dist = "No12_Confirm";
		}
		return dist;
	}

	
	//画面への返却値設定(更新後)
	//引数:syainID … 入力された更新後社員ID
	//引数:syainNAME … 入力された更新後社員名
	//引数:beforeID … 入力された更新前社員ID
	//引数:beforeNAME … 入力された更新前社員名
	//引き数:id_Judge … 社員ID入力欄を赤くするかの変数
	//引き数:name_Judge … 社員名入力欄を赤くするかの変数
	//引き数:変数mvに入っている画面遷移情報と変数
	//引き数:msgList … 画面出力メッセージの入れ子
	//戻り値:voidのため無し
	public void scDataSend_No12After(String syainID,String syainNAME,String beforeID,String beforeNAME,
			int id_Judge,int name_Judge ,ModelAndView mv,List<String> msgList) 
	{
		mv.addObject("id_point",id_Judge);
		mv.addObject("name_point",name_Judge);
		mv.addObject("before01",beforeID);
		mv.addObject("before02",beforeNAME);
		mv.addObject("after01",syainID);
		mv.addObject("after02",syainNAME);
		mv.addObject("key03",msgList);
	}	
	
	
	//DB更新処理&画面遷移URL設定(更新後)
	//引数:syainID … 入力された更新後社員ID
	//引数:syainNAME … 入力された更新後社員名
	//引数:beforeID … 入力された更新前社員ID
	//引数:beforeNAME … 入力された更新前社員名
	//引数:mv … ModelAndViewのインスタンス化変数
	//引数:msgList … 画面出力メッセージの入れ子
	//戻り値:id_Judge … 社員ID入力欄を赤くするかの変数
	public int update_Function(String syainID,String syainNAME,String beforeID,String beforeNAME,
			ModelAndView mv,List<String> msgList) throws SQLException
	{		
		//変数宣言
		int id_Judge = 0;
		try {
			//更新前社員ID入力、登録判定呼び出し
			int check_Before = idExistJudge(beforeID);
			
			//更新後社員ID入力、登録判定呼び出し
			int check_After = idExistJudge(syainID);
			
			if(check_Before == 0)
			{
				msgList.add("社員IDが登録されていません");
				mv.setViewName("No12_After");
				id_Judge = 1;
			}
			else if(check_After > 0)
			{
				msgList.add("社員IDが登録されています");
				mv.setViewName("No12_After");
				id_Judge = 1;
			}
			else if(check_Before == 1 && check_After ==0)
			{
				System.out.println("登録されている社員ID："+beforeID+"、社員名："+beforeNAME+"に更新する情報の社員ID："+syainID+"、社員名："+syainNAME+"へ更新します");
				Hashtable<String, Object> kousinhensu = new Hashtable<String,Object>();
				
				//変数宣言
				kousinhensu.put("key0",syainID);
				kousinhensu.put("key1",syainNAME);
				
				//DB更新処理呼び出し
				dbAccess(kousinhensu,beforeID,beforeNAME);
				mv.setViewName("No12_Save");
				System.out.println("登録完了");
			}
		}
		catch(Exception e)
		{
			e. printStackTrace();
			System.out.println("error");
		}
		return id_Judge;
	}



}
