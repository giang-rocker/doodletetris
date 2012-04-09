package myGame.doodleTetris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import myGame.doodleTetris.framework.Game;

public class LoadMap {
	
	public static InputStream is;
	public static InputStreamReader inputreader;
	public static BufferedReader buffreader;
	public static int rowNum=1;
	public static int colNum=1;
	public static boolean map[][] = new boolean[colNum][rowNum];
	public Game game;
	
	public LoadMap (Game game) {
		this.game = game;
		
	}
	public  void resetMap(){
		for(int col=0;col<colNum;col++)
			for(int row=0;row<rowNum;row++)
				map[col][row]=false;
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
	
	public void getRow(){
		
	}
	
	public  void readFile() throws IOException{
		String strRow="";
		//resetMap();
		int indexRow =0;
		colNum =Integer.parseInt(buffreader.readLine().trim())+1;
		rowNum = Integer.parseInt(buffreader.readLine().trim());
		map = new boolean[colNum][rowNum];
		resetMap();
		try {
			strRow = buffreader.readLine();
			//System.out.println("String row: "+strRow);
			while(strRow!=null){
				String[] arrRow=strRow.split("");
				for(int col=0;col<arrRow.length;col++){
					//System.out.println("col="+col+" indexRow="+indexRow+" arrRow.lenght="+arrRow.length+" arrRow[col]="+arrRow[col]);
					if(arrRow[col].equals("1"))
						map[col][indexRow]=true;
					else
						map[col][indexRow]=false;
				}
				
				indexRow++;
				strRow = buffreader.readLine();
				//System.out.println("strRow: "+strRow);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		is.close();
		
	}
	
	public  void addMapToBoard(Board board){
		for(int col=0;col<colNum;col++){
			for(int row=0;row<rowNum;row++){
				//System.out.println("col="+col+" row="+row);
				if(map[col][row])
				board.map[(Board.BOARD_WIDTH-colNum)/2+col][Board.BOARD_HEIGHT-rowNum+row]=Block.BlockType.MAP;
			}
		}
	}
	
	
	
}
