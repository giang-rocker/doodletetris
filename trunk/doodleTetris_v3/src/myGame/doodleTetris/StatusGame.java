package myGame.doodleTetris;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;

public class StatusGame {
	
	public int intervalTimer;
	public int score;
	public int level;
	public Block.BlockType statusBoard[][]= new Block.BlockType[Board.BOARD_WIDTH][Board.BOARD_HEIGHT];
	public String data[];
	public Game game;
	public StatusGame(Game game){
		this.game = game;
	}
	public void saveStatus(String filename){
		try {
			FileOutputStream fos = game.getContext().openFileOutput(filename, Context.MODE_WORLD_READABLE);
			fos.write((intervalTimer+" ").getBytes());
			fos.write((score+" ").getBytes());
			fos.write((score+" ").getBytes());
			for(int col=0;col<Board.BOARD_WIDTH;col++){
				for(int row=0;row<Board.BOARD_HEIGHT;row++){
					fos.write((statusBoard[col][row]+" ").getBytes());
				}
			}
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void loadStatus(String filename){
		try {
			FileInputStream fis = game.getContext().openFileInput(filename);
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
	public String[] getData(String filename){
		loadStatus(filename);
		return data;
	}
	

}
