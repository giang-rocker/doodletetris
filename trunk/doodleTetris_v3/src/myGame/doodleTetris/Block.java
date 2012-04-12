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
		ROCKET,
		DYNAMITE,
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
			Boomb,
			Rocket,
			Dynamite
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
	
	public boolean isItem () {
		if (name == blockName.Boomb || name == blockName.Dynamite ||name == blockName.Rocket)
		return true;
		return false;
	}
	
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
			// vi tri
			status[1][1] = true;
			break;
		}
		case 9:{
			type = BlockType.ROCKET;
			name = blockName.Rocket;
			// vi tri
			status[1][1] = true;
			break;
		}
		case 8:{
			type = BlockType.DYNAMITE;
			name = blockName.Dynamite;
			// vi tri
			status[1][1] = true;
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
	
	public void setPosition (int x, int y) {
		this.x = x;
		this.y = y;
		
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
				if ((x+i) == Board.BOARD_WIDTH-1 || currentBoardStatus.statusBoard[x+i+1][y+j]!=BlockType.NULL  )
					return false;
				
				
			}// for j
		}//for i
		
		x++; return true;
		
	}
	public  boolean goLeft(Board currentBoardStatus){
		for (int i=0;i<BLOCK_WIDTH;i++){
			for (int j=0;j<BLOCK_HEIGHT;j++) {
				if ( status[i][j]==true  )
				if ( (x+i) == 0 || currentBoardStatus.statusBoard[x+i-1][y+j]!=BlockType.NULL )
				return false;
				
			}// for j
		}//for i
		
		x--; return true;
	}
	public  boolean goDown(Board currentBoardStatus){
		
		for (int i=0;i<BLOCK_WIDTH;i++){
			for (int j=0;j<BLOCK_HEIGHT;j++) {
				if (status[i][j]==true )
				if ( (y+j) == Board.BOARD_HEIGHT-1 || currentBoardStatus.statusBoard[x+i][y+1+j]!=BlockType.NULL)
				return false;
				
			}// for j
		}//for i
		
		y++; return true;
	}
	public  boolean rotation(Board currentBoardStatus){
		int delta =0;
		boolean f = true;
		
		// square,
			if (name == blockName.Sq || isItem() ) {
				return f;
			}
		// rule
		if (direction == blockDirection.LEFT ||  direction == blockDirection.RIGHT)
		if (name == blockName.S || name == blockName.Z || name == blockName.I ) delta = 1;
		
		// copy
		boolean temp_rotation[][] =  new boolean [BLOCK_WIDTH][BLOCK_WIDTH];
		boolean temp[][] =  new boolean [BLOCK_WIDTH][BLOCK_WIDTH];
		
		for (int i=0;i<4;i++)
			for (int j =0;j<4;j++) {
				temp[i][j]=false;
				temp_rotation[i][j]=false;
				temp[i][j] = status[i][j];
				
			}
	
		// L
		if (name == blockName.I ) {
			for (int i=0;i<4;i++)
				for (int j =0;j<4;j++)
					if (i-delta>=0)
						temp_rotation[i-delta][j] = temp[j][3-i];
			
			
		}
		
		// orther case
		for (int i=0;i<3;i++)
			for (int j =0;j<3;j++)
				temp_rotation[i][j+delta] = temp[j][2-i];
	
		
		// kiem tra
					for (int i=0;i<4;i++)
						for (int j =0;j<4;j++)
							if (temp_rotation[i][j] == true)
								if (x + i < 0 || y + j < 0 || x+i >=Board.BOARD_WIDTH || y+j >= Board.BOARD_HEIGHT)
								f =false;
								else if ( currentBoardStatus.statusBoard[x+i][y+j] != BlockType.NULL)
								f =false;
		
			if (f) 
			{
				for (int i=0;i<4;i++)
					for (int j =0;j<4;j++)
						status[i][j] = temp_rotation[i][j];
			
				// doi huong
				direction = nextDirection();
			}
	return f;
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
		int ra = r.nextInt(20);
		if (item!=-1) ra  = item;
		item=-1;
		if (ra<17) ra = ra%7;
		else ra = ra %10;
		Block nextBlock = new Block(ra);
		return nextBlock;
		 
		 
	 }
	 
	 public static int Next_Block_id() {
		Random r = new Random();
		int ra = r.nextInt(20);
		if (item!=-1) ra  = item;
		item=-1;
		Log.d ("Block id",Integer.toString(ra));
		
		if (ra<17) ra = ra%7;
		else ra = ra %10;
		return ra;
	 }
	 
}
