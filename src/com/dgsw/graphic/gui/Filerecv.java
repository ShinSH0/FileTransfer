package com.dgsw.graphic.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.dgsw.makeSetting;
import com.dgsw.database.DBmain;
import com.dgsw.socket.Socketmain;

public class Filerecv extends JFrame implements Runnable{
	
	JScrollPane scroll;
	JPanel myfile;
	JPanel[] myfile_panel = new JPanel[10];
	JButton[] btnNewButton_1 = new JButton[10];
	JButton[] btnNewButton_2 = new JButton[10];
	JLabel[] lblSize = new JLabel[10];
	
	Socketmain socket = new Socketmain();
	String recv = null;
	String send = null;
	File[] file = new File[10];
	
	
	int i =0;
	int myheight = 10;
	
	makeSetting mks = new makeSetting();
	String Directory = mks.getSettingInfo(0);
	private JLabel lblNewLabel;
	private JLabel lblOnline;
	public static JLabel Filerecv;
	
	Thread thread;
	
	String UID;

	public Filerecv() {}
	public Filerecv(String UID) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.WHITE);
		setSize(700,400);
		getContentPane().setLayout(null);
		setVisible(true);
		
		this.UID = UID;
		
		thread = new Thread(this);
		thread.start();
		
		myfile = new JPanel();
		myfile.setBackground(Color.WHITE);
		
		scroll = new JScrollPane(myfile);
		scroll.setBounds(12, 12, 600, 152);
		scroll.getVerticalScrollBar().setUnitIncrement(20);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		myfile.setBounds(12, 12, 600, 152);
		myfile.setLayout(null);
		getContentPane().add(scroll);
		
		JButton btnNewButton = new JButton("Option");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Option o = new Option();
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 20));
		btnNewButton.setBounds(12, 263, 97, 88);
		getContentPane().add(btnNewButton);
		
		lblNewLabel = new JLabel("\uD604\uC7AC \uC0C1\uD0DC :");
		lblNewLabel.setBounds(206, 336, 68, 15);
		getContentPane().add(lblNewLabel);
		
		lblOnline = new JLabel("Online");
		lblOnline.setBounds(273, 336, 57, 15);
		getContentPane().add(lblOnline);
		
		Filerecv = new JLabel("");
		Filerecv.setBounds(22, 174, 650, 22);
		getContentPane().add(Filerecv);
		
	}

	@Override
	public void run() {
		while(true) {
		recv = socket.recvData();
		System.out.println(recv);
		if(recv.contains("Filestart")) {
			
			Directory = mks.getSettingInfo(0);
			
			String[] s = recv.split(":")[1].split("<");
			
			
			System.out.println(s[0] + " < " + s[1] + " < " + s[2]);
			
			int filenum = Integer.parseInt(s[2]);
			file[filenum] = new File(Directory.trim() + s[0].trim());
			System.out.println("받아올 파일 : " + file[filenum].getPath() + " : " + s[1]);
			socket.recvFile(file[filenum], s[1]);
			System.out.println("파일저장");

		}
		if(recv.contains("NewFile")) {
			String s = recv.split(":")[1];
			String[] s2 = s.split("<");
			int filenum = Integer.parseInt(s2[0]);
			String filename = s2[1];
			
			lblSize[filenum].setText(String.format("File : %s", filename));
			
			
			
			}
		
		if(recv.contains("AddPanel")) {
			makePanel();
			}
		
		if(recv.contains("RemovePanel")) {
			String s = recv.split(":")[1];
			myfile.remove(myfile_panel[Integer.parseInt(s)]);
			myfile_panel[Integer.parseInt(s)] = null;
			push();
			myheight -= 70;
			myfile.setPreferredSize(new Dimension(560, myheight + 72));
			myfile.repaint();
			}
		
		
		}
		
	}
	
	void push() {
		int index = 0;
		
		while(myfile_panel[index] != null) 
			index++;
		
		
			for(int _tmp = index+1 ; _tmp < 10 ; _tmp ++) {
				if(myfile_panel[_tmp] != null) {
					Rectangle r = myfile_panel[_tmp].getBounds();
					r.y -= 70;
					myfile_panel[_tmp].setBounds(r);
				}
			}
		
		myfile.repaint();
	}
	
	void makePanel() {
		
		
		for(i=0;i<10;i++) {
			if(myfile_panel[i] == null)
				break;
		}
		int num = i;
		myfile_panel[i] = new JPanel(); 
		myfile.add(myfile_panel[i]);
		myfile_panel[i].setBorder(new LineBorder(new Color(0, 0, 0)));
		myfile_panel[i].setBackground(Color.WHITE);
		myfile_panel[i].setBounds(12, myheight, 520, 60);
		myfile_panel[i].setLayout(null);
		
//		btnNewButton_1[num] = new JButton("-");
//		btnNewButton_1[num].addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				myfile.remove(myfile_panel[num]);
//				myfile.repaint();
//				myfile_panel[num] = null; 
//				push();
//				myheight -= 70;
//				myfile.setPreferredSize(new Dimension(560, myheight + 72));
//			}
//		});
//		btnNewButton_1[num].setBackground(Color.WHITE);
//		btnNewButton_1[num].setVerticalAlignment(SwingConstants.BOTTOM);
//		btnNewButton_1[num].setFont(new Font("Arial", Font.BOLD, 50));
//		btnNewButton_1[num].setBounds(438, 1, 80, 58);
//		myfile_panel[i].add(btnNewButton_1[num]);
		
		lblSize[num] = new JLabel("Size :");
		lblSize[num].setFont(new Font("굴림", Font.BOLD, 12));
		lblSize[num].setBounds(12, 5, 300, 30);
		myfile_panel[i].add(lblSize[num]);
		

		i++;
		
		myfile.setPreferredSize(new Dimension(560, myheight+ 72));
		
		myheight += 70;
		
		myfile.repaint();
		
		
	}
}
