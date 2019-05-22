package com.dgsw.graphic.gui;

import javax.swing.*;

import com.dgsw.Main;
import com.dgsw.database.DBmain;
import com.dgsw.socket.Socketmain;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Lobby extends JFrame {

	
	Socketmain socket = new Socketmain();
	DBmain db = new DBmain();
	private JTextField textField;
	String uid = null;
	
	public Lobby(String UID) {
		
		this.uid = UID;
		
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(78, 10, 394, 49);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblUid = new JLabel("UID");
		lblUid.setFont(new Font("SansSerif", Font.PLAIN, 30));
		lblUid.setBounds(12, 10, 97, 49);
		getContentPane().add(lblUid);
		
		JButton btn_mkroom = new JButton("\uBC29 \uB9CC\uB4E4\uAE30");
		btn_mkroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Filesend f = new Filesend(UID);
				db.setStatus(1, UID);
				dispose();
			}
		});
		btn_mkroom.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btn_mkroom.setBackground(Color.WHITE);
		btn_mkroom.setBounds(148, 159, 125, 92);
		getContentPane().add(btn_mkroom);
		
		JButton btn_connect = new JButton("\uC5F0\uACB0");
		btn_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lblUid.getText() != null) {
					socket.serverConnect(db.getIP(lblUid.getText()));
					System.out.println(db.getIP(lblUid.getText()));
					Filerecv recv = new Filerecv(UID);
					dispose();
				}
			}
		});
		btn_connect.setFont(new Font("SansSerif", Font.PLAIN, 20));
		btn_connect.setBackground(Color.WHITE);
		btn_connect.setBounds(11, 159, 125, 92);
		getContentPane().add(btn_connect);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("±¼¸²", Font.PLAIN, 22));
		lblNewLabel.setBounds(12, 69, 373, 55);
		getContentPane().add(lblNewLabel);
		
		lblNewLabel.setText("My UID : " + UID);
		
		setBackground(Color.WHITE);
		setSize(500,300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}

}
