package com.dgsw;



import com.dgsw.database.DBmain;
import com.dgsw.graphic.gui.Login;


public class Main {
	
	
	
	public static void main(String[] args) {
		
		makeSetting mks = new makeSetting();
		mks.getSetting(null);
		
		DBmain db = new DBmain();
		
		
		
		db.Connect();
		
		
		Login l = new Login();
		
		
		
		
		Login login = new Login();
		login.setVisible(true);
		
	}
}
