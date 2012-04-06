package myGame.doodleTetris;

import myGame.doodleTetris.Block.BlockType;


public class Board {
	public static int BOARD_WIDTH = 13;
	public static int BOARD_HEIGHT =32;
	
	public static float TICK_INTIAL = 0.5f; //Thời gian quy định ban đầu cho phiên làm việc
	public static float TICK_DECREMENT = 0.05f;//Độ giảm thời gian làm việc
	public static int SCORE_LV_STEP = 1000; 
	
	public static int SCORE_INCREMENT = 100;
	
	float tickTime = 0f;//Thời gian đã qua của 1 phiên làm việc
	float tick = TICK_INTIAL;//Thời gian hiện tại của 1 phiên làm việc
	
	public Block.BlockType map [][] = new Block.BlockType[BOARD_WIDTH][BOARD_HEIGHT];
	public Block currentBlock;
	
	public int level = 1;
	public int score =0;
	public boolean gameOver = false;
	
	
	int bonus;
	public static boolean nextMap = false;
	
	public Board(){
		for (int i=0;i<BOARD_WIDTH;i++){
			for (int j=0;j<BOARD_HEIGHT ;j++) 
				map[i][j]= BlockType.NULL;
			
		}
		currentBlock = Block.Next_Block();
	}

	public void clearRow (int row) {
		for (int i=0;i<Board.BOARD_WIDTH;i++)
			for (int j=row;j>0;j--)
				   map[i][j] = map[i][j-1];
	
		for (int j=0;j<Board.BOARD_WIDTH;j++)//Không cần cái này
			map[j][0] = BlockType.NULL;
	
	}
	
	public void addBlock (Block currentBlock) {
		
		for (int i=0;i<Block.BLOCK_WIDTH;i++)
		for (int j=0;j<Block.BLOCK_HEIGHT;j++)
			if(currentBlock.status[i][j]==true)
            {
               map[i+currentBlock.x][j+currentBlock.y] = currentBlock.type;
            }
		
	}
	
	
	public void update(float deltaTime) {
		
		tickTime+= deltaTime;
		
		while (tickTime>tick){
			tickTime-=tick;
			if (!currentBlock.goDown(this)){
				this.addBlock(currentBlock);
				int row = checkFullRow();
				//(row is bonus)
				
				//Duong update sound
				try {
					Sound.startSound();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				//
				score += SCORE_INCREMENT*row *(1+(float)row/10);
				if (checkOver ()) gameOver = true;
				currentBlock = Block.Next_Block();
				
			}
			if (score>level *SCORE_LV_STEP && tick- TICK_DECREMENT > 0){ level ++ ;tick -= TICK_DECREMENT; }
		}
	}
	
	//Duong insert method updateForArcadeGameScreen()
	public void updateForArcadeGameScreen(float deltaTime){
		tickTime+= deltaTime;
		
		while (tickTime>tick){
			tickTime-=tick;
			if (!currentBlock.goDown(this)){
				this.addBlock(currentBlock);
				int row = checkFullRow();
				//(row is bonus)
				
				//Duong update sound
				try {
					Sound.startSound();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				//
				score += SCORE_INCREMENT*row *(1+(float)row/10);
				if (checkOver ()) gameOver = true;
				currentBlock = Block.Next_Block();
				//Duong check finish map
				if(LoadMap.rowNum==0){
					nextMap = true;	
				}
				//
				
			}
			if (score>level *SCORE_LV_STEP && tick- TICK_DECREMENT > 0){ level ++ ;tick -= TICK_DECREMENT; }
		}
	}
	//
	public boolean checkOver () {
		for (int i=0;i<Block.BLOCK_WIDTH;i++)
			for (int j=0;j<Block.BLOCK_HEIGHT;j++)
				if(currentBlock.status[i][j]==true &&  (j+currentBlock.y)<4)
	            {
	              return true;
	            }
		return false;
	}
	
	public int checkFullRow () {
		int i,j;
		int n =0;
		
		for (i=0 ;i<Block.BLOCK_HEIGHT;i++){
			boolean f=true;
			boolean existMap=false;
			if ( currentBlock.type !=  BlockType.BOOMB || currentBlock.status[1][i] !=true) {
			for (j=0;j<Board.BOARD_WIDTH ;j++){
				if (i+currentBlock.y<BOARD_HEIGHT){
					if(map[j][i+currentBlock.y] ==BlockType.NULL )
					{   
						f=false; break;
					}
					//Duong test BlockType.Map
					else if(map[j][i+currentBlock.y]==BlockType.MAP){
						existMap=true;
					}
					//
				}
				else 
				{f=false;  break;}
			}// for j
		}
		if (f && (i+currentBlock.y<BOARD_HEIGHT))	{
			n++; clearRow (i+currentBlock.y); 
			//Duong test exist block of map
			if(existMap){
				LoadMap.rowNum--;
				System.out.println("rowNum: "+LoadMap.rowNum);
			}
			//
			}
		}// for o
		return n;
	}
	public int getBonus () {
		if (bonus>=3) return bonus;
		else return -1;
		
	}
}
