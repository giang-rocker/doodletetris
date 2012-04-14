package myGame.doodleTetris;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import myGame.doodleTetris.framework.Game;

import android.content.Context;

public class Setting {
	String data[];
	public Game game;
	public Setting(Game game)
	{
		this.game = game;
	}
	public void saveSetting(boolean music, float volMusic, boolean sound, float volSound,boolean engLanguage){
		try {
			FileOutputStream fos = game.getContext().openFileOutput("setting",Context.MODE_WORLD_READABLE);
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
			FileInputStream fis =game.getContext().openFileInput("setting");
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
