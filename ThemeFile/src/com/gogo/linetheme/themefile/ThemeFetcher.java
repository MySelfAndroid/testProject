package com.gogo.linetheme.themefile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;
import android.util.Log;

public class ThemeFetcher {
	
	private List<File> themeFiles;
	public List<File> getAllThemeFiles(){
		
		themeFiles = new ArrayList<File>(); 
		String downloadFolderStr = Environment.getExternalStorageDirectory()+File.separator+"Download";
		
		File downloadFolder = new File(downloadFolderStr);
		
		if(downloadFolder !=null && downloadFolder.isDirectory() && downloadFolder.exists()){
			this.getNextSearch(downloadFolder);
		}else{
			
			downloadFolderStr = Environment.getExternalStorageDirectory()+File.separator+"download";
			
			File downloadFolder2 = new File(downloadFolderStr);
			
			if(downloadFolder2 !=null && downloadFolder2.isDirectory() && downloadFolder2.exists()){
				
				this.getNextSearch(downloadFolder2);
				
			}
		}
		
		return themeFiles;
	}
	
	private void getNextSearch(File downloadFolder){
		
		File[] subDictory = downloadFolder.listFiles();
		
		for(File subFile:subDictory){
			Log.d("@@@", subFile.getName());
			if(subFile.isFile()){
				
				if(subFile.getName().indexOf("themefile") != -1){
					themeFiles.add(subFile);
					Log.d("@@@","find theme: "+ subFile.getName());
				}
			}else{
				
				if(subFile.isDirectory()){
					this.getNextSearch(subFile);
				}
			}
			
		}
		
	}
}
