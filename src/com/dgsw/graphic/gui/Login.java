package com.dgsw.graphic.gui;

import javax.swing.*;

import com.dgsw.database.DBmain;
import com.dgsw.socket.Socketmain;

import java.awt.*;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.awt.event.ActionEvent;


public class Login extends JFrame{
	private JTextField text_ID;
	private String inputID;
	private String inputPW;
	private JPasswordField text_pw;
	private int UID;

	
	DBmain db = new DBmain();
	
	public Login() {
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setSize(600,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		JLabel message_warn = new JLabel("");
		message_warn.setBackground(Color.WHITE);
		message_warn.setForeground(Color.RED);
		message_warn.setFont(new Font("SansSerif", Font.PLAIN, 25));
		message_warn.setBounds(44, 499, 498, 52);
		getContentPane().add(message_warn);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 40));
		lblNewLabel.setBounds(36, 37, 76, 43);
		getContentPane().add(lblNewLabel);
		
		text_ID = new JTextField();
		text_ID.setBackground(Color.WHITE);
		text_ID.setFont(new Font("SansSerif", Font.PLAIN, 40));
		text_ID.setBounds(115, 26, 398, 61);
		getContentPane().add(text_ID);
		text_ID.setColumns(10);
		
		text_pw = new JPasswordField();
		text_pw.setFont(new Font("SansSerif", Font.PLAIN, 40));
		text_pw.setEchoChar('*');
		text_pw.setBackground(Color.WHITE);
		text_pw.setBounds(115, 150, 398, 61);
		getContentPane().add(text_pw);
		
		JLabel lblPw = new JLabel("PW");
		lblPw.setFont(new Font("Arial", Font.BOLD, 40));
		lblPw.setBackground(Color.WHITE);
		lblPw.setBounds(25, 161, 76, 43);
		getContentPane().add(lblPw);
		
		JButton btn_login = new JButton("\uB85C\uADF8\uC778");
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputID = text_ID.getText();
				inputPW = String.valueOf(text_pw.getPassword());
				
				UID = db.login(inputID, inputPW);
				System.out.println("Login");
				
				if(UID == -1) {
					message_warn.setText("잘못된 아이디 혹은 비밀번호 입니다.");
				}
				
				else {
					System.out.println(UID);
					Lobby lobby = new Lobby(String.valueOf(UID));
					//Maintitle maintitle = new Maintitle(String.valueOf(UID));
				//	maintitle.setVisible(true);
					dispose();
				}
				
			}
		});
		btn_login.setFont(new Font("SansSerif", Font.BOLD, 35));
		btn_login.setBackground(Color.WHITE);
		btn_login.setBounds(36, 248, 506, 61);
		getContentPane().add(btn_login);
		
		JButton btn_reg = new JButton("\uD68C\uC6D0\uAC00\uC785");
		btn_reg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register register = new Register();
				register.setVisible(true);
				register.setSize(600,600);
			}
		});
		btn_reg.setFont(new Font("SansSerif", Font.BOLD, 35));
		btn_reg.setBackground(Color.WHITE);
		btn_reg.setBounds(36, 336, 506, 61);
		getContentPane().add(btn_reg);
		
		JLabel lblIP = new JLabel("");
		lblIP.setFont(new Font("굴림", Font.PLAIN, 17));
		lblIP.setBounds(36, 419, 506, 52);
		getContentPane().add(lblIP);
		
		try {
			
			
			lblIP.setText(InetAddress.getLocalHost().getHostAddress());
		}catch (Exception e) {}
		
		
	}

	public String getPW() {
		return inputPW;
	}

	public void setPW(String pW) {
		inputPW = pW;
	}

	public String getID() {
		return inputID;
	}

	public void setID(String iD) {
		inputID = iD;
	}

	public int getUID() {
		return UID;
	}

	public void setUID(int uID) {
		UID = uID;
	}
}
