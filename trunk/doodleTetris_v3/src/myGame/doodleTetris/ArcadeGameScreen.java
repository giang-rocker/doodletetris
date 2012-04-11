package myGame.doodleTetris;

import java.io.IOException;

import myGame.doodleTetris.Block.BlockType;
import myGame.doodleTetris.framework.AndroidGraphics;
import myGame.doodleTetris.framework.Game;
import myGame.doodleTetris.framework.SingleTouch;

public class ArcadeGameScreen extends ClassicGameScreen {
	// create doi tuiong load map
	int id_stage;
	LoadMap loadMap;

	@Override
	public void setupButton() {
		super.setupButton();
		// set lai menu button
		Asset.btn_nextStage.setPosition(48, 312);
		Asset.btn_playAgain.setPosition(48, 552);
		Asset.btn_menuStage.setPosition(48, 432);
				
	};
	
	public ArcadeGameScreen(Game game, int id) {
		// TODO Auto-generated constructor stub
		super(game);
		id_stage = id;
		loadMap = new LoadMap(game);
		setBoardMap(id);
		setupButton();
		
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
	SingleTouch TouchEvent = game.getTouchEvent();
		
		
		int duration = 80000000;
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
		
		// check stageClean state
		if (checkStageClean ())  {
			// stageCelan 
			 strScore = ""+currentScore;  
			gameState = GameState.StageClean; 
			Asset.sound_stageClean.play();
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
		drawStar (2, 138 ,192+36);
		
	}
	@Override
	public void updateStageClean () {
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
	
	boolean checkStageClean () {
		for (int i=0;i<Board.BOARD_WIDTH;i++) {
			
			if (board.map[i][Board.BOARD_HEIGHT-1] == BlockType.MAP) return false;
		}
		
		return true;
	}
	

}
