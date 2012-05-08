package myGame.doodleTetris;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;

public class HighScore {
	String data[][]= new String [3][5];
	public Context context;
	int minRow = 0;
	public HighScore(Context context)
	{
		this.context = context;
	}
	public void saveHighScore(){
		try {
			FileOutputStream fos = context.openFileOutput("highscore",Context.MODE_WORLD_READABLE);
			for(int row=0;row<5;row++){
				fos.write((data[0][row]+" ").getBytes());
				fos.write((data[1][row]+" ").getBytes());
				fos.write((data[2][row]+"@").getBytes());
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
	public boolean loadHighScore(){
		String temp[];
		String t[];
		try {
			FileInputStream fis =context.openFileInput("highscore");
			String str="";
			byte[] buffer = new byte[fis.available()];
			while(fis.read(buffer)!=-1){
				str = new String(buffer).trim();
			}
			System.out.println("Chuoi luu "+str);
			str=str.substring(0,str.length()-1);//Bo ki tu @ o cuoi chuoi
			System.out.println("Chuoi luu(bo @) "+str);
			temp = str.split("@");
			for(int i=0;i<temp.length;i++){
				t=temp[i].split(" ");
				System.out.println("Temp String "+temp[i]);
				for(int j =0;j<t.length;j++){
					data[j][i]=t[j];
					System.out.println("Tung so: "+t[j]);
				}
				
			}
			System.out.println("First data: "+data[0][0]+" "+data[1][0]+" "+data[2][0]);
			System.out.println("Second data: "+data[0][1]+" "+data[1][1]+" "+data[2][1]);
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
	
	public void setFirstData(){
		if(!loadHighScore()){
			for(int row=0;row<5;row++)
				for(int col=0;col<3;col++)
						data[col][row]="100";
		}
		
	}
	
	public boolean checkHighScore(int Score){
		for(int i=0;i<5;i++){
			System.out.println(data[0][i].trim()+"+"+data[1][i].trim()+"+"+data[2][i].trim()+"+");
			if(Integer.parseInt(data[0][i].trim())<Score)
				return true;
		}
		return false;
	}
	public void minRow(){
		minRow =0;
		for(int i=1;i<5;i++)
			if(Integer.parseInt(data[0][minRow].trim())>Integer.parseInt(data[0][i].trim()))
				minRow=i;
	}
	public void resetData(String Score, String Level, String Time){//Insert data duoc them vao
		//If checkHighScore return true
		minRow();
		data[0][minRow]=Score;
		data[1][minRow]=Level;
		data[2][minRow]=Time;
	}
	public  String[] dataToSingleArray(int row){
		String[] result = new String[3];
		for(int i=0;i<3;i++){
			result[i]= data[i][row];
		}
		return result;
	}
	public void singleArrayToData(String[] input, int row){
		for(int i=0;i<3;i++){
			data[i][row]=input[i];
		}
	}
	public void sort(){
		for(int row=0;row<4;row++){
			for(int i=row+1;i<5;i++){
				if(Integer.parseInt(data[0][row].trim())<Integer.parseInt(data[0][i].trim())){	
					String ArrayOne[] = dataToSingleArray(row);
					String ArrayTwo[] = dataToSingleArray(i);
					String temp[] = ArrayOne;
					ArrayOne = ArrayTwo;
					ArrayTwo = temp;
					singleArrayToData(ArrayOne, row);
					singleArrayToData(ArrayTwo, i);
				}
			}
		}
	}
	
}
