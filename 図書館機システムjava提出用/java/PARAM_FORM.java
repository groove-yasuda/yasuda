package com.example.demo;

import java.io.Serializable;
import java.util.List;

public class PARAM_FORM implements Serializable{

	private int card_Number;	
	private String user_Name;
	private String book_Number;
	private String book_Name;
	private String genre_Name;
	private String return_Confi;
	private Object scrn_Var;
	private List<String> list_Send;
	private List<String> list_Send_Confi;
	private List<String> data_Send;
	
	
	int getCard_Number() {
		return card_Number;
	}
	public void setCard_Number(int card_Number) {
		this.card_Number = card_Number;
	}
	public String getUser_Name() {
		return user_Name;
	}
	public void setUser_Name(String user_Name) {
		this.user_Name = user_Name;
	}
	public String getBook_Number() {
		return book_Number;
	}
	public void setBook_Number(String book_Number) {
		this.book_Number = book_Number;
	}
	public String getBook_Name() {
		return book_Name;
	}
	public void setBook_Name(String book_Name) {
		this.book_Name = book_Name;
	}
	public String getGenre_Name() {
		return genre_Name;
	}
	public void setGenre_Name(String genre_Name) {
		this.genre_Name = genre_Name;
	}
	public Object getScrn_Var() {
		return scrn_Var;
	}
	public void setScrn_Var(Object scrn_Var) {
		this.scrn_Var = scrn_Var;
	}
	public List<String> getList_Send() {
		return list_Send;
	}
	public void setList_Send(List<String> list_Send) {
		this.list_Send = list_Send;
	}
	public List<String> getList_Send_Confi() {
		return list_Send_Confi;
	}
	public void setList_Send_Confi(List<String> list_Send_Confi) {
		this.list_Send_Confi = list_Send_Confi;
	}
	public List<String> getData_Send() {
		return data_Send;
	}
	public void setData_Send(List<String> data_Send) {
		this.data_Send = data_Send;
	}
	public String getReturn_Confi() {
		return return_Confi;
	}
	public void setReturn_Confi(String return_Confi) {
		this.return_Confi = return_Confi;
	}

	
	

}
