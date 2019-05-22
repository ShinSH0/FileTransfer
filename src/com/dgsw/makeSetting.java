package com.dgsw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class makeSetting {

	static List<String> setting = new ArrayList<>();
	
	public void getSetting(String directory) {
		File thisfile = new File(".");
		
		try {
			
			File settings = new File(thisfile.getCanonicalPath() + "\\settings.txt");
			
			
			BufferedWriter w;
			BufferedReader r;
			System.out.println(settings.getPath());
			
			if(!(settings.exists())) {
				settings.createNewFile();
		
			}
			r = new BufferedReader(new FileReader(settings));
			w = new BufferedWriter(new FileWriter(settings));
			
			if(r.readLine() == null) {
			w.append("FileDirectory ~ " + settings.getParentFile()+"\\\r\n" +
					"DBServerIP ~ 10.80.162.41");
			}
			
			if(directory != null) {
				w.close();
				r.close();
				settings.delete();
				settings.createNewFile();
				w = new BufferedWriter(new FileWriter(settings));
				r = new BufferedReader(new FileReader(settings));
				w.write("FileDirectory ~ " + directory + "\\\r\n" +
						"DBServerIP ~ 10.80.162.41");	
				System.out.println(directory);
			}
			
			w.flush();
			w.close();
			r.close();
			
			String s;

			setting.clear();
			r = new BufferedReader(new FileReader(settings));
			
			while((s = r.readLine()) != null) {
				
				String[] info = s.split("~");
				setting.add(info[1].trim());
				System.out.println(info[1]);
			}
			
			r.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getSettingInfo(int index) {
		return setting.get(index);
	}
}
