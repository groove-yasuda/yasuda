package com.example.demo;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

public class No14_Model extends parent_NoClass2{
	/**
	 * 
	 */
	private static final long serialVersionUID = 465559335575550510L;

	//DB検索処理
	//引数:search … 検索文字
	//引数:prime_Rank … 優先順位のカラム(syainID or syainNAME)
	//引数:condition … 検索条件
	//引数:syainID … ソート順カラム(syainID)
	//引数:syainNAME … ソート順カラム(syainNAME)
	//引数:msgList … 画面出力メッセージの入れ子
	//戻り値:voidのため無し
	public void select_Function(String search,String prime_Rank,String condition,
			String syainID,String syainNAME,List<String> searchList) throws SQLException 
	{
		//変数宣言
		judge_Master jum = new judge_Master();
		String search_Alt = "";//検索カラム指定の際に検索対象とは別のカラムを代入する変数
		String rank = "";//第一優先のカラム
		String rank_Alt = "";//第二優先のカラム
		String rank_Order1 = "";//第一優先のソート条件(昇順 or 降順)
		String rank_Order2 = "";//第二優先のソート条件(昇順 or 降順)
		boolean a = false;
		Hashtable<String, Object> search_Variable = new Hashtable<String,Object>();
		Hashtable<String, Object> sortorder = new Hashtable<String,Object>();

		//社員名入力形式判定呼び出し(true:社員名検索 ; false:社員ID検索)
		a = jum.nameJudge(search);
		if(a == true)
		{
			search_Alt = "syainNAME";
		}
		else
		{
			search_Alt = "syainID";
		}

		if(prime_Rank.equals("syainID"))
		{
			rank = "syainID";
			rank_Alt = "syainNAME";
			rank_Order1 = syainID;
			rank_Order2 = syainNAME;
		}
		else
		{
			rank = "syainNAME";
			rank_Alt = "syainID";
			rank_Order1 = syainNAME;
			rank_Order2 = syainID;
		}

		search_Variable.put("key0",condition);//検索条件(一致)
		search_Variable.put("key1",search_Alt);//検索カラム(入力した文字の半角/全角でID/NAMEになる)
		search_Variable.put("key2",search);//入力した文字
		search_Variable.put("key3",rank);//第一優先のカラム
		search_Variable.put("key4",rank_Alt);//第二優先のカラム

		sortorder.put("key1",rank_Order1);//第一優先のソート順
		sortorder.put("key2",rank_Order2);//第二優先のソート順

		//DB検索処理
		searchList.addAll(dbAccess(search_Variable,sortorder));
		System.out.println("list="+searchList);
	}

	//削除確認メッセージ追加判定
	//引数:search … 検索文字
	//引数:searchList … 画面出力メッセージの入れ子
	//戻り値:search_Judge … 検索文字が正常入力されているか(正常:0 ; エラー:1)
	public int  msgAdd(String search,List<String> searchList) 
	{
		//変数宣言
		int search_Judge = 0;
		if( searchList.size() == 0 )
		{
			searchList.add("検索結果が見つかりませんでした");
			search_Judge = 1;
		}

		if(search == "")
		{
			searchList.add("検索対象が未入力です");
			search_Judge = 1;
		}
		return search_Judge;
	}

	//画面への返却値設定
	//引数:search … 検索文字
	//引数:search_Judge … 検索文字が正常入力されているか(正常:0 ; エラー:1)
	//引数:変数mvに入っている画面遷移情報と変数
	//引数:searchList … 画面出力メッセージの入れ子
	//戻り値:voidのため無し
	public void scDataSendSelect(String search,int search_Judge,ModelAndView mv,List<String> searchList) 
	{
		mv.addObject("key01",search);
		mv.addObject("key02",search_Judge);
		mv.addObject("key03",searchList);
	}
}
