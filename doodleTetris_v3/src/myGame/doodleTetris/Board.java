package myGame.doodleTetris;

import java.util.Random;

import android.test.IsolatedContext;
import android.util.Log;
import myGame.doodleTetris.Block.BlockType;
import myGame.doodleTetris.Block.blockName;


public class Board {
	public static int BOARD_WIDTH = 13;
	public static int BOARD_HEIGHT =32;
	
	//public static float TICK_INTIAL = 0.5f; //Thá»�i gian quy Ä‘á»‹nh ban Ä‘áº§u cho phiÃªn lÃ m viá»‡c
	// test
	public static float TICK_INTIAL =0.5f; //Thá»�i gian quy Ä‘á»‹nh ban Ä‘áº§u cho phiÃªn lÃ m viá»‡c
	//public static float TICK_DECREMENT = 0.05f;//Ä�á»™ giáº£m thá»�i gian lÃ m viá»‡c
	public static float TICK_DECREMENT = 0.0f;//Ä�á»™ giáº£m thá»�i gian lÃ m viá»‡c
	public static int SCORE_LV_STEP = 999; 
	
	// diem 1 row
	public static int SCORE_INCREMENT = 99;
	
	float tickTime = 0f;//Thá»�i gian Ä‘Ã£ qua cá»§a 1 phiÃªn lÃ m viá»‡c
	float tick = TICK_INTIAL;//Thá»�i gian hiá»‡n táº¡i cá»§a 1 phiÃªn lÃ m viá»‡c
	
	public Block.BlockType statusBoard [][] = new Block.BlockType[BOARD_WIDTH][BOARD_HEIGHT];
	
	public Block currentBlock;
	public Block nextBlock;
	public Block shadowBlock; // shadow of current Block;
	public int nextBlockID;	
	public int lines=0;
	public int level = 1;	
	public int score =0;
	public boolean gameOver = false;
	
	
	int bonus;
	public static boolean nextMap = false;
	
	public Board(){
		for (int i=0;i<BOARD_WIDTH;i++){
			for (int j=0;j<BOARD_HEIGHT ;j++) 
				statusBoard[i][j]= BlockType.NULL;
			
		}

		currentBlock = Block.Next_Block();
		nextBlockID = Block.Next_Block_id();
		nextBlock = new Block (nextBlockID);
		nextBlock.setPosition(0,0);
		
	}

	void addNewLine () {
		
		for (int i=0;i<Board.BOARD_WIDTH;i++)
			for (int j=0;j<=Board.BOARD_HEIGHT-2;j++)
				statusBoard[i][j] = statusBoard[i][j+1];
	
		Random r = new Random ();
		int ra = r.nextInt(13);
		if (ra < 3 ) ra = 3;
		for (int j=0;j<Board.BOARD_WIDTH;j++)//KhÃ´ng cáº§n cÃ¡i nÃ y
			if (j%ra ==0 && j !=0)
			 statusBoard[j][BOARD_HEIGHT-1] = BlockType.NULL;
			else 
			 statusBoard[j][BOARD_HEIGHT-1] = BlockType.ORANGE;
	
		Log.d("add new","add new");
	}
	
	public void clearRow (int row) {
		
		for (int i=0;i<Board.BOARD_WIDTH;i++)
			for (int j=row;j>0;j--)
				statusBoard[i][j] = statusBoard[i][j-1];
	
		for (int j=0;j<Board.BOARD_WIDTH;j++)//KhÃ´ng cáº§n cÃ¡i nÃ y
			statusBoard[j][0] = BlockType.NULL;
	
	}
	
	public void addBlock (Block currentBlock) {
		
		for (int i=0;i<Block.BLOCK_WIDTH;i++)
		for (int j=0;j<Block.BLOCK_HEIGHT;j++)
			if(currentBlock.status[i][j]==true)
            {
				statusBoard[i+currentBlock.x][j+currentBlock.y] = currentBlock.type;
            }
		
	}
	
	public void removeBlock (Block currentBlock) {
		
		for (int i=0;i<Block.BLOCK_WIDTH;i++)
		for (int j=0;j<Block.BLOCK_HEIGHT;j++)
			if(currentBlock.status[i][j]==true)
            {
				statusBoard[i+currentBlock.x][j+currentBlock.y] = BlockType.NULL;
            }
		
	}
	
	public void update(float deltaTime) {
		tickTime+= deltaTime;
		
		while (tickTime>tick){
			tickTime-=tick;
			if (!currentBlock.goDown(this)){
			
				this.addBlock(currentBlock); // add block to board
				
				if (currentBlock.isItem()) { processItem (); Asset.sound_cleanRow.play(); }
				else {
				int row = checkFullRow();
				if (row==0) Asset.sound_endFall.play();
				lines += row;
				//(row is bonus)
				score += SCORE_INCREMENT*row *(1+(float)row/10);
				// tăng level
				if (score>(((level-1) *(SCORE_LV_STEP+1))+SCORE_LV_STEP) && tick- TICK_DECREMENT > 0){ level ++ ;tick -= TICK_DECREMENT; }
				
				// check over
				if (checkOver (row)) gameOver = true;
				}
				
				// next block
				currentBlock = new Block(nextBlockID);
				nextBlockID = Block.Next_Block_id();
				nextBlock = new Block (nextBlockID);
				nextBlock.setPosition(0, 0);
			}
		}
	}
	
	

	//Duong insert method updateForArcadeGameScreen()
	public void updateForArcadeGameScreen(float deltaTime){
		tickTime+= deltaTime;
		
		while (tickTime>tick){
			tickTime-=tick;
			if (!currentBlock.goDown(this)){
			
				this.addBlock(currentBlock);
				if (currentBlock.isItem()) { processItem (); Asset.sound_cleanRow.play(); }
				else {
				int row = checkFullRow();
				
				if (row==0) Asset.sound_endFall.play(); // khong an diem
				//(row is bonus)
				
				score += SCORE_INCREMENT*row *(1+(float)row/10);
				// kiem tra over ( - so dong an )
				if (checkOver (row)) gameOver = true;
				}
				currentBlock = new Block(nextBlockID);
				nextBlockID = Block.Next_Block_id();
				nextBlock = new Block (nextBlockID);
				nextBlock.setPosition(0, 0);
		
			}
			if (score>level *SCORE_LV_STEP && tick- TICK_DECREMENT > 0){ level ++ ;tick -= TICK_DECREMENT; }
		}
	}
	//
	public boolean checkOver (int row) {
		for (int i=0;i<Block.BLOCK_WIDTH;i++)
			for (int j=0;j<Block.BLOCK_HEIGHT;j++)
				if(currentBlock.status[i][j]==true &&  (j+currentBlock.y)<=4 - row)
	            {
	              return true;
	            }
		return false;
	}
	
	/* Item position 1-1
	 ****
	 *#**
	 ****
	 ****
	 */
	public void processItem () {
		if (currentBlock.name == blockName.Boomb)
			for (int i=0;i< Block.BLOCK_HEIGHT-1;i++)
				for (int j=0;j< Block.BLOCK_HEIGHT-1;j++)
					if (currentBlock.x+i>=0 && currentBlock.x+i<=Board.BOARD_WIDTH-1  )
						if (currentBlock.y+j>=0 && currentBlock.y+j<=Board.BOARD_HEIGHT-1  )
							statusBoard[currentBlock.x+i][currentBlock.y+j] = BlockType.NULL;
		
		if (currentBlock.name == blockName.Rocket)
			for (int i=currentBlock.y;i< Board.BOARD_HEIGHT;i++)
					statusBoard[currentBlock.x+1][i] = BlockType.NULL;
		
		if (currentBlock.name == blockName.Dynamite)
			clearRow(currentBlock.y+1);
		
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
					if(statusBoard[j][i+currentBlock.y] ==BlockType.NULL )
					{   
						f=false; break;
					}
					//Duong test BlockType.Map
					else if(statusBoard[j][i+currentBlock.y]==BlockType.MAP){
						existMap=true;
					}
					//
					}
					else 
					{f=false;  break;}
			}// for j
		} // vong if
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
	
	/*
	"1/ The Height of the TALLEST column // chieu cao cao nhat"
	"2/ Hole Count // so lo"
	"3/ Lines cleared  // so dong duoc xoa\n" +
	"4/ max different height // chenh lech cot cao nhat va thap nhat "
	"5/ Deepest hole // do sau cua lo thap nhat "
	"6/ total dept of holes // tong chieu sau cua cac lo  "
	"7/ Horizontal Roughness // do nham be mat ngang "
	"8/ sum of height // tong chieu cao "
	*/
	public int rankBoard( Block b, Chromosome currentChromosome ) {
		Block shadow = b.setShadow(this);
		int result= 0;
	
		int heightest =0;
		int shortest = 300;
		int holes=0;
		int clearLine=0;
		int maxDiffHeight=0;
		int deepestOfHole=0;
		int totalDeptOfhHole=0;
		int HorizontalRoughness =0 ; // do nham be mat
		int sumHeight =0;
		
		this.addBlock(shadow);
		int maxY = -1;
		
		int preHeight=-1;
		int curHeight=-1;
		
		for (int i=0; i< Board.BOARD_WIDTH;i++){
			maxY = -1;
			for (int j=0; j< Board.BOARD_HEIGHT;j++) {
				// tim thay phan tu cao nhat
				if (statusBoard[i][j]!= BlockType.NULL) {maxY = j; break;}
				} //for j
			
			if (maxY ==-1) curHeight = 0;
			else
			curHeight = (Board.BOARD_HEIGHT- maxY ) ;
			
			if (curHeight > heightest) heightest =curHeight; // chieu cao cao nhat
			if (curHeight < shortest) shortest =curHeight; // chieu cao thap
			
			if (preHeight!=-1){
				
			if (curHeight>preHeight)
				HorizontalRoughness = HorizontalRoughness+ (curHeight - preHeight);
			else 
				HorizontalRoughness = HorizontalRoughness+ (preHeight - curHeight);
			
			}
			
			preHeight = curHeight;
			
			// ton tai it nhat 1 block
			if (maxY!=-1){
			// tong chieu cao
			sumHeight+= (Board.BOARD_HEIGHT- maxY ) ;
			
			// ket hop tim holes
			for (int k=maxY; k< Board.BOARD_HEIGHT ;k++) {
					if (statusBoard[i][k]== BlockType.NULL){
					if ( (k- maxY ) > deepestOfHole) deepestOfHole = (k- maxY ); // lo sau nhat
					totalDeptOfhHole += (k- maxY ); // tong chieu sau
					holes ++;
				}
				} // for maxY to BoardHeught
			
			}	// if (maxY!=-1)
		}//for i
		
		
		boolean f = true;
		// tim clear row
		
		// check clearow
		for (int i=0; i< Board.BOARD_HEIGHT;i++) {
				f = true;
			for (int j=0; j< Board.BOARD_WIDTH;j++) {
				if (statusBoard[j][i]== BlockType.NULL)
					{f = false; break;}
			}
			if (f) clearLine++;
		}
		
		
		maxDiffHeight = heightest- shortest;
		
		// log stat
		Log.d ("current stat", "heightest:"+heightest+"-holes:"+holes+"-clearLine:"+clearLine+"-maxDiffHeight:"
					+maxDiffHeight+"-deepestOfHole:"+deepestOfHole+"-totalDeptOfhHole:"
					+totalDeptOfhHole+"-HorizontalRoughness:"+HorizontalRoughness+"-sumHeight:"
					+sumHeight);
		
		int i =0;
		result = 
		 heightest *currentChromosome.gen[i++]+
		 holes*currentChromosome.gen[i++]+
		 clearLine*currentChromosome.gen[i++]+
		 maxDiffHeight*currentChromosome.gen[i++]+
		 deepestOfHole*currentChromosome.gen[i++]+
		 totalDeptOfhHole*currentChromosome.gen[i++]+
		 HorizontalRoughness*currentChromosome.gen[i++]+
		 (sumHeight -clearLine*Board.BOARD_WIDTH ) *currentChromosome.gen[i++];
		
		
		removeBlock(shadow);
		return result;
		
	}
}