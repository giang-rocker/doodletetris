package myGame.doodleTetris;

import myGame.doodleTetris.framework.Game;

public class CrazyGameScreen extends ClassicGameScreen {

	
	public CrazyGameScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	void drawBoard(Board board) {
	int unit_cell = Block.UNIT_CELL;
		
		// ve board
	for (int j=4;j<Board.BOARD_HEIGHT;j++){
		for (int i=0;i<Board.BOARD_WIDTH;i++)	 {
				drawCell(board.statusBoard[i][j],(Board.BOARD_WIDTH-i)*unit_cell,(Board.BOARD_HEIGHT-1-j)*unit_cell);
				}// for j
			}//for i
		
		// ve block
			for (int i=Block.BLOCK_HEIGHT-1;i>=0;i--){
			for (int j=Block.BLOCK_WIDTH-1;j>=0;j--) {
				if (board.currentBlock.status[i][j])
				drawCell(board.currentBlock.type,(Board.BOARD_HEIGHT-board.currentBlock.y+j-4)*unit_cell,(Board.BOARD_WIDTH-3-board.currentBlock.x+i+1)*unit_cell);
				}// for j
			}//for i
	} 
}
