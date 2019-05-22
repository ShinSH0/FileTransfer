package com.dgsw.graphic.gui;

import java.awt.*;
import javax.swing.*;

import com.dgsw.database.DBmain;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Register extends JFrame{
	private JTextField text_id;
	private JPasswordField text_pw;
	private JPasswordField text_repw;
	private JLabel lblrepaasword;
	private JButton btnNewButton;
	
	DBmain db = new DBmain();
	private JLabel err_id;
	private JLabel err_pw;
	
	public Register() {
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		JLabel lblId = new JLabel("ID");
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblId.setFont(new Font("Arial", Font.BOLD, 40));
		lblId.setBackground(Color.WHITE);
		lblId.setBounds(32, 45, 130, 43);
		getContentPane().add(lblId);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblPassword.setBackground(Color.WHITE);
		lblPassword.setBounds(25, 160, 137, 43);
		getContentPane().add(lblPassword);
		
		lblrepaasword = new JLabel("re-Password");
		lblrepaasword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblrepaasword.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblrepaasword.setBackground(Color.WHITE);
		lblrepaasword.setBounds(25, 270, 137, 43);
		getContentPane().add(lblrepaasword);
		
		err_id = new JLabel("");
		err_id.setForeground(Color.RED);
		err_id.setBackground(Color.WHITE);
		err_id.setFont(new Font("SansSerif", Font.PLAIN, 25));
		err_id.setBounds(174, 107, 398, 34);
		getContentPane().add(err_id);
		
		err_pw = new JLabel("");
		err_pw.setForeground(Color.RED);
		err_pw.setFont(new Font("SansSerif", Font.PLAIN, 25));
		err_pw.setBackground(Color.WHITE);
		err_pw.setBounds(131, 222, 441, 34);
		getContentPane().add(err_pw);
		
		text_id = new JTextField();
		text_id.setFont(new Font("SansSerif", Font.PLAIN, 40));
		text_id.setColumns(10);
		text_id.setBackground(Color.WHITE);
		text_id.setBounds(174, 36, 398, 61);
		getContentPane().add(text_id);
		
		text_pw = new JPasswordField();
		text_pw.setFont(new Font("SansSerif", Font.PLAIN, 40));
		text_pw.setEchoChar('*');
		text_pw.setBackground(Color.WHITE);
		text_pw.setBounds(174, 151, 398, 61);
		getContentPane().add(text_pw);
		
		text_repw = new JPasswordField();
		text_repw.setFont(new Font("SansSerif", Font.PLAIN, 40));
		text_repw.setEchoChar('*');
		text_repw.setBackground(Color.WHITE);
		text_repw.setBounds(174, 264, 398, 61);
		getContentPane().add(text_repw);
		
		btnNewButton = new JButton("\uD68C\uC6D0\uAC00\uC785");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ID = text_id.getText();
				String PW = String.valueOf(text_pw.getPassword());
				String rePW = String.valueOf(text_repw.getPassword());
				int err = db.register(ID, PW, rePW);
				
				if(err == -2) {
					err_id.setText("이미 존재하는 아이디입니다.");
					err_pw.setText("");
				}
				if(err == -1) {
					err_id.setText("");
					err_pw.setText("비밀번호와 확인비밀번호가 다릅니다.");
				}
				if(err == 0) {
					dispose();
				}
			}
		});
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 45));
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(32, 360, 520, 177);
		getContentPane().add(btnNewButton);
	}
}
