package com.example.demo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.servlet.ModelAndView;

public class parent_NoClass2 extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4317509414419897342L;
	@Autowired
	private JdbcTemplate jdbcTemplate;


	//社員ID入力＆登録判定
	//引数:syainID … 入力された社員ID
	//引数:msgList … 画面出力メッセージの入れ子
	//戻り値:社員IDが正常入力されているか(正常:0 ; エラー:1)
	public int inJudgeId(String syainID,List<String> msgList) throws ClassNotFoundException, SQLException {
		//変数宣言
		boolean id_Judge = false;
		judge_Master jum = new judge_Master();
		int check = 0;

		if(syainID != "")
		{
			//ID入力形式判定の呼び出し
			id_Judge = jum.idJudge(syainID);
			if(id_Judge == false)
			{
				msgList.add("社員IDの入力形式に誤りがあります");//ID入力エラーメッセージ
				check = 1;
			}
		}
		else
		{
			msgList.add("社員IDが未入力です");//ID入力エラーメッセージ
			check = 1;
		}
		if(msgList.size()==0)
		{
			//社員ID登録判定の呼び出し
			int check1 = idExistJudge(syainID);
			if(check1>0)
			{
				msgList.add("社員IDが登録されています");
				check = 1;
			}
		}
		return check;
	}

	//社員名入力＆登録判定
	//引数:syainNAME … 入力された社員名
	//引数:msgList … 画面出力メッセージの入れ子
	//戻り値:社員名が正常入力されているか(正常:0 ; エラー:1)
	public int inJudgeName(String syainNAME,List<String> msgList ) {
		//変数宣言
		boolean name_Judge = false;
		judge_Master jum = new judge_Master();
		int check = 0;

		if(syainNAME != "")//NAMEが入力されてる
		{
			//社員名入力判定の呼び出し
			name_Judge = jum.nameJudge(syainNAME);
			if(name_Judge == false)//NAME入力エラーチェック
			{
				msgList.add("社員名の入力形式に誤りがあります");//NAME入力エラーメッセージ
				check = 1;
			}
		}
		else
		{
			msgList.add("社員名が未入力です");//ID入力エラーメッセージ
			check = 1;
		}
		return check;
	}

	//社員ID登録判定
	//引数:syainID … 入力された社員ID
	//戻り値:社員IDが登録されているか(正常:0 ; エラー:1)
	public int idExistJudge(String syainID) throws ClassNotFoundException, SQLException
	{
		//変数宣言
		judge_Master jum = new judge_Master();
		int check = 0;
		
		//社員ID登録判定呼び出し
		check = jum.idExistJudge(syainID);
		return check;
	}

	//社員名登録判定
	//引数:syainNAME … 入力された社員名
	//戻り値:社員NAMEが登録されているか(正常:0 ; エラー:1)
	public int nameExistJudge(String syainNAME) throws ClassNotFoundException, SQLException
	{
		//変数宣言
		judge_Master jum = new judge_Master();
		int check = 0;
		
		//社員名登録判定呼び出し
		check = jum.nameExistJudge(syainNAME);
		return check;
	}

	//オーバーロード(登録機能)
	//引数:HashTable hshの情報
	//戻り値:voidのため無し
	public void dbAccess(Hashtable<String, Object> hsh)
	{
		//DB接続処理の呼び出し
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		
		//登録処理呼び出し
		jdbcTemplate.update("INSERT INTO syainmst(syainID,syainNAME) VALUES(?,?)", hsh.get("key0") ,hsh.get("key1"));
	}

	//オーバーロード(更新機能)
	//引数:HashTable hsh … 社員情報(社員ID、社員名)
	//引数:beforeID … 更新前社員ID
	//引数:beforeNAME … 更新前社員名
	//戻り値:voidのため無し
	public void dbAccess(Hashtable <String, Object> hsh,String beforeID,String beforeNAME) throws SQLException
	{
		//DB接続処理の呼び出し
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		
		//更新処理の呼び出し
		jdbcTemplate.update("UPDATE syainmst SET syainID = ?,syainNAME = ? WHERE syainID = ? AND syainNAME = ? ",hsh.get("key0"),hsh.get("key1"),beforeID,beforeNAME);
	}

	//オーバーロード(削除機能)
	//引数:配列 data[] … 社員情報(社員ID、社員名)
	//戻り値:voidのため無し
	public void dbAccess(String data[]) throws SQLException
	{	
		//DB接続処理の呼び出し
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		
		//削除処理の呼び出し
		jdbcTemplate.update("DELETE FROM syainmst WHERE syainID = ? AND syainNAME = ? ",data[0],data[1]);
	}

	//オーバーライド(検索機能)
	//引数:HashTable condition … 社員情報(検索条件[検索条件,検索カラム,検索文字,優先順位1,優先順位2])
	//引数:HashTable sort … ソート順情報(ソート順[syainID,sayinNAME])
	//引数:syainID … 入力された社員ID
	//引数:syainNAME … 入力された社員名
	//戻り値:voidのため無し
	public List<String> dbAccess(Hashtable<String, Object> condition,Hashtable<String, Object> sort) throws SQLException
	{
		//DB接続処理の呼び出し
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		List <String>searchList = new ArrayList<String>();
		
		//変数宣言
		String before="";
		String usiro ="";
		
		//社員マスタ検索条件の判定
		if(condition.get("key0").equals("front"))
		{
			before = "";
			usiro = "%";
		}
		else if(condition.get("key0").equals("back"))
		{
			before = "%";
			usiro = "";
		}
		else if(condition.get("key0").equals("all"))
		{
			before = "";
			usiro = "";
		}
		else if(condition.get("key0").equals("part"))
		{
			before = "%";
			usiro = "%";
		}
		
		//検索処理の呼び出し
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM syainmst where "+condition.get("key1")+" like ? ORDER BY "+condition.get("key3")+" "+sort.get("key1")+","+condition.get("key4")+" "+sort.get("key2")+"",
				before+condition.get("key2")+usiro);
		
		//検索結果をListに追加する処理
		for(int l = 0;l < list.size();l++)
		{
			searchList.add(list.get(l).toString().replaceAll("^\\{|\\}$", ""));
		}
		return searchList;
	}

	//オーバーロード(IDとNAMEでの組み合わせ存在チェック)
	//引数:syainID … 入力された社員ID
	//引数:syainNAME … 入力された社員名
	//引数:msgList … 画面出力メッセージの入れ子
	//戻り値:社員ID,社員名の組み合わせが社員マスタに登録されている数
	public int existCheck(String syainID,String syainNAME,List<String> msgList) throws SQLException
	{
		//DB接続処理の呼び出し
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		
		//変数宣言
		int check2 ;
		
		//社員ID、社員名組み合わせ存在判定の呼び出し
		List<?> check = jdbcTemplate.queryForList("SELECT COUNT(*) FROM syainmst where syainID = ? AND syainNAME = ?",syainID,syainNAME);
		LinkedCaseInsensitiveMap<?> syainList = (LinkedCaseInsensitiveMap<?>) check.get(0);
		
		//判定結果の返却値を設定
		check2 = Integer.parseInt(syainList.get("COUNT(*)").toString());
		return check2;
	}
	//"select * from syainMst where syainName like ? and syainId between ? and ? order by "+sortOrderSet.get("orderSet1")[0]+" "+sortOrderSet.get("orderSet1")[1]+","+sortOrderSet.get("orderSet2")[0]+" "+sortOrderSet.get("orderSet2")[1]  , "%"+searchOrderSet.get("srhString")+"%", searchOrderSet.get("lowId"), searchOrderSet.get("topId")

	//画面への返却値設定
	//引数:syainID … 入力された社員ID
	//引数:syainNAME … 入力された社員名
	//引き数:id_point … 社員ID入力欄を赤くするかの変数
	//引き数:name_point … 社員名入力欄を赤くするかの変数
	//引数:変数mvに入っている画面遷移情報と変数
	//引数:msgList … 画面出力メッセージの入れ子
	//戻り値:voidのため無し
	public void scDataSend(String syainID,String syainNAME,int id_point,
			int name_point ,ModelAndView mv,List<String> msgList) 
	{
		mv.addObject("key01",syainID);
		mv.addObject("key02",syainNAME);
		mv.addObject("id_point",id_point);
		mv.addObject("name_point",name_point);
		mv.addObject("key03",msgList);
	}

	//画面出力メッセージ設定
	//引数:msgList … 画面出力メッセージの入れ子
	//引数:変数mvに入っている画面遷移情報と変数
	//戻り値:voidのため無し
	public void msgOut(List<String> msgList,ModelAndView mv)
	{
		//メッセージを画面の返却値に設定
		for(int i = 0; i < msgList.size();i++)
		{
			mv.addObject("key04"+(i),msgList.get(i));
		}
	}
	
	
}



//http://localhost:8080/11-14_yasuda/No11.jsp
