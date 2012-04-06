package myGame.doodleTetris;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import myGame.doodleTetris.framework.AndroidGame;
import android.content.Context;
import android.content.res.AssetManager;


public class StatusMap {
	public static int mapID;
	public static int lock;
	public static int time;
	public static int score;
	public static int rate;
	public static String[] data;
	public static void setVariable(int ID, int Lock,int Time, int Score, int Rate){
		mapID = ID;
		lock = Lock;
		time = Time;
		score = Score;
		rate = Rate;
	}
	public static void saveStatus(String path){
		try {
			FileOutputStream fos = AndroidGame.getContext().openFileOutput(path,Context.MODE_WORLD_READABLE);
			fos.write((mapID+" ").getBytes());
			fos.write((lock+" ").getBytes());
			fos.write((time+" ").getBytes());
			fos.write((score+" ").getBytes());
			fos.write((rate+" ").getBytes());
			fos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void openStatus(String path){
		try {
			FileInputStream fis =AndroidGame.getContext().openFileInput(path);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void setFirstStatus(AssetManager am){
		String pathDir=  "/Map";
		int id=1;
		try {
			String list[]= am.list(pathDir);
			if(list.length==0){
				System.out.println("Lenght is zero");
			}
			else
				System.out.println("List's lenght: "+list.length+" ");
			if(list!=null){
				for(int i=0;i<list.length;i++){
					setVariable(id, 1, 0, 0, 0);
					saveStatus(pathDir+"/"+list[i]);
					System.out.println("Creat file: "+pathDir+"/"+list[i]);
					id++;
				}
			}
			else
				System.out.println("List is null");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Cannot create files");
		}
	}
	
	
}
