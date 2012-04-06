package myGame.doodleTetris;

import java.io.IOException;

import myGame.doodleTetris.Block.BlockType;
import myGame.doodleTetris.framework.AndroidGame;
import myGame.doodleTetris.framework.AndroidGraphics;
import myGame.doodleTetris.framework.Game;
import myGame.doodleTetris.framework.SingleTouch;

public class ArcadeGameScreen extends ClassicGameScreen {
	int k;

	public ArcadeGameScreen(Game game, int id) {
		// TODO Auto-generated constructor stub
		super(game);
		setBoardMap(id);
		setupButton();
		//Duong insert method sound and music
		setSound();
		setMusic();
		StatusMap.setFirstStatus(AndroidGame.am);
		//
	}
	
	public void setBoardMap(int id){
		board = new Board();
		//set map
		try {
			setMap(id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
	}
	

	//Duong set map
	void setMap(int id) throws IOException{
		switch (id) {
		case 1:
			LoadMap.accessFile("Map/mapHeart");
			break;
		case 2:
			LoadMap.accessFile("Map/mapRocket");
			break;
		default:
			LoadMap.accessFile("Map/map1");
			break;
		}
		LoadMap.readFile();
		LoadMap.addMapToBoard(board);
	}
	
	@Override
	void updateRunning (float deltaTime){
	SingleTouch TouchEvent = game.getTouchEvent();
		int duration = 200000000;
		if (timeExpired(start,duration))	{
			start = (int)System.nanoTime();
			if ( Asset.btn_rotate.isTouch(TouchEvent)   )
				board.currentBlock.rotation(board);
			
			if ( Asset.btn_left.isTouch(TouchEvent)   )
				board.currentBlock.goLeft(board);
			
			if ( Asset.btn_right.isTouch(TouchEvent)   )
				board.currentBlock.goRight(board);
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
				Music.pauseMusic();
			}
			else{
				Asset.icon_music.setBitmap(g.newBitmap("Button/icon_music.png"));
				Music.startMusic();
			}
			
			isMusic = !isMusic;
		}
	
		if ( Asset.icon_sound.isTouch(TouchEvent)   ){
			if (isSound)
			{
				Asset.icon_sound.setBitmap(g.newBitmap("Button/icon_sound_dis.png"));
				Sound.unloadSound();
			}
			else 
			{
				Asset.icon_sound.setBitmap(g.newBitmap("Button/icon_sound.png"));
				Sound.setResSoundID(myGame.doodleTetris.R.raw.endfall);
				Sound.loadSound(AndroidGame.getContext());
			}
		
			isSound= !isSound;
		}
		
		if (Asset.btn_pause.isTouch(TouchEvent)) {gameState = GameState.Paused; return;}
		if (board.gameOver) {gameState = GameState.GameOver; return;}

		board.updateForArcadeGameScreen(deltaTime);
		
			
		if (oldScore != board.score){
			oldScore = oldScore+5;
			strScore = "" + oldScore;
		}
		
		
		
	}
	@Override
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
			//Duong insert case MAP
		case MAP:
			g.drawImage(Asset.block_map.bitmap,x,y);
			break;
		}// switch case
	}
	
	
}
