package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//vueとの接続用
@CrossOrigin(origins = "http://localhost:8082")
@RestController
public class Ctrl {
	
	Model model = new Model();
	
	//初期画面表示
	  @GetMapping("/")
	  public ModelAndView home() {
			//変数宣言
			ModelAndView mv = new ModelAndView();
			mv.setViewName("index");
			return mv;
	  }
	  
	  
		//社員ID存在チェック
		@RequestMapping(value="/id_Check", method = RequestMethod.POST)
		@ResponseBody
		public int id_Exist_Check(@RequestBody Param form) throws ClassNotFoundException, SQLException
		{
			//変数宣言
			String syainID = form.getIdOver();
			int result = 0;
			
			//社員ID存在チェック
			int judgeCheck = model.idExistJudge(syainID);
			
			//社員ID存在チェックの結果によって処理を振り分ける
				if(judgeCheck == 0)
				{
					result = 0;
				}
				else if(judgeCheck == 1)
				{
					result = 1;
				}
				
				return result;

		}
	  
	  
	  	//社員情報登録機能の処理
		@RequestMapping(value="/insert_Func", method = RequestMethod.POST)
		@ResponseBody
		public boolean insert(@RequestBody Param form) throws ClassNotFoundException, SQLException
		{
			//変数宣言
			String syainID = form.getIdOver();
			String syainNAME = form.getNameOver();
			String syainBIRTH = form.getBirth();
			String syainAGE = form.getAge();
			String syainGENDER = form.getGender();
			String syainKIHONKYU = form.getKihonkyu();
			String syainKOUTUHI = form.getKoutuhi();
			boolean dist= false;
			
			//社員ID存在チェック呼び出し
			int judgeCheck = model.idExistJudge(syainID);
			
			//社員ID存在チェックの結果によって処理を振り分ける
			if(judgeCheck == 0)//結果が存在しないとき
			{
				System.out.println("社員IDに"+syainID+"、社員名に"+syainNAME+"を登録します");

				//変数宣言
				//Hshtableに情報を格納
				Hashtable<String, Object> insert_Variable = new Hashtable<String,Object>();
				insert_Variable.put("key0", syainID);
				insert_Variable.put("key1", syainNAME);
				insert_Variable.put("key2", syainBIRTH);
				insert_Variable.put("key3", syainAGE);
				insert_Variable.put("key4", syainGENDER);
				insert_Variable.put("key5", syainKIHONKYU);
				insert_Variable.put("key6", syainKOUTUHI);

				//DB登録処理の呼び出し
				model.dbAccess(insert_Variable);
				dist = true;
				System.out.println("登録完了");
			}
			else//結果が存在するとき
			{
				dist = false;
			}
			return dist;

		}
		
		

		
		//社員情報更新機能の処理
		@RequestMapping(value="/update_Func", method = RequestMethod.POST)
		@ResponseBody
		public boolean update(@RequestBody Param form) throws ClassNotFoundException, SQLException
		{
			//変数宣言
			String beforeID = form.getIdOver();
			String beforeNAME = form.getNameOver();
			String afterID = form.getIdOver_After();
			String afterNAME = form.getNameOver_After();
			boolean dist= false;
			
			//社員ID,社員名組み合わせチェックの呼び出し
			int judgeCheck = model.existCheck(afterID,afterNAME);
			
			//存在チェックの結果によって処理を振り分ける
			if(judgeCheck == 0)//存在しない時
			{
				System.out.println("社員ID:"+beforeID+"を"+afterID+"に、社員名:"+beforeNAME+"を"+afterNAME+"に変更します");

				//変数宣言
				//Hshtableに情報を格納
				Hashtable<String, Object> hsh = new Hashtable<String,Object>();
				hsh.put("key0", afterID);
				hsh.put("key1", afterNAME);

				//DB更新処理の呼び出し
				model.dbAccess(hsh, beforeID, beforeNAME);
				dist = true;
				System.out.println("変更完了");
			}
			else//存在する時
			{
				dist = false;
			}
			return dist;

		}
		
		
		
		//社員情報削除機能の処理
		@RequestMapping(value="/delete_Func", method = RequestMethod.POST)
		@ResponseBody
		public boolean delete(@RequestBody Param form) throws ClassNotFoundException, SQLException
		{
			//変数宣言
			String syainID = form.getIdOver();
			String syainNAME = form.getNameOver();
			String syainBIRTH = form.getBirth();
			String[] data = {syainID};
			boolean dist= false;
			
			//社員ID,社員名,社員生年月日組み合わせチェックの呼び出し
			int judgeCheck = model.existCheck(syainID,syainNAME,syainBIRTH);
			
			//組み合わせチェックの結果によって処理を振り分ける
			if(judgeCheck == 1)//存在する時
			{
				System.out.println("社員ID:"+syainID+"、社員名:"+syainNAME+"を削除します");


				//DB削除処理の呼び出し
				model.dbAccess(data);
				
				dist = true;
				System.out.println("削除完了");
			}
			else//存在しない時
			{
				dist = false;
			}
			return dist;

		}
		
		
		
		
		//社員情報検索機能の処理(一覧検索)
		@RequestMapping(value="/select_Func", method = RequestMethod.POST)
		@ResponseBody
		public List<String> select(@RequestBody Param form) throws ClassNotFoundException, SQLException
		{
			//変数宣言
			String search_Alt = "";//検索カラム指定の際に検索対象とは別のカラムを代入する変数

			Hashtable<String, Object> search_Variable = new Hashtable<String,Object>();
			Hashtable<String, Object> search_char = new Hashtable<String,Object>();
			List <String>search_List = new ArrayList<String>();

			//社員名入力形式判定呼び出し(true:社員名検索 ; false:社員ID検索)
			if(form.getSearch_Target().equals("ID"))
			{
				search_Alt = "syainID";
			}
			else
			{
				search_Alt = "syainNAME";
			}

			search_Variable.put("key0",form.getSearch_Option());//検索条件(一致)
			search_Variable.put("key1",search_Alt);//検索カラム(入力した文字の半角/全角でID/NAMEになる)
			
			search_char.put("key0",form.getInput_Emp_Search());//入力した文字

			//DB検索処理
			search_List.addAll(model.dbAccess(search_Variable,search_char));
			
			return search_List;

		}
		
		
		
		
		//社員情報検索機能の処理(個人検索)
		@RequestMapping(value="/select_Private_Func", method = RequestMethod.POST)
		@ResponseBody
		public Hashtable<String, Object> select_private(@RequestBody Param form) throws ClassNotFoundException, SQLException
		{
			Hashtable<String, Object> search = new Hashtable<String,Object>();
			List <String>search_List = new ArrayList<String>();
			List <String>tax_List = new ArrayList<String>();
			String syainID = form.getInput_Emp_Search();


			//DB検索処理
			search_List.addAll(model.dbAccess_Private(syainID));
			tax_List.addAll(model.dbAccess_tax());
			
			
			search.put("key0",search_List);
			search.put("key1",tax_List);
			

			return  search;

		}

	}



