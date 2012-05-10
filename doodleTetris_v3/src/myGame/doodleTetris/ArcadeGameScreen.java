package myGame.doodleTetris;

import java.io.IOException;
import java.util.Random;

import myGame.doodleTetris.Block.BlockType;
import myGame.doodleTetris.framework.AndroidGraphics;
import myGame.doodleTetris.framework.Game;
import myGame.doodleTetris.framework.Music;
import myGame.doodleTetris.framework.SingleTouch;
import myGame.doodleTetris.framework.Sound;

public class ArcadeGameScreen extends ClassicGameScreen {
	// create doi tuiong load map
	int id_stage;
	LoadMap loadMap;
	int rate;
	
	@Override
	public void setup() {
		super.setup();
		// set lai menu button
		Asset.btn_nextStage.setPosition(48, 312);
		Asset.btn_playAgain.setPosition(48, 552);
		Asset.btn_menuStage.setPosition(48, 432);
		Board.BOARD_HEIGHT = 32;
		Board.BOARD_WIDTH=13;
				
	};
	
	public ArcadeGameScreen(Game game, int id) {
		// TODO Auto-generated constructor stub
		super(game);
		id_stage = id;
		loadMap = new LoadMap(game);
		setBoardMap(id);
		setup();
		
	}
	//Duong set map
		// Giang - sửa load theo ID

	public void setBoardMap(int id){
		board = new Board();
		//set map
		try {
			String pathDir="Map";
			
			String list[]= game.getAsset().list(pathDir);
			loadMap.accessFile(pathDir + "/" +list[id]);
			loadMap.readFile();
			loadMap.addMapToBoard(board);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
	}
	

	
	@Override
	void updateRunning (float deltaTime){
		
		
		if (isAutoPlay) {
			// tinh toan bestMove
			if (board.currentBlock.isBestPosition == false){
				bestMove();
				// auto di chuyen
			while (board.currentBlock.direction != bestDirection) {board.currentBlock.rotation(board);}
			while (bestX > board.currentBlock.x) { board.currentBlock.goRight(board); }
			while (bestX < board.currentBlock.x) { board.currentBlock.goLeft(board); }
			//tu dong di xuong
			while (board.currentBlock.goDown(board)) ;
			}
		}
		
	SingleTouch TouchEvent = game.getTouchEvent();
		
		int duration = 100000000;
		if (timeExpired(start,duration))	{
			start = (int)System.nanoTime();
			
			if ( Asset.btn_rotate.isTouchDown(TouchEvent)   )
				{ board.currentBlock.rotation(board); 		 Asset.sound_move.play(); }
				
			
			if ( Asset.btn_left.isTouchDown(TouchEvent)   )
			{	board.currentBlock.goLeft(board);	 Asset.sound_move.play(); }
			
			if ( Asset.btn_right.isTouchDown(TouchEvent)   )
				{board.currentBlock.goRight(board); 	 Asset.sound_move.play(); }
			}
	
		if ( Asset.btn_down.isTouchDown(TouchEvent)   )
			board.currentBlock.goDown(board);
		
		AndroidGraphics g = game.getGraphics();
		// check touch item
		if ( Block.item ==-1){
		if ( Asset.icon_boomb.isTouch(TouchEvent) )
			{Block.item = 17;Asset.icon_boomb.setPosition(-5000, -5000);}
		if ( Asset.icon_dynamite.isTouch(TouchEvent)   )
			{Block.item = 18;Asset.icon_dynamite.setPosition(-5000, -5000);}
		if ( Asset.icon_rocket.isTouch(TouchEvent)   )
			{Block.item = 19;Asset.icon_rocket.setPosition(-5000, -5000);}
		}
		
		if ( Asset.icon_music.isTouchDown(TouchEvent)   )
		{
			if (isMusic){
				Asset.icon_music.setBitmap(g.newBitmap("Button/icon_music_dis.png"));
				isMusic = !isMusic;
				music.stop();
			}
			else{
				Asset.icon_music.setBitmap(g.newBitmap("Button/icon_music.png"));
				isMusic = !isMusic;
	            setMusic();
			}
			
		}
	
		if ( Asset.icon_sound.isTouchDown(TouchEvent)   ){
			if (isSound)
			{
				Asset.icon_sound.setBitmap(g.newBitmap("Button/icon_sound_dis.png"));
				Sound.appVolume = 0;
		//		Sound.unloadSound();
			}
			else 
			{
				Asset.icon_sound.setBitmap(g.newBitmap("Button/icon_sound.png"));
				Sound.appVolume=Float.parseFloat(dataSetting[3].trim())/100.0f;
				
		//		Sound.setResSoundID(myGame.doodleTetris.R.raw.endfall);
		//		Sound.loadSound(AndroidGame.getContext());
			}
		
			isSound= !isSound;
		}
		if ( Asset.btn_autoPlay.isTouchDown(TouchEvent)   )
		{isAutoPlay = !isAutoPlay; }
		if (Asset.btn_pause.isTouch(TouchEvent)) {gameState = GameState.Paused; return;}
		if (board.gameOver) { Asset.sound_gameOver.play();   gameState = GameState.GameOver; return;}
		
		// check stageClean state
		if (checkStageClean ())  {
			// stageCelan 
			strScore = ""+currentScore;  
			gameState = GameState.StageClean; 
			Asset.sound_stageClean.play();
			StatusMap statusMap = new StatusMap(game);
			String filename = id_stage+"";
			//get rate
			rate=1;
			int timeForRow= (300*40)*((id_stage+1)*10/100);
			
			for(int i=1;i<=2;i++){
				if(time>=LoadMap.rowNum*timeForRow*(i-1) && time <LoadMap.rowNum*timeForRow*(i)){
					rate = 3-i;
				}
			}
			//
			//Kiểm tra time nào nhỏ hơn
			if(statusMap.openStatus(filename)){				
				if(Float.parseFloat(statusMap.data[1])>Float.parseFloat(time+"")){
					statusMap.saveStatus(filename, new LevelInfo (id_stage,time,currentScore,rate,true));
				}
			}
			else{
				statusMap.saveStatus(filename, new LevelInfo (id_stage,time,currentScore,rate,true));
			}
			//
			return;
			
		}
		board.updateForArcadeGameScreen(deltaTime);
		
			
		// check an diem
		if (currentScore != board.score){
			// kiem tra level
			if (level != board.level ) { Asset.sound_nextLevel.play();  level = board.level;}
			else Asset.sound_cleanRow.play();
			currentScore = board.score;
		}
		
		// set diem
		if (tempScore<currentScore-5) { tempScore+=5; }
		else { tempScore=currentScore; }
		
		strScore = "" + tempScore;
		
		
		// time running
		time++;
		
		
	}
	@Override
	void drawStageCleanUI (){
		AndroidGraphics g = game.getGraphics();
		g.drawImage(Asset.UI_StageClear);
		g.drawImage(Asset.btn_nextStage);
		
		// set lai position play Again
		Asset.btn_playAgain.setPosition(48, 552);
		g.drawImage(Asset.btn_playAgain);
		g.drawImage(Asset.btn_menuStage);
		drawScore(""+currentScore, 84,180);
		drawTime (time, 192, 180);
		drawStar (rate, 138 ,192+36);
		
	}
	@Override
	public void updateStageClean () {
		music.pause();
		SingleTouch TouchEvent = game.getTouchEvent();
		
		if ( Asset.UI_GameOver.isTouch(TouchEvent)   )
			game.setScreen(new MainMenu(game));
		if ( Asset.btn_nextStage.isTouch(TouchEvent)   )
			game.setScreen(new ArcadeGameScreen(game,++id_stage));
		if ( Asset.btn_playAgain.isTouch(TouchEvent)   )
			game.setScreen(new ArcadeGameScreen(game,id_stage));
		if ( Asset.btn_menuStage.isTouch(TouchEvent)   )
			game.setScreen(new SelectLevelScreen(game));
	}
	
	@Override
	void drawPauseUI (){
		AndroidGraphics g = game.getGraphics();
		g.drawImage(Asset.UI_Pause);
		Asset.btn_playAgain.setPosition(48, 312);
		g.drawImage(Asset.btn_playAgain);
		g.drawImage(Asset.btn_menuStage);
		
	}
	@Override
	public void updatePause () {
		SingleTouch TouchEvent = game.getTouchEvent();
		
		if ( Asset.UI_Pause.isTouch(TouchEvent)   )
			{gameState = GameState.Running;}
		if ( Asset.btn_playAgain.isTouch(TouchEvent)   )
			game.setScreen(new ArcadeGameScreen(game,id_stage));
		if ( Asset.btn_menuStage.isTouch(TouchEvent)   )
			game.setScreen(new SelectLevelScreen(game));
		}
	@Override
	void drawGameOverUI (){
		AndroidGraphics g = game.getGraphics();
		g.drawImage(Asset.UI_GameOver);
		Asset.btn_playAgain.setPosition(48, 312);
		g.drawImage(Asset.btn_playAgain);
		g.drawImage(Asset.btn_menuStage);
		
		drawStringNumber (""+currentScore, 84,192);
		drawTime (time, 192, 192);
		
	}
	@Override
	
	void updateGameOver (){
		try {
			music.stop();	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		SingleTouch TouchEvent = game.getTouchEvent();
		
			if ( Asset.btn_playAgain.isTouch(TouchEvent)   )
			game.setScreen(new ArcadeGameScreen(game,id_stage));
		if ( Asset.btn_menuStage.isTouch(TouchEvent)   )
			game.setScreen(new SelectLevelScreen(game));
	}
	
	boolean checkStageClean () {
		for (int i=0;i<Board.BOARD_WIDTH;i++) {
			for(int j=Board.BOARD_HEIGHT;j>(Board.BOARD_HEIGHT-LoadMap.rowNum);j--){
				if (board.statusBoard[i][j-1] == BlockType.MAP) return false;
			}
		}
		
		return true;
	}
	

}
