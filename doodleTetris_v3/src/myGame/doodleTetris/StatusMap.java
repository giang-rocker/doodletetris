package myGame.doodleTetris;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import myGame.doodleTetris.framework.Game;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class StatusMap {
	public  int mapID;
	public  int unlock;
	public  int time;
	public  int score;
	public  int rate;
	public  boolean firstTime=true;
	public  String[] data;
	
	public Game game;
	
	public StatusMap (Game game)
	{
		this.game = game;
	}
	
	public  void setVariable(int ID, int unLock,int Time, int Score, int Rate){
		mapID = ID;
		unlock = unLock;
		time = Time;
		score = Score;
		rate = Rate;
	}
	
	public  void saveStatus(String filename, LevelInfo lvInfo){
		
		try {
			FileOutputStream fos = game.getContext().openFileOutput(filename,Context.MODE_WORLD_READABLE);
			fos.write((lvInfo.id+" ").getBytes());
			fos.write((lvInfo.timeRecord+" ").getBytes());
			fos.write((lvInfo.scoreRecord+" ").getBytes());
			fos.write((lvInfo.rate+" ").getBytes());
			fos.write((lvInfo.isUnlock+"").getBytes());
			fos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  void saveStatus(String filename){
		try {
			FileOutputStream fos = game.getContext().openFileOutput(filename,Context.MODE_WORLD_READABLE);
			fos.write((mapID+" ").getBytes());
			fos.write((time+" ").getBytes());
			fos.write((score+" ").getBytes());
			fos.write((rate+" ").getBytes());
			fos.write((unlock+"").getBytes());
			fos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Boolean openStatus(String filename){
		try {
			FileInputStream fis =game.getContext().openFileInput(filename);
			String str="";
			byte[] buffer = new byte[fis.available()];
			while(fis.read(buffer)!=-1){
				str = new String(buffer).trim();
			}
			data = str.split(" ");
			fis.close();
			System.out.println("File "+filename+" is read; id="+data[0]);
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
		catch(Exception e){
			return false;
		}
		return true;
	}
	public  void setFirstStatus(){
		System.out.println("Call function setFirstStatus");
		
		if(firstTime){
			String pathDir="Map";
			try {
				String list[]= game.getAsset().list(pathDir);
				if(list!=null){
					for(int i=0;i<list.length;i++){
						setVariable(i, 1, 1, 1, 1);
						saveStatus(list[i]);
						System.out.println("Create file: "+list[i]);
						Log.d("System.out.println","Create file");
					}
				}
				else
					System.out.println("List is null");
		
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Cannot create files");
			}
			firstTime=false;
		}
	}
	
	public String[] getData () {
		return data;
	}
	
	
	// Giang - try to write text file on external Memory
	/*
	public void isMapInfoExist (){
		File root = Environment.getExternalStorageDirectory();
		String path = root+"/DoodleTetris/";
		boolean exists = (new File(path).exists());
		if (!exists) {new File(path).mkdirs();}
		
		File mapInfo = new File(path+"mapInfo"+".txt");
		if (!mapInfo.exists()){
		try {
			mapInfo.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileWriter info = null ;
		try {
			info = new FileWriter(mapInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter outer = new BufferedWriter(info);
		// Write log entries to file
		
		try {
			String list[]= game.getAsset().list("Map");
		
			if(list!=null){
				for(int i=0;i<list.length;i++){
					outer.write( Integer.toString(i) +" 0 0 0 0\n");
				}
			}
			else
				System.out.println("List is null");
	
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cannot create files");
		}
		
		try {
			outer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else {
			Log.d("exst", "exst");
			
		}
	}
	
	*/
	
	//test luu the nho
	public void saveMapInfo (int id, int score, int time, int rate, boolean isUnlock){
		File root = Environment.getExternalStorageDirectory();
		String path = root+"/DoodleTetris/";
		boolean exists = (new File(path).exists());
		if (!exists) {new File(path).mkdirs();}
		
		File mapInfo = new File(path+"mapInfo");
		if (!mapInfo.exists()){
		try {
			mapInfo.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileWriter info = null ;
		try {
			info = new FileWriter(mapInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter outer = new BufferedWriter(info);
		// Write log entries to file
		
		try {
			String list[]= game.getAsset().list("Map");
		
			if(list!=null){
				for(int i=0;i<list.length;i++){
					outer.write( Integer.toString(i) +" 0 0 0 0\n");
				}
			}
			else
				System.out.println("List is null");
	
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cannot create files");
		}
		
		try {
			outer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else {
			Log.d("exst", "exst");
			
		}
	}
	
	public void loadToListLevelInfo (LevelInfo[] lvInfo) {
		
		
	}
}
