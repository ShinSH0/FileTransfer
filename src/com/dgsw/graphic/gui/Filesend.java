package com.dgsw.graphic.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.dgsw.database.DBmain;
import com.dgsw.socket.Socketmain;

public class Filesend extends JFrame implements Runnable{
	
	JScrollPane scroll;
	JPanel myfile;
	JPanel[] myfile_panel = new JPanel[10];
	JButton[] btnNewButton_1 = new JButton[10];
	JButton[] btnNewButton_2 = new JButton[10];
	static JLabel[] lblSize = new JLabel[10];
	static JLabel[] lblDirectory = new JLabel[10];
	JLabel lblNewLabel_1;
	
	int i = 0;
	int myheight = 10;
	private JButton btnNewButton;
	
	Socketmain socket = new Socketmain();
	public static File[] sendFile = new File[10];
	
	boolean filesend = false;
	
	Thread thread;
	
	String UID;
	int online = 0;
	
	DBmain db = new DBmain();
	
	public Filesend() {}
	public Filesend(String UID) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		thread = new Thread(this);
		thread.start();
		
		getContentPane().setBackground(Color.WHITE);
		setSize(700, 400);
		getContentPane().setLayout(null);
		
		this.UID = UID;
		
		
		
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
		
		JButton Addfile = new JButton("+");
		Addfile.setBackground(Color.WHITE);
		Addfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				socket.sendData("AddPanel");
				makePanel();
			}
		});
		Addfile.setFont(new Font("±¼¸²", Font.BOLD, 20));
		Addfile.setBounds(624, 10, 48, 48);
		getContentPane().add(Addfile);
		
		btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filesend = true;
				System.out.println(filesend);
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 20));
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(12, 289, 115, 62);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("\uC0C1\uB300\uBC29 \uC0C1\uD0DC :");
		lblNewLabel.setBounds(421, 336, 79, 15);
		getContentPane().add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Offline");
		lblNewLabel_1.setBounds(499, 336, 57, 15);
		getContentPane().add(lblNewLabel_1);
		
		setVisible(true);
	}
	

	@Override
	public void run() {
		socket.serverOpen();
		int key = 0;
		System.out.println("while start");
		while(true) {
			if(socket.socket != null) {
				lblNewLabel_1.setText("Online");
				lblNewLabel_1.repaint();
				online = 1;
			}
			
			if(online == 1 && key == 0) {
				db.setStatus(0, UID);
				key = 1;
			}
			
			if(filesend == true) {
				int i = 0;
				while(i < 10) {
					if(sendFile[i] != null) {
						socket.sendData("start");
						socket.sendData(String.format("Filestart :%s<%s<%s", sendFile[i].getName(), sendFile[i].length(),i));
						socket.sendFile(sendFile[i]);
						
						try {
							thread.sleep(500);
							}catch(InterruptedException ie) {}
						
					}
					i++;
				}
				filesend = false;
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
		
		btnNewButton_1[num] = new JButton("-");
		btnNewButton_1[num].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myfile.remove(myfile_panel[num]);
				myfile.repaint();
				myfile_panel[num] = null; 
				push();
				myheight -= 70;
				myfile.setPreferredSize(new Dimension(560, myheight + 72));
				
				socket.sendData("RemovePanel :" + num);
				sendFile[num] = null;
			}
		});
		btnNewButton_1[num].setBackground(Color.WHITE);
		btnNewButton_1[num].setVerticalAlignment(SwingConstants.BOTTOM);
		btnNewButton_1[num].setFont(new Font("Arial", Font.BOLD, 50));
		btnNewButton_1[num].setBounds(438, 1, 80, 58);
		myfile_panel[i].add(btnNewButton_1[num]);
		
		lblSize[num] = new JLabel("Size :");
		lblSize[num].setFont(new Font("±¼¸²", Font.BOLD, 12));
		lblSize[num].setBounds(12, 5, 300, 30);
		myfile_panel[i].add(lblSize[num]);
		
		lblDirectory[num] = new JLabel("Directory :");
		lblDirectory[num].setFont(new Font("±¼¸²", Font.BOLD, 12));
		lblDirectory[num].setBounds(12, 30, 300, 30);
		myfile_panel[i].add(lblDirectory[num]);
		
		btnNewButton_2[num] = new JButton("...");
		btnNewButton_2[num].setFont(new Font("Arial", Font.BOLD, 30));
		btnNewButton_2[num].setBackground(Color.WHITE);
		btnNewButton_2[num].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileChooseDialog f = new FileChooseDialog(num);
				
			}
		});
		btnNewButton_2[num].setBounds(359, 1, 80, 58);
		myfile_panel[i].add(btnNewButton_2[num]);

		i++;
		
		myfile.setPreferredSize(new Dimension(560, myheight+ 72));
		
		myheight += 70;
		
		myfile.repaint();

	}
	
	static void ChangeFileInfo(int num, String Directory) {
		sendFile[num] = new File(Directory);
		
		lblDirectory[num].setText("Directory : " + sendFile[num].getPath() + sendFile[num].getName());
		
		lblSize[num].setText("Size : "+String.valueOf((sendFile[num].length() )) + "Byte"  );
		System.out.println(sendFile[num].getPath());
		System.out.println(String.valueOf((sendFile[num].length())));
		}
}
