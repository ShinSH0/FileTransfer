package com.dgsw.database;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;

import com.dgsw.makeSetting;
import com.dgsw.socket.Socketmain;


public class DBmain {
	
	
	public final String driver = "com.mysql.jdbc.Driver";
	
	
	static Statement state;
	public static int uid;

	makeSetting mks = new makeSetting();
	public String dbURL = "jdbc:mysql://"+ mks.getSettingInfo(1)+":3306" + "/java";
	public void Connect() {
		try {
			while(true) {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(dbURL,"Java","");
			state = conn.createStatement();
			System.out.println("DB연결 성공!" + state);
			if (state != null)
				break;
			}
		}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			catch (SQLException se) {
				se.printStackTrace();
			}

			
	}
	
	public int login(String id, String pw) {
		ResultSet rs;
		try {
			rs = state.executeQuery("select * from userinfo where ID = '" + id + 
					"'and PW = '" + pw + "'");
			if (rs.next()) {
				if(rs.getString("ID") == null || rs.getString("PW") == null) {
					return -1;
				}
				else {
					// login success
					return rs.getInt("uid");
				}
			}
			rs.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
		
		return -1;
	}
	
	public int register(String id, String pw, String repw) {
		ResultSet rs;
			try {
				rs = state.executeQuery("select * from userinfo where ID = '" + id + "'");
				if(rs.next()) {
					return -2; // 아이디가 중복일 때
				}
				else if(!(pw.equals(repw))) {
					return -1; // 비밀번호와 확인비밀번호가 맞지 않을 때
				}
				else {
					state.execute("insert into userinfo (ID, PW) values ('" + id + "','" + pw + "')");
					return 0;
				}
		}catch(SQLException se) {
			se.printStackTrace();
		}
		return 0;
	}

	
	public int setStatus(int online, String UID) {
		ResultSet rs;
		try {
			if(online == 1) {
			InetAddress ip = InetAddress.getLocalHost();
//			rs = state.executeQuery("insert into uidstatus (UID, Online, IP) values ('" + UID + "'," + "true" + ",'" + 
//			ip.getHostAddress() + "')");
			
			int err = state.executeUpdate(String.format("insert into uidstatus (UID, Online, IP) values ('%s', true, '%s')", UID, ip.getHostAddress()));
			
			System.out.println("Online");
			}
			else if(online == 2) {
				int err = state.executeUpdate(String.format("update uidstatus set Online = false where UID = '%s'", UID));
			}
			else {
				state.executeUpdate("delete from uidstatus where UID = " + UID);
				System.out.println("Offline");
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		return 0;
	}
	
	public String getIP(String UID) {
		String IP = null;
		ResultSet rs;
		try {
			rs = state.executeQuery("select * from uidstatus where UID =" + UID);
			if(rs.next()) {
				IP = rs.getString("IP");
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}
		return IP;
	}
	
}
