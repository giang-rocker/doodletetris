package myGame.doodleTetris;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import myGame.doodleTetris.framework.Game;

import android.content.Context;

public class Setting {
	String data[];
	public Context context;
	public Setting(Context context)
	{
		this.context = context;
	}
	public void saveSetting(boolean music, int volMusic, boolean sound, int volSound,boolean engLanguage){
		try {
			FileOutputStream fos = context.openFileOutput("setting",Context.MODE_WORLD_READABLE);
			fos.write((music+" ").getBytes());
			fos.write((volMusic+" ").getBytes());
			fos.write((sound+" ").getBytes());
			fos.write((volSound+" ").getBytes());
			fos.write((engLanguage+"").getBytes());
			fos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean loadSetting(){
		try {
			FileInputStream fis =context.openFileInput("setting");
			String str="";
			byte[] buffer = new byte[fis.available()];
			while(fis.read(buffer)!=-1){
				str = new String(buffer).trim();
			}
			data = str.split(" ");
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File is not found");
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String [] getData(){
		return data;
	}
	
}
