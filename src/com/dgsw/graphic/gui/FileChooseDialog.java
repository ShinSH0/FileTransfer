package com.dgsw.graphic.gui;

import javax.swing.*;
import java.awt.Color;
import java.awt.FileDialog;

import javax.swing.border.LineBorder;

import com.dgsw.socket.Socketmain;

import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionEvent;

public class FileChooseDialog implements DropTargetListener{
	
	private String Directory = null;
	
	private JTextField text_directory;
	
	DropTarget target;

	public FileChooseDialog(int num) {
		JDialog dialog = new JDialog();
		dialog.getContentPane().setBackground(Color.WHITE);
		
		dialog.getContentPane().setLayout(null);
		
		JPanel panel_dragdrop = new JPanel();
		panel_dragdrop.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_dragdrop.setBackground(Color.WHITE);
		panel_dragdrop.setBounds(12, 10, 410, 155);
		dialog.getContentPane().add(panel_dragdrop);
		panel_dragdrop.setLayout(null);
		
		target = new DropTarget(panel_dragdrop,DnDConstants.ACTION_COPY_OR_MOVE,
									(DropTargetListener)this, true, null);
		
		
		JLabel lbldrag = new JLabel("Drag & Drop");
		lbldrag.setHorizontalAlignment(SwingConstants.CENTER);
		lbldrag.setFont(new Font("Arial", Font.BOLD, 40));
		lbldrag.setBounds(12, 10, 386, 135);
		panel_dragdrop.add(lbldrag);
		
		JLabel lblinfo = new JLabel("\uBCF4\uB0B4\uACE0 \uC2F6\uC740 \uD30C\uC77C\uC744 \uC5EC\uAE30\uC5D0 \uB193\uC73C\uC138\uC694");
		lblinfo.setFont(new Font("굴림", Font.BOLD, 12));
		lblinfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblinfo.setBounds(90, 111, 235, 23);
		panel_dragdrop.add(lblinfo);
		
		JLabel lbldir = new JLabel("\uD30C\uC77C\uACBD\uB85C :");
		lbldir.setBounds(12, 237, 57, 15);
		dialog.getContentPane().add(lbldir);
		lbldir.setFont(new Font("SansSerif", Font.PLAIN, 12));
		
		JButton btnOpenFile = new JButton("\uC9C1\uC811 \uC120\uD0DD\uD558\uAE30");
		btnOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileDialog filedialog = new FileDialog(dialog, "파일선택", FileDialog.LOAD);
				filedialog.setVisible(true);
				setDirectory(filedialog.getDirectory() + filedialog.getFile());
				text_directory.setText(Directory);
				
				
			}
		});
		btnOpenFile.setBackground(Color.WHITE);
		btnOpenFile.setFont(new Font("SansSerif", Font.PLAIN, 25));
		btnOpenFile.setBounds(12, 175, 410, 52);
		dialog.getContentPane().add(btnOpenFile);
		
		JButton btnApply = new JButton("\uD655\uC778");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Directory != null) {
					Filesend.ChangeFileInfo(num, Directory);
					File temp = new File(Directory);
					Socketmain s = new Socketmain();
					s.sendData(String.format("NewFile :%s<%s", num,temp.getName()));
				}
				dialog.dispose();
			}
		});
		btnApply.setBackground(Color.WHITE);
		btnApply.setFont(new Font("SansSerif", Font.BOLD, 30));
		btnApply.setBounds(12, 264, 410, 74);
		dialog.getContentPane().add(btnApply);
		
		text_directory = new JTextField();
		text_directory.setBounds(81, 233, 341, 21);
		dialog.getContentPane().add(text_directory);
		text_directory.setColumns(10);
		dialog.setSize(450,400);
		dialog.setVisible(true);
	}

	public String getDirectory() {
		return Directory;
	}

	public void setDirectory(String directory) {
		Directory = directory;
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		if((dtde.getDropAction()& DnDConstants.ACTION_COPY_OR_MOVE) != 0) {
			dtde.acceptDrop(dtde.getDropAction());
			Transferable tr = dtde.getTransferable();
			try {
			List list = (List)tr.getTransferData(DataFlavor.javaFileListFlavor);
			File file  = (File)list.get(0);
			Directory = file.getPath();
			text_directory.setText(Directory);
			}catch(IOException ioe) {} catch (UnsupportedFlavorException ufe) {}
		}
		
	}
}
