package myGame.doodleTetris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import myGame.doodleTetris.framework.Game;

public class ReadChromosome {
	public  InputStream is;
	public  InputStreamReader inputreader;
	public  BufferedReader buffreader;
	public  int dataRow[] = new int[8];
public Game game;
	
	public ReadChromosome (Game game) {
		this.game = game;
		
	}
	public  void accessFile(String path){
		//is = AndroidGame.getContext().getResources().openRawResource(id);
		
		try {
			is=game.getContext().getAssets().open(path);
			inputreader = new InputStreamReader(is);
			buffreader = new BufferedReader(inputreader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  void readFile() throws IOException{
		String strRow="";
		//resetMap();
		try {
			strRow = buffreader.readLine();
			//System.out.println("String row: "+strRow);
			while(strRow!=null){
				String[] row=strRow.split("|");				
				strRow = buffreader.readLine();
				for(int i = 0;i<row.length;i++){
					dataRow[i] = Integer.parseInt(row[i].trim());
				}
				//System.out.println("strRow: "+strRow);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		is.close();
	}
	public int[] getData(){
		return dataRow;
	}
	
	
	public Chromosome setChromosome(){
		Chromosome s = new Chromosome();
		for (int i=0;i<Chromosome.numOfGen;i++)
			s.gen[i] = dataRow[i];
		return s;
	}
}
