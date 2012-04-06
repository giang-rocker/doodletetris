package myGame.doodleTetris;

import java.util.Random;

import android.util.Log;

public class Block {
	public static int BLOCK_WIDTH = 4;
	public static int BLOCK_HEIGHT = 4;
	public static int UNIT_CELL = 24;
	
	 static enum BlockType {
		RED,
		BLUE,
		GREEN,
		WHITE,
		ORANGE,
		YELLOW,
		PURPLE,
		NULL,
		// items
		BOOMB,
		MAP
		
	} 
	 
	 static enum blockName {
			Ll,
			Lr,
			S,
			Z,
			I,
			T,
			Sq,
			Boomb
		} 
	 
	 static enum blockDirection {
			UP,DOWN,RIGHT,LEFT
		} 
	int x,y;//Lưu top và left của mảng
	BlockType type;
	blockName name;
	blockDirection direction;
	boolean status[][] = new boolean [BLOCK_WIDTH][BLOCK_WIDTH];
	
	public Block(  ){
		for (int i=0;i<BLOCK_WIDTH;i++) status[i][i] =false;
		x= (Board.BOARD_WIDTH /2); 
		y = 0;
	}
	int blockStyle1[] = {15,15,30,60,51,57,58};
	int blockStyle[] = {15,15,184,408,240,312,120};
	
	public Block(int id  ){
		
		for (int i=0;i<BLOCK_WIDTH;i++)for (int j=0;j<BLOCK_HEIGHT;j++) status[i][j] =false;
		
		x= ((Board.BOARD_WIDTH)/2);
		y =0;
		direction = blockDirection.DOWN;
		switch (id){
		case 0:{
			type = BlockType.ORANGE;
			name = blockName.I;
			direction = blockDirection.LEFT;
			for (int i=1;i<2;i++)
				for (int j=0;j<BLOCK_HEIGHT;j++)
				{
					status[j][i] = true;
					
				}
			break;
		}
		case 1:{
			type = BlockType.YELLOW;
			name = blockName.Sq;
			for (int i=1;i<3;i++)
				for (int j=1;j<3;j++)
				{
					status[i][j] = true;
					
				}
			break;
		
		}
		// boomb
		case 7:{
			type = BlockType.BOOMB;
			name = blockName.Boomb;
			direction = blockDirection.LEFT;
			for (int i=1;i<2;i++)
				for (int j=0;j<2;j++)
				{
					status[i][j] = true;
					
				}
			break;
		}
		case 8:{
			type = BlockType.BOOMB;
			name = blockName.Boomb;
			direction = blockDirection.LEFT;
			for (int i=1;i<2;i++)
				for (int j=0;j<3;j++)
				{
					status[i][j] = true;
					
				}
			break;
		}
		case 9:{
			type = BlockType.BOOMB;
			name = blockName.Boomb;
			direction = blockDirection.LEFT;
			for (int i=1;i<2;i++)
				for (int j=0;j<4;j++)
				{
					status[i][j] = true;
					
				}
			break;
		}
		default:{
			int len=0;
			
			switch (id) {
			case 2:
				type = BlockType.GREEN;
				name = blockName.T;
				break;
			case 3:
				type = BlockType.WHITE;
				name = blockName.Z;
				break;
			case 4:
				type = BlockType.BLUE;
				name = blockName.S;
				break;
			case 5:
				type = BlockType.RED;
				name = blockName.Ll;
				break;
			case 6:
				type = BlockType.YELLOW;
				name = blockName.Lr;
				break;
			
			}
			
			boolean r[] = new boolean[6];
			r = intToBinary(id);
			for (int i=0;i<3;i++)
				for (int j=0;j<3;j++)
				{
					status[j][i] = r[len++];
				
				}
			break;
		}
	}// switch case
	}
	
	public boolean[] intToBinary (int id) {
		
		boolean result [] = new boolean [9];
		for (int i=0;i<9;i++) result[i]= false;
		int num = blockStyle[id];
		int len = 0;
		while (num!=0) {
			if (num%2==1) result[len] = true ;
			num=num/2;
			len++;
		}
		
		return result;
		
	}
	
	public  boolean goRight(Board currentBoardStatus) {
		for (int i=0;i<BLOCK_WIDTH;i++){
			for (int j=0;j<BLOCK_HEIGHT;j++) {
				if ( status[i][j]==true )
				if ((x+i) == Board.BOARD_WIDTH-1 || currentBoardStatus.map[x+i+1][y+j]!=BlockType.NULL  )
					return false;
				
				
			}// for j
		}//for i
		
		x++; return true;
		
	}
	public  boolean goLeft(Board currentBoardStatus){
		for (int i=0;i<BLOCK_WIDTH;i++){
			for (int j=0;j<BLOCK_HEIGHT;j++) {
				if ( status[i][j]==true  )
				if ( (x+i) == 0 || currentBoardStatus.map[x+i-1][y+j]!=BlockType.NULL )
				return false;
				
			}// for j
		}//for i
		
		x--; return true;
	}
	public  boolean goDown(Board currentBoardStatus){
		
		for (int i=0;i<BLOCK_WIDTH;i++){
			for (int j=0;j<BLOCK_HEIGHT;j++) {
				if (status[i][j]==true )
				if ( (y+j) == Board.BOARD_HEIGHT-1 || currentBoardStatus.map[x+i][y+1+j]!=BlockType.NULL)
				return false;
				
			}// for j
		}//for i
		
		y++; return true;
	}
	public  boolean rotation(Board currentBoardStatus){
		int delta =0;
		
		// square,
		if (name == blockName.Sq || name == blockName.Boomb) {
			return true;
		}
		// rule
		if (direction == blockDirection.LEFT ||  direction == blockDirection.RIGHT)
		if (name == blockName.S || name == blockName.Z || name == blockName.I ) delta = 1;
		
		// doi huong
		direction = nextDirection();
		// copy
		boolean temp[][] =  new boolean [BLOCK_WIDTH][BLOCK_WIDTH];
		for (int i=0;i<4;i++)
			for (int j =0;j<4;j++){
				temp[i][j] = status[i][j];
				 status[i][j]=false;
			}
	
		// L
		if (name == blockName.I ) {
			for (int i=0;i<4;i++)
				for (int j =0;j<4;j++)
					if (i-delta>=0)
					status[i-delta][j] = temp[j][3-i];
			
			return true;
		}
		
		// orther case
		for (int i=0;i<3;i++)
			for (int j =0;j<3;j++)
				status[i][j+delta] = temp[j][2-i];
	
		return true;
	}
	
	//
	
	
	blockDirection nextDirection  () {
		switch (direction) {
		case DOWN :
			return blockDirection.LEFT;
		case LEFT:
			return blockDirection.UP;
		case UP:
			return blockDirection.RIGHT;
		case RIGHT:
			return blockDirection.DOWN;
		default :
		return blockDirection.DOWN;
		}
	}
	
	
	public static int item = -1;
	 public static Block Next_Block() {
		Random r = new Random();
		int ra = r.nextInt(7);
		if (item!=-1) ra  = item; item=-1;
		Block nextBlock = new Block(ra);
		 StringBuilder b = new StringBuilder();
		 b.append(ra); String text = b.toString();
		 Log.d("type", text);
		return nextBlock;
		 
		 
	 }
}
