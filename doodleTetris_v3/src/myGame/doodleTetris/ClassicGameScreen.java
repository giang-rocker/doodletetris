package myGame.doodleTetris;

import java.util.Random;

import myGame.doodleTetris.Block.BlockType;
import myGame.doodleTetris.framework.AndroidGraphics;
import myGame.doodleTetris.framework.Game;
import myGame.doodleTetris.framework.Screen;
import myGame.doodleTetris.framework.SingleTouch;

public class ClassicGameScreen extends Screen {

	boolean isMusic = true;
	boolean isSound = true;
	
	public void setupButton () {
		// set game button arrow
		Asset.btn_left.setPosition(48, 696);
		Asset.btn_right.setPosition(216, 696);
		Asset.btn_rotate.setPosition(360, 696);
		Asset.btn_down.setPosition(360, 600);
		Asset.btn_pause.setPosition(384, 72);
		
		// set menu button
		Asset.btn_mainMenu.setPosition(48, 312);
		Asset.btn_newGame.setPosition(48, 432);
		
		// set icon
		Asset.icon_boomb.setPosition(384,360);
		Asset.icon_dynamite.setPosition(384,360+72);
		Asset.icon_rocket.setPosition(384,360+72+72);
		
		Asset.icon_music.setPosition(360,24);
		Asset.icon_sound.setPosition(360+48,24);
		
		Random r = new Random ();
		Asset.bg_gameScreen = Asset.list_bg[r.nextInt(5)];
		
		
	}
	
	enum GameState {
		Ready,
		Running,
		Paused,
		GameOver
	}
	
	GameState gameState = GameState.Running;
	Board board;
	int oldScore=0;
	String strScore="0";
	int level=1;
	int time=0;
	String minus,second;
	public ClassicGameScreen(Game game) {
		super(game);
		board = new Board();
		setupButton ();
		// TODO Auto-generated constructor stub
	//	play track
//		Asset.bg_track.play ();
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
	g.drawImage(Asset.btn_left);
	g.drawImage(Asset.btn_right);
	g.drawImage(Asset.btn_rotate);
	g.drawImage(Asset.btn_down);
	g.drawImage(Asset.btn_pause);
	// icon
	g.drawImage(Asset.icon_boomb);
	g.drawImage(Asset.icon_dynamite);
	g.drawImage(Asset.icon_rocket);
	
	//
	g.drawImage(Asset.icon_music);
	g.drawImage(Asset.icon_sound);

	drawScore(strScore);
	drawLevel( level);
	drawTime (time,360,312);

	if (!timeExpired(startDrawBonus, bonusDuration)){
		drawBonus(board.getBonus());
	}
	drawBoard(board);
	
	
	if (gameState == GameState.Paused )
		{drawPauseUI(); }
	
	if (gameState == GameState.GameOver )
	{drawGameOverUI();}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		if (gameState == GameState.Running)
			gameState = GameState.Paused;
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
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
				drawCell(board.map[i][j],(i+1)*unit_cell,(j-4)*unit_cell);
				}// for j
			}//for i
		
		// ve block
		for (int i=0;i<Block.BLOCK_HEIGHT;i++){
			for (int j=0;j<Block.BLOCK_HEIGHT;j++) {
				if (board.currentBlock.status[i][j])
				drawCell(board.currentBlock.type,(board.currentBlock.x+i+1)*unit_cell,(board.currentBlock.y+j-4)*unit_cell);
				}// for j
			}//for i
		
	}
	// ve block
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
		case BOOMB:
			g.drawImage(Asset.item_boomb.bitmap,x,y);
			break;
		case NULL:
			g.drawImage(Asset.block_null.bitmap,x,y);
			break;
			
		}// switch case
	}
	// ve diem
	public void drawScore(String score){
		String o ="0000";
		
		int len  = score.length();
		score = o.substring(len) + score;
		
		int UNIT_CHAR = 24;
		int startX = 360;
		int startY = 240;
		for (int i=0;i<4;i++){
			drawNumber(score.charAt(i),startX+i*UNIT_CHAR,startY);
		
		}
	}
	
	//ve level
	public void drawLevel(int level){
		AndroidGraphics g = game.getGraphics();
		g.drawImage(Asset.numberLevel[level].bitmap,24,168);
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
	void updateRunning (float deltaTime){
		SingleTouch TouchEvent = game.getTouchEvent();
		
		
		int duration = 80000000;
		if (timeExpired(start,duration))	{
			start = (int)System.nanoTime();
			
			if ( Asset.btn_rotate.isTouch(TouchEvent)   )
				{ board.currentBlock.rotation(board); 		 Asset.sound_move.play(); }
				
			
			if ( Asset.btn_left.isTouch(TouchEvent)   )
			{	board.currentBlock.goLeft(board);	 Asset.sound_move.play(); }
			
			if ( Asset.btn_right.isTouch(TouchEvent)   )
				{board.currentBlock.goRight(board); 	 Asset.sound_move.play(); }
			}
	
		if ( Asset.btn_down.isTouch(TouchEvent)   )
			board.currentBlock.goDown(board);
		
		AndroidGraphics g = game.getGraphics();
		// check touch item
		if ( Block.item ==-1){
		if ( Asset.icon_boomb.isTouch(TouchEvent) )
			{Block.item = 7;Asset.icon_boomb.setPosition(-5000, -5000);}
		if ( Asset.icon_dynamite.isTouch(TouchEvent)   )
			{Block.item = 8;Asset.icon_dynamite.setPosition(-5000, -5000);}
		if ( Asset.icon_rocket.isTouch(TouchEvent)   )
			{Block.item = 9;Asset.icon_rocket.setPosition(-5000, -5000);}
		}
		
		if ( Asset.icon_music.isTouch(TouchEvent)   )
		{
			if (isMusic){
				Asset.icon_music.setBitmap(g.newBitmap("Button/icon_music_dis.png"));
		//		Music.pauseMusic();
			}
			else{
				Asset.icon_music.setBitmap(g.newBitmap("Button/icon_music.png"));
		//		Music.startMusic();
			}
			
			isMusic = !isMusic;
		}
	
		if ( Asset.icon_sound.isTouch(TouchEvent)   ){
			if (isSound)
			{
				Asset.icon_sound.setBitmap(g.newBitmap("Button/icon_sound_dis.png"));
		//		Sound.unloadSound();
			}
			else 
			{
				Asset.icon_sound.setBitmap(g.newBitmap("Button/icon_sound.png"));
		//		Sound.setResSoundID(myGame.doodleTetris.R.raw.endfall);
		//		Sound.loadSound(AndroidGame.getContext());
			}
		
			isSound= !isSound;
		}
		
		if (Asset.btn_pause.isTouch(TouchEvent)) {gameState = GameState.Paused; return;}
		if (board.gameOver) { Asset.sound_gameOver.play();   gameState = GameState.GameOver; return;}

		board.update(deltaTime);
		
			
		if (oldScore != board.score){
			oldScore = oldScore+5;
			strScore = "" + oldScore;
		}
		
		level = board.level;
		// finis all
		time++;
	}
	
	// kiem tra trang thai on pause
	void updatePause (){
		SingleTouch TouchEvent = game.getTouchEvent();
		Asset.bg_track.pause();
		if ( Asset.UI_Pause.isTouch(TouchEvent)   ){
			gameState = GameState.Running;
	//			Music.startMusic();
		}
		if ( Asset.btn_mainMenu.isTouch(TouchEvent)   )
			game.setScreen(new MainMenu(game));
		if ( Asset.btn_newGame.isTouch(TouchEvent)   )
			game.setScreen(new ClassicGameScreen(game));
	}

	// kiem tra trang thai on game over
	void updateGameOver (){
		SingleTouch TouchEvent = game.getTouchEvent();
		
		if ( Asset.UI_GameOver.isTouch(TouchEvent)   )
			game.setScreen(new MainMenu(game));
		if ( Asset.btn_mainMenu.isTouch(TouchEvent)   )
			game.setScreen(new MainMenu(game));
		if ( Asset.btn_newGame.isTouch(TouchEvent)   )
			game.setScreen(new ClassicGameScreen(game));
	}
	
	// ve pause
	void drawPauseUI (){
		AndroidGraphics g = game.getGraphics();
		g.drawImage(Asset.UI_Pause);
		g.drawImage(Asset.btn_mainMenu);
		g.drawImage(Asset.btn_newGame);
		
	}
	
	// ve game ogber
	void drawGameOverUI (){
		AndroidGraphics g = game.getGraphics();
		g.drawImage(Asset.UI_GameOver);
		g.drawImage(Asset.btn_mainMenu);
		g.drawImage(Asset.btn_newGame);
		drawStringNumber (strScore, 84,192);
		drawTime (time, 192, 192);
		
	}
	// ve chuoi so s tai vi tri bat dau X, Y
	void drawStringNumber (String s, int startX, int startY) {
		
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
		
		drawStringNumber (minus,StartX,StartY);
		drawStringNumber (":",StartX+48,StartY);
		drawStringNumber (second,StartX+44+12,StartY);
	}
	// kiem tra ki han
	public boolean timeExpired (int startTime, int duration)
	{
		if ((int)System.nanoTime() - startTime >= duration) 
		{  return true;}
		return false;
	}


}
