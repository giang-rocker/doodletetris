package myGame.doodleTetris;

import myGame.doodleTetris.Block.BlockType;
import myGame.doodleTetris.ClassicGameScreen.GameState;
import myGame.doodleTetris.framework.Game;
import myGame.doodleTetris.framework.SingleTouch;

public class AdvanceGameScreen extends ClassicGameScreen {

	public AdvanceGameScreen(Game game) {
	
		super(game);
		// TODO Auto-generated constructor stub
	}
	@Override
	void updateRunning (float deltaTime){
		super.updateRunning(deltaTime);
		
		if(time%(40*30) == 0 ) board.addNewLine();
	}
	
	@Override
	void updatePause (){
		SingleTouch TouchEvent = game.getTouchEvent();
		Asset.bg_track.pause();
		if ( Asset.UI_Pause.isTouch(TouchEvent)   ){
			gameState = GameState.Running;
	//			Music.startMusic();
		}
		// kiem tra xac nhan bam
		if ( Asset.btn_mainMenu.isTouch(TouchEvent)   )
			game.setScreen(new MainMenu(game));
		if ( Asset.btn_playAgain.isTouch(TouchEvent)   )
			game.setScreen(new AdvanceGameScreen(game));
	}

	@Override
	// kiem tra trang thai on game over
	void updateGameOver (){
		SingleTouch TouchEvent = game.getTouchEvent();
		
		if ( Asset.UI_GameOver.isTouch(TouchEvent)   )
			game.setScreen(new MainMenu(game));
		if ( Asset.btn_mainMenu.isTouch(TouchEvent)   )
			game.setScreen(new MainMenu(game));
		if ( Asset.btn_playAgain.isTouch(TouchEvent)   )
			game.setScreen(new AdvanceGameScreen(game));
	}
	
	
	
}
