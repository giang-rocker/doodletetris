package myGame.doodleTetris;

import myGame.doodleTetris.framework.Game;
import myGame.doodleTetris.framework.SingleTouch;

public class CrazyGameScreen extends ClassicGameScreen {

	boolean upSideDown = false ;
	
	public CrazyGameScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	void updateRunning (float deltaTime){
		super.updateRunning(deltaTime);
		
		if(time%(40*30) == 0 ){ board.addNewLine(); upSideDown = !upSideDown;}
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
	
	@Override
	void drawBoard (Board board) {
		int unit_cell = Block.UNIT_CELL;
		// ve board
		
		if (upSideDown){
		for (int i=0;i<Board.BOARD_WIDTH;i++){
			for (int j=4;j<Board.BOARD_HEIGHT;j++) {
				drawCell(board.statusBoard[i][Board.BOARD_HEIGHT+3-j],(i+1)*unit_cell,(j-4)*unit_cell);
				}// for j
			}//for i
		
		}
		else {
			for (int i=0;i<Board.BOARD_WIDTH;i++){
				for (int j=4;j<Board.BOARD_HEIGHT;j++) {
					drawCell(board.statusBoard[i][j],(i+1)*unit_cell,(j-4)*unit_cell);
					}// for j
				}//for i
			
		}
	}
	@Override
	// ve Block ()
	void drawBlock (Block block, int startX, int startY) {
		int unit_cell = Block.UNIT_CELL;
		if (upSideDown){
		for (int i=0;i<Block.BLOCK_HEIGHT;i++){
			for (int j=0;j<Block.BLOCK_HEIGHT;j++) {
				if (block.status[i][j])
				drawCell(block.type,(block.x+i)*unit_cell +startX ,(Board.BOARD_HEIGHT+3-block.y-j)*unit_cell +startY);
				}// for j
			}//for i
	
		}
		else {
			for (int i=0;i<Block.BLOCK_HEIGHT;i++){
				for (int j=0;j<Block.BLOCK_HEIGHT;j++) {
					if (block.status[i][j])
					drawCell(block.type,(block.x+i)*unit_cell +startX ,(block.y+j)*unit_cell +startY);
					}// for j
				}//for i
		}
			
		}
	
}
