package com.example.demo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class MENU {
	
	
	 @RequestMapping("/IDOU") 
	 public String menuScreen() 
	 {
		 return "menu";
	 }
		 
	@RequestMapping("/MENU") 
	public String idouScreen(@ModelAttribute InsertSyainForm form)
	{
		String hantei = "0";
		String NO = "";
		hantei = form.gethantei();
		if(hantei.equals("0"))
		{
			NO = "No11";
		}
		else if(hantei.equals("1"))
		{
			NO = "No12_mae";
		}
		else if(hantei.equals("2"))
		{
			NO = "No13";
		}
		else if(hantei.equals("3"))
		{
			NO = "No14";
		}
		return NO;
	}
	
}
