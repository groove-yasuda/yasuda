package com.example.demo;



import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedCaseInsensitiveMap;

public class judge_Master {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public String idJudge(String workId)
	{
		
		String a = "";
		//ID規定判定
		Pattern idHantei = Pattern.compile("[A-Z]{1}[0-9]{3}");
		Matcher idHanteim = idHantei.matcher(workId);

		//X000判定準備
		Pattern plural = Pattern.compile("^(?!.*000).+$");
		Matcher pluralm = plural.matcher(workId);

		if(idHanteim.matches() == true && pluralm.matches() == true)
		{
			a = "true";
		}
		else
		{
			a = "false";
		}
		return a;
	}

	public String nameJudge(String workName)
	{
		String b = "";
		//NAME文字数判定
		Pattern nameMojisu = Pattern.compile("^[^a-zA-Z0-9]*${1,256}");
		Matcher nameMojisum = nameMojisu.matcher(workName);
		if(nameMojisum.matches() == true)
		{
			b = "true";
		}
		else
		{
			b = "false";
		}
		return b;
	}

	public int idExistJudge(String workId) throws SQLException, ClassNotFoundException
	{
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		int check2 = 1;
		List<?> check = jdbcTemplate.queryForList("SELECT COUNT(*) FROM syainmst where syainID = \""+ workId +"\"");
			LinkedCaseInsensitiveMap<?> syainList = (LinkedCaseInsensitiveMap<?>) check.get(0);
			check2 = Integer.parseInt(syainList.get("COUNT(*)").toString());
		return check2;
	}
	public int nameExistJudge(String workName) throws SQLException, ClassNotFoundException
	{
		envFile e = new envFile();
		jdbcTemplate = e.jdbcTemplate();
		int check2 = 1;
		List<?> check = jdbcTemplate.queryForList("SELECT COUNT(*) FROM syainmst where syainNAME = \"" + workName + "\"");
			LinkedCaseInsensitiveMap<?> syainList = (LinkedCaseInsensitiveMap<?>) check.get(0);
			check2 = Integer.parseInt(syainList.get("COUNT(*)").toString());
		return check2;
	}

}