package com.dgsw.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;

import com.dgsw.graphic.gui.Filerecv;




public class Socketmain {
	
	public static ServerSocket server;
	public static Socket socket;
	
	static InputStream in;
	static OutputStream out;
	static DataInputStream dis;
	static DataOutputStream dos;
	
	
	
	public int serverOpen() {
		try {

		server = new ServerSocket(11254);
		
		System.out.println(server);
		
		socket = server.accept();
		
		
		in = socket.getInputStream();
		out = socket.getOutputStream();
		dis = new DataInputStream(in);
		dos = new DataOutputStream(out);
		
		}catch(IOException ie) {}     
		return 0;
	}
	public int serverConnect(String IP) {
		if(IP != null) {
			try {
				socket  = new Socket(IP, 11254);
				in = socket.getInputStream();
				out = socket.getOutputStream();
				dis = new DataInputStream(in);
				dos = new DataOutputStream(out);
			
			}catch(IOException ie) {}
		}
		else {
			return -1;
		}
		return 0;
	}
	
	public int sendData(String data) {
		OutputStreamWriter osw = null;
		BufferedWriter writer = null;
		try {
			osw = new OutputStreamWriter(out);
			writer = new BufferedWriter(osw);
			writer.write(data);
			writer.newLine();
			writer.flush();
			System.out.println(data);
		}catch(IOException ie) {
			ie.printStackTrace();
		}
		return 0;
	}
	
	public String recvData() {
		InputStreamReader isr = null;
		String recvData = null;
		BufferedReader reader = null;
		try {
			isr = new InputStreamReader(in);
			reader = new BufferedReader(isr);
			recvData = reader.readLine();
			
			System.out.println(recvData);
			
		}catch(IOException ie) {
			ie.printStackTrace();
		}
		return recvData;
	}
		


	
	public void sendFile(File file) {
		System.out.println("sendfile");
		FileInputStream fin = null;
		byte[] buffer = new byte[2048];
		int t = 0;
		try {		
			fin = new FileInputStream(file);
			int readcount = 0;
			while((readcount = fin.read(buffer)) != -1) {
				out.write(buffer,0,2048);
				System.out.println(buffer);
				if(readcount > file.length()) break;
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void recvFile(File file, String size) {
		System.out.println("recvfile");
		DataInputStream dis = null;
		FileOutputStream fos = null;
		long length = 0;
		System.out.println(file.getAbsolutePath());
		try {
		dis = new DataInputStream(in);
		fos = new FileOutputStream(file);
		
		Filerecv f = new Filerecv();
		file.createNewFile();
		
		byte[] buffer = new byte[2048];
		int readcount = 0;
		
		while((readcount = dis.read(buffer,0,2048)) != -1) {
			
			System.out.println("file : " + buffer);
			length += readcount;
			double progress = (double)((double)length / (double)Long.parseLong(size));
			f.Filerecv.setText(String.format("%s : %d / %s ( %.2f %% )", file.getName(), length, size, progress*100));
			f.Filerecv.repaint();
			fos.write(buffer,0,readcount);
			
			if(progress*100 >= 100) break;
		}
		f.Filerecv.setText("");
		fos.close();
		
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}
