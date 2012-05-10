package myGame.doodleTetris;

import myGame.doodleTetris.framework.AndroidGraphics;
import myGame.doodleTetris.framework.Game;
import myGame.doodleTetris.framework.Screen;

public class AboutScreen extends Screen {

	public AboutScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		this.screenName = "About";
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void present(float deltaTime) {
		// TODO Auto-generated method stub
		AndroidGraphics g = game.getGraphics();
		g.drawImage(Asset.bg_about);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
