package com.dgsw.graphic.gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.dgsw.makeSetting;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Option extends JFrame{
	
	private String Directory;
	static File file;
	public String getDirectory() {
		return Directory;
	}
	public void setDirectory(String directory) {
		Directory = directory;
	}
	private JTextField text_Directory;
	public Option() {
		makeSetting mks = new makeSetting();
		
		JDialog dialog = new JDialog();
		
		setDirectory(mks.getSettingInfo(0));
		System.out.println(Directory);
		this.file = new File(Directory);
		dialog.getContentPane().setBackground(Color.WHITE);
		
		dialog.getContentPane().setLayout(null);
		
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		text_Directory = new JTextField();
		text_Directory.setFont(new Font("±º∏≤", Font.PLAIN, 18));
		text_Directory.setBounds(94, 10, 328, 38);
		getContentPane().add(text_Directory);
		text_Directory.setColumns(10);
		
		text_Directory.setText(Directory);
		text_Directory.repaint();
		
		JLabel lblNewLabel = new JLabel("Directory");
		lblNewLabel.setFont(new Font("±º∏≤", Font.PLAIN, 15));
		lblNewLabel.setBounds(12, 10, 70, 38);
		getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("º≥¡§ ¿˙¿Â");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser j = new JFileChooser();
				j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				j.setCurrentDirectory(file);
				int val = j.showSaveDialog(dialog);
				if(val == JFileChooser.APPROVE_OPTION) {
					file = j.getSelectedFile();
					setDirectory(file.getAbsolutePath());
					text_Directory.setText(Directory);
				}
			}
		});
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(379, 58, 43, 43);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\uC124\uC815 \uC800\uC7A5");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mks.getSetting(Directory);
				dispose();
				
			}
		});
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnNewButton_1.setBounds(12, 181, 97, 70);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("\uCDE8\uC18C");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBackground(Color.WHITE);
		btnNewButton_2.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnNewButton_2.setBounds(121, 181, 97, 70);
		getContentPane().add(btnNewButton_2);
		
		setVisible(true);
		setSize(450, 300);
	}
}
