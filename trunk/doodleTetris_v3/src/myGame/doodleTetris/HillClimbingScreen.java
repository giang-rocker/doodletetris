package myGame.doodleTetris;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import myGame.doodleTetris.Block.BlockType;
import myGame.doodleTetris.Block.blockDirection;
import myGame.doodleTetris.HillClimbingScreen1.GameState;
import myGame.doodleTetris.framework.AndroidGraphics;
import myGame.doodleTetris.framework.Game;
import myGame.doodleTetris.framework.Music;
import myGame.doodleTetris.framework.Screen;
import myGame.doodleTetris.framework.SingleTouch;
import myGame.doodleTetris.framework.Sound;
import android.os.Environment;
import android.util.Log;

public class HillClimbingScreen extends Screen {

	boolean isMusic = true;
	boolean isSound = true;
	String nameMusic[]={"bg_track_midi","bg_track_midi1","bg_track_midi2","bg_track_midi3"};
	Music music;
	int countChromosome;
	boolean isStop ;
	int limitChromosome = 20;
	int countlimit = 0;
	boolean isAlive ;
	Chromosome currentChromosome;
	// truyen giong
	
	
		public void setup () {
		// bg
		Asset.bg_scoreBoard.setPosition(14*24+3, 9*24);
		Asset.bg_nextBlock.setPosition(15*24, 5*24);
		
		// set game button arrow
		Asset.btn_right.setPosition(216, 696);
		Asset.btn_left.setPosition(48, 696);
		Asset.btn_rotate.setPosition(360, 696);
		Asset.btn_down.setPosition(360, 600);
		Asset.btn_pause.setPosition(384, 60);
		
		// set menu button
		Asset.btn_mainMenu.setPosition(48, 312);
		Asset.btn_playAgain.setPosition(48, 432);
		
		// set icon
		Asset.icon_music.setPosition(360,360);
		Asset.icon_sound.setPosition(360,360+48);
		Asset.icon_star.setPosition(360,360+48+48);
		
		Asset.icon_music_dis.setPosition(360 + 48 ,360);
		Asset.icon_sound_dis.setPosition(360 + 48 ,360+48);
		Asset.icon_star_dis.setPosition(360 + 48 ,360+48+48);
		
		// random bg
		Random r = new Random ();
		Asset.bg_gameScreen = Asset.list_bg[r.nextInt(Asset.list_bg.length)];
		currentChromosome =Chromosome.generateChromosome();
		countChromosome =0;
		Board.TICK_DECREMENT=0.0f;
		Board.BOARD_HEIGHT = 24;
		Board.BOARD_WIDTH=10;
		isAlive=false;
		//currentChromosome.gen = new int[] {141 , -455  , -154 , -379 ,246 ,-406 , -273 , -481 ,-65};

		}
	
	enum GameState {
		Ready,
		Running,
		Paused,
		StageClean,
		GameOver,
		Exit,
	}
	
	GameState gameState = GameState.Running;
	Board board;
	int currentScore;
	int tempScore=0;
	String strScore="0";
	int level=1;
	int time=0;
	String minus,second;
	
	public HillClimbingScreen (Game game) {
		super(game);
		board = new Board();
		setup ();
		// TODO Auto-generated constructor stub
	//	play track
//		Asset.bg_track.play ();
		//Duong update music 12/4/2012
		Random r = new Random();
		int resid = game.getContext().getResources().getIdentifier(nameMusic[r.nextInt(nameMusic.length)], "raw", game.getContext().getPackageName());
		music = new Music(game.getContext(), resid);
		//music.play();
		Sound.appVolume = 0f;
		Block.generateBlock ();
		Block.isLearn = true;
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		if (gameState == GameState.Running)
			updateRunning(deltaTime);
		if (gameState == GameState.Paused)
			updatePause();
		if (gameState == GameState.GameOver)
			updateGameOver();
		if (gameState == GameState.StageClean)
			updateStageClean();
		if (gameState == GameState.Exit)
			updateExit();
	}
	
	// int bonus image duration present
	int bonusDuration = 250000000;
	int startDrawBonus =-1;
	@Override
	public void present(float deltaTime) {
		// TODO Auto-generated method stub
	AndroidGraphics g = game.getGraphics();
	g.drawImage(Asset.bg_gameScreen);
	g.drawImage(Asset.bg_board.bitmap, (int )Asset.bg_board.x,(int ) Asset.bg_board.y);
//	g.drawImage(Asset.btn_left);
//	g.drawImage(Asset.btn_right);
//	g.drawImage(Asset.btn_rotate);
//	g.drawImage(Asset.btn_down);
	g.drawImage(Asset.btn_pause);
	//draw Level
	drawLevel( level);

	// icon
	
	// draw boar statistic
	g.drawImage(Asset.bg_scoreBoard.bitmap,240+4*24,552-48);
 	drawBoard(board);
	
	
	// draw GEn
	for (int i=0;i<Chromosome.numOfGen;i++){
		drawStringNumber(currentChromosome.gen[i], 10*24+3,i*48);
		
	if (i==currentChromosome.genIndex){
		drawStringNumber(i, 60,525+48+24);
		drawStringNumber(currentValueofCurrentGen, 120,525+48+24);
	}
	}
	
	drawStringNumber(currentChromosome.fitnessValue,120,552);
	
	// drawLines
	drawStringNumber(board.lines,360,525+5);
	// draw Time
	drawStringNumber(countChromosome,360,585);
	// draw Limit chromosome nhay vot
	drawStringNumber(countlimit,360,585+24+24);
	//dongbang
	drawStringNumber(countDongBang,360,585+24+24+48);
	

	//drawBlock (board.shadowBlock, 24, -4*24);
	
	if (gameState == GameState.Paused )
		{drawPauseUI(); }
	
	if (gameState == GameState.GameOver )
	{drawGameOverUI();}
	if (gameState == GameState.StageClean )
	{drawStageCleanUI();}
	
	if (gameState == GameState.Exit )
		{drawExitUI();}
	
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		if (gameState == GameState.Running)
			gameState = GameState.Exit;
		    music.stop();
	}

	@Override
	public void resume(){
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	void drawBoard (Board board) {
		int unit_cell = Block.UNIT_CELL;
		
		// ve board
		for (int i=0;i<Board.BOARD_WIDTH;i++){
			for (int j=4;j<Board.BOARD_HEIGHT;j++) {
				drawCell(board.statusBoard[i][j],(i+1)*unit_cell,(j-4)*unit_cell);
				}// for j
			}//for i
		
		
	}
	
	// ve Block ()
	void drawBlock (Block block, int startX, int startY) {
		int unit_cell = Block.UNIT_CELL;
		for (int i=0;i<Block.BLOCK_HEIGHT;i++){
			for (int j=0;j<Block.BLOCK_HEIGHT;j++) {
				if (block.status[i][j])
				drawCell(block.type,(block.x+i)*unit_cell +startX ,(block.y+j)*unit_cell +startY);
				}// for j
			}//for i
	}
	
	// ve cell
	public void drawCell(BlockType blocktype, int x, int y){
		AndroidGraphics g = game.getGraphics();
			
		switch (blocktype){
		case RED:
			g.drawImage( Asset.block_red.bitmap,x,y);
			break;
		case BLUE:
			g.drawImage(Asset.block_blue.bitmap,x,y);
			break;
		case GREEN:
			g.drawImage(Asset.block_green.bitmap,x,y);
			break;
		case WHITE:
			g.drawImage(Asset.block_white.bitmap,x,y);
			break;
		case YELLOW:
			g.drawImage(Asset.block_yellow.bitmap,x,y);
			break;
		case ORANGE:
			g.drawImage(Asset.block_orange.bitmap,x,y);
			break;
		case PURPLE:
			g.drawImage(Asset.block_purple.bitmap,x,y);
			break;
		case NULL:
			g.drawImage(Asset.block_null.bitmap,x,y);
			break;
			//Duong insert case MAP
		case MAP:
			g.drawImage(Asset.block_map.bitmap,x,y);
			break;
		// item
		case BOOMB:
			g.drawImage(Asset.item_boomb.bitmap,x,y);
			break;
		case ROCKET:
			g.drawImage(Asset.item_rocket.bitmap,x,y);
			break;
		case DYNAMITE:
			g.drawImage(Asset.item_dynamite.bitmap,x,y);
			break;
			}// switch case
	}
		
	//ve level
	public void drawLevel(int level){
		String lv = ""+level;
		AndroidGraphics g = game.getGraphics();
		int startX= 24; int startY = 168;
		int len  = lv.length();
		int UNIT_CHAR = 187;
		for (int i=0;i<len;i++){
			g.drawImage(Asset.numberLevel[(int)lv.charAt(i)-48].bitmap,startX+UNIT_CHAR*i,startY);
		}	
		}
	
	// ve so
	public void drawNumber(Character c, int x, int y){
		
		AndroidGraphics g = game.getGraphics();
			g.drawImage( Asset.number[(int)c-48].bitmap,x,y);
		}
	
	// ve bonus
	public void drawBonus(int bonus){
		AndroidGraphics g = game.getGraphics();
		if (bonus == -1) return;
		g.drawImage(Asset.bonusImg[bonus].bitmap,72,312);
	}
	
	int start = (int) System.nanoTime();
	int numAlive = 0;
	int currentValueofCurrentGen=0;
	boolean isDongBang=true ;
	int countDongBang = 0 ;
	int bestFitnessValue = 0;
	boolean isClimb= false;
	void updateRunning (float deltaTime){

		if (board.gameOver  ) 
			{
			
			if (board.lines>=10) isAlive = true;
			
				if (isAlive){
					if (board.lines<=100000 ){
						
						
						//  co vi tri tot hon // neu gia tri tot hon thi tiep tuc voi gen tiep theo
						if (board.lines> currentChromosome.fitnessValue) {
							countlimit =0;
							currentChromosome.fitnessValue = board.lines;
							currentChromosome.genIndex++;
							currentChromosome.genIndex =currentChromosome.genIndex  %Chromosome.numOfGen;
							// luu gia tri gen hien tai
							currentValueofCurrentGen = currentChromosome.gen[currentChromosome.genIndex];
					//			logPopulation (currentChromosome);
							isClimb= true;
						}
						
						else{
						countlimit++;
						isClimb = false;
						}
						// leo doi voi gen hien tai
						hillClimbing (currentChromosome);
						
						if (countlimit == limitChromosome)  {
							// tra ve gia tri gen cu
							currentChromosome.gen[currentChromosome.genIndex] = currentValueofCurrentGen ;
							// luu gia tri ban dau cua gen tiep theo
							currentChromosome.genIndex++;
							currentChromosome.genIndex =currentChromosome.genIndex  %Chromosome.numOfGen;
							currentValueofCurrentGen = currentChromosome.gen[currentChromosome.genIndex];
							countlimit=0;
						}
					
					logPopulation (currentChromosome,isClimb);
					
					//dem dong bang
					if (board.lines != currentChromosome.fitnessValue)
					{
						countDongBang =0;
					}
						else {
						countDongBang++;
					}
					
					
					if (countDongBang== limitChromosome*(Chromosome.numOfGen+1))
					{
						currentChromosome = Chromosome.generateChromosome();
						isAlive = false;
					}
					
					}
					else {
					currentChromosome.fitnessValue = board.lines;
					
					logPopulation (currentChromosome,isClimb);
					
					gameState = GameState.GameOver; return;	
					}
				} // if is alive
			// neu chua ton tai thi tao moi
				else
					currentChromosome = Chromosome.generateChromosome();
				
				countChromosome++;
				Block.next =0;
				board = new Board();
			}

		
		// tinh toan bestMove
		if (board.currentBlock.isBestPosition == false){
			bestMove();
	//	int tempRank = board.rankBoard(board.currentBlock, population.chromosomes[currentChromosome]) ;
			
			//Log.d ("CURRENT ", "Score : " + Float.toString(tempRank) +" = current X : "+  Integer.toString( board.currentBlock.x) + " CURRENT DIrection :  " + getDirection( board.currentBlock.direction)) ;
	
			// auto di chuyen
		while (board.currentBlock.direction != bestDirection)
		board.currentBlock.rotation(board);
			
		
		
		while (bestX > board.currentBlock.x) { board.currentBlock.goRight(board); }
		while (bestX < board.currentBlock.x) { board.currentBlock.goLeft(board); }
		
		while (board.currentBlock.goDown(board)) ;
		}
			
		
		SingleTouch TouchEvent = game.getTouchEvent();
		
		
	
		
		board.shadowBlock = board.currentBlock.setShadow(board);	
	
		board.update(Board.TICK_INTIAL );
		
		if (Asset.btn_pause.isTouch(TouchEvent)) {gameState = GameState.Paused; return;}
		
	
		
		if (currentScore != board.score){
			// kiem tra level
			if (level != board.level ) { Asset.sound_nextLevel.play();  level = board.level;}
			else Asset.sound_cleanRow.play();
			currentScore = board.score;
		}
		
		if (tempScore<currentScore-5) { tempScore+=5; strScore = "" + tempScore;}
		else { tempScore=currentScore; strScore = "" + tempScore;}
		
		
		
		// finis all
		time++;
	}
	
	// kiem tra trang thai on pause
	void updatePause (){
		SingleTouch TouchEvent = game.getTouchEvent();
		if ( Asset.UI_Pause.isTouch(TouchEvent)   ){
			gameState = GameState.Running;
	//			Music.startMusic();
		}
		// kiem tra xac nhan bam
		if ( Asset.btn_mainMenu.isTouch(TouchEvent)   )
			game.setScreen(new MainMenu(game));
		if ( Asset.btn_playAgain.isTouch(TouchEvent)   )
			{ board = new Board(); gameState = GameState.Running; }
	}

	// kiem tra trang thai on game over
	void updateGameOver (){
		
		SingleTouch TouchEvent = game.getTouchEvent();
		
		if ( Asset.UI_GameOver.isTouch(TouchEvent)   )
			game.setScreen(new MainMenu(game));
		if ( Asset.btn_mainMenu.isTouch(TouchEvent)   )
			game.setScreen(new MainMenu(game));
		if ( Asset.btn_playAgain.isTouchDown(TouchEvent)   )
		{ board = new Board(); gameState = GameState.Running; }
		}
	
	// stage Clean mode
	public void updateStageClean () {
	}
	
	public void updateExit () {
		gameState  = GameState.Exit;
		SingleTouch TouchEvent = game.getTouchEvent();
		{
		if (Asset.btn_yes.isTouch(TouchEvent)){
			
			System.gc();
			// try to kill this process
			game.setScreen(new MainMenu(game));
			}
			else if (Asset.btn_no.isTouch(TouchEvent))  {
				gameState  = GameState.Paused;
			}
		}
	}
	
	// ve pause
	void drawPauseUI (){
		AndroidGraphics g = game.getGraphics();
		g.drawImage(Asset.UI_Pause);
		g.drawImage(Asset.btn_mainMenu);
		g.drawImage(Asset.btn_playAgain);
		
	}
	
	// ve game ogber
	void drawGameOverUI (){
	
		AndroidGraphics g = game.getGraphics();
		g.drawImage(Asset.UI_GameOver);
		g.drawImage(Asset.btn_mainMenu);
		g.drawImage(Asset.btn_playAgain);
		drawStringNumber (board.lines, 84,192);
		drawTime (time, 192, 192);
	
		
	}
	//StaeClean mode;
	
	void drawStageCleanUI (){
	}
	void drawExitUI (){
		Asset.btn_yes.setPosition(144, 288);
		Asset.btn_no.setPosition(240, 288);
		AndroidGraphics g = game.getGraphics();
		g.drawImage(Asset.UI_Exit);
		g.drawImage(Asset.btn_yes);
		g.drawImage(Asset.btn_no);
	}
	// ve chuoi so s tai vi tri bat dau X, Y
	void drawStringNumber (int s, int startX, int startY) {
		if (s<0)
		{
			game.getGraphics().drawImage(Asset.minues.bitmap, startX, startY);
			
			s=s*-1;
		}
		
		String str = ""+s;
		startX+=24;
		int len  = str.length();
		int UNIT_CHAR = 24;
		for (int i=0;i<len;i++){
			drawNumber(str.charAt(i),startX+UNIT_CHAR*i,startY);
		}
	}

	void drawString (String s, int startX, int startY) {

		int len  = s.length();
		int UNIT_CHAR = 24;
		for (int i=0;i<len;i++){
			drawNumber(s.charAt(i),startX+UNIT_CHAR*i,startY);
		}
	}

	
	// ve time
	void drawTime (int time, int StartX, int StartY) {
		int realTime = time/40;
		String o = "0";
		
		if (realTime/60>9) o = ""; else o = "0";
		minus  = o +  realTime/60;
		
		if (realTime%60>9) o = ""; else o = "0";
		second = o + realTime%60;
		
		drawString (minus,StartX,StartY);
		drawString (":",StartX+48,StartY);
		drawString (second,StartX+44+12,StartY);
	}
	// ve ngoi sao
	void drawStar (int rate, int startX, int startY) {
		AndroidGraphics g = game.getGraphics();
		int len  = 3;
		int UNIT_CHAR = 36;
		for (int i=0;i<len;i++){
			if (i<rate)
			g.drawImage(Asset.icon_star.bitmap,startX+UNIT_CHAR*i,startY);
			else 
				g.drawImage(Asset.icon_star_dis.bitmap,startX+UNIT_CHAR*i,startY);
		}
	}
	// ve diem tai vi tri X,Y;
	public void drawScore(String score, int startX, int startY){
		String o ="0000";
		int len  = score.length();
		score = o.substring(len) + score;
		
		int UNIT_CHAR = 24;
		
		for (int i=0;i<4;i++){
			drawNumber(score.charAt(i),startX+i*UNIT_CHAR,startY);
		
		}
	}
	// kiem tra ki han
	public boolean timeExpired (int startTime, int duration)
	{
		if ((int)System.nanoTime() - startTime >= duration) 
		{  return true;}
		return false;
	}

	
	int bestX = -3;
	Block.blockDirection bestDirection =null;
	long maxRank=0 ;
	
	void bestMove (){
		 Block blockTemp = new Block(board.currentBlock);
		 maxRank = -99999999999999999L ;
		 long tempRank =0;
	
		// xoay 4 vi tri
		for (int i =0; i <=4; i++) {
			
			// di chuyen qua trai het muc
			while (blockTemp.goLeft(board));
			
			// di chuyen qua phai va tinh toan
			do {
				
				tempRank = board.rankBoard(blockTemp, currentChromosome)  ;
				if (tempRank  > maxRank){
					maxRank = tempRank ;
					bestX = blockTemp.x;
					bestDirection = blockTemp.direction;
				}
			}
			while (blockTemp.goRight(board));
			blockTemp.setPosition( Board.BOARD_WIDTH/2-2,0);
			blockTemp.rotation(board);
		
		}///end for rotation 4 direction
		
		//Log.d ("BEST ", "maxRank : " + Float.toString(maxRank) +" = best X : "+  Integer.toString( bestX) + " BEST DIrection :  " + getDirection(bestDirection)) ;

		
		board.currentBlock.isBestPosition = true;
	}// end function
		
		
	String getDirection (blockDirection d) {
		String k = "";
		switch (d) {
		case UP:
			return "UP";
		case DOWN:
			return "DOWN";
		case LEFT:
			return "LEFT";
		case RIGHT:
			return "RIGHT";
		}
		
		return k;
		
	}
	
	void hillClimbing (Chromosome currentChromosome){
		Random ra = new Random();
		int randomW, sign=1;
		randomW = (((int)System.nanoTime() + ra.nextInt())%Chromosome.RangeofGenValue);
		if (ra.nextBoolean())  sign = -1; else sign =1; 
		randomW= randomW *sign;

		
		if (countlimit>limitChromosome/2) {
			while (randomW<=currentValueofCurrentGen){
				randomW = (((int)System.nanoTime() + ra.nextInt())%Chromosome.RangeofGenValue);
				if (ra.nextBoolean())  sign = -1; else sign =1; 
				randomW= randomW *sign;
			}
			
		}
		else {
			while (randomW>=currentValueofCurrentGen){
				randomW = (((int)System.nanoTime() + ra.nextInt())%Chromosome.RangeofGenValue);
				if (ra.nextBoolean())  sign = -1; else sign =1; 
				randomW= randomW *sign;
			}
			
		}
		
		currentChromosome.gen[currentChromosome.genIndex] =randomW;
	}
	
	// better Chromosome
	 void logPopulation (Chromosome currentChromosome, boolean isClimb){
		File root = Environment.getExternalStorageDirectory();
		String path = root+"/DoodleTetris/";
		boolean exists = (new File(path).exists());
		if (!exists) {new File(path).mkdirs();}
		File mapInfo = null;
		if (!isClimb)
		mapInfo = new File(path+ "Chromosome"+ Integer.toString(countChromosome)+"_Log.txt" );
		else 
		mapInfo = new File(path+ "Chromosome"+ Integer.toString(countChromosome)+"_Log_CLIMB.txt" );
		
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
				outer.write(" ==========CHROMOSOME  "+ Integer.toString(countChromosome) +" STATISTIC =========== :\n");
			
				String ChromosomeStat= "";
				// ghi thong so tung Chromosome
				
					// thong so gen
					for (int k =0; k<Chromosome.numOfGen;k++)
						ChromosomeStat +=  Integer.toString( currentChromosome.gen[k])+ " , "  ;
					ChromosomeStat += ";"  ;
					// thong so
					ChromosomeStat +="\n FITNESS VALUE :" +  Integer.toString( currentChromosome.fitnessValue) ;
					ChromosomeStat += "\n";
					outer.write(ChromosomeStat);
					ChromosomeStat= "";
		
					outer.write("-- RESULT --\n");
		
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
}
